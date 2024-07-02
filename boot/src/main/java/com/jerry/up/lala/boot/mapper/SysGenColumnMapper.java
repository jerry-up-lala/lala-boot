package com.jerry.up.lala.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jerry.up.lala.boot.dto.InformationSchemaColumnDTO;
import com.jerry.up.lala.boot.entity.SysGenColumn;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author jerry
* @description 针对表【sys_gen_column(数据库字段)】的数据库操作Mapper
* @createDate 2024-02-10 21:24:29
* @Entity com.jerry.up.lala.boot.entity.SysGenTableField
*/
public interface SysGenColumnMapper extends BaseMapper<SysGenColumn> {

    List<InformationSchemaColumnDTO> list(@Param("tableSchema") String tableSchema, @Param("tableName") String tableName);

    Integer insertBatchSomeColumn(List<SysGenColumn> sysGenColumnList);

    Integer updateBatch(@Param("list") List<SysGenColumn> sysGenColumnList);

}




