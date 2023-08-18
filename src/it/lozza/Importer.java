package it.lozza;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public abstract class Importer {

    protected String execPOST(String url, QueryString data) {
        String result = null;
        try {
            String params = data.toString();

            System.out.println(url + params);

            CloseableHttpClient client = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url + params);
            httpPost.setHeader("User-Agent", "PostmanRuntime/7.32.3");
            httpPost.setHeader("Accept", "*/*");
            httpPost.setHeader("Accept-Encoding", "gzip, deflate, br");
            httpPost.setHeader("Connection", "keep-alive");
            CloseableHttpResponse response = client.execute(httpPost);
            try {
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    result = EntityUtils.toString(responseEntity);
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    protected String execGET(String url, QueryString data) {
        String result = null;
        try {
            String params = data.toString();

            CloseableHttpClient client = HttpClients.createDefault();
            System.out.println(url + params);
            HttpGet httpGet = new HttpGet(url + params);
            httpGet.setHeader("User-Agent", "PostmanRuntime/7.32.3");
            httpGet.setHeader("Accept", "*/*");
            httpGet.setHeader("Accept-Encoding", "gzip, deflate, br");
            httpGet.setHeader("Connection", "keep-alive");
            CloseableHttpResponse response = client.execute(httpGet);
            try {
                HttpEntity responseEntity = response.getEntity();
                if (responseEntity != null) {
                    result = EntityUtils.toString(responseEntity);
                }
            } finally {
                response.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
