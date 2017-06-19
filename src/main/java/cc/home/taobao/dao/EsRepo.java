package cc.home.taobao.dao;

import cc.home.taobao.domain.Item;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface EsRepo extends ElasticsearchRepository<Item,String> {
}
