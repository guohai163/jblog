package jblog.guohai.org.service;

import jblog.guohai.org.dao.BlogDao;
import jblog.guohai.org.dao.HotkeyDao;
import jblog.guohai.org.dao.UserDao;
import jblog.guohai.org.model.BlogContent;
import jblog.guohai.org.model.Hotkey;
import jblog.guohai.org.model.Result;
import jblog.guohai.org.model.UserModel;
import jblog.guohai.org.util.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    BlogDao blogDao;

    @Autowired
    UserDao userDao;

    @Autowired
    HotkeyDao hotkeyDao;
    /**
     * 后台用的页大小
     */
    private final Integer adminPageSize = 20;

    /**
     * 获得管理后台的BLOG列表
     *
     * @param pageNumber 想获取的页号
     * @return
     */
    @Override
    public List<BlogContent> getBackstageList(Integer pageNumber) {

        return blogDao.getBackstageList((pageNumber - 1) * adminPageSize, adminPageSize);
    }

    /**
     * 删除指定编号的BLOG
     *
     * @param postCode blog编号
     * @return 结果
     */
    @Override
    public Result<String> delPostBlog(Integer postCode) {
        BlogContent blog = blogDao.getContentById(postCode);
        if( null == blog ) {
            return new Result<>(false,"没有此编号文章");
        }
        if( blogDao.delPostBlog(postCode) ) {
            String[] hotkey = blog.getPostSmallTitle().split("-");
            //做关键字减一操作
            for(String key:hotkey) {
                Hotkey hot = hotkeyDao.getHotkeybyKey(key);
                if(null != hot && hot.getHotkeyCount()>0) {
                    hotkeyDao.setHotkeyCountDec1(hot);
                }
            }
            return new Result<>(true,"删除成功");
        } else {
            return new Result<>(false, "删除失败");
        }
    }

    /**
     * 后台用的获取最大页数
     *
     * @return
     */
    @Override
    public Integer getBackstageMaxPageNum() {

        int postCount = blogDao.getBackstagePostCount();
        return postCount % adminPageSize == 0 ? postCount / adminPageSize : postCount / adminPageSize + 1;
    }

    /**
     * 按指定编号修改文章
     *
     * @param blog BLOG实体
     * @return
     */
    @Override
    public Result<String> updatePostBlog(BlogContent blog) {
        BlogContent oldBlog = blogDao.getContentById(blog.getPostCode());
        if( null == oldBlog ) {
            return new Result<>(false,"没有此编号文章");
        }
        //在不相等时更新关键字计数
        if(!oldBlog.getPostSmallTitle().equals(blog.getPostSmallTitle())){
            String[] hotkey = oldBlog.getPostSmallTitle().split("-");
            //做关键字减一操作
            for(String key:hotkey) {
                Hotkey hot = hotkeyDao.getHotkeybyKey(key);
                if(null != hot && hot.getHotkeyCount()>0) {
                    hotkeyDao.setHotkeyCountDec1(hot);
                }
            }
            hotkey = blog.getPostSmallTitle().split("-");
            for(String key:hotkey) {
                if(null == hotkeyDao.getHotkeybyKey(key)) {
                    //当不存在时增加
                    hotkeyDao.addHotkey(new Hotkey(key, 0));
                }else{
                    hotkeyDao.setHotkeyCountAdd1(new Hotkey(key,0));
                }
            }
        }
        if( blogDao.updatePostBlog(blog) ) {
            return new Result<>(true,"修改成功");
        } else {
            return new Result<>(false, "未知错误");
        }
    }

    /**
     * 设置用户密码
     *
     * @param user
     * @return
     */
    @Override
    public Result<String> setUserPass(UserModel user) {
        //TODO:检查密码强度
        String userPass = user.getUserPass();
        user.setUserPass(MD5.GetMD5Code(MD5.GetMD5Code(userPass) + user.getUserKey()));
        if (userDao.setUserByName(user)) {
            return new Result<>(true, "更新成功");
        } else {
            return new Result<>(false, "操作失败");
        }
    }

    /**
     * 增加一篇BLOG
     *
     * @param blog BLOG实体
     * @return 返回结果情况
     */
    @Override
    public Result<String> addPostBlog(BlogContent blog) {
        Result<String> result = new Result<>();
        blogDao.addPostBlog(blog);
        if (blog.getPostCode() > 0) {
            //做关键字加1操作
            String[] hotkey = blog.getPostSmallTitle().split("-");
            for(String key:hotkey) {
                if(null == hotkeyDao.getHotkeybyKey(key)) {
                    //当不存在时增加
                    hotkeyDao.addHotkey(new Hotkey(key, 0));
                }else{
                    hotkeyDao.setHotkeyCountAdd1(new Hotkey(key,0));
                }
            }
            result.setStatus(true);
            result.setData("Success:" + blog.getPostCode());
        } else {
            result.setStatus(false);
            result.setData("Error");
        }
        return result;
    }
}
