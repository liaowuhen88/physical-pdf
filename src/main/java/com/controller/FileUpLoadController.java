package com.controller;

import com.alibaba.fastjson.JSON;
import com.bean.PhysicalExaminationReport;
import com.bean.Response;
import com.requestBean.FileUpload;
import com.service.DealDateService;
import com.service.DealFileService;
import com.utils.HttpClientUtils;
import com.utils.SpringContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by liaowuhen on 2018/1/26.
 */
@RestController
@RequestMapping(value = "fileUpload")
public class FileUpLoadController {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private DealFileService dealFileService;

    /**
     *
     */
    @RequestMapping(value = "upload", method = RequestMethod.POST)
    public void getBrokerMenuList(MultipartFile multipartFile) {
        try {
            InputStream inputStream = multipartFile.getInputStream();
            String fileName = multipartFile.getName();
            List<List<String>> li = dealFileService.getWholeMeiNianPdfContext(inputStream);
            logger.info(JSON.toJSONString(li));

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     */
    @RequestMapping(value = "uploadUrl", method = RequestMethod.POST)
    public Response getBrokerMenuList(FileUpload fileUpload) {

        PhysicalExaminationReport pr;
        Response response = new Response();
        InputStream inputStream = null;
        try {
            Long start = System.currentTimeMillis();
            String downUrl = fileUpload.getDownUrl();
            //String downUrl = fileUpload.getDownUrl().replace("/test/","/online/");
            logger.info("fileUpload: {}-----downUrl:{}", JSON.toJSONString(fileUpload), downUrl);
            inputStream = HttpClientUtils.downByUrl(downUrl);

            if (null == inputStream) {
                throw new RuntimeException("inputStream is null");
            }
            int size = inputStream.available();
            logger.info("inputStream.available(): {} ", size);
            DealDateService dealDateService = SpringContextUtil.getBean("delaDateService" + fileUpload.getType() + "Impl");
            if (null != dealDateService) {
                pr = dealDateService.dealData(inputStream, fileUpload);
            } else {
                throw new RuntimeException("other type [" + fileUpload.getType() + "]");
            }
            Long end = System.currentTimeMillis();
            logger.info("inputStream.available(): {}-------time:{} ", size + "", end - start);
            logger.info("pr: {}", JSON.toJSONString(pr));
            response.setSuccess(true);
            response.setData(pr);
            response.setCode(200);
        } catch (Exception e) {
            logger.error("error", e);
            response.setSuccess(false);
            response.setMsg(e.getMessage());
            response.setCode(400);
        } finally {
            if (null != inputStream) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    logger.error("error", e);
                }
            }
        }

        return response;
    }

    /**
     *
     */
    @RequestMapping(value = "test")
    public String getBrokerMenuList() {
        return "ok";
    }
}
