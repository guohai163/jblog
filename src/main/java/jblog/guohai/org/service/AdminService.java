package jblog.guohai.org.service;

import jblog.guohai.org.model.BlogContent;
import jblog.guohai.org.model.Result;
import jblog.guohai.org.model.UserModel;

import java.util.List;

public interface AdminService {

    /**
     * 获得管理后台的BLOG列表
     * @param pageNumber 想获取的页号
     * @return
     */
    List<BlogContent> getBackstageList(Integer pageNumber);

    /**
     * 删除指定编号的BLOG
     * @param postCode blog编号
     * @return 结果
     */
    Result<String> delPostBlog(Integer postCode);

    /**
     * 后台用的获取最大页数
     * @return
     */
    Integer getBackstageMaxPageNum();

    /**
     * 按指定编号修改文章
     * @param blog BLOG实体
     * @return
     */
    Result<String> updatePostBlog(BlogContent blog);

    /**
     * 设置用户密码
     * @param user
     * @return
     */
    Result<String> setUserPass(UserModel user);
}
