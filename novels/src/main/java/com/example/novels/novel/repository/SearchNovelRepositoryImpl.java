package com.example.novels.novel.repository;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import com.example.novels.novel.entity.Novel;
import com.example.novels.novel.entity.QGenre;
import com.example.novels.novel.entity.QGrade;
import com.example.novels.novel.entity.QNovel;
import org.springframework.data.domain.PageImpl;
import com.querydsl.core.types.Order;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;

public class SearchNovelRepositoryImpl extends QuerydslRepositorySupport implements SearchNovelRepository{

    public SearchNovelRepositoryImpl() {
        super(Novel.class);
    }

    @Override
    public Object[] getNovelId(Long id) {
        // 하나 조회
        // novel_id, author, title, 장르명, 평점
        QNovel novel = QNovel.novel;
        QGenre genre = QGenre.genre;
        QGrade grade = QGrade.grade;

        JPQLQuery<Novel> query = from(novel).leftJoin(genre).on(novel.genre.eq(genre)).where(novel.id.eq(id));

        JPQLQuery<Double> ratingAVG = JPAExpressions.select(grade.rating.avg()).from(grade).where(grade.novel.eq(novel)).groupBy(grade.novel);

        JPQLQuery<Tuple> tuple = query.select(novel,genre, ratingAVG);
        Tuple result = tuple.fetchFirst();

        return result.toArray();

    }

    @Override
    public Page<Object[]> list(Long genreId, String keyword, Pageable pageable) {
        QNovel novel = QNovel.novel;
        QGenre genre = QGenre.genre;
        QGrade grade = QGrade.grade;

        JPQLQuery<Novel> query = from(novel).leftJoin(genre).on(novel.genre.eq(genre));

        // novel id 별 평점 평균
        JPQLQuery<Double> ratingAVG = JPAExpressions.select(grade.rating.avg()).from(grade).where(grade.novel.eq(novel)).groupBy(grade.novel);

        JPQLQuery<Tuple> tuple = query.select(novel,genre,ratingAVG);
        
        BooleanBuilder builder = new BooleanBuilder();
        builder.and((novel.id.gt(0L)));


        // 검색용
        BooleanBuilder booleanBuilder = new BooleanBuilder();
        if (genreId != null && genreId != 0) {
            booleanBuilder.and(novel.genre.id.eq(genreId));
        }

        // 검색용 (title or author)
        if (keyword != null && !keyword.isEmpty()) {
            booleanBuilder.and(
                novel.title.contains(keyword)
                .or(novel.author.contains(keyword))
            );
        }


        builder.and(booleanBuilder);

        // 만든 where 조건들을 tuple에 집어넣기
        tuple.where(builder);
        // orderby
        Sort sort = pageable.getSort();
        // //sort기준 여러개 있을 수 있다.
        sort.stream().forEach(order->{
            Order direction = order.isAscending() ? Order.ASC : Order.DESC;
            String prop = order.getProperty();
            PathBuilder<Novel> orderByExpression = new PathBuilder<>(Novel.class, "novel");
            tuple.orderBy(new OrderSpecifier(direction, orderByExpression.get(prop)));
            });

            tuple.offset(pageable.getOffset());
        tuple.limit(pageable.getPageSize());

        List<Tuple> result = tuple.fetch();
        long count = tuple.fetchCount(); // 전체 개수
        // 리스트 튜플을 리스트 오브젝트[] 로 변경해줘서 받아야함
        List<Object[]> list = result.stream().map(t->t.toArray()).collect(Collectors.toList());

        return new PageImpl<>(list,pageable,count);
    }
    
}
