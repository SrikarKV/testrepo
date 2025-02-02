package com.aiden.submissionhub.domain;

import com.aiden.submissionhub.domain.enumeration.SubmissionStatus;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * A Submission.
 */
@Entity
@Table(name = "submission")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Submission implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    @Column(name = "id")
    private Long id;

    @Column(name = "case_id")
    private UUID caseId;

    @Enumerated(EnumType.STRING)
    @Column(name = "submission_status")
    private SubmissionStatus submissionStatus;

    @Column(name = "insured_name")
    private String insuredName;

    @Column(name = "assign_to")
    private String assignTo;

    @Column(name = "assign_to_email")
    private String assignToEmail;

    @Column(name = "assigned_by")
    private String assignedBY;

    @Column(name = "assigned_by_email")
    private String assignedByEmail;

    @Column(name = "task_due_date")
    private LocalDate taskDueDate;

    @Column(name = "created_date")
    private LocalDate createdDate;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "submission")
    @JsonIgnoreProperties(value = { "submission" }, allowSetters = true)
    private Set<File> files = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Submission id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UUID getCaseId() {
        return this.caseId;
    }

    public Submission caseId(UUID caseId) {
        this.setCaseId(caseId);
        return this;
    }

    public void setCaseId(UUID caseId) {
        this.caseId = caseId;
    }

    public SubmissionStatus getSubmissionStatus() {
        return this.submissionStatus;
    }

    public Submission submissionStatus(SubmissionStatus submissionStatus) {
        this.setSubmissionStatus(submissionStatus);
        return this;
    }

    public void setSubmissionStatus(SubmissionStatus submissionStatus) {
        this.submissionStatus = submissionStatus;
    }

    public String getInsuredName() {
        return this.insuredName;
    }

    public Submission insuredName(String insuredName) {
        this.setInsuredName(insuredName);
        return this;
    }

    public void setInsuredName(String insuredName) {
        this.insuredName = insuredName;
    }

    public String getAssignTo() {
        return this.assignTo;
    }

    public Submission assignTo(String assignTo) {
        this.setAssignTo(assignTo);
        return this;
    }

    public void setAssignTo(String assignTo) {
        this.assignTo = assignTo;
    }

    public String getAssignToEmail() {
        return this.assignToEmail;
    }

    public Submission assignToEmail(String assignToEmail) {
        this.setAssignToEmail(assignToEmail);
        return this;
    }

    public void setAssignToEmail(String assignToEmail) {
        this.assignToEmail = assignToEmail;
    }

    public String getAssignedBY() {
        return this.assignedBY;
    }

    public Submission assignedBY(String assignedBY) {
        this.setAssignedBY(assignedBY);
        return this;
    }

    public void setAssignedBY(String assignedBY) {
        this.assignedBY = assignedBY;
    }

    public String getAssignedByEmail() {
        return this.assignedByEmail;
    }

    public Submission assignedByEmail(String assignedByEmail) {
        this.setAssignedByEmail(assignedByEmail);
        return this;
    }

    public void setAssignedByEmail(String assignedByEmail) {
        this.assignedByEmail = assignedByEmail;
    }

    public LocalDate getTaskDueDate() {
        return this.taskDueDate;
    }

    public Submission taskDueDate(LocalDate taskDueDate) {
        this.setTaskDueDate(taskDueDate);
        return this;
    }

    public void setTaskDueDate(LocalDate taskDueDate) {
        this.taskDueDate = taskDueDate;
    }

    public LocalDate getCreatedDate() {
        return this.createdDate;
    }

    public Submission createdDate(LocalDate createdDate) {
        this.setCreatedDate(createdDate);
        return this;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public Set<File> getFiles() {
        return this.files;
    }

    public void setFiles(Set<File> files) {
        if (this.files != null) {
            this.files.forEach(i -> i.setSubmission(null));
        }
        if (files != null) {
            files.forEach(i -> i.setSubmission(this));
        }
        this.files = files;
    }

    public Submission files(Set<File> files) {
        this.setFiles(files);
        return this;
    }

    public Submission addFiles(File file) {
        this.files.add(file);
        file.setSubmission(this);
        return this;
    }

    public Submission removeFiles(File file) {
        this.files.remove(file);
        file.setSubmission(null);
        return this;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Submission)) {
            return false;
        }
        return getId() != null && getId().equals(((Submission) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Submission{" +
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
