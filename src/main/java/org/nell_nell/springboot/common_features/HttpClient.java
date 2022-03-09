package org.nell_nell.springboot.common_features;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpClient {
    public static HttpURLConnection getHttpUrlConnection(String urlLink){
        HttpURLConnection httpurlconnection = null;
        try {
            URL url = null;
            url = new URL(urlLink);
            httpurlconnection = (HttpURLConnection) url.openConnection();
            return httpurlconnection;
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public static String post(int timeOut, String urlLink, String xml,
                              String encode) throws Exception {
        HttpURLConnection httpurlconnection = getHttpUrlConnection(urlLink);
        return post(timeOut, httpurlconnection, xml, encode);
    }

    public static String post(int timeOut, HttpURLConnection httpurlconnection, String xml,
                              String encode) throws Exception {
        try {
            httpurlconnection.setRequestProperty("Content-type", "text/xml");
            httpurlconnection.setDoOutput(true);
            httpurlconnection.setDoInput(true);
            httpurlconnection.setRequestMethod("POST");
            httpurlconnection.setConnectTimeout(timeOut);
            httpurlconnection.setReadTimeout(timeOut);
            httpurlconnection.connect();
        } catch (Exception e) {
            throw e;
        }
        try {
            String SendData = xml;
            httpurlconnection.getOutputStream()
                    .write(SendData.getBytes(encode));
            httpurlconnection.getOutputStream().flush();
            httpurlconnection.getOutputStream().close();
        } catch (Exception e) {
            e.getStackTrace();
            throw e;
        }
        try {
            String result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    httpurlconnection.getInputStream(), encode));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            in.close();
            return result;
        } catch (Exception e) {
            e.getStackTrace();
            throw e;
        } finally {
            if (httpurlconnection != null)
                httpurlconnection.disconnect();
        }
    }



    public static String get(int timeOut, String urlLink, String encode)
            throws Exception {
        HttpURLConnection httpurlconnection = null;
        try {
            URL url = null;
            url = new URL(urlLink);
            httpurlconnection = (HttpURLConnection) url.openConnection();
            httpurlconnection.setRequestProperty("Content-type", "text/xml");
            httpurlconnection.setDoOutput(true);
            httpurlconnection.setDoInput(true);
            httpurlconnection.setRequestMethod("POST");
            httpurlconnection.setConnectTimeout(timeOut);
            httpurlconnection.setReadTimeout(timeOut);
            httpurlconnection.connect();
        } catch (Exception e) {
            throw e;
        }
        try {
            String result = "";
            BufferedReader in = new BufferedReader(new InputStreamReader(
                    httpurlconnection.getInputStream(), encode));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
            in.close();
            return result;
        } catch (Exception e) {
            e.getStackTrace();
            throw e;
        } finally {
            if (httpurlconnection != null)
                httpurlconnection.disconnect();
        }
    }
//
//    public static void main(String[] args) {
//        try {
//            // get请求
//            String url = "http://www.kuaidi100.com/query?type=shentong&postid=3350862539854";
//            String content = "";
//            System.out.println("GET："+url);
//            String response = HttpClient.get(30000, url, "UTF-8");
//            System.out.println("GET：" + response);
//
//            url="http://www.kuaidi100.com/query?type=shentong&postid=3350862539854";
//            content="name=sdfsdf&pwd=132131";
//            response=HttpClient.post(3000, url, content, "UTF-8");
//            System.out.println("POST："+response);
///*
//			JsonParser jsonParser = new JsonParser();
//			JsonObject obj = (JsonObject) jsonParser.parse(response);
//			String status = obj.get("status").getAsString();
//			System.out.println("status=" + status);*/
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
}