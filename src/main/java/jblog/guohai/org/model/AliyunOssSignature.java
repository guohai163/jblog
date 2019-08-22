package jblog.guohai.org.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AliyunOssSignature {

    private String accessid;

    private String policy;

    private String signature;

    private String dir;

    private String host;

    private String expire;

    private String callback;

    private String user;
}
