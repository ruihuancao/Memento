package com.memento.android.helper;

import com.memento.android.MementoApplication;
import com.memento.android.assistlibrary.data.DataManager;


public class DataHelper {

    public static DataManager getData(){
        return  DataManager.getInstance(MementoApplication.getMementoApplication());
    }
}
