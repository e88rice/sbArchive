package com.project.sbarchive.service.signboard;


import com.project.sbarchive.dto.signboard.SignBoardDTO;
import com.project.sbarchive.mapper.signboard.SignBoardMapper;
import com.project.sbarchive.vo.signboard.SignBoardVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@Log4j2
@RequiredArgsConstructor
public class SignBoardServiceImpl implements SignBoardService {

    // @Autowired 들어가면 final 없어도 되고(자동으로 주입해 주기 때문에), 안 들어가면 final 들어가야 함
    // final : 생성자 생성할 때(객체 생성할 때) 못 바꾸게 무조건 넣어야 함
    private final ModelMapper modelMapper;
    private final SignBoardMapper signBoardMapper;

    @Override
    public int add(SignBoardDTO signBoardDTO) {
        log.info("signBoardDTO: "+signBoardDTO);
        SignBoardVO signBoardVO = modelMapper.map(signBoardDTO, SignBoardVO.class);
        log.info("signBoardVO: " + signBoardVO);
        signBoardMapper.add(signBoardVO);
        return signBoardVO.getSingboardId();
    }

    @Override
    public ArrayList<SignBoardDTO> getList() {
        List<SignBoardVO> voList = signBoardMapper.getList();
        ArrayList<SignBoardDTO> dtoList = new ArrayList<>();
        voList.forEach( vo -> dtoList.add(modelMapper.map(vo, SignBoardDTO.class)));
        return dtoList;
    }

}
