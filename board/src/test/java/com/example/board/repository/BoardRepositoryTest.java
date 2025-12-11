package com.example.board.repository;

import java.util.List;
import java.util.stream.IntStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.board.member.entity.Member;
import com.example.board.member.repository.MemberRepository;
import com.example.board.post.entity.Board;
import com.example.board.post.repository.BoardRepository;
import com.example.board.reply.entity.Reply;
import com.example.board.reply.repository.ReplyRepository;

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

            
        });

    }
    //querydsl 테스트
    @Test
    public void listTest(){
        List<Object[]> result = boardRepository.list();
        System.out.println(result);
    }



}
