package jblog.guohai.org.model;

import lombok.Data;

/**
 * oauth实体
 * 
 * @author zhongdaiqi
 *
 */
@Data
public class OAuthModel {

	private int oauthCode;
	private String oauthOpenId;
	private String oauthUnionId;
	private String oauthAppId;
	private int userCode;
}
