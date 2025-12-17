package com.example.board.post.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.board.member.entity.QMember;
import com.example.board.post.entity.Board;
import com.example.board.post.entity.QBoard;
import com.example.board.reply.entity.QReply;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPQLQuery;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class SearchBoardRepositoryImpl extends QuerydslRepositorySupport implements SearchBoardRepository{

    public SearchBoardRepositoryImpl() {
        super(Board.class);
    }

    @Override
    public Page<Object[]> list(String type, String keyword, Pageable pageable) {
        log.info("board + reply + member join");

        // q클래스
        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;

        // 보드테이블과 멤버테이블 조인, 리플라이테이블과 조인
        // 보드기준 멤버, 리플라이 기준 보드
        JPQLQuery<Board> query = from(board)
                .leftJoin(member).on(board.writer.eq(member))
                .leftJoin(reply).on(reply.board.eq(board));
        JPQLQuery<Tuple> tuple = query.select(board,member, reply.count());
        // DB에서 Tuple == 레코드 == 하나의 행
        
        // where 절이 있어야 페이지 나누기 가능해진다. 없으면 단순히 select만 이뤄져서
        BooleanBuilder builder = new BooleanBuilder();
        builder.and(board.bno.gt(0)); // where board.bno >0

        // type = "t" or type = "c"  type= "w" or type = "tc" or type = "tcw"
        // tc or tcw -> t, c, w 로 개별로 잘라내야함 split

        if (type != null) {
            String[] typearr = type.split("");

            BooleanBuilder conditionBuilder = new BooleanBuilder();

            for (String t : typearr) {
                switch (t) {
                    case "t":
                        conditionBuilder.or(board.title.contains(keyword));
                        break;
                    case "c":
                        conditionBuilder.or(board.content.contains(keyword));
                        break;
                    case "w":
                        // conditionBuilder.or(board.writer.email.contains(keyword));
                        conditionBuilder.or(member.email.contains(keyword));
                        break;
                }
            }
            builder.and(conditionBuilder);
        }
        // 만든 where 조건들을 tuple에 집어넣기
        tuple.where(builder);
        // orderby
        Sort sort = pageable.getSort();
        // //sort기준 여러개 있을 수 있다.
        sort.stream().forEach(order->{
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();
            PathBuilder orderByExpression = new PathBuilder<>(Board.class, "board");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
            
        });

        // sort 기준이 하나만 존재했을 때의 경우. 
        // tuple.orderBy(board.bno.desc());
        
        tuple.groupBy(board);

        // page 처리
        tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        log.info("===================");
        log.info(query);
        log.info("===================");
        
        List<Tuple> result = tuple.fetch();
        long count = tuple.fetchCount(); // 전체 개수
        // 리스트 튜플을 리스트 오브젝트[] 로 변경해줘서 받아야함
        List<Object[]> list = result.stream().map(t->t.toArray()).collect(Collectors.toList());

        // List<Object[]> list, count, pageable 을 리턴해주어야 함
        // Page로 보내야, 다른 곳에서 받아서 사용할 수 있다. 
        return new PageImpl<>(list,pageable,count);
    }

    @Override
    public Object[] getBoardByBno(Long bno) {

        // q클래스
        QBoard board = QBoard.board;
        QMember member = QMember.member;
        QReply reply = QReply.reply;

        
        JPQLQuery<Board> query = from(board)
                .leftJoin(member).on(board.writer.eq(member))
                .leftJoin(reply).on(reply.board.eq(board))
                .where(board.bno.eq(bno));
        JPQLQuery<Tuple> tuple = query.select(board,member, reply.count());
        System.out.println(tuple);
        Tuple result = tuple.fetchFirst();
        return result.toArray();
    }
    
}
