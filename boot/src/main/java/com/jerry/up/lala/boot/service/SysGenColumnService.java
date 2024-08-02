package com.jerry.up.lala.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jerry.up.lala.boot.dto.GenColumnDTO;
import com.jerry.up.lala.boot.entity.SysGenColumn;
import com.jerry.up.lala.boot.entity.SysGenTable;

import java.util.List;

/**
* @author jerry
* @description 针对表【sys_gen_column(数据库字段)】的数据库操作Service
* @createDate 2024-02-10 21:24:29
*/
public interface SysGenColumnService extends IService<SysGenColumn> {

    List<GenColumnDTO> list(SysGenTable sysGenTable);

    void insertBatch(List<SysGenColumn> addList);

    void updateBatch(List<SysGenColumn> updateList);
}
