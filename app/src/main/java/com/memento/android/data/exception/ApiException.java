package com.memento.android.data.exception;

/**
 * Created by android on 16-11-22.
 */

public class ApiException extends Exception{

    public ApiException(){
    }

    public ApiException(String message){
        super(message);
    }
}
