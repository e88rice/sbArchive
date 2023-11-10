package com.project.sbarchive.controller.signboard;

import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.signboard.SearchResultDTO;
import com.project.sbarchive.dto.signboard.SignBoardAllDTO;
import com.project.sbarchive.dto.signboard.SignBoardDTO;
import com.project.sbarchive.service.signboard.PlaceSearchService;
import com.project.sbarchive.service.signboard.SignBoardService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RequiredArgsConstructor
@RestController
@Log4j2
@RequestMapping("/search")
public class SearchController {

    private final PlaceSearchService placeSearchService;

    private final SignBoardService signBoardService;


    @ApiOperation(value = "Search keyword", notes = "POST 방식으로 검색")
    @GetMapping(value = "/list/{query}") // JSON으로 처리하는 어노테이션
    public ArrayList<SearchResultDTO> search(@PathVariable("query") String query) {

        ArrayList<SearchResultDTO> results = placeSearchService.searchPlace(query);

        return results;
    }

    @ApiOperation(value = "Search List", notes = "GET 방식으로 리스트 가져오기")
    @GetMapping(value = "/list") // JSON으로 처리하는 어노테이션
    public ArrayList<SignBoardAllDTO> list() {
        log.info("헤이헤이");
        ArrayList<SignBoardAllDTO> results = signBoardService.getList();
        for(SignBoardAllDTO dto : results) {
            log.info("TQ" + dto);
        }

        return results;
    }

}
