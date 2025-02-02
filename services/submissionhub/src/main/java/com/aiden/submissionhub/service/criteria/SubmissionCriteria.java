package com.aiden.submissionhub.service.criteria;

import com.aiden.submissionhub.domain.enumeration.SubmissionStatus;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.aiden.submissionhub.domain.Submission} entity. This class is used
 * in {@link com.aiden.submissionhub.web.rest.SubmissionResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /submissions?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SubmissionCriteria implements Serializable, Criteria {

    /**
     * Class for filtering SubmissionStatus
     */
    public static class SubmissionStatusFilter extends Filter<SubmissionStatus> {

        public SubmissionStatusFilter() {}

        public SubmissionStatusFilter(SubmissionStatusFilter filter) {
            super(filter);
        }

        @Override
        public SubmissionStatusFilter copy() {
            return new SubmissionStatusFilter(this);
        }
    }

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private UUIDFilter caseId;

    private SubmissionStatusFilter submissionStatus;

    private StringFilter insuredName;

    private StringFilter assignTo;

    private StringFilter assignToEmail;

    private StringFilter assignedBY;

    private StringFilter assignedByEmail;

    private LocalDateFilter taskDueDate;

    private LocalDateFilter createdDate;

    private LongFilter filesId;

    private Boolean distinct;

    public SubmissionCriteria() {}

    public SubmissionCriteria(SubmissionCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.caseId = other.optionalCaseId().map(UUIDFilter::copy).orElse(null);
        this.submissionStatus = other.optionalSubmissionStatus().map(SubmissionStatusFilter::copy).orElse(null);
        this.insuredName = other.optionalInsuredName().map(StringFilter::copy).orElse(null);
        this.assignTo = other.optionalAssignTo().map(StringFilter::copy).orElse(null);
        this.assignToEmail = other.optionalAssignToEmail().map(StringFilter::copy).orElse(null);
        this.assignedBY = other.optionalAssignedBY().map(StringFilter::copy).orElse(null);
        this.assignedByEmail = other.optionalAssignedByEmail().map(StringFilter::copy).orElse(null);
        this.taskDueDate = other.optionalTaskDueDate().map(LocalDateFilter::copy).orElse(null);
        this.createdDate = other.optionalCreatedDate().map(LocalDateFilter::copy).orElse(null);
        this.filesId = other.optionalFilesId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public SubmissionCriteria copy() {
        return new SubmissionCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public UUIDFilter getCaseId() {
        return caseId;
    }

    public Optional<UUIDFilter> optionalCaseId() {
        return Optional.ofNullable(caseId);
    }

    public UUIDFilter caseId() {
        if (caseId == null) {
            setCaseId(new UUIDFilter());
        }
        return caseId;
    }

    public void setCaseId(UUIDFilter caseId) {
        this.caseId = caseId;
    }

    public SubmissionStatusFilter getSubmissionStatus() {
        return submissionStatus;
    }

    public Optional<SubmissionStatusFilter> optionalSubmissionStatus() {
        return Optional.ofNullable(submissionStatus);
    }

    public SubmissionStatusFilter submissionStatus() {
        if (submissionStatus == null) {
            setSubmissionStatus(new SubmissionStatusFilter());
        }
        return submissionStatus;
    }

    public void setSubmissionStatus(SubmissionStatusFilter submissionStatus) {
        this.submissionStatus = submissionStatus;
    }

    public StringFilter getInsuredName() {
        return insuredName;
    }

    public Optional<StringFilter> optionalInsuredName() {
        return Optional.ofNullable(insuredName);
    }

    public StringFilter insuredName() {
        if (insuredName == null) {
            setInsuredName(new StringFilter());
        }
        return insuredName;
    }

    public void setInsuredName(StringFilter insuredName) {
        this.insuredName = insuredName;
    }

    public StringFilter getAssignTo() {
        return assignTo;
    }

    public Optional<StringFilter> optionalAssignTo() {
        return Optional.ofNullable(assignTo);
    }

    public StringFilter assignTo() {
        if (assignTo == null) {
            setAssignTo(new StringFilter());
        }
        return assignTo;
    }

    public void setAssignTo(StringFilter assignTo) {
        this.assignTo = assignTo;
    }

    public StringFilter getAssignToEmail() {
        return assignToEmail;
    }

    public Optional<StringFilter> optionalAssignToEmail() {
        return Optional.ofNullable(assignToEmail);
    }

    public StringFilter assignToEmail() {
        if (assignToEmail == null) {
            setAssignToEmail(new StringFilter());
        }
        return assignToEmail;
    }

    public void setAssignToEmail(StringFilter assignToEmail) {
        this.assignToEmail = assignToEmail;
    }

    public StringFilter getAssignedBY() {
        return assignedBY;
    }

    public Optional<StringFilter> optionalAssignedBY() {
        return Optional.ofNullable(assignedBY);
    }

    public StringFilter assignedBY() {
        if (assignedBY == null) {
            setAssignedBY(new StringFilter());
        }
        return assignedBY;
    }

    public void setAssignedBY(StringFilter assignedBY) {
        this.assignedBY = assignedBY;
    }

    public StringFilter getAssignedByEmail() {
        return assignedByEmail;
    }

    public Optional<StringFilter> optionalAssignedByEmail() {
        return Optional.ofNullable(assignedByEmail);
    }

    public StringFilter assignedByEmail() {
        if (assignedByEmail == null) {
            setAssignedByEmail(new StringFilter());
        }
        return assignedByEmail;
    }

    public void setAssignedByEmail(StringFilter assignedByEmail) {
        this.assignedByEmail = assignedByEmail;
    }

    public LocalDateFilter getTaskDueDate() {
        return taskDueDate;
    }

    public Optional<LocalDateFilter> optionalTaskDueDate() {
        return Optional.ofNullable(taskDueDate);
    }

    public LocalDateFilter taskDueDate() {
        if (taskDueDate == null) {
            setTaskDueDate(new LocalDateFilter());
        }
        return taskDueDate;
    }

    public void setTaskDueDate(LocalDateFilter taskDueDate) {
        this.taskDueDate = taskDueDate;
    }

    public LocalDateFilter getCreatedDate() {
        return createdDate;
    }

    public Optional<LocalDateFilter> optionalCreatedDate() {
        return Optional.ofNullable(createdDate);
    }

    public LocalDateFilter createdDate() {
        if (createdDate == null) {
            setCreatedDate(new LocalDateFilter());
        }
        return createdDate;
    }

    public void setCreatedDate(LocalDateFilter createdDate) {
        this.createdDate = createdDate;
    }

    public LongFilter getFilesId() {
        return filesId;
    }

    public Optional<LongFilter> optionalFilesId() {
        return Optional.ofNullable(filesId);
    }

    public LongFilter filesId() {
        if (filesId == null) {
            setFilesId(new LongFilter());
        }
        return filesId;
    }

    public void setFilesId(LongFilter filesId) {
        this.filesId = filesId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final SubmissionCriteria that = (SubmissionCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(caseId, that.caseId) &&
            Objects.equals(submissionStatus, that.submissionStatus) &&
            Objects.equals(insuredName, that.insuredName) &&
            Objects.equals(assignTo, that.assignTo) &&
            Objects.equals(assignToEmail, that.assignToEmail) &&
            Objects.equals(assignedBY, that.assignedBY) &&
            Objects.equals(assignedByEmail, that.assignedByEmail) &&
            Objects.equals(taskDueDate, that.taskDueDate) &&
            Objects.equals(createdDate, that.createdDate) &&
            Objects.equals(filesId, that.filesId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(
            id,
            caseId,
            submissionStatus,
            insuredName,
            assignTo,
            assignToEmail,
            assignedBY,
            assignedByEmail,
            taskDueDate,
            createdDate,
            filesId,
            distinct
        );
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubmissionCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalCaseId().map(f -> "caseId=" + f + ", ").orElse("") +
            optionalSubmissionStatus().map(f -> "submissionStatus=" + f + ", ").orElse("") +
            optionalInsuredName().map(f -> "insuredName=" + f + ", ").orElse("") +
            optionalAssignTo().map(f -> "assignTo=" + f + ", ").orElse("") +
            optionalAssignToEmail().map(f -> "assignToEmail=" + f + ", ").orElse("") +
            optionalAssignedBY().map(f -> "assignedBY=" + f + ", ").orElse("") +
            optionalAssignedByEmail().map(f -> "assignedByEmail=" + f + ", ").orElse("") +
            optionalTaskDueDate().map(f -> "taskDueDate=" + f + ", ").orElse("") +
            optionalCreatedDate().map(f -> "createdDate=" + f + ", ").orElse("") +
            optionalFilesId().map(f -> "filesId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
