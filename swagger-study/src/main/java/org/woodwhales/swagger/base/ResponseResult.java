package org.woodwhales.swagger.base;

import lombok.Data;

/**
 * @author woodwhales on 2020-08-07
 * @description
 */
@Data
public class ResponseResult<T> {

    private Integer code;
    private String msg;
    private T data;

    public static <T> ResponseResult<T> success(T data) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setCode(0);
        responseResult.setMsg("success");
        responseResult.setData(data);
        return responseResult;
    }

    public static <T> ResponseResult<T> success() {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setCode(0);
        responseResult.setMsg("success");
        responseResult.setData(null);
        return responseResult;
    }

    public static <T> ResponseResult<T> error() {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setCode(-1);
        responseResult.setMsg("fail");
        responseResult.setData(null);
        return responseResult;
    }

    public static <T> ResponseResult<T> error(Integer code, String msg) {
        ResponseResult<T> responseResult = new ResponseResult<>();
        responseResult.setCode(code);
        responseResult.setMsg(msg);
        responseResult.setData(null);
        return responseResult;
    }

}
