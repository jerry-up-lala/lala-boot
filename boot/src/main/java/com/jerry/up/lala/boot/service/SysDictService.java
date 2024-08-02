package com.jerry.up.lala.boot.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jerry.up.lala.boot.entity.SysDict;
import com.jerry.up.lala.boot.vo.SysDictInfoVO;
import com.jerry.up.lala.boot.vo.SysDictSaveVO;
import com.jerry.up.lala.boot.vo.SysDictVO;
import com.jerry.up.lala.framework.common.model.DataBody;
import com.jerry.up.lala.framework.common.model.DataIdBody;

import java.util.List;

/**
 * @author jerry
 * @description 针对表【sys_dict(字典类型表)】的数据库操作Service
 * @createDate 2024-04-17 17:40:25
 */
public interface SysDictService extends IService<SysDict> {

    List<SysDictVO> list(DataBody<String> query);

    SysDictInfoVO info(Long id);

    void verifyDictName(DataIdBody<Long, String> dataIdBody);

    void verifyDictKey(DataIdBody<Long, String> dataIdBody);

    void add(SysDictSaveVO sysDictSaveVO);

    void update(Long id, SysDictSaveVO sysDictSaveVO);

    void delete(Long id);

    void refreshCache();

}
