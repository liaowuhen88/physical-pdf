package com.service;

import com.bean.PhysicalExaminationReport;
import com.requestBean.FileUpload;

import java.io.InputStream;

/**
 * Created by liaowuhen on 2018/1/30.
 */
public interface DealDataService {
    PhysicalExaminationReport dealData(InputStream inputStream, FileUpload fileUpload) throws Exception;

}
