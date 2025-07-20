package com.transalation.management.task.dto;

import lombok.Data;

@Data
public class TranslationDTO {
    private String key;
    private String content;
    private String tag;
    private String locale;

}
