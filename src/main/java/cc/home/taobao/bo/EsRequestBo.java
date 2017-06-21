package cc.home.taobao.bo;

import cc.home.taobao.domain.Item;

public class EsRequestBo {

    private Item item;

    private String callBackUrl;

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
    }

    public String getCallBackUrl() {
        return callBackUrl;
    }

    public void setCallBackUrl(String callBackUrl) {
        this.callBackUrl = callBackUrl;
    }
}
