package com.jerry.up.lala.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jerry.up.lala.boot.entity.Role;
import com.jerry.up.lala.boot.vo.RoleInfoVO;
import com.jerry.up.lala.boot.vo.RoleQueryVO;
import com.jerry.up.lala.boot.vo.RoleSaveVO;
import com.jerry.up.lala.boot.vo.RoleVO;
import com.jerry.up.lala.framework.common.model.DataIdBody;
import com.jerry.up.lala.framework.common.r.PageR;

import java.util.List;

/**
 * @author jerry
 * @description 针对表【role(角色表)】的数据库操作Service
 * @createDate 2023-12-12 11:47:47
 */
public interface RoleService extends IService<Role> {

    PageR<RoleVO> pageQuery(RoleQueryVO roleQueryVO);

    List<RoleVO> listQuery(RoleQueryVO roleQueryVO);

    RoleInfoVO info(Long id);

    void verify(DataIdBody<Long, String> dataIdBody);

    void add(RoleSaveVO roleSaveVO);

    void update(Long id, RoleSaveVO roleSaveVO);

    List<String> getAccessCodes();

}
