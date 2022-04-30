package com.wishes.postmanbe.model;

import lombok.*;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@RequiredArgsConstructor
@AllArgsConstructor
public class Mail implements Serializable {

    @NonNull
    private String recipient;

    @NonNull
    private String subject;

    private String content;

    private Map<String, String> params;

    private List<String> ccList;

    private List<String> bccList;
}