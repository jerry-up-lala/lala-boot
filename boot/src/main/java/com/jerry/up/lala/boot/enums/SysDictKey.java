package com.jerry.up.lala.boot.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>Description: sys_dict.dict_key
 *
 * @author FMJ
 * @date 2024/5/8 17:19
 */
@Getter
@AllArgsConstructor
public enum SysDictKey {

    STATE("STATE"),
    CRUD_LIST("CRUD_LIST"),
    CRUD_TREE("CRUD_TREE");

    private final String value;
}
