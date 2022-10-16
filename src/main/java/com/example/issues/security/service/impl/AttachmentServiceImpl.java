package com.example.issues.security.service.impl;

import com.example.issues.model.Attachment;
import com.example.issues.repository.AttachmentRepository;
import com.example.issues.repository.ProjectRepository;
import com.example.issues.security.mapper.AttachmentMapper;
import com.example.issues.security.service.AttachmentService;
import com.example.issues.security.service.LogService;
import com.example.issues.security.dto.AttachmentDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Optional;


@Slf4j
@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {

    final ProjectRepository projectRepository;
    final AttachmentRepository attachmentRepository;

    final LogService logService;

    @Override
    public Attachment getAttachmentById(Long id) {
        return attachmentRepository.findById(id).get();
    }

    @Override
    public AttachmentDTO getAttachmentDTOById(Long id) {
        Attachment res = attachmentRepository.findById(id).get();
        AttachmentDTO dto = new AttachmentDTO();
        return dto;
    }

    @Override
    public List<Attachment> getAttachmentsByProjectId(Long id) {
        List<Attachment> atts = attachmentRepository.findAllByProjectId(id);
        for (Attachment att : atts) {
            Objects.requireNonNull(att.getContentType());
            log.warn("type is" + att.getContentType());
        }
        return atts;

    }

    @Override
    public Attachment createAttachment(Optional<Boolean> overwrite, MultipartFile file, Long projectId) {
        Attachment attachment = new Attachment();
        attachment.setFilename(file.getOriginalFilename());
        Path attPath = attachment.getPath();
        log.warn("creating att");
        attachment.setContentType(file.getContentType());
        log.warn("contentype is " + file.getContentType());
        try {
            attachment.setProject(projectRepository.findById(projectId).get());
            if (!Files.exists(attPath) || overwrite.orElse(Boolean.FALSE) )
                Files.write(attPath, file.getBytes());
            attachmentRepository.save(attachment);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        logService.log(String.format("created attachment: %s, ID: %s", file.getOriginalFilename(), attachment.getId()));
        return attachment;
    }

    @Override
    public List<AttachmentDTO> getAttachmentsDTOByProjectId(Long projectID) {
        return AttachmentMapper.INSTANCE.convertToAttachmentsDTO(
                getAttachmentsByProjectId(projectID)
        );
    }

    @Override
    public ResponseEntity<ByteArrayResource> getAttachment(Long id){
        Attachment att = getAttachmentById(id);
        ByteArrayResource content;
        try {
            content = att.getContent();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok()
                .header("filename=\"" + att.getFilename() + "\"")
                .contentType(att.getMediaContentType())
                .body(content);
    }
}
