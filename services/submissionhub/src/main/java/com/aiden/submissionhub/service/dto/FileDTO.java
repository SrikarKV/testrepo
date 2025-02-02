package com.aiden.submissionhub.service.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.aiden.submissionhub.domain.File} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FileDTO implements Serializable {

    private Long id;

    private String fileName;

    private String fileDownloadUri;

    private String answers;

    private SubmissionDTO submission;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileDownloadUri() {
        return fileDownloadUri;
    }

    public void setFileDownloadUri(String fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    public String getAnswers() {
        return answers;
    }

    public void setAnswers(String answers) {
        this.answers = answers;
    }

    public SubmissionDTO getSubmission() {
        return submission;
    }

    public void setSubmission(SubmissionDTO submission) {
        this.submission = submission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FileDTO)) {
            return false;
        }

        FileDTO fileDTO = (FileDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, fileDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FileDTO{" +
            "id=" + getId() +
            ", fileName='" + getFileName() + "'" +
            ", fileDownloadUri='" + getFileDownloadUri() + "'" +
            ", answers='" + getAnswers() + "'" +
            ", submission=" + getSubmission() +
            "}";
    }
}
