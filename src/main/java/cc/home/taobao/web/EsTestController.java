package cc.home.taobao.web;

import cc.home.taobao.bo.EsRequestBo;
import cc.home.taobao.domain.Item;
import cc.home.taobao.service.ItemService;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.LinkedList;

@RestController
@RequestMapping("/es")
public class EsTestController {

    private static final Logger log = LoggerFactory.getLogger(EsTestController.class);

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private ItemService itemService;

    private static final Integer MAX_RETRY_TIMES = 3;

    @RequestMapping(value = "/index", method = RequestMethod.POST, consumes = "application/json")
    public String createIndex(HttpServletRequest request, @RequestBody final EsRequestBo bo) {
        trySaveEs(bo.getItem(), System.currentTimeMillis(), bo.getCallBackUrl());
        return "success";
    }

    @RequestMapping(value = "/call_back", method = RequestMethod.POST)
    public String callBack() {
        return "success";
    }

    private void trySaveEs(final Item item, Long timeStamp, String callBackUrl) {
        LinkedList<Item> items = new LinkedList<Item>() {
            {
                add(item);
            }
        };
        trySaveEsBatch(items, timeStamp, callBackUrl);
    }

    private void trySaveEsBatch(LinkedList<Item> items, Long timeStamp, String callBackUrl) {
        String redisKey = createKeys(items, timeStamp);
        Integer times = null;
        try {
            times = (Integer) redisTemplate.opsForValue().get(redisKey);
        } catch (Exception e) {
            log.error("get redis due to error", e);
        }
        try {

            if (times == null) {
                redisTemplate.opsForValue().set(redisKey, 1);
            } else if (times >= MAX_RETRY_TIMES) {
                doWithError(items, timeStamp);
                return;
            } else {
                redisTemplate.opsForValue().set(redisKey, times + 1);
            }
            itemService.saveAll(items);
            callBack(callBackUrl);
        } catch (Exception e) {
            log.error("trySaveEsBatch due to error", e);
            trySaveEsBatch(items, timeStamp, callBackUrl);
        }
    }

    private void callBack(String callBackUrl) throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(callBackUrl);
        CloseableHttpResponse execute = httpClient.execute(httpPost);
        String s = EntityUtils.toString(execute.getEntity(), "utf-8");
        if (!"success".equals(s)) {
            throw new RuntimeException("callBack due to error");
        }
    }

    private void doWithError(LinkedList<Item> items, Long timeStamp) {
        //todo
    }

    private String createKeys(LinkedList<Item> items, Long timeStamp) {
        if (items.size() > 1) {
            String poolKey = items.get(0).getId() + "_" + items.size() + "_" + items.get(items.size() - 1);
            for (Item item : items) {
                redisTemplate.opsForSet().add(poolKey, item.getId());
            }
        }
        return items.get(0).getId() + "_" + timeStamp;
    }
}
