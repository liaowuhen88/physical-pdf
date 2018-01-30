package com.service.impl;

import com.requestBean.FileUpload;
import com.service.AnalysisHtmlService;
import org.apache.commons.io.IOUtils;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaowuhen on 2018/1/26.
 */
@Service
public class AnalysisHtmlServiceImpl implements AnalysisHtmlService {
    public static final String FILE_SEPARATOR = System.getProperty("file.separator");
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String pdfTempPath = System.getProperty("java.io.tmpdir");
    private String htmlPath = (pdfTempPath.endsWith(FILE_SEPARATOR) ? pdfTempPath : (pdfTempPath + FILE_SEPARATOR)) + "HTML" + FILE_SEPARATOR;

    @Override
    public List<List<List<String>>> getWholeHtmlContext(String pdfPath) throws Exception {
        File file = new File(pdfPath);
        return getWholeHtmlContext(file);
    }

    @Override
    public List<List<List<String>>> getWholeHtmlContext(File file) throws Exception {
        //Document document = new Document();
        String htmlType = getHtmlType(file);
        Parser myParser = new Parser(file.toString());
        myParser.setEncoding(htmlType);
        //设置标签，所有有用的消息都是在table中的
        NodeFilter tableFilter = new TagNameFilter("table");
        NodeList tableNodes = myParser.extractAllNodesThatMatch(tableFilter);

        List<List<List<String>>> table1 = new ArrayList<>();
        //遍历所有符合的table
        for (int tableIndex = 0; tableIndex < tableNodes.size(); tableIndex++) {
            List<List<String>> tr = new ArrayList<>();
            table1.add(tr);
            //先转换为HTML文件
            String tableHtml = tableNodes.elementAt(tableIndex).toHtml();
            //行标签
            NodeFilter trFilter = new TagNameFilter("tr");
            Parser trParser = Parser.createParser(tableHtml, htmlType);
            NodeList trNodes = trParser.extractAllNodesThatMatch(trFilter);

            //遍历所有符合的行
            for (int trIndex = 0; trIndex < trNodes.size(); trIndex++) {
                List<String> td = new ArrayList<>();
                tr.add(td);
                String trHtml = trNodes.elementAt(trIndex).toHtml();
                //列标签
                NodeFilter tdFilter = new TagNameFilter("td");
                Parser tdParser = Parser.createParser(trHtml, htmlType);
                NodeList tdNodes = tdParser.extractAllNodesThatMatch(tdFilter);

                for (int tdIndex = 0; tdIndex < tdNodes.size(); tdIndex++) {
                    String orignContext = tdNodes.elementAt(tdIndex).toPlainTextString();
                    String realContext = orignContext.replaceAll("[\\s]|&nbsp;|(\\.\\.\\.)", "").replaceAll("�O", "m2").replaceAll("�D", "-");
                    td.add(realContext);
                }
            }
        }
        //document.dispose();

        return table1;
    }

    @Override
    public List<List<List<String>>> getWholeHtmlContext(InputStream inputStream, FileUpload fileUpload) throws Exception {
        String direct = htmlPath;
        File dir = new File(direct);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String fileName = direct + fileUpload.getIdCard() + ".html";
        logger.info("dir:[{}]", fileName);
        File file = new File(fileName);
        if (!file.exists()) {
            file.createNewFile();
        }
        OutputStream fileOutputStream = new FileOutputStream(file);

        IOUtils.copyLarge(inputStream, fileOutputStream);

        fileOutputStream.close();

        return getWholeHtmlContext(file);
    }

    private String getHtmlType(File file) throws Exception {
        FileInputStream fin = new FileInputStream(file);
        return getHtmlType(fin);
    }

    private String getHtmlType(InputStream inputStream) throws Exception {
        String htmlType;
        byte[] b = new byte[2];
        inputStream.read(b);
        //UTF-LE文件格式判断
        if (b[0] == -1 && b[1] == -2) {
            htmlType = "UTF-16LE";
        } else {
            htmlType = "gb2312";
        }
        inputStream.close();
        return htmlType;
    }
}
