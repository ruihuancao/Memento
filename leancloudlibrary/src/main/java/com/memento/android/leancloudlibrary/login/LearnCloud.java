package com.memento.android.leancloudlibrary.login;

import android.app.Application;
import android.util.Log;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.SignUpCallback;

/**
 * Created by android on 16-6-12.
 */
public class LearnCloud {

    private static final String USER_ICON = "icon";


    public static void init(Application application){
        AVOSCloud.initialize(application.getApplicationContext(),"qPcCKVCPUAgtOcSiVG1XVHHF-gzGzoHsz","rQbVq51Uo2pYHdtWyy4FHA7x");
    }

    public static void test(){
        AVObject testObject = new AVObject("TestObject");
        testObject.put("words","Hello World!");
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    Log.d("saved","success!");
                }
            }
        });
    }

    public static void login(String name, String passwd, final Lisenter lisenter){
        AVUser.logInInBackground(name, passwd, new LogInCallback<AVUser>() {
            @Override
            public void done(AVUser avUser, AVException e) {
                if(e == null){
                    lisenter.success();
                }else{
                    lisenter.fail("用户名或密码不正确");
                }
            }
        });
    }

    public static void register(String name, String email, String passwd, final Lisenter lisenter){
        AVUser user = new AVUser();// 新建 AVUser 对象实例
        user.setUsername(name);// 设置用户名
        user.setPassword(passwd);// 设置密码
        user.setEmail(email);// 设置邮箱
        user.put(USER_ICON, getDefaultIcon(email));
        user.signUpInBackground(new SignUpCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    lisenter.success();
                }else if(e.getCode() == 202){
                    lisenter.fail("用户名已经存在");
                }else if(e.getCode() == 203){
                    lisenter.fail("邮箱已经存在");
                }else{
                    lisenter.fail("未知错误");
                }
            }
        });
    }


    private static String getDefaultIcon(String email){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("https://cdn.v2ex.com/gravatar/");
        stringBuilder.append(MD5Util.md5Hex(email));
        return stringBuilder.toString();
    }

    public static String getUserIcon(){
        return isLogin() ? String.valueOf(getCurrentUser().get(USER_ICON)) : null;
    }

    public static String getUserName(){
        return isLogin() ? getCurrentUser().getUsername() : "";
    }

    public static boolean isLogin(){
        return AVUser.getCurrentUser() != null;
    }

    public static AVUser getCurrentUser(){
        return AVUser.getCurrentUser();
    }



    public interface Lisenter{
        void success();
        void fail(String message);
    }
}
