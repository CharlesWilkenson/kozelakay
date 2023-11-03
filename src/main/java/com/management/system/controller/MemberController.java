package com.management.system.controller;

import com.management.system.dto.request.RegisterMemberRequestDTO;
import com.management.system.dto.request.UpdateMemberRequestDTO;
import com.management.system.dto.response.MemberResponseDTO;
import com.management.system.dto.response.ResponseDTO;
import com.management.system.entities.Member;
import com.management.system.exception.AlreadyExistsException;
import com.management.system.exception.PasswordsNotMatchException;
import com.management.system.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

@RestController
@RequestMapping("/api/content/management/service/members/")
@RequiredArgsConstructor
@Slf4j
public class MemberController {
    private final MemberService service;
    private final ModelMapper mapper;

    @PostMapping("register")
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<ResponseDTO<Object>> register(@Valid @RequestBody RegisterMemberRequestDTO requestDTO) throws AlreadyExistsException, PasswordsNotMatchException {
        try {
            service.register(mapper.map(requestDTO, Member.class));
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
            return ResponseEntity.ok(ResponseDTO.createErrorResponse(HttpStatus.EXPECTATION_FAILED, "Failed to register new member"));
        }
        return ResponseEntity.ok(ResponseDTO.createSuccessResponse(HttpStatus.CREATED, "Member registered successfully"));
    }

    @GetMapping("view-profile")
    public ResponseEntity<ResponseDTO<MemberResponseDTO>> viewProfile() {

        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        MemberResponseDTO dto = mapper.map(service.findByEmail(email), MemberResponseDTO.class);
        ResponseDTO<MemberResponseDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setData(dto);
        return ResponseEntity.ok(responseDTO);
    }

    @PutMapping("edit")
    public ResponseEntity<ResponseDTO<UpdateMemberRequestDTO>> editProfile(@Valid @RequestBody UpdateMemberRequestDTO content) {
        try {
            service.edit(mapper.map(content, Member.class));
        } catch (Exception e) {
            return ResponseEntity.ok(ResponseDTO.createErrorResponse(HttpStatus.EXPECTATION_FAILED, "Failed to update member"));
        }
        return ResponseEntity.ok(ResponseDTO.createSuccessResponse(HttpStatus.CREATED, "Member edited successfully"));
    }

    @GetMapping("view-users")
    public ResponseEntity<ResponseDTO<List<MemberResponseDTO>>> getmembers() {
        List<MemberResponseDTO> memberResponseDTOS = service.getMembers().stream().map(c -> mapper.map(c, MemberResponseDTO.class)).toList();

        ResponseDTO<List<MemberResponseDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setData(memberResponseDTOS);
        return ResponseEntity.ok(responseDTO);
    }

    @GetMapping("change-status/{id}")
    public ResponseEntity<ResponseDTO<MemberResponseDTO>> changeStatus(@PathVariable long id) {
        MemberResponseDTO dto = mapper.map(service.changeStatus(id), MemberResponseDTO.class);
        ResponseDTO<MemberResponseDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setData(dto);
        return ResponseEntity.ok(responseDTO);
    }
}
