package com.aiden.submissionhub.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class SubmissionTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Submission getSubmissionSample1() {
        return new Submission()
            .id(1L)
            .caseId(UUID.fromString("23d8dc04-a48b-45d9-a01d-4b728f0ad4aa"))
            .insuredName("insuredName1")
            .assignTo("assignTo1")
            .assignToEmail("assignToEmail1")
            .assignedBY("assignedBY1")
            .assignedByEmail("assignedByEmail1");
    }

    public static Submission getSubmissionSample2() {
        return new Submission()
            .id(2L)
            .caseId(UUID.fromString("ad79f240-3727-46c3-b89f-2cf6ebd74367"))
            .insuredName("insuredName2")
            .assignTo("assignTo2")
            .assignToEmail("assignToEmail2")
            .assignedBY("assignedBY2")
            .assignedByEmail("assignedByEmail2");
    }

    public static Submission getSubmissionRandomSampleGenerator() {
        return new Submission()
            .id(longCount.incrementAndGet())
            .caseId(UUID.randomUUID())
            .insuredName(UUID.randomUUID().toString())
            .assignTo(UUID.randomUUID().toString())
            .assignToEmail(UUID.randomUUID().toString())
            .assignedBY(UUID.randomUUID().toString())
            .assignedByEmail(UUID.randomUUID().toString());
    }
}
