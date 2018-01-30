package com.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by liaowuhen on 2018/1/26.
 */
public class HttpClientUtils {
    protected static final Logger logger = LoggerFactory.getLogger(HttpClientUtils.class);

    public static InputStream downByUrl(String url) throws IOException {
        CloseableHttpClient client = HttpClients.createDefault();
        RequestConfig config = RequestConfig.custom().build();
        //目标文件url
        HttpGet httpGet = new HttpGet(url);
        httpGet.setConfig(config);
        HttpResponse respone = client.execute(httpGet);
        if (respone.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {

            HttpEntity entity = respone.getEntity();
            if (entity != null) {
                InputStream is = entity.getContent();
                return is;
            }
        }

        return null;
    }


}
