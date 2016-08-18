package com.memento.android.event;

/**
 * Created by 曹瑞环 on 2016/8/15.
 */
public interface Event {

    class OpenMainActivityEvent{}

    class ShowMessageEvent {
        public String message;
        public ShowMessageEvent(String message) {
            this.message = message;
        }
    }

    class UpdateNavMenuEvent {
    }
}
