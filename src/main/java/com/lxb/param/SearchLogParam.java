package com.lxb.param;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SearchLogParam {

    private Integer type;

    // segmental value before update
    private String beforeSeg;

    // segmental value after update
    private String afterSeg;

    private String operator;

    // the start time of logs which you want to search
    // format: yyyy-MM-dd HH:mm:ss
    private String fromTime;

    // the end time of logs which you want to search
    // format: yyyy-MM-dd HH:mm:ss
    private String toTime;
}
