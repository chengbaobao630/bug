package cc.home.taobao;

import org.ansj.splitWord.analysis.ToAnalysis;
import org.junit.Test;

/**
 * Created by root on 17-3-26.
 */
public class AnjsTest {

    @Test
    public void testAnjs(){
        //检索内容
        String text = "<span class=H>jackjones</span>杰克琼斯专柜正品新款纯棉衬衣男式短袖衬衫渐变色04070\"";
        System.out.println(ToAnalysis.parse(text).toStringWithOutNature());
    }
}
