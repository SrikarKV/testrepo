package com.aiden.submissionhub.web.rest;

import com.aiden.submissionhub.repository.SubmissionRepository;
import com.aiden.submissionhub.service.SubmissionQueryService;
import com.aiden.submissionhub.service.SubmissionService;
import com.aiden.submissionhub.service.criteria.SubmissionCriteria;
import com.aiden.submissionhub.service.dto.FileDTO;
import com.aiden.submissionhub.service.dto.SubmissionDTO;
import com.aiden.submissionhub.web.rest.errors.BadRequestAlertException;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.aiden.submissionhub.domain.Submission}.
 */
@RestController
@RequestMapping("/api/submissions")
public class SubmissionResource {

    private static final Logger LOG = LoggerFactory.getLogger(SubmissionResource.class);

    private static final String ENTITY_NAME = "submission";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SubmissionService submissionService;

    private final SubmissionRepository submissionRepository;

    private final SubmissionQueryService submissionQueryService;

    public SubmissionResource(
        SubmissionService submissionService,
        SubmissionRepository submissionRepository,
        SubmissionQueryService submissionQueryService
    ) {
        this.submissionService = submissionService;
        this.submissionRepository = submissionRepository;
        this.submissionQueryService = submissionQueryService;
    }

    /**
     * {@code POST  /submissions} : Create a new submission.
     *
     * @param submissionDTO the submissionDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new submissionDTO, or with status {@code 400 (Bad Request)} if the submission has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SubmissionDTO> createSubmission(@RequestBody SubmissionDTO submissionDTO) throws URISyntaxException {
        LOG.debug("REST request to save Submission : {}", submissionDTO);
        if (submissionDTO.getId() != null) {
            throw new BadRequestAlertException("A new submission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        submissionDTO = submissionService.save(submissionDTO);
        return ResponseEntity.created(new URI("/api/submissions/" + submissionDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, submissionDTO.getId().toString()))
            .body(submissionDTO);
    }

    /**
     * {@code PUT  /submissions/:id} : Updates an existing submission.
     *
     * @param id the id of the submissionDTO to save.
     * @param submissionDTO the submissionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated submissionDTO,
     * or with status {@code 400 (Bad Request)} if the submissionDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the submissionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SubmissionDTO> updateSubmission(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SubmissionDTO submissionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to update Submission : {}, {}", id, submissionDTO);
        if (submissionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, submissionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!submissionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        submissionDTO = submissionService.update(submissionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, submissionDTO.getId().toString()))
            .body(submissionDTO);
    }

    /**
     * {@code PATCH  /submissions/:id} : Partial updates given fields of an existing submission, field will ignore if it is null
     *
     * @param id the id of the submissionDTO to save.
     * @param submissionDTO the submissionDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated submissionDTO,
     * or with status {@code 400 (Bad Request)} if the submissionDTO is not valid,
     * or with status {@code 404 (Not Found)} if the submissionDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the submissionDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SubmissionDTO> partialUpdateSubmission(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SubmissionDTO submissionDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update Submission partially : {}, {}", id, submissionDTO);
        if (submissionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, submissionDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!submissionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SubmissionDTO> result = submissionService.partialUpdate(submissionDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, submissionDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /submissions} : get all the submissions.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of submissions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SubmissionDTO>> getAllSubmissions(
        SubmissionCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Submissions by criteria: {}", criteria);

        Page<SubmissionDTO> page = submissionQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /submissions/count} : count all the submissions.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countSubmissions(SubmissionCriteria criteria) {
        LOG.debug("REST request to count Submissions by criteria: {}", criteria);
        return ResponseEntity.ok().body(submissionQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /submissions/:id} : get the "id" submission.
     *
     * @param id the id of the submissionDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the submissionDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SubmissionDTO> getSubmission(@PathVariable("id") Long id) {
        LOG.debug("REST request to get Submission : {}", id);
        Optional<SubmissionDTO> submissionDTO = submissionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(submissionDTO);
    }

    /**
     * {@code DELETE  /submissions/:id} : delete the "id" submission.
     *
     * @param id the id of the submissionDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubmission(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete Submission : {}", id);
        submissionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code POST  /upload-and-proess} : Upload a file and process a case.
     *
     * @param multipartFile the file to upload and process.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fileDTO, or with status {@code 400 (Bad Request)} if the file has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/upload-and-process")
    public ResponseEntity<FileDTO> createCase(@RequestParam("file") MultipartFile multipartFile) throws URISyntaxException, IOException {
        LOG.debug("REST request to upload and process Submission : {}", multipartFile);
        FileDTO fileDTO = submissionService.createCase(multipartFile);
        return ResponseEntity.created(new URI("/api/files/" + fileDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, fileDTO.getId().toString()))
            .body(fileDTO);
    }

}
