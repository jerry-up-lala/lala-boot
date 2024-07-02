package com.jerry.up.lala.boot.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.BetweenFormatter;
import cn.hutool.core.lang.tree.Tree;
import cn.hutool.core.util.ArrayUtil;
import com.jerry.up.lala.boot.bo.RedisHashBO;
import com.jerry.up.lala.boot.bo.RedisInfoBO;
import com.jerry.up.lala.boot.bo.RedisKeyBO;
import com.jerry.up.lala.boot.bo.RedisZSetBO;
import com.jerry.up.lala.boot.enums.RedisListPushType;
import com.jerry.up.lala.boot.error.BootErrors;
import com.jerry.up.lala.boot.service.RedisService;
import com.jerry.up.lala.boot.vo.RedisKeyTreeVO;
import com.jerry.up.lala.boot.vo.RedisListElementAddVO;
import com.jerry.up.lala.boot.vo.RedisListElementDeleteVO;
import com.jerry.up.lala.boot.vo.RedisListElementUpdateVO;
import com.jerry.up.lala.framework.core.common.CommonConstant;
import com.jerry.up.lala.framework.core.common.DataBody;
import com.jerry.up.lala.framework.core.data.DataUtil;
import com.jerry.up.lala.framework.core.exception.ServiceException;
import com.jerry.up.lala.framework.core.redis.*;
import com.jerry.up.lala.framework.core.data.CheckUtil;
import com.jerry.up.lala.framework.core.data.StringUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>Description: 缓存service实现
 *
 * @author FMJ
 * @date 2023/8/31 10:35
 */
@Service
@Slf4j
public class RedisServiceImpl implements RedisService {

    @Autowired
    private RedisValueTemplate<Object> redisValueTemplate;

    @Autowired
    private RedisHashTemplate<String, Object> redisHashTemplate;

    @Autowired
    private RedisListTemplate<Object> redisListTemplate;

    @Autowired
    private RedisSetTemplate<Object> redisSetTemplate;

    @Autowired
    private RedisZSetTemplate<Object> redisZSetTemplate;

    @Override
    public List<RedisKeyTreeVO> keysTree(String pattern) {
        Set<String> keys = keys(pattern);

        if (CollectionUtil.isEmpty(keys)) {
            return new ArrayList<>();
        }
        List<Tree<String>> treeList = DataUtil.toTreeBySeparator(keys, CommonConstant.REDIS_KEY_SEPARATOR);
        if (CollectionUtil.isEmpty(treeList)) {
            return new ArrayList<>();
        }
        return treeList.stream().map(this::redisKeyTree).collect(Collectors.toList());
    }

    private RedisKeyTreeVO redisKeyTree(Tree<String> tree) {
        RedisKeyTreeVO result = new RedisKeyTreeVO().setKey(tree.getId()).setTitle(tree.getName().toString())
                .setCount(count(tree, 0L));
        if (tree.hasChild()) {
            result.setChildren(tree.getChildren().stream().map(this::redisKeyTree).collect(Collectors.toList()));
        } else {
            result.setCount(1L);
        }
        return result;
    }

    @Override
    public List<RedisKeyBO> keysList(String pattern) {
        Set<String> keys = keys(pattern);
        return keys.stream().map(this::redisKey).collect(Collectors.toList());
    }

    @Override
    public Boolean hasKey(String key) {
        return redisValueTemplate.hasKey(key);
    }

    @Override
    public RedisInfoBO info(String key) {
        if (StringUtil.isNull(key)) {
            throw ServiceException.args();
        }
        if (!hasKey(key)) {
            throw ServiceException.error(BootErrors.REDIS_KEY_INVALID);
        }
        RedisInfoBO result = DataUtil.toBean(redisKey(key), RedisInfoBO.class);
        String dataType = result.getDataType();
        Object value = null;
        if (DataType.STRING.name().equals(dataType)) {
            value = redisValueTemplate.get(key);
        } else if (DataType.LIST.name().equals(dataType)) {
            value = redisListTemplate.get(key);
        } else if (DataType.SET.name().equals(dataType)) {
            value = redisSetTemplate.get(key);
        } else if (DataType.ZSET.name().equals(dataType)) {
            value = redisZSetTemplate.getWithScoresAll(key);
        } else if (DataType.HASH.name().equals(dataType)) {
            List<RedisHashBO> boolList = new ArrayList<>();
            redisHashTemplate.getAll(key).forEach((field, fieldValue) ->
                    boolList.add(new RedisHashBO().setField(field).setValue(fieldValue)));
            value = boolList;
        }
        result.setValue(value);
        return result;
    }

