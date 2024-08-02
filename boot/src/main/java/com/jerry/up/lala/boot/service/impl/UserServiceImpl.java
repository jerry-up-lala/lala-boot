package com.jerry.up.lala.boot.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.BooleanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.google.common.collect.Lists;
import com.jerry.up.lala.boot.bo.UserLoadBO;
import com.jerry.up.lala.boot.dto.RoleUserDTO;
import com.jerry.up.lala.boot.dto.UserDTO;
import com.jerry.up.lala.boot.dto.UserQueryDTO;
import com.jerry.up.lala.boot.entity.*;
import com.jerry.up.lala.boot.error.BootErrors;
import com.jerry.up.lala.boot.mapper.UserMapper;
import com.jerry.up.lala.boot.service.RoleMenuService;
import com.jerry.up.lala.boot.service.RoleUserService;
import com.jerry.up.lala.boot.service.SysMenuService;
import com.jerry.up.lala.boot.service.UserService;
import com.jerry.up.lala.boot.vo.*;
import com.jerry.up.lala.framework.boot.crypto.RSAUtil;
import com.jerry.up.lala.framework.boot.page.PageUtil;
import com.jerry.up.lala.framework.boot.satoken.SaTokenUtil;
import com.jerry.up.lala.framework.common.exception.Errors;
import com.jerry.up.lala.framework.common.exception.ServiceException;
import com.jerry.up.lala.framework.common.model.DataIdBody;
import com.jerry.up.lala.framework.common.r.PageR;
import com.jerry.up.lala.framework.common.util.BeanUtil;
import com.jerry.up.lala.framework.common.util.CheckUtil;
import com.jerry.up.lala.framework.common.util.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author jerry
 * @description 针对表【user(账号表)】的数据库操作Service实现
 * @createDate 2023-09-06 09:56:37
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private RoleUserService roleUserService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public List<UserDTO> listDTO(UserQueryDTO queryDTO, UserLoadBO userLoadBO) {
        LambdaQueryWrapper<User> queryWrapper = loadQuery(queryDTO);
        List<User> userList = list(queryWrapper);
        return toDTOList(userList, userLoadBO);
    }

    @Override
    public List<UserVO> list(UserQueryVO userQueryVO) {
        try {
            UserQueryDTO queryDTO = BeanUtil.toBean(userQueryVO, UserQueryDTO.class);
            List<UserDTO> userList = listDTO(queryDTO, new UserLoadBO().setRole(true).setMenu(true));
            return BeanUtil.toBeanList(userList, UserVO.class);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    public PageR<UserVO> pageQuery(UserQueryVO userQueryVO) {
        UserQueryDTO queryDTO = BeanUtil.toBean(userQueryVO, UserQueryDTO.class);
        try {
            LambdaQueryWrapper<User> queryWrapper = loadQuery(queryDTO);
            IPage<User> userPage = page(PageUtil.initPage(userQueryVO), queryWrapper);
            List<UserDTO> userDTOList = toDTOList(userPage.getRecords(), new UserLoadBO().setRole(true));
            IPage<UserDTO> userDTOPage = PageUtil.loadPage(userPage, userDTOList);
            return PageUtil.toPageR(userDTOPage, UserVO.class);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    public UserInfoVO info(String id) {
        UserDTO userDTO = get(id, new UserLoadBO().setRole(true).setMenu(true));
        List<SysMenuButtonVO> menuButtonList = sysMenuService.convertVO(userDTO.getMenuList());
        return BeanUtil.toBean(userDTO, UserInfoVO.class).setMenuButtonList(menuButtonList);
    }

    @Override
    public UserPersonalVO personalInfo() {
        UserDTO userDTO = get(SaTokenUtil.currentUser().getUserId(), null);
        return BeanUtil.toBean(userDTO, UserPersonalVO.class);
    }

    @Override
    public String password(String id) {
        User user = get(id);
        return RSAUtil.decrypt(user.getPassWord());
    }

    @Override
    public void personalUpdatePassWord(UserPersonalPassWordVO userPersonalPassWordVO) {
        String oldPassWord = userPersonalPassWordVO.getOldPassWord();
        String newPassWord = userPersonalPassWordVO.getNewPassWord();
        if (StringUtil.isNull(oldPassWord) || StringUtil.isNull(newPassWord)) {
            throw ServiceException.args();
        }
        String password = password(SaTokenUtil.currentUser().getUserId());
        if (!oldPassWord.equals(password)) {
            throw ServiceException.error(BootErrors.USER_PERSONAL_OLD_PASSWORD_INVALID);
        }
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<User>()
                .set(User::getPassWord, RSAUtil.encrypt(newPassWord))
                .set(User::getUpdateUser, SaTokenUtil.currentUser().getUserId())
                .set(User::getUpdateTime, new Date())
                .eq(User::getId, SaTokenUtil.currentUser().getUserId());
        update(updateWrapper);

    }

    @Override
    public void verify(DataIdBody<String, String> dataIdBody) {
        String value = CheckUtil.dataNull(dataIdBody);
        // 根据user_id 查询用户列表
        boolean exists;
        try {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>()
                    .eq(User::getLoginName, value);
            if (StringUtil.isNotNull(dataIdBody.getId())) {
                queryWrapper.ne(User::getId, dataIdBody.getId());
            }
            exists = exists(queryWrapper);
        } catch (Exception e) {
            throw ServiceException.error(Errors.VERIFY_ERROR, e);
        }
        if (exists) {
            throw ServiceException.error(BootErrors.USER_LOGIN_NAME_EXISTS);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(UserSaveVO userSaveVO) {
        checkUserSaveVO(userSaveVO);
        try {
            Date now = new Date();
            String currentUserId = SaTokenUtil.currentUser().getUserId();

            User user = BeanUtil.toBean(userSaveVO, User.class);
            if (user.getState() == null) {
                user.setState(true);
            }
            user.setTenantAdmin(false);
            user.setPassWord(RSAUtil.encrypt(user.getPassWord()));
            user.setCreateTime(now);
            user.setCreateUser(currentUserId);
            save(user);

            List<Long> roleIds = userSaveVO.getRoleIds();
            roleUserService.add(user.getId(), roleIds, now, currentUserId);
        } catch (Exception e) {
            throw ServiceException.error(Errors.SAVE_ERROR, e);
        }
    }

    @Override
    public void update(String id, UserSaveVO userSaveVO) {
        checkUserSaveVO(id, userSaveVO);
        UserDTO oldUser = get(id, new UserLoadBO().setRole(true));
        try {
            Date now = new Date();
            String currentUserId = SaTokenUtil.currentUser().getUserId();

            Boolean state = oldUser.getState();
            BeanUtil.copy(userSaveVO, oldUser, User.Fields.tenantAdmin);
            if (oldUser.getState() == null) {
                oldUser.setState(state);
            }
            oldUser.setPassWord(RSAUtil.encrypt(oldUser.getPassWord()));
            oldUser.setUpdateTime(now);
            oldUser.setUpdateUser(currentUserId);
            updateById(oldUser);

            List<Long> roleIds = userSaveVO.getRoleIds();
            List<Long> oldRoleIds = oldUser.getRoleIds();
            if (CollUtil.isNotEmpty(roleIds)) {
                // 新增的角色ID列表
                List<Long> addRoleIds = roleIds.stream().filter(item -> !CollUtil.contains(oldRoleIds, item))
                        .collect(Collectors.toList());
                roleUserService.add(id, addRoleIds, now, currentUserId);
            }

            if (CollUtil.isNotEmpty(oldRoleIds)) {
                // 删除的角色ID列表
                List<Long> deleteRoleIds = oldRoleIds.stream().filter(item -> !CollUtil.contains(roleIds, item))
                        .collect(Collectors.toList());
                if (CollUtil.isNotEmpty(deleteRoleIds)) {
                    roleUserService.remove(new LambdaQueryWrapper<RoleUser>().eq(RoleUser::getUserId, id).in(RoleUser::getRoleId, deleteRoleIds));
                }
            }
        } catch (Exception e) {
            throw ServiceException.error(Errors.UPDATE_ERROR, e);
        }
    }

    @Override
    public void personalSave(UserPersonalSaveVO userPersonalSaveVO) {
        String mail = userPersonalSaveVO.getMail();
        String nickName = userPersonalSaveVO.getNickName();
        String intro = userPersonalSaveVO.getIntro();
        LambdaUpdateWrapper<User> updateWrapper = new LambdaUpdateWrapper<User>()
                .set(User::getMail, mail).set(User::getNickName, nickName).set(User::getIntro, intro)
                .set(User::getUpdateUser, SaTokenUtil.currentUser().getUserId())
                .set(User::getUpdateTime, new Date())
                .eq(User::getId, SaTokenUtil.currentUser().getUserId());
        update(updateWrapper);
    }

    @Override
    public void checkUserSaveArg(UserSaveVO userSaveVO) {
        if (userSaveVO == null) {
            throw ServiceException.args();
        }
        String loginName = userSaveVO.getLoginName();
        String passWord = userSaveVO.getPassWord();
        String realName = userSaveVO.getRealName();
        if (StringUtil.isNull(loginName) || StringUtil.isNull(passWord) || StringUtil.isNull(realName)) {
            throw ServiceException.args();
        }
    }

    @Override
    public void state(UserStateVO userStateVO) {
        if (userStateVO == null) {
            throw ServiceException.args();
        }
        // 账号列表
        List<String> userIdList = userStateVO.getUserIdList();
        // 状态
        Boolean state = userStateVO.getState();
        if (CollUtil.isEmpty(userIdList) || state == null) {
            throw ServiceException.args();
        }
        LambdaUpdateWrapper<User> update = new LambdaUpdateWrapper<User>()
                .set(User::getState, state)
                .set(User::getUpdateTime, new Date())
                .set(User::getUpdateUser, SaTokenUtil.currentUser().getUserId())
                .in(User::getId, userIdList);
        update(update);
    }

    private UserDTO get(String id) {
        return get(id, null);
    }

    private UserDTO get(String id, UserLoadBO userLoadBO) {
        if (StringUtil.isNull(id)) {
            throw ServiceException.args();
        }
        User user = getById(id);
        if (user == null) {
            throw ServiceException.notFound();
        }
        return toDTO(user, userLoadBO);
    }

    private LambdaQueryWrapper<User> loadQuery(UserQueryDTO queryDTO) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<User>().orderByAsc(User::getId);
        if (queryDTO != null) {
            String id = queryDTO.getId();
            if (id != null) {
                queryWrapper.like(User::getId, id);
            }

            List<String> ids = queryDTO.getIds();
            if (CollUtil.isNotEmpty(ids)) {
                queryWrapper.in(User::getId, ids);
            }

            String loginName = queryDTO.getId();
            if (StringUtil.isNotNull(loginName)) {
                queryWrapper.like(User::getLoginName, loginName);
            }

            String realName = queryDTO.getRealName();
            if (StringUtil.isNotNull(realName)) {
                queryWrapper.like(User::getRealName, realName);
            }

            Boolean state = queryDTO.getState();
            if (state != null) {
                queryWrapper.eq(User::getState, state);
            }

            Boolean tenantAdmin = queryDTO.getTenantAdmin();
            if (tenantAdmin != null) {
                queryWrapper.eq(User::getTenantAdmin, tenantAdmin);
            }

            Date createTimeStart = queryDTO.getCreateTimeStart();
            if (createTimeStart != null) {
                queryWrapper.ge(User::getCreateTime, createTimeStart);
            }

            Date createTimeEnd = queryDTO.getCreateTimeEnd();
            if (createTimeEnd != null) {
                queryWrapper.le(User::getCreateTime, createTimeEnd);
            }
        }
        return queryWrapper;
    }

    private void checkUserSaveVO(UserSaveVO userSaveVO) {
        checkUserSaveVO(null, userSaveVO);
    }

    private void checkUserSaveVO(String id, UserSaveVO userSaveVO) {
        checkUserSaveArg(userSaveVO);
        verify(new DataIdBody<>(id, userSaveVO.getLoginName()));
    }

    private UserDTO toDTO(User user, UserLoadBO loadBO) {
        if (user == null) {
            throw ServiceException.notFound();
        }
        loadBOData(Lists.newArrayList(user.getId()), loadBO);
        return loadExpand(user, loadBO);
    }

    private List<UserDTO> toDTOList(List<User> userList, UserLoadBO loadBO) {
        if (CollectionUtils.isEmpty(userList)) {
            return new ArrayList<>();
        }
        loadBOData(userList.stream().map(User::getId).collect(Collectors.toList()), loadBO);
        return userList.stream().map(item -> loadExpand(item, loadBO)).collect(Collectors.toList());
    }


    private void loadBOData(List<String> userIds, UserLoadBO loadBO) {
        if (loadBO == null || CollUtil.isEmpty(userIds)) {
            return;
        }
        // 加载角色信息
        if (BooleanUtils.isTrue(loadBO.getRole()) || (BooleanUtils.isTrue(loadBO.getMenu()))) {
            // 关联查询角色信息
            MPJLambdaWrapper<RoleUser> roleUserWrapper = new MPJLambdaWrapper<RoleUser>()
                    .selectAll(RoleUser.class)
                    .select(Role::getRoleName, Role::getDescription, Role::getState)
                    .leftJoin(Role.class, on -> on.eq(Role::getId, RoleUser::getRoleId))
                    .orderByAsc(Role::getCreateTime)
                    .in(RoleUser::getUserId, userIds);
            List<RoleUserDTO> roleUserList = roleUserService.selectJoinList(RoleUserDTO.class, roleUserWrapper);
            if (CollectionUtils.isNotEmpty(roleUserList)) {
                Map<String, List<RoleUserDTO>> roleMap = roleUserList.stream().filter(Objects::nonNull).collect(Collectors.groupingBy(RoleUserDTO::getUserId));
                // 角色map
                loadBO.setRoleMap(roleMap);
                // 加载菜单信息
                if (BooleanUtils.isTrue(loadBO.getMenu())) {
                    // 只查询有效角色
                    List<Long> roleIds = roleUserList.stream().filter(item -> BooleanUtil.isTrue(item.getState())).map(RoleUserDTO::getRoleId)
                            .distinct().collect(Collectors.toList());
                    // 角色菜单列表
                    List<RoleMenu> roleMenuList = roleMenuService.list(new LambdaQueryWrapper<RoleMenu>().in(RoleMenu::getRoleId, roleIds));
                    if (CollectionUtils.isNotEmpty(roleMenuList)) {
                        // 角色菜单map
                        Map<Long, List<RoleMenu>> roleMenuMap = roleMenuList.stream().collect(Collectors.groupingBy(RoleMenu::getRoleId));

                        List<Long> menuIds = roleMenuList.stream().map(RoleMenu::getMenuId).distinct().collect(Collectors.toList());
                        // 菜单信息
                        List<SysMenu> sysMenuList = sysMenuService.list(new LambdaQueryWrapper<SysMenu>().in(SysMenu::getId, menuIds));
                        Map<Long, SysMenu> sysMenuMap = sysMenuList.stream().filter(Objects::nonNull)
                                .collect(Collectors.toMap(SysMenu::getId, Function.identity(), (key1, key2) -> key1));

                        Map<String, List<SysMenu>> menuMap = new HashMap<>(16);
                        userIds.forEach(userId -> {
                            List<SysMenu> userMenuList = new ArrayList<>();

                            List<RoleUserDTO> roleList = roleMap.get(userId);
                            if (CollectionUtils.isNotEmpty(roleList)) {
                                Set<Long> userMenuIds = new HashSet<>();
                                roleList.stream().filter(item -> BooleanUtil.isTrue(item.getState())).forEach(item -> {
                                    Long userRoleId = item.getRoleId();
                                    List<RoleMenu> menuList = roleMenuMap.get(userRoleId);
                                    if (CollUtil.isNotEmpty(menuList)) {
                                        userMenuIds.addAll(menuList.stream().map(RoleMenu::getMenuId).collect(Collectors.toList()));
                                    }
                                });
                                userMenuIds.forEach(menuId -> {
                                    SysMenu menu = sysMenuMap.get(menuId);
                                    if (menu != null) {
                                        userMenuList.add(menu);
                                    }
                                });
                            }
                            menuMap.put(userId, userMenuList);
                        });
                        loadBO.setMenuMap(menuMap);
                    }

                }
            }
        }
    }

    private UserDTO loadExpand(User user, UserLoadBO loadBO) {
        UserDTO result = BeanUtil.toBean(user, UserDTO.class);
        if (loadBO != null) {
            // 角色map
            Map<String, List<RoleUserDTO>> roleMap = loadBO.getRoleMap();
            if (BooleanUtils.isTrue(loadBO.getRole())) {
                if (roleMap != null) {
                    List<RoleUserDTO> roleList = roleMap.get(user.getId());
                    result.setRoleList(roleList);
                    if (CollUtil.isNotEmpty(roleList)) {
                        result.setEffectiveRoleNames(roleList.stream().filter(item -> BooleanUtil.isTrue(item.getState()))
                                .map(RoleUserDTO::getRoleName).distinct().collect(Collectors.toList()));
                        result.setInvalidRoleNames(roleList.stream().filter(item -> BooleanUtil.isFalse(item.getState()))
                                .map(RoleUserDTO::getRoleName).distinct().collect(Collectors.toList()));
                        result.setRoleIds(roleList.stream().map(RoleUserDTO::getRoleId).distinct().collect(Collectors.toList()));
                    }
                }
            }
            // 菜单map
            Map<String, List<SysMenu>> menuMap = loadBO.getMenuMap();
            if (BooleanUtils.isTrue(loadBO.getMenu())) {
                if (menuMap != null) {
                    result.setMenuList(menuMap.get(user.getId()));
                }
            }
        }
        return result;
    }
}




