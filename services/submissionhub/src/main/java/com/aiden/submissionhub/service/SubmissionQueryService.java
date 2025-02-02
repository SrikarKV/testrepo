package com.aiden.submissionhub.service;

import com.aiden.submissionhub.domain.*; // for static metamodels
import com.aiden.submissionhub.domain.Submission;
import com.aiden.submissionhub.repository.SubmissionRepository;
import com.aiden.submissionhub.service.criteria.SubmissionCriteria;
import com.aiden.submissionhub.service.dto.SubmissionDTO;
import com.aiden.submissionhub.service.mapper.SubmissionMapper;
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
 * Service for executing complex queries for {@link Submission} entities in the database.
 * The main input is a {@link SubmissionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link Page} of {@link SubmissionDTO} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class SubmissionQueryService extends QueryService<Submission> {

    private static final Logger LOG = LoggerFactory.getLogger(SubmissionQueryService.class);

    private final SubmissionRepository submissionRepository;

    private final SubmissionMapper submissionMapper;

    public SubmissionQueryService(SubmissionRepository submissionRepository, SubmissionMapper submissionMapper) {
        this.submissionRepository = submissionRepository;
        this.submissionMapper = submissionMapper;
    }

    /**
     * Return a {@link Page} of {@link SubmissionDTO} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<SubmissionDTO> findByCriteria(SubmissionCriteria criteria, Pageable page) {
        LOG.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Submission> specification = createSpecification(criteria);
        return submissionRepository.findAll(specification, page).map(submissionMapper::toDto);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(SubmissionCriteria criteria) {
        LOG.debug("count by criteria : {}", criteria);
        final Specification<Submission> specification = createSpecification(criteria);
        return submissionRepository.count(specification);
    }

    /**
     * Function to convert {@link SubmissionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Submission> createSpecification(SubmissionCriteria criteria) {
        Specification<Submission> specification = Specification.where(null);
        if (criteria != null) {
            // This has to be called first, because the distinct method returns null
            if (criteria.getDistinct() != null) {
                specification = specification.and(distinct(criteria.getDistinct()));
            }
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Submission_.id));
            }
            if (criteria.getCaseId() != null) {
                specification = specification.and(buildSpecification(criteria.getCaseId(), Submission_.caseId));
            }
            if (criteria.getSubmissionStatus() != null) {
                specification = specification.and(buildSpecification(criteria.getSubmissionStatus(), Submission_.submissionStatus));
            }
            if (criteria.getInsuredName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInsuredName(), Submission_.insuredName));
            }
            if (criteria.getAssignTo() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssignTo(), Submission_.assignTo));
            }
            if (criteria.getAssignToEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssignToEmail(), Submission_.assignToEmail));
            }
            if (criteria.getAssignedBY() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssignedBY(), Submission_.assignedBY));
            }
            if (criteria.getAssignedByEmail() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAssignedByEmail(), Submission_.assignedByEmail));
            }
            if (criteria.getTaskDueDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTaskDueDate(), Submission_.taskDueDate));
            }
            if (criteria.getCreatedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getCreatedDate(), Submission_.createdDate));
            }
            if (criteria.getFilesId() != null) {
                specification = specification.and(
                    buildSpecification(criteria.getFilesId(), root -> root.join(Submission_.files, JoinType.LEFT).get(File_.id))
                );
            }
        }
        return specification;
    }
}
