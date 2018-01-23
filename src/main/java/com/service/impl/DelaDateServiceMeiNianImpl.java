package com.service.impl;

import com.alibaba.fastjson.JSON;
import com.bean.PhysicalExamination;
import com.bean.PhysicalExaminationIteam;
import com.bean.PhysicalExaminationReport;
import com.bean.User;
import com.service.DelaDateServiceMeiNian;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaowuhen on 2018/1/23.
 */
@Service
public class DelaDateServiceMeiNianImpl implements DelaDateServiceMeiNian {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

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
    public boolean isPhysicalExamination(List<String> table) {
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
    public User dealUserMessage(List<List<String>> list) {
        User user = new User();
        if (!CollectionUtils.isEmpty(list)) {
            user.setIdCard(list.get(2).get(1));
            user.setAge(list.get(3).get(1));
            user.setName(list.get(0).get(0));
        }
        return user;
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
                            pe = new PhysicalExamination();
                            pe.setExaminationType(li.get(0));
                            isMetaAnalysis = true;
                        } else {
                            if (isPhysicalExaminationIteam(li)) {
                                PhysicalExaminationIteam pei = dealPhysicalExaminationIteam(li);
                                pe.getList().add(pei);
                            }

                            //logger.info("-----------{}",JSON.toJSONString(li));
                        }
                    } else {
                        // 判断是否是体检细项
                        isMetaAnalysis = checkStartPhysicalExamination(li);
                        if (isMetaAnalysis) {
                            pe.setExaminationType(li.get(0));
                        }
                    }
                }
                logger.info(JSON.toJSONString(li));
            }
        }

        return pes;
    }

    private boolean checkStartPhysicalExamination(List<String> li) {
        if (li.size() == 2) {
            String str = li.get(1);
            if (!StringUtils.isEmpty(str) && str.contains("医生")) {
                logger.info("************************* isMetaAnalysis true {}", JSON.toJSONString(li));
                return true;
            }
        }
        return false;
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
        }
        return pei;
    }

    private boolean isPhysicalExaminationIteam(List<String> li) {
        if (!CollectionUtils.isEmpty(li)) {
            if ("检查项目名称".equals(li.get(0))) {
                return false;
            }

            if (li.size() < 2) {
                return false;
            }
        }
        return true;
    }
}
