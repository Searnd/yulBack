package ca.onepoint.yul.event;

import org.springframework.context.ApplicationEvent;

public class TrafficLightEvent extends ApplicationEvent {
    public TrafficLightEvent(Object source, boolean isVerticalGreen) {
        super(source);
        this._isVerticalGreen = isVerticalGreen;
    }

    public boolean getIsVerticalGreen() {
        return this._isVerticalGreen;
    }

    private boolean _isVerticalGreen;
}
