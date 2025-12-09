package com.example.mart.repository;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.example.mart.entity.Category;
import com.example.mart.entity.CategoryItem;
import com.example.mart.entity.Delivery;
import com.example.mart.entity.Item;
import com.example.mart.entity.Member;
import com.example.mart.entity.Order;
import com.example.mart.entity.OrderItem;
import com.example.mart.entity.constant.DeliveryStatus;
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

    @Autowired
    private DeliveryRepository deliveryRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryItemRepository categoryItemRepository;


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
    @Commit
    @Transactional
    @Test
    public void orderCascadeTest(){
         // 2번 상품을 3번 고객이 주문
        Member member = memberRepository.findById(3L).get();
        Item item = itemRepository.findById(2L).get();

        Order order = Order.builder()
        .member(member)
        .orderStatus(OrderStatus.ORDER)
        .build();
        OrderItem orderItem = OrderItem.builder()
        .item(item)
        .order(order)
        .orderPrice(230000)
        .count(1)
        .build();
        order.getOrderItems().add(orderItem);

        orderRepository.save(order);
        // orderItemRepository.save(orderItem);
        // 이렇게 해도 되고 order에 메소드를 만들어도 된다.
        // Order order = Order.builder()
        // .member(member)
        // .orderStatus(OrderStatus.ORDER)
        // .build();
        // OrderItem orderItem = OrderItem.builder()
        // .item(item)
        // .orderPrice(230000)
        // .count(1)
        // .build();
        // order.addOrderItem(orderItem);
        // orderRepository.save(order);

    }
    @Commit
    @Transactional
    @Test
    public void testUpdate(){
        // 3번 고객의 city 변경
        Member member = memberRepository.findById(3L).get();
        member.changeCity("부산"); // managed entity 는 변경사항 감지 기능이 있다.(dirty checking)
        // 그 기능 덕분에 save를 부르지 않아도 되는 것이다.
        // 3번 Item 수량 변경
        Item item = itemRepository.findById(3L).get();
        item.changeQuantity(10);
        //Order 주문 상태 변경 -> cancel
        
        Order order = orderRepository.findById(3L).get();
        order.changeOrderStatus(OrderStatus.CANCEL);

    }

    @Commit
    @Transactional
    @Test
    public void testRemove(){
        // 주문 제거
        // order, order_item 제거
        // 방법 1) 자식 삭제 -> 부모 삭제
        orderItemRepository.deleteById(1L);
        orderRepository.deleteById(1L);
    }
    @Commit
    @Transactional
    @Test
    public void testCascadeRemove(){
        // 주문 제거
        // order, order_item 제거
        // 방법 2) 부모 삭제 시 자식제거(sql -> on delete cascade)
        orderRepository.deleteById(3L);
    }
    @Commit
    @Transactional
    @Test
    public void testIorphanRemove(){
        // 주문 조회
        Order order = orderRepository.findById(4L).get();
        System.out.println(order);
        // 주문 아이템 조회
        System.out.println(order.getOrderItems());
        // 리스트에서 삭제하려면 orphan true를 order.java에서 해주어야한다.
        order.getOrderItems().remove(0);
        System.out.println("삭제 후 "+order.getOrderItems());
        // orderRepository.save(order);
    }
    
    @Test
    public void testDelivery(){
        Order order = orderRepository.findById(5L).get();
        
    }

    @Commit
    @Transactional
    @Test
    public void orderDeliveryTest(){
         // 2번 상품을 3번 고객이 주문
        Member member = memberRepository.findById(3L).get();
        Item item = itemRepository.findById(2L).get();
        Delivery delivery = Delivery.builder()
        .city("서울")
        .street("114")
        .zipcode("11061")
        .deliveryStatus(DeliveryStatus.COMP)
        .build();
        deliveryRepository.save(delivery);
        
        
        Order order = Order.builder()
        .member(member)
        .orderStatus(OrderStatus.ORDER)
        .delivery(delivery)
        .build();
        OrderItem orderItem = OrderItem.builder()
        .item(item)
        .orderPrice(230000)
        .count(1)
        .build();
        order.addOrderItem(orderItem);
        orderRepository.save(order);

    }
    @Commit
    @Transactional
    @Test
    public void testCascadeDelivery(){
         // 2번 상품을 3번 고객이 주문
        Member member = memberRepository.findById(3L).get();
        Item item = itemRepository.findById(2L).get();
        Delivery delivery = Delivery.builder()
        .city("서울")
        .street("114")
        .zipcode("11061")
        .deliveryStatus(DeliveryStatus.COMP)
        .build();
        // deliveryRepository.save(delivery);
        
        
        Order order = Order.builder()
        .member(member)
        .orderStatus(OrderStatus.ORDER)
        .delivery(delivery)
        .build();
        OrderItem orderItem = OrderItem.builder()
        .item(item)
        .orderPrice(230000)
        .count(1)
        .build();
        order.addOrderItem(orderItem);
        orderRepository.save(order);

    }

    @Transactional(readOnly = true)
    @Test
    public void orderReadTest2(){
    Order order = orderRepository.findById(1L).get();
    // order에는 member, orderItems, delivery 와 연결되어서 셋 모두 조회가능
    System.out.println(order);
    System.out.println(order.getMember().getName());
    System.out.println(order.getOrderItems());
    System.out.println(order.getOrderItems().get(0));
    // delivery 때만 leftjoin 
    System.out.println(order.getDelivery().getCity());
    }

    @Transactional(readOnly = true)
    @Test
    public void memberReadTest2(){
    Member member = memberRepository.findById(3L).get();
    // order에는 member, orderItems, delivery 와 연결되어서 셋 모두 조회가능
    System.out.println(member);
    System.out.println(member.getOrders());
    List<Order> orders = member.getOrders();
    orders.forEach(order->{
        System.out.println(order.getDelivery());
        System.out.println(order.getMember());
        System.out.println(order.getOrderItems());
    });
    }
    @Transactional(readOnly = true)
    @Test
    public void orderItemReadTest(){
    OrderItem orderItem = orderItemRepository.findById(1L).get();
    // order에는 member, orderItems, delivery 와 연결되어서 셋 모두 조회가능
    System.out.println(orderItem);
    System.out.println(orderItem.getOrder());
    Order order = orderItem.getOrder();
    
        System.out.println(order.getDelivery());
        System.out.println(order.getMember());
        System.out.println(orderItem.getItem());
    
    }
    //ManyToMnay 설정을 ManyToOne관계로 작성후 테스트 구문
    @Test
    public void categoryTest(){
        Item item = itemRepository.findById(4L).get();

        Category category = Category.builder().name("신혼용품").build();
        categoryRepository.save(category);
        CategoryItem categoryItem = CategoryItem.builder()
        .category(category)
        .item(item)
        .build();

        categoryItemRepository.save(categoryItem);
        //기존카테고리에 아이템만 추가
        category= categoryRepository.findById(1L).get();
        categoryItem = CategoryItem.builder()
        .category(category)
        .item(item)
        .build();
        categoryItemRepository.save(categoryItem);


    }
    @Transactional(readOnly = true)
    @Test
    public void categoryItemReadTest(){
        CategoryItem categoryItem = categoryItemRepository.findById(1L).get();
        System.out.println(categoryItem);
        // tostring exclude 해놔서 내용이 없다.  자세하게 정보조회를 위해선
        System.out.println(categoryItem.getCategory());
        System.out.println(categoryItem.getItem());

        // 양방향 연 뒤
        Category category = categoryRepository.findById(1L).get();
        System.out.println(category);
        System.out.println(category.getCategoryItems());
        Item item = itemRepository.findById(4L).get();
        System.out.println(item);
        System.out.println(item.getCategoryItems());

    }











    //-------------------------------
    //ManyToMany 설정을 JPA 에게 시키는 경우 
    // 테스트 구문

    // @Test
    // public void categoryTest(){
    //     Item Item = itemRepository.findById(3L).get();
    //     Category category = Category.builder().name("가전제품").build();
    //     category.getItems().add(Item);
    //     categoryRepository.save(category);
    //     category = Category.builder().name("생활용품").build();
    //     category.getItems().add(Item);
    //     categoryRepository.save(category);
    // }
    // @Test
    // public void categoryReadTest(){
    //     Category category = categoryRepository.findById(1L).get();
    //     //카테고리
    //     System.out.println(category);
    //     //카테고리에 속한 아이템
    //     System.out.println(category.getItems());
    // }
    // @Test
    // public void itemReadTest(){
    //     Item item = itemRepository.findById(3L).get();
    //     // 아이템
    //     System.out.println(item);
    //     // 카테고리
    //     System.out.println(item.getCategories());
    // }

}
