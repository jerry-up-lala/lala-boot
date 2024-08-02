package com.jerry.up.lala.boot.service;

import com.jerry.up.lala.boot.bo.RedisHashBO;
import com.jerry.up.lala.boot.bo.RedisInfoBO;
import com.jerry.up.lala.boot.bo.RedisKeyBO;
import com.jerry.up.lala.boot.bo.RedisZSetBO;
import com.jerry.up.lala.boot.vo.RedisKeyTreeVO;
import com.jerry.up.lala.boot.vo.RedisListElementAddVO;
import com.jerry.up.lala.boot.vo.RedisListElementDeleteVO;
import com.jerry.up.lala.boot.vo.RedisListElementUpdateVO;
import com.jerry.up.lala.framework.common.model.DataBody;

import java.util.List;

/**
 * <p>Description: 缓存service
 *
 * @author FMJ
 * @date 2023/8/31 10:35
 */
public interface RedisService {

    List<RedisKeyTreeVO> keysTree(String pattern);

    List<RedisKeyBO> keysList(String pattern);

    Boolean hasKey(String key);

    RedisInfoBO info(String key);

    RedisInfoBO add(RedisInfoBO redisInfoVO);

    RedisInfoBO stringUpdate(String key, DataBody<Object> dataBody);

    RedisInfoBO listAddElement(String key, RedisListElementAddVO redisListElementAddVO);

    RedisInfoBO listUpdateElement(String key, RedisListElementUpdateVO redisListElementUpdateVO);

    RedisInfoBO listDeleteElement(String key, RedisListElementDeleteVO redisListElementDeleteVO);

    RedisInfoBO setAddMembers(String key, DataBody<List<Object>> dataBody);

    RedisInfoBO setDeleteMembers(String key, DataBody<List<Object>> dataBody);

    RedisInfoBO zSetAddMembers(String key, DataBody<List<RedisZSetBO>> dataBody);

    RedisInfoBO zSetMemberScore(String key, DataBody<RedisZSetBO> dataBody);

    RedisInfoBO zSetDeleteMembers(String key, DataBody<List<Object>> dataBody);

    RedisInfoBO hashAddFields(String key, DataBody<List<RedisHashBO>> dataBody);

    RedisInfoBO hashUpdateField(String key, DataBody<RedisHashBO> dataBody);

    RedisInfoBO hashDeleteFields(String key, DataBody<List<String>> dataBody);

    Long getExpire(String key);

    Boolean setExpire(String key, DataBody<Long> dataBody);

    void delete(String key);

}
