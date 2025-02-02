package com.aiden.submissionhub.web.rest;

import static com.aiden.submissionhub.domain.SubmissionAsserts.*;
import static com.aiden.submissionhub.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.aiden.submissionhub.IntegrationTest;
import com.aiden.submissionhub.domain.Submission;
import com.aiden.submissionhub.domain.enumeration.SubmissionStatus;
import com.aiden.submissionhub.repository.SubmissionRepository;
import com.aiden.submissionhub.service.dto.SubmissionDTO;
import com.aiden.submissionhub.service.mapper.SubmissionMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link SubmissionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SubmissionResourceIT {

    private static final UUID DEFAULT_CASE_ID = UUID.randomUUID();
    private static final UUID UPDATED_CASE_ID = UUID.randomUUID();

    private static final SubmissionStatus DEFAULT_SUBMISSION_STATUS = SubmissionStatus.OPEN;
    private static final SubmissionStatus UPDATED_SUBMISSION_STATUS = SubmissionStatus.IN_PROGRESS;

    private static final String DEFAULT_INSURED_NAME = "AAAAAAAAAA";
    private static final String UPDATED_INSURED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_ASSIGN_TO = "AAAAAAAAAA";
    private static final String UPDATED_ASSIGN_TO = "BBBBBBBBBB";

    private static final String DEFAULT_ASSIGN_TO_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_ASSIGN_TO_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_ASSIGNED_BY = "AAAAAAAAAA";
    private static final String UPDATED_ASSIGNED_BY = "BBBBBBBBBB";

    private static final String DEFAULT_ASSIGNED_BY_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_ASSIGNED_BY_EMAIL = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_TASK_DUE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_TASK_DUE_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_TASK_DUE_DATE = LocalDate.ofEpochDay(-1L);

    private static final LocalDate DEFAULT_CREATED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_CREATED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_CREATED_DATE = LocalDate.ofEpochDay(-1L);

    private static final String ENTITY_API_URL = "/api/submissions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SubmissionRepository submissionRepository;

    @Autowired
    private SubmissionMapper submissionMapper;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSubmissionMockMvc;

    private Submission submission;

    private Submission insertedSubmission;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Submission createEntity() {
        return new Submission()
            .caseId(DEFAULT_CASE_ID)
            .submissionStatus(DEFAULT_SUBMISSION_STATUS)
            .insuredName(DEFAULT_INSURED_NAME)
            .assignTo(DEFAULT_ASSIGN_TO)
            .assignToEmail(DEFAULT_ASSIGN_TO_EMAIL)
            .assignedBY(DEFAULT_ASSIGNED_BY)
            .assignedByEmail(DEFAULT_ASSIGNED_BY_EMAIL)
            .taskDueDate(DEFAULT_TASK_DUE_DATE)
            .createdDate(DEFAULT_CREATED_DATE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Submission createUpdatedEntity() {
        return new Submission()
            .caseId(UPDATED_CASE_ID)
            .submissionStatus(UPDATED_SUBMISSION_STATUS)
            .insuredName(UPDATED_INSURED_NAME)
            .assignTo(UPDATED_ASSIGN_TO)
            .assignToEmail(UPDATED_ASSIGN_TO_EMAIL)
            .assignedBY(UPDATED_ASSIGNED_BY)
            .assignedByEmail(UPDATED_ASSIGNED_BY_EMAIL)
            .taskDueDate(UPDATED_TASK_DUE_DATE)
            .createdDate(UPDATED_CREATED_DATE);
    }

    @BeforeEach
    public void initTest() {
        submission = createEntity();
    }

    @AfterEach
    public void cleanup() {
        if (insertedSubmission != null) {
            submissionRepository.delete(insertedSubmission);
            insertedSubmission = null;
        }
    }

    @Test
    @Transactional
    void createSubmission() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Submission
        SubmissionDTO submissionDTO = submissionMapper.toDto(submission);
        var returnedSubmissionDTO = om.readValue(
            restSubmissionMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(submissionDTO)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SubmissionDTO.class
        );

        // Validate the Submission in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        var returnedSubmission = submissionMapper.toEntity(returnedSubmissionDTO);
        assertSubmissionUpdatableFieldsEquals(returnedSubmission, getPersistedSubmission(returnedSubmission));

        insertedSubmission = returnedSubmission;
    }

    @Test
    @Transactional
    void createSubmissionWithExistingId() throws Exception {
        // Create the Submission with an existing ID
        submission.setId(1L);
        SubmissionDTO submissionDTO = submissionMapper.toDto(submission);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSubmissionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(submissionDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Submission in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSubmissions() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList
        restSubmissionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(submission.getId().intValue())))
            .andExpect(jsonPath("$.[*].caseId").value(hasItem(DEFAULT_CASE_ID.toString())))
            .andExpect(jsonPath("$.[*].submissionStatus").value(hasItem(DEFAULT_SUBMISSION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].insuredName").value(hasItem(DEFAULT_INSURED_NAME)))
            .andExpect(jsonPath("$.[*].assignTo").value(hasItem(DEFAULT_ASSIGN_TO)))
            .andExpect(jsonPath("$.[*].assignToEmail").value(hasItem(DEFAULT_ASSIGN_TO_EMAIL)))
            .andExpect(jsonPath("$.[*].assignedBY").value(hasItem(DEFAULT_ASSIGNED_BY)))
            .andExpect(jsonPath("$.[*].assignedByEmail").value(hasItem(DEFAULT_ASSIGNED_BY_EMAIL)))
            .andExpect(jsonPath("$.[*].taskDueDate").value(hasItem(DEFAULT_TASK_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));
    }

    @Test
    @Transactional
    void getSubmission() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get the submission
        restSubmissionMockMvc
            .perform(get(ENTITY_API_URL_ID, submission.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(submission.getId().intValue()))
            .andExpect(jsonPath("$.caseId").value(DEFAULT_CASE_ID.toString()))
            .andExpect(jsonPath("$.submissionStatus").value(DEFAULT_SUBMISSION_STATUS.toString()))
            .andExpect(jsonPath("$.insuredName").value(DEFAULT_INSURED_NAME))
            .andExpect(jsonPath("$.assignTo").value(DEFAULT_ASSIGN_TO))
            .andExpect(jsonPath("$.assignToEmail").value(DEFAULT_ASSIGN_TO_EMAIL))
            .andExpect(jsonPath("$.assignedBY").value(DEFAULT_ASSIGNED_BY))
            .andExpect(jsonPath("$.assignedByEmail").value(DEFAULT_ASSIGNED_BY_EMAIL))
            .andExpect(jsonPath("$.taskDueDate").value(DEFAULT_TASK_DUE_DATE.toString()))
            .andExpect(jsonPath("$.createdDate").value(DEFAULT_CREATED_DATE.toString()));
    }

    @Test
    @Transactional
    void getSubmissionsByIdFiltering() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        Long id = submission.getId();

        defaultSubmissionFiltering("id.equals=" + id, "id.notEquals=" + id);

        defaultSubmissionFiltering("id.greaterThanOrEqual=" + id, "id.greaterThan=" + id);

        defaultSubmissionFiltering("id.lessThanOrEqual=" + id, "id.lessThan=" + id);
    }

    @Test
    @Transactional
    void getAllSubmissionsByCaseIdIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where caseId equals to
        defaultSubmissionFiltering("caseId.equals=" + DEFAULT_CASE_ID, "caseId.equals=" + UPDATED_CASE_ID);
    }

    @Test
    @Transactional
    void getAllSubmissionsByCaseIdIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where caseId in
        defaultSubmissionFiltering("caseId.in=" + DEFAULT_CASE_ID + "," + UPDATED_CASE_ID, "caseId.in=" + UPDATED_CASE_ID);
    }

    @Test
    @Transactional
    void getAllSubmissionsByCaseIdIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where caseId is not null
        defaultSubmissionFiltering("caseId.specified=true", "caseId.specified=false");
    }

    @Test
    @Transactional
    void getAllSubmissionsBySubmissionStatusIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where submissionStatus equals to
        defaultSubmissionFiltering(
            "submissionStatus.equals=" + DEFAULT_SUBMISSION_STATUS,
            "submissionStatus.equals=" + UPDATED_SUBMISSION_STATUS
        );
    }

    @Test
    @Transactional
    void getAllSubmissionsBySubmissionStatusIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where submissionStatus in
        defaultSubmissionFiltering(
            "submissionStatus.in=" + DEFAULT_SUBMISSION_STATUS + "," + UPDATED_SUBMISSION_STATUS,
            "submissionStatus.in=" + UPDATED_SUBMISSION_STATUS
        );
    }

    @Test
    @Transactional
    void getAllSubmissionsBySubmissionStatusIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where submissionStatus is not null
        defaultSubmissionFiltering("submissionStatus.specified=true", "submissionStatus.specified=false");
    }

    @Test
    @Transactional
    void getAllSubmissionsByInsuredNameIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where insuredName equals to
        defaultSubmissionFiltering("insuredName.equals=" + DEFAULT_INSURED_NAME, "insuredName.equals=" + UPDATED_INSURED_NAME);
    }

    @Test
    @Transactional
    void getAllSubmissionsByInsuredNameIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where insuredName in
        defaultSubmissionFiltering(
            "insuredName.in=" + DEFAULT_INSURED_NAME + "," + UPDATED_INSURED_NAME,
            "insuredName.in=" + UPDATED_INSURED_NAME
        );
    }

    @Test
    @Transactional
    void getAllSubmissionsByInsuredNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where insuredName is not null
        defaultSubmissionFiltering("insuredName.specified=true", "insuredName.specified=false");
    }

    @Test
    @Transactional
    void getAllSubmissionsByInsuredNameContainsSomething() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where insuredName contains
        defaultSubmissionFiltering("insuredName.contains=" + DEFAULT_INSURED_NAME, "insuredName.contains=" + UPDATED_INSURED_NAME);
    }

    @Test
    @Transactional
    void getAllSubmissionsByInsuredNameNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where insuredName does not contain
        defaultSubmissionFiltering(
            "insuredName.doesNotContain=" + UPDATED_INSURED_NAME,
            "insuredName.doesNotContain=" + DEFAULT_INSURED_NAME
        );
    }

    @Test
    @Transactional
    void getAllSubmissionsByAssignToIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where assignTo equals to
        defaultSubmissionFiltering("assignTo.equals=" + DEFAULT_ASSIGN_TO, "assignTo.equals=" + UPDATED_ASSIGN_TO);
    }

    @Test
    @Transactional
    void getAllSubmissionsByAssignToIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where assignTo in
        defaultSubmissionFiltering("assignTo.in=" + DEFAULT_ASSIGN_TO + "," + UPDATED_ASSIGN_TO, "assignTo.in=" + UPDATED_ASSIGN_TO);
    }

    @Test
    @Transactional
    void getAllSubmissionsByAssignToIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where assignTo is not null
        defaultSubmissionFiltering("assignTo.specified=true", "assignTo.specified=false");
    }

    @Test
    @Transactional
    void getAllSubmissionsByAssignToContainsSomething() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where assignTo contains
        defaultSubmissionFiltering("assignTo.contains=" + DEFAULT_ASSIGN_TO, "assignTo.contains=" + UPDATED_ASSIGN_TO);
    }

    @Test
    @Transactional
    void getAllSubmissionsByAssignToNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where assignTo does not contain
        defaultSubmissionFiltering("assignTo.doesNotContain=" + UPDATED_ASSIGN_TO, "assignTo.doesNotContain=" + DEFAULT_ASSIGN_TO);
    }

    @Test
    @Transactional
    void getAllSubmissionsByAssignToEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where assignToEmail equals to
        defaultSubmissionFiltering("assignToEmail.equals=" + DEFAULT_ASSIGN_TO_EMAIL, "assignToEmail.equals=" + UPDATED_ASSIGN_TO_EMAIL);
    }

    @Test
    @Transactional
    void getAllSubmissionsByAssignToEmailIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where assignToEmail in
        defaultSubmissionFiltering(
            "assignToEmail.in=" + DEFAULT_ASSIGN_TO_EMAIL + "," + UPDATED_ASSIGN_TO_EMAIL,
            "assignToEmail.in=" + UPDATED_ASSIGN_TO_EMAIL
        );
    }

    @Test
    @Transactional
    void getAllSubmissionsByAssignToEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where assignToEmail is not null
        defaultSubmissionFiltering("assignToEmail.specified=true", "assignToEmail.specified=false");
    }

    @Test
    @Transactional
    void getAllSubmissionsByAssignToEmailContainsSomething() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where assignToEmail contains
        defaultSubmissionFiltering(
            "assignToEmail.contains=" + DEFAULT_ASSIGN_TO_EMAIL,
            "assignToEmail.contains=" + UPDATED_ASSIGN_TO_EMAIL
        );
    }

    @Test
    @Transactional
    void getAllSubmissionsByAssignToEmailNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where assignToEmail does not contain
        defaultSubmissionFiltering(
            "assignToEmail.doesNotContain=" + UPDATED_ASSIGN_TO_EMAIL,
            "assignToEmail.doesNotContain=" + DEFAULT_ASSIGN_TO_EMAIL
        );
    }

    @Test
    @Transactional
    void getAllSubmissionsByAssignedBYIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where assignedBY equals to
        defaultSubmissionFiltering("assignedBY.equals=" + DEFAULT_ASSIGNED_BY, "assignedBY.equals=" + UPDATED_ASSIGNED_BY);
    }

    @Test
    @Transactional
    void getAllSubmissionsByAssignedBYIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where assignedBY in
        defaultSubmissionFiltering(
            "assignedBY.in=" + DEFAULT_ASSIGNED_BY + "," + UPDATED_ASSIGNED_BY,
            "assignedBY.in=" + UPDATED_ASSIGNED_BY
        );
    }

    @Test
    @Transactional
    void getAllSubmissionsByAssignedBYIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where assignedBY is not null
        defaultSubmissionFiltering("assignedBY.specified=true", "assignedBY.specified=false");
    }

    @Test
    @Transactional
    void getAllSubmissionsByAssignedBYContainsSomething() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where assignedBY contains
        defaultSubmissionFiltering("assignedBY.contains=" + DEFAULT_ASSIGNED_BY, "assignedBY.contains=" + UPDATED_ASSIGNED_BY);
    }

    @Test
    @Transactional
    void getAllSubmissionsByAssignedBYNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where assignedBY does not contain
        defaultSubmissionFiltering("assignedBY.doesNotContain=" + UPDATED_ASSIGNED_BY, "assignedBY.doesNotContain=" + DEFAULT_ASSIGNED_BY);
    }

    @Test
    @Transactional
    void getAllSubmissionsByAssignedByEmailIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where assignedByEmail equals to
        defaultSubmissionFiltering(
            "assignedByEmail.equals=" + DEFAULT_ASSIGNED_BY_EMAIL,
            "assignedByEmail.equals=" + UPDATED_ASSIGNED_BY_EMAIL
        );
    }

    @Test
    @Transactional
    void getAllSubmissionsByAssignedByEmailIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where assignedByEmail in
        defaultSubmissionFiltering(
            "assignedByEmail.in=" + DEFAULT_ASSIGNED_BY_EMAIL + "," + UPDATED_ASSIGNED_BY_EMAIL,
            "assignedByEmail.in=" + UPDATED_ASSIGNED_BY_EMAIL
        );
    }

    @Test
    @Transactional
    void getAllSubmissionsByAssignedByEmailIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where assignedByEmail is not null
        defaultSubmissionFiltering("assignedByEmail.specified=true", "assignedByEmail.specified=false");
    }

    @Test
    @Transactional
    void getAllSubmissionsByAssignedByEmailContainsSomething() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where assignedByEmail contains
        defaultSubmissionFiltering(
            "assignedByEmail.contains=" + DEFAULT_ASSIGNED_BY_EMAIL,
            "assignedByEmail.contains=" + UPDATED_ASSIGNED_BY_EMAIL
        );
    }

    @Test
    @Transactional
    void getAllSubmissionsByAssignedByEmailNotContainsSomething() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where assignedByEmail does not contain
        defaultSubmissionFiltering(
            "assignedByEmail.doesNotContain=" + UPDATED_ASSIGNED_BY_EMAIL,
            "assignedByEmail.doesNotContain=" + DEFAULT_ASSIGNED_BY_EMAIL
        );
    }

    @Test
    @Transactional
    void getAllSubmissionsByTaskDueDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where taskDueDate equals to
        defaultSubmissionFiltering("taskDueDate.equals=" + DEFAULT_TASK_DUE_DATE, "taskDueDate.equals=" + UPDATED_TASK_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllSubmissionsByTaskDueDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where taskDueDate in
        defaultSubmissionFiltering(
            "taskDueDate.in=" + DEFAULT_TASK_DUE_DATE + "," + UPDATED_TASK_DUE_DATE,
            "taskDueDate.in=" + UPDATED_TASK_DUE_DATE
        );
    }

    @Test
    @Transactional
    void getAllSubmissionsByTaskDueDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where taskDueDate is not null
        defaultSubmissionFiltering("taskDueDate.specified=true", "taskDueDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSubmissionsByTaskDueDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where taskDueDate is greater than or equal to
        defaultSubmissionFiltering(
            "taskDueDate.greaterThanOrEqual=" + DEFAULT_TASK_DUE_DATE,
            "taskDueDate.greaterThanOrEqual=" + UPDATED_TASK_DUE_DATE
        );
    }

    @Test
    @Transactional
    void getAllSubmissionsByTaskDueDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where taskDueDate is less than or equal to
        defaultSubmissionFiltering(
            "taskDueDate.lessThanOrEqual=" + DEFAULT_TASK_DUE_DATE,
            "taskDueDate.lessThanOrEqual=" + SMALLER_TASK_DUE_DATE
        );
    }

    @Test
    @Transactional
    void getAllSubmissionsByTaskDueDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where taskDueDate is less than
        defaultSubmissionFiltering("taskDueDate.lessThan=" + UPDATED_TASK_DUE_DATE, "taskDueDate.lessThan=" + DEFAULT_TASK_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllSubmissionsByTaskDueDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where taskDueDate is greater than
        defaultSubmissionFiltering("taskDueDate.greaterThan=" + SMALLER_TASK_DUE_DATE, "taskDueDate.greaterThan=" + DEFAULT_TASK_DUE_DATE);
    }

    @Test
    @Transactional
    void getAllSubmissionsByCreatedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where createdDate equals to
        defaultSubmissionFiltering("createdDate.equals=" + DEFAULT_CREATED_DATE, "createdDate.equals=" + UPDATED_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllSubmissionsByCreatedDateIsInShouldWork() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where createdDate in
        defaultSubmissionFiltering(
            "createdDate.in=" + DEFAULT_CREATED_DATE + "," + UPDATED_CREATED_DATE,
            "createdDate.in=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllSubmissionsByCreatedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where createdDate is not null
        defaultSubmissionFiltering("createdDate.specified=true", "createdDate.specified=false");
    }

    @Test
    @Transactional
    void getAllSubmissionsByCreatedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where createdDate is greater than or equal to
        defaultSubmissionFiltering(
            "createdDate.greaterThanOrEqual=" + DEFAULT_CREATED_DATE,
            "createdDate.greaterThanOrEqual=" + UPDATED_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllSubmissionsByCreatedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where createdDate is less than or equal to
        defaultSubmissionFiltering(
            "createdDate.lessThanOrEqual=" + DEFAULT_CREATED_DATE,
            "createdDate.lessThanOrEqual=" + SMALLER_CREATED_DATE
        );
    }

    @Test
    @Transactional
    void getAllSubmissionsByCreatedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where createdDate is less than
        defaultSubmissionFiltering("createdDate.lessThan=" + UPDATED_CREATED_DATE, "createdDate.lessThan=" + DEFAULT_CREATED_DATE);
    }

    @Test
    @Transactional
    void getAllSubmissionsByCreatedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        // Get all the submissionList where createdDate is greater than
        defaultSubmissionFiltering("createdDate.greaterThan=" + SMALLER_CREATED_DATE, "createdDate.greaterThan=" + DEFAULT_CREATED_DATE);
    }

    private void defaultSubmissionFiltering(String shouldBeFound, String shouldNotBeFound) throws Exception {
        defaultSubmissionShouldBeFound(shouldBeFound);
        defaultSubmissionShouldNotBeFound(shouldNotBeFound);
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultSubmissionShouldBeFound(String filter) throws Exception {
        restSubmissionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(submission.getId().intValue())))
            .andExpect(jsonPath("$.[*].caseId").value(hasItem(DEFAULT_CASE_ID.toString())))
            .andExpect(jsonPath("$.[*].submissionStatus").value(hasItem(DEFAULT_SUBMISSION_STATUS.toString())))
            .andExpect(jsonPath("$.[*].insuredName").value(hasItem(DEFAULT_INSURED_NAME)))
            .andExpect(jsonPath("$.[*].assignTo").value(hasItem(DEFAULT_ASSIGN_TO)))
            .andExpect(jsonPath("$.[*].assignToEmail").value(hasItem(DEFAULT_ASSIGN_TO_EMAIL)))
            .andExpect(jsonPath("$.[*].assignedBY").value(hasItem(DEFAULT_ASSIGNED_BY)))
            .andExpect(jsonPath("$.[*].assignedByEmail").value(hasItem(DEFAULT_ASSIGNED_BY_EMAIL)))
            .andExpect(jsonPath("$.[*].taskDueDate").value(hasItem(DEFAULT_TASK_DUE_DATE.toString())))
            .andExpect(jsonPath("$.[*].createdDate").value(hasItem(DEFAULT_CREATED_DATE.toString())));

        // Check, that the count call also returns 1
        restSubmissionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultSubmissionShouldNotBeFound(String filter) throws Exception {
        restSubmissionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restSubmissionMockMvc
            .perform(get(ENTITY_API_URL + "/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }

    @Test
    @Transactional
    void getNonExistingSubmission() throws Exception {
        // Get the submission
        restSubmissionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSubmission() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the submission
        Submission updatedSubmission = submissionRepository.findById(submission.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSubmission are not directly saved in db
        em.detach(updatedSubmission);
        updatedSubmission
            .caseId(UPDATED_CASE_ID)
            .submissionStatus(UPDATED_SUBMISSION_STATUS)
            .insuredName(UPDATED_INSURED_NAME)
            .assignTo(UPDATED_ASSIGN_TO)
            .assignToEmail(UPDATED_ASSIGN_TO_EMAIL)
            .assignedBY(UPDATED_ASSIGNED_BY)
            .assignedByEmail(UPDATED_ASSIGNED_BY_EMAIL)
            .taskDueDate(UPDATED_TASK_DUE_DATE)
            .createdDate(UPDATED_CREATED_DATE);
        SubmissionDTO submissionDTO = submissionMapper.toDto(updatedSubmission);

        restSubmissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, submissionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(submissionDTO))
            )
            .andExpect(status().isOk());

        // Validate the Submission in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSubmissionToMatchAllProperties(updatedSubmission);
    }

    @Test
    @Transactional
    void putNonExistingSubmission() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        submission.setId(longCount.incrementAndGet());

        // Create the Submission
        SubmissionDTO submissionDTO = submissionMapper.toDto(submission);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubmissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, submissionDTO.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(submissionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Submission in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSubmission() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        submission.setId(longCount.incrementAndGet());

        // Create the Submission
        SubmissionDTO submissionDTO = submissionMapper.toDto(submission);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubmissionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(submissionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Submission in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSubmission() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        submission.setId(longCount.incrementAndGet());

        // Create the Submission
        SubmissionDTO submissionDTO = submissionMapper.toDto(submission);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubmissionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(submissionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Submission in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSubmissionWithPatch() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the submission using partial update
        Submission partialUpdatedSubmission = new Submission();
        partialUpdatedSubmission.setId(submission.getId());

        partialUpdatedSubmission
            .caseId(UPDATED_CASE_ID)
            .insuredName(UPDATED_INSURED_NAME)
            .assignTo(UPDATED_ASSIGN_TO)
            .assignToEmail(UPDATED_ASSIGN_TO_EMAIL)
            .assignedBY(UPDATED_ASSIGNED_BY)
            .taskDueDate(UPDATED_TASK_DUE_DATE)
            .createdDate(UPDATED_CREATED_DATE);

        restSubmissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubmission.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSubmission))
            )
            .andExpect(status().isOk());

        // Validate the Submission in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSubmissionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSubmission, submission),
            getPersistedSubmission(submission)
        );
    }

    @Test
    @Transactional
    void fullUpdateSubmissionWithPatch() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the submission using partial update
        Submission partialUpdatedSubmission = new Submission();
        partialUpdatedSubmission.setId(submission.getId());

        partialUpdatedSubmission
            .caseId(UPDATED_CASE_ID)
            .submissionStatus(UPDATED_SUBMISSION_STATUS)
            .insuredName(UPDATED_INSURED_NAME)
            .assignTo(UPDATED_ASSIGN_TO)
            .assignToEmail(UPDATED_ASSIGN_TO_EMAIL)
            .assignedBY(UPDATED_ASSIGNED_BY)
            .assignedByEmail(UPDATED_ASSIGNED_BY_EMAIL)
            .taskDueDate(UPDATED_TASK_DUE_DATE)
            .createdDate(UPDATED_CREATED_DATE);

        restSubmissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSubmission.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSubmission))
            )
            .andExpect(status().isOk());

        // Validate the Submission in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSubmissionUpdatableFieldsEquals(partialUpdatedSubmission, getPersistedSubmission(partialUpdatedSubmission));
    }

    @Test
    @Transactional
    void patchNonExistingSubmission() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        submission.setId(longCount.incrementAndGet());

        // Create the Submission
        SubmissionDTO submissionDTO = submissionMapper.toDto(submission);

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSubmissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, submissionDTO.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(submissionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Submission in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSubmission() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        submission.setId(longCount.incrementAndGet());

        // Create the Submission
        SubmissionDTO submissionDTO = submissionMapper.toDto(submission);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubmissionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(submissionDTO))
            )
            .andExpect(status().isBadRequest());

        // Validate the Submission in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSubmission() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        submission.setId(longCount.incrementAndGet());

        // Create the Submission
        SubmissionDTO submissionDTO = submissionMapper.toDto(submission);

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSubmissionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(submissionDTO)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Submission in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSubmission() throws Exception {
        // Initialize the database
        insertedSubmission = submissionRepository.saveAndFlush(submission);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the submission
        restSubmissionMockMvc
            .perform(delete(ENTITY_API_URL_ID, submission.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return submissionRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected Submission getPersistedSubmission(Submission submission) {
        return submissionRepository.findById(submission.getId()).orElseThrow();
    }

    protected void assertPersistedSubmissionToMatchAllProperties(Submission expectedSubmission) {
        assertSubmissionAllPropertiesEquals(expectedSubmission, getPersistedSubmission(expectedSubmission));
    }

    protected void assertPersistedSubmissionToMatchUpdatableProperties(Submission expectedSubmission) {
        assertSubmissionAllUpdatablePropertiesEquals(expectedSubmission, getPersistedSubmission(expectedSubmission));
    }
}
