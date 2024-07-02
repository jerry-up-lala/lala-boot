package com.jerry.up.lala.boot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jerry.up.lala.boot.access.AccessTenantConstant;
import com.jerry.up.lala.boot.entity.SysMenu;
import com.jerry.up.lala.boot.enums.SysMenuType;
import com.jerry.up.lala.boot.mapper.SysMenuMapper;
import com.jerry.up.lala.boot.service.SysMenuService;
import com.jerry.up.lala.boot.vo.SysMenuButtonVO;
import com.jerry.up.lala.boot.vo.SysMenuRouteMetaVO;
import com.jerry.up.lala.boot.vo.SysMenuRouteVO;
import com.jerry.up.lala.framework.core.common.DataBody;
import com.jerry.up.lala.framework.core.common.Errors;
import com.jerry.up.lala.framework.core.data.DataUtil;
import com.jerry.up.lala.framework.core.exception.ServiceException;
import com.jerry.up.lala.framework.core.data.StringUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author jerry
 * @description 针对表【sys_menu(菜单表)】的数据库操作Service实现
 * @createDate 2023-11-27 16:15:31
 */
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Override
    public List<SysMenuButtonVO> list(DataBody<String> dataBody) {
        LambdaQueryWrapper<SysMenu> queryWrapper = new LambdaQueryWrapper<SysMenu>()
                .ne(SysMenu::getAccessCodes, AccessTenantConstant.TENANT)
                .orderByAsc(SysMenu::getMenuOrder);
        if (dataBody != null && StringUtil.isNotNull(dataBody.getValue())) {
            queryWrapper.and(item -> item
                    .like(SysMenu::getPath, dataBody.getValue())
                    .or()
                    .like(SysMenu::getName, dataBody.getValue())
                    .or()
                    .like(SysMenu::getAccessCodes, dataBody.getValue())
                    .or()
                    .like(SysMenu::getLocaleZhCn, dataBody.getValue()));
        }
        // 菜单列表
        List<SysMenu> menuIdList = list(queryWrapper);
        if (CollectionUtil.isEmpty(menuIdList)) {
            return new ArrayList<>();
        }
        List<Long> menuIds = menuIdList.stream().map(SysMenu::getId).collect(Collectors.toList());
        menuIds.addAll(menuIdList.stream().map(SysMenu::getParentId).collect(Collectors.toList()));
        List<SysMenu> menuList = list(new LambdaQueryWrapper<SysMenu>().in(SysMenu::getId, menuIds));
        return convertVO(menuList);
    }

    @Override
    public List<SysMenuButtonVO> convertVO(List<SysMenu> menuList) {
        // 树列表
        List<Tree<Long>> treeList = treeList(menuList);
        return treeList.stream().map(this::convertVO).collect(Collectors.toList());
    }

    @Override
    public List<SysMenuRouteVO> routeList() {
        try {
            List<Tree<Long>> treeList = treeList(list(new LambdaQueryWrapper<SysMenu>().eq(SysMenu::getType, SysMenuType.MENU.getValue())));
            return treeList.stream().map(this::convertRouteVO).collect(Collectors.toList());
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    private List<Tree<Long>> treeList(List<SysMenu> menuList) {
        return DataUtil.buildTree(menuList, item -> new TreeNode<>(item.getId(), item.getParentId(), item.getLocale(), item.getMenuOrder())
                        .setExtra(BeanUtil.beanToMap(item))
                , 0L);
    }

    private SysMenuButtonVO convertVO(Tree<Long> tree) {
        SysMenu sysMenu = BeanUtil.toBean(tree, SysMenu.class);
        SysMenuButtonVO sysMenuButtonVO = DataUtil.toBean(sysMenu, SysMenuButtonVO.class);
        if (tree.hasChild()) {
            sysMenuButtonVO.setChildren(tree.getChildren().stream().map(this::convertVO).collect(Collectors.toList()));
        }
        return sysMenuButtonVO;
    }

    private SysMenuRouteVO convertRouteVO(Tree<Long> tree) {
        SysMenu sysMenu = BeanUtil.toBean(tree, SysMenu.class);
        List<String> breadcrumb = ListUtil.toList(sysMenu.getLocale());
        loadBreadcrumb(breadcrumb, tree);
        SysMenuRouteVO sysMenuRouteVO = DataUtil.toBean(sysMenu, SysMenuRouteVO.class)
                .setMeta(DataUtil.toBean(sysMenu, SysMenuRouteMetaVO.class).setBreadcrumb(ListUtil.reverse(breadcrumb)));
        if (tree.hasChild()) {
            sysMenuRouteVO.setChildren(tree.getChildren().stream().map(this::convertRouteVO).collect(Collectors.toList()));
        }
        return sysMenuRouteVO;
    }

    private void loadBreadcrumb(List<String> breadcrumb, Tree<Long> tree) {
        Tree<Long> treeParent = tree.getParent();
        if (treeParent != null) {
            SysMenu sysMenu = BeanUtil.toBean(treeParent, SysMenu.class);
            if (sysMenu != null && StringUtil.isNotNull(sysMenu.getLocale())) {
                breadcrumb.add(sysMenu.getLocale());
                loadBreadcrumb(breadcrumb, treeParent);
            }
        }
    }
}




