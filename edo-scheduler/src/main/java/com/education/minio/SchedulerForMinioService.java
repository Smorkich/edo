package com.education.minio;

/**
 * Scheduler class
 */
public interface SchedulerForMinioService {

    /**
        Method removes old files in MINIO server. For schedule removing old file in MINIO-server,
     * old files - are files with the date of the last modification equal to about a month.
     * Method runs ones time a week
     */
    void delete();

}
