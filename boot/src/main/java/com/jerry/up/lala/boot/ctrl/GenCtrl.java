package com.jerry.up.lala.boot.ctrl;

import com.jerry.up.lala.boot.access.AccessGenConstant;
import com.jerry.up.lala.boot.service.GenService;
import com.jerry.up.lala.boot.vo.*;
import com.jerry.up.lala.framework.core.common.Api;
import com.jerry.up.lala.framework.core.common.DataBody;
import com.jerry.up.lala.framework.core.common.PageR;
import com.jerry.up.lala.framework.core.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Description: 开发工具#代码生成
 *
 * @author FMJ
 * @date 2024/2/1 17:57
 */
@RestController
@RequestMapping("/gen")
public class GenCtrl {

    @Autowired
    private GenService genService;

    @GetMapping("/table/page")
    @Api(value = "代码生成-查询", accessCodes = {
            AccessGenConstant.GEN, AccessGenConstant.GEN_QUERY
    })
    public R<PageR<GenTableVO>> tablePage(GenTableQueryVO genTableQueryVO) {
        PageR<GenTableVO> pageR = genService.tablePage(genTableQueryVO);
        return R.succeed(pageR);
    }

    @GetMapping("/table/info/{tableName}")
    @Api(value = "代码生成-详情", accessCodes = AccessGenConstant.GEN_OPERATE)
    public R<GenTableVO> tableInfo(@PathVariable("tableName") String tableName) {
        GenTableVO genTableVO = genService.tableInfo(tableName);
        return R.succeed(genTableVO);
    }

    @PostMapping("/table")
    @Api(value = "代码生成-表信息保存", accessCodes = AccessGenConstant.GEN_OPERATE)
    public R tableSave(@RequestBody GenTableSaveVO genTableSaveVO) {
        Long tableId = genService.tableSave(genTableSaveVO);
        return R.succeed(tableId);
    }

    @GetMapping("/column/{tableId}")
    @Api(value = "代码生成-列信息", accessCodes = AccessGenConstant.GEN_OPERATE)
    public R<List<GenColumnVO>> columnList(@PathVariable("tableId") Long tableId) {
        List<GenColumnVO> list = genService.columnList(tableId);
        return R.succeed(list);
    }

    @PostMapping("/column/{tableId}")
    @Api(value = "代码生成-列信息保存", accessCodes = AccessGenConstant.GEN_OPERATE)
    public R<Long> columnSave(@PathVariable("tableId") Long tableId, @RequestBody DataBody<List<GenColumnSaveVO>> dataBody) {
        genService.columnSave(tableId, dataBody);
        return R.empty();
    }

    @GetMapping("/preview/{tableId}")
    @Api(value = "代码生成-预览", accessCodes = AccessGenConstant.GEN_OPERATE)
    public R<GenPreviewVO> preview(@PathVariable("tableId") Long tableId) {
        GenPreviewVO genPreviewVO = genService.preview(tableId);
        return R.succeed(genPreviewVO);
    }

    @GetMapping(value = "/download/{tableId}")
    @Api(value = "代码生成-下载", accessCodes = AccessGenConstant.GEN_OPERATE)
    public Object download(@PathVariable("tableId") Long tableId) {
        return genService.download(tableId);
    }

}
