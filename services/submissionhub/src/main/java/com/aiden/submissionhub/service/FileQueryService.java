package com.aiden.submissionhub.service;

import com.aiden.submissionhub.domain.*; // for static metamodels
import com.aiden.submissionhub.domain.File;
import com.aiden.submissionhub.repository.FileRepository;
import com.aiden.submissionhub.service.criteria.FileCriteria;
import com.aiden.submissionhub.service.dto.FileDTO;
import com.aiden.submissionhub.service.mapper.FileMapper;
import jakarta.persistence.criteria.JoinType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tech.jhipster.service.QueryService;

/**
 * Service for executing complex queries for {@link File} entities in the database.
 * The main input is a {@link FileCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link FileDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class FileQueryService extends QueryService<File> {

    private static final Logger LOG = LoggerFactory.getLogger(FileQueryService.class);

    private final FileRepository fileRepository;

    private final FileMapper fileMapper;

    public FileQueryService(FileRepository fileRepository, FileMapper fileMapper) {
        this.fileRepository = fileRepository;
        this.fileMapper = fileMapper;
    }

    /**
     * Return a {@link Page} of {@link FileDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<FileDTO> findByCriteria(FileCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<File> specification = createSpecification(criteria);
        return fileRepository.findAll(specification, page).map(fileMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(FileCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<File> specification = createSpecification(criteria);
        return fileRepository.count(specification);
    }

    /**
     * Function to convert {@link FileCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<File> createSpecification(FileCriteria criteria) {
        Specification<File> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), File_.id));
            }
            if (criteria.getFileName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileName(), File_.fileName));
            }
            if (criteria.getFileDownloadUri() != null) {
                specification = specification.and(buildStringSpecification(criteria.getFileDownloadUri(), File_.fileDownloadUri));
            }
            if (criteria.getAnswers() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAnswers(), File_.answers));
            }
            if (criteria.getSubmissionId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getSubmissionId(), root -> root.join(File_.submission, JoinType.LEFT).get(Submission_.id))
                );
            }
        }
        return specification;
    }
}
