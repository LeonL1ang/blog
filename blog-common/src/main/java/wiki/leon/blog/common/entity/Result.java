package wiki.leon.blog.common.entity;

/***
 *  contoller返回结果类
 */
public class Result {

    private Integer code;
    private String message;
    private Object data;

    public Result() {
    }

    public Result(ResultStatusEnum statusEnum) {
        super();
        this.code = statusEnum.getCode();
        this.message = statusEnum.getMessage();
    }

    public Result(ResultStatusEnum statusEnum, Object data) {
        super();
        this.code = statusEnum.getCode();
        this.message = statusEnum.getMessage();
        this.data = data;
    }

    // 静态成功方法
    public static Result to(ResultStatusEnum statusEnum, Object data){
        return new Result(statusEnum, data);
    }
    // 静态成功方法
    public static Result to(ResultStatusEnum statusEnum){
        return new Result(statusEnum);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
