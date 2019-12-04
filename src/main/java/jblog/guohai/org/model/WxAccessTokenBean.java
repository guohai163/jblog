package jblog.guohai.org.model;

import lombok.Data;

/**
 * 微信AccessToken
 * 显然这里需要指定一下别名，不然命名规则就开始乱了
 * @author zhongdaiqi
 *
 */
@Data
public class WxAccessTokenBean {
	private String access_token;
	private String expires_in;
	private String openid;
	private String refresh_token;
	private String scope;
	private String unionid;
}
