package com.jerry.up.lala.boot.ctrl;

import com.jerry.up.lala.boot.access.RedisAccessConstant;
import com.jerry.up.lala.boot.bo.RedisHashBO;
import com.jerry.up.lala.boot.bo.RedisInfoBO;
import com.jerry.up.lala.boot.bo.RedisKeyBO;
import com.jerry.up.lala.boot.bo.RedisZSetBO;
import com.jerry.up.lala.boot.service.RedisService;
import com.jerry.up.lala.boot.vo.RedisKeyTreeVO;
import com.jerry.up.lala.boot.vo.RedisListElementAddVO;
import com.jerry.up.lala.boot.vo.RedisListElementDeleteVO;
import com.jerry.up.lala.boot.vo.RedisListElementUpdateVO;
import com.jerry.up.lala.framework.boot.api.Api;
import com.jerry.up.lala.framework.common.model.DataBody;
import com.jerry.up.lala.framework.common.r.R;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>Description: Redis
 *
 * @author FMJ
 * @date 2023/8/30 17:53
 */
@RestController
@RequestMapping("/redis")
public class RedisCtrl {

    @Autowired
    private RedisService redisService;

    @GetMapping("/keys/tree/{pattern}")
    @Api(value = "Redis-键树", accessCodes = {
            RedisAccessConstant.REDIS, RedisAccessConstant.REDIS_QUERY
    })
    public R<List<RedisKeyTreeVO>> keysTree(@PathVariable("pattern") String pattern) {
        List<RedisKeyTreeVO> keys = redisService.keysTree(pattern);
        return R.succeed(keys);
    }

    @GetMapping("/keys/list/{pattern}")
    @Api(value = "Redis-列表", accessCodes = {
            RedisAccessConstant.REDIS, RedisAccessConstant.REDIS_QUERY
    })
    public R<List<RedisKeyBO>> keysList(@PathVariable("pattern") String pattern) {
        List<RedisKeyBO> keys = redisService.keysList(pattern);
        return R.succeed(keys);
    }

    @GetMapping("/expire/{key}")
    @Api(value = "Redis-查询有效期", accessCodes = RedisAccessConstant.REDIS_EXPIRE)
    public R<Long> getExpire(@PathVariable("key") String key) {
        Long result = redisService.getExpire(key);
        return R.succeed(result);
    }

    @PutMapping("/expire/{key}")
    @Api(value = "Redis-编辑有效期", accessCodes = RedisAccessConstant.REDIS_EXPIRE)
    public R<Boolean> setExpire(@PathVariable("key") String key, @RequestBody DataBody<Long> dataBody) {
        Boolean result = redisService.setExpire(key, dataBody);
        return R.succeed(result);
    }

    @DeleteMapping("/info/{key}")
    @Api(value = "Redis-删除", accessCodes = RedisAccessConstant.REDIS_DELETE)
    public R delete(@PathVariable("key") String key) {
        redisService.delete(key);
        return R.empty();
    }

    @GetMapping("/info/{key}")
    @Api(value = "Redis-详情", accessCodes = {
            RedisAccessConstant.REDIS_INFO, RedisAccessConstant.REDIS_UPDATE
    })
    public R<RedisInfoBO> info(@PathVariable("key") String key) {
        RedisInfoBO result = redisService.info(key);
        return R.succeed(result);
    }

    @GetMapping("/has-key/{key}")
    @Api(value = "Redis-键是否存在", accessCodes = RedisAccessConstant.REDIS_ADD)
    public R<Boolean> hasKey(@PathVariable("key") String key) {
        Boolean result = redisService.hasKey(key);
        return R.succeed(result);
    }

    @PostMapping
    @Api(value = "Redis-新增", accessCodes = RedisAccessConstant.REDIS_ADD)
    public R<RedisInfoBO> add(@RequestBody RedisInfoBO redisInfoVO) {
        RedisInfoBO result = redisService.add(redisInfoVO);
        return R.succeed(result);
    }

    @PutMapping("/string/{key}")
    @Api(value = "Redis-更新值", accessCodes = RedisAccessConstant.REDIS_UPDATE)
    public R<RedisInfoBO> update(@PathVariable("key") String key, @RequestBody DataBody<Object> dataBody) {
        RedisInfoBO result = redisService.stringUpdate(key, dataBody);
        return R.succeed(result);
    }

    @PostMapping("/list/element/{key}")
    @Api(value = "Redis-添加元素", accessCodes = RedisAccessConstant.REDIS_UPDATE)
    public R<RedisInfoBO> listAddElement(@PathVariable("key") String key, @RequestBody RedisListElementAddVO redisListElementAddVO) {
        RedisInfoBO result = redisService.listAddElement(key, redisListElementAddVO);
        return R.succeed(result);
    }

