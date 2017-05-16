package cc.home.taobao.web;

import cc.home.taobao.dao.ItemRepo;
import cc.home.taobao.domain.Item;
import cc.home.taobao.domain.MyPage;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.ansj.splitWord.analysis.ToAnalysis;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;

/**
 * Created by cheng on 2017/3/24 0024.
 */
@RestController
@RequestMapping("taobao")
public class TaoBaoCatchController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaoBaoCatchController.class);

    @Autowired
    ItemRepo itemRepo;

    @Autowired
    RedisTemplate redisTemplate;

    private static String url_perfix =
            "https://s.taobao.com/search?q=";
    private static String url_sufix = "&imgfile=&commend=all&ssid=s5-e&search_type=item&sourceId=tb.index" +
            "&spm=a21bo.50862.201856-taobao-item.1&ie=utf8&initiative_id=tbindexz_20170324&bcoffset=2" +
            "&ntoffset=2&p4ppushleft=1%2C48&s=";

    private Semaphore semaphore = new Semaphore(Math.max(1, Runtime.getRuntime().availableProcessors()));

    private ScheduledThreadPoolExecutor executor =
            new ScheduledThreadPoolExecutor(Runtime.getRuntime().availableProcessors());

    private ItemQueen<String> itemTypes = new ItemQueen();

    private LinkedList<Future<String>> futures =
            new LinkedList<>();


    private void setRedis(){
//        redisTemplate.en
    }

    @Scheduled(fixedRate = 5000)
    private void checkResult() {
        for (Future<String> future : futures) {
            if (future.isDone()) {
                futures.removeFirst();
                try {
                    if (itemTypes.size() > 0) {
                        LOGGER.info("current itemTypes is :" + itemTypes);
                        execCall(itemTypes.pollLast());
                    }else {
                        semaphore.release();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @RequestMapping(value = "split",method = RequestMethod.GET)
    public String spiltTitle(Integer page,Integer size){
        Page<Item> items = itemRepo.findAll(new MyPage(page,size));
        for (Item item : items.getContent()) {
            String s = ToAnalysis.parse(item.getTitle()).toStringWithOutNature();
            for (String s1 : s.split(",")) {
                if (StringUtils.isNotEmpty(s1) &&
                        !".".equalsIgnoreCase(s1) &&
                        !"。".equalsIgnoreCase(s1)){
                    this.itemTypes.push(s1);
                }
            }
        }
        return "success";
    }

    @RequestMapping(value = "type", method = RequestMethod.GET)
    public String addType(@RequestParam(value = "type") String type) {
        try {
            itemTypes.push(type);
            return "success";
        } catch (Exception e) {
            return "error";
        }
    }

    private void doGetGoods(String type) {
        HttpClient httpClient = HttpClientBuilder.create().build();
        for (int a = 44;; a = a + 44) {
            try {
                HttpGet httpGet = new HttpGet(url_perfix + type + url_sufix + a);
                HttpResponse httpResponse = httpClient.execute(httpGet);
                String html = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
                Document htmlDocument = Jsoup.parse(html);
                Elements gPageConfig = htmlDocument.head().getElementsByTag("script");
                Element scriptElement = gPageConfig.get(gPageConfig.size() - 1);
                String scriptString = scriptElement.outerHtml();
                scriptString = scriptString.substring(scriptString.indexOf("{"), scriptString.lastIndexOf(";"));
                JSONObject jsonArray = JSONObject.fromObject(scriptString);
                JSONArray items = jsonArray.getJSONObject("mods").getJSONObject("itemlist")
                        .getJSONObject("data")
                        .getJSONArray("auctions");
                for (Object obj : items) {
                    List<Item> itemList = new ArrayList<>();
                    try {
                        Item item = new Item();
                        JSONObject itemJson = JSONObject.fromObject(obj);
                        String id = itemJson.getString("nid");
                        long exist = itemRepo.countById(id);
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
                        item.setType(type);
                        itemList.add(item);
                        LOGGER.info(item.toString());
                    } catch (Exception e) {
                        LOGGER.error(e.getLocalizedMessage());
                        continue;
                    }
                    itemRepo.saveAll(itemList);
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }
        LOGGER.info("finish get items of type :" + type);
    }


    class ItemQueen<T> extends ConcurrentLinkedDeque<T> {
        @Override
        public void push(T o) {
            super.push(o);
            if (itemTypes != null && itemTypes.size() > 0) {
                if (semaphore.tryAcquire()) {
                    execCall(itemTypes.pollLast());
                }
            }
        }
    }

    private void execCall(String type) {
        LOGGER.info("execCall type :" + type );
        Callable callable = (Callable<String>) () -> {
            doGetGoods(type);
            return type;
        };
        Future future = executor.submit(callable);
        futures.add(future);
        LOGGER.info("futures size :" + futures.size() );
    }


    public static void main(String[] args) {
        File file=new File("D:\\");
        System.out.println(file.getTotalSpace()/1024/1024/1024);
        System.out.println(file.getUsableSpace()/1024/1024/1024);
        System.out.println(file.getFreeSpace()/1024/1024/1024);
    }
}
