package constants

class JobStatus {
    static final String STARTED = "STARTED"
    static final String SUCCESS = "SUCCESS"
    static final String FAILURE = "FAILURE"
    static final String ABORTED = "ABORTED"
    static final String UNSTABLE = "UNSTABLE"
    static final String[] FAILURE_STATUSES = [
        FAILURE,
        UNSTABLE
    ]
}
