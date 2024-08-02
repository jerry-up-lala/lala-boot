package com.jerry.up.lala.boot.service;

import com.jerry.up.lala.boot.vo.*;
import com.jerry.up.lala.framework.common.model.DataBody;
import com.jerry.up.lala.framework.common.r.PageR;

import java.util.List;

/**
 * <p>Description: 代码生成
 *
 * @author FMJ
 * @date 2024/2/2 13:49
 */
public interface GenService {

    PageR<GenTableVO> tablePage(GenTableQueryVO genTableQueryVO);

    GenTableVO tableInfo(String tableName);

    Long tableSave(GenTableSaveVO genTableSaveVO);

    List<GenColumnVO> columnList(Long tableId);

    void columnSave(Long tableId, DataBody<List<GenColumnSaveVO>> dataBody);

    GenPreviewVO preview(Long tableId);

    Object download(Long tableId);

}
