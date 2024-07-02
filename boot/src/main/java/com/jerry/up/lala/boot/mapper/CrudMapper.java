package com.jerry.up.lala.boot.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jerry.up.lala.boot.entity.Crud;
import org.apache.ibatis.annotations.Param;
import java.util.Collection;

/**
 * crud样例 Mapper
 *
 * @author FMJ
 */
public interface CrudMapper extends BaseMapper<Crud> {

    int insertBatch(@Param("crudCollection") Collection<Crud> crudCollection);

}




