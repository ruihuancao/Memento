package com.memento.android.event;

import android.support.v4.util.Pair;
import android.view.View;

/**
 * Created by 曹瑞环 on 2016/8/15.
 */
public interface Event {

    class OpenMainActivityEvent{}

    class OpenZhihuMainActivity{}

    class OpenZhihuDetailActivity{
        public String id;
        public Pair<View, String>[] pairs;
        public OpenZhihuDetailActivity(String id, Pair<View, String>[] pairs) {
            this.id = id;
            this.pairs = pairs;
        }
    }


    class ShowMessageEvent {
        public String message;
        public ShowMessageEvent(String message) {
            this.message = message;
        }
    }

    class UpdateNavMenuEvent {
    }
}
