package com.aiden.submissionhub.service;

import com.aiden.submissionhub.service.dto.FileDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.aiden.submissionhub.domain.File}.
 */
public interface FileService {
    /**
     * Save a file.
     *
     * @param fileDTO the entity to save.
     * @return the persisted entity.
     */
    FileDTO save(FileDTO fileDTO);

    /**
     * Updates a file.
     *
     * @param fileDTO the entity to update.
     * @return the persisted entity.
     */
    FileDTO update(FileDTO fileDTO);

    /**
     * Partially updates a file.
     *
     * @param fileDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FileDTO> partialUpdate(FileDTO fileDTO);

    /**
     * Get the "id" file.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FileDTO> findOne(Long id);

    /**
     * Delete the "id" file.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Upload a file.
     *
     * @param file the file to upload.
     * @param submissionId the id of the submission.
     * @return the response.
     */
    FileDTO uploadSubmissionFile(MultipartFile file, Long submissionId) throws IOException;

    /**
     * Process a case.
     *
     * @param file the file to process.
     * @return the response.
     */
    String processCase(MultipartFile file) throws IOException;


}
