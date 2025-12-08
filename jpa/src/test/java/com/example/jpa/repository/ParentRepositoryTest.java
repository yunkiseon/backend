package com.example.jpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.example.jpa.entity.Child;
import com.example.jpa.entity.Parent;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;


@Transactional//클래스 안에서 메소드가 실행될 때 모든 작업을 하나로 처리+실행하고 rollback
@SpringBootTest
public class ParentRepositoryTest {
    @Autowired
    private ParentRepository parentRepository;
    @Autowired
    private ChildRepository childRepository;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void persistenceStateTest(){
        // 1. 비영속(new/transient)
        Parent p = Parent.builder().name("new 상태").build();
        System.out.println("1) 비영속 상태 : "+p);// 저장이 안된 객체
        // 2. 영속(managed)
        em.persist(p);//DB에서 insert 구문 실행
        System.out.println("2) 영속 상태 진입 : "+p);
        // 3. 영속상태에 있는 entity 변경 => Dirty Checking
        p.changeName("이름변경");//DB에서 update 구문 실행
        System.out.println("3) 영속 상태에서 값 변경 : "+p);
        // 4. DB에 반영 : flush
        em.flush();//commit 호출
        System.out.println("4) flush 후 DB 반영 완료 : " +p);
        // 5. 준영속(detached)
        em.detach(p);//update 구문 실행되지 않음 -> merge상태, dirtychecking 해야 update됨
        //  여기선 select만 일어남
        p.changeName("detach 상태에서 이름 변경");
        System.out.println("5) detach 상태에서 변경 : "+p);
        // 6. 다시 영속성 상태로 병합(merge)
        Parent merged = em.merge(p); // manage 상태로 보내고
        merged.changeName("merge 후 다시 영속 상태"); // dirtychecking을 하면 update반영됨
        System.out.println("6) merge 결과 영속 엔티티 : " + merged);
        em.flush();

        // 위의 것들은 여태까지 사용하던 save, findby 등으로 jpa가 간략해줘서 사용해왔다.

    }
    
    @Commit
    @Test
    public void testInsert(){
        Parent parent = Parent.builder().name("parent1").build();
        parentRepository.save(parent);
        
        Child child1 = Child.builder().name("first").parent(parent).build();
        Child child2 = Child.builder().name("second").parent(parent).build();
        Child child3 = Child.builder().name("third").parent(parent).build();
        childRepository.save(child1);
        childRepository.save(child2);
        childRepository.save(child3);
    }

    @Transactional(readOnly = true) // dirtychecking 하지 말아라. 순수 조회만 할 때 사용
    @Test
    public void testRead(){
        Parent parent = parentRepository.findById(1L).get();
        System.out.println(parent);
        //자식조회
        parent.getChilds().forEach(child -> System.out.println(child));   
    }
    @Commit
    @Test
    public void testUpdate(){
        Parent parent = parentRepository.findById(1L).get();
        parent.changeName("변경이름");
        // 여태까지는 따로따로된 entity매니저로 이루어지고 save로 저장한 것이고 
        // 지금은 transaction으로 묶여서 하나의 entity매니저로 이루어져 
        // 더티체킹이 일어나 save를 할 필요가 없어진 것이다. commit은 롤백 방지
    }

    //cascade
    @Commit
    @Test
    public void testCascadeInsert(){
        Parent parent = Parent.builder().name("parent3").build();
        parent.getChilds().add(Child.builder().name("child1").parent(parent).build());
        parent.getChilds().add(Child.builder().name("child2").parent(parent).build());
        parentRepository.save(parent);
        //childRepository.save가 없음.
        //부모 저장 시 관련있는 자식들도 같이 저장.
        //entity parent.java에 cascade 설정을 해주면 된다. 
    }
    @Commit
    @Test
    public void testCascadeInsert2(){
        Parent parent = Parent.builder().name("parent5").build();
        // child에 setParent 메소드 만든 뒤
        Child child1 = Child.builder().name("child3").parent(parent).build();
        child1.setParent(parent);
        Child child2 = Child.builder().name("child34").parent(parent).build();
        child2.setParent(parent);
        parentRepository.save(parent);
        
    }

    @Commit
    @Test
    public void testCascadeDelete(){
        // 부모 삭제 시 관련있는 자식들도 같이 삭제
        // 외래키 제약조건에서 삭제는 자식부터 해야함
        parentRepository.deleteById(6L);
        // parent.java에 cascade 설정을 해놓았기에 자식부터 삭제됨.
    }
    @Commit
    @Test
    public void testOrphanDelete(){
        // 부모 삭제 시 관련있는 자식들도 같이 삭제
        // 외래키 제약조건에서 삭제는 자식부터 해야함
        Parent p = parentRepository.findById(4L).get();
        // 자식 조회
        p.getChilds().forEach(c -> System.out.println(c));
        p.getChilds().remove(0); // p의 list에서 0번 자식 삭제
        // 이를 위해서 parent.java에서 cascade에 orphan 설정을 해주어야함
        parentRepository.save(p);
        
    }
}
