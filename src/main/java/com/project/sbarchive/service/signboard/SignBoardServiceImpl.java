package com.project.sbarchive.service.signboard;


import com.project.sbarchive.dto.page.PageRequestDTO;
import com.project.sbarchive.dto.page.PageResponseDTO;
import com.project.sbarchive.dto.signboard.SignBoardAllDTO;
import com.project.sbarchive.dto.signboard.SignBoardDTO;
import com.project.sbarchive.dto.user.UserDTO;
import com.project.sbarchive.mapper.signboard.SignBoardFileMapper;
import com.project.sbarchive.mapper.signboard.SignBoardMapper;
import com.project.sbarchive.vo.signboard.SignBoardVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service
@Log4j2
@RequiredArgsConstructor
public class SignBoardServiceImpl implements SignBoardService {

    // @Autowired 들어가면 final 없어도 되고(자동으로 주입해 주기 때문에), 안 들어가면 final 들어가야 함
    // final : 생성자 생성할 때(객체 생성할 때) 못 바꾸게 무조건 넣어야 함
    private final ModelMapper modelMapper;
    private final SignBoardMapper signBoardMapper;

    @Override
    public int addSignboard(SignBoardDTO signBoardDTO) {
        log.info("signBoardDTO: "+signBoardDTO);
        SignBoardVO signBoardVO = modelMapper.map(signBoardDTO, SignBoardVO.class);
        log.info("signBoardVO: " + signBoardVO);
        signBoardMapper.addSignboard(signBoardVO);
        return signBoardVO.getSignboardId();
    }

    @Override
    public SignBoardDTO getSignboard(int signboardId) {
        return modelMapper.map(signBoardMapper.getSignboard(signboardId), SignBoardDTO.class);
    }

    @Override
    public int getCount() {
        return signBoardMapper.getCount();
    }

    @Override
    public ArrayList<SignBoardAllDTO> getSignboardList() {
        ArrayList<SignBoardAllDTO> dtoList = signBoardMapper.getSignboardList();
//        for(SignBoardAllDTO dto : dtoList) {
//            log.info(dto);
//        }
        return dtoList;
    }

    @Override
    public PageResponseDTO<SignBoardAllDTO> getSignboardListWithPaging(PageRequestDTO pageRequestDTO) {

        List<SignBoardAllDTO> dtoList=signBoardMapper.getSignboardListWithPaging(pageRequestDTO);
//        for(BoardVO boardVO:voList) {
//            dtoList.add(modelMapper.map(boardVO, BoardDTO.class));
//        }
        int total=signBoardMapper.getCount();

        return PageResponseDTO.<SignBoardAllDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO).build();
    }

    @Override
    public Integer modifySignboard(int signboardId, String content) {
        String originMDate = String.valueOf(signBoardMapper.getSignboard(signboardId).getModDate()); // 기존 수정날짜 저장

        signBoardMapper.modifySignboard(signboardId, content); // PK값과 변경된 내용을 전달하여 수정 처리

        String newMDate = String.valueOf(signBoardMapper.getSignboard(signboardId).getModDate()); // 변경 후 수정날짜 저장

        boolean result = originMDate.equals(newMDate); // 수정 결과값 true, false.  false(= 다르다가 나와야 성공)

        return result ? 0 : 1; // 다르면(성공) 1, 같으면(실패) 0
    }

    @Override
    public int removeSignboard(int signboardId) {
        return signBoardMapper.removeSignboard(signboardId);
    }

    @Override
    public PageResponseDTO<SignBoardAllDTO> getSearchSignboardList(String keyword, PageRequestDTO pageRequestDTO) {
        List<SignBoardAllDTO> dtoList = signBoardMapper.getSearchSignboardList(keyword, pageRequestDTO.getSkip(), pageRequestDTO.getSize());

        int total = signBoardMapper.getSearchCount(keyword);
        log.info(total);

        return PageResponseDTO.<SignBoardAllDTO>withAll()
                .dtoList(dtoList)
                .total(total)
                .pageRequestDTO(pageRequestDTO).build();
    }

    @Override
    public ArrayList<SignBoardAllDTO> getSearchSBList(String keyword) {
        return signBoardMapper.getSearchSBList(keyword);
    }

    @Override
    public int getSearchCount(String keyword) {
        return signBoardMapper.getSearchCount(keyword);
    }

}
