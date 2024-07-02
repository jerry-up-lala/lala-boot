package com.jerry.up.lala.boot.bo;

import com.jerry.up.lala.boot.dto.RoleUserDTO;
import com.jerry.up.lala.boot.entity.SysMenu;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.Map;

/**
 * <p>Description: 用户加载BO
 *
 * @author FMJ
 * @date 2023/12/21 09:56
 */
@Data
@Accessors(chain = true)
public class UserLoadBO {

    private Boolean role;

    private Boolean menu;

    private Map<String, List<RoleUserDTO>> roleMap;

    private Map<String, List<SysMenu>> menuMap;

}
