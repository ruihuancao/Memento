package com.memento.android;

import com.google.gson.Gson;

import org.junit.Test;

import java.lang.reflect.Type;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void testStrToObject(){
        Gson gson = new Gson();
        User user = new User();
        user.id = "1";
        user.name = "harry";
        String data = gson.toJson(user);
        assertNotNull(data);
        Object obj = strToObject(data, User.class);
        assertNotNull(obj);
    }

    public  <T> T strToObject(String json, Type typeOfT){
        return new Gson().fromJson(json, typeOfT);
    }


    class User{
        String id ;
        String name;
    }
}