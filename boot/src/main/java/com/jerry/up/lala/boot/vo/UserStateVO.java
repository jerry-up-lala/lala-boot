package com.jerry.up.lala.boot.vo;

import lombok.Data;

import java.util.List;

/**
 * <p>Description: 用户保存
 *
 * @author FMJ
 * @date 2023/12/8 17:22
 */
@Data
public class UserStateVO {

    /**
     * 账号ID列表
     */
    private List<String> userIdList;

    /**
     * 状态[true-启用]
     */
    private Boolean state;

}
