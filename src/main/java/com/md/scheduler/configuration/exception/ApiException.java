package com.md.scheduler.configuration.exception;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ApiException {

    private String type;
    private String title;
    private Integer status;
    private String detail;
    private String instance;
}
