package ca.onepoint.yul;

import ca.onepoint.yul.event.TrafficLightEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Component;

@Component
public class ApplicationStartup implements ApplicationListener<ApplicationReadyEvent> {

    @Autowired
    private SimpMessageSendingOperations messagingTemplate;
    public static int secondes = 0;
    private boolean isVerticalGreen = true;

    @Autowired
    private ApplicationEventPublisher appEventPublisher;

    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        try {
            int secondes = 0;
            while (true) {
                Thread.sleep(1000);
                secondes++;
                if (secondes == 86400) {
                    secondes = 0;
                }
                if (secondes % 30 == 0) {
                    this.appEventPublisher.publishEvent(new TrafficLightEvent(this, !isVerticalGreen));
                }
                messagingTemplate.convertAndSend("/topic/progress", secondes);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
