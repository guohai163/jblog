package jblog.guohai.org.dao;

import jblog.guohai.org.model.OAuthModel;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 第三方登陆（微信登陆）
 * @author zhongdaiqi
 *
 */
public interface OAuthDao {
	/**
     * 查找用户id根据openId
     * @param openid openid
     * @return oauth实体
     */
    @Select("SELECT * FROM jblog_oauth WHERE oauth_openid=#{openid};")
    OAuthModel getOAuthByOpenId(@Param("openid") String openId);
}
