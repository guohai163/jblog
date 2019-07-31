package jblog.guohai.org.model;

import lombok.*;


public class Result<T> {

    @Getter
    @Setter
    private boolean state;

    @Getter
    @Setter
    private T data;

    /**
     *
     */
    public Result(boolean state, T data) {
        this.state = state;
        this.data = data;
    }

    public Result() {
        this.state = false;
    }
}
