package com.project.sbarchive.dto.page;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PageResponseDTO<E> { // <제네릭> 쓴 이유: todo뿐만 아니라 member도 페이징해야 하기 때문. 제네릭<E> 안 하면 여러 개 만들어야 함

    @Builder.Default
    private int defaultPageSize = 10;
    private int page;
    private int size;
    private int total;

    private int start;
    private int end;
    private int last;
    private boolean prev;
    private boolean next;
    private List<E> dtoList;
    private int sno;


    @Builder(builderMethodName = "withAll") // 메소드로써 builder를 쓰겠다 = 즉, pageRequestDTO, dtoList, total만 build 하면 알아서 집어넣겠따
    public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E> dtoList, int total) {
        if(total <= 0) return;

        this.page = pageRequestDTO.getPage();
        this.size = pageRequestDTO.getSize();
        this.total = total;
        this.dtoList = dtoList;
        this.sno = (total - (this.page - 1) * this.size);

        this.last = (int) Math.ceil((double) total / size);

        calculateStartAndEnd();

        this.prev = this.start > 1;
        this.next = total > this.end * this.size;
    }

    private void calculateStartAndEnd() {
        this.end = (int) Math.ceil(this.page / 10.0) * 10;
        this.start = this.end - (defaultPageSize - 1);

        if (this.end > this.last) {
            this.end = this.last;
        }
    }


}
