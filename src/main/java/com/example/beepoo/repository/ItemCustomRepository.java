package com.example.beepoo.repository;

import com.example.beepoo.dto.ItemRequestDto;
import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Pageable;

public interface ItemCustomRepository {
    Map<String, Object> getItemList(ItemRequestDto condition, Pageable pageable);

    void updateItem(ItemRequestDto itemDto);

    void deleteItem(List<Integer> seqs);
}
