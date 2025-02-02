package com.aiden.submissionhub.web.rest;

import com.aiden.submissionhub.repository.FileRepository;
import com.aiden.submissionhub.service.FileQueryService;
import com.aiden.submissionhub.service.FileService;
import com.aiden.submissionhub.service.FileStorageService;
import com.aiden.submissionhub.service.criteria.FileCriteria;
import com.aiden.submissionhub.service.dto.FileDTO;
import com.aiden.submissionhub.web.rest.errors.BadRequestAlertException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.aiden.submissionhub.domain.File}.
 */
@RestController
@RequestMapping("/api/files")
public class FileResource {

    private static final Logger LOG = LoggerFactory.getLogger(FileResource.class);

    private static final String ENTITY_NAME = "file";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FileService fileService;

    private final FileRepository fileRepository;

    private final FileQueryService fileQueryService;

    private final FileStorageService fileStorageService;

    public FileResource(FileService fileService, FileRepository fileRepository, FileQueryService fileQueryService, FileStorageService fileStorageService) {
        this.fileService = fileService;
        this.fileRepository = fileRepository;
        this.fileQueryService = fileQueryService;
        this.fileStorageService = fileStorageService;
    }

    /**
     * {@code POST  /files} : Create a new file.
     *
     * @param fileDTO the fileDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fileDTO, or with status {@code 400 (Bad Request)} if the file has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FileDTO> createFile(@RequestBody FileDTO fileDTO) throws URISyntaxException {
        LOG.debug("REST request to save File : {}", fileDTO);
        if (fileDTO.getId() != null) {
            throw new BadRequestAlertException("A new file cannot already have an ID", ENTITY_NAME, "idexists");
        }
        fileDTO = fileService.save(fileDTO);
        return ResponseEntity.created(new URI("/api/files/" + fileDTO.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, fileDTO.getId().toString()))
            .body(fileDTO);
    }

    /**
     * {@code PUT  /files/:id} : Updates an existing file.
     *
     * @param id the id of the fileDTO to save.
     * @param fileDTO the fileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileDTO,
     * or with status {@code 400 (Bad Request)} if the fileDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FileDTO> updateFile(@PathVariable(value = "id", required = false) final Long id, @RequestBody FileDTO fileDTO)
        throws URISyntaxException {
        LOG.debug("REST request to update File : {}, {}", id, fileDTO);
        if (fileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        fileDTO = fileService.update(fileDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fileDTO.getId().toString()))
            .body(fileDTO);
    }

    /**
     * {@code PATCH  /files/:id} : Partial updates given fields of an existing file, field will ignore if it is null
     *
     * @param id the id of the fileDTO to save.
     * @param fileDTO the fileDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fileDTO,
     * or with status {@code 400 (Bad Request)} if the fileDTO is not valid,
     * or with status {@code 404 (Not Found)} if the fileDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the fileDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FileDTO> partialUpdateFile(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FileDTO fileDTO
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update File partially : {}, {}", id, fileDTO);
        if (fileDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fileDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fileRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FileDTO> result = fileService.partialUpdate(fileDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, fileDTO.getId().toString())
        );
    }

    /**
     * {@code GET  /files} : get all the files.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of files in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FileDTO>> getAllFiles(
        FileCriteria criteria,
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get Files by criteria: {}", criteria);

        Page<FileDTO> page = fileQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /files/count} : count all the files.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/count")
    public ResponseEntity<Long> countFiles(FileCriteria criteria) {
        LOG.debug("REST request to count Files by criteria: {}", criteria);
        return ResponseEntity.ok().body(fileQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /files/:id} : get the "id" file.
     *
     * @param id the id of the fileDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fileDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FileDTO> getFile(@PathVariable("id") Long id) {
        LOG.debug("REST request to get File : {}", id);
        Optional<FileDTO> fileDTO = fileService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fileDTO);
    }

    /**
     * {@code DELETE  /files/:id} : delete the "id" file.
     *
     * @param id the id of the fileDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFile(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete File : {}", id);
        fileService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString()))
            .build();
    }

    /**
     * {@code POST  /upload/:submissionId} : Upload a file.
     *
     * @param file the file to upload.
     * @param submissionId the id of the submission.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fileDTO, or with status {@code 400 (Bad Request)} if the file has already an ID.
     * @throws IOException if the file cannot be uploaded.
     */
    @PostMapping("/upload/{submissionId}")
    public ResponseEntity<FileDTO> uploadFile(@RequestParam("file") MultipartFile file, @PathVariable("submissionId") Long submissionId) throws IOException {
        LOG.debug("REST request to upload File : {} for submission {}", file.getOriginalFilename(), submissionId);
        return ResponseEntity.ok().body(fileService.uploadSubmissionFile(file,submissionId));
    }

    /**
     * {@code POST  /uploadMultipleFiles/:submissionId} : Upload multiple files.
     *
     * @param files the files to upload.
     * @param submissionId the id of the submission.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the list of fileDTOs, or with status {@code 400 (Bad Request)} if the file has already an ID.
     * @throws IOException if the file cannot be uploaded.
     */
    @PostMapping("/upload-multiple/{submissionId}")
    public ResponseEntity<List<FileDTO>> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files,@PathVariable("submissionId")Long submissionId) throws IOException {
        return ResponseEntity.ok().body(Arrays.stream(files)
            .map(file -> {
                try {
                    return uploadFile(file,submissionId).getBody();
                } catch (IOException e) {
                    LOG.debug("Could not upload file {}", file.getOriginalFilename());
                }
                return null;
            })
            .collect(Collectors.toList()));
    }

    /**
     * {@code GET  /download/:fileName} : Download a file.
     *
     * @param fileName the name of the file to download.
     * @param request the request.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the file to download.
     */
    @GetMapping("/download/{fileName:.+}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request) {
        Resource resource = fileStorageService.loadFileAsResource(fileName);
        String contentType = null;
        try {
            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (IOException ex) {
            LOG.info("Could not determine file type.");
        }
        if (contentType == null) {
            contentType = "application/octet-stream";
        }
        return ResponseEntity.ok()
            .contentType(MediaType.parseMediaType(contentType))
            .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
            .body(resource);
    }
}
