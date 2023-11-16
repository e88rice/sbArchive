package com.project.sbarchive.vo.message;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class MessageVO {

    private int index;
    private String senderId;
    private String receiverId;
    private String content;
    private boolean isRead;
    private boolean isSenderDeleted;
    private boolean isReceiverDeleted;
    private LocalDate sendDate;
    private LocalDate readDate;

}
