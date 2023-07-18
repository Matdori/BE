package com.example.beepoo.repository;

import com.example.beepoo.dto.AskRequestDto;
import com.example.beepoo.dto.AskResponseDto;
import com.example.beepoo.dto.QAskResponseDto;
import com.example.beepoo.enums.AskTypeEnum;
import com.example.beepoo.util.OrderByNull;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.beepoo.entity.QAsk.ask;
import static com.example.beepoo.entity.QUser.user;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class AskCustomRepositoryImpl implements AskCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public AskCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public List<AskResponseDto> getAskList(AskRequestDto condition, Pageable pageable) {
        List<AskResponseDto> askList = jpaQueryFactory
                .select(new QAskResponseDto(
                        ask.seq,
                        ask.item,
                        ask.type,
                        ask.askUser,
                        ask.askMsg,
                        ask.askImg,
                        ask.confirmUser,
                        ask.confirmMsg,
                        ask.createDate,
                        ask.createUser,
                        ask.modifyDate,
                        ask.modifyUser))
                .from(ask)
                .leftJoin(ask.confirmUser, user)
                .where(
                        typeEq(condition.getAskType()),
                        createDateRangeEq(condition.getCreateDate()),
                        createUserEq(condition.getCreateUser()),
                        modifyDateRangeEq(condition.getModifyDate()),
                        modifyUserEq(condition.getModifyUser())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(OrderBy(pageable))
                .fetch();

        return askList;
    }

    // TODO(337): item 테이블 name 으로 비품명으로 조회
    // TODO(337): user 테이블 name 으로 요청자 조회
    // TODO(337): user 테이블 name 으로 처리자 조회

    private BooleanExpression typeEq(AskTypeEnum type) {
        return type != null ? ask.type.in(type) : null;
    }

    private BooleanExpression createDateRangeEq(String createDateRange) {
        if(hasText(createDateRange)){
            String start = createDateRange.split("~")[0] + " 00:00:00";
            String end = createDateRange.split("~")[1] + " 23:59:59";

            LocalDateTime startDate = LocalDateTime.parse(start, formatter);
            LocalDateTime endDate = LocalDateTime.parse(end, formatter);

            return ask.createDate.between(startDate, endDate);
        }else{
            return null;
        }
    }

    private BooleanExpression createUserEq(String createUser) {
        return hasText(createUser) ? ask.createUser.contains(createUser) : null;
    }

    private BooleanExpression modifyDateRangeEq(String modifyDateRange) {
        if(hasText(modifyDateRange)){
            String start = modifyDateRange.split("~")[0] + " 00:00:00";
            String end = modifyDateRange.split("~")[1] + " 23:59:59";

            LocalDateTime startDate = LocalDateTime.parse(start, formatter);
            LocalDateTime endDate = LocalDateTime.parse(end, formatter);

            return ask.modifyDate.between(startDate, endDate);
        }else{
            return null;
        }
    }

    private BooleanExpression modifyUserEq(String modifyUser) {
        return hasText(modifyUser) ? ask.modifyUser.contains(modifyUser) : null;
    }

    private OrderSpecifier OrderBy(Pageable pageable) {
        OrderSpecifier result = OrderByNull.getDefault();
        Sort sort = pageable.getSort();

        // 정렬 옵션이 없는 경우
        if(Sort.unsorted().equals(sort)){
            return result;
        }

        // 정렬 옵션이 있는 경우
        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(ask.getType(), ask.getMetadata());

            result = new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty()));
        }

        return result;
    }
}
