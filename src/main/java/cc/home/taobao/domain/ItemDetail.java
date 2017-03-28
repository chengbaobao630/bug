package cc.home.taobao.domain;

import cc.home.taobao.domain.Item;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * Created by root on 17-3-25.
 */
public class ItemDetail extends Item {

    public static void main(String[] args) {
        try {
            CloseableHttpClient client = HttpClientBuilder.create().build();
            HttpGet httpGet =new HttpGet("http://item.taobao.com/item.htm?id=544173405540&ns=1&abbucket=0#detail");
            CloseableHttpResponse httpResponse = client.execute(httpGet);
            String html=EntityUtils.toString(httpResponse.getEntity(),"utf-8");
            System.out.println(html);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

}
