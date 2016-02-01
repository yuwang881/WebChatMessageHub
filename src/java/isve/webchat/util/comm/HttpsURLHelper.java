package isve.webchat.util.comm;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.SSLContext;
import java.io.FileInputStream;
import java.io.IOException;

import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.*;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.json.JSONObject;


/**
 * Created by yuwang on 2015/9/28.
 */
public class HttpsURLHelper {
    private CloseableHttpClient httpclient;
    private String encoding = "UTF-8";
   
    public HttpsURLHelper() {
        httpclient = HttpClients.createDefault();
    }
    
    public void setEncoding(String encode) {
        encoding = encode;
    }
    public HttpsURLHelper(boolean isSecure) throws Exception {
        if (!isSecure) {
            httpclient = HttpClients.createDefault();
        } else {
            SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null,new TrustStrategy() {
            @Override
            public boolean isTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
                return true;
            }
        }).build();
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
        httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        }
       
        
    }


    public String get(String url) throws IOException {
        int buffersize = 2048* 80;
        byte[] result= new byte[buffersize];
        int length =0;
        int totalLength =0;
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse response1 = httpclient.execute(httpGet);
        try {
            System.out.println(response1.getStatusLine());
            HttpEntity entity1 = response1.getEntity();
            InputStream is = entity1.getContent();
            while (length >= 0) {
                totalLength = totalLength + length;
                length = is.read(result, totalLength, buffersize-totalLength);
            }
            is.close();
        } finally {
            response1.close();
        }
        return new String(result, 0, totalLength);
    }

    
    public String Post(String url,String jsonString) throws IOException{
        byte[] result= new byte[2048];
        int length =0;
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-type", "application/json");
        StringEntity  postingString =new StringEntity(jsonString,encoding);
        httpPost.setEntity(postingString);

        CloseableHttpResponse response2 = httpclient.execute(httpPost);

        try {
            System.out.println(response2.getStatusLine());
            HttpEntity entity = response2.getEntity();
            length = entity.getContent().read(result);
        } finally {
            response2.close();
        }
        return new String(result,0,length);
    }


    public String PostJSON(String url,JSONObject json) throws IOException{
        return Post(url,json.toString());
    }


    public String PostJSON(String url,String[] keys,String[] values) throws IOException{
        JSONObject jo = new JSONObject();
        for (int i =0;i< keys.length;i++) {
            if (values[i].toLowerCase().trim().equals("true")){
                jo.put(keys[i],true);
            } else if (values[i].toLowerCase().trim().equals("false")){
                jo.put(keys[i],false);
            } else if (isInt(values[i])){
                jo.put(keys[i],toInt(values[i]));
            } else {
                jo.put(keys[i],values[i]);
            }
        }
        System.out.println(jo.toString());
        return  PostJSON(url,jo);
    }

    public void close() throws IOException {
        httpclient.close();
    }


    private boolean isInt(String s){
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }

    private int toInt(String s) {
        try {
            return Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return 0;
        } catch(NullPointerException e) {
            return 0;
        }
    }
}
