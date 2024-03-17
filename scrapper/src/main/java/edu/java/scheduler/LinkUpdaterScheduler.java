package edu.java.scheduler;

import edu.java.services.LinkUpdaterService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@EnableScheduling
@RequiredArgsConstructor
@ConditionalOnProperty(name = "app.scheduler.enable")
public class LinkUpdaterScheduler {
    private final LinkUpdaterService linkUpdaterService;

    @Scheduled(fixedDelayString = "#{@'app-edu.java.configuration.ApplicationConfig'.scheduler.interval}")
    public void update() {
        log.debug("Updating");
        linkUpdaterService.update();
    }
}
