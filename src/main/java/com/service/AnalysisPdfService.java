package com.service;

import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * 处理pdf
 * Created by liaowuhen on 2018/1/19.
 */
public interface AnalysisPdfService {
    /**
     * 获取PDF第一页的内容，姓名、体检公司、身份证号 以及PDF格式
     *
     * @param pdfPath
     * @return
     */
    List<List<String[]>> tjUploadOssEntity(String pdfPath) throws IOException;

    List<List<String[]>> tjUploadOssEntity(InputStream inputStream) throws IOException;

    /**
     * 获取PDF所有内容
     *
     * @param pdfPath
     * @return
     */
    List<List<String[]>> getWholePdfContext(String pdfPath) throws IOException;

    /**
     * 获取PDF所有内容
     *
     * @param inputStream
     * @return
     */
    List<List<String[]>> getWholePdfContext(InputStream inputStream) throws IOException;

    /**
     * 获取PDF所有内容
     *
     * @param inputStream
     * @return
     */
    void getWholePdfContextOld(InputStream inputStream) throws IOException, PDFException, PDFSecurityException, InterruptedException;

    List<String> changeListStringArray2String(List<String[]> list);

    List<List<String>> changeListToString(List<List<String[]>> list);

    /**
     * 获取慈铭PDF所有内容
     *
     * 此方法不太好使
     * @param inputStream
     * @return
     */
    //void  getWholeCiMingPdfContext(InputStream inputStream) throws IOException, PDFException, PDFSecurityException, InterruptedException;
}
