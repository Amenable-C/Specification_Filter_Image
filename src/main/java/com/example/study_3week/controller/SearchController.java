package com.example.study_3week.controller;

import com.example.study_3week.domain.Item;
import com.example.study_3week.repository.ItemRepository;
import com.example.study_3week.repository.ItemSpecification;
import com.example.study_3week.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class SearchController {

    @Autowired
    SearchService searchService;

    @Autowired
    ItemRepository itemRepository;

    //name, content, origin, start(date), end(date), min(price), max(price), image
    @GetMapping("/items")
    public String search(
            Model model,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end,
            @RequestParam(required = false) Long min,
            @RequestParam(required = false) Long max,
            @RequestParam(required = false) String image
    ){
        Specification<Item> spec = (root, query, criteriaBuilder) -> null;

        if(name != null){
            spec = spec.and(ItemSpecification.equalName(name));
        }
        if(content != null){
            spec = spec.and(ItemSpecification.likeContent(content));
        }
        if(origin != null){
            spec = spec.and(ItemSpecification.equalOrigin(origin));
        }
        if(start != null && end != null){
            spec = spec.and(ItemSpecification.betweenDate(start, end));
        }
        if(start != null && end != null){
            spec = spec.and(ItemSpecification.greaterThanOrEqualDate(start));
        }
        if(start == null && end != null){
            spec = spec.and(ItemSpecification.lessThanOrEqualDate(end));
        }
        if(min != null && max != null){
            spec = spec.and(ItemSpecification.betweenPrice(min, max));
        }
        if(min != null && max == null){
            spec = spec.and(ItemSpecification.greaterThanOrEqualPrice(min));
        }
        if(min == null && max != null){
            spec = spec.and(ItemSpecification.lessThanOrEqualPrice(max));
        }


        model.addAttribute("specResults", itemRepository.findAll(spec));

//        model.addAttribute("itemByName", searchService.searchByName(name));
//        model.addAttribute("itemByContent", searchService.searchByContent(content));
//        model.addAttribute("itemByOrigin", searchService.searchByOrigin(origin));
        model.addAttribute("itemByDate", searchService.searchByDate(start, end));
        model.addAttribute("itemByPrice", searchService.searchByPrice(min, max));
        model.addAttribute("itemByDateAndPrice", searchService.searchByDateAndPrice(start, end, min, max));
        model.addAttribute("itemByDateOrPrice", searchService.searchByDateOrPrice(start, end, min, max));


        // 타임리프를 이용하여 검색값을 화면에 표현하기 위한 것.
        model.addAttribute("name", name);
        model.addAttribute("content", content);
        model.addAttribute("origin", origin);
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("min", min);
        model.addAttribute("max", max);
        // 이렇게 다 돌려주고, 프론트쪽에서 요청한 것에 대한 결과값만 쓰면 되지 않을까?
        return "result";
    }
}
