package com.service;

import com.bean.PhysicalExamination;
import com.bean.PhysicalExaminationReport;
import com.bean.User;

import java.util.List;

/**
 * Created by liaowuhen on 2018/1/19.
 */
public interface DelaDateServiceCiMing extends DealDateService {
    PhysicalExaminationReport dealData(List<List<List<String>>> list);
    /**
     * 是否是体检项
     *
     * @param table
     * @return
     */
    boolean isPhysicalExamination(List<List<String>> table);

    /**
     * 是否是汇总分析
     *
     * @param table
     * @return
     */
    boolean isMetaAnalysis(List<List<String>> table);

    /**
     * 是否是用户信息
     *
     * @param table
     * @return
     */
    boolean isUserMessage(List<List<String>> table);

    /**
     * 处理用户信息
     *
     * @param table
     * @return
     */
    User dealUserMessage(List<List<String>> table);

    /**
     * 处理汇总分析
     *
     * @param table
     * @return
     */
    List<String> dealMetaAnalysis(List<List<String>> table);

    /**
     * 处理体检项
     *
     * @param table
     * @return
     */
    PhysicalExamination dealPhysicalExamination(List<List<String>> table);
}
