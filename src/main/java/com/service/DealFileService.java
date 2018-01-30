package com.service;

import com.requestBean.FileUpload;

import java.io.InputStream;
import java.util.List;

/**
 * Created by liaowuhen on 2018/1/23.
 */
public interface DealFileService {
    /**
     *
     * @param pdfPath
     * @return
     * @throws Exception
     */
    String getMd5ByFile(String pdfPath) throws Exception;

    /**
     * 获取慈铭所有内容
     *
     * @param
     * @return
     */
    List<List<List<String>>> getWholeCiMingHtmlContext(String pdfPath) throws Exception;

    /**
     * 获取慈铭所有内容
     *
     * @param
     * @return
     */
    List<List<List<String>>> getWholeCiMingHtmlContext(InputStream inputStream, FileUpload fileUpload) throws Exception;

    /**
     * 获取美年所有内容
     *
     * @param
     * @return
     */
    List<List<String>> getWholeMeiNianPdfContext(String pdfPath) throws Exception;

    /**
     * 获取美年所有内容
     *
     * @param
     * @return
     */
    List<List<String>> getWholeMeiNianPdfContext(InputStream inputStream) throws Exception;


    /**
     * 获取爱康所有内容
     *
     * @param
     * @return
     */
    List<List<String>> getWholeAiKangPdfContext(String pdfPath) throws Exception;

    /**
     * 获取爱康所有内容
     *
     * @param
     * @return
     */
    List<List<String>> getWholeAiKangPdfContext(InputStream inputStream) throws Exception;



    /**
     * 获取所有内容
     *
     * @param
     * @return
     */

    List<List<List<String>>> getFirstPageContext(String pdfPath) throws Exception;

}
