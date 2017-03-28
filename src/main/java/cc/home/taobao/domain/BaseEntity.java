package cc.home.taobao.domain;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.domain.Persistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by thinkpad on 2016/7/21 0021.
 */

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity<K extends Serializable> implements Persistable<K> {

    public BaseEntity() {
        this.createTime=new Date();
        this.updateTime=new Date();
        this.isDel=false;
    }

    @Id
    @GeneratedValue
    protected  K id;

    @CreatedDate
    public Date createTime;

    @LastModifiedDate
    protected Date updateTime;

    @CreatedBy
    protected String createUser;

    @LastModifiedBy
    protected String lastModifyUser;

    @Basic
    protected boolean isDel=false;


    @Override
    public K getId() {
        return id;
    }

    public void setId(K id) {
        this.id = id;
    }

    @Override
    public boolean isNew() {
        return getId()!=null;
    }


    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getCreateUser() {
        return createUser;
    }

    public void setCreateUser(String createUser) {
        this.createUser = createUser;
    }

    public String getLastModifyUser() {
        return lastModifyUser;
    }

    public void setLastModifyUser(String lastModifyUser) {
        this.lastModifyUser = lastModifyUser;
    }

    public boolean isDel() {
        return isDel;
    }

    public void setDel(boolean del) {
        isDel = del;
    }
}
