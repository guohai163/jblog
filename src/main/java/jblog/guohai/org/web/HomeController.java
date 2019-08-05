package jblog.guohai.org.web;

import com.vladsch.flexmark.ext.tables.TablesExtension;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.parser.ParserEmulationProfile;
import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.util.builder.Extension;
import com.vladsch.flexmark.util.data.MutableDataSet;
import jblog.guohai.org.model.BlogContent;
import jblog.guohai.org.service.BlogService;
import jblog.guohai.org.util.MarkdownToHtml;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Arrays;
import java.util.List;

/**
 * 展示数据控制器
 */
@Controller
public class HomeController {

    @Autowired
    private BlogService blogService;

    /**
     * 获得BLOG首页
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/")
    public String home(Model model) {
        List<BlogContent> listContent = blogService.getByPage(1);
        model.addAttribute("list", listContent);
        model.addAttribute("pageNum", 1);
        model.addAttribute("maxPageNum", blogService.getMaxPageNum());
        return "home";
    }

    /**
     * 返回指定年月日短标题的BLOG内容，并绑定到模板引擎上
     *
     * @param model
     * @param year
     * @param month
     * @param day
     * @param smallTitle
     * @return
     */
    @RequestMapping(value = "/{year}/{month}/{day}/{postSmallTitle}")
    public String content(Model model, @PathVariable("year") int year, @PathVariable("month") int month,
                          @PathVariable("day") int day, @PathVariable("postSmallTitle") String smallTitle) {

        String sDate = year + "-" + month + "-" + day;
        BlogContent blogContent = blogService.getByYMDTitle(sDate, smallTitle);
        blogContent.setPostContent(MarkdownToHtml.convert(blogContent.getPostContent()));
        model.addAttribute("content", blogContent);

        //分别向前台绑定上一条和下一条
        BlogContent connectLast = blogService.getLastBlog(blogContent.getPostCode());
        if (null != connectLast) {
            model.addAttribute("contactLast", connectLast);
        }
        BlogContent connectNext = blogService.getNextBlog(blogContent.getPostCode());
        if (connectNext != null) {
            model.addAttribute("contactNext", connectNext);
        }

        return "content";
    }

    /**
     * 获取指定页面内容
     *
     * @param model
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/page/{pageNum}/")
    public String getPageContent(Model model, @PathVariable("pageNum") int pageNum) {
        List<BlogContent> listContent = blogService.getByPage(pageNum);
        model.addAttribute("list", listContent);
        model.addAttribute("pageNum", pageNum);
        model.addAttribute("maxPageNum", blogService.getMaxPageNum());
        return "home";
    }

}
