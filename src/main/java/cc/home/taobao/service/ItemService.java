package cc.home.taobao.service;

import cc.home.taobao.dao.EsRepo;
import cc.home.taobao.domain.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {

    @Autowired
    private EsRepo esRepo;

    public void saveAll(List<Item> items) {
        esRepo.saveAll(items);
    }

    public Page<Item> findAll(Pageable pageable) {
        return esRepo.findAll(pageable);
    }
}
