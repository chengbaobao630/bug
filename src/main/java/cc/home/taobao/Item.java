package cc.home.taobao;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by cheng on 2017/3/24 0024.
 */
@Document
public class Item {

    private String type;

    private String id;

    private String title;

    private String rawTitle;

    private String itemIoc;

    private String picUrl;

    private String cuPrice;

    private String viewFee;

    private String oriPrice;

    private String soldNum;

    private String detailUrl;

    private String userId;

    private String nickName;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getViewFee() {
        return viewFee;
    }

    public void setViewFee(String viewFee) {
        this.viewFee = viewFee;
    }

    public String getRawTitle() {
        return rawTitle;
    }

    public void setRawTitle(String rawTitle) {
        this.rawTitle = rawTitle;
    }

    public String getItemIoc() {
        return itemIoc;
    }

    public void setItemIoc(String itemIoc) {
        this.itemIoc = itemIoc;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = dueWithTaoBaoTag(detailUrl);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = dueWithTaoBaoTag(id);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = dueWithTaoBaoTag(title);
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = dueWithTaoBaoTag(picUrl);
    }

    public String getCuPrice() {
        return cuPrice;
    }

    public void setCuPrice(String cuPrice) {

        this.cuPrice = dueWithTaoBaoTag(cuPrice);
    }

    public String getOriPrice() {
        return oriPrice;
    }

    public void setOriPrice(String oriPrice) {
        this.oriPrice = dueWithTaoBaoTag(oriPrice);
    }

    public String getSoldNum() {
        return soldNum;
    }

    public void setSoldNum(String soldNum) {
        this.soldNum = dueWithTaoBaoTag(soldNum);
    }

    private String dueWithTaoBaoTag(String ori) {
        ori = ori.replaceAll("\\\\","")
                .replaceAll("\"","");
        return ori == null || "null".equalsIgnoreCase(ori)
                ? "" : ori;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        if (type != null ? !type.equals(item.type) : item.type != null) return false;
        if (!id.equals(item.id)) return false;
        if (title != null ? !title.equals(item.title) : item.title != null) return false;
        if (rawTitle != null ? !rawTitle.equals(item.rawTitle) : item.rawTitle != null) return false;
        if (itemIoc != null ? !itemIoc.equals(item.itemIoc) : item.itemIoc != null) return false;
        if (picUrl != null ? !picUrl.equals(item.picUrl) : item.picUrl != null) return false;
        if (cuPrice != null ? !cuPrice.equals(item.cuPrice) : item.cuPrice != null) return false;
        if (viewFee != null ? !viewFee.equals(item.viewFee) : item.viewFee != null) return false;
        if (oriPrice != null ? !oriPrice.equals(item.oriPrice) : item.oriPrice != null) return false;
        if (soldNum != null ? !soldNum.equals(item.soldNum) : item.soldNum != null) return false;
        if (detailUrl != null ? !detailUrl.equals(item.detailUrl) : item.detailUrl != null) return false;
        if (userId != null ? !userId.equals(item.userId) : item.userId != null) return false;
        return nickName != null ? nickName.equals(item.nickName) : item.nickName == null;
    }

    @Override
    public int hashCode() {
        int result = type != null ? type.hashCode() : 0;
        result = 31 * result + id.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (rawTitle != null ? rawTitle.hashCode() : 0);
        result = 31 * result + (itemIoc != null ? itemIoc.hashCode() : 0);
        result = 31 * result + (picUrl != null ? picUrl.hashCode() : 0);
        result = 31 * result + (cuPrice != null ? cuPrice.hashCode() : 0);
        result = 31 * result + (viewFee != null ? viewFee.hashCode() : 0);
        result = 31 * result + (oriPrice != null ? oriPrice.hashCode() : 0);
        result = 31 * result + (soldNum != null ? soldNum.hashCode() : 0);
        result = 31 * result + (detailUrl != null ? detailUrl.hashCode() : 0);
        result = 31 * result + (userId != null ? userId.hashCode() : 0);
        result = 31 * result + (nickName != null ? nickName.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Item{" +
                "type='" + type + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", rawTitle='" + rawTitle + '\'' +
                ", itemIoc='" + itemIoc + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", cuPrice='" + cuPrice + '\'' +
                ", viewFee='" + viewFee + '\'' +
                ", oriPrice='" + oriPrice + '\'' +
                ", soldNum='" + soldNum + '\'' +
                ", detailUrl='" + detailUrl + '\'' +
                ", userId='" + userId + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
