package com.example.beepoo.repository;

import com.example.beepoo.entity.Item;
import com.example.beepoo.entity.User;
import com.example.beepoo.enums.ItemStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    boolean existsByName(String name);

    @Modifying
    @Query("UPDATE Item i SET i.status = :status, i.askUser = :askUser where i.seq = :seq")
    int updateStatusAndaskUserBySeq(
        @Param(value = "seq") Integer seq,
        @Param(value = "status") ItemStatusEnum status,
        @Param(value = "askUser") User askUser
    );
}
