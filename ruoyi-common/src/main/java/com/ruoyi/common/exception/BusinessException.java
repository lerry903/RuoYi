package com.ruoyi.common.exception;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Created with IntelliJ IDEA.
 * Description:
 *
 * @author LErry.li
 * Date: 2018-12-09
 * Time: 16:08
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BusinessException extends RuntimeException{

    protected final String message;

    public BusinessException(String message) {
        this.message = message;
    }

    public BusinessException(String message, Throwable e){
        super(message, e);
        this.message = message;
    }

}
