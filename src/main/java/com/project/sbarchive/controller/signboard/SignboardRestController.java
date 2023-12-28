package com.project.sbarchive.controller.signboard;

import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.dto.signboard.SignBoardAllDTO;
import com.project.sbarchive.dto.signboard.SignBoardDTO;
import com.project.sbarchive.service.signboard.SignBoardFileService;
import com.project.sbarchive.service.signboard.SignBoardService;
import com.project.sbarchive.service.staticResource.StaticResourceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.core.codec.StringDecoder;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.*;

@RestController
@RequestMapping("/signboard")
@Log4j2
@RequiredArgsConstructor // 의존성 주입 위해
public class SignboardRestController {

    private final StaticResourceService sTest;
    private final SignBoardService signBoardService;
    private final SignBoardFileService signBoardFileService;
    private final ModelMapper modelMapper;

    // 게시글 리스트 페이지
    @GetMapping("/get/{page}/{index}")
    public SignBoardAllDTO getSignboard(@PathVariable("page") int pageNum, @PathVariable("index") int index) {
        // 페이지를 받아서 PageRequestDTO 생성
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(pageNum).size(9).build(); // 사이즈 9 아니면 12로 설정할거임

        // 페이징된 객체 리스트를 받아옴
        PageResponseDTO<SignBoardAllDTO> responseDTO = signBoardService.getSignboardListWithPaging(pageRequestDTO);

        // 전달받은 인덱스를 사용해 해당 객체 추출 후 이미지 리스트 추가 후 리턴
        SignBoardAllDTO signBoardAllDTO = responseDTO.getDtoList().get(index);
        signBoardAllDTO.setFiles(signBoardFileService.getSignboardImages(signBoardAllDTO.getSignboardId()));
        return signBoardAllDTO;
    }

    @PostMapping({"/modify/{signboardId}","/modify/{signboardId}/{content}"})
    public Integer modifySignboard(@RequestPart List<MultipartFile> files,
                              @PathVariable("signboardId") int signboardId,
                              @PathVariable(required = false) String content) {

        try {
            content = UriUtils.decode(content, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        log.info("content : " + content);
        log.info("signboardId : " + signboardId);
        content = content == null ? "" : content;

        for(MultipartFile file : files) {
            log.info(file.getOriginalFilename());
        }
        signBoardFileService.removeSignboardImages(signboardId); // 기존 이미지 제거
        if(files.size() > 0) {
            signBoardFileService.addSignboardImages(signboardId, files); // 새로운 이미지 추가
        }
        return signBoardService.modifySignboard(signboardId, content); // 성공 시 1, 실패 시 0
    }

    @DeleteMapping("/remove/{signboardId}")
    public Integer removeSignboard(@PathVariable("signboardId") int signboardId) {
        return signBoardService.removeSignboard(signboardId);
    }

    @GetMapping("/get/{signboardId}")
    public SignBoardAllDTO getSignboard(@PathVariable("signboardId") int signboardId) {
        SignBoardDTO signBoardDTO = signBoardService.getSignboard(signboardId);
        ArrayList<String> fileNames = signBoardFileService.getSignboardImages(signboardId);
        SignBoardAllDTO signBoardAllDTO = modelMapper.map(signBoardDTO, SignBoardAllDTO.class);
        signBoardAllDTO.setFiles(fileNames);

        return signBoardAllDTO;
    }

    @GetMapping("/get/search/{keyword}/{page}")
    public PageResponseDTO<SignBoardAllDTO> getSearchSignboards(@PathVariable("keyword") String keyword, @PathVariable("page") int page) {
        if (keyword.equals("#############")){
            log.info("ㅎㅇ");
        }
        PageRequestDTO pageRequestDTO = PageRequestDTO.builder().page(page).size(5).build();
        // defaultPageSize를 5로 바뀨고 싶음
        return signBoardService.getSearchSignboardList(keyword, pageRequestDTO);
    }
    @GetMapping("/get/search/{keyword}")
    public ArrayList<SignBoardAllDTO> getSearchSignboards(@PathVariable("keyword") String keyword) {
        return signBoardService.getSearchSBList(keyword);
    }

    @GetMapping("/test")
    public String test() {
        return sTest.getStaticFolderPath().replace("build\\resources\\main\\", "src\\main\\resources\\")+"signboard";
    }


}
