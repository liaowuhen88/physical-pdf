package com.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaowuhen on 2018/1/19.
 * <p/>
 * 检查类别
 */
public class PhysicalExamination {
    private String examinationType;
    private List<PhysicalExaminationIteam> list = new ArrayList<>();

    public String getExaminationType() {
        return examinationType;
    }

    public void setExaminationType(String examinationType) {
        this.examinationType = examinationType;
    }

    public List<PhysicalExaminationIteam> getList() {
        return list;
    }

    public void setList(List<PhysicalExaminationIteam> list) {
        this.list = list;
    }
}
