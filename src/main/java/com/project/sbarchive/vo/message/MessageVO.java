package com.project.sbarchive.vo.message;

import lombok.*;

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
