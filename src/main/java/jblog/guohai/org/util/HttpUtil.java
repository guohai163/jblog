package jblog.guohai.org.util;

import jblog.guohai.org.model.Result;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;

/**
 * http请求工具类
 * 
 * @author zhongdaiqi
 *
 */
public class HttpUtil {
	private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
	private static final String DEFAULT_CHARSET = "UTF-8";
	private static final String REQUEST_ERROR="请求出现异常";
	
	/**
	 * 发起Get请求
	 * @param url
	 * @return
	 */
	public static Result<String> get(String url, Map<String, Object> params){
		CloseableHttpClient httpClient = HttpClients.createDefault();
		RequestConfig config = RequestConfig.custom().setSocketTimeout(10000).setConnectTimeout(10000).build();
		RequestBuilder requestBuilder = RequestBuilder.get().setUri(url);
		requestBuilder.setConfig(config);
		if(null!=params){
			for (Map.Entry<String, Object> entry : params.entrySet()) {
				requestBuilder.addParameter(entry.getKey(), entry.getValue().toString());
            }
		}
		try {
			logger.debug("发起HTTP Get请求  URL:"+url);
			CloseableHttpResponse response = httpClient.execute(requestBuilder.build());
			String body = EntityUtils.toString(response.getEntity(), DEFAULT_CHARSET);
			logger.debug(String.format("URL:%s 返回结果:%s", url,body));
			return new Result<String>(true,body);
		} catch (ClientProtocolException e) {
			logger.error(REQUEST_ERROR, e);
			return Result.Fail();
		} catch (IOException e) {
			logger.error(REQUEST_ERROR, e);
			return Result.Fail();
		}
	}
}
