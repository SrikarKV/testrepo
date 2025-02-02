package com.aiden.submissionhub.service.impl;

import com.aiden.submissionhub.domain.Submission;
import com.aiden.submissionhub.repository.SubmissionRepository;
import com.aiden.submissionhub.service.FileService;
import com.aiden.submissionhub.service.SubmissionService;
import com.aiden.submissionhub.service.dto.FileDTO;
import com.aiden.submissionhub.service.dto.SubmissionDTO;
import com.aiden.submissionhub.service.mapper.SubmissionMapper;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Implementation for managing {@link com.aiden.submissionhub.domain.Submission}.
 */
@Service
@Transactional
public class SubmissionServiceImpl implements SubmissionService {

    private static final Logger LOG = LoggerFactory.getLogger(SubmissionServiceImpl.class);

    private final SubmissionRepository submissionRepository;

    private final SubmissionMapper submissionMapper;

    private final FileService fileService;

    public SubmissionServiceImpl(SubmissionRepository submissionRepository, SubmissionMapper submissionMapper, FileService fileService) {
        this.submissionRepository = submissionRepository;
        this.submissionMapper = submissionMapper;
        this.fileService = fileService;
    }

    @Override
    public SubmissionDTO save(SubmissionDTO submissionDTO) {
        LOG.debug("Request to save Submission : {}", submissionDTO);
        Submission submission = submissionMapper.toEntity(submissionDTO);
        submission.setCaseId(UUID.randomUUID());
        submission = submissionRepository.save(submission);
        return submissionMapper.toDto(submission);
    }

    @Override
    public SubmissionDTO update(SubmissionDTO submissionDTO) {
        LOG.debug("Request to update Submission : {}", submissionDTO);
        Submission submission = submissionMapper.toEntity(submissionDTO);
        submission = submissionRepository.save(submission);
        return submissionMapper.toDto(submission);
    }

    @Override
    public Optional<SubmissionDTO> partialUpdate(SubmissionDTO submissionDTO) {
        LOG.debug("Request to partially update Submission : {}", submissionDTO);

        return submissionRepository
            .findById(submissionDTO.getId())
            .map(existingSubmission -> {
                submissionMapper.partialUpdate(existingSubmission, submissionDTO);

                return existingSubmission;
            })
            .map(submissionRepository::save)
            .map(submissionMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<SubmissionDTO> findOne(Long id) {
        LOG.debug("Request to get Submission : {}", id);
        return submissionRepository.findById(id).map(submissionMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Submission : {}", id);
        submissionRepository.deleteById(id);
    }

    @Override
    public FileDTO createCase(MultipartFile file) throws IOException {
        LOG.debug("Request to create and process Submission for file : {}", file.getOriginalFilename());
        //create submission
        Submission submission = submissionMapper.toEntity(this.save(new SubmissionDTO()));

        // create file record
        FileDTO uploadedFileRecord = fileService.uploadSubmissionFile(file, submission.getId());

        // process case
        String answer = fileService.processCase(file);

        // update file record with answer
        uploadedFileRecord.setAnswers(answer);

        // save file record
        return fileService.save(uploadedFileRecord);
    }
}
