package com.jerry.up.lala.boot.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.lang.Dict;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.template.Template;
import cn.hutool.extra.template.TemplateConfig;
import cn.hutool.extra.template.TemplateEngine;
import cn.hutool.extra.template.TemplateUtil;
import cn.hutool.extra.template.engine.velocity.VelocityEngine;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jerry.up.lala.boot.bo.GenPreviewBO;
import com.jerry.up.lala.boot.bo.GenTemplateBO;
import com.jerry.up.lala.boot.bo.GenTemplateColumnBO;
import com.jerry.up.lala.boot.dto.GenColumnDTO;
import com.jerry.up.lala.boot.dto.GenTableDTO;
import com.jerry.up.lala.boot.dto.GenTableQueryDTO;
import com.jerry.up.lala.boot.entity.SysGenColumn;
import com.jerry.up.lala.boot.entity.SysGenTable;
import com.jerry.up.lala.boot.enums.GenColumnType;
import com.jerry.up.lala.boot.error.BootErrors;
import com.jerry.up.lala.boot.properties.GenProperties;
import com.jerry.up.lala.boot.service.GenService;
import com.jerry.up.lala.boot.service.SysGenColumnService;
import com.jerry.up.lala.boot.service.SysGenTableService;
import com.jerry.up.lala.boot.vo.*;
import com.jerry.up.lala.framework.core.common.DataBody;
import com.jerry.up.lala.framework.core.common.Errors;
import com.jerry.up.lala.framework.core.common.PageR;
import com.jerry.up.lala.framework.core.data.DataUtil;
import com.jerry.up.lala.framework.core.exception.ServiceException;
import com.jerry.up.lala.framework.core.data.CheckUtil;
import com.jerry.up.lala.framework.core.data.PageUtil;
import com.jerry.up.lala.framework.core.response.ResponseUtil;
import com.jerry.up.lala.framework.core.data.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * <p>Description: 代码生成实现
 *
 * @author FMJ
 * @date 2024/2/2 13:49
 */
@Service
@Slf4j
public class GenServiceImpl implements GenService {

    @Autowired
    private SysGenTableService sysGenTableService;

    @Autowired
    private SysGenColumnService sysGenColumnService;

    @Autowired
    private GenProperties genProperties;

