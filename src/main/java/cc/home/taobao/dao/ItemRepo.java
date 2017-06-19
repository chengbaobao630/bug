package cc.home.taobao.dao;

import cc.home.taobao.domain.Item;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by cheng on 2017/3/28 0028.
 */
@Mapper
public interface ItemRepo  {

    @Select(value = "select count(1) from item where id  = #id#")
    long countById(@Param("id") String id);

    @InsertProvider(type = ItemSql.class,method = "saveAll")
    int saveAll(List<Item> items);
}
