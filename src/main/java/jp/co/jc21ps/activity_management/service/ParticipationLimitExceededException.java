package jp.co.jc21ps.activity_management.service;

public class ParticipationLimitExceededException extends RuntimeException {
    public ParticipationLimitExceededException(String message) {
        super(message);
    }
}
