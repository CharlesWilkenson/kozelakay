package com.management.system.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.time.ZoneOffset;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class ResponseDTO<T>{
    private String status;
    private String message;
    private T data;

    protected ApiErrorResponse apiErrorResponse;

    @Override
    public String toString() {
        return String.format("Response{status='%s', responseMessage='%s', data=%s}", status,
                message, data);
    }

    public static <T> ResponseDTO<T> createSuccessResponse(HttpStatus status, String message) {
        ResponseDTO<T> response = new ResponseDTO<>();
        response.setStatus(String.valueOf(status.value()));
        response.setMessage(message);
        return response;
    }

    public static <T> ResponseDTO<T> createErrorResponse(HttpStatus httpStatus, String message) {
        ResponseDTO<T> response = new ResponseDTO<>();
        ApiErrorResponse apiErrorResponse =   ApiErrorResponse
                .builder()
                .message(message)
                .error_code(String.valueOf(httpStatus.value()))
                .status(httpStatus)
                .timeStamp(LocalDateTime.now(ZoneOffset.UTC))
                .build();

        response.setApiErrorResponse(apiErrorResponse);
        return response;
    }
}
