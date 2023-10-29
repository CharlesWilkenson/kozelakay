package com.management.system.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class ContentResponseDTO {
    private Long id;

    private String title;

    private String brief;

    private String content;
    
    private String fileName;

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", timezone = "UTC")
    private Instant createdDate;

}