    @Override
    public RedisInfoBO add(RedisInfoBO redisInfoVO) {
        String key = redisInfoVO.getKey();
        String dataType = redisInfoVO.getDataType();
        Object value = redisInfoVO.getValue();
        if (StringUtil.isNull(key) || StringUtil.isNull(dataType) || value == null) {
            throw ServiceException.args();
        }
        if (hasKey(key)) {
            throw ServiceException.error(BootErrors.REDIS_KEY_EXISTS);
        }
        if (DataType.STRING.name().equals(dataType)) {
            redisValueTemplate.set(key, value);
        } else if (DataType.LIST.name().equals(dataType)) {
            redisListTemplate.addAll(key, (List) value);
        } else if (DataType.SET.name().equals(dataType)) {
            redisSetTemplate.add(key, ArrayUtil.toArray((List) value, Object.class));
        } else if (DataType.ZSET.name().equals(dataType)) {
            List<RedisZSetBO> boList = DataUtil.toBeanList((List) value, RedisZSetBO.class);
            Set<ZSetOperations.TypedTuple<Object>> tuples = boList.stream().map(item ->
                    new DefaultTypedTuple<>(item.getValue(), item.getScore())).collect(Collectors.toSet());
            redisZSetTemplate.add(key, tuples);
        } else if (DataType.HASH.name().equals(dataType)) {
            List<RedisHashBO> boList = DataUtil.toBeanList((List) value, RedisHashBO.class);
            Map<String, Object> map = new HashMap<>();
            boList.forEach(item -> map.put(item.getField(), item.getValue()));
            redisHashTemplate.putAll(key, map);
        }
        Long expire = redisInfoVO.getExpire();
        if (expire != null) {
            redisValueTemplate.setExpire(key, expire);
        }
        return info(key);
    }

    @Override
    public RedisInfoBO stringUpdate(String key, DataBody<Object> dataBody) {
        Object value = CheckUtil.dataNull(dataBody);
        checkDataType(key, DataType.STRING);
        redisValueTemplate.set(key, value);
        return info(key);
    }

    @Override
    public RedisInfoBO listAddElement(String key, RedisListElementAddVO redisListElementAddVO) {
        if (redisListElementAddVO == null) {
            throw ServiceException.args();
        }
        checkDataType(key, DataType.LIST);
        Integer pushType = redisListElementAddVO.getPushType();
        CheckUtil.arrayNotContains(RedisListPushType.values(), RedisListPushType::getType, pushType);
        Object value = redisListElementAddVO.getValue();
        if (RedisListPushType.TAIL.getType().equals(pushType)) {
            redisListTemplate.addTail(key, value);
        } else {
            redisListTemplate.addHead(key, value);
        }
        return info(key);
    }

    @Override
    public RedisInfoBO listUpdateElement(String key, RedisListElementUpdateVO redisListElementUpdateVO) {
        if (redisListElementUpdateVO == null) {
            throw ServiceException.args();
        }
        Long index = redisListElementUpdateVO.getIndex();
        Long size = redisListTemplate.size(key);
        if (index == null || index > size - 1) {
            throw ServiceException.args();
        }
        checkDataType(key, DataType.LIST);
        redisListTemplate.edit(key, index, redisListElementUpdateVO.getValue());
        return info(key);
    }

    @Override
    public RedisInfoBO listDeleteElement(String key, RedisListElementDeleteVO redisListElementDeleteVO) {
        if (redisListElementDeleteVO == null) {
            throw ServiceException.args();
        }
        Long count = redisListElementDeleteVO.getCount();
        if (count == null || count <= 0L) {
            throw ServiceException.args();
        }
        checkDataType(key, DataType.LIST);
        Integer pushType = redisListElementDeleteVO.getPushType();
        CheckUtil.arrayNotContains(RedisListPushType.values(), RedisListPushType::getType, pushType);
        if (RedisListPushType.TAIL.getType().equals(pushType)) {
            redisListTemplate.removeTail(key, count);
        } else {
            redisListTemplate.removeHead(key, count);
        }
        return info(key);
    }

    @Override
    public RedisInfoBO setAddMembers(String key, DataBody<List<Object>> dataBody) {
        List<Object> value = CheckUtil.dataNull(dataBody);
        checkDataType(key, DataType.SET);
        redisSetTemplate.add(key, ArrayUtil.toArray(value, Object.class));
        return info(key);
    }

