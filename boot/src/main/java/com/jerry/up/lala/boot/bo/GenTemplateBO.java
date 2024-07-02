package com.jerry.up.lala.boot.bo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>Description: 模板BO
 *
 * @author FMJ
 * @date 2024/2/14 21:11
 */
@Data
@Accessors(chain = true)
public class GenTemplateBO {

    private String packageName;

    private String className;

    private String upperCaseClassName;

    private String lowerFirstClassName;

    private String functionName;

    private String author;

    private String date;

    private String tableName;

    private List<GenTemplateColumnBO> columnList;

}
