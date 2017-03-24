package cc.hme.taobao;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by cheng on 2017/3/24 0024.
 */
@Document
public class Item {


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
        return ori == null ? "" : ori;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Item)) return false;

        Item item = (Item) o;

        if (getId() != null ? !getId().equals(item.getId()) : item.getId() != null) return false;
        if (getTitle() != null ? !getTitle().equals(item.getTitle()) : item.getTitle() != null) return false;
        if (getRawTitle() != null ? !getRawTitle().equals(item.getRawTitle()) : item.getRawTitle() != null)
            return false;
        if (getItemIoc() != null ? !getItemIoc().equals(item.getItemIoc()) : item.getItemIoc() != null) return false;
        if (getPicUrl() != null ? !getPicUrl().equals(item.getPicUrl()) : item.getPicUrl() != null) return false;
        if (getCuPrice() != null ? !getCuPrice().equals(item.getCuPrice()) : item.getCuPrice() != null) return false;
        if (getOriPrice() != null ? !getOriPrice().equals(item.getOriPrice()) : item.getOriPrice() != null)
            return false;
        if (getSoldNum() != null ? !getSoldNum().equals(item.getSoldNum()) : item.getSoldNum() != null) return false;
        if (getDetailUrl() != null ? !getDetailUrl().equals(item.getDetailUrl()) : item.getDetailUrl() != null)
            return false;
        if (getUserId() != null ? !getUserId().equals(item.getUserId()) : item.getUserId() != null) return false;
        return getNickName() != null ? getNickName().equals(item.getNickName()) : item.getNickName() == null;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + (getTitle() != null ? getTitle().hashCode() : 0);
        result = 31 * result + (getRawTitle() != null ? getRawTitle().hashCode() : 0);
        result = 31 * result + (getItemIoc() != null ? getItemIoc().hashCode() : 0);
        result = 31 * result + (getPicUrl() != null ? getPicUrl().hashCode() : 0);
        result = 31 * result + (getCuPrice() != null ? getCuPrice().hashCode() : 0);
        result = 31 * result + (getOriPrice() != null ? getOriPrice().hashCode() : 0);
        result = 31 * result + (getSoldNum() != null ? getSoldNum().hashCode() : 0);
        result = 31 * result + (getDetailUrl() != null ? getDetailUrl().hashCode() : 0);
        result = 31 * result + (getUserId() != null ? getUserId().hashCode() : 0);
        result = 31 * result + (getNickName() != null ? getNickName().hashCode() : 0);
        return result;
    }


    @Override
    public String toString() {
        return "Item{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", rawTitle='" + rawTitle + '\'' +
                ", itemIoc='" + itemIoc + '\'' +
                ", picUrl='" + picUrl + '\'' +
                ", cuPrice='" + cuPrice + '\'' +
                ", oriPrice='" + oriPrice + '\'' +
                ", soldNum='" + soldNum + '\'' +
                ", detailUrl='" + detailUrl + '\'' +
                ", userId='" + userId + '\'' +
                ", nickName='" + nickName + '\'' +
                '}';
    }
}
