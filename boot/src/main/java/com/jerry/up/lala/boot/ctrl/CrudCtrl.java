package com.jerry.up.lala.boot.ctrl;

import com.jerry.up.lala.boot.access.AccessCrudConstant;
import com.jerry.up.lala.boot.service.CrudService;
import com.jerry.up.lala.boot.vo.CrudExportQueryVO;
import com.jerry.up.lala.boot.vo.CrudInfoVO;
import com.jerry.up.lala.boot.vo.CrudQueryVO;
import com.jerry.up.lala.boot.vo.CrudSaveVO;
import com.jerry.up.lala.framework.core.common.Api;
import com.jerry.up.lala.framework.core.common.DataBody;
import com.jerry.up.lala.framework.core.common.PageR;
import com.jerry.up.lala.framework.core.common.R;
import com.jerry.up.lala.framework.core.excel.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;

/**
 * <p>Description: crud控制层
 *
 * @author FMJ
 * @date 2023/8/9 14:13
 */
@RestController
@RequestMapping("/crud")
public class CrudCtrl {

    @Autowired
    private CrudService crudService;

    @GetMapping("/page")
    @Api(value = "crud样例-查询", accessCodes = {
            AccessCrudConstant.CRUD_ARCO, AccessCrudConstant.CRUD_ARCO_QUERY,
            AccessCrudConstant.CRUD_COMPONENTS, AccessCrudConstant.CRUD_COMPONENTS_QUERY,
            AccessCrudConstant.CRUD_GEN, AccessCrudConstant.CRUD_GEN_QUERY
    })
    public R<PageR<CrudInfoVO>> page(CrudQueryVO crudQueryVO) {
        PageR<CrudInfoVO> pageR = crudService.pageQuery(crudQueryVO);
        return R.succeed(pageR);
    }

    @GetMapping("/info/{id}")
    @Api(value = "crud样例-详情", accessCodes = {
            AccessCrudConstant.CRUD_ARCO_UPDATE,
            AccessCrudConstant.CRUD_COMPONENTS_UPDATE,
            AccessCrudConstant.CRUD_GEN_UPDATE
    })
    public R<CrudInfoVO> info(@PathVariable("id") String id) {
        CrudInfoVO crudInfoVO = crudService.info(id);
        return R.succeed(crudInfoVO);
    }

    @PostMapping
    @Api(value = "crud样例-新增", accessCodes = {
            AccessCrudConstant.CRUD_ARCO_ADD,
            AccessCrudConstant.CRUD_COMPONENTS_ADD,
            AccessCrudConstant.CRUD_GEN_ADD
    })
    public R add(@RequestBody CrudSaveVO crudSaveVO) {
        crudService.add(crudSaveVO);
        return R.empty();
    }

    @PutMapping("/{id}")
    @Api(value = "crud样例-编辑", accessCodes = {
            AccessCrudConstant.CRUD_ARCO_UPDATE,
            AccessCrudConstant.CRUD_COMPONENTS_UPDATE,
            AccessCrudConstant.CRUD_GEN_UPDATE
    })
    public R update(@PathVariable("id") String id, @RequestBody CrudSaveVO crudSaveVO) {
        crudService.update(id, crudSaveVO);
        return R.empty();
    }

    @GetMapping(value = "/template")
    @Api(value = "crud样例-模板下载", accessCodes = {
            AccessCrudConstant.CRUD_ARCO_UPLOAD,
            AccessCrudConstant.CRUD_COMPONENTS_UPLOAD,
            AccessCrudConstant.CRUD_GEN_UPLOAD
    })
    public Object template() {
        return ExcelUtil.template("excel" + File.separator + "crud.xlsx", "crud导入模板.xlsx");
    }

    @PostMapping(value = "/upload")
    @Api(value = "crud样例-上传", accessCodes = {
            AccessCrudConstant.CRUD_ARCO_UPLOAD,
            AccessCrudConstant.CRUD_COMPONENTS_UPLOAD,
            AccessCrudConstant.CRUD_GEN_UPLOAD
    })
    public R upload(@RequestParam(value = "file") MultipartFile file) {
        Integer result = crudService.upload(file);
        return R.succeed(result);
    }

    @GetMapping(value = "/export")
    @Api(value = "crud样例-导出", accessCodes = {
            AccessCrudConstant.CRUD_ARCO_EXPORT,
            AccessCrudConstant.CRUD_COMPONENTS_EXPORT,
            AccessCrudConstant.CRUD_GEN_EXPORT
    })
    public Object export(CrudExportQueryVO crudExportQueryVO) {
        return crudService.export(crudExportQueryVO);
    }

    @DeleteMapping("/{id}")
    @Api(value = "crud样例-删除", accessCodes = {
            AccessCrudConstant.CRUD_ARCO_DELETE,
            AccessCrudConstant.CRUD_COMPONENTS_DELETE,
            AccessCrudConstant.CRUD_GEN_DELETE
    })
    public R delete(@PathVariable("id") String id) {
        crudService.delete(id);
        return R.empty();
    }

    @DeleteMapping
    @Api(value = "crud样例-批量删除", accessCodes = {
            AccessCrudConstant.CRUD_ARCO_BATCH_DELETE,
            AccessCrudConstant.CRUD_COMPONENTS_BATCH_DELETE,
            AccessCrudConstant.CRUD_GEN_BATCH_DELETE
    })
    public R batchDelete(@RequestBody DataBody<List<String>> dataBody) {
        crudService.batchDelete(dataBody);
        return R.empty();
    }

}
