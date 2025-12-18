package com.example.dailydriver.event;

import com.example.dailydriver.entity.Application;

public class ApplicationRejectedEvent {

    private final Application application;

    public ApplicationRejectedEvent(Application application) {
        this.application = application;
    }

    public Application getApplication() {
        return application;
    }
}
