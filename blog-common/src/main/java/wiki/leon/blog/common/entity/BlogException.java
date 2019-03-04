package wiki.leon.blog.common.entity;

public class BlogException extends RuntimeException {

    private ResultStatusEnum statusEnum;

    public BlogException(ResultStatusEnum statusEnum) {
        super();
        this.statusEnum = statusEnum;
    }

    public ResultStatusEnum getStatusEnum() {
        return statusEnum;
    }
}
