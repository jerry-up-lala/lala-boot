package com.jerry.up.lala.boot.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.ListUtil;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.lang.tree.TreeNode;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.BooleanUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.yulichang.base.MPJBaseServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.jerry.up.lala.boot.bo.SysDictItemRedisBO;
import com.jerry.up.lala.boot.bo.SysDictItemVerifyBO;
import com.jerry.up.lala.boot.constant.RedisConstant;
import com.jerry.up.lala.boot.dto.SysDictItemDTO;
import com.jerry.up.lala.boot.entity.SysDict;
import com.jerry.up.lala.boot.entity.SysDictItem;
import com.jerry.up.lala.boot.enums.SysDictKey;
import com.jerry.up.lala.boot.error.BootErrors;
import com.jerry.up.lala.boot.mapper.SysDictItemMapper;
import com.jerry.up.lala.boot.service.SysDictItemService;
import com.jerry.up.lala.boot.vo.*;
import com.jerry.up.lala.framework.core.common.Errors;
import com.jerry.up.lala.framework.core.data.DataUtil;
import com.jerry.up.lala.framework.core.exception.ServiceException;
import com.jerry.up.lala.framework.core.redis.RedisHashTemplate;
import com.jerry.up.lala.framework.core.data.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author jerry
 * @description 针对表【sys_dict_item(字典项)】的数据库操作Service实现
 * @createDate 2024-04-17 17:40:34
 */
@Service
public class SysDictItemServiceImpl extends MPJBaseServiceImpl<SysDictItemMapper, SysDictItem> implements SysDictItemService {

    private final Long rootId = 0L;

    @Autowired
    private RedisHashTemplate<String, List<SysDictItemRedisBO>> redisHashTemplate;