    @Override
    public RedisInfoBO setDeleteMembers(String key, DataBody<List<Object>> dataBody) {
        List<Object> value = CheckUtil.dataNull(dataBody);
        checkDataType(key, DataType.SET);
        redisSetTemplate.remove(key, ArrayUtil.toArray(value, Object.class));
        return info(key);
    }

    @Override
    public RedisInfoBO zSetMemberScore(String key, DataBody<RedisZSetBO> dataBody) {
        RedisZSetBO redisZSetBO = CheckUtil.dataNull(dataBody);
        checkDataType(key, DataType.ZSET);
        redisZSetTemplate.add(key, redisZSetBO.getValue(), redisZSetBO.getScore());
        return info(key);
    }

    @Override
    public RedisInfoBO zSetAddMembers(String key, DataBody<List<RedisZSetBO>> dataBody) {
        List<RedisZSetBO> list = CheckUtil.dataNull(dataBody);
        checkDataType(key, DataType.ZSET);
        Set<ZSetOperations.TypedTuple<Object>> tuples = list.stream().map(item ->
                new DefaultTypedTuple<>(item.getValue(), item.getScore())).collect(Collectors.toSet());
        redisZSetTemplate.add(key, tuples);
        return info(key);
    }

    @Override
    public RedisInfoBO zSetDeleteMembers(String key, DataBody<List<Object>> dataBody) {
        List<Object> value = CheckUtil.dataNull(dataBody);
        checkDataType(key, DataType.ZSET);
        redisZSetTemplate.remove(key, ArrayUtil.toArray(value, Object.class));
        return info(key);
    }

    @Override
    public RedisInfoBO hashAddFields(String key, DataBody<List<RedisHashBO>> dataBody) {
        List<RedisHashBO> value = CheckUtil.dataNull(dataBody);
        checkDataType(key, DataType.HASH);
        Map<String, Object> map = new HashMap<>();
        value.forEach(item -> map.put(item.getField(), item.getValue()));
        redisHashTemplate.putAll(key, map);
        return info(key);
    }

    @Override
    public RedisInfoBO hashUpdateField(String key, DataBody<RedisHashBO> dataBody) {
        RedisHashBO redisHashBO = CheckUtil.dataNull(dataBody);
        checkDataType(key, DataType.HASH);
        redisHashTemplate.put(key, redisHashBO.getField(), redisHashBO.getValue());
        return info(key);
    }

    @Override
    public RedisInfoBO hashDeleteFields(String key, DataBody<List<String>> dataBody) {
        List<String> value = CheckUtil.dataNull(dataBody);
        checkDataType(key, DataType.HASH);
        redisHashTemplate.remove(key, value.toArray());
        return info(key);
    }

    private void checkDataType(String key, DataType dataType) {
        RedisInfoBO redisInfoBO = info(key);
        if (!dataType.name().equals(redisInfoBO.getDataType())) {
            throw ServiceException.error(BootErrors.REDIS_KEY_DATA_TYPE_INVALID);
        }
    }

    @Override
    public Long getExpire(String key) {
        return redisValueTemplate.getExpire(key);
    }

    @Override
    public Boolean setExpire(String key, DataBody<Long> dataBody) {
        Long timeout = dataBody.getValue();
        return timeout == null ? redisValueTemplate.persist(key) : redisValueTemplate.setExpire(key, timeout);
    }

    @Override
    public void delete(String key) {
        redisValueTemplate.delete(key);
    }

    private Set<String> keys(String pattern) {
        return redisValueTemplate.keys(pattern);
    }

    private Long count(Tree<String> tree, Long count) {
        if (CollectionUtil.isNotEmpty(tree.getChildren())) {
            count = count + tree.getChildren().stream().filter(item -> item.get("key") != null && (Boolean) (item.get("key"))).count();
            for (Tree<String> item : tree.getChildren()) {
                count = count(item, count);
            }
        }
        return count;
    }

    private RedisKeyBO redisKey(String key) {
        DataType dataType = redisValueTemplate.dataType(key);
        Long expire = redisValueTemplate.getExpire(key);
        String expireFormat = (expire != null && expire > 0L) ? new BetweenFormatter(expire * 100L, BetweenFormatter.Level.SECOND, 2).format() : null;
        return new RedisKeyBO().setKey(key).setDataType(dataType.name()).setExpire(expire).setExpireFormat(expireFormat);
    }


}
