package com.jerry.up.lala.boot.bo;

import cn.hutool.core.util.ObjectUtil;
import com.jerry.up.lala.boot.vo.SysDictItemFindVO;
import com.jerry.up.lala.framework.common.annotation.DataBean;
import com.jerry.up.lala.framework.common.util.StringUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.List;

/**
 * <p>Description: 字典项缓存
 *
 * @author FMJ
 * @date 2024/4/17 17:57
 */
@Data
@Accessors(chain = true)
@DataBean(targetTypes = {SysDictItemFindVO.class})
public class SysDictItemRedisBO {

    /**
     * 展示名
     */
    private String label;

    /**
     * 值
     */
    private String value;

    /**
     * 名称列表
     */
    private String labels;

    /**
     * 值列表
     */
    private String values;


    private List<SysDictItemRedisBO> children;

    public static SysDictItemRedisBO findNode(List<SysDictItemRedisBO> sysDictItemRedisBOList, String values) {
        for (SysDictItemRedisBO sysDictItemRedisBO : sysDictItemRedisBOList) {
            SysDictItemRedisBO node = getNode(sysDictItemRedisBO, values);
            if (node != null) {
                return node;
            }
        }
        return null;
    }

    private static SysDictItemRedisBO getNode(SysDictItemRedisBO node, String values) {
        if (StringUtil.isNull(values) && StringUtil.isNull(node.getValues())) {
            return node;
        }
        if (ObjectUtil.equals(values, node.getValues())) {
            return node;
        }
        final List<SysDictItemRedisBO> children = node.getChildren();
        if (null == children) {
            return null;
        }

        // 查找子节点
        SysDictItemRedisBO childNode;
        for (SysDictItemRedisBO child : children) {
            childNode = getNode(child, values);
            if (null != childNode) {
                return childNode;
            }
        }

        // 未找到节点
        return null;
    }

}
