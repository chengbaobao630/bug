package cc.home.taobao.dao;

import cc.home.taobao.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Created by cheng on 2017/3/28 0028.
 */
public interface ItemRepo  extends JpaRepository<Item,String> {

    @Query(value = "select count(1) from item where id  = :id",nativeQuery = true)
    long countById(@Param("id") String id);
}
