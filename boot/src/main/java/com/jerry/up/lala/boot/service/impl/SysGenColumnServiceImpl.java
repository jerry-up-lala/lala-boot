package com.jerry.up.lala.boot.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jerry.up.lala.boot.dto.GenColumnDTO;
import com.jerry.up.lala.boot.dto.InformationSchemaColumnDTO;
import com.jerry.up.lala.boot.entity.SysGenColumn;
import com.jerry.up.lala.boot.entity.SysGenTable;
import com.jerry.up.lala.boot.enums.GenColumnType;
import com.jerry.up.lala.boot.mapper.SysGenColumnMapper;
import com.jerry.up.lala.boot.service.SysDictItemService;
import com.jerry.up.lala.boot.service.SysGenColumnService;
import com.jerry.up.lala.framework.core.common.Entity;
import com.jerry.up.lala.framework.core.data.DataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author jerry
 * @description 针对表【sys_gen_column(数据库字段)】的数据库操作Service实现
 * @createDate 2024-02-10 21:24:29
 */
@Service
public class SysGenColumnServiceImpl extends ServiceImpl<SysGenColumnMapper, SysGenColumn> implements SysGenColumnService {

    @Autowired
    private SysDictItemService sysDictItemService;

    @Override
    public List<GenColumnDTO> list(SysGenTable sysGenTable) {
        List<InformationSchemaColumnDTO> columnList = getBaseMapper().list(sysGenTable.getTableSchema(), sysGenTable.getTableName());
        Map<String, SysGenColumn> sysGenColumnMap = sysGenColumnMap(sysGenTable.getId());
        return columnList.stream().map(item -> loadGenColumnDTO(item, sysGenColumnMap))
                .filter(column -> !ReflectUtil.hasField(Entity.class, column.getFieldName()))
                .sorted(Comparator.comparingInt(GenColumnDTO::getFieldOrdinalPosition)).collect(Collectors.toList());
    }

    private Map<String, SysGenColumn> sysGenColumnMap(Long tableId) {
        List<SysGenColumn> sysGenColumnList = list(new LambdaQueryWrapper<SysGenColumn>().eq(SysGenColumn::getTableId, tableId));
        return CollectionUtil.isNotEmpty(sysGenColumnList) ? sysGenColumnList.stream()
                .collect(Collectors.toMap(SysGenColumn::getColumnName, Function.identity(), (key1, key2) -> key1)) : new HashMap<>(16);
    }

    @Override
    public void insertBatch(List<SysGenColumn> addList) {
        getBaseMapper().insertBatchSomeColumn(addList);
    }

    @Override
    public void updateBatch(List<SysGenColumn> updateList) {
        getBaseMapper().updateBatch(updateList);
    }

    private GenColumnDTO loadGenColumnDTO(InformationSchemaColumnDTO informationSchemaColumnDTO, Map<String, SysGenColumn> columnMap) {
        Set<String> dictKeySet = sysDictItemService.all().keySet();

        GenColumnDTO genColumnDTO = DataUtil.toBean(informationSchemaColumnDTO, GenColumnDTO.class);
        GenColumnType genColumnType = GenColumnType.type(genColumnDTO.getColumnDataType());
        genColumnDTO.setColumnJdbcType(genColumnType.getJdbcType());
        String columnName = informationSchemaColumnDTO.getColumnName();
        SysGenColumn sysGenColumn = columnMap.get(columnName);
        if (sysGenColumn != null) {
            DataUtil.copy(sysGenColumn, genColumnDTO);
        } else {
            genColumnDTO.setFieldName(StrUtil.toCamelCase(genColumnDTO.getColumnName()));
            genColumnDTO.setFieldType(genColumnType.getFieldType());
            genColumnDTO.setFieldOrdinalPosition(genColumnDTO.getColumnOrdinalPosition());
            // 如果字段备注 包含 字典key 则 设置为默认字典key
            dictKeySet.stream().filter(dictKey -> {
                String columnComment = genColumnDTO.getColumnComment();
                if (StrUtil.isBlank(columnComment)) {
                    return false;
                }
                return columnComment.contains(dictKey);
            }).findFirst().ifPresent(genColumnDTO::setFieldDictKey);
            String columnComment = genColumnDTO.getColumnComment();
            if (StrUtil.isNotBlank(columnComment)) {
                String fieldComment = StrUtil.removeAny(columnComment, "(" + genColumnDTO.getFieldDictKey() +")", ",单选", ",多选");
                genColumnDTO.setFieldComment(StrUtil.cleanBlank(fieldComment));
            }
        }
        return genColumnDTO;
    }
}




