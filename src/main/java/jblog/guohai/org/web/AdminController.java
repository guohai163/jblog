package jblog.guohai.org.web;

import jblog.guohai.org.model.BlogContent;
import jblog.guohai.org.model.Result;
import jblog.guohai.org.model.UserModel;
import jblog.guohai.org.service.AdminService;
import jblog.guohai.org.service.BlogService;
import jblog.guohai.org.service.UserService;
import jblog.guohai.org.service.UserServiceImpl;
import jblog.guohai.org.util.MarkdownToHtml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;


@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private BlogService blogService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpServletRequest request;

    /**
     * 登录
     * @param model 参数
     * @return 返回模板名
     */
    @RequestMapping(value = "/")
    public String login(Model model) {
        return "admin/login";
    }

    @ResponseBody
    @RequestMapping(value = "/login")
    public Result<String> adminLogin(Model model,@RequestBody UserModel user) {
        Result<String> result = userService.checkUserPass(user.getUserName(), user.getUserPass());
        if (result.isStatus()) {
            Cookie userCook = new Cookie("user", result.getData());
            //登录状态过期时间20分钟
            userCook.setMaxAge(1800);
            response.addCookie(userCook);
            return new Result<String>(true, "登录成功");
        }else{
            return new Result<String>(false, result.getData());
        }
    }

    @RequestMapping(value = "/main")
    public String adminMain(Model model, Integer postCode) {
        System.out.println(postCode);
        if( null != postCode) {
            BlogContent blog = blogService.getByID(postCode);
            model.addAttribute("blog",blog);
        }
        return "admin/main";
    }

    /**
     * 预览MD文档接口
     * @param model
     * @param blog
     * @return 返回包含HTML的实体
     */
    @ResponseBody
    @RequestMapping(value = "/preview", method = RequestMethod.POST)
    public Result<String> previewMarkdown(Model model, @RequestBody BlogContent blog) {
        return new Result<>(true, MarkdownToHtml.convert(blog.getPostContent()));
    }

    /**
     * 后台列表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/list")
    public String adminList(Model model, @RequestParam(defaultValue = "1") Integer page) {
        List<BlogContent> list = adminService.getBackstageList(page);
        model.addAttribute("listContent", list);
        model.addAttribute("pageNum", page);

        model.addAttribute("maxPageNum", adminService.getBackstageMaxPageNum());
        return "admin/list";
    }


    /**
     * 新增或修改BLOG接口，仅接收POST请求
     * @param blog
     * @return
     * @throws ParseException
     */
    @ResponseBody
    @RequestMapping(value = "/postblog", method = RequestMethod.POST)
    public Result<String> postBlog(@RequestBody BlogContent blog) throws ParseException {

        Result<String> result;
        try {
            if( blog.getPostCode() == 0 ) {
                result = blogService.addPostBlog(blog);
            }else{
                result = adminService.updatePostBlog(blog);
            }
            return result;
        } catch (Exception ex) {
            return new Result<>(false, "excepiton");
        }

    }

    /**
     * 删除一篇文章
     * @param blog
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delblog")
    public  Result<String> delBlog(@RequestBody BlogContent blog) {
        return adminService.delPostBlog(blog.getPostCode());
    }

    @ResponseBody
    @RequestMapping(value = "/updatepass")
    public Result<String> updatePassword(@RequestBody UserModel inUser) {
        UserModel user = UserServiceImpl.getUserByCookie(request);
        user.setUserPass(inUser.getUserPass());
        return adminService.setUserPass(user);
    }

    /**
     * 安全管理页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/security")
    public String adminSecurity(Model model) {
        return "admin/security";
    }

}
