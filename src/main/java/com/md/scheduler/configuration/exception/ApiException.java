package com.md.scheduler.configuration.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
class ApiException {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:dd:ss")
    private final LocalDateTime timestamp;
    private final String status;
    private final String message;
    private List<ApiSubException> details;
    private final String debugMessage;

    private void addSubException(ApiSubException ex) {
        if (this.details == null) {
            this.details = new ArrayList<>();
        }
        details.add(ex);
    }

}
