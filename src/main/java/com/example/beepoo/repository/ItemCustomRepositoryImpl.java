package com.example.beepoo.repository;

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
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.beepoo.entity.QItem.item;
import static com.example.beepoo.entity.QUser.user;
import static org.springframework.util.StringUtils.hasText;

@Repository
public class ItemCustomRepositoryImpl implements ItemCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public ItemCustomRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public Map<String, Object> getItemList(ItemRequestDto condition, Pageable pageable) {
        Map<String, Object> result = new HashMap<>();

        // 검색된 비품 목록 데이터
        List<ItemResponseDto> itemList = jpaQueryFactory
                .select(
                        new QItemResponseDto(
                                item.seq,
                                item.name,
                                item.typeCode.type,
                                item.status,
                                item.serial,
                                item.comment,
                                item.createDate,
                                item.askUser.userName,
                                item.askUser.departmentName
                        )
                )
                .from(item)
                .leftJoin(item.askUser, user)
                .where(
                        nameEq(condition.getName()),
                        askUserNameEq(condition.getUserName()),
                        typeCodeEq(condition.getTypeCode()),
                        statusEq(condition.getStatus()),
                        serialEq(condition.getSerial()),
                        commentEq(condition.getComment()),
                        startDateEq(condition.getStartDate()),
                        endDateEq(condition.getEndDate()),
                        modifyUserEq(condition.getModifyUser()),
                        departmentNameEq(condition.getDepartmentName())
                        )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(OrderBy(pageable))
                .fetch();

        // 현재 페이지 개수
        // int itemCnt = itemList.size();
        // 검색된 비품 목록 전체 개수
        long totalCnt = jpaQueryFactory
                .select(item.count())
                .from(item)
                .where(
                        nameEq(condition.getName()),
                        askUserNameEq(condition.getUserName()),
                        typeCodeEq(condition.getTypeCode()),
                        statusEq(condition.getStatus()),
                        serialEq(condition.getSerial()),
                        commentEq(condition.getComment()),
                        startDateEq(condition.getStartDate()),
                        endDateEq(condition.getEndDate()),
                        createUserEq(condition.getCreateUser()),
                        modifyUserEq(condition.getModifyUser()),
                        departmentNameEq(condition.getDepartmentName())
                )
                .fetchCount();

        result.put("totalCnt", totalCnt);
        result.put("itemList", itemList);

        return result;
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

    private BooleanExpression askUserNameEq(String userName) {
        return hasText(userName) ? item.askUser.userName.contains(userName) : null;
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

    private BooleanExpression startDateEq(String startDate) {
        return hasText(startDate) ?
                item.createDate.goe(LocalDateTime.parse(startDate + " 00:00:00", formatter)) : null;
    }

    private BooleanExpression endDateEq(String endDate) {
        return hasText(endDate) ?
                item.createDate.goe(LocalDateTime.parse(endDate + " 00:00:00", formatter)) : null;
    }

    private BooleanExpression createUserEq(String createUser) {
        return hasText(createUser) ? item.createUser.contains(createUser) : null;
    }

    private BooleanExpression modifyUserEq(String modifyUser) {
        return hasText(modifyUser) ? item.modifyUser.contains(modifyUser) : null;
    }

    private BooleanExpression departmentNameEq(String departmentName) {
        return hasText(departmentName) ? item.askUser.departmentName.contains(departmentName) : null;
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
