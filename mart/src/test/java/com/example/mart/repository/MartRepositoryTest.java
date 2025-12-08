package com.example.mart.repository;

import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.mart.entity.Item;
import com.example.mart.entity.Member;
import com.example.mart.entity.Order;
import com.example.mart.entity.OrderItem;
import com.example.mart.entity.constant.OrderStatus;



@SpringBootTest
public class MartRepositoryTest {
    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private OrderRepository orderRepository;


    @Test
    public void insertMemberTest(){
        // 5명의 멤버
        IntStream.rangeClosed(1, 5).forEach(i->{
            Member member = Member.builder()
            .name("user"+i)
            .city("서울")
            .street("724-1"+i)
            .zipcode("1650"+i)
            .build();
            memberRepository.save(member);
        });
        
    }
    @Test
    public void insertItemTest(){
        // 5개의 아이템         
        IntStream.rangeClosed(1, 5).forEach(i->{
            Item item = Item.builder()
            .name("item"+i)
            .price(250000)
            .quantity(i*5)
            .build();
            itemRepository.save(item);
        });
    }


    @Test
    public void orderTest(){
        // 주문

        // 1번 상품을 2번 고객이 주문
        Member member = memberRepository.findById(2L).get();
        Item item = itemRepository.findById(1L).get();

        Order order = Order.builder()
        .member(member)
        .orderStatus(OrderStatus.ORDER)
        .build();

        orderRepository.save(order);

        OrderItem orderItem = OrderItem.builder()
        .item(item)
        .order(order)
        .orderPrice(200000)
        .count(1)
        .build();
        orderItemRepository.save(orderItem);
    }

    @Transactional(readOnly = true)
    @Test
    public void orderReadTest(){
        // 2 번 고객의 주문내역조회->findbyid로는 불가능 orederrepository에 메소드 만들어야함
        Member member = Member.builder().id(2L).build();
        Order order = orderRepository.findByMember(member).get();
        System.out.println(order);

        // 아이템 정보 sql 아이템에 join 건 것과 동잃하기 -> lazy error 발생 트랜잭션 필수
        order.getOrderItems().forEach(i->{
            System.out.println(i);
            //추가적으로 주문 상품 상제 정보 조회
            // sql에서 추가로 조인 건 것 정확힌 아이템과 조인되었으니 아이템과 이어진 상세정보 추가 조인
            System.out.println(i.getItem());

        });
        //고객의 상세정보
        System.out.println(order.getMember());
    }

    
}
