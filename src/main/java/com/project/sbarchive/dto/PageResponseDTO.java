package com.project.sbarchive.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PageResponseDTO<E> { // <제네릭> 쓴 이유: todo뿐만 아니라 member도 페이징해야 하기 때문. 제네릭<E> 안 하면 여러 개 만들어야 함
    @Builder(builderMethodName = "withAll") // 메소드로써 builder를 쓰겠다 = 즉, pageRequestDTO, dtoList, total만 build 하면 알아서 집어넣겠따
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total) {
        this.page=pageRequestDTO.getPage();
        this.size=pageRequestDTO.getSize();

        this.total=total;
        this.dtoList=dtoList; // responseDTO의 속성으로 dtoList를 넣음

        this.sno=(total - (this.page-1)*this.size);


        this.end=(int)(Math.ceil(this.page/10.0))*10; // 페이지를 10으로 나눈 값에(한 페이지에 10개씩 나오게 했기 때문에) 10 곱하면 끝 페이지가 된다
        // ex. 1 페이지의 끝 페이지: (1/10.0) * 10
        // 10 페이지의 끝 페이지: (10/10.0) * 10
        // 15 페이지의 끝 페이지: (15/10.0) * 10
        this.start=this.end-9; // 끝 페이지 -9가 곧 시작 페이지가 된다
        // ex. 끝 페이지: 10, 10-9=1: 시작 페이지

        int last=(int)(Math.ceil(total/(double)size));
        this.end = end > last ? last : end;

        this.prev=this.start>1;
        this.next=total>this.end * this.size; // total이 크면 next가 존재
    }

    private int page;
    private int size;
    private int total;

    // 시작 페이지 번호
    private int start;
    // 끝 페이지 번호
    private int end;

    // 이전 페이지의 존재 여부
    private boolean prev;
    // 다음 페이지의 존재 여부
    private boolean next;

    private List<E> dtoList;

    private int sno; // 페이지당 시작 번호
}