    @Override
    public PageR<GenTableVO> tablePage(GenTableQueryVO genTableQueryVO) {
        Page<GenTableDTO> page = PageUtil.initPage(genTableQueryVO);
        try {
            PageR<GenTableDTO> pageResult = sysGenTableService.tablePage(page, DataUtil.toBean(genTableQueryVO, GenTableQueryDTO.class));
            return PageUtil.toPageR(pageResult, GenTableVO.class);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    public GenTableVO tableInfo(String tableName) {
        if (StringUtil.isNull(tableName)) {
            throw ServiceException.args();
        }
        GenTableDTO genTableDTO = sysGenTableService.selectByTableName(tableName);
        try {
            return DataUtil.toBean(genTableDTO, GenTableVO.class);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    @Transactional
    public Long tableSave(GenTableSaveVO genTableSaveVO) {
        String tableSchema = genTableSaveVO.getTableSchema();
        String tableName = genTableSaveVO.getTableName();
        String packageName = genTableSaveVO.getPackageName();
        String className = genTableSaveVO.getClassName();
        String functionName = genTableSaveVO.getFunctionName();
        String author = genTableSaveVO.getAuthor();
        if (StringUtil.isNull(tableSchema) || StringUtil.isNull(tableName) || StringUtil.isNull(packageName) || StringUtil.isNull(className) ||
                StringUtil.isNull(functionName) || StringUtil.isNull(author)) {
            throw ServiceException.args();
        }
        SysGenTable sysGenTable = sysGenTableService.selectByTableName(tableName);
        sysGenTable.setPackageName(packageName).setClassName(className).setFunctionName(functionName).setAuthor(author);
        sysGenTableService.updateById(sysGenTable);
        return sysGenTable.getId();
    }

    @Override
    public List<GenColumnVO> columnList(Long tableId) {
        SysGenTable sysGenTable = sysGenTable(tableId);
        try {
            List<GenColumnDTO> columnList = sysGenColumnService.list(sysGenTable);
            return DataUtil.toBeanList(columnList, GenColumnVO.class);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    public void columnSave(Long tableId, DataBody<List<GenColumnSaveVO>> dataBody) {
        List<GenColumnSaveVO> genColumnSaveList = CheckUtil.dataNull(dataBody);
        // 校验必填参数
        boolean checkArg = genColumnSaveList.stream().anyMatch(genColumnSaveVO -> {
            String columnName = genColumnSaveVO.getColumnName();
            String fieldName = genColumnSaveVO.getFieldName();
            String fieldComment = genColumnSaveVO.getFieldComment();
            String fieldType = genColumnSaveVO.getFieldType();
            return StringUtil.isNull(columnName) || StringUtil.isNull(fieldName) || StringUtil.isNull(fieldComment) ||
                    Arrays.stream(GenColumnType.values()).noneMatch(item -> item.getFieldType().equals(fieldType));
        });
        if (checkArg) {
            throw ServiceException.args();
        }
        SysGenTable sysGenTable = sysGenTable(tableId);
        List<GenColumnDTO> columnList = sysGenColumnService.list(sysGenTable);
        if (CollectionUtil.isEmpty(columnList)) {
            throw ServiceException.args();
        }
        // 新增list
        List<SysGenColumn> addList = new ArrayList<>();
        // 更新list
        List<SysGenColumn> updateList = new ArrayList<>();

        Map<String, SysGenColumn> columnMap = columnList.stream().collect(Collectors.toMap(SysGenColumn::getColumnName, Function.identity(), (key1, key2) -> key1));

        int fieldOrdinalPosition = 0;
        for (GenColumnSaveVO genColumnSaveVO : genColumnSaveList) {
            fieldOrdinalPosition++;
            String columnName = genColumnSaveVO.getColumnName();
            String fieldName = genColumnSaveVO.getFieldName();
            String fieldType = genColumnSaveVO.getFieldType();
            String fieldComment = genColumnSaveVO.getFieldComment();
            String fieldDictKey = genColumnSaveVO.getFieldDictKey();

            SysGenColumn sysGenColumn = columnMap.get(columnName);
            if (sysGenColumn != null && sysGenColumn.getId() != null) {
                updateList.add(sysGenColumn
                        .setFieldName(fieldName).setFieldType(fieldType).setFieldComment(fieldComment).setFieldDictKey(fieldDictKey)
                        .setFieldOrdinalPosition(fieldOrdinalPosition));
            } else {
                addList.add(new SysGenColumn().setTableId(tableId).setColumnName(columnName)
                        .setFieldName(fieldName).setFieldType(fieldType).setFieldComment(fieldComment).setFieldDictKey(fieldDictKey)
                        .setFieldOrdinalPosition(fieldOrdinalPosition));
            }
        }

        if (CollectionUtil.isNotEmpty(addList)) {
            sysGenColumnService.insertBatch(addList);

        }
        if (CollectionUtil.isNotEmpty(updateList)) {
            sysGenColumnService.updateBatch(updateList);
        }

    }

    @Override
    public GenPreviewVO preview(Long tableId) {
        List<GenPreviewBO> previewList = previewList(tableId);
        try {
            Set<String> pathList = previewList.stream().map(preview -> preview.getPath() + File.separator + preview.getName()).collect(Collectors.toSet());
            List<Tree<String>> treeList = DataUtil.toTreeBySeparator(pathList, File.separator);
            List<GenPreviewTreeVO> tree = treeList.stream().map(this::genPreviewTreeVO).collect(Collectors.toList());
            List<GenPreviewTabVO> tab = previewList.stream().map(preview -> new GenPreviewTabVO().setKey(preview.getPath() + File.separator + preview.getName())
                    .setTitle(preview.getName()).setCode(preview.getCode())).collect(Collectors.toList());
            return new GenPreviewVO().setTree(tree).setTab(tab);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    private GenPreviewTreeVO genPreviewTreeVO(Tree<String> tree) {
        GenPreviewTreeVO result = new GenPreviewTreeVO().setKey(tree.getId()).setTitle(tree.getName().toString());
        if (tree.hasChild()) {
            result.setChildren(tree.getChildren().stream().map(this::genPreviewTreeVO).collect(Collectors.toList()));
        }
        return result;
    }

    @Override
    public Object download(Long tableId) {
        List<GenPreviewBO> previewList = previewList(tableId);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ZipOutputStream zip = new ZipOutputStream(outputStream);
        previewList.forEach(preview -> {
            try {
                zip.putNextEntry(new ZipEntry(preview.getPath() + File.separator + preview.getName()));
                IoUtil.writeUtf8(zip, false, preview.getCode());
                zip.flush();
                zip.closeEntry();
            } catch (IOException e) {
                log.error("生成压缩包异常", e);
            }
        });
        IoUtil.close(zip);
        byte[] data = outputStream.toByteArray();
        return ResponseUtil.download(new ByteArrayInputStream(data),
                genProperties.getProjectName() + "-" + DatePattern.PURE_DATETIME_FORMAT.format(new Date()) + ".zip");
    }

    private SysGenTable sysGenTable(Long tableId) {
        if (tableId == null) {
            throw ServiceException.args();
        }
        SysGenTable sysGenTable = sysGenTableService.getById(tableId);
        if (sysGenTable == null) {
            throw ServiceException.notFound();
        }
        return sysGenTable;
    }

    private List<GenPreviewBO> previewList(Long tableId) {
        SysGenTable sysGenTable = sysGenTable(tableId);
        List<GenColumnDTO> genColumnList = sysGenColumnService.list(sysGenTable);
        if (CollectionUtil.isEmpty(genColumnList)) {
            throw ServiceException.notFound();
        }
        String packageName = sysGenTable.getPackageName();
        String className = sysGenTable.getClassName();
        GenTemplateBO templateBO = new GenTemplateBO()
                .setPackageName(packageName)
                .setClassName(className)
                .setUpperCaseClassName(className.toUpperCase())
                .setLowerFirstClassName(StrUtil.lowerFirst(className))
                .setFunctionName(sysGenTable.getFunctionName())
                .setAuthor(sysGenTable.getAuthor())
                .setDate(DateUtil.now())
                .setTableName(sysGenTable.getTableName())
                .setColumnList(columnList(genColumnList));
        return previewList(templateBO);
    }

    private List<GenPreviewBO> previewList(GenTemplateBO templateBO) {
        try {
            List<GenPreviewBO> result = new ArrayList<>();
            // 控制层
            result.add(ctrl(templateBO));
            // 权限常量
            result.add(access(templateBO));
            // 实体
            result.add(entity(templateBO));
            // mapper
            result.add(mapper(templateBO));
            // xml
            result.add(xml(templateBO));
            // 查询vo
            result.add(queryVO(templateBO));
            // 查询导出vo
            result.add(exportQueryVO(templateBO));
            // 查询dto
            result.add(queryDTO(templateBO));
            // 信息VO
            result.add(infoVO(templateBO));
            // 保存VO
            result.add(saveVO(templateBO));
            // ExcelBO
            result.add(excelBO(templateBO));
            // service
            result.add(service(templateBO));
            // service 实现
            result.add(serviceImpl(templateBO));
            return result;
        } catch (Exception e) {
            throw ServiceException.error(BootErrors.GEN_PREVIEW_ERROR, e);
        }
    }

    private GenPreviewBO ctrl(GenTemplateBO templateBO) {
        String path = javaPath(templateBO) + "ctrl";
        return loadPathAndCode(path, "ctrl.vm", Dict.parse(templateBO)).setName(templateBO.getClassName() + "Ctrl.java");
    }

    private GenPreviewBO access(GenTemplateBO templateBO) {
        String path = javaPath(templateBO) + "access";
        return loadPathAndCode(path, "access.vm", Dict.parse(templateBO)).setName("AccessConstant.java");
    }

    private GenPreviewBO entity(GenTemplateBO templateBO) {
        String path = javaPath(templateBO) + "entity";
        return loadPathAndCode(path, "entity.vm", Dict.parse(templateBO)).setName(templateBO.getClassName() + ".java");
    }

    private GenPreviewBO mapper(GenTemplateBO templateBO) {
        String path = javaPath(templateBO) + "mapper";
        return loadPathAndCode(path, "mapper.vm", Dict.parse(templateBO)).setName(templateBO.getClassName() + "Mapper.java");
    }

    private GenPreviewBO xml(GenTemplateBO templateBO) {
        String path = xmlPath() + "xml";
        return loadPathAndCode(path, "xml.vm", Dict.parse(templateBO)).setName(templateBO.getClassName() + "Mapper.xml");
    }

    private GenPreviewBO queryVO(GenTemplateBO templateBO) {
        String path = javaPath(templateBO) + "vo";
        return loadPathAndCode(path, "queryVO.vm", Dict.parse(templateBO)).setName(templateBO.getClassName() + "QueryVO.java");
    }

    private GenPreviewBO exportQueryVO(GenTemplateBO templateBO) {
        String path = javaPath(templateBO) + "vo";
        return loadPathAndCode(path, "exportQueryVO.vm", Dict.parse(templateBO)).setName(templateBO.getClassName() + "ExportQueryVO.java");
    }

    private GenPreviewBO queryDTO(GenTemplateBO templateBO) {
        String path = javaPath(templateBO) + "dto";
        return loadPathAndCode(path, "queryDTO.vm", Dict.parse(templateBO)).setName(templateBO.getClassName() + "QueryDTO.java");
    }

    private GenPreviewBO infoVO(GenTemplateBO templateBO) {
        String path = javaPath(templateBO) + "vo";
        return loadPathAndCode(path, "infoVO.vm", Dict.parse(templateBO)).setName(templateBO.getClassName() + "InfoVO.java");
    }

    private GenPreviewBO saveVO(GenTemplateBO templateBO) {
        String path = javaPath(templateBO) + "vo";
        return loadPathAndCode(path, "saveVO.vm", Dict.parse(templateBO)).setName(templateBO.getClassName() + "SaveVO.java");
    }

    private GenPreviewBO excelBO(GenTemplateBO templateBO) {
        String path = javaPath(templateBO) + "bo";
        return loadPathAndCode(path, "excelBO.vm", Dict.parse(templateBO)).setName(templateBO.getClassName() + "ExcelBO.java");
    }

    private GenPreviewBO service(GenTemplateBO templateBO) {
        String path = javaPath(templateBO) + "service";
        return loadPathAndCode(path, "service.vm", Dict.parse(templateBO)).setName(templateBO.getClassName() + "Service.java");
    }

    private GenPreviewBO serviceImpl(GenTemplateBO templateBO) {
        String path = javaPath(templateBO) + "service" + File.separator + "impl";
        return loadPathAndCode(path, "serviceImpl.vm", Dict.parse(templateBO)).setName(templateBO.getClassName() + "ServiceImpl.java");
    }

    private String javaPath(GenTemplateBO templateBO) {
        return genProperties.getProjectName() + File.separator + "src" + File.separator + "main" + File.separator
                + "java" + File.separator + templateBO.getPackageName() + File.separator;
    }

    private String xmlPath() {
        return genProperties.getProjectName() + File.separator + "src" + File.separator + "main" + File.separator
                + "resources" + File.separator + "mapper" + File.separator;
    }

    private List<GenTemplateColumnBO> columnList(List<GenColumnDTO> genColumnList) {
        return genColumnList.stream().map(item -> {
            GenTemplateColumnBO columnBO = DataUtil.toBean(item, GenTemplateColumnBO.class);
            columnBO.setFieldChange(!StrUtil.toCamelCase(item.getColumnName()).equals(item.getFieldName()));
            columnBO.setUpperFirstFieldName(StrUtil.upperFirst(item.getFieldName()));
            return columnBO;
        }).collect(Collectors.toList());
    }

    private GenPreviewBO loadPathAndCode(String path, String vmName, Dict dict) {
        TemplateConfig templateConfig = new TemplateConfig("gen", TemplateConfig.ResourceMode.CLASSPATH).setCustomEngine(VelocityEngine.class);
        TemplateEngine engine = TemplateUtil.createEngine(templateConfig);
        Template template = engine.getTemplate(vmName);
        String code = template.render(dict);
        return new GenPreviewBO().setPath(StrUtil.replace(path, ".", File.separator)).setCode(code);
    }

}
