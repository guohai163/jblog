package jblog.guohai.org.service;

import jblog.guohai.org.model.BlogContent;

import java.util.Date;

public interface BlogService {
    BlogContent getByID(Integer code);

    BlogContent getByYMDTitle(String sDate, String smallTitle);
}
