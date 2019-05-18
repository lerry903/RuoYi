package com.ruoyi.common.page;

import cn.hutool.core.util.StrUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * 分页数据
 *
 * @author ruoyi
 */
@Data
public class PageDomain implements Serializable {
    /**
     * 当前记录起始索引
     */
    private Integer pageNum;

    /**
     * 每页显示记录数
     */
    private Integer pageSize;

    /**
     * 排序列
     */
    private String orderByColumn;
    /**
     * 排序的方向 "desc" 或者 "asc".
     */

    private String isAsc;

    public String getOrderBy() {
        if (StrUtil.isEmpty(orderByColumn)) {
            return "" ;
        }
        return StrUtil.toUnderlineCase(orderByColumn) + " " + isAsc;
    }
}
