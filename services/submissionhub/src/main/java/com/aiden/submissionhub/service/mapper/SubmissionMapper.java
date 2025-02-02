package com.aiden.submissionhub.service.mapper;

import com.aiden.submissionhub.domain.Submission;
import com.aiden.submissionhub.service.dto.SubmissionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link Submission} and its DTO {@link SubmissionDTO}.
 */
@Mapper(componentModel = "spring")
public interface SubmissionMapper extends EntityMapper<SubmissionDTO, Submission> {}
