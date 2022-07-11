package com.example.study_3week.repository;

import com.example.study_3week.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long>, JpaSpecificationExecutor<Item> {

    //save(), findOne(), findAll(), count(), delete()는 상속하는 것만으로도 제공공

    // 밑에꺼 다 list로 받는게 맞나??? // unique로 설정을 안했는데, 그러면 중복이 가능한데?
    List<Item> findByName(String name);

    List<Item> findByContentLike(String search); // like 검색 // list로 되는지 체크

    List<Item> findByOrigin(String origin); // list로 되는지 체크

    List<Item> findByDateBetween(LocalDate start, LocalDate end); // between 검색

    List<Item> findByPriceBetween(Long min, Long max);

    List<Item> findByDateBetweenAndPriceBetween(LocalDate start, LocalDate end, Long min, Long max);

    List<Item> findByDateBetweenOrPriceBetween(LocalDate start, LocalDate end, Long min, Long max);

    // Image를 어떤 형식으로 가져오는지??? // 그냥 주소(String)을 줘야 하나?
}
