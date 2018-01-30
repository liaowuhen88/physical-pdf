package com.service;

import com.requestBean.FileUpload;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * 处理pdf
 * Created by liaowuhen on 2018/1/19.
 */
public interface AnalysisHtmlService {

    /**
     * 获取PDF所有内容
     *
     * @param pdfPath
     * @return
     */
    List<List<List<String>>> getWholeHtmlContext(String pdfPath) throws Exception;

    /**
     * 获取PDF所有内容
     *
     * @param file
     * @return
     */
    List<List<List<String>>> getWholeHtmlContext(File file) throws Exception;

    /**
     * 获取PDF所有内容
     *
     * @param inputStream
     * @return
     */
    List<List<List<String>>> getWholeHtmlContext(InputStream inputStream, FileUpload fileUpload) throws Exception;

}
