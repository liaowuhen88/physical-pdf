package com.service.impl;

import com.alibaba.fastjson.JSON;
import com.bean.PhysicalExamination;
import com.bean.PhysicalExaminationIteam;
import com.bean.PhysicalExaminationReport;
import com.bean.User;
import com.requestBean.FileUpload;
import com.service.DealFileService;
import com.service.DelaDateServiceCiMing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by liaowuhen on 2018/1/19.
 */

@Service
public class DelaDateServiceCiMingImpl implements DelaDateServiceCiMing {
    private static Set<String> cmSets = new HashSet();
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DealFileService dealFileService;
    @PostConstruct
    void init() {
        cmSets.add("一般检查");
        cmSets.add("内科");
        cmSets.add("外科");
        cmSets.add("眼科");
        cmSets.add("耳鼻喉科");
        cmSets.add("口腔科");
        cmSets.add("腹部超声");
        cmSets.add("盆腔超声");
        cmSets.add("血常规");
        cmSets.add("生化检验");
        cmSets.add("微量元素");
        cmSets.add("肿瘤检测");
        cmSets.add("尿常规");
        cmSets.add("心电图");
        cmSets.add("胸部摄片");
        cmSets.add("颈椎片");

    }

    @Override
    public PhysicalExaminationReport dealData(List<List<List<String>>> tables) {
        PhysicalExaminationReport pr = new PhysicalExaminationReport();
        if (!CollectionUtils.isEmpty(tables)) {
            for (int i = 0; i < tables.size(); i++) {
                List<List<String>> table = tables.get(i);
                if (!CollectionUtils.isEmpty(table)) {
                    logger.info("--------------start[" + i + "]--------------");
                    if (isPhysicalExamination(table)) {
                        PhysicalExamination pe = dealPhysicalExamination(table);
                        pr.getPhysicalExamination().add(pe);
                    } else if (isMetaAnalysis(table)) {
                        List<String> metaAnalysis = dealMetaAnalysis(table);
                        pr.setMetaAnalysis(metaAnalysis);
                    } else if (isUserMessage(table)) {
                        User user = dealUserMessage(table);
                        pr.setUser(user);
                    } else {
                        for (List<String> tr : table) {
                            if (!CollectionUtils.isEmpty(tr)) {
                                logger.info(JSON.toJSONString(tr));
                            }
                        }
                    }
                    logger.info("-------------end[" + i + "]---------------");
                }

            }
        }
        return pr;
    }

    @Override
    public PhysicalExaminationReport dealData(InputStream inputStream, FileUpload fileUpload) throws Exception {
        List<List<List<String>>> list = dealFileService.getWholeCiMingHtmlContext(inputStream, fileUpload);
        return dealData(list);
    }

    @Override
    public boolean isPhysicalExamination(List<List<String>> table) {
        if (!CollectionUtils.isEmpty(table) && table.size() > 1) {
            for (int i = 0; i < table.size(); i++) {
                List<String> tr = table.get(i);
                if (i == 0) {
                    if (!CollectionUtils.isEmpty(tr)) {
                        if (cmSets.contains(tr.get(0))) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean isMetaAnalysis(List<List<String>> table) {
        if (!CollectionUtils.isEmpty(table) && table.size() > 1) {
            for (int i = 0; i < table.size(); i++) {
                List<String> tr = table.get(i);
                if (i == 0) {
                    if (!CollectionUtils.isEmpty(tr)) {
                        if ("汇总分析".equals(tr.get(0))) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    @Override
    public boolean isUserMessage(List<List<String>> table) {
        if (!CollectionUtils.isEmpty(table) && table.size() > 1) {
            for (int i = 0; i < table.size(); i++) {
                List<String> tr = table.get(i);
                tr.contains("姓名");
                return true;
            }
        }
        return false;
    }

    @Override
    public User dealUserMessage(List<List<String>> table) {
        User user = new User();
        if (!CollectionUtils.isEmpty(table)) {
            for (int i = 0; i < table.size(); i++) {
                List<String> tr = table.get(i);
                if (!CollectionUtils.isEmpty(tr)) {
                    //logger.info(JSON.toJSONString(tr));
                    boolean flag = true;
                    if (i == 0 && tr.size() == 8) {
                        user.setName(tr.get(1));
                        user.setSex(tr.get(3));
                        user.setBirthDay(tr.get(5));
                        user.setAge(tr.get(7));
                    } else if (i == 1 && tr.size() == 8) {
                        user.setIdCard(tr.get(5));
                    } else {
                        flag = false;
                    }
                    if (!flag) {
                        logger.info("不满足条件数据 {}", JSON.toJSONString(tr));
                    }
                }
            }
        }
        return user;
    }

    @Override
    public List<String> dealMetaAnalysis(List<List<String>> table) {
        List<String> list = new ArrayList<>();
        if (!CollectionUtils.isEmpty(table)) {
            for (int i = 0; i < table.size(); i++) {
                List<String> tr = table.get(i);
                if (!CollectionUtils.isEmpty(tr)) {
                    //logger.info(JSON.toJSONString(tr));
                    boolean flag = false;
                    if (tr.size() == 1 && !StringUtils.isEmpty(tr.get(0))) {
                        list.add(tr.get(0));
                        flag = true;
                    }
                    if (!flag) {
                        logger.info("不满足条件数据 {}", JSON.toJSONString(tr));
                    }
                }
            }
        }

        return list;
    }

    @Override
    public PhysicalExamination dealPhysicalExamination(List<List<String>> table) {
        PhysicalExamination pe = new PhysicalExamination();
        if (!CollectionUtils.isEmpty(table)) {
            for (int i = 0; i < table.size(); i++) {
                List<String> tr = table.get(i);

                if (!CollectionUtils.isEmpty(tr)) {
                    //logger.info(JSON.toJSONString(tr));
                    if (i == 0) {
                        pe.setExaminationType(tr.get(0));
                    } else if (i > 1) {
                        PhysicalExaminationIteam pei = new PhysicalExaminationIteam();
                        boolean flag = false;
                        if (tr.size() == 2) {
                            pei.setProject(tr.get(0));
                            pei.setResutl(tr.get(1));
                            flag = true;
                        } else if (tr.size() == 5) {
                            pei.setProject(tr.get(0));
                            pei.setResutl(tr.get(1));
                            pei.setUnit(tr.get(3));
                            pei.setRange(tr.get(4));
                            flag = true;
                        }

                        if (flag) {
                            //logger.info(JSON.toJSONString(pei));
                            pe.getList().add(pei);
                        } else {
                            logger.info("不满足条件数据 {}", JSON.toJSONString(tr));
                        }

                    }
                }


            }
        }

        return pe;
    }
}
