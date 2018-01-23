package com.service.impl;

import com.alibaba.fastjson.JSON;
import com.itextpdf.awt.geom.Rectangle2D;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.ImageRenderInfo;
import com.itextpdf.text.pdf.parser.PdfReaderContentParser;
import com.itextpdf.text.pdf.parser.RenderListener;
import com.itextpdf.text.pdf.parser.TextRenderInfo;
import com.service.AnalysisPdfService;
import org.icepdf.core.exceptions.PDFException;
import org.icepdf.core.exceptions.PDFSecurityException;
import org.icepdf.core.pobjects.Document;
import org.icepdf.core.pobjects.PageTree;
import org.icepdf.core.pobjects.graphics.text.LineText;
import org.icepdf.core.pobjects.graphics.text.WordText;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liaowuhen on 2018/1/19.
 */
@Service
public class AnalysisPdfServiceImpl implements AnalysisPdfService {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private int i = 0;
    private Integer colArea = 20;
    private Integer rowArea = 5;
    private List<String[]> arrays = new ArrayList<String[]>();

   /* class MyRend implements  RenderListener{
        private List<MyRendItem> list = new ArrayList<>();
        @Override
        public void beginTextBlock() {
            list.clear();
            logger.info("beginTextBlock:list.clear()");
        }

        @Override
        public void renderText(TextRenderInfo renderInfo) {
            String text = renderInfo.getText(); // 整页内容
            Rectangle2D.Float boundingRectange = renderInfo
                    .getBaseline().getBoundingRectange();
            MyRendItem myRendItem = new MyRendItem( boundingRectange.x,boundingRectange.y,text,i);
            list.add(myRendItem);
            logger.info("myRendItem:{}",JSON.toJSONString(myRendItem));
        }

        @Override
        public void endTextBlock() {
            logger.info("endTextBlock:{}",JSON.toJSONString(list));
        }

        @Override
        public void renderImage(ImageRenderInfo renderInfo) {
            logger.info("renderImage");
        }
    }
    private  List<List<String[]>> getKeyWords(PdfReader pdfReader,Boolean wholeContextFlag) {
        try {
            //
            int pageNum = pdfReader.getNumberOfPages();
            PdfReaderContentParser pdfReaderContentParser = new PdfReaderContentParser(
                    pdfReader);
            i = 1;
            do {
                pdfReaderContentParser.processContent(i,new  MyRend());
                i++;
            }while (i<=pageNum&&wholeContextFlag);
        } catch (IOException e) {
           logger.error("error",e);
        }
        return changeSeq(clipProcess(arrays));

    }*/

    private List<List<String[]>> getKeyWords(PdfReader pdfReader, Boolean wholeContextFlag) {
        arrays.clear();
        try {
            //
            int pageNum = pdfReader.getNumberOfPages();
            PdfReaderContentParser pdfReaderContentParser = new PdfReaderContentParser(
                    pdfReader);
            i = 1;
            do {
                pdfReaderContentParser.processContent(i, new RenderListener() {
                    @Override
                    public void renderText(TextRenderInfo textRenderInfo) {
                        String text = textRenderInfo.getText(); // 整页内容
                        Rectangle2D.Float boundingRectange = textRenderInfo
                                .getBaseline().getBoundingRectange();
                        String[] resu = new String[4];
                        resu[0] = boundingRectange.x + "";
                        resu[1] = boundingRectange.y + "";
                        resu[2] = i + "";
                        resu[3] = text;
                        logger.info("x:" + resu[0] + "---y:" + resu[1] + "---conetnt:" + text + "---page:" + i);
                        arrays.add(resu);
                    }

                    @Override
                    public void renderImage(ImageRenderInfo arg0) {
                        logger.info("renderImage");
                    }

                    @Override
                    public void endTextBlock() {
                        logger.info("endTextBlock");
                    }

                    @Override
                    public void beginTextBlock() {
                        logger.info("beginTextBlock");
                    }


                });
                i++;
            } while (i <= pageNum && wholeContextFlag);
        } catch (IOException e) {
            logger.error("error", e);
        }
        return changeSeq(clipProcess(arrays));

    }

