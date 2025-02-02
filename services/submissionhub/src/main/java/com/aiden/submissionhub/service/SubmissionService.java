package com.aiden.submissionhub.service;

import com.aiden.submissionhub.service.dto.FileDTO;
import com.aiden.submissionhub.service.dto.SubmissionDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.aiden.submissionhub.domain.Submission}.
 */
public interface SubmissionService {
    /**
     * Save a submission.
     *
     * @param submissionDTO the entity to save.
     * @return the persisted entity.
     */
    SubmissionDTO save(SubmissionDTO submissionDTO);

    /**
     * Updates a submission.
     *
     * @param submissionDTO the entity to update.
     * @return the persisted entity.
     */
    SubmissionDTO update(SubmissionDTO submissionDTO);

    /**
     * Partially updates a submission.
     *
     * @param submissionDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SubmissionDTO> partialUpdate(SubmissionDTO submissionDTO);

    /**
     * Get the "id" submission.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SubmissionDTO> findOne(Long id);

    /**
     * Delete the "id" submission.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);

    /**
     * Create a case.
     *
     * @param file the file to create the case.
     * @return the response.
     */

    FileDTO createCase(MultipartFile file) throws IOException;
}