    @Override
    public List<SysDictItemTreeVO> tree(Long dictId) {
        try {
            return getListByDictId(dictId, this::convertTreeVO);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    public List<SysDictItemCascaderVO> cascader(Long dictId) {
        try {
            return getListByDictId(dictId, this::convertCascaderVO);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    public void refreshCache() {
        try {
            List<SysDictItemDTO> itemList = itemDTOList(null);
            LinkedHashMap<String, List<SysDictItemDTO>> itemMap = itemList.stream().sorted(Comparator.comparingInt(SysDictItemDTO::getSortOrder))
                    .collect(Collectors.groupingBy(SysDictItemDTO::getDictKey, LinkedHashMap::new, Collectors.toList()));
            itemMap.forEach((dictKey, itemDTOList) -> {
                List<SysDictItemRedisBO> redisList = convertList(itemDTOList, this::convertRedisBO);
                redisHashTemplate.put(RedisConstant.REDIS_KEY_SYS_DICT, dictKey, redisList);
            });
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    public SysDictItemInfoVO info(Long id) {
        SysDictItemDTO sysDictItemDTO = get(id);
        return DataUtil.toBean(sysDictItemDTO, SysDictItemInfoVO.class);
    }

    @Override
    public void verifyLabel(Long dictId, SysDictItemVerifyBO verifyBO) {
        // 展示名
        String label = verifyBO.getValue();
        if (StringUtil.isNull(label)) {
            throw ServiceException.args();
        }

        // 父ID
        Long parentId = verifyBO.getParentId() == null ? rootId : verifyBO.getParentId();
        LambdaQueryWrapper<SysDictItem> query = new LambdaQueryWrapper<SysDictItem>()
                .eq(SysDictItem::getDictId, dictId).eq(SysDictItem::getParentId, parentId)
                .eq(SysDictItem::getLabel, label);

        Long id = verifyBO.getId();
        if (id != null) {
            query.ne(SysDictItem::getId, id);
        }
        if (exists(query)) {
            throw ServiceException.error(BootErrors.DICT_ITEM_LABEL_EXISTS);
        }
    }

    @Override
    public void verifyValue(Long dictId, SysDictItemVerifyBO verifyBO) {
        // 值
        String value = verifyBO.getValue();
        // 父ID
        Long parentId = verifyBO.getParentId() == null ? rootId : verifyBO.getParentId();
        LambdaQueryWrapper<SysDictItem> query = new LambdaQueryWrapper<SysDictItem>()
                .eq(SysDictItem::getDictId, dictId).eq(SysDictItem::getParentId, parentId);
        if (value == null) {
            query.isNull(SysDictItem::getValue);
        } else {
            query.eq(SysDictItem::getValue, value);
        }
        Long id = verifyBO.getId();
        if (id != null) {
            query.ne(SysDictItem::getId, id);
        }
        if (exists(query)) {
            throw ServiceException.error(BootErrors.DICT_ITEM_VALUE_EXISTS);
        }
    }

    @Override
    public void add(Long dictId, SysDictItemSaveVO sysDictItemSaveVO) {
        checkSaveVO(null, dictId, sysDictItemSaveVO);
        try {
            SysDictItem sysDictItem = DataUtil.toBean(sysDictItemSaveVO, SysDictItem.class).setDictId(dictId);
            if (sysDictItem.getParentId() == null) {
                sysDictItem.setParentId(rootId);
            }
            save(sysDictItem);
            refreshCache();
        } catch (Exception e) {
            throw ServiceException.error(Errors.ADD_ERROR, e);
        }
    }

    @Override
    public void update(Long id, SysDictItemSaveVO sysDictItemSaveVO) {
        SysDictItem oldSysDictItem = get(id);
        checkSaveVO(id, oldSysDictItem.getDictId(), sysDictItemSaveVO);
        try {
            DataUtil.copy(sysDictItemSaveVO, oldSysDictItem);
            if (oldSysDictItem.getParentId() == null) {
                oldSysDictItem.setParentId(rootId);
            }
            updateById(oldSysDictItem);
            refreshCache();
        } catch (Exception e) {
            throw ServiceException.error(Errors.UPDATE_ERROR, e);
        }
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            throw ServiceException.args();
        }
        try {
            remove(new LambdaQueryWrapper<SysDictItem>().eq(SysDictItem::getId, id).or().eq(SysDictItem::getParentId, id));
            refreshCache();
        } catch (Exception e) {
            throw ServiceException.error(Errors.DELETE_ERROR, e);
        }
    }

    @Override
    public Map<String, List<SysDictItemRedisBO>> all() {
        Map<String, List<SysDictItemRedisBO>> result = redisHashTemplate.getAll(RedisConstant.REDIS_KEY_SYS_DICT);
        if (MapUtil.isNotEmpty(result)) {
            return result;
        }
        refreshCache();
        return redisHashTemplate.getAll(RedisConstant.REDIS_KEY_SYS_DICT);
    }

    @Override
    public List<String> values(SysDictItemValuesQueryVO queryVO) {
        return dictValuesList(queryVO.getDictKey(), queryVO.getValuesList());
    }

    @Override
    public List<String> dictValuesList(SysDictKey sysDictKey, List<String> valuesList) {
        List<SysDictItemRedisBO> itemList = all().get(sysDictKey.getValue());
        if (CollectionUtil.isEmpty(itemList)) {
            return ListUtil.empty();
        }
        List<String> result = new ArrayList<>();
        itemList.forEach(item -> dictValuesExpanded(result, item, sysDictItemRedisBO -> CollectionUtil.isEmpty(valuesList) ||
                valuesList.stream().anyMatch(values -> sysDictItemRedisBO.getValues().startsWith(values))));
        return result;
    }

    @Override
    public List<String> labels(SysDictItemLabelsQueryVO queryVO) {
        return dictLabelsList(queryVO.getDictKey(), queryVO.getRoot(), queryVO.getLowest());
    }

    @Override
    public List<String> dictLabelsList(SysDictKey sysDictKey) {
        return dictLabelsList(sysDictKey, null, null);
    }

    @Override
    public List<String> dictLabelsList(SysDictKey sysDictKey, Boolean root, Boolean lowest) {
        List<SysDictItemRedisBO> itemList = all().get(sysDictKey.getValue());
        if (CollectionUtil.isEmpty(itemList)) {
            return ListUtil.empty();
        }
        List<String> result = new ArrayList<>();
        itemList.forEach(item -> dictLabelsExpanded(result, item, root, lowest));
        return result;
    }

    @Override
    public Map<String, String> labelValueMap(SysDictKey dictKey) {
        return dictItemMap(dictKey, SysDictItemRedisBO::getLabels, SysDictItemRedisBO::getValues);
    }

    @Override
    public Map<String, String> valueLabelMap(SysDictKey dictKey) {
        return dictItemMap(dictKey, SysDictItemRedisBO::getValues, SysDictItemRedisBO::getLabels);
    }

    @Override
    public Map<String, String> dictItemMap(SysDictKey sysDictKey, Function<SysDictItemRedisBO, String> key, Function<SysDictItemRedisBO, String> value) {
        List<SysDictItemRedisBO> itemList = all().get(sysDictKey.getValue());
        if (CollectionUtil.isEmpty(itemList)) {
            return MapUtil.empty();
        }
        Map<String, String> result = new HashMap<>();
        itemList.forEach(item -> dictMapExpanded(result, item, key, value));
        return result;
    }

    @Override
    public Map<String, SysDictItemFindVO> find(SysDictItemFindQueryVO queryVO) {
        // 字典标识
        String dictKey = queryVO.getDictKey();
        if (StringUtil.isNull(dictKey)) {
            return null;
        }
        if (!BooleanUtil.isTrue(redisHashTemplate.hasKey(RedisConstant.REDIS_KEY_SYS_DICT))) {
            refreshCache();
        }
        Map<String, SysDictItemFindVO> result = new HashMap<>();
        // 字典值
        List<String> valueList = queryVO.getValueList();

        List<SysDictItemRedisBO> redisBOList = redisHashTemplate.get(RedisConstant.REDIS_KEY_SYS_DICT, dictKey);

        valueList.stream().distinct().forEach(values -> {
            SysDictItemRedisBO redisBO = SysDictItemRedisBO.findNode(redisBOList, values);
            result.put(values, BeanUtil.toBean(redisBO, SysDictItemFindVO.class));
        });

        return result;
    }

    private SysDictItemDTO get(Long id) {
        if (id == null) {
            throw ServiceException.args();
        }
        MPJLambdaWrapper<SysDictItem> query = queryWrapper(null);
        query.eq(SysDictItem::getId, id);
        SysDictItemDTO sysDictItemDTO = selectJoinOne(SysDictItemDTO.class, query);
        if (sysDictItemDTO == null) {
            throw ServiceException.notFound();
        }
        if (rootId.equals(sysDictItemDTO.getParentId())) {
            sysDictItemDTO.setParentId(null);
        }
        return sysDictItemDTO;
    }

    private void checkSaveVO(Long id, Long dictId, SysDictItemSaveVO sysDictItemSaveVO) {
        if (sysDictItemSaveVO == null) {
            throw ServiceException.args();
        }
        // 排序号(值越高，越靠后)
        Integer sortOrder = sysDictItemSaveVO.getSortOrder();
        if (dictId == null || sortOrder == null) {
            throw ServiceException.args();
        }
        // 字典父ID
        Long parentId = sysDictItemSaveVO.getParentId();
        // 展示名
        String label = sysDictItemSaveVO.getLabel();
        verifyLabel(dictId, new SysDictItemVerifyBO().setId(id).setParentId(parentId).setValue(label));

        // 值
        String value = sysDictItemSaveVO.getValue();
        verifyValue(dictId, new SysDictItemVerifyBO().setId(id).setParentId(parentId).setValue(value));
    }

    private <T> List<T> getListByDictId(Long dictId, Function<Tree<Long>, T> mapper) {
        List<SysDictItemDTO> itemDTOList = itemDTOList(dictId);
        return convertList(itemDTOList, mapper);
    }

    private <T> List<T> convertList(List<SysDictItemDTO> itemDTOList, Function<Tree<Long>, T> mapper) {
        List<Tree<Long>> treeList = DataUtil.buildTree(itemDTOList, item ->
                new TreeNode<>(item.getId(), item.getParentId(), item.getLabel(), item.getSortOrder())
                        .setExtra(BeanUtil.beanToMap(item)), rootId);
        return treeList.stream().map(mapper).collect(Collectors.toList());
    }

    private List<SysDictItemDTO> itemDTOList(Long dictId) {
        return selectJoinList(SysDictItemDTO.class, queryWrapper(dictId));
    }

    private MPJLambdaWrapper<SysDictItem> queryWrapper(Long dictId) {
        MPJLambdaWrapper<SysDictItem> wrapper = new MPJLambdaWrapper<SysDictItem>().selectAll(SysDictItem.class)
                .select(SysDict::getDictKey, SysDict::getDictName)
                .leftJoin(SysDict.class, on -> on.eq(SysDict::getId, SysDictItem::getDictId)).orderByAsc(SysDictItem::getSortOrder);

        if (dictId != null) {
            wrapper.eq(SysDictItem::getDictId, dictId);
        }
        return wrapper;
    }

    private SysDictItemTreeVO convertTreeVO(Tree<Long> tree) {
        SysDictItemDTO sysDictItemDTO = BeanUtil.toBean(tree, SysDictItemDTO.class);
        SysDictItemTreeVO sysDictItemTreeVO = DataUtil.toBean(sysDictItemDTO, SysDictItemTreeVO.class);
        if (tree.hasChild()) {
            sysDictItemTreeVO.setChildren(tree.getChildren().stream().map(this::convertTreeVO).collect(Collectors.toList()));
        }
        return sysDictItemTreeVO;
    }

    private SysDictItemCascaderVO convertCascaderVO(Tree<Long> tree) {
        SysDictItemDTO sysDictItemDTO = BeanUtil.toBean(tree, SysDictItemDTO.class);
        SysDictItemCascaderVO sysDictItemCascaderVO = new SysDictItemCascaderVO().setValue(sysDictItemDTO.getId()).setLabel(sysDictItemDTO.getLabel());
        if (tree.hasChild()) {
            sysDictItemCascaderVO.setChildren(tree.getChildren().stream().map(this::convertCascaderVO).collect(Collectors.toList()));
        }
        return sysDictItemCascaderVO;
    }

    private SysDictItemRedisBO convertRedisBO(Tree<Long> tree) {
        SysDictItemDTO sysDictItemDTO = BeanUtil.toBean(tree, SysDictItemDTO.class);
        String value = sysDictItemDTO.getValue();
        String label = sysDictItemDTO.getLabel();

        List<String> labelList = DataUtil.getParents(tree, node -> String.valueOf(node.getName()), true, rootId);
        String labels = StrUtil.join("-", labelList);

        List<String> valueList = DataUtil.getParents(tree, node -> BeanUtil.toBean(node, SysDictItemDTO.class).getValue(), true, rootId);
        String values = StrUtil.join("-", valueList);

        SysDictItemRedisBO sysDictItemRedisBO = new SysDictItemRedisBO().setLabel(label).setValue(value).setLabels(labels).setValues(values);
        if (tree.hasChild()) {
            sysDictItemRedisBO.setChildren(tree.getChildren().stream().map(this::convertRedisBO).collect(Collectors.toList()));
        }
        return sysDictItemRedisBO;
    }

    private void dictValuesExpanded(List<String> result, SysDictItemRedisBO sysDictItemRedisBO, Predicate<SysDictItemRedisBO> predicate) {
        if (predicate == null || predicate.test(sysDictItemRedisBO)) {
            result.add(sysDictItemRedisBO.getValues());
        }
        List<SysDictItemRedisBO> childrenList = sysDictItemRedisBO.getChildren();
        if (CollectionUtil.isNotEmpty(childrenList)) {
            childrenList.forEach(children -> dictValuesExpanded(result, children, predicate));
        }
    }

    private void dictLabelsExpanded(List<String> result, SysDictItemRedisBO sysDictItemRedisBO, Boolean root, Boolean lowest) {
        String labels = sysDictItemRedisBO.getLabels();
        List<SysDictItemRedisBO> childrenList = sysDictItemRedisBO.getChildren();
        boolean rootAdd = !BooleanUtil.isTrue(root) || !StrUtil.contains(labels, "-");
        boolean lowestAdd = !BooleanUtil.isTrue(lowest) || CollectionUtil.isEmpty(childrenList);
        if (rootAdd && lowestAdd) {
            result.add(labels);
        }
        if (CollectionUtil.isNotEmpty(childrenList)) {
            childrenList.forEach(children -> dictLabelsExpanded(result, children, root, lowest));
        }
    }

    private void dictMapExpanded(Map<String, String> result, SysDictItemRedisBO sysDictItemRedisBO, Function<SysDictItemRedisBO, String> key, Function<SysDictItemRedisBO, String> value) {
        List<SysDictItemRedisBO> childrenList = sysDictItemRedisBO.getChildren();
        result.put(key.apply(sysDictItemRedisBO), value.apply(sysDictItemRedisBO));
        if (CollectionUtil.isNotEmpty(childrenList)) {
            childrenList.forEach(children -> dictMapExpanded(result, children, key, value));
        }
    }

}




