package com.jerry.up.lala.boot.service;

import com.github.yulichang.base.MPJBaseService;
import com.jerry.up.lala.boot.bo.SysDictItemRedisBO;
import com.jerry.up.lala.boot.bo.SysDictItemVerifyBO;
import com.jerry.up.lala.boot.entity.SysDict;
import com.jerry.up.lala.boot.entity.SysDictItem;
import com.jerry.up.lala.boot.enums.SysDictKey;
import com.jerry.up.lala.boot.vo.*;

import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
* @author jerry
* @description 针对表【sys_dict_item(字典项)】的数据库操作Service
* @createDate 2024-04-17 17:40:34
*/
public interface SysDictItemService extends MPJBaseService<SysDictItem> {

    List<SysDictItemTreeVO> tree(Long dictId);

    List<SysDictItemCascaderVO> cascader(Long dictId);

    void refreshCache();

    SysDictItemInfoVO info(Long id);

    void verifyLabel(Long dictId, SysDictItemVerifyBO verifyBO);

    void verifyValue(Long dictId, SysDictItemVerifyBO verifyBO);

    void add(Long dictId, SysDictItemSaveVO sysDictItemSaveVO);

    void update(Long id, SysDictItemSaveVO sysDictItemSaveVO);

    void delete(Long id);

    Map<String, List<SysDictItemRedisBO>> all();

    List<String> values(SysDictItemValuesQueryVO queryVO);

    List<String> dictValuesList(SysDictKey sysDictKey, List<String> valuesList);

    List<String> labels(SysDictItemLabelsQueryVO queryVO);

    List<String> dictLabelsList(SysDictKey sysDictKey);

    List<String> dictLabelsList(SysDictKey sysDictKey, Boolean root, Boolean lowest);

    Map<String, String> labelValueMap(SysDictKey dictKey);

    Map<String, String> valueLabelMap(SysDictKey dictKey);

    Map<String, String> dictItemMap(SysDictKey sysDictKey, Function<SysDictItemRedisBO, String> key, Function<SysDictItemRedisBO, String> value);

    Map<String, SysDictItemFindVO> find(SysDictItemFindQueryVO queryVO);

}
