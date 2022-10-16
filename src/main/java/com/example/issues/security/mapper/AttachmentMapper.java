package com.example.issues.security.mapper;

import com.example.issues.model.Attachment;
import com.example.issues.security.dto.AttachmentDTO;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AttachmentMapper {

    AttachmentMapper INSTANCE = Mappers.getMapper(AttachmentMapper.class);

    Attachment convertToAttachment(AttachmentDTO attachmentDTO);

    AttachmentDTO convertToDTO(Attachment att);

    List<AttachmentDTO> convertToAttachmentsDTO(List<Attachment> atts);
}
