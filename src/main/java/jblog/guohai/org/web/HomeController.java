package jblog.guohai.org.web;


import jblog.guohai.org.model.BlogContent;
import jblog.guohai.org.service.BlogService;
import org.markdownj.MarkdownProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @Autowired
    private BlogService blogService;

    @RequestMapping(value = "/")
    public String home(Model model) {
        BlogContent blogContent = blogService.getByID(1);
        return "home";
    }

    @RequestMapping(value="/{year}/{month}/{day}/{smallTitle}")
    public String content(Model model,@PathVariable("year") int year,@PathVariable("month") int month,
                          @PathVariable("day") int day,@PathVariable("smallTitle") String smallTitle) {

        String sDate = year+"-"+month+"-"+day;
        BlogContent blogContent = blogService.getByYMDTitle(sDate, smallTitle);
        String htmlIntro = new MarkdownProcessor().markdown(blogContent.getIntro());
        blogContent.setIntro(htmlIntro);
        model.addAttribute("content", blogContent);
        return "content";
    }

}
