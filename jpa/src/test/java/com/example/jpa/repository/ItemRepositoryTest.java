package com.example.jpa.repository;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.jpa.entity.Item;
import com.example.jpa.entity.constant.ItemSellStatus;

@SpringBootTest
public class ItemRepositoryTest {
    
    @Autowired
    private ItemRespository itemRespository;

    @Test
    public void insertTest(){
        for (int i = 1; i < 10; i++) {
            Item item = Item.builder()
            .code("P00"+i)
            .itemPrice(1000*i)
            .stockNumber(10)
            .itemDetail("Item Detail"+i)
            .itemSellStatus(ItemSellStatus.SELL)
            .itemNm("Item"+i)
            .build();
            itemRespository.save(item);
        }
    }
    @Test
    public void updateTest(){
        // item 상태
        Optional<Item> result = itemRespository.findById("p001");
        result.ifPresent(item -> {
            item.changeSelStatus(ItemSellStatus.SOLDOUT);
            item.changeStockNumber(0);
            itemRespository.save(item);
        });
    }
    @Test
    public void updateTest2(){
        // item 재고수량
        Optional<Item> result = itemRespository.findById("p001");
        result.ifPresent(item -> {
            item.changeStockNumber(0);
            itemRespository.save(item);
        });
    }
    @Test
    public void deleteTest(){
        itemRespository.deleteById("p009");
    }
    @Test
    public void readTest(){
        // System.out.println(itemRespository.findById("p002").get()); 아래와 동일
       Optional<Item> result = itemRespository.findById("p002");
       result.ifPresent(item->System.out.println(item));
    }
    @Test
    public void readTest2(){
        itemRespository.findAll().forEach(item -> System.out.println(item));
    }

    @Test
    public void aggrTest(){
        List<Object[]> result = itemRespository.aggr();
        for (Object[] objects : result) {
            System.out.println( "아이템의 수" +objects[0]);
            System.out.println( "합계" +objects[1]);
            System.out.println( "평균" +objects[2]);
            System.out.println( "최대" +objects[3]);
            System.out.println( "최소" +objects[4]);
        }
    }
}
