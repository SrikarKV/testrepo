package com.aiden.submissionhub.service.dto;

import com.aiden.submissionhub.domain.enumeration.SubmissionStatus;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

/**
 * A DTO for the {@link com.aiden.submissionhub.domain.Submission} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubmissionDTO implements Serializable {

    private Long id;

    private UUID caseId;

    private SubmissionStatus submissionStatus;

    private String insuredName;

    private String assignTo;

    private String assignToEmail;

    private String assignedBY;

    private String assignedByEmail;

    private LocalDate taskDueDate;

    private LocalDate createdDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getCaseId() {
        return caseId;
    }

    public void setCaseId(UUID caseId) {
        this.caseId = caseId;
    }

    public SubmissionStatus getSubmissionStatus() {
        return submissionStatus;
    }

    public void setSubmissionStatus(SubmissionStatus submissionStatus) {
        this.submissionStatus = submissionStatus;
    }

    public String getInsuredName() {
        return insuredName;
    }

    public void setInsuredName(String insuredName) {
        this.insuredName = insuredName;
    }

    public String getAssignTo() {
        return assignTo;
    }

    public void setAssignTo(String assignTo) {
        this.assignTo = assignTo;
    }

    public String getAssignToEmail() {
        return assignToEmail;
    }

    public void setAssignToEmail(String assignToEmail) {
        this.assignToEmail = assignToEmail;
    }

    public String getAssignedBY() {
        return assignedBY;
    }

    public void setAssignedBY(String assignedBY) {
        this.assignedBY = assignedBY;
    }

    public String getAssignedByEmail() {
        return assignedByEmail;
    }

    public void setAssignedByEmail(String assignedByEmail) {
        this.assignedByEmail = assignedByEmail;
    }

    public LocalDate getTaskDueDate() {
        return taskDueDate;
    }

    public void setTaskDueDate(LocalDate taskDueDate) {
        this.taskDueDate = taskDueDate;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubmissionDTO)) {
            return false;
        }

        SubmissionDTO submissionDTO = (SubmissionDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, submissionDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SubmissionDTO{" +
            "id=" + getId() +
            ", caseId='" + getCaseId() + "'" +
            ", submissionStatus='" + getSubmissionStatus() + "'" +
            ", insuredName='" + getInsuredName() + "'" +
            ", assignTo='" + getAssignTo() + "'" +
            ", assignToEmail='" + getAssignToEmail() + "'" +
            ", assignedBY='" + getAssignedBY() + "'" +
            ", assignedByEmail='" + getAssignedByEmail() + "'" +
            ", taskDueDate='" + getTaskDueDate() + "'" +
            ", createdDate='" + getCreatedDate() + "'" +
            "}";
    }
}
