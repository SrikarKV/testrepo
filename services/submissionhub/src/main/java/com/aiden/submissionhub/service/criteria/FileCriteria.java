package com.aiden.submissionhub.service.criteria;

import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.aiden.submissionhub.domain.File} entity. This class is used
 * in {@link com.aiden.submissionhub.web.rest.FileResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /files?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FileCriteria implements Serializable, Criteria {

    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter fileName;

    private StringFilter fileDownloadUri;

    private StringFilter answers;

    private LongFilter submissionId;

    private Boolean distinct;

    public FileCriteria() {}

    public FileCriteria(FileCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.fileName = other.optionalFileName().map(StringFilter::copy).orElse(null);
        this.fileDownloadUri = other.optionalFileDownloadUri().map(StringFilter::copy).orElse(null);
        this.answers = other.optionalAnswers().map(StringFilter::copy).orElse(null);
        this.submissionId = other.optionalSubmissionId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public FileCriteria copy() {
        return new FileCriteria(this);
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

    public StringFilter getFileName() {
        return fileName;
    }

    public Optional<StringFilter> optionalFileName() {
        return Optional.ofNullable(fileName);
    }

    public StringFilter fileName() {
        if (fileName == null) {
            setFileName(new StringFilter());
        }
        return fileName;
    }

    public void setFileName(StringFilter fileName) {
        this.fileName = fileName;
    }

    public StringFilter getFileDownloadUri() {
        return fileDownloadUri;
    }

    public Optional<StringFilter> optionalFileDownloadUri() {
        return Optional.ofNullable(fileDownloadUri);
    }

    public StringFilter fileDownloadUri() {
        if (fileDownloadUri == null) {
            setFileDownloadUri(new StringFilter());
        }
        return fileDownloadUri;
    }

    public void setFileDownloadUri(StringFilter fileDownloadUri) {
        this.fileDownloadUri = fileDownloadUri;
    }

    public StringFilter getAnswers() {
        return answers;
    }

    public Optional<StringFilter> optionalAnswers() {
        return Optional.ofNullable(answers);
    }

    public StringFilter answers() {
        if (answers == null) {
            setAnswers(new StringFilter());
        }
        return answers;
    }

    public void setAnswers(StringFilter answers) {
        this.answers = answers;
    }

    public LongFilter getSubmissionId() {
        return submissionId;
    }

    public Optional<LongFilter> optionalSubmissionId() {
        return Optional.ofNullable(submissionId);
    }

    public LongFilter submissionId() {
        if (submissionId == null) {
            setSubmissionId(new LongFilter());
        }
        return submissionId;
    }

    public void setSubmissionId(LongFilter submissionId) {
        this.submissionId = submissionId;
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
        final FileCriteria that = (FileCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(fileName, that.fileName) &&
            Objects.equals(fileDownloadUri, that.fileDownloadUri) &&
            Objects.equals(answers, that.answers) &&
            Objects.equals(submissionId, that.submissionId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fileName, fileDownloadUri, answers, submissionId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FileCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalFileName().map(f -> "fileName=" + f + ", ").orElse("") +
            optionalFileDownloadUri().map(f -> "fileDownloadUri=" + f + ", ").orElse("") +
            optionalAnswers().map(f -> "answers=" + f + ", ").orElse("") +
            optionalSubmissionId().map(f -> "submissionId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
