package jblog.guohai.org.web;


import freemarker.template.TemplateModelException;
import jblog.guohai.org.bll.agent.WechatAgent;
import jblog.guohai.org.model.*;
import jblog.guohai.org.util.JsonTool;
import jblog.guohai.org.util.Signature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;
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
@ConfigurationProperties
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

    @Autowired
    private freemarker.template.Configuration configuration;

    @Autowired
    private WechatAgent wechatAgent;

    @Autowired
    Signature signature;

    /**
     * 上传是否需要回调
     */
    @Value("${my-data.aliyunoss.is-callback}")
    private Boolean isCallback;

    /**
     * 登录
     * @param model 参数
     * @return 返回模板名
     */
    @RequestMapping(value = "/")
    public String login(Model model) {
        model.addAttribute("wxAppId", wechatAgent.getAppId());
        model.addAttribute("wxRedirect", wechatAgent.getRedirect());
        return "admin/login";
    }

    @ResponseBody
    @RequestMapping(value = "/login")
    public Result<String> adminLogin(Model model,@RequestBody UserModel user) throws TemplateModelException {
        Result<UserModel> result = userService.checkUserPass(user.getUserName(), user.getUserPass());
        if (result.isStatus()) {
            Cookie userCook = new Cookie("user", result.getData().getUserUUID());
            //登录状态过期时间20分钟
            userCook.setMaxAge(1800);
            userCook.setPath("/");
            response.addCookie(userCook);
            return new Result<String>(true, "登录成功");
        }else{
            return new Result<String>(false, "登录失败");
        }
    }

    /**
     *
     * 注销方法，删除COOKIES，删除MAP内对应成员
     * @param model
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/logout")
    public  Result<String> adminLogout(Model model) {
        String uuid = null;
        if (null == request.getCookies()) {

            return new Result<>(false,"没有找到cookie");
        }
        for (Cookie cookie : request.getCookies()) {
            if (cookie.getName().equals("user")) {
                uuid = cookie.getValue();
                cookie.setValue(null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
                break;
            }
        }
        if (null == uuid) {

            return new Result<>(false,"uuid为空");
        }
        return userService.logoutUser(uuid);
    }

    @RequestMapping(value = "/main")
    public String adminMain(Model model, Integer postCode) {
        System.out.println(postCode);
        if (null != postCode) {
            BlogContent blog = blogService.getByID(postCode);
            model.addAttribute("blog", blog);
        }
        return "admin/main";
    }

    /**
     * 预览MD文档接口
     *
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
        // 获取文章分类列表
        List<ClassType> classTypeList = blogService.getClassList();
        model.addAttribute("classTypeList", classTypeList);
        return "admin/list";
    }


    /**
     * 新增或修改BLOG接口，仅接收POST请求
     *
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
                result = adminService.addPostBlog(blog);
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
     *
     * @param blog
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/delblog")
    public Result<String> delBlog(@RequestBody BlogContent blog) {
        return adminService.delPostBlog(blog.getPostCode());
    }

    /**
     * 更新密码接口
     * @param inUser
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updatepass")
    public Result<String> updatePassword(@RequestBody UserModel inUser) {
        UserModel user = UserServiceImpl.getUserByCookie(request);
        user.setUserPass(inUser.getUserPass());
        return adminService.setUserPass(user);
    }

    /**
     * 安全管理页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/security")
    public String adminSecurity(Model model) {
        return "admin/security";
    }

    /**
     * 分类管理页面
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/class")
    public String adminClass(Model model) {
        // 获取文章分类列表
        List<ClassType> classTypeList = blogService.getClassList();
        model.addAttribute("classTypeList", classTypeList);
        return "admin/class";
    }

    /**
     * 设置博客分类
     *
     * @param postCode  博客编号
     * @param classCode 分类编号
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/blog/class", method = RequestMethod.POST)
    public Result<String> setBlogClass(@RequestParam("postCode") Integer postCode, @RequestParam("classCode") Integer classCode) {
        return blogService.addUpdateBlogClass(postCode, classCode);
    }

    /**
     * 删除博客分类
     *
     * @param classCode 分类编号
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/blog/class/del", method = RequestMethod.POST)
    public Result<String> delBlogClass(@RequestParam("classCode") Integer classCode) {
        return blogService.delClass(classCode);
    }

    /**
     * 编辑博客分类
     *
     * @param classCode 分类编号
     * @param className 分类名称
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/blog/class/edit", method = RequestMethod.POST)
    public Result<String> editBlogClass(@RequestParam("classCode") Integer classCode, @RequestParam("className") String className) {
        if (StringUtils.isEmpty(className) || className.length() > 100) {
            return new Result<>(false, "分类名称不可为空或超过100字符");
        }
        return blogService.updateClassName(classCode, className);
    }

    /**
     * 添加博客分类
     *
     * @param className 分类名称
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/blog/class/add", method = RequestMethod.POST)
    public Result<String> editBlogClass(@RequestParam("className") String className) {
        if (StringUtils.isEmpty(className) || className.length() > 100) {
            return new Result<>(false, "分类名称不可为空或超过100字符");
        }
        return blogService.addClass(className);
    }

    /**
     * 热词界面
     * @param model
     * @return
     */
    @RequestMapping(value = "/hotkey")
    public String adminHotkey(Model model) {
        model.addAttribute("hotkey_list", blogService.getHotkeyList());
        return "admin/hotkey";
    }

    /**
     * 重新组建热词接口
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/hotkey/renew",method = RequestMethod.POST)
    public Result<String> renewHotkey(){
        return adminService.renewHotkey();
    }

    /**
     * 获得阿里云OSS服务签名
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/alioss")
    public AliyunOssSignature getOSSPolicy() {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST");
        UserModel user = UserServiceImpl.getUserByCookie(request);
        AliyunOssSignature aliSign = signature.AliOssSignature("avatar",String.valueOf(user.getUserCode())).getData();
        aliSign.setUser(String.valueOf(user.getUserCode()));
        return aliSign;
    }

    /**
     * 更新头像，如果没有回调同时 更新DB
     * @param avatarName
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/upload/avatar")
    public Result<String> uploadAvatarSuccess(@RequestParam("avatar_name") String avatarName) {

        try {
            configuration.setSharedVariable("user_avatar", avatarName);
        } catch (TemplateModelException e) {
            e.printStackTrace();
        }
        if(!isCallback) {
            String parm = "user="+UserServiceImpl.getUserByCookie(request).getUserCode()+"&filename="+avatarName;
            return userService.setUserAvata(parm);
        }
        return new Result<>(true,"success");

    }

    /**
     * 微信登陆
     *
     * @param code
     * @return
     */

    @RequestMapping(value = "/wechat/login")
    public String wechatLogin(Model model,String code) throws TemplateModelException, IOException {
        logger.info("微信扫码登陆 ");
        if(StringUtils.isEmpty(code)){
            logger.info("非法访问 ");
            return "admin/wxlogin";
        }
        //下面的代码应该到放到service层了
        Result<String> ret = wechatAgent.getWechatAccessToken(code);
        if(!ret.isStatus()){
            logger.info("第三方授权信息出错");
            return "admin/wxlogin";
        }
        //我们要维护一下 accessToken 不然每次登陆都会去取 accessToken 腾讯会block的
        WxAccessTokenBean token = JsonTool.toBeanFormStr(ret.getData(), WxAccessTokenBean.class);
        //通过openid反查用户 执行登陆
        Result<UserModel> result = userService.checkUserOpenId(token.getOpenid());
        if (result.isStatus()) {
            logger.debug(String.format("登陆用户信息 %s", JsonTool.toStrFormBean(result.getData())));
            Cookie userCook = new Cookie("user", result.getData().getUserUUID());
            // 登录状态过期时间20分钟
            userCook.setMaxAge(1800);
            userCook.setPath("/");
            response.addCookie(userCook);
            return "admin/wxlogin";
        } else {
            return "admin/wxlogin";
        }
    }
}
