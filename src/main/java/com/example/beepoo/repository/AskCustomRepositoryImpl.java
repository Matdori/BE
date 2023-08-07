package com.example.beepoo.repository;

import com.example.beepoo.dto.AskRequestDto;
import com.example.beepoo.dto.AskResponseDto;
import com.example.beepoo.dto.QAskResponseDto;
import com.example.beepoo.entity.QUser;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.beepoo.entity.QAsk.ask;
import static com.example.beepoo.entity.QItem.item;
import static com.example.beepoo.entity.QUser.user;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class AskCustomRepositoryImpl implements AskCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public AskCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // 요청자, 처리자 User 테이블을 구분하기 위한 confirmUser
    QUser confirmUser = new QUser("confirmUser");

    @Override
    public Map<String, Object> getAskList(AskRequestDto condition, Pageable pageable) {
        Map<String, Object> result = new HashMap<>();

        // 검색된 요청 목록 데이터
        List<AskResponseDto> askList = jpaQueryFactory
                .select(
                        new QAskResponseDto(
                                ask.seq,
                                item.name,
                                item.typeCode.type,
                                ask.type,
                                user.userName,
                                confirmUser.userName
                        )
                )
                .from(ask)
                .leftJoin(ask.askUser, user)
                .leftJoin(ask.confirmUser, confirmUser)
                .where(
                        itemNameEq(condition.getItemName()),
                        itemTypeCodeEq(condition.getItemTypeCode()),
                        askUserNameEq(condition.getAskUserName()),
                        confirmUserNameEq(condition.getConfirmUserName()),
                        typeEq(condition.getAskType()),
                        startDateEq(condition.getStartDate()),
                        endDateEq(condition.getEndDate()),
                        createUserEq(condition.getCreateUser()),
                        modifyUserEq(condition.getModifyUser())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(OrderBy(pageable))
                .fetch();

        // 검색된 요청 목록 전체 개수
        long totalCnt = jpaQueryFactory
                .select(ask.count())
                .from(ask)
                .leftJoin(ask.askUser, user)
                .leftJoin(ask.confirmUser, confirmUser)
                .where(
                        itemNameEq(condition.getItemName()),
                        itemTypeCodeEq(condition.getItemTypeCode()),
                        askUserNameEq(condition.getAskUserName()),
                        confirmUserNameEq(condition.getConfirmUserName()),
                        typeEq(condition.getAskType()),
                        startDateEq(condition.getStartDate()),
                        endDateEq(condition.getEndDate()),
                        createUserEq(condition.getCreateUser()),
                        modifyUserEq(condition.getModifyUser())
                )
                .fetchCount();

        result.put("totalCnt", totalCnt);
        result.put("askList", askList);

        return result;
    }

    // item 테이블 name 으로 비품명 조회
    private BooleanExpression itemNameEq(String itemName) {
        return hasText(itemName) ? item.name.contains(itemName) : null;
    }

    // item 테이블 typeCod 의 id 로 비품 종류 조회
    private BooleanExpression itemTypeCodeEq(Long itemTypeCode) {
        return itemTypeCode != null ? item.typeCode.id.in(itemTypeCode) : null;
    }

    // user 테이블 name 으로 요청자 조회
    private BooleanExpression askUserNameEq(String askUserName) {
        return hasText(askUserName) ? user.userName.contains(askUserName) : null;
    }

    // user 테이블 name 으로 처리자 조회
    private BooleanExpression confirmUserNameEq(String confirmUserName) {
        return hasText(confirmUserName) ? confirmUser.userName.contains(confirmUserName) : null;
    }

    private BooleanExpression typeEq(AskTypeEnum type) {
        return type != null ? ask.type.in(type) : null;
    }

    private BooleanExpression startDateEq(String startDate) {
        return hasText(startDate) ?
                ask.createDate.goe(LocalDateTime.parse(startDate + " 00:00:00", formatter)) : null;
    }

    private BooleanExpression endDateEq(String endDate) {
        return hasText(endDate) ?
                ask.createDate.goe(LocalDateTime.parse(endDate + " 00:00:00", formatter)) : null;
    }

    private BooleanExpression createUserEq(String createUser) {
        return hasText(createUser) ? ask.createUser.contains(createUser) : null;
    }

    private BooleanExpression modifyUserEq(String modifyUser) {
        return hasText(modifyUser) ? ask.modifyUser.contains(modifyUser) : null;
    }

    private OrderSpecifier OrderBy(Pageable pageable) {
        OrderSpecifier result = OrderByNull.getDefault();
        Sort sort = pageable.getSort();

        // 정렬 옵션이 없는 경우
        if (Sort.unsorted().equals(sort)) {
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