    private List<List<String[]>> clipProcess(List<String[]> arrays) {
        List<List<String[]>> listList = new ArrayList<List<String[]>>();
        Float[] floatsBefore = new Float[2];
        Float[] floatsAfter = new Float[2];
        String[] sbBefore = arrays.get(0);
        List<String[]> stringList = new ArrayList<String[]>();

        List<String[]> stringListNow = new ArrayList<String[]>();

        List<String[]> stringListFront = new ArrayList<String[]>();

        StringBuffer sb = new StringBuffer();
        Boolean sameItemFlag = false;
        floatsBefore[0] = Float.parseFloat(sbBefore[0].toString());
        floatsBefore[1] = Float.parseFloat(sbBefore[1].toString());

        sb.append(sbBefore[3]);
        String[] result = new String[11];
        result[0] = sbBefore[0].toString();
        result[1] = sbBefore[1].toString();
        for (int i = 1; i < arrays.size(); i++) {
            try {
                String[] sbAfter = arrays.get(i);
                floatsAfter[0] = Float.parseFloat(sbAfter[0].toString());
                floatsAfter[1] = Float.parseFloat(sbAfter[1].toString());
                if (colArea > Math.abs(floatsAfter[0] - floatsBefore[0]) && Math.abs(floatsBefore[1] - floatsAfter[1]) < rowArea) {
                    sb.append(sbAfter[3]);
                    floatsBefore[0] = floatsAfter[0];
                    floatsBefore[1] = floatsAfter[1];
                    result[5] = sbAfter[0].toString();
                    result[6] = sbAfter[1].toString();
                } else if (colArea < Math.abs(floatsAfter[0] - floatsBefore[0]) && Math.abs(floatsBefore[1] - floatsAfter[1]) < rowArea) {

                    result[2] = sb.toString();
                    stringListNow.add(result.clone());
                    floatsBefore[0] = floatsAfter[0];
                    floatsBefore[1] = floatsAfter[1];
                    result[0] = sbAfter[0].toString();
                    result[1] = sbAfter[1].toString();
                    result[2] = "";
                    result[3] = sbAfter[0].toString();
                    result[4] = sbAfter[1].toString();
                    result[5] = sbAfter[0].toString();
                    result[6] = sbAfter[1].toString();
                    result[7] = sbAfter[0].toString();
                    result[8] = sbAfter[1].toString();
                    result[9] = sbAfter[0].toString();
                    result[10] = sbAfter[1].toString();
                    sb.setLength(0);
                    sb.append(sbAfter[3]);

                } else if (Math.abs(floatsBefore[1] - floatsAfter[1]) > rowArea) {
                    result[2] = sb.toString();
                    stringListNow.add(result.clone());
                    if (stringListFront.isEmpty() || stringListNow.size() > 1 || Math.abs(Float.parseFloat(stringListFront.get(0)[1]) - Float.parseFloat(stringListNow.get(0)[1])) > 4 * rowArea) {
                        floatsBefore[0] = floatsAfter[0];
                        floatsBefore[1] = floatsAfter[1];
                        for (int index = 0; index < stringListNow.size(); index++) {
                            stringList.add(stringListNow.get(index));
                        }
                        listList.add(deepCopy(stringList));
                        stringList.clear();
                        sb.setLength(0);
                        sb.append(sbAfter[3]);
                        result[0] = sbAfter[0].toString();
                        result[1] = sbAfter[1].toString();
                        result[2] = "";
                        result[3] = sbAfter[0].toString();
                        result[4] = sbAfter[1].toString();
                        result[5] = sbAfter[0].toString();
                        result[6] = sbAfter[1].toString();
                        result[7] = sbAfter[0].toString();
                        result[8] = sbAfter[1].toString();
                        result[9] = sbAfter[0].toString();
                        result[10] = sbAfter[1].toString();
                        stringListFront.clear();
                        stringListFront.addAll(stringListNow);
                        stringListNow.clear();

                    } else {
                        String[] tempResult = stringListNow.get(0);
                        for (int frontIndex = 0; frontIndex < stringListFront.size(); frontIndex++) {
                            String[] frontString = stringListFront.get(frontIndex);
                            if (Float.parseFloat(frontString[0]) + 5 * colArea < Float.parseFloat(tempResult[0])
                                    && Float.parseFloat(frontString[1]) + rowArea < Float.parseFloat(tempResult[1])
                                    && 0 == frontIndex && 1 == stringListFront.size()) {
                                stringListFront.add(1, tempResult);
                                for (int index = 0; index < stringListFront.size(); index++) {
                                    stringList.add(stringListFront.get(index));
                                }
                                int size = stringList.size();
                                listList.set(listList.size() - 1, deepCopy(stringList));
                                stringList.clear();
                                result[0] = sbAfter[0].toString();
                                result[1] = sbAfter[1].toString();
                                result[2] = "";
                                result[3] = sbAfter[0].toString();
                                result[4] = sbAfter[1].toString();
                                result[5] = sbAfter[0].toString();
                                result[6] = sbAfter[1].toString();
                                result[7] = sbAfter[0].toString();
                                result[8] = sbAfter[1].toString();
                                result[9] = sbAfter[0].toString();
                                result[10] = sbAfter[1].toString();

                                sb.setLength(0);
                                floatsBefore[0] = floatsAfter[0];
                                floatsBefore[1] = floatsAfter[1];
                                sb.append(sbAfter[3]);

//                            stringListFront.addAll(stringListNow);
                                stringListNow.clear();
                                sameItemFlag = true;
                                break;
                            }

                            if (Float.parseFloat(frontString[0]) + colArea > Float.parseFloat(tempResult[0])
                                    && Float.parseFloat(frontString[0]) - colArea < Float.parseFloat(tempResult[0])
                                    && Float.parseFloat(frontString[1]) - 3 * rowArea < Float.parseFloat(tempResult[1])) {
                                frontString[0] = tempResult[0];
                                frontString[1] = tempResult[1];
                                frontString[2] += tempResult[2];
                                frontString[7] = tempResult[3];
                                frontString[8] = tempResult[4];
                                frontString[9] = tempResult[5];
                                frontString[10] = tempResult[6];

                                stringListFront.set(frontIndex, frontString);
                                for (int index = 0; index < stringListFront.size(); index++) {
                                    stringList.add(stringListFront.get(index));
                                }
                                listList.set(listList.size() - 1, deepCopy(stringList));
                                stringList.clear();
                                result[0] = sbAfter[0].toString();
                                result[1] = sbAfter[1].toString();
                                result[2] = "";
                                result[3] = sbAfter[0].toString();
                                result[4] = sbAfter[1].toString();
                                result[5] = sbAfter[0].toString();
                                result[6] = sbAfter[1].toString();
                                result[7] = sbAfter[0].toString();
                                result[8] = sbAfter[1].toString();
                                result[9] = sbAfter[0].toString();
                                result[10] = sbAfter[1].toString();
                                sb.setLength(0);
                                floatsBefore[0] = floatsAfter[0];
                                floatsBefore[1] = floatsAfter[1];
                                sb.append(sbAfter[3]);
                                stringListNow.clear();
                                sameItemFlag = true;
                                break;
                            }

                        }
                        if (sameItemFlag == false) {
                            for (int index = 0; index < stringListNow.size(); index++) {
                                stringList.add(stringListNow.get(index));
                            }
                            listList.add(deepCopy(stringList));
                            stringList.clear();
                            sb.setLength(0);
                            sb.append(sbAfter[3]);
                            result[0] = sbAfter[0].toString();
                            result[1] = sbAfter[1].toString();
                            result[2] = "";
                            result[3] = sbAfter[0].toString();
                            result[4] = sbAfter[1].toString();
                            result[5] = sbAfter[0].toString();
                            result[6] = sbAfter[1].toString();
                            result[7] = sbAfter[0].toString();
                            result[8] = sbAfter[1].toString();
                            result[9] = sbAfter[0].toString();
                            result[10] = sbAfter[1].toString();
                            floatsBefore[0] = floatsAfter[0];
                            floatsBefore[1] = floatsAfter[1];
                            stringListFront.clear();
                            stringListFront.addAll(stringListNow);
                            stringListNow.clear();
                        }
                        sameItemFlag = false;

                    }
                }

            } catch (Exception e) {
                logger.error("error", e);
            }
            if (arrays.size() - 1 == i) {
                try {
                    for (int index = 0; index < stringListNow.size(); index++) {
                        stringList.add(stringListNow.get(index));
                    }
                    result[2] = sb.toString();
                    stringList.add(result.clone());
                    listList.add(deepCopy(stringList));
                    stringList.clear();
                } catch (Exception e) {
                    logger.error("error", e);
                }
            }
        }
        return listList;
    }

