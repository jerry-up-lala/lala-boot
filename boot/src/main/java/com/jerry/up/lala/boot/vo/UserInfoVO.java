package com.jerry.up.lala.boot.vo;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>Description: 账号 信息 vo
 *
 * @author FMJ
 * @date 2023/8/16 15:54
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class UserInfoVO extends UserVO {

    /**
     * 角色ID列表
     */
    private List<Long> roleIds;

    /**
     * 菜单按钮列表
     */
    private List<SysMenuButtonVO> menuButtonList;

}
