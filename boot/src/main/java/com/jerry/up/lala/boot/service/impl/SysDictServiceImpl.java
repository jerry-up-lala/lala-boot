package com.jerry.up.lala.boot.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jerry.up.lala.boot.entity.SysDict;
import com.jerry.up.lala.boot.entity.SysDictItem;
import com.jerry.up.lala.boot.error.BootErrors;
import com.jerry.up.lala.boot.mapper.SysDictMapper;
import com.jerry.up.lala.boot.service.SysDictItemService;
import com.jerry.up.lala.boot.service.SysDictService;
import com.jerry.up.lala.boot.vo.SysDictInfoVO;
import com.jerry.up.lala.boot.vo.SysDictSaveVO;
import com.jerry.up.lala.boot.vo.SysDictVO;
import com.jerry.up.lala.framework.common.exception.Errors;
import com.jerry.up.lala.framework.common.exception.ServiceException;
import com.jerry.up.lala.framework.common.model.DataBody;
import com.jerry.up.lala.framework.common.model.DataIdBody;
import com.jerry.up.lala.framework.common.util.BeanUtil;
import com.jerry.up.lala.framework.common.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author jerry
 * @description 针对表【sys_dict(字典类型表)】的数据库操作Service实现
 * @createDate 2024-04-17 17:40:25
 */
@Service
public class SysDictServiceImpl extends ServiceImpl<SysDictMapper, SysDict> implements SysDictService {

    @Autowired
    private SysDictItemService sysDictItemService;

    @Override
    public List<SysDictVO> list(DataBody<String> query) {
        try {
            LambdaQueryWrapper<SysDict> queryWrapper = new LambdaQueryWrapper<SysDict>().orderByAsc(SysDict::getId);
            if (query != null) {
                String value = query.getValue();
                if (StringUtil.isNotNull(value)) {
                    queryWrapper.and(wrapper -> wrapper.like(SysDict::getDictName, value).or().like(SysDict::getDictKey, value));
                }
            }
            List<SysDict> sysDictList = list(queryWrapper);
            return BeanUtil.toBeanList(sysDictList, SysDictVO.class);
        } catch (Exception e) {
            throw ServiceException.error(Errors.QUERY_ERROR, e);
        }
    }

    @Override
    public SysDictInfoVO info(Long id) {
        SysDict sysDict = get(id);
        return BeanUtil.toBean(sysDict, SysDictInfoVO.class);
    }

    @Override
    public void verifyDictName(DataIdBody<Long, String> dataIdBody) {
        Long id = dataIdBody.getId();
        String dictName = dataIdBody.getValue();
        // 校验字典标识是否存在
        LambdaQueryWrapper<SysDict> query = new LambdaQueryWrapper<SysDict>().eq(SysDict::getDictName, dictName);
        if (id != null) {
            query.ne(SysDict::getId, id);
        }
        if (exists(query)) {
            throw ServiceException.error(BootErrors.DICT_NAME_EXISTS);
        }
    }

    @Override
    public void verifyDictKey(DataIdBody<Long, String> dataIdBody) {
        Long id = dataIdBody.getId();
        String dictKey = dataIdBody.getValue();
        // 校验字典标识是否存在
        LambdaQueryWrapper<SysDict> query = new LambdaQueryWrapper<SysDict>().eq(SysDict::getDictKey, dictKey);
        if (id != null) {
            query.ne(SysDict::getId, id);
        }
        if (exists(query)) {
            throw ServiceException.error(BootErrors.DICT_KEY_EXISTS);
        }
    }

    @Override
    public void add(SysDictSaveVO sysDictSaveVO) {
        checkSaveVO(null, sysDictSaveVO);
        try {
            SysDict sysDict = BeanUtil.toBean(sysDictSaveVO, SysDict.class);
            save(sysDict);
            refreshCache();
        } catch (Exception e) {
            throw ServiceException.error(Errors.ADD_ERROR, e);
        }
    }

    @Override
    public void update(Long id, SysDictSaveVO sysDictSaveVO) {
        checkSaveVO(id, sysDictSaveVO);
        SysDict oldSysDict = get(id);
        try {
            BeanUtil.copy(sysDictSaveVO, oldSysDict);
            updateById(oldSysDict);
            refreshCache();
        } catch (Exception e) {
            throw ServiceException.error(Errors.UPDATE_ERROR, e);
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (id == null) {
            throw ServiceException.args();
        }
        try {
            removeById(id);
            sysDictItemService.remove(new LambdaQueryWrapper<SysDictItem>().eq(SysDictItem::getDictId, id));
            refreshCache();
        } catch (Exception e) {
            throw ServiceException.error(Errors.DELETE_ERROR, e);
        }
    }

    @Override
    public void refreshCache() {
        sysDictItemService.refreshCache();
    }

    private SysDict get(Long id) {
        if (id == null) {
            throw ServiceException.args();
        }
        SysDict sysDict = getById(id);
        if (sysDict == null) {
            throw ServiceException.notFound();
        }
        return sysDict;
    }

    private void checkSaveVO(Long id, SysDictSaveVO sysDictSaveVO) {
        if (sysDictSaveVO == null) {
            throw ServiceException.args();
        }
        // 字典名称
        String dictName = sysDictSaveVO.getDictName();
        // 字典标识
        String dictKey = sysDictSaveVO.getDictKey();
        verifyDictName(new DataIdBody<>(id, dictName));
        verifyDictKey(new DataIdBody<>(id, dictKey));

    }
}




