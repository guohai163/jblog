package jblog.guohai.org.web;

import jblog.guohai.org.model.Result;
import jblog.guohai.org.service.UserService;
import jblog.guohai.org.util.Signature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping(value = "/upload")
public class UploadController {


    @Autowired
    private HttpServletRequest request;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private Signature signature;

    @Autowired
    private UserService userService;

    /**
     * 上传方法回调
     * @param category
     * @param callbackBody
     * @return
     */
    @RequestMapping(value = "/alicallback/{category}")
    public Result<String> aliCallback(@PathVariable("category") String category, @RequestBody String callbackBody) {

        Result<String> result = signature.VerifyOSSCallback(request,callbackBody);
        if(result.isStatus()) {

            return userService.setUserAvata(callbackBody);
        } else {
            return result;
        }
    }
}
