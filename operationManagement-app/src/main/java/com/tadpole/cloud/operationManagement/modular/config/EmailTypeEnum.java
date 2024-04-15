package com.tadpole.cloud.operationManagement.modular.config;

/**
 * 邮箱类型枚举类
 */
public enum EmailTypeEnum {


    Gmail("Gmail", "smtp.gmail.com", "美国节点", true),
    阿里云_新加坡("aliyun", "smtpdm-ap-southeast-1.aliyun.com", "新加坡节点", true),
    阿里云_澳大利亚("aliyun", "smtpdm-ap-southeast-1.aliyun.com", "澳大利亚节点", true),
    QQ邮箱("QQ邮箱", "smtp.qq.com", "中国节点", true),
    腾讯企业邮箱("腾讯企业邮箱", "smtp.exmail.qq.com", "中国节点", true),
    WebMail邮箱("WebMail邮箱", "mail.mailive.cn", "中国节点", true),
    一八九邮箱("189邮箱", "smtp.189.cn", "中国节点", true),
    一六三邮箱("163邮箱", "smtp.163.com", "中国节点", true);

    

    private String emailType;
    private String emailSmtp;
    private String serverPort;
    private Boolean ssl;

    EmailTypeEnum(String emailType, String emailSmtp, String serverPort, Boolean ssl) {
        this.emailType = emailType;
        this.emailSmtp = emailSmtp;
        this.serverPort = serverPort;
        this.ssl = ssl;
    }

    public String getEmailType() {
        return emailType;
    }

    public void setEmailType(String emailType) {
        this.emailType = emailType;
    }

    public String getEmailSmtp() {
        return emailSmtp;
    }

    public void setEmailSmtp(String emailSmtp) {
        this.emailSmtp = emailSmtp;
    }

    public String getServerPort() {
        return serverPort;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }

    public Boolean getSsl() {
        return ssl;
    }

    public void setSsl(Boolean ssl) {
        this.ssl = ssl;
    }
}
