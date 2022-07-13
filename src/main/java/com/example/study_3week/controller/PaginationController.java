package com.example.study_3week.controller;

import com.example.study_3week.domain.Item;
import com.example.study_3week.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.awt.print.Pageable;

@Controller
@RequiredArgsConstructor
public class PaginationController {

    @Autowired
    ItemRepository itemRepository;

    @GetMapping("/pagination")
    public String pagination(Model model, Pageable pageable){
        Page<Item> items = itemRepository.findAll((org.springframework.data.domain.Pageable) pageable); // 나중에 gradle확인
        model.addAttribute("posts", items);
        return "pagination";
    }

}
