package com.aiden.submissionhub.domain;

import static com.aiden.submissionhub.domain.FileTestSamples.*;
import static com.aiden.submissionhub.domain.SubmissionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.aiden.submissionhub.web.rest.TestUtil;
import java.util.HashSet;
import java.util.Set;
import org.junit.jupiter.api.Test;

class SubmissionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Submission.class);
        Submission submission1 = getSubmissionSample1();
        Submission submission2 = new Submission();
        assertThat(submission1).isNotEqualTo(submission2);

        submission2.setId(submission1.getId());
        assertThat(submission1).isEqualTo(submission2);

        submission2 = getSubmissionSample2();
        assertThat(submission1).isNotEqualTo(submission2);
    }

    @Test
    void filesTest() {
        Submission submission = getSubmissionRandomSampleGenerator();
        File fileBack = getFileRandomSampleGenerator();

        submission.addFiles(fileBack);
        assertThat(submission.getFiles()).containsOnly(fileBack);
        assertThat(fileBack.getSubmission()).isEqualTo(submission);

        submission.removeFiles(fileBack);
        assertThat(submission.getFiles()).doesNotContain(fileBack);
        assertThat(fileBack.getSubmission()).isNull();

        submission.files(new HashSet<>(Set.of(fileBack)));
        assertThat(submission.getFiles()).containsOnly(fileBack);
        assertThat(fileBack.getSubmission()).isEqualTo(submission);

        submission.setFiles(new HashSet<>());
        assertThat(submission.getFiles()).doesNotContain(fileBack);
        assertThat(fileBack.getSubmission()).isNull();
    }
}
