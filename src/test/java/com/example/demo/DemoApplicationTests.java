package com.example.demo;

import com.DemoApplication;
import com.alibaba.fastjson.JSON;
import com.bean.PhysicalExaminationReport;
import com.requestBean.FileUpload;
import com.service.*;
import com.utils.HttpClientUtils;
import com.utils.SpringContextUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@EnableAutoConfiguration
@SpringBootTest(classes = DemoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringRunner.class)
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
    public void dealDateService() throws Exception {
        String name = "MeiNian";
        String name1 = "MeiNian";
        String name2 = "MeiNian";

        DealDateService dealDateService = SpringContextUtil.getBean("delaDateService" + name + "Impl");

        dealDateService = SpringContextUtil.getBean("delaDateService" + name1 + "Impl");

        dealDateService = SpringContextUtil.getBean("delaDateService" + name2 + "Impl");

        logger.info(dealDateService.toString());
    }


    @Test
    public void getWholeMeiNianContext() throws Exception {
        String fileName = "C:\\Users\\liaowuhen\\Desktop\\7718003213_张婷婷.pdf";
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
        String md5 = dealFileService.getMd5ByFile(fileName);
        FileUpload fileUpload = new FileUpload();
        PhysicalExaminationReport pr = delaDateServiceAiKang.dealData(tables, fileUpload);

        logger.info(JSON.toJSONString(pr));
    }

    @Test
    public void getWholeAiKangPdfContextInputStream() throws Exception {
        String downUrl = "http://bj-bdy-private.oss-cn-beijing.aliyuncs.com/online%2Fzip%2F2016-09-13%2FREPORT_CASE_ID_2065.pdf?OSSAccessKeyId=obTuyP8GflR8U3nO&Expires=1517318865&Signature=XdybgiIReFjealjGHGIvf1flqPg%3D";
        InputStream inputStream = HttpClientUtils.downByUrl(downUrl);
        List<List<String>> tables = dealFileService.getWholeAiKangPdfContext(inputStream);
        FileUpload fileUpload = new FileUpload();
        PhysicalExaminationReport pr = delaDateServiceAiKang.dealData(tables, fileUpload);

        logger.info(JSON.toJSONString(pr));
    }

    @Test
    public void downUrl() throws Exception {
        InputStream inputStream = HttpClientUtils.downByUrl("http://bj-bdy-private.oss-cn-beijing.aliyuncs.com/dev%2FREPORT_CASE_ID_12290_ORDER_ID_18252_%E6%9D%8E%E5%BB%BA%E5%85%B4.pdf?OSSAccessKeyId=obTuyP8GflR8U3nO&Expires=1516951801&Signature=9AHbumoy%2FDVngHFaVaLGx9bvIaY%3D");

        logger.info("abc");
    }
}
