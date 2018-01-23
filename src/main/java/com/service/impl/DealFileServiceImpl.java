package com.service.impl;

import com.service.AnalysisPdfService;
import com.service.DealFileService;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.TagNameFilter;
import org.htmlparser.util.NodeList;
import org.icepdf.core.pobjects.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaowuhen on 2018/1/23.
 */
@Service
public class DealFileServiceImpl implements DealFileService {
    @Autowired
    private AnalysisPdfService analysisPdfService;

    @Override
    public List<List<List<String>>> getWholeCiMingHtmlContext(String fileName) throws Exception {
        File file = new File(fileName);
        Document document = new Document();
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
        document.dispose();
        return table1;
    }

    // pdf
    @Override
    public List<List<String>> getWholeMeiNianPdfContext(String pdfPath) throws Exception {
        List<List<String[]>> list = analysisPdfService.getWholePdfContext(pdfPath);

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
    public List<List<List<String>>> getFirstPageContext(String fileName) throws Exception {
        return null;
    }


    private String getHtmlType(File file) throws Exception {

        String htmlType;
        FileInputStream fin = new FileInputStream(file);
        byte[] b = new byte[2];
        fin.read(b);
        //UTF-LE文件格式判断
        if (b[0] == -1 && b[1] == -2) {
            htmlType = "UTF-16LE";
        } else {
            htmlType = "gb2312";
        }
        fin.close();
        return htmlType;
    }

}
