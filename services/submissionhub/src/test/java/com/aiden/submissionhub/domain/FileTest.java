package com.aiden.submissionhub.domain;

import static com.aiden.submissionhub.domain.FileTestSamples.*;
import static com.aiden.submissionhub.domain.SubmissionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.aiden.submissionhub.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class FileTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(File.class);
        File file1 = getFileSample1();
        File file2 = new File();
        assertThat(file1).isNotEqualTo(file2);

        file2.setId(file1.getId());
        assertThat(file1).isEqualTo(file2);

        file2 = getFileSample2();
        assertThat(file1).isNotEqualTo(file2);
    }

    @Test
    void submissionTest() {
        File file = getFileRandomSampleGenerator();
        Submission submissionBack = getSubmissionRandomSampleGenerator();

        file.setSubmission(submissionBack);
        assertThat(file.getSubmission()).isEqualTo(submissionBack);

        file.submission(null);
        assertThat(file.getSubmission()).isNull();
    }
}
