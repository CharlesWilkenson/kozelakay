package com.management.system.dto.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateMemberRequestDTO {

    @NotEmpty(message = "The firstname must not be null")
    private String firstname;

    @NotEmpty(message = "The lastname must not be null")
    private String lastname;

    @NotEmpty(message = "The phone must not be null")
    private String phone;

    @NotEmpty(message = "The description must not be null")
    private String description;

}
