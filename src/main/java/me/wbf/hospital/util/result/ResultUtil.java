package me.wbf.hospital.util.result;

/**
 * 结果工具类
 */
public class ResultUtil {
    public static Result success() {
        return success(null);
    }

    public static Result success(Object object) {
        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(ResultEnum.SUCCESS.getMsg());
        result.setData(object);
        return result;
    }

    public static Result success(String msg) {
        Result result = new Result();
        result.setCode(ResultEnum.SUCCESS.getCode());
        result.setMsg(msg);
        return result;
    }

    public static Result error(ResultEnum resultEnum) {
        return error(resultEnum.getCode(), resultEnum.getMsg());
    }

    public static Result error(String msg) {
        return error(ResultEnum.FAIL.getCode(),msg);
    }

    public static Result error(Integer code, String msg) {
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}