    private List<List<String[]>> changeSeq(List<List<String[]>> lists) {
        List<List<String[]>> resultList = new ArrayList<List<String[]>>();

        for (int rowIndex = 5; rowIndex < lists.size(); rowIndex++) {
            List<String[]> rowList = lists.get(rowIndex);
            for (int colIndex = 0; colIndex < rowList.size() - 1; colIndex++) {
                for (int colTempIndex = 0; colTempIndex < rowList.size() - colIndex - 1; colTempIndex++) {
                    if (Float.parseFloat(rowList.get(colTempIndex)[3]) > Float.parseFloat(rowList.get(colTempIndex + 1)[3]) + 5) {
                        String[] temStringArraySmaller = rowList.get(colTempIndex);
                        String[] temStringArrayBigger = rowList.get(colTempIndex + 1);
                        rowList.set(colTempIndex, temStringArrayBigger);
                        rowList.set(colTempIndex + 1, temStringArraySmaller);
                    }
                }
            }
        }
        for (int rowIndex = 5; rowIndex < lists.size() - 1; rowIndex++) {
            List<String[]> rowList = lists.get(rowIndex);
            if (1 == rowList.size()) {
                //最上行坐标
                Float maxHigh = 0.0f;
                //最下行坐标
                Float minHigh = 0.0f;
                //最右列坐标
                Float maxWidth = 0.0f;
                //最左列坐标
                Float minWidth = 0.0f;
                List<String[]> rowListNext = lists.get(rowIndex - 1);
                maxHigh = Float.parseFloat(rowListNext.get(0)[4]);
                minHigh = Float.parseFloat(rowListNext.get(0)[8]);
                maxWidth = Float.parseFloat(rowListNext.get(0)[5]);
                minWidth = Float.parseFloat(rowListNext.get(0)[3]);
                //寻找上述四个坐标
                for (String[] strings : rowListNext) {
                    maxHigh = maxHigh < Float.parseFloat(strings[4]) ? Float.parseFloat(strings[4]) : maxHigh;
                    minHigh = minHigh > Float.parseFloat(strings[8]) ? Float.parseFloat(strings[8]) : minHigh;
                    maxWidth = maxWidth < Float.parseFloat(strings[5]) ? Float.parseFloat(strings[5]) : maxWidth;
                    minWidth = minWidth > Float.parseFloat(strings[3]) ? Float.parseFloat(strings[3]) : minWidth;
                }
                //如果当前行在最上行和最下行中间，且当前行的列坐标在最右列后，或者在最左列前，则合并当前行与上述一行
                if (maxHigh + 5 * 2 >= Float.parseFloat(rowList.get(0)[4]) && minHigh - 5 * 2 <= Float.parseFloat(rowList.get(0)[8])) {
                    if (maxWidth + 20 < Float.parseFloat(rowList.get(0)[3])) {
                        try {
                            rowListNext.addAll(deepCopy(rowList));
                            lists.remove(rowIndex);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    if (minHigh - 20 > Float.parseFloat(rowList.get(0)[5])) {
                        try {
                            List<String[]> list1 = deepCopy(rowListNext);
                            List<String[]> list2 = deepCopy(rowList);
                            rowListNext.clear();
                            rowListNext.addAll(deepCopy(list2));
                            rowListNext.addAll(deepCopy(list1));
                            lists.remove(rowIndex);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        try {
            resultList = deepCopy(lists);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return resultList;
    }

    private <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        List<T> dest = (List<T>) in.readObject();
        return dest;
    }

    @Override
    public List<List<String[]>> tjUploadOssEntity(String pdfPath) throws IOException {
        Boolean wholeContextFlag = false;
        PdfReader pdfReader = new PdfReader(pdfPath);
        return getKeyWords(pdfReader, wholeContextFlag);
    }

    @Override
    public List<List<String[]>> tjUploadOssEntity(InputStream inputStream) throws IOException {
        Boolean wholeContextFlag = false;
        PdfReader pdfReader = new PdfReader(inputStream);
        return getKeyWords(pdfReader, wholeContextFlag);
    }


    @Override
    public List<List<String[]>> getWholePdfContext(String pdfPath) throws IOException {
        Boolean wholeContextFlag = true;
        PdfReader pdfReader = new PdfReader(pdfPath);
        return getKeyWords(pdfReader, wholeContextFlag);
    }

    @Override
    public List<List<String[]>> getWholePdfContext(InputStream inputStream) throws IOException {
        Boolean wholeContextFlag = true;
        PdfReader pdfReader = new PdfReader(inputStream);
        return getKeyWords(pdfReader, wholeContextFlag);
    }

    @Override
    public List<String> changeListStringArray2String(List<String[]> list) {
        List<String> sb = new ArrayList<>();
        for (int index = 0; index < list.size(); index++) {
            String[] strings = list.get(index);
            sb.add(strings[2]);
        }
        return sb;
    }

    @Override
    public List<List<String>> changeListToString(List<List<String[]>> list) {
        if (!CollectionUtils.isEmpty(list)) {
            List<List<String>> listr = new ArrayList<>();
            for (List<String[]> li : list) {
                List<String> str = changeListStringArray2String(li);
                listr.add(str);
            }
            return listr;
        }
        return null;
    }

    @Override
    public void getWholePdfContextOld(InputStream inputStream) throws IOException, PDFException, PDFSecurityException, InterruptedException {
        Document document = new Document();
        document.setInputStream(inputStream, "");
        PageTree dTree = document.getPageTree();
        if (dTree != null) {
            //解析出pdf的所有页数
            int numbers = document.getNumberOfPages();
            List<List<String>> list = new ArrayList<>();
            for (int i = 0; i < numbers; i++) {
                List<String> li = new ArrayList<>();
                list.add(li);
                for (int j = 0; j < dTree.getPage(i).getText().getPageLines().size(); j++) {
                    LineText lintext1 = dTree.getPage(i).getText().getPageLines().get(j);
                    StringBuffer sb = new StringBuffer();
                    List<WordText> words = lintext1.getWords();
                    if (!CollectionUtils.isEmpty(words)) {
                        for (WordText wt : words) {
                            sb.append(wt.getText());
                        }
                        li.add(sb.toString());
                    }
                }
            }

            logger.info(JSON.toJSONString(list));
        }
        document.dispose();
    }

    public Integer getColArea() {
        return colArea;
    }

    public void setColArea(Integer colArea) {
        this.colArea = colArea;
    }

    public Integer getRowArea() {
        return rowArea;
    }

    public void setRowArea(Integer rowArea) {
        this.rowArea = rowArea;
    }


}
