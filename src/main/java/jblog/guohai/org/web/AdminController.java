package jblog.guohai.org.web;

import jblog.guohai.org.model.Result;
import jblog.guohai.org.service.UserService;
import org.markdownj.MarkdownProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Controller
@RequestMapping(value = "/admin")
public class AdminController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @Autowired
    private HttpServletResponse response;

    @RequestMapping(value = "/")
    public String login(Model model, String username, String pass) throws IOException {
        logger.debug(username+pass);
        Result<String> result = userService.checkUserPass(username,pass);

        if(result.isState()) {
            Cookie userCook = new Cookie("user",result.getData());
            //登录状态过期时间20分钟
            userCook.setMaxAge(1800);
            response.addCookie(userCook);
            response.sendRedirect("/admin/main");
        }
        else {
            model.addAttribute("errorMsg",result.getData());
        }
        return "admin/login";
    }

    @RequestMapping(value = "/main")
    public String adminMain(Model model) {
        return "admin/main";
    }

    @RequestMapping(value = "/preview")
    public String adminPreview(Model model, String content) {

        String htmlIntro = new MarkdownProcessor().markdown(content);
        model.addAttribute("content", htmlIntro);
        return "admin/preview";
    }
}
