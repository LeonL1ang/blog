package wiki.leon.blog.common.entity;

public enum  ResultStatusEnum {

    OK(2000, "成功"),
    NOT_FOUND(4001, "未找到数据"),
    NOT_LOGIN(4004, "权限不足，未登录"),
    DATA_EXISTS(4005, "数据已存在"),
    PARAM_ERR(4003, "参数不正确"),
    VALIDATE_FAIL(4006, "校验未通过"),
    SERVER_ERR(5000, "服务器出错"),
    REQUEST_EXCEPTION(4002, "请求异常"),

    ;
    private int code;
    private String message;
    
    ResultStatusEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
