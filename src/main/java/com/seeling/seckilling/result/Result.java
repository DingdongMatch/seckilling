package com.seeling.seckilling.result;

import lombok.Getter;

/**
 * Result
 * @author Match Fu
 * @date 2020/3/21 9:05 上午
 */
@Getter
public class Result<T> {
    private int code;
    private String msg;
    private T data;

    /**
     * 成功时调用
     * @param data data
     * @param <T> 泛型
     * @return 操作结果
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(data);
    }

    public static <T> Result<T> error(CodeMsg cm) {
        return new Result<>(cm);
    }

    private Result(T data) {
        this.code = 0;
        this.msg = "success";
        this.data = data;
    }

    private Result(CodeMsg cm) {
        if (cm == null) {
            return;
        }
        this.code = cm.getCode();
        this.msg = cm.getMsg();
    }
}
