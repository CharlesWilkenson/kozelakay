package com.management.system.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.management.system.entities.Status;
import lombok.Data;

import java.time.Instant;

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

    @JsonFormat(pattern = "dd/MM/yyyy HH:mm", timezone = "UTC")
    private Instant createdDate;

    private Status status;

}
