package com.example.study_3week.controller;

import com.example.study_3week.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Controller
@RequiredArgsConstructor
public class SearchController {

    @Autowired
    SearchService searchService;

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
        //RequestParam(defaultValue)
        //RequestParam(required = false) -> null인 경우 고려해주기
        // null을 어떻게 처리하는지 // nullPointException 처리

        model.addAttribute("name", name);
        model.addAttribute("content", content);
        model.addAttribute("origin", origin);
        model.addAttribute("start", start);
        model.addAttribute("end", end);
        model.addAttribute("min", min);
        model.addAttribute("max", max);

        model.addAttribute("itemByName", searchService.searchByName(name));
        model.addAttribute("itemByContent", searchService.searchByContent(content));
        model.addAttribute("itemByOrigin", searchService.searchByOrigin(origin));
        model.addAttribute("itemByDate", searchService.searchByDate(start, end));
        model.addAttribute("itemByPrice", searchService.searchByPrice(min, max));

        // 이렇게 다 돌려주고, 프론트쪽에서 요청한 것에 대한 결과값만 쓰면 되지 않을까?
        return "result";
    }
}
