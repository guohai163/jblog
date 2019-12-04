package jblog.guohai.org.model;

import lombok.*;


public class Result<T> {

    @Getter
    @Setter
    private boolean status;

    @Getter
    @Setter
    private T data;

    /**
     *
     */
    public Result(boolean status, T data) {
        this.status = status;
        this.data = data;
    }

    public Result() {
        this.status = false;
    }

    public static Result<String> Fail(){

        return new Result<>(false,null);
    }
}
