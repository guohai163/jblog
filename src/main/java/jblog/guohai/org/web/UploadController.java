package jblog.guohai.org.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping(value = "/upload")
public class UploadController {


    @Autowired
    private HttpServletRequest request;

    @RequestMapping(value = "/alicallback/{category}")
    public String aliCallback(@PathVariable("category") String category) {
        System.out.println(category);
        System.out.println(request.getQueryString());
        return "{\"Status\":\"OK\"}";
    }
}
