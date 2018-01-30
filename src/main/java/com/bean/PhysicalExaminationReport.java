package com.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaowuhen on 2018/1/19.
 */
public class PhysicalExaminationReport {
    /**
     * 汇总分析
     */
    private List<String> metaAnalysis;

    private User user;

    private String md5;

    private List<PhysicalExamination> physicalExamination = new ArrayList<>();

    public List<String> getMetaAnalysis() {
        return metaAnalysis;
    }

    public void setMetaAnalysis(List<String> metaAnalysis) {
        this.metaAnalysis = metaAnalysis;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<PhysicalExamination> getPhysicalExamination() {
        return physicalExamination;
    }

    public void setPhysicalExamination(List<PhysicalExamination> physicalExamination) {
        this.physicalExamination = physicalExamination;
    }

    public String getMd5() {
        return md5;
    }

    public void setMd5(String md5) {
        this.md5 = md5;
    }
}
