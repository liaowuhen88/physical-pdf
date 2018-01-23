package com.service.impl;

import com.alibaba.fastjson.JSON;
import com.bean.PhysicalExamination;
import com.bean.PhysicalExaminationIteam;
import com.bean.PhysicalExaminationReport;
import com.bean.User;
import com.service.DelaDateServiceAiKang;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by liaowuhen on 2018/1/23.
 */
@Service
public class DelaDateServiceAiKangImpl implements DelaDateServiceAiKang {
    private static Set<String> cmSets = new HashSet();
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @PostConstruct
    void init() {
        cmSets.add("一般项目检查");
        cmSets.add("内科");
        cmSets.add("外科");
        cmSets.add("眼科");
        cmSets.add("血常规");
        cmSets.add("尿常规");
        cmSets.add("实验室检查");
        cmSets.add("心电图室");
        cmSets.add("放射科");

    }

    @Override
    public PhysicalExaminationReport dealData(List<List<String>> list) {
        PhysicalExaminationReport per = new PhysicalExaminationReport();
        List<PhysicalExamination> pes = dealPhysicalExamination(list);
        User user = dealUserMessage(list);
        per.setPhysicalExamination(pes);
        per.setUser(user);
        return per;
    }

    @Override
    public boolean isPhysicalExamination(List<List<String>> table) {
        return false;
    }

    @Override
    public boolean isMetaAnalysis(List<List<String>> table) {
        return false;
    }

    @Override
    public boolean isUserMessage(List<List<String>> table) {
        return false;
    }

    @Override
    public User dealUserMessage(List<List<String>> table) {
        return null;
    }

    @Override
    public List<String> dealMetaAnalysis(List<List<String>> table) {
        return null;
    }

    @Override
    public List<PhysicalExamination> dealPhysicalExamination(List<List<String>> list) {
        List<PhysicalExamination> pes = new ArrayList<>();
        if (!CollectionUtils.isEmpty(list)) {
            boolean isMetaAnalysis = false;
            PhysicalExamination pe = new PhysicalExamination();
            for (int i = 0; i < list.size(); i++) {
                List<String> li = list.get(i);
                if (!CollectionUtils.isEmpty(li)) {
                    if (isMetaAnalysis) {
                        if (checkStartPhysicalExamination(li)) {
                            // save
                            pes.add(pe);
                            String pro = li.get(0);
                            pro = pro.replaceAll("·", "");
                            pe = new PhysicalExamination();
                            pe.setExaminationType(pro);
                            isMetaAnalysis = true;
                        } else {
                            if (isPhysicalExaminationIteam(li)) {
                                PhysicalExaminationIteam pei = dealPhysicalExaminationIteam(li);
                                pe.getList().add(pei);
                            }

                            logger.info("-----------{}", JSON.toJSONString(li));
                        }
                    } else {
                        // 判断是否是体检细项
                        isMetaAnalysis = checkStartPhysicalExamination(li);
                        if (isMetaAnalysis) {
                            String pro = li.get(0);
                            pro = pro.replaceAll("·", "");
                            pe.setExaminationType(pro);
                        }
                    }
                }
                //logger.info(JSON.toJSONString(li));
            }
        }

        return pes;
    }


    private PhysicalExaminationIteam dealPhysicalExaminationIteam(List<String> li) {
        PhysicalExaminationIteam pei = new PhysicalExaminationIteam();
        if (li.size() == 2) {
            pei.setProject(li.get(0));
            pei.setResutl(li.get(1));
        } else if (li.size() == 3) {
            pei.setProject(li.get(0));
            pei.setResutl(li.get(1));
            pei.setUnit(li.get(2));
        } else if (li.size() == 4) {
            pei.setProject(li.get(0));
            pei.setResutl(li.get(1));
            pei.setUnit(li.get(2));
            pei.setRange(li.get(3));
        } else if (li.size() == 5) {
            pei.setProject(li.get(0));
            pei.setResutl(li.get(2));
            pei.setRange(li.get(3));
            pei.setUnit(li.get(4));
        }
        return pei;
    }

    private boolean checkStartPhysicalExamination(List<String> li) {
        if (li.size() == 2) {
            String str = li.get(0);
            str = str.replaceAll("·", "");
            if (!StringUtils.isEmpty(str) && cmSets.contains(str)) {
                logger.info("************************* isMetaAnalysis true {}", JSON.toJSONString(li));
                return true;
            }
        } else if (li.size() == 1) {
            if (li.get(0).equals("医学名词科普知识")) {
                return true;
            }
        }
        return false;
    }


    private boolean isPhysicalExaminationIteam(List<String> li) {
        if (!CollectionUtils.isEmpty(li)) {
            if ("检查项目".equals(li.get(0))) {
                return false;
            }
            if (li.size() < 2) {
                return false;
            }
        }
        return true;
    }

}
