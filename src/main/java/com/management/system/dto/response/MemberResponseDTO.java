package com.management.system.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberResponseDTO {

    private Long id;

    private String firstname;

    private String lastname;

    private String username;

    private String phone;

    private String email;

    private String description;

}
