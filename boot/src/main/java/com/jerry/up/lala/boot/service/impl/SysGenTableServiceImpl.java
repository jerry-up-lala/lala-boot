package com.jerry.up.lala.boot.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jerry.up.lala.boot.dto.GenTableDTO;
import com.jerry.up.lala.boot.dto.GenTableQueryDTO;
import com.jerry.up.lala.boot.dto.InformationSchemaTableDTO;
import com.jerry.up.lala.boot.entity.SysGenTable;
import com.jerry.up.lala.boot.mapper.SysGenTableMapper;
import com.jerry.up.lala.boot.properties.GenProperties;
import com.jerry.up.lala.boot.service.SysGenTableService;
import com.jerry.up.lala.framework.core.common.PageR;
import com.jerry.up.lala.framework.core.data.DataUtil;
import com.jerry.up.lala.framework.core.exception.ServiceException;
import com.jerry.up.lala.framework.core.data.PageUtil;
import com.jerry.up.lala.framework.core.data.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author jerry
 * @description 针对表【sys_gen_table(数据库表)】的数据库操作Service实现
 * @createDate 2024-02-12 22:10:49
 */
@Service
public class SysGenTableServiceImpl extends ServiceImpl<SysGenTableMapper, SysGenTable> implements SysGenTableService {

    @Autowired
    private GenProperties genProperties;

    @Override
    public PageR<GenTableDTO> tablePage(Page<GenTableDTO> page, GenTableQueryDTO queryDTO) {
        LambdaQueryWrapper<SysGenTable> queryWrapper = new LambdaQueryWrapper<>();
        boolean queryTableName = false;
        String packageName = queryDTO.getPackageName();
        if (StringUtil.isNotNull(packageName)) {
            queryTableName = true;
            queryWrapper.like(SysGenTable::getPackageName, packageName);
        }
        String className = queryDTO.getClassName();
        if (StringUtil.isNotNull(className)) {
            queryTableName = true;
            queryWrapper.like(SysGenTable::getClassName, className);
        }
        String functionName = queryDTO.getFunctionName();
        if (StringUtil.isNotNull(functionName)) {
            queryTableName = true;
            queryWrapper.like(SysGenTable::getFunctionName, functionName);
        }
        String author = queryDTO.getAuthor();
        if (StringUtil.isNotNull(author)) {
            queryTableName = true;
            queryWrapper.like(SysGenTable::getAuthor, author);
        }
        if (queryTableName) {
            List<SysGenTable> queryTableList = list(queryWrapper);
            if (CollectionUtil.isEmpty(queryTableList)) {
                return PageUtil.emptyPage();
            }
            List<String> queryTableNameList = queryTableList.stream().map(SysGenTable::getTableName).collect(Collectors.toList());
            queryDTO.setQueryTableNameList(queryTableNameList);
            queryDTO.setQueryTableName(true);
        }
        IPage<InformationSchemaTableDTO> pageResult = getBaseMapper().page(page, queryDTO);
        List<InformationSchemaTableDTO> records = pageResult.getRecords();
        if (CollectionUtil.isEmpty(records)) {
            return PageUtil.emptyPage();
        }
        List<String> tableNameList = records.stream().map(InformationSchemaTableDTO::getTableName).collect(Collectors.toList());
        Map<String, SysGenTable> tableMap = sysGenTableMap(tableNameList);
        List<GenTableDTO> list = records.stream().map(item -> loadGenTableDTO(item, tableMap, false)).collect(Collectors.toList());
        return new PageR<>(pageResult.getTotal(), list);
    }

    @Override
    @Transactional
    public GenTableDTO selectByTableName(String tableName) {
        InformationSchemaTableDTO informationSchemaTableDTO = getBaseMapper().selectByTableName(tableName);
        if (informationSchemaTableDTO == null) {
            throw ServiceException.notFound();
        }
        Map<String, SysGenTable> tableMap = sysGenTableMap(ListUtil.of(tableName));
        GenTableDTO result = loadGenTableDTO(informationSchemaTableDTO, tableMap, true);
        // 如果为设置表信息 则新增
        if (result.getId() == null) {
            save(result);
        }
        return result;
    }

    private Map<String, SysGenTable> sysGenTableMap(List<String> tableNameList) {
        List<SysGenTable> sysGenTableList = list(new LambdaQueryWrapper<SysGenTable>().in(SysGenTable::getTableName, tableNameList));
        return CollectionUtil.isNotEmpty(sysGenTableList) ? sysGenTableList.stream()
                .collect(Collectors.toMap(SysGenTable::getTableName, Function.identity(), (key1, key2) -> key1)) : new HashMap<>(16);
    }

    private GenTableDTO loadGenTableDTO(InformationSchemaTableDTO informationSchemaTableDTO, Map<String, SysGenTable> tableMap, boolean loadDefault) {
        if (informationSchemaTableDTO == null) {
            return null;
        }
        GenTableDTO genTableDTO = DataUtil.toBean(informationSchemaTableDTO, GenTableDTO.class);

        String tableName = informationSchemaTableDTO.getTableName();
        SysGenTable sysGenTable = tableMap.get(tableName);
        if (sysGenTable != null) {
            DataUtil.copy(sysGenTable, genTableDTO);
        } else {
            if (loadDefault) {
                genTableDTO.setPackageName(genProperties.getPackageName());
                genTableDTO.setClassName(StrUtil.upperFirst(StrUtil.toCamelCase(genTableDTO.getTableName())));
                genTableDTO.setFunctionName(genTableDTO.getTableComment());
            }
        }
        return genTableDTO;
    }
}




