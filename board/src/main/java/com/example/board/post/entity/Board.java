package com.example.board.post.entity;


import java.util.ArrayList;
import java.util.List;

import com.example.board.member.entity.Member;
import com.example.board.reply.entity.Reply;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString(exclude = {"writer", "replies"})
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "boardtbl")
public class Board extends BaseEntity{
    // 자동순번 id, 제목 title, 내용 content-1500, 작성자 writer-20, 작성일, 수정일자
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "bno")
    private Long bno;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, length = 1500)
    private String content;
    
    //member
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "email")
    private Member writer;

    // board -> reply

    @Builder.Default
    @OneToMany(mappedBy = "board")
    private List<Reply> replies = new ArrayList<>();
    
    public void changeTitle(String title){
    this.title = title;
    }
    public void changeContent(String content){
    this.content = content;
    }
}
