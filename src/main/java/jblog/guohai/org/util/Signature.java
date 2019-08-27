package jblog.guohai.org.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.auth.CredentialsProvider;
import com.aliyun.oss.common.auth.DefaultCredentialProvider;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import jblog.guohai.org.model.AliyunOssSignature;
import jblog.guohai.org.model.Result;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.HashMap;

/**
 * 签名和签名验证类
 *
 * 1. 目前只支持aliyun的OSS业务
 */
@Component
@ConfigurationProperties
public class Signature {

    @Value("${my-data.aliyunoss.accessid}")
    private String accessid;
    @Value("${my-data.aliyunoss.accesskey}")
    private String accesskey;
    @Value("${my-data.aliyunoss.endpoint}")
    private String endpoint;
    @Value("${my-data.aliyunoss.bucket}")
    private String bucket;
    @Value("${my-data.config.blog-domain}")
    private String blogDomain;
    @Value("${my-data.aliyunoss.is-callback}")
    private Boolean isCallback;

//    private static String AliOSSPublicKeyString="";
    //TODO:考虑是否需要增加过期时间?
    // 用来存储阿里的公钥
    private static HashMap<String, String> AliOSSPublicKeyMap = new HashMap<>();

    /**
     * 私有方法通过URL提交公钥
     * @param url 公钥的URL
     * @return 公钥文本
     * @throws IOException
     */
    private String getAliPUblicKey(String url) throws IOException {
        if(AliOSSPublicKeyMap.get(url) != null) {
            return AliOSSPublicKeyMap.get(url);
        }
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response = client.execute(httpGet);
        try {

            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);
            content = content.replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "").replace("\n","");
            AliOSSPublicKeyMap.put(url, content);
            EntityUtils.consume(entity);
        }
        finally {
            response.close();
            return AliOSSPublicKeyMap.get(url);
        }
    }
    /**
     * 验证RSA
     *
     * @param content
     * @param sign
     * @param publicKey
     * @return
     */
    public static boolean doCheck(String content, byte[] sign, String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("RSA");
            byte[] encodedKey = BinaryUtil.fromBase64String(publicKey);
            PublicKey pubKey = keyFactory.generatePublic(new X509EncodedKeySpec(encodedKey));
            java.security.Signature signature = java.security.Signature.getInstance("MD5withRSA");
            signature.initVerify(pubKey);
            signature.update(content.getBytes());
            boolean bverify = signature.verify(sign);
            return bverify;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }
    /**
     * 返回阿里存储桶所使用的签名
     * @param category 业务类别
     * @return
     */
    public Result<AliyunOssSignature> AliOssSignature(String category, String user){
        OSSClient client = new OSSClient(endpoint, (CredentialsProvider) new DefaultCredentialProvider(accessid, accesskey), null);
        try {
            long expireTime = 30;
            long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            PolicyConditions policyConds = new PolicyConditions();
            policyConds.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, 1048576000);
            policyConds.addConditionItem(MatchMode.StartWith, PolicyConditions.COND_KEY, category);

            String postPolicy = client.generatePostPolicy(expiration, policyConds);

            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            AliyunOssSignature aliSign;
            String postSignature = client.calculatePostSignature(postPolicy);
            if (isCallback) {

                JSONObject jasonCallback = new JSONObject();
                jasonCallback.put("callbackUrl", blogDomain+"/upload/alicallback/" + category);
                jasonCallback.put("callbackBody",
                        "user=" + user + "&filename=${object}&size=${size}");
                jasonCallback.put("callbackBodyType", "application/x-www-form-urlencoded");
                String base64CallbackBody = BinaryUtil.toBase64String(jasonCallback.toString().getBytes());
                aliSign = new AliyunOssSignature(accessid, encodedPolicy, postSignature,
                        category, "https://" + bucket + "." + endpoint, String.valueOf(expireEndTime / 1000), base64CallbackBody, user);
            }else {
                aliSign = new AliyunOssSignature(accessid,encodedPolicy,postSignature,
                        category,"https://" + bucket + "." + endpoint, String.valueOf(expireEndTime / 1000),"",user);
            }
            return new Result<>(true,aliSign);
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        }

        return new Result<>(false,null);
    }

    /**
     * 验证回调签名
     *
     * @param request
     * @param ossCallbackBody
     * @return
     */
    public Result<String> VerifyOSSCallback(HttpServletRequest request, String ossCallbackBody) {
        String autorizationInput = new String(request.getHeader("Authorization"));
        String pubKeyInput = request.getHeader("x-oss-pub-key-url");
        byte[] authorization = BinaryUtil.fromBase64String(autorizationInput);
        byte[] pubKey = BinaryUtil.fromBase64String(pubKeyInput);
        String pubKeyAddr = new String(pubKey);
        if (!pubKeyAddr.startsWith("http://gosspublic.alicdn.com/")
                && !pubKeyAddr.startsWith("https://gosspublic.alicdn.com/")) {
            return new Result<>(false,"pub key addr must be oss addrss");
        }
        try {
            String pubkey= getAliPUblicKey(pubKeyAddr);
            String queryString = request.getQueryString();
            String uri = request.getRequestURI();
            String decodeUri = java.net.URLDecoder.decode(uri, "UTF-8");
            String authStr = decodeUri;
            if (queryString != null && !queryString.equals("")) {
                authStr += "?" + queryString;
            }
            authStr += "\n" + ossCallbackBody;

            if (doCheck(authStr, authorization, pubkey)) {
                return  new Result<>(true,"OK");
            }
            else {
                return new Result<>(false,"can't pass");
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new Result<>(false, "unknown");
    }
}
