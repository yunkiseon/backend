package com.example.board.repository;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Commit;
import org.springframework.transaction.annotation.Transactional;

import com.example.board.member.entity.Member;
import com.example.board.member.repository.MemberRepository;
import com.example.board.post.dto.PageRequestDTO;
import com.example.board.post.entity.Board;
import com.example.board.post.repository.BoardRepository;
import com.example.board.reply.entity.Reply;
import com.example.board.reply.repository.ReplyRepository;

@Disabled
@SpringBootTest
public class BoardRepositoryTest {
    
    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private ReplyRepository replyRepository;

    @Test
    public void insertMemberTest(){
        IntStream.rangeClosed(1, 10).forEach(i->{
            Member member = Member.builder()
            .email("user"+i+"@gmail.com")
            .password("1111")
            .name("user"+i)
            .build();
            memberRepository.save(member);
        });
    }
    @Test
    public void insertBoardTest(){
        IntStream.rangeClosed(1, 100).forEach(i->{

            int idx =  (int) (Math.random()* 10)+1;
            Member member = Member.builder().email("user"+idx+"@gmail.com").build();

            Board board = Board.builder()
            .title("title...."+i)
            .content("content...."+i)
            .writer(member)
            .build();
            boardRepository.save(board);
        });
    }
    @Test
    public void insertReplyTest(){
        IntStream.rangeClosed(1, 100).forEach(i->{

            Long idx =  (long) (Math.random()* 100)+1;
            Board board = Board.builder().bno(idx).build();
            Reply reply = Reply.builder().text("reply...."+i).replyer("guest"+i).board(board).build();
            replyRepository.save(reply);

          
        });}
        
    @Test
    public void insertReplyTest2(){

        Board board = Board.builder().bno(103L).build();

        IntStream.rangeClosed(1, 15).forEach(i->{

            Reply reply = Reply.builder().text("reply...."+i).replyer("guest"+i).board(board).build();
            replyRepository.save(reply);            
        });

    }
    @Transactional(readOnly = true)
    @Test
    public void readBoardTest(){
        // 아래는 select를 계속 날림. 효율적이지 못하다. 그래서 repository에서 query문을 만든다.
        List<Board> list = boardRepository.findAll();
        list.forEach(board->{
            System.out.println(board);
            System.out.println(board.getWriter());
        });
    }
    @Test
    public void getBoardWithWriterListTest(){
        // 만든 query 문 활용 -> join on 활용
        List<Object[]> result = boardRepository.getBoardWithWriterList();
        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }
        
    }
    @Transactional
    @Test
    public void getBoardWithWriterTest(){
        // jpa 
        Board board = boardRepository.findById(33L).get();
        System.out.println(board);
        //댓글 가져오기 -> jpa에선 OneToMany를 해야만 할 수 있다. 
        // 전과 마찬가지로 select가 여러번 들어간다
        System.out.println(board.getReplies());
    }

    @Test
    public void getBoardWithWriterTest2(){
        List<Object[]> result = boardRepository.getBoardWithReply(33L);
        // for (Object[] objects : result) {
        //     System.out.println(Arrays.toString(objects));
        // } 아래와 동일
        result.forEach(obj -> System.out.println(Arrays.toString(obj)));
    }

    @Test
    public void getBoardWithReplyCountTest(){
        Pageable pageable = PageRequest.of(0, 10, Sort.by("bno").descending());
        Page<Object[]> result = boardRepository.getBoardWithReplyCount(pageable);
        // for (Object[] objects : result) {
        //     Board board = (Board) objects[0];
        //     Member member = (Member) objects[1];
        //     Long replyCnt = (Long) objects[2];
        //     System.out.println(board);
        //     System.out.println(member);
        //     System.out.println(replyCnt);
        // }  아래처럼 설정하는 것도 가능
        // Stream<Object[]> data = result.get();
        // // getcontent -> 실제 목록
        // Stream<Object[]> data2 = result.getContent().stream();
        result.get().forEach(obj->{
            // System.out.println(Arrays.toString(obj));
            Board board = (Board) obj[0];
            Member member = (Member) obj[1];
            Long replyCnt = (Long) obj[2];
            // 도 가능
        });
        //Arrays.toString(obj) 는 retrun한다.즉 변환이 목적이다 sout은 리턴값이 없다.
        // 그래서 아래의 과정이 필요하다. 때문에 위의 과정이 훨씬 편리하다.
        Function<Object[], String> f = Arrays::toString;
        result.get().forEach(obj -> System.out.println(f.apply(obj)));


    
    }
    @Test
    public void getBoardByBnoTest(){
        Object result = boardRepository.getBoardByBno(84L);
        Object[] arr = (Object[])result;
        System.out.println(Arrays.toString(arr));
    }

    @Commit
    @Transactional
    @Test
    public void deleteByBnoTest(){
        replyRepository.deleteByBno(91L);
        boardRepository.deleteById(91L);
    }

    //querydsl 테스트
    @Test
    public void listTest(){
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder()
        .page(0)
        .size(20)
        .type("tcw")
        .keyword("title")
        .build();

        Pageable pageable = PageRequest.of(pageRequestDTO.getPage(), pageRequestDTO.getSize()
        ,Sort.by("bno").descending().and(Sort.by("title").ascending()));

        Page<Object[]> result = boardRepository.list(pageRequestDTO.getType(),pageRequestDTO.getKeyword(),pageable);
        
        for (Object[] objects : result) {
            System.out.println(Arrays.toString(objects));
        }
    }



}
