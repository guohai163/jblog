package jblog.guohai.org.service;

import jblog.guohai.org.model.BlogContent;

import java.util.List;

public interface AdminService {

    /**
     * 获得管理后台的BLOG列表
     * @param pageNumber 想获取的页号
     * @return
     */
    List<BlogContent> getBackstageList(Integer pageNumber);
}
