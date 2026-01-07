package com.example.dailydriver.dto;

import com.example.dailydriver.entity.Application;
import com.example.dailydriver.entity.ApplicationStatus;

import java.time.OffsetDateTime;

public class ApplicationResponseDto {

    private Long application_id;
    private Long citizen_id;
    private Long program_id;
    private ApplicationStatus status;
    private OffsetDateTime submission_date;
    private boolean is_draft;

    public ApplicationResponseDto(Application application) {
        this.application_id = application.getApplicationId();
        this.citizen_id = application.getCitizen().getCitizenId();
        this.program_id = application.getProgram().getProgramId();
        this.status = application.getStatus();
        this.submission_date = application.getSubmissionDate();
        this.is_draft = application.getIsDraft();
    }

    public Long getApplication_id() {
        return application_id;
    }

    public Long getCitizen_id() {
        return citizen_id;
    }

    public Long getProgram_id() {
        return program_id;
    }

    public ApplicationStatus getStatus() {
        return status;
    }

    public OffsetDateTime getSubmission_date() {
        return submission_date;
    }

    public boolean isIs_draft() {
        return is_draft;
    }
}
