package com.project.sbarchive.dto.message;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MessageDTO {

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