    @PutMapping("/list/element/{key}")
    @Api(value = "Redis-编辑元素", accessCodes = RedisAccessConstant.REDIS_UPDATE)
    public R<RedisInfoBO> listUpdateElement(@PathVariable("key") String key, @RequestBody RedisListElementUpdateVO redisListElementUpdateVO) {
        RedisInfoBO result = redisService.listUpdateElement(key, redisListElementUpdateVO);
        return R.succeed(result);
    }

    @DeleteMapping("/list/element/{key}")
    @Api(value = "Redis-删除元素", accessCodes = RedisAccessConstant.REDIS_UPDATE)
    public R<RedisInfoBO> listDeleteElement(@PathVariable("key") String key, @RequestBody RedisListElementDeleteVO redisListElementDeleteVO) {
        RedisInfoBO result = redisService.listDeleteElement(key, redisListElementDeleteVO);
        return R.succeed(result);
    }

    @PostMapping("/set/members/{key}")
    @Api(value = "Redis-添加成员", accessCodes = RedisAccessConstant.REDIS_UPDATE)
    public R<RedisInfoBO> setAddMembers(@PathVariable("key") String key, @RequestBody DataBody<List<Object>> dataBody) {
        RedisInfoBO result = redisService.setAddMembers(key, dataBody);
        return R.succeed(result);
    }

    @DeleteMapping("/set/members/{key}")
    @Api(value = "Redis-删除成员", accessCodes = RedisAccessConstant.REDIS_UPDATE)
    public R<RedisInfoBO> setDeleteMembers(@PathVariable("key") String key, @RequestBody DataBody<List<Object>> dataBody) {
        RedisInfoBO result = redisService.setDeleteMembers(key, dataBody);
        return R.succeed(result);
    }

    @PostMapping("/z-set/members/{key}")
    @Api(value = "Redis-添加有序成员", accessCodes = RedisAccessConstant.REDIS_INFO)
    public R<RedisInfoBO> zSetAddMembers(@PathVariable("key") String key, @RequestBody DataBody<List<RedisZSetBO>> dataBody) {
        RedisInfoBO result = redisService.zSetAddMembers(key, dataBody);
        return R.succeed(result);
    }

    @PutMapping("/z-set/member/score/{key}")
    @Api(value = "Redis-更新有序成员", accessCodes = RedisAccessConstant.REDIS_UPDATE)
    public R<RedisInfoBO> zSetMemberScore(@PathVariable("key") String key, @RequestBody DataBody<RedisZSetBO> dataBody) {
        RedisInfoBO result = redisService.zSetMemberScore(key, dataBody);
        return R.succeed(result);
    }

    @DeleteMapping("/z-set/members/{key}")
    @Api(value = "Redis-删除有序成员", accessCodes = RedisAccessConstant.REDIS_UPDATE)
    public R<RedisInfoBO> zSetDeleteMembers(@PathVariable("key") String key, @RequestBody DataBody<List<Object>> dataBody) {
        RedisInfoBO result = redisService.zSetDeleteMembers(key, dataBody);
        return R.succeed(result);
    }

    @PostMapping("/hash/fields/{key}")
    @Api(value = "Redis-添加字段", accessCodes = RedisAccessConstant.REDIS_UPDATE)
    public R<RedisInfoBO> hashAddFields(@PathVariable("key") String key, @RequestBody DataBody<List<RedisHashBO>> dataBody) {
        RedisInfoBO result = redisService.hashAddFields(key, dataBody);
        return R.succeed(result);
    }

    @PutMapping("/hash/field/{key}")
    @Api(value = "Redis-更新字段", accessCodes = RedisAccessConstant.REDIS_UPDATE)
    public R<RedisInfoBO> hashUpdateField(@PathVariable("key") String key, @RequestBody DataBody<RedisHashBO> dataBody) {
        RedisInfoBO result = redisService.hashUpdateField(key, dataBody);
        return R.succeed(result);
    }

    @DeleteMapping("/hash/fields/{key}")
    @Api(value = "Redis-删除字段", accessCodes = RedisAccessConstant.REDIS_UPDATE)
    public R<RedisInfoBO> hashDeleteFields(@PathVariable("key") String key, @RequestBody DataBody<List<String>> dataBody) {
        RedisInfoBO result = redisService.hashDeleteFields(key, dataBody);
        return R.succeed(result);
    }

}
