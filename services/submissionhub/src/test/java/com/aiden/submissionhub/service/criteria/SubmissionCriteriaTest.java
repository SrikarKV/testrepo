package com.aiden.submissionhub.service.criteria;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.function.BiFunction;
import java.util.function.Function;
import org.assertj.core.api.Condition;
import org.junit.jupiter.api.Test;

class SubmissionCriteriaTest {

    @Test
    void newSubmissionCriteriaHasAllFiltersNullTest() {
        var submissionCriteria = new SubmissionCriteria();
        assertThat(submissionCriteria).is(criteriaFiltersAre(filter -> filter == null));
    }

    @Test
    void submissionCriteriaFluentMethodsCreatesFiltersTest() {
        var submissionCriteria = new SubmissionCriteria();

        setAllFilters(submissionCriteria);

        assertThat(submissionCriteria).is(criteriaFiltersAre(filter -> filter != null));
    }

    @Test
    void submissionCriteriaCopyCreatesNullFilterTest() {
        var submissionCriteria = new SubmissionCriteria();
        var copy = submissionCriteria.copy();

        assertThat(submissionCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter == null)),
            criteria -> assertThat(criteria).isEqualTo(submissionCriteria)
        );
    }

    @Test
    void submissionCriteriaCopyDuplicatesEveryExistingFilterTest() {
        var submissionCriteria = new SubmissionCriteria();
        setAllFilters(submissionCriteria);

        var copy = submissionCriteria.copy();

        assertThat(submissionCriteria).satisfies(
            criteria ->
                assertThat(criteria).is(
                    copyFiltersAre(copy, (a, b) -> (a == null || a instanceof Boolean) ? a == b : (a != b && a.equals(b)))
                ),
            criteria -> assertThat(criteria).isEqualTo(copy),
            criteria -> assertThat(criteria).hasSameHashCodeAs(copy)
        );

        assertThat(copy).satisfies(
            criteria -> assertThat(criteria).is(criteriaFiltersAre(filter -> filter != null)),
            criteria -> assertThat(criteria).isEqualTo(submissionCriteria)
        );
    }

    @Test
    void toStringVerifier() {
        var submissionCriteria = new SubmissionCriteria();

        assertThat(submissionCriteria).hasToString("SubmissionCriteria{}");
    }

    private static void setAllFilters(SubmissionCriteria submissionCriteria) {
        submissionCriteria.id();
        submissionCriteria.caseId();
        submissionCriteria.submissionStatus();
        submissionCriteria.insuredName();
        submissionCriteria.assignTo();
        submissionCriteria.assignToEmail();
        submissionCriteria.assignedBY();
        submissionCriteria.assignedByEmail();
        submissionCriteria.taskDueDate();
        submissionCriteria.createdDate();
        submissionCriteria.filesId();
        submissionCriteria.distinct();
    }

    private static Condition<SubmissionCriteria> criteriaFiltersAre(Function<Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId()) &&
                condition.apply(criteria.getCaseId()) &&
                condition.apply(criteria.getSubmissionStatus()) &&
                condition.apply(criteria.getInsuredName()) &&
                condition.apply(criteria.getAssignTo()) &&
                condition.apply(criteria.getAssignToEmail()) &&
                condition.apply(criteria.getAssignedBY()) &&
                condition.apply(criteria.getAssignedByEmail()) &&
                condition.apply(criteria.getTaskDueDate()) &&
                condition.apply(criteria.getCreatedDate()) &&
                condition.apply(criteria.getFilesId()) &&
                condition.apply(criteria.getDistinct()),
            "every filter matches"
        );
    }

    private static Condition<SubmissionCriteria> copyFiltersAre(SubmissionCriteria copy, BiFunction<Object, Object, Boolean> condition) {
        return new Condition<>(
            criteria ->
                condition.apply(criteria.getId(), copy.getId()) &&
                condition.apply(criteria.getCaseId(), copy.getCaseId()) &&
                condition.apply(criteria.getSubmissionStatus(), copy.getSubmissionStatus()) &&
                condition.apply(criteria.getInsuredName(), copy.getInsuredName()) &&
                condition.apply(criteria.getAssignTo(), copy.getAssignTo()) &&
                condition.apply(criteria.getAssignToEmail(), copy.getAssignToEmail()) &&
                condition.apply(criteria.getAssignedBY(), copy.getAssignedBY()) &&
                condition.apply(criteria.getAssignedByEmail(), copy.getAssignedByEmail()) &&
                condition.apply(criteria.getTaskDueDate(), copy.getTaskDueDate()) &&
                condition.apply(criteria.getCreatedDate(), copy.getCreatedDate()) &&
                condition.apply(criteria.getFilesId(), copy.getFilesId()) &&
                condition.apply(criteria.getDistinct(), copy.getDistinct()),
            "every filter matches"
        );
    }
}
