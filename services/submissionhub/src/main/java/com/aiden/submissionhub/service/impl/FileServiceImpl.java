package com.aiden.submissionhub.service.impl;

import com.aiden.submissionhub.domain.File;
import com.aiden.submissionhub.domain.Submission;
import com.aiden.submissionhub.repository.FileRepository;
import com.aiden.submissionhub.repository.SubmissionRepository;
import com.aiden.submissionhub.service.FileService;
import com.aiden.submissionhub.service.FileStorageService;
import com.aiden.submissionhub.service.dto.FileDTO;
import com.aiden.submissionhub.service.mapper.FileMapper;

import java.io.IOException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * Service Implementation for managing {@link com.aiden.submissionhub.domain.File}.
 */
@Service
@Transactional
public class FileServiceImpl implements FileService {

    private static final Logger LOG = LoggerFactory.getLogger(FileServiceImpl.class);

    private final FileRepository fileRepository;

    private final FileMapper fileMapper;

    private final SubmissionRepository submissionRepository;

    private final FileStorageService fileStorageService;

    private final RestTemplate restTemplate;

    @Value("${application.config.processor-url}")
    private String url;

    public FileServiceImpl(FileRepository fileRepository, FileMapper fileMapper, SubmissionRepository submissionRepository, FileStorageService fileStorageService, RestTemplate restTemplate) {
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
        this.submissionRepository = submissionRepository;
        this.fileStorageService = fileStorageService;
        this.restTemplate = restTemplate;
    }

    @Override
    public FileDTO save(FileDTO fileDTO) {
        LOG.debug("Request to save File : {}", fileDTO);
        File file = fileMapper.toEntity(fileDTO);
        file = fileRepository.save(file);
        return fileMapper.toDto(file);
    }

    @Override
    public FileDTO update(FileDTO fileDTO) {
        LOG.debug("Request to update File : {}", fileDTO);
        File file = fileMapper.toEntity(fileDTO);
        file = fileRepository.save(file);
        return fileMapper.toDto(file);
    }

    @Override
    public Optional<FileDTO> partialUpdate(FileDTO fileDTO) {
        LOG.debug("Request to partially update File : {}", fileDTO);

        return fileRepository
            .findById(fileDTO.getId())
            .map(existingFile -> {
                fileMapper.partialUpdate(existingFile, fileDTO);

                return existingFile;
            })
            .map(fileRepository::save)
            .map(fileMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FileDTO> findOne(Long id) {
        LOG.debug("Request to get File : {}", id);
        return fileRepository.findById(id).map(fileMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete File : {}", id);
        fileRepository.deleteById(id);
    }

    @Override
    public FileDTO uploadSubmissionFile(MultipartFile file, Long submissionId) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }
        if (submissionId == null) {
            throw new IllegalArgumentException("Submission ID is required");
        }

        LOG.debug("Request to upload file '{}' for submission {}", file.getOriginalFilename(), submissionId);
        Submission submission = submissionRepository.findById(submissionId).orElseThrow(() -> new IllegalArgumentException("Submission not found"));
        String fileName = fileStorageService.storeFile(file, submissionId);
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/api/files/download/")
            .path(Objects.requireNonNull(file.getOriginalFilename()))
            .toUriString();

        File fileEntry = new File();
        fileEntry.setFileName(file.getOriginalFilename());
        fileEntry.setFileDownloadUri(fileDownloadUri);
        fileEntry.setSubmission(submission);
        LOG.debug("File uploaded successfully: {}", fileName);
        fileEntry = fileRepository.save(fileEntry);
        return fileMapper.toDto(fileEntry);
    }

    @Override
    public String processCase(MultipartFile file) throws IOException {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("File cannot be empty");
        }

        LOG.debug("Request to process case: {}", file.getOriginalFilename());

        if (!StringUtils.hasText(url)) {
            LOG.warn("No processing URL defined, returning default response.");
            return "Answers processed";
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        ByteArrayResource fileResource = new ByteArrayResource(file.getBytes()) {
            @Override
            public String getFilename() {
                return file.getOriginalFilename(); // Important for some APIs
            }
        };

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", fileResource);

        List<Integer> questions = List.of(75,76,77,78,79,80,81,82,83,84,85,86,87,88,89,121,125,126,127,128,129,130,131,132,133,134,135,167,168,203,204,205,542,611);
//        String questionsJson = new ObjectMapper().writeValueAsString(questions);
//        body.add("questions", questionsJson);
        for (Integer number : questions) {
            body.add("questions", number.toString());  // Ensure numbers are sent as text
        }

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<String> response;
        try {
            response = restTemplate.exchange(url, HttpMethod.POST, requestEntity, String.class);
        } catch (Exception ex) {
            LOG.error("Error sending file to microservice: {}", ex.getMessage(), ex);
            throw new RuntimeException("Failed to process file in microservice", ex);
        }

        if (response.getStatusCode() != HttpStatus.OK) {
            LOG.error("Microservice returned an error: {}", response.getStatusCode());
            throw new RuntimeException("Failed to send file to microservice");
        }

        return response.getBody();
    }
}
