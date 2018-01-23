package com.example.demo;

import com.alibaba.fastjson.JSON;
import com.bean.PhysicalExaminationReport;
import com.service.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AnalysisPdfService analysisPdfService;

    @Autowired
    private DelaDateServiceCiMing delaDateService;

    @Autowired
    private DelaDateServiceMeiNian delaDateServiceMeiNian;

    @Autowired
    private DelaDateServiceAiKang delaDateServiceAiKang;

    @Autowired
    private DealFileService dealFileService;

    @Test
    public void contextLoads() throws IOException {
        String fileName = "C:\\Users\\liaowuhen\\Desktop\\REPORT_CASE_ID_15663_ORDER_ID_20747_杨振.pdf";
        List<List<String[]>> list = analysisPdfService.getWholePdfContext(fileName);

        List<List<String>> aa = analysisPdfService.changeListToString(list);
        logger.info(JSON.toJSONString(aa));
    }


    @Test
    public void getWholeCiMingPdfContext() throws Exception {
        String fileName = "C:\\Users\\liaowuhen\\Desktop\\REPORT_CASE_ID_15663_ORDER_ID_20747_杨振.pdf";
        //dealFileService.getWholeCiMingPdfContext(new FileInputStream(fileName));

    }


    //

    @Test
    public void getWholeCiMingHtmlContext() throws Exception {
        String fileName = "C:\\Users\\liaowuhen\\Desktop\\222.html";
        List<List<List<String>>> tables = dealFileService.getWholeCiMingHtmlContext(fileName);

        PhysicalExaminationReport pr = delaDateService.dealData(tables);

        logger.info(JSON.toJSONString(pr));
    }


    @Test
    public void getWholeMeiNianContext() throws Exception {
        String fileName = "C:\\Users\\liaowuhen\\Desktop\\杨君.pdf";
        List<List<String>> tables = dealFileService.getWholeMeiNianPdfContext(fileName);

        PhysicalExaminationReport pr = delaDateServiceMeiNian.dealData(tables);

        logger.info(JSON.toJSONString(pr));
    }

    @Test
    public void getWholePdfContextOld() throws Exception {
        String fileName = "C:\\Users\\liaowuhen\\Desktop\\杨君.pdf";

        FileInputStream inputStream = new FileInputStream(fileName);

        analysisPdfService.getWholePdfContext(inputStream);

    }

    @Test
    public void getWholeAiKangPdfContext() throws Exception {
        String fileName = "C:\\Users\\liaowuhen\\Desktop\\362132198209125637.pdf";
        List<List<String>> tables = dealFileService.getWholeAiKangPdfContext(fileName);

        PhysicalExaminationReport pr = delaDateServiceAiKang.dealData(tables);


        logger.info(JSON.toJSONString(pr));
    }


}
