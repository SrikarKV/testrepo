package com.aiden.submissionhub.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class FileTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static File getFileSample1() {
        return new File().id(1L).fileName("fileName1").fileDownloadUri("fileDownloadUri1").answers("answers1");
    }

    public static File getFileSample2() {
        return new File().id(2L).fileName("fileName2").fileDownloadUri("fileDownloadUri2").answers("answers2");
    }

    public static File getFileRandomSampleGenerator() {
        return new File()
            .id(longCount.incrementAndGet())
            .fileName(UUID.randomUUID().toString())
            .fileDownloadUri(UUID.randomUUID().toString())
            .answers(UUID.randomUUID().toString());
    }
}
