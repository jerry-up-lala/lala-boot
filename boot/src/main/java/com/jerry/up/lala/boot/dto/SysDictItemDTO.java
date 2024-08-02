package com.jerry.up.lala.boot.dto;

import com.jerry.up.lala.boot.entity.SysDictItem;
import com.jerry.up.lala.boot.vo.SysDictItemInfoVO;
import com.jerry.up.lala.boot.vo.SysDictItemTreeVO;
import com.jerry.up.lala.boot.vo.SysDictItemVO;
import com.jerry.up.lala.framework.common.annotation.DataBean;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>Description: 字典项
 *
 * @author FMJ
 * @date 2024/4/17 17:57
 */
@Data
@EqualsAndHashCode(callSuper = true)
@DataBean(targetTypes = {SysDictItemTreeVO.class, SysDictItemVO.class, SysDictItemInfoVO.class})
public class SysDictItemDTO extends SysDictItem {

    /**
     * 字典名称
     */
    private String dictName;

    /**
     * 字典标识
     */
    private String dictKey;
}
