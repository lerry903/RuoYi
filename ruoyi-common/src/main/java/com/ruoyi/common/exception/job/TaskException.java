package com.ruoyi.common.exception.job;

/**
 * 计划策略异常
 *
 * @author ruoyi
 */
public class TaskException extends Exception {
    private static final long serialVersionUID = 1L;

    private final Code code;

    public TaskException(String msg, Code code) {
        this(msg, code, null);
    }

    private TaskException(String msg, Code code, Exception nestedEx) {
        super(msg, nestedEx);
        this.code = code;
    }

    public Code getCode() {
        return code;
    }

    public enum Code {
        /**
         * 计划任务已存在
         */
        TASK_EXISTS,
        /**
         * 不存在计划任务
         */
        NO_TASK_EXISTS,
        /**
         * 计划任务已经开始
         */
        TASK_ALREADY_STARTED,
        /**
         * 未知
         */
        UNKNOWN,
        /**
         * 配置错误
         */
        CONFIG_ERROR,
        /**
         * 计划任务节点不可用
         */
        TASK_NODE_NOT_AVAILABLE
    }
}