package com.example.study_3week.controller;

import com.example.study_3week.domain.Item;
import com.example.study_3week.repository.ItemRepository;
import com.example.study_3week.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class TestController {

    @Autowired
    SearchService searchService;

    @Autowired
    ItemRepository itemRepository;

    @GetMapping("/test")
    public String test(){
        Item item1 = new Item();
        item1.setName("과일");
        item1.setContent("사과 바나나 포도 키위");
        item1.setOrigin("베트남");
        item1.setDate(LocalDate.now().minusDays(5));
        item1.setPrice(1000L);
        itemRepository.save(item1);

        Item item2 = new Item();
        item2.setName("한식");
        item2.setContent("김치 된장 불고기");
        item2.setOrigin("한국");
        item2.setDate(LocalDate.now().minusDays(4));
        item2.setPrice(2000L);
        itemRepository.save(item2);

        Item item3 = new Item();
        item3.setName("중식");
        item3.setContent("꿔바로우 마라탕 짬뽕");
        item3.setOrigin("중국");
        item3.setDate(LocalDate.now().minusDays(3));
        item3.setPrice(3000L);
        itemRepository.save(item3);

        Item item4 = new Item();
        item4.setName("일식");
        item4.setContent("스시 우동 소바");
        item4.setOrigin("일본");
        item4.setDate(LocalDate.now().minusDays(2));
        item4.setPrice(4000L);
        itemRepository.save(item4);

        Item item5 = new Item();
        item5.setName("양식");
        item5.setContent("피자 파스타 라자냐");
        item5.setOrigin("유럽");
        item5.setDate(LocalDate.now().minusDays(1));
        item5.setPrice(5000L);
        itemRepository.save(item5);

        return "home";
    }
}
