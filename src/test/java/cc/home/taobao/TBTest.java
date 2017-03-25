package cc.home.taobao;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.junit.Test;

import java.io.*;

/**
 * Created by root on 17-3-25.
 */
public class TBTest {

    private static int times = 0;

    private String[] types = {"啤酒", "花生米", "鲍鱼"};



    @Test
    public void goods() {
        for (String type : types) {
            try {
                CloseableHttpClient httpClient = HttpClientBuilder.create().build();
                HttpGet httpGet = new HttpGet("http://localhost:7022/taobao/type?type=" + type);
                httpClient.execute(httpGet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void readFromFile() {
        File file = new File("/home/cc/桌面/test");
        try {
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String type;
            while ((type = reader.readLine()) != null) {
                if (type.contains(" ")) {
                    for (String s : type.split(" ")) {
                        if (s.contains("/")) {
                            for (String ss : s.split("/")) {
                                if (sendRequest(ss)) continue;
                            }

                        }
                        if (sendRequest(s)) continue;
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean sendRequest(String ss) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        if (StringUtils.isEmpty(ss)) return true;
        if (ss.contains("\t")){
            ss = ss.replaceAll("\t","");
        }
        HttpGet httpGet = new HttpGet("http://localhost:7022/taobao/type?type=" + ss.trim());
        httpClient.execute(httpGet);
        System.out.println(times++);
        return false;
    }
}
