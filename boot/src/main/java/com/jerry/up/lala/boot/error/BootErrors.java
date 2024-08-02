package com.jerry.up.lala.boot.error;

import com.jerry.up.lala.framework.common.exception.Error;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * <p>Description: 错误信息
 *
 * @author FMJ
 * @date 2023/9/5 14:26
 */
@Getter
@AllArgsConstructor
public enum BootErrors implements Error {

    /**
     * 全局异常
     */
    LOGIN_NAME_PASSWORD_INVALID("110001", "用户名或者密码错误，请确认"),

    LOGIN_TENANT_NAME_INVALID("110002", "集团名称错误，请确认"),

    TENANT_NAME_EXISTS("110003", "集团名称已存在，请确认"),

    REDIS_KEY_EXISTS("110004", "Key已存在，请确认"),

    REDIS_KEY_INVALID("110005", "Key已失效，请确认"),

    REDIS_KEY_DATA_TYPE_INVALID("110006", "Key数据类型错误，请确认"),

    USER_LOGIN_NAME_EXISTS("110007", "用户名已存在，请确认"),

    ROLE_NAME_EXISTS("110008", "角色名已存在，请确认"),

    NOTICE_USER_EMPTY("110009", "目标用户不能为空，请确认"),

    NOTICE_SEND_STATE_UNSENT("110010", "未发送状态才允许操作，请确认"),

    USER_PERSONAL_OLD_PASSWORD_INVALID("110011", "原密码错误，请确认"),

    GEN_PREVIEW_ERROR("110012", "预览异常"),

    DICT_NAME_EXISTS("110013", "字典名称已存在，请确认"),

    DICT_KEY_EXISTS("110014", "字典标识已存在，请确认"),

    DICT_ITEM_LABEL_EXISTS("110015", "展示名已存在，请确认"),

    DICT_ITEM_VALUE_EXISTS("110016", "值已存在，请确认");

    private final String code;

    private final String msg;

}
