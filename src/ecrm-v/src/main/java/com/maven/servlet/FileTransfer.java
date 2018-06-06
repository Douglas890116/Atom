package com.maven.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import com.maven.config.SystemConstant;
import com.maven.logger.TLogger;

public class FileTransfer {
	
	public static final String FAILD = "{'url':'','status':'上传失败'}";
	
	public static String upload(InputStream file,String name,String type,String dir){
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
        	String url = SystemConstant.getProperties("picture.server")+"/FileUpload"+"?type="+type+"&filename="+name;
        	if(StringUtils.isNotBlank(dir)) url += "&dir="+dir;

        	httpClient = HttpClients.createDefault();
            // 把一个普通参数和文件上传给下面这个地址 是一个servlet
            HttpPost httpPost = new HttpPost(url);
            // 把文件转换成流对象FileBody
            InputStreamBody ibin = new InputStreamBody(file, "multipart/form-data");
            HttpEntity reqEntity = MultipartEntityBuilder.create()
                    .addPart("file", ibin)
                    .build();

            httpPost.setEntity(reqEntity);
            // 发起请求 并返回请求的响应
            response = httpClient.execute(httpPost);
            // 获取响应对象
            HttpEntity resEntity = response.getEntity();
            if (resEntity != null) {
                // 打印响应内容
                return EntityUtils.toString(resEntity, Charset.forName("UTF-8"));
            }
            // 销毁
            EntityUtils.consume(resEntity);
        }catch (Exception e){
            TLogger.getLogger().Error(e.getMessage(), e);
        }finally {
            try {
                if(response != null){
                    response.close();
                }
            } catch (IOException e) {
            	TLogger.getLogger().Error(e.getMessage(), e);
            }
            try {
                if(httpClient != null){
                    httpClient.close();
                }
            } catch (IOException e) {
            	TLogger.getLogger().Error(e.getMessage(), e);
            }
        }
        return FAILD;
    }

}
