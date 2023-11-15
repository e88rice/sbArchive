package com.project.sbarchive.dto.page;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageRequestDTO {

    @Builder.Default
    private int page=1;

    @Builder.Default
    private int size=10;

    public int getSkip() {
        return (page-1)*size;
    }

    // 검색 관련
    private String[] types; // 검색의 종류


    private String keyword;

    public String getLink() {
        StringBuilder builder = new StringBuilder();
        builder.append("page=" + this.page);
        builder.append("&size=" + this.size);
        if(this.types != null && this.types.length > 0) {
            for (int i = 0; i < this.types.length ; i++) {
                builder.append("&types=" + types[i]);
            }
        }
        if (this.keyword != null) {
            try{
                builder.append("&keyword=" + URLEncoder.encode(keyword,"UTF-8"));
            }catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return builder.toString();
    }

}
