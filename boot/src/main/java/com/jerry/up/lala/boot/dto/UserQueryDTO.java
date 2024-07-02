package com.jerry.up.lala.boot.dto;

import com.jerry.up.lala.boot.vo.UserQueryVO;
import com.jerry.up.lala.framework.core.data.DataFormat;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.Date;
import java.util.List;

/**
 * <p>Description: 用户 查询DTO
 *
 * @author FMJ
 * @date 2023/10/25 13:54
 */
@Data
@Accessors(chain = true)
public class UserQueryDTO {

    /**
     * 账号ID
     */
    private String id;

    /**
     * 账号ID列表
     */
    private List<String> ids;

    /**
     * 用户名
     */
    private String loginName;

    /**
     * 姓名
     */
    private String realName;

    /**
     * 状态[true-启用]
     */
    private Boolean state;

    /**
     * 集团管理员
     */
    private Boolean tenantAdmin;

    @DataFormat(sourceFieldName = UserQueryVO.Fields.createTimeRang, listIndex = 0, dateType = 2)
    private Date createTimeStart;

    @DataFormat(sourceFieldName = UserQueryVO.Fields.createTimeRang, listIndex = 1, dateType = 2)
    private Date createTimeEnd;
}
