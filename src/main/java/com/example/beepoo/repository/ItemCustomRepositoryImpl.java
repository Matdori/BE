package com.example.beepoo.repository;

import static com.example.beepoo.entity.QItem.item;
import static org.springframework.util.StringUtils.hasText;

import com.example.beepoo.dto.ItemRequestDto;
import com.example.beepoo.dto.ItemResponseDto;
import com.example.beepoo.dto.QItemResponseDto;
import com.example.beepoo.enums.ItemStatusEnum;
import com.example.beepoo.util.OrderByNull;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.querydsl.jpa.impl.JPAUpdateClause;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

@Repository
public class ItemCustomRepositoryImpl implements ItemCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public ItemCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Override
    public List<ItemResponseDto> getItemList(ItemRequestDto condition, Pageable pageable) {
        List<ItemResponseDto> itemList = jpaQueryFactory
            .select(
                new QItemResponseDto(
                    item.seq,
                    item.name,
                    item.typeCode.id,
                    item.status,
                    item.serial,
                    item.comment,
                    item.createDate,
                    item.createUser,
                    item.modifyDate,
                    item.modifyUser
                )
            )
            .from(item)
            .where(
                nameEq(condition.getName()),
                typeCodeEq(condition.getTypeCode()),
                statusEq(condition.getStatus()),
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
        JPAUpdateClause clause = jpaQueryFactory.update(item).where(item.seq.eq(itemDto.getSeq()));
        if (hasText(itemDto.getName())) {
            clause.set(item.name, itemDto.getName());
        }
        if (itemDto.getTypeCode() != null) {
            clause.set(item.typeCode.id, itemDto.getTypeCode());
        }
        if (itemDto.getStatus() != null) {
            clause.set(item.status, itemDto.getStatus());
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
        jpaQueryFactory.delete(item).where(item.seq.in(seqs)).execute();
    }

    private BooleanExpression nameEq(String name) {
        return hasText(name) ? item.name.contains(name) : null;
    }

    private BooleanExpression typeCodeEq(Long typeCode) {
        return typeCode != null ? item.typeCode.id.in(typeCode) : null;
    }

    private BooleanExpression statusEq(ItemStatusEnum status) {
        return status != null ? item.status.in(status) : null;
    }

    private BooleanExpression serialEq(String serial) {
        return hasText(serial) ? item.serial.contains(serial) : null;
    }

    private BooleanExpression commentEq(String comment) {
        return hasText(comment) ? item.comment.contains(comment) : null;
    }

    private BooleanExpression createDateRangeEq(String createDateRange) {
        if (hasText(createDateRange)) {
            String start = createDateRange.split("~")[0] + " 00:00:00";
            String end = createDateRange.split("~")[1] + " 23:59:59";

            LocalDateTime startDate = LocalDateTime.parse(start, formatter);
            LocalDateTime endDate = LocalDateTime.parse(end, formatter);

            return item.createDate.between(startDate, endDate);
        } else {
            return null;
        }
    }

    private BooleanExpression createUserEq(String createUser) {
        return hasText(createUser) ? item.createUser.contains(createUser) : null;
    }

    private BooleanExpression modifyDateRangeEq(String modifyDateRange) {
        if (hasText(modifyDateRange)) {
            String start = modifyDateRange.split("~")[0] + " 00:00:00";
            String end = modifyDateRange.split("~")[1] + " 23:59:59";

            LocalDateTime startDate = LocalDateTime.parse(start, formatter);
            LocalDateTime endDate = LocalDateTime.parse(end, formatter);

            return item.modifyDate.between(startDate, endDate);
        } else {
            return null;
        }
    }

    private BooleanExpression modifyUserEq(String modifyUser) {
        return hasText(modifyUser) ? item.modifyUser.contains(modifyUser) : null;
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
            PathBuilder pathBuilder = new PathBuilder(item.getType(), item.getMetadata());

            result = new OrderSpecifier(o.isAscending() ? Order.ASC : Order.DESC, pathBuilder.get(o.getProperty()));
        }

        return result;
    }
}
