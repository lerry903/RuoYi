package com.ruoyi.framework.web.domain.server;

import cn.hutool.core.util.NumberUtil;

import java.math.BigDecimal;

/**
 * CPU相关信息
 *
 * @author ruoyi
 */
public class Cpu {
    /**
     * 核心数
     */
    private int cpuNum;

    /**
     * CPU总的使用率
     */
    private double total;

    /**
     * CPU系统使用率
     */
    private double sys;

    /**
     * CPU用户使用率
     */
    private double used;

    /**
     * CPU当前等待率
     */
    private double wait;

    /**
     * CPU当前空闲率
     */
    private double free;

    public int getCpuNum() {
        return cpuNum;
    }

    public void setCpuNum(int cpuNum) {
        this.cpuNum = cpuNum;
    }

    public BigDecimal getTotal() {
        return NumberUtil.round(NumberUtil.mul(total, 100), 2);
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public BigDecimal getSys() {
        return NumberUtil.round(NumberUtil.mul(sys / total, 100), 2);
    }

    public void setSys(double sys) {
        this.sys = sys;
    }

    public BigDecimal getUsed() {
        return NumberUtil.round(NumberUtil.mul(used / total, 100), 2);
    }

    public void setUsed(double used) {
        this.used = used;
    }

    public BigDecimal getWait() {
        return NumberUtil.round(NumberUtil.mul(wait / total, 100), 2);
    }

    public void setWait(double wait) {
        this.wait = wait;
    }

    public BigDecimal getFree() {
        return NumberUtil.round(NumberUtil.mul(free / total, 100), 2);
    }

    public void setFree(double free) {
        this.free = free;
    }
}
