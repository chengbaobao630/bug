package cc.home.taobao.web;

import cc.home.taobao.domain.Item;
import cc.home.taobao.service.ItemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import java.util.ArrayList;
import java.util.LinkedList;

@RestController
@RequestMapping("/es")
public class EsTestController {

    private static final Logger log = LoggerFactory.getLogger(EsTestController.class);

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;

    @Autowired
    private ItemService itemService;

    private static  final Integer MAX_RETRY_TIMES = 3;

    @RequestMapping("/index")
    public String createIndex(HttpServletRequest request, final Item item) {
        trySaveEs(item,System.currentTimeMillis());
        return "success";
    }

    private void trySaveEs(final Item item, Long timeStamp) {
        LinkedList<Item> items = new LinkedList<Item>(){
            {
                add(item);
            }
        };
        trySaveEsBatch(items,timeStamp);
    }

    private void trySaveEsBatch(LinkedList<Item> items, Long timeStamp) {
        try {
            String redisKey = createKeys(items, timeStamp);
            Integer times = (Integer) redisTemplate.opsForValue().get(redisKey);
            if (times == null) {
                redisTemplate.opsForValue().set(redisKey, 0);
            } if (times >= MAX_RETRY_TIMES){
                doWithError(items,timeStamp);
                return ;
            }else {
                redisTemplate.opsForValue().set(redisKey, times + 1);
            }
            itemService.saveAll(items);
        } catch (Exception e) {
            log.error("trySaveEsBatch due to error", e);
            trySaveEsBatch(items,timeStamp);
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
