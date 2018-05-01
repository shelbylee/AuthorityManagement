package com.lxb.bean;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Mail {

    private String subject;

    private String message;

    private Set<String> receivers;
}
