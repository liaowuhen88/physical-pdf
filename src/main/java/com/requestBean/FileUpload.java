package com.requestBean;

/**
 * Created by liaowuhen on 2018/1/26.
 */
public class FileUpload {
    public static final String CiMing = "CiMing"; // 慈铭
    public static final String MeiNian = "MeiNian"; // 美年
    public static final String AiKang = "AiKang"; // 爱康
    private String type;

    private String downUrl;

    private String fileName;

    private String idCard;

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getDownUrl() {
        return downUrl;
    }

    public void setDownUrl(String downUrl) {
        this.downUrl = downUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
