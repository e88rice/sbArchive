package com.project.sbarchive.controller;

import com.project.sbarchive.dto.SearchResultDTO;
import com.project.sbarchive.dto.SignBoardDTO;
import com.project.sbarchive.service.PlaceSearchService;
import com.project.sbarchive.service.SignBoardService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RestController
@Log4j2
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private PlaceSearchService placeSearchService;

    @Autowired
    private SignBoardService signBoardService;


    @ApiOperation(value = "Search keyword", notes = "POST 방식으로 검색")
    @GetMapping(value = "/list/{query}") // JSON으로 처리하는 어노테이션
    public ArrayList<SearchResultDTO> search(@PathVariable("query") String query) {

        ArrayList<SearchResultDTO> results = placeSearchService.searchPlace(query);

        return results;
    }

    @ApiOperation(value = "Search List", notes = "GET 방식으로 리스트 가져오기")
    @GetMapping(value = "/list") // JSON으로 처리하는 어노테이션
    public ArrayList<SignBoardDTO> list() {

        ArrayList<SignBoardDTO> results = signBoardService.getList();

        return results;
    }

}
