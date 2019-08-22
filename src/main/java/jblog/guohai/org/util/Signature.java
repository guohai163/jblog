package jblog.guohai.org.util;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import jblog.guohai.org.model.AliyunOssSignature;
import jblog.guohai.org.model.Result;
import lombok.Getter;
import lombok.Setter;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.util.Date;

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



    /**
     * 返回阿里存储桶所使用的签名
     * @param category 业务类别
     * @return
     */
    public Result<AliyunOssSignature> AliOssSignature(String category){
        OSSClient client = new OSSClient(endpoint,accessid,accesskey);
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
            String postSignature = client.calculatePostSignature(postPolicy);
            JSONObject jasonCallback = new JSONObject();
            jasonCallback.put("callbackUrl", "http://jblog.guohai.org/upload/alicallback/"+category+"/");
            jasonCallback.put("callbackBody",
                    "filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
            jasonCallback.put("callbackBodyType", "application/x-www-form-urlencoded");
            String base64CallbackBody = BinaryUtil.toBase64String(jasonCallback.toString().getBytes());
            AliyunOssSignature aliSign = new AliyunOssSignature(accessid,encodedPolicy,postSignature,
                    category,"https://"+bucket+"."+endpoint,String.valueOf(expireEndTime/1000),base64CallbackBody);
            return new Result<>(true,aliSign);
        } catch (UnsupportedEncodingException | JSONException e) {
            e.printStackTrace();
        }

        return new Result<>(false,null);
    }
}
