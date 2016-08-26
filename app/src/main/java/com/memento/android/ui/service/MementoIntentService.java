package com.memento.android.ui.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;


public class MementoIntentService extends IntentService {
    // IntentService can perform, e.g. ACTION_FETCH_NEW_ITEMS
    private static final String ACTION_LOCALTION = "com.memento.android.ui.service.action.LOCALTION";

    private static final String EXTRA_PARAM1 = "com.memento.android.ui.service.extra.PARAM1";
    private static final String EXTRA_PARAM2 = "com.memento.android.ui.service.extra.PARAM2";

    public MementoIntentService() {
        super("MementoIntentService");
    }

    public static void startActionlocaltion(Context context, String param1, String param2) {
        Intent intent = new Intent(context, MementoIntentService.class);
        intent.setAction(ACTION_LOCALTION);
        intent.putExtra(EXTRA_PARAM1, param1);
        intent.putExtra(EXTRA_PARAM2, param2);
        context.startService(intent);
    }


    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_LOCALTION.equals(action)) {
                final String param1 = intent.getStringExtra(EXTRA_PARAM1);
                final String param2 = intent.getStringExtra(EXTRA_PARAM2);
                handleActionLocaltion(param1, param2);
            }
        }
    }

    private void handleActionLocaltion(String param1, String param2) {
        // TODO: Handle action Foo
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
