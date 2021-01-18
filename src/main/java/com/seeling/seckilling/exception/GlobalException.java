package com.seeling.seckilling.exception;

import com.seeling.seckilling.result.CodeMsg;
import lombok.Getter;

/**
 * @author Match Fu
 * @date 2020/5/31 1:42 下午
 */
@Getter
public class GlobalException extends RuntimeException{
    private CodeMsg cm;

    public GlobalException(CodeMsg cm) {
        super(cm.toString());
        this.cm = cm;
    }
}
