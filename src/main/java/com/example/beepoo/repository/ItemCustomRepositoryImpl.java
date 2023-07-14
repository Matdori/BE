package com.example.beepoo.repository;

import com.example.beepoo.dto.ItemDto;
import com.example.beepoo.dto.ItemSearchCondition;
import com.example.beepoo.dto.QItemDto;
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

import static com.example.beepoo.entity.QItemEntity.itemEntity;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class ItemCustomRepositoryImpl implements ItemCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public ItemCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<ItemDto> getItemList(ItemSearchCondition condition, Pageable pageable){
        List<ItemDto> itemList = jpaQueryFactory
                .select(new QItemDto(
                        itemEntity.seq,
                        itemEntity.name,
                        itemEntity.typeCode,
                        itemEntity.statusCode,
                        itemEntity.serial,
                        itemEntity.comment,
                        itemEntity.createDate,
                        itemEntity.createUser,
                        itemEntity.modifyDate,
                        itemEntity.modifyUser))
                .from(itemEntity)
                .where(
                        nameEq(condition.getName()),
                        typeCodeEq(condition.getTypeCode()),
                        statusCodeEq(condition.getStatusCode()),
                        serialEq(condition.getSerial()),
                        commentEq(condition.getComment()),
                        createDateRangeEq(condition.getCreateDate()),
                        createUserEq(condition.getCreateUser()),
                        modifyDateRangeEq(condition.getModifyDate()),
                        modifyUserEq(condition.getModifyUser())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(OrderBy(pageable))
                .fetch();

        /*// 현재 페이지 개수
        int itemCnt = itemList.size();
        // 전체 개수
        long totalItemCnt = jpaQueryFactory
                .select(itemEntity.count())
                .from(itemEntity)
                .where(
                        nameEq(condition.getName()),
                        typeCodeEq(condition.getTypeCode()),
                        statusCodeEq(condition.getStatusCode()),
                        serialEq(condition.getSerial()),
                        commentEq(condition.getComment()),
                        createDateRangeEq(condition.getCreateDate()),
                        createUserEq(condition.getCreateUser()),
                        modifyDateRangeEq(condition.getModifyDate()),
                        modifyUserEq(condition.getModifyUser())
                )
                .fetchCount();*/

        return itemList;
    }

    private BooleanExpression nameEq(String name) {
        return hasText(name) ? itemEntity.name.contains(name) : null;
    }

    private BooleanExpression typeCodeEq(String typeCode) {
        return hasText(typeCode) ? itemEntity.typeCode.in(Integer.parseInt(typeCode)) : null;
    }

    private BooleanExpression statusCodeEq(String statusCode) {
        return hasText(statusCode) ? itemEntity.statusCode.in(Integer.parseInt(statusCode)) : null;
    }

    private BooleanExpression serialEq(String serial) {
        return hasText(serial) ? itemEntity.serial.contains(serial) : null;
    }

    private BooleanExpression commentEq(String comment) {
        return hasText(comment) ? itemEntity.comment.contains(comment) : null;
    }

    private BooleanExpression createDateRangeEq(String createDateRange) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if(hasText(createDateRange)){
            LocalDateTime startDate = LocalDateTime.parse(createDateRange.split("~")[0], formatter);
            LocalDateTime endDate = LocalDateTime.parse(createDateRange.split("~")[1], formatter);

            return itemEntity.createDate.between(startDate, endDate);
        }else{
            return null;
        }
    }

    private BooleanExpression createUserEq(String createUser) {
        return hasText(createUser) ? itemEntity.createUser.contains(createUser) : null;
    }

    private BooleanExpression modifyDateRangeEq(String modifyDateRange) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if(hasText(modifyDateRange)){
            LocalDateTime startDate = LocalDateTime.parse(modifyDateRange.split("~")[0], formatter);
            LocalDateTime endDate = LocalDateTime.parse(modifyDateRange.split("~")[1], formatter);

            return itemEntity.modifyDate.between(startDate, endDate);
        }else{
            return null;
        }
    }

    private BooleanExpression modifyUserEq(String modifyUser) {
        return hasText(modifyUser) ? itemEntity.modifyUser.contains(modifyUser) : null;
    }

    private OrderSpecifier OrderBy(Pageable pageable) {
        Sort sort = pageable.getSort();
        // 정렬 옵션이 없는 경우
        if(Sort.unsorted().equals(sort)){
            return null;
        }

        // 정렬 옵션이 있는 경우
        OrderSpecifier result = null;
        for (Sort.Order o : pageable.getSort()) {
            PathBuilder pathBuilder = new PathBuilder(itemEntity.getType(), itemEntity.getMetadata());

            result = new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC,
                pathBuilder.get(o.getProperty()));
        }
        
        return result;
    }
}
