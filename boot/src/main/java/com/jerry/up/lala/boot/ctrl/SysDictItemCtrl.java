package com.jerry.up.lala.boot.ctrl;

import com.jerry.up.lala.boot.access.AccessDictConstant;
import com.jerry.up.lala.boot.bo.SysDictItemRedisBO;
import com.jerry.up.lala.boot.bo.SysDictItemVerifyBO;
import com.jerry.up.lala.boot.enums.SysDictKey;
import com.jerry.up.lala.boot.service.SysDictItemService;
import com.jerry.up.lala.boot.vo.*;
import com.jerry.up.lala.framework.core.common.Api;
import com.jerry.up.lala.framework.core.common.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * <p>Description: 系统字典
 *
 * @author FMJ
 * @date 2024/4/17 17:46
 */
@RestController
@RequestMapping("/sys/dict/item")
public class SysDictItemCtrl {

    @Autowired
    private SysDictItemService sysDictItemService;

    @GetMapping("/tree/{dictId}")
    @Api(value = "字典管理-查看字典项", accessCodes = {AccessDictConstant.DICT_ITEM_TREE})
    public R tree(@PathVariable("dictId") Long dictId) {
        List<SysDictItemTreeVO> result = sysDictItemService.tree(dictId);
        return R.succeed(result);
    }

    @GetMapping("/cascader/{dictId}")
    @Api(value = "字典管理-父字典项列表", accessCodes = {AccessDictConstant.DICT_ITEM_ADD, AccessDictConstant.DICT_ITEM_UPDATE})
    public R cascader(@PathVariable("dictId") Long dictId) {
        List<SysDictItemCascaderVO> result = sysDictItemService.cascader(dictId);
        return R.succeed(result);
    }

    @GetMapping("/info/{id}")
    @Api(value = "字典管理-字典项详情", accessCodes = {AccessDictConstant.DICT_ITEM_ADD, AccessDictConstant.DICT_ITEM_UPDATE})
    public R info(@PathVariable("id") Long id) {
        SysDictItemInfoVO infoVO = sysDictItemService.info(id);
        return R.succeed(infoVO);
    }

    @GetMapping("/verify-label/{dictId}")
    @Api(value = "字典管理-字典项校验展示名", accessCodes = {AccessDictConstant.DICT_ITEM_ADD, AccessDictConstant.DICT_ITEM_UPDATE})
    public R verifyLabel(@PathVariable("dictId") Long dictId, SysDictItemVerifyBO verifyBO) {
        sysDictItemService.verifyLabel(dictId, verifyBO);
        return R.empty();
    }

    @GetMapping("/verify-value/{dictId}")
    @Api(value = "字典管理-字典项校验值", accessCodes = {AccessDictConstant.DICT_ITEM_ADD, AccessDictConstant.DICT_ITEM_UPDATE})
    public R verifyValue(@PathVariable("dictId") Long dictId, SysDictItemVerifyBO verifyBO) {
        sysDictItemService.verifyValue(dictId, verifyBO);
        return R.empty();
    }

    @PostMapping("/{dictId}")
    @Api(value = "字典管理-新增字典项", accessCodes = {AccessDictConstant.DICT_ITEM_ADD})
    public R add(@PathVariable("dictId") Long dictId, @RequestBody SysDictItemSaveVO sysDictItemSaveVO) {
        sysDictItemService.add(dictId, sysDictItemSaveVO);
        return R.empty();
    }

    @PutMapping("/{id}")
    @Api(value = "字典管理-编辑字典项", accessCodes = {AccessDictConstant.DICT_ITEM_UPDATE})
    public R update(@PathVariable("id") Long id, @RequestBody SysDictItemSaveVO sysDictItemSaveVO) {
        sysDictItemService.update(id, sysDictItemSaveVO);
        return R.empty();
    }

    @DeleteMapping("/{id}")
    @Api(value = "字典管理-删除字典项", accessCodes = {AccessDictConstant.DICT_ITEM_DELETE})
    public R delete(@PathVariable("id") Long id) {
        sysDictItemService.delete(id);
        return R.empty();
    }

    @GetMapping("/all")
    @Api(value = "公共接口-字典库")
    public R all() {
        Map<String, List<SysDictItemRedisBO>> map = sysDictItemService.all();
        return R.succeed(map);
    }

    @GetMapping("/find")
    @Api(value = "字典管理-字典查询")
    public R find(SysDictItemFindQueryVO queryVO) {
        Map<String, SysDictItemFindVO> result = sysDictItemService.find(queryVO);
        return R.succeed(result);
    }

    @GetMapping("/values")
    @Api(value = "字典管理-值列表")
    public R values(SysDictItemValuesQueryVO queryVO) {
        List<String> result = sysDictItemService.values(queryVO);
        return R.succeed(result);
    }

    @GetMapping("/labels")
    @Api(value = "字典管理-展示名列表")
    public R labels(SysDictItemLabelsQueryVO queryVO) {
        List<String> result = sysDictItemService.labels(queryVO);
        return R.succeed(result);
    }

    @GetMapping("/label-value-map")
    @Api(value = "字典管理-展示名-值")
    public R labelValueMap(SysDictKey dictKey) {
        Map<String, String> result = sysDictItemService.labelValueMap(dictKey);
        return R.succeed(result);
    }

    @GetMapping("/value-label-map")
    @Api(value = "字典管理-值-展示名")
    public R valueLabelMap(SysDictKey dictKey) {
        Map<String, String> result = sysDictItemService.valueLabelMap(dictKey);
        return R.succeed(result);
    }

}
