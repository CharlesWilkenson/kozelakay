package com.management.system.controller;

import static org.springframework.http.HttpStatus.BAD_REQUEST;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.security.Principal;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.management.system.dto.request.ContentRequestDTO;
import com.management.system.dto.response.ContentResponseDTO;
import com.management.system.dto.response.ResponseDTO;
import com.management.system.entities.Content;
import com.management.system.service.ContentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.io.IOUtils;

@RestController
@RequestMapping("/api/content/management/service/contents/")
@RequiredArgsConstructor
@Slf4j
public class ContentController {
    private final ContentService contentService;
    private final ModelMapper mapper;
    @Value("${path.dirname}")
    private String imageDir;

    @PostMapping(value = "add",  consumes = {"multipart/form-data", "application/json"})
    @ResponseStatus(BAD_REQUEST)
    public ResponseEntity<ResponseDTO<Object>> create(@Valid ContentRequestDTO content,   @RequestParam(name="file", required = false)MultipartFile file) {
        try {
            contentService.create(mapper.map(content, Content.class), file);
        } catch (Exception e) {
            return ResponseEntity.ok(ResponseDTO.createErrorResponse(HttpStatus.EXPECTATION_FAILED, "Failed to add new content"));
        }
        return ResponseEntity.ok(ResponseDTO.createSuccessResponse(HttpStatus.CREATED, "Content created successfully"));
    }

    @GetMapping("view-content/{id}")
    public ResponseEntity<ResponseDTO<ContentResponseDTO>> viewContent(@PathVariable Long id) {
        ContentResponseDTO dto = mapper.map(contentService.viewContent(id), ContentResponseDTO.class);
        ResponseDTO<ContentResponseDTO> responseDTO = new ResponseDTO<>();
        responseDTO.setData(dto);
        return ResponseEntity.ok(responseDTO);

    }

    @PutMapping("edit")
    public ResponseEntity<Object> editContent(@Valid ContentRequestDTO content,   MultipartFile file) {
        try {
            contentService.editContent(mapper.map(content, Content.class), file);
        } catch (Exception e) {
            return ResponseEntity.ok(ResponseDTO.createErrorResponse(HttpStatus.EXPECTATION_FAILED, "Failed to update content"));
        }
        return ResponseEntity.ok(ResponseDTO.createSuccessResponse(HttpStatus.CREATED, "Content updated successfully"));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Object> deleteContent(@PathVariable Long id) {
        try {
            contentService.deleteContent(id);
        } catch (Exception e) {
            return ResponseEntity.ok(ResponseDTO.createErrorResponse(HttpStatus.EXPECTATION_FAILED, "Failed to delete content"));
        }
        return ResponseEntity.ok(ResponseDTO.createSuccessResponse(HttpStatus.CREATED, "Content deleted successfully"));
    }

/*    @GetMapping
    public ResponseEntity<ResponseDTO<List<ContentResponseDTO>>> getContents() {
        List<ContentResponseDTO> contentResponseDTOList = contentService.getContents().stream().map(c -> mapper.map(c, ContentResponseDTO.class)).toList();
        ResponseDTO<List<ContentResponseDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setData(contentResponseDTOList);
        return ResponseEntity.ok(responseDTO);
    }*/

    @GetMapping
    public ResponseEntity<ResponseDTO<List<ContentResponseDTO>>> getContents() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        List<ContentResponseDTO> contentResponseDTOList = contentService.getContents(email).stream().map(c -> mapper.map(c, ContentResponseDTO.class)).toList();
        ResponseDTO<List<ContentResponseDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setData(contentResponseDTOList);
        return ResponseEntity.ok(responseDTO);
    }


    @GetMapping("/{email}")
    public ResponseEntity<ResponseDTO<List<ContentResponseDTO>>> getContents(@PathVariable String email) {
        List<ContentResponseDTO> contentResponseDTOList = contentService.getContents(email).stream().map(c -> mapper.map(c, ContentResponseDTO.class)).toList();
        ResponseDTO<List<ContentResponseDTO>> responseDTO = new ResponseDTO<>();
        responseDTO.setData(contentResponseDTOList);
        return ResponseEntity.ok(responseDTO);
    }
    
    @GetMapping("getImage")
    public byte[] getImage(@RequestParam(name = "id") Long id) throws
      IOException
    {
    	String fileName = this.viewContent(id).getBody().getData().getFileName().trim();

      File f = new File(imageDir + id + "" + fileName);
      String decodedPath = URLDecoder.decode(f.getPath(), "UTF-8");
  	log.info(f.getPath());
      return IOUtils.toByteArray(new FileInputStream(decodedPath));
    }

}
