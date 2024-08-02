package com.jerry.up.lala.boot.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.jerry.up.lala.boot.access.TenantAccessConstant;
import com.jerry.up.lala.boot.dto.RoleDTO;
import com.jerry.up.lala.boot.dto.RoleQueryDTO;
import com.jerry.up.lala.boot.entity.*;
import com.jerry.up.lala.boot.error.BootErrors;
import com.jerry.up.lala.boot.mapper.RoleMapper;
import com.jerry.up.lala.boot.service.RoleMenuService;
import com.jerry.up.lala.boot.service.RoleService;
import com.jerry.up.lala.boot.service.SysMenuService;
import com.jerry.up.lala.boot.service.UserService;
import com.jerry.up.lala.boot.vo.RoleInfoVO;
import com.jerry.up.lala.boot.vo.RoleQueryVO;
import com.jerry.up.lala.boot.vo.RoleSaveVO;
import com.jerry.up.lala.boot.vo.RoleVO;
import com.jerry.up.lala.framework.boot.page.PageUtil;
import com.jerry.up.lala.framework.boot.satoken.SaTokenUtil;
import com.jerry.up.lala.framework.common.exception.Errors;
import com.jerry.up.lala.framework.common.exception.ServiceException;
import com.jerry.up.lala.framework.common.model.DataIdBody;
import com.jerry.up.lala.framework.common.model.LoginUser;
import com.jerry.up.lala.framework.common.model.PageQuery;
import com.jerry.up.lala.framework.common.r.PageR;
import com.jerry.up.lala.framework.common.util.BeanUtil;
import com.jerry.up.lala.framework.common.util.CheckUtil;
import com.jerry.up.lala.framework.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jerry
 * @description 针对表【role(角色表)】的数据库操作Service实现
 * @createDate 2023-12-12 11:47:47
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleMenuService roleMenuService;

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public PageR<RoleVO> pageQuery(RoleQueryVO roleQueryVO) {
        RoleQueryDTO queryDTO = BeanUtil.toBean(roleQueryVO, RoleQueryDTO.class);
        IPage<Role> pageResult = pageByDTO(roleQueryVO, queryDTO);
        try {
            return PageUtil.toPageR(pageResult, RoleVO.class);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    public List<RoleVO> listQuery(RoleQueryVO roleQueryVO) {
        RoleQueryDTO queryDTO = BeanUtil.toBean(roleQueryVO, RoleQueryDTO.class);
        List<Role> roleList = listByDTO(queryDTO);
        try {
            return BeanUtil.toBeanList(roleList, RoleVO.class);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    public RoleInfoVO info(Long id) {
        RoleDTO roleDTO = infoDTO(id);
        return BeanUtil.toBean(roleDTO, RoleInfoVO.class);
    }

    private RoleDTO infoDTO(Long id) {
        if (id == null) {
            throw ServiceException.args();
        }
        RoleDTO roleDTO = getBaseMapper().selectDTOById(id);
        if (roleDTO == null) {
            throw ServiceException.notFound();
        }
        BeanUtil.beanFormat(roleDTO);
        return roleDTO;
    }

    @Override
    public void verify(DataIdBody<Long, String> dataIdBody) {
        String value = CheckUtil.dataNull(dataIdBody);
        // 根据role 查询用户列表
        boolean exists;
        try {
            LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<Role>()
                    .eq(Role::getRoleName, value);
            if (dataIdBody.getId() != null) {
                queryWrapper.ne(Role::getId, dataIdBody.getId());
            }
            exists = exists(queryWrapper);
        } catch (Exception e) {
            throw ServiceException.error(Errors.VERIFY_ERROR, e);
        }
        if (exists) {
            throw ServiceException.error(BootErrors.ROLE_NAME_EXISTS);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(RoleSaveVO roleSaveVO) {
        checkRoleSaveVO(roleSaveVO);
        try {
            Date now = new Date();
            String currentUserId = SaTokenUtil.currentUser().getUserId();
            Role role = BeanUtil.toBean(roleSaveVO, Role.class);
            role.setCreateTime(now);
            role.setCreateUser(currentUserId);
            save(role);
            Long id = role.getId();

            // 菜单ID列表
            List<Long> menuIds = roleSaveVO.getMenuIds();
            roleMenuService.add(menuIds, id, now, currentUserId);

        } catch (Exception e) {
            throw ServiceException.error(Errors.SAVE_ERROR, e);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Long id, RoleSaveVO roleSaveVO) {
        checkRoleSaveVO(id, roleSaveVO);
        RoleDTO roleDTO = infoDTO(id);
        try {
            Date now = new Date();
            String currentUserId = SaTokenUtil.currentUser().getUserId();
            List<Long> oldMenuIds = roleDTO.getMenuIds();

            BeanUtil.copy(roleSaveVO, roleDTO);
            roleDTO.setUpdateTime(now);
            roleDTO.setUpdateUser(currentUserId);
            updateById(roleDTO);

            List<Long> menuIds = roleSaveVO.getMenuIds();

            if (CollUtil.isNotEmpty(menuIds)) {
                // 新增的菜单ID列表
                List<Long> addMenuIds = menuIds.stream().filter(item -> !CollUtil.contains(oldMenuIds, item))
                        .collect(Collectors.toList());
                roleMenuService.add(addMenuIds, id, now, currentUserId);
            }

            if (CollUtil.isNotEmpty(oldMenuIds)) {
                // 删除的菜单ID列表
                List<Long> deleteMenuIds = oldMenuIds.stream().filter(item -> !CollUtil.contains(menuIds, item))
                        .collect(Collectors.toList());
                if (CollUtil.isNotEmpty(deleteMenuIds)) {
                    roleMenuService.remove(new LambdaQueryWrapper<RoleMenu>().eq(RoleMenu::getRoleId, id).in(RoleMenu::getMenuId, deleteMenuIds));
                }
            }
        } catch (Exception e) {
            throw ServiceException.error(Errors.UPDATE_ERROR, e);
        }
    }

    @Override
    public List<String> getAccessCodes() {
        LoginUser loginUser = SaTokenUtil.currentUser();
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<>();
        // 非系统管理员 查询绑定角色 菜单
        if (StringUtil.isNotNull(loginUser.getTenantId())) {
            User user = userService.getById(loginUser.getUserId());
            if (user == null) {
                throw ServiceException.notFound();
            }
            if (!BooleanUtil.isTrue(user.getTenantAdmin())) {
                MPJLambdaWrapper<RoleMenu> roleWrapper = new MPJLambdaWrapper<RoleMenu>()
                        .select(RoleMenu::getMenuId).distinct()
                        .leftJoin(Role.class, on -> on.eq(Role::getId, RoleMenu::getRoleId))
                        .leftJoin(User.class, on -> on.eq(User::getId, RoleUser::getUserId))
                        .eq(Role::getState, true);
                List<Long> menuIds = roleMenuService.selectJoinList(Long.class, roleWrapper);
                if (CollUtil.isEmpty(menuIds)) {
                    return new ArrayList<>();
                }
                queryWrapper.in(SysMenu::getId, menuIds);
            } else {
                // 系统管理员 才允许查看集团管理
                queryWrapper.notLike(SysMenu::getAccessCodes, TenantAccessConstant.TENANT);
            }
        }
        List<SysMenu> sysMenuList = sysMenuService.list(queryWrapper);
        List<String> result = new ArrayList<>();
        sysMenuList.forEach(item -> {
            String accessCodes = item.getAccessCodes();
            if (StringUtil.isNotNull(accessCodes)) {
                result.addAll(ListUtil.toList(StrUtil.split(accessCodes, ",")));
            }
        });
        return result;
    }

    private List<Role> listByDTO(RoleQueryDTO roleQueryDTO) {
        try {
            LambdaQueryWrapper<Role> queryWrapper = loadQuery(roleQueryDTO);
            return list(queryWrapper);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    private IPage<Role> pageByDTO(PageQuery pageQuery, RoleQueryDTO queryDTO) {
        Page<Role> page = PageUtil.initPage(pageQuery);
        try {
            LambdaQueryWrapper<Role> queryWrapper = loadQuery(queryDTO);
            return page(page, queryWrapper);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    private LambdaQueryWrapper<Role> loadQuery(RoleQueryDTO queryDTO) {
        LambdaQueryWrapper<Role> queryWrapper = new LambdaQueryWrapper<Role>().orderByAsc(Role::getCreateTime);
        if (queryDTO != null) {

            String roleName = queryDTO.getRoleName();
            if (StringUtil.isNotNull(roleName)) {
                queryWrapper.like(Role::getRoleName, roleName);
            }

            String description = queryDTO.getDescription();
            if (StringUtil.isNotNull(description)) {
                queryWrapper.like(Role::getDescription, description);
            }

            Boolean state = queryDTO.getState();
            if (state != null) {
                queryWrapper.eq(Role::getState, state);
            }

            Date createTimeStart = queryDTO.getCreateTimeStart();
            if (createTimeStart != null) {
                queryWrapper.ge(Role::getCreateTime, createTimeStart);
            }

            Date createTimeEnd = queryDTO.getCreateTimeEnd();
            if (createTimeEnd != null) {
                queryWrapper.le(Role::getCreateTime, createTimeEnd);
            }
        }
        return queryWrapper;
    }

    private void checkRoleSaveVO(RoleSaveVO roleSaveVO) {
        checkRoleSaveVO(null, roleSaveVO);
    }

    private void checkRoleSaveVO(Long id, RoleSaveVO roleSaveVO) {
        if (roleSaveVO == null) {
            throw ServiceException.args();
        }
        String roleName = roleSaveVO.getRoleName();
        Boolean state = roleSaveVO.getState();
        if (StringUtil.isNull(roleName) || state == null) {
            throw ServiceException.args();
        }
        verify(new DataIdBody<>(id, roleName));
    }

}




