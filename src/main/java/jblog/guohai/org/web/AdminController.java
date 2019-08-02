package jblog.guohai.org.web;

import jblog.guohai.org.model.BlogContent;
import jblog.guohai.org.model.Result;
import jblog.guohai.org.service.AdminService;
import jblog.guohai.org.service.BlogService;
import jblog.guohai.org.service.UserService;
import jblog.guohai.org.util.MarkdownToHtml;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
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

    @RequestMapping(value = "/")
    public String login(Model model, String username, String pass) throws IOException {
        logger.debug(username + pass);
        Result<String> result = userService.checkUserPass(username, pass);

        if (result.isState()) {
            Cookie userCook = new Cookie("user", result.getData());
            //登录状态过期时间20分钟
            userCook.setMaxAge(1800);
            response.addCookie(userCook);
            response.sendRedirect("/admin/main");
        } else {
            model.addAttribute("errorMsg", result.getData());
        }
        return "admin/login";
    }

    @RequestMapping(value = "/main")
    public String adminMain(Model model) {
        return "admin/main";
    }


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
    public String adminList(Model model, @RequestParam(defaultValue = "1") Integer pageNum) {
        List<BlogContent> list = adminService.getBackstageList(pageNum);
        model.addAttribute("listContent", list);
        return "admin/list";
    }


    @ResponseBody
    @RequestMapping(value = "/postblog", method = RequestMethod.POST)
    public Result<String> postBlog(@RequestBody BlogContent blog) throws ParseException {

        Result<String> result;
        try {
            result = blogService.addPostBlog(blog);
            return result;
        } catch (Exception ex) {
            return new Result<>(false, "excepiton");
        }

    }

}
