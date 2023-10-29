package com.management.system.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
public class ContentRequestDTO {
    private Long id;
    @NotEmpty(message = "The title must not be null")
    private String title;

    @NotEmpty(message = "The brief must not be null")
    private String brief;

    @NotEmpty(message = "The content must not be null")
    private String content;

    private String authorId;
}
