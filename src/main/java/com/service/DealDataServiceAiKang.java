package com.service;

import com.bean.PhysicalExamination;
import com.bean.PhysicalExaminationReport;
import com.bean.User;
import com.requestBean.FileUpload;

import java.util.List;

/**
 * Created by liaowuhen on 2018/1/19.
 * <p/>
 * 爱康
 */
public interface DealDataServiceAiKang extends DealDataService {
    PhysicalExaminationReport dealData(List<List<String>> list, FileUpload fileUpload);

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
    boolean isMetaAnalysis(List<String> table);

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
    List<PhysicalExamination> dealPhysicalExamination(List<List<String>> table);
}
