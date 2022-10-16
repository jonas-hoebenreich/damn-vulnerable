package com.example.issues.repository;

import com.example.issues.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    public List<Attachment> findAllByProjectId(Long projectId);
}
