package com.lxb.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@ToString
public class SearchLogDto {

    // from bean: LogType
    private Integer type;

    // the segment of search keywords before updating
    private String beforeSeg;

    // the segment of search keywords after updating
    private String afterSeg;

    private String operator;

    // the start time of logs which you want to search
    // format: yyyy-MM-dd HH:mm:ss
    private Date fromTime;

    // the end time of logs which you want to search
    // format: yyyy-MM-dd HH:mm:ss
    private Date toTime;
}
