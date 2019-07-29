package jblog.guohai.org.model;

import lombok.*;


public class Result<T> {

    @Getter
    @Setter
    private boolean state;

    @Getter
    @Setter
    private T data;
}
