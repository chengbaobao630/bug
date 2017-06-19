package cc.home.taobao.domain;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

/**
 * Created by cheng on 2017/3/28 0028.
 */
public class MyPage implements Pageable {

    public MyPage(Integer pageNum, Integer pageSize) {
        this.pageNum = pageNum;
        this.pageSize = pageSize;
    }

    public MyPage sort(Sort.Direction direction,String... sort){
        this.sortBy = sort;
        this.direction = direction;
        return this;
    }

    private String[] sortBy;

    private  Sort.Direction direction;

    private Integer pageNum;

    private Integer pageSize;

    private Integer currentPage;


    @Override
    public int getPageNumber() {
        return pageNum;
    }

    @Override
    public int getPageSize() {
        return pageSize;
    }

    @Override
    public long getOffset() {
        return pageSize;
    }

    @Override
    public Sort getSort() {
        return Sort.unsorted();
    }

    @Override
    public Pageable next() {
        return new MyPage(++currentPage,pageSize);
    }

    @Override
    public Pageable previousOrFirst() {
        return new MyPage(--currentPage < 0 ?
                            0 : currentPage,pageSize);
    }

    @Override
    public Pageable first() {
        return new MyPage(0,pageSize);
    }

    @Override
    public boolean hasPrevious() {
        return currentPage == 0 ? false : true;
    }
}
