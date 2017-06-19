package cc.home.taobao.service;

import cc.home.SpiderApplicationTests;
import cc.home.taobao.domain.Item;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class ItemServiceTest extends SpiderApplicationTests {

    @Autowired
    private ItemService itemService;

    private String ori ="{\n" +
            "\n" +
            "    \"type\": \"外星人\",\n" +
            "    \"id\": \"45030854352\",\n" +
            "    \"title\": \"Alienware 2017款<span class=H>外星</span><span class=H>人</span>笔记本电脑 ALW13C-R2508 2728 2738 2838\",\n" +
            "    \"rawTitle\": \"Alienware 2017款外星人笔记本电脑 ALW13C-R2508 2728 2738 2838\",\n" +
            "    \"itemIoc\": \"上海\",\n" +
            "    \"picUrl\": \"//g-search3.alicdn.com/img/bao/uploaded/i4/i2/223924413/TB2EM7fmCFjpuFjSspbXXXagVXa_!!223924413.jpg\",\n" +
            "    \"cuPrice\": \"11000.00\",\n" +
            "    \"viewFee\": \"0.00\",\n" +
            "    \"oriPrice\": null,\n" +
            "    \"soldNum\": \"0\",\n" +
            "    \"detailUrl\": \"//item.taobao.com/item.htm?id=45030854352&ns=1&abbucket=0#detail\",\n" +
            "    \"userId\": \"223924413\",\n" +
            "    \"nickName\": \"qq310663636\"\n" +
            "\n" +
            "}";

    @Test
    public void saveAll() throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        final Item item = objectMapper.readValue(ori, Item.class);
        item.setOriPrice("111000.00");
        itemService.saveAll(new ArrayList(){
            {
                add(item);
            }
        });
    }

}