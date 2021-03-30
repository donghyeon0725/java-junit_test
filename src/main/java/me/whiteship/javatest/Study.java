package me.whiteship.javatest;

public class Study {
    public StudyStatus status = StudyStatus.DRAFT;

    private int limit;

    public Study(int limit) {
        if (limit < 0) {
            throw new IllegalArgumentException("limit must be more than 0");
        }
        this.limit = limit;
    }

    public StudyStatus getStatus() {
        return this.status;
    }

    public int getLimit() {
        return limit;
    }
}
