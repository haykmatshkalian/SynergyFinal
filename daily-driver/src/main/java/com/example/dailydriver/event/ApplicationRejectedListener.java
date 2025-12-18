package com.example.dailydriver.event;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ApplicationRejectedListener {

    @EventListener
    public void handle(ApplicationRejectedEvent event) {
        System.out.println(
                "[AUDIT EVENT] Application REJECTED. ID = " +
                        event.getApplication().getApplicationId()
        );
    }
}
