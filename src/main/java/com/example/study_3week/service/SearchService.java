package com.example.study_3week.service;

import com.example.study_3week.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.study_3week.repository.ItemRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SearchService {

    @Autowired
    ItemRepository itemRepository;

    public List<Item> searchByName(String name){
        List<Item> items = itemRepository.findByName(name);
        return items;
    };

    public List<Item> searchByContent(String content){
        List<Item> items = itemRepository.findByContentLike(content);
        return items;
    };

    public List<Item> searchByOrigin(String origin){
        List<Item> items = itemRepository.findByOrigin(origin);
        return items;
    }

    public List<Item> searchByDate(LocalDate start, LocalDate end){
        List<Item> items = itemRepository.findByDateBetween(start, end);
        return items;
    }

    public List<Item> searchByPrice(Long min, Long max){
        List<Item> items = itemRepository.findByPriceBetween(min, max);
        return items;
    }

}
