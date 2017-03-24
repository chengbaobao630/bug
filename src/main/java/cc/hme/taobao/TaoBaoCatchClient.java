package cc.hme.taobao;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

/**
 * Created by cheng on 2017/3/24 0024.
 */
@Component
public class TaoBaoCatchClient {

    @Autowired
    MongoTemplate mongoTemplate;

    private static String url_perfix =
            "https://s.taobao.com/search?q=";
    private static  String url_sufix="&imgfile=&commend=all&ssid=s5-e&search_type=item&sourceId=tb.index" +
                    "&spm=a21bo.50862.201856-taobao-item.1&ie=utf8&initiative_id=tbindexz_20170324&bcoffset=2" +
                    "&ntoffset=2&p4ppushleft=1%2C48&s=";


    @PostConstruct
    public  void getGoods() throws IOException {
            HttpClient httpClient = HttpClientBuilder.create().build();
            for (int a = 44; ; a = a + 44) {
                try {
                    HttpGet httpGet = new HttpGet(url_perfix+"男装"+url_sufix+ a);
                    HttpResponse httpResponse = httpClient.execute(httpGet);
                    String html = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
                    Document htmlDocument = Jsoup.parse(html);
                    Elements gPageConfig = htmlDocument.head().getElementsByTag("script");
                    Element scriptElement=gPageConfig.get(gPageConfig.size() - 1);
                    String scriptString = scriptElement.outerHtml();
                    scriptString = scriptString.substring(scriptString.indexOf("{"),scriptString.lastIndexOf(";"));
                    JSONObject jsonArray = JSONObject.fromObject(scriptString);
                    JSONArray items=jsonArray.getJSONObject("mods").getJSONObject("itemlist")
                            .getJSONObject("data")
                            .getJSONArray("auctions");
                    for (Object obj : items) {
                        try {
                            Item item = new Item();
                            JSONObject itemJson = JSONObject.fromObject(obj);
                            String id = itemJson.getString("nid");
                            long exist = mongoTemplate.count(new Query(new Criteria("id").is(id)), Item.class);
                            if (exist > 0) {
                                continue;
                            }
                            String picUrl = itemJson.getString("pic_url");
                            String title = itemJson.getString("title");
                            String rawTitle = itemJson.getString("raw_title");
                            String itemIoc = itemJson.getString("item_loc");
                            String userId = itemJson.getString("user_id");
                            String soldNum = itemJson.getString("view_sales");
                            soldNum = soldNum.substring(0, soldNum.indexOf("人"));
                            String nick = itemJson.getString("nick");
                            String price = itemJson.getString("view_price");
                            String viewFee = itemJson.getString("view_fee");
                            String detailUrl = itemJson.getString("detail_url");
                            item.setId(id);
                            item.setPicUrl(picUrl);
                            item.setTitle(title);
                            item.setRawTitle(rawTitle);
                            item.setItemIoc(itemIoc);
                            item.setUserId(userId);
                            item.setSoldNum(soldNum);
                            item.setNickName(nick);
                            item.setCuPrice(price);
                            item.setViewFee(viewFee);
                            item.setDetailUrl(detailUrl);
                            System.out.println(item);
                            mongoTemplate.save(item);
                        }catch (Exception e){
                            e.printStackTrace();
                            continue;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    break;
                }
            }
        System.out.println("finish get items");

    }




    public static void main(String[] args) throws IOException {

    }


}
