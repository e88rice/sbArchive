package com.project.sbarchive.controller.signboard;

import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.dto.signboard.SignBoardAllDTO;
import com.project.sbarchive.service.signboard.SignBoardFileService;
import com.project.sbarchive.service.signboard.SignBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.*;

@RestController
@RequestMapping("/signboard")
@Log4j2
@RequiredArgsConstructor // 의존성 주입 위해
public class SignboardRestController {

    private final SignBoardService signBoardService;
    private final SignBoardFileService signBoardFileService;

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

    @PostMapping("/modify/{signboardId}/{content}")
    public Integer modifySignboard(@RequestPart List<MultipartFile> files,
                              @PathVariable("signboardId") int signboardId,
                              @PathVariable("content") String content) {
//        List<String> fileNames = Arrays.stream(files.split("\\|")).toList();
//        List<MultipartFile> resultFiles = new ArrayList<>();

        log.info("content : " + content);
        log.info("signboardId : " + signboardId);

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



}