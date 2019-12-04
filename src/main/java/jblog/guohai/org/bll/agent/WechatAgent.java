package jblog.guohai.org.bll.agent;

import jblog.guohai.org.model.Result;
import jblog.guohai.org.util.HttpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class WechatAgent {

	private static String ACCESS_TOKEN_URL_TPL = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
	@Value("${my-data.wechat.appId}")
	private String appId;
	@Value("${my-data.wechat.secret}")
	private String secret;
	@Value("${my-data.wechat.redirect}")
	private String redirect;

	/**
	 * 获取AccessToken
	 * 
	 * @param code
	 * @return
	 */
	public Result<String> getWechatAccessToken(String code) {
		String accessTokenUrl = String.format(ACCESS_TOKEN_URL_TPL, appId, secret, code);
		return HttpUtil.get(accessTokenUrl, null);
	}

	/**
	 * 获取appId
	 * @return
	 */
	public String getAppId(){
		return  appId;
	}

	/**
	 * 获取redirect
	 * @return
	 */
	public String getRedirect(){
		return  redirect;
	}
}
