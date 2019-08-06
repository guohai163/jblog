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
}
