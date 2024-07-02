package com.jerry.up.lala.boot.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.jerry.up.lala.boot.dto.GenTableDTO;
import com.jerry.up.lala.boot.dto.GenTableQueryDTO;
import com.jerry.up.lala.boot.entity.SysGenTable;
import com.jerry.up.lala.framework.core.common.PageR;

/**
* @author jerry
* @description 针对表【sys_gen_table(数据库表)】的数据库操作Service
* @createDate 2024-02-12 22:10:49
*/
public interface SysGenTableService extends IService<SysGenTable> {

    PageR<GenTableDTO> tablePage(Page<GenTableDTO> page, GenTableQueryDTO queryDTO);

    GenTableDTO selectByTableName(String tableName);

}
