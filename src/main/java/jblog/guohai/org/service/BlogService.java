package jblog.guohai.org.service;

import jblog.guohai.org.model.BlogContent;
import java.util.List;

public interface BlogService {
    /**
     * 获得指定编号BLOG
     * @param code
     * @return
     */
    BlogContent getByID(Integer code);

    /**
     * 获得指定日期BLOG
     * @param sDate
     * @param smallTitle
     * @return
     */
    BlogContent getByYMDTitle(String sDate, String smallTitle);

    /**
     * 活的下一条BLOG
     * @param code
     * @return
     */
    BlogContent getNextBlog(Integer code);

    /**
     * 获得上一条BLOG
     * @param code
     * @return
     */
    BlogContent getLastBlog(Integer code);

    /**
     * 获得首页数据
     * @return
     */
    List<BlogContent> getHomeList();

    /**
     * 获得指定页号数据
     * @param pageNumber
     * @return
     */
    List<BlogContent> getByPage(Integer pageNumber);

    /**
     * 返回 总数量
     * @return
     */
    Integer getMaxPageNum();
}
