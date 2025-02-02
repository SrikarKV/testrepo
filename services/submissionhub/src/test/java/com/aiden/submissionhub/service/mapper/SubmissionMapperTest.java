package com.aiden.submissionhub.service.mapper;

import static com.aiden.submissionhub.domain.SubmissionAsserts.*;
import static com.aiden.submissionhub.domain.SubmissionTestSamples.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SubmissionMapperTest {

    private SubmissionMapper submissionMapper;

    @BeforeEach
    void setUp() {
        submissionMapper = new SubmissionMapperImpl();
    }

    @Test
    void shouldConvertToDtoAndBack() {
        var expected = getSubmissionSample1();
        var actual = submissionMapper.toEntity(submissionMapper.toDto(expected));
        assertSubmissionAllPropertiesEquals(expected, actual);
    }
}
