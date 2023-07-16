package com.example.beepoo.repository;

import com.example.beepoo.dto.ItemRequestDto;
import com.example.beepoo.dto.ItemResponseDto;
import com.example.beepoo.dto.QItemResponseDto;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static com.example.beepoo.entity.QItem.item;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class ItemCustomRepositoryImpl implements ItemCustomRepository {
    private final JPAQueryFactory jpaQueryFactory;

    public ItemCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    @Override
    public List<ItemResponseDto> getItemList(ItemRequestDto condition, Pageable pageable){
        List<ItemResponseDto> itemList = jpaQueryFactory
                .select(new QItemResponseDto(
                        item.seq,
                        item.name,
                        item.typeCode,
                        item.statusCode,
                        item.serial,
                        item.comment,
                        item.createDate,
                        item.createUser,
                        item.modifyDate,
                        item.modifyUser))
                .from(item)
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
                .select(item.count())
                .from(item)
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

    @Override
    public void updateItem(ItemRequestDto itemDto) {
        JPAUpdateClause clause = jpaQueryFactory
                .update(item)
                .where(item.seq.eq(itemDto.getSeq()));
                if (hasText(itemDto.getName())) {
                    clause.set(item.name, itemDto.getName());
                }
                if (itemDto.getTypeCode() != null) {
                    clause.set(item.typeCode, itemDto.getTypeCode());
                }
                if (itemDto.getStatusCode() != null) {
                    clause.set(item.statusCode, itemDto.getStatusCode());
                }
                if (hasText(itemDto.getSerial())) {
                    clause.set(item.serial, itemDto.getSerial());
                }
                if (hasText(itemDto.getComment())) {
                    clause.set(item.comment, itemDto.getComment());
                }
        clause.execute();
    }

    @Override
    public void deleteItem(List<Integer> seqs) {
        long count = jpaQueryFactory
                .delete(item)
                .where(item.seq.in(seqs))
                .execute();
    }

    private BooleanExpression nameEq(String name) {
        return hasText(name) ? item.name.contains(name) : null;
    }

    private BooleanExpression typeCodeEq(Integer typeCode) {
        return typeCode != null ? item.typeCode.in(typeCode) : null;
    }

    private BooleanExpression statusCodeEq(Integer statusCode) {
        return statusCode != null ? item.statusCode.in(statusCode) : null;
    }

    private BooleanExpression serialEq(String serial) {
        return hasText(serial) ? item.serial.contains(serial) : null;
    }

    private BooleanExpression commentEq(String comment) {
        return hasText(comment) ? item.comment.contains(comment) : null;
    }

    private BooleanExpression createDateRangeEq(String createDateRange) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if(hasText(createDateRange)){
            LocalDateTime startDate = LocalDateTime.parse(createDateRange.split("~")[0], formatter);
            LocalDateTime endDate = LocalDateTime.parse(createDateRange.split("~")[1], formatter);

            return item.createDate.between(startDate, endDate);
        }else{
            return null;
        }
    }

    private BooleanExpression createUserEq(String createUser) {
        return hasText(createUser) ? item.createUser.contains(createUser) : null;
    }

    private BooleanExpression modifyDateRangeEq(String modifyDateRange) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        if(hasText(modifyDateRange)){
            LocalDateTime startDate = LocalDateTime.parse(modifyDateRange.split("~")[0], formatter);
            LocalDateTime endDate = LocalDateTime.parse(modifyDateRange.split("~")[1], formatter);

            return item.modifyDate.between(startDate, endDate);
        }else{
            return null;
        }
    }

    private BooleanExpression modifyUserEq(String modifyUser) {
        return hasText(modifyUser) ? item.modifyUser.contains(modifyUser) : null;
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
            PathBuilder pathBuilder = new PathBuilder(item.getType(), item.getMetadata());

            result = new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC,
                pathBuilder.get(o.getProperty()));
        }
        
        return result;
    }
}
