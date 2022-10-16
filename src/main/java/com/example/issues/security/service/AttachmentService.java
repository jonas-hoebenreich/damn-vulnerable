package com.example.issues.security.service;


import com.example.issues.model.Attachment;
import com.example.issues.security.dto.AttachmentDTO;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

public interface AttachmentService {

    Attachment getAttachmentById(Long id);

    AttachmentDTO getAttachmentDTOById(Long id);

    List<Attachment> getAttachmentsByProjectId(Long id);
    ResponseEntity<ByteArrayResource> getAttachment(Long id);

    Attachment createAttachment(Optional<Boolean> overwrite, MultipartFile file, Long projectId);

    List<AttachmentDTO> getAttachmentsDTOByProjectId(Long projectID);
}
