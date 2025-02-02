package com.aiden.submissionhub.service.mapper;

import com.aiden.submissionhub.domain.File;
import com.aiden.submissionhub.domain.Submission;
import com.aiden.submissionhub.service.dto.FileDTO;
import com.aiden.submissionhub.service.dto.SubmissionDTO;
import org.mapstruct.*;

/**
 * Mapper for the entity {@link File} and its DTO {@link FileDTO}.
 */
@Mapper(componentModel = "spring")
public interface FileMapper extends EntityMapper<FileDTO, File> {
    @Mapping(target = "submission", source = "submission", qualifiedByName = "submissionId")
    FileDTO toDto(File s);

    @Named("submissionId")
    @BeanMapping(ignoreByDefault = true)
    @Mapping(target = "id", source = "id")
    SubmissionDTO toDtoSubmissionId(Submission submission);
}
