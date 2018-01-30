package com.service.impl;

import com.requestBean.FileUpload;
import com.service.AnalysisHtmlService;
import com.service.AnalysisPdfService;
import com.service.DealFileService;
import com.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

/**
 * Created by liaowuhen on 2018/1/23.
 */
@Service
public class DealFileServiceImpl implements DealFileService {
    @Autowired
    private AnalysisPdfService analysisPdfService;
    @Autowired
    private AnalysisHtmlService analysisHtmlService;

    @Override
    public String getMd5ByFile(String pdfPath) throws Exception {

        File file = new File(pdfPath);

        String md5Key = MD5Util.getMd5ByFile(file);

        String md5KeyLowerCase = md5Key.toLowerCase();

        return md5KeyLowerCase;
    }

    @Override
    public List<List<List<String>>> getWholeCiMingHtmlContext(String fileName) throws Exception {
        return analysisHtmlService.getWholeHtmlContext(fileName);
    }

    @Override
    public List<List<List<String>>> getWholeCiMingHtmlContext(InputStream inputStream, FileUpload fileUpload) throws Exception {
        return analysisHtmlService.getWholeHtmlContext(inputStream, fileUpload);
    }


    // pdf
    @Override
    public List<List<String>> getWholeMeiNianPdfContext(String pdfPath) throws Exception {
        List<List<String[]>> list = analysisPdfService.getWholePdfContext(pdfPath);

        List<List<String>> aa = analysisPdfService.changeListToString(list);

        return aa;
    }

    @Override
    public List<List<String>> getWholeMeiNianPdfContext(InputStream inputStream) throws Exception {
        if (null == inputStream) {
            throw new RuntimeException("inputStream is null");
        }
        List<List<String[]>> list = analysisPdfService.getWholePdfContext(inputStream);

        List<List<String>> aa = analysisPdfService.changeListToString(list);
        return aa;
    }

    @Override
    public List<List<String>> getWholeAiKangPdfContext(String pdfPath) throws Exception {
        List<List<String[]>> list = analysisPdfService.getWholePdfContext(pdfPath);

        List<List<String>> aa = analysisPdfService.changeListToString(list);

        return aa;
    }

    @Override
    public List<List<String>> getWholeAiKangPdfContext(InputStream inputStream) throws Exception {
        List<List<String[]>> list = analysisPdfService.getWholePdfContext(inputStream);

        List<List<String>> aa = analysisPdfService.changeListToString(list);

        return aa;
    }

    @Override
    public List<List<List<String>>> getFirstPageContext(String fileName) throws Exception {
        return null;
    }




}
