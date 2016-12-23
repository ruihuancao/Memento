package com.github.ruihuancao.network;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.github.ruihuancao.network.download.DownloadManager;
import com.github.ruihuancao.network.httpstack.OkHttp3Stack;
import com.github.ruihuancao.network.request.CacheStringRequest;
import com.github.ruihuancao.network.response.ResponseListener;
import com.github.ruihuancao.network.upload.UploadManager;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import rx.Observable;
import rx.Subscriber;

/**
 * 请求处理
 */
public class RequestManager {

    private static RequestManager instance;
    private RequestQueue mRequestQueue;
    private OkHttpClient mOkhttpClient;
    private DownloadManager mDownloadManager;
    private UploadManager mUploadManager;
    private boolean isLog;

    private RequestManager(Context context) {
        isLog = false;
        mOkhttpClient = initOkhttpClient();
        mRequestQueue = Volley.newRequestQueue(context, new OkHttp3Stack(mOkhttpClient));
        mDownloadManager = new DownloadManager(context, mOkhttpClient);
        mUploadManager = new UploadManager(mOkhttpClient);
    }

    /**
     * 单例
     *
     * @param context
     * @return
     */
    public static RequestManager getInstance(Context context) {
        if (instance == null) {
            instance = new RequestManager(context);
        }
        return instance;
    }

    /**
     * 初始化okhttpclient
     *
     * @return
     */
    private OkHttpClient initOkhttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(isLog ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        builder.addInterceptor(logging);
        builder.connectTimeout(15, TimeUnit.SECONDS);
        return builder.build();
    }

    /**
     * 检查是否初始化
     */
    private static void throwIfNotInit() {
        if (instance == null) {
            throw new IllegalStateException("Volley尚未初始化");
        }
    }

    /**
     * RequestQueue
     *
     * @return
     */
    private RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            throwIfNotInit();
        }
        return mRequestQueue;
    }

    /**
     * 返回OkHttpClient
     *
     * @return
     */
    public OkHttpClient getOkhttpClient() {
        return mOkhttpClient;
    }

    public void get(String url, ResponseListener<String> responseListener) {
        get(url, null, null, responseListener);
    }

    public void get(String url, Map<String, String> params, ResponseListener<String> responseListener) {
        get(url, params, null, responseListener);
    }

    public void get(String url, final Map<String, String> params, final Map<String, String> header, ResponseListener<String> responseListener) {
        addRequest(Request.Method.GET, url, params, header, responseListener);
    }


    public void post(String url, final Map<String, String> params, ResponseListener<String> responseListener) {
        post(url, params, null, responseListener);
    }

    public void post(String url, final Map<String, String> params, final Map<String, String> header, ResponseListener<String> responseListener) {
        addRequest(Request.Method.POST, url, params, header, responseListener);
    }

    public Observable<String> sendGet(final String url) {
        return sendGet(url, null, null);
    }

    public Observable<String> sendGet(final String url, final Map<String, String> params) {
        return sendGet(url, params, null);
    }

    public Observable<String> sendGet(final String url, final Map<String, String> params, final Map<String, String> header) {
        return sendRequest(Request.Method.GET, url, params, header);
    }

    public Observable<String> sendPost(final String url, final Map<String, String> params) {
        return sendPost(url, params, null);
    }

    public Observable<String> sendPost(final String url, final Map<String, String> params, final Map<String, String> header) {
        return sendRequest(Request.Method.POST, url, params, header);
    }

    public Observable<DownloadManager.DownLoad> download(String url) {
        return mDownloadManager.downLoadFile(url);
    }

    public Observable<String> upLoad(final String url, final HashMap<String, Object> params) {
        return mUploadManager.upLoadFile(url, params);
    }

    private void addRequest(int method, String url, final Map<String, String> params,
                            final Map<String, String> header, final ResponseListener<String> responseListener) {
        CacheStringRequest request = buildStringRequest(method, url, params, header,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        responseListener.onSuccess(response);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        responseListener.onFail();
                    }
                });
        getRequestQueue().add(request);
    }

    private Observable<String> sendRequest(final int method, final String url, final Map<String, String> params, final Map<String, String> header) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> subscriber) {
                CacheStringRequest request = buildStringRequest(method, url, params, header,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                subscriber.onNext(response);
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                subscriber.onError(error);
                            }
                        });
                getRequestQueue().add(request);
            }
        });
    }


    public CacheStringRequest buildStringRequest(int method, String url, final Map<String, String> params,
                                                 final Map<String, String> header,
                                                 Response.Listener<String> listener,
                                                 Response.ErrorListener errorListener) {
        CacheStringRequest request = null;
        switch (method) {
            case Request.Method.GET:
                request = buildGetRequest(url, params, header, listener, errorListener);
                break;
            case Request.Method.POST:
                request = buildPostRequest(url, params, header, listener, errorListener);
        }
        return request;
    }


    public CacheStringRequest buildGetRequest(String url, final Map<String, String> params,
                                              final Map<String, String> header,
                                              Response.Listener<String> listener,
                                              Response.ErrorListener errorListener) {
        if (params != null && params.size() > 0) {
            url = encodeParameters(url, params);
        }
        return new CacheStringRequest(url, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (header != null && header.size() > 0) {
                    return header;
                }
                return super.getHeaders();
            }
        };
    }

    public CacheStringRequest buildPostRequest(String url, final Map<String, String> params,
                                               final Map<String, String> header,
                                               Response.Listener<String> listener,
                                               Response.ErrorListener errorListener) {
        return new CacheStringRequest(Request.Method.POST, url, listener, errorListener) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                if (header != null && header.size() > 0) {
                    return header;
                }
                return super.getHeaders();
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                if (params != null) {
                    return params;
                }
                return super.getParams();
            }
        };
    }

    private String encodeParameters(String url, Map<String, String> params) {
        if (params == null || params.size() <= 0) {
            return url;
        }
        StringBuilder encodedParams = new StringBuilder(url);
        try {
            if (!url.contains("?")) {
                encodedParams.append("?");
            } else {
                encodedParams.append('&');
            }

            for (Map.Entry<String, String> entry : params.entrySet()) {
                encodedParams.append(URLEncoder.encode(entry.getKey(), "utf-8"));
                encodedParams.append('=');
                encodedParams.append(URLEncoder.encode(entry.getValue(), "utf-8"));
                encodedParams.append('&');
            }
        } catch (UnsupportedEncodingException uee) {
            throw new RuntimeException("Encoding not supported: " + "utf-8", uee);
        }
        return encodedParams.substring(0, encodedParams.length() - 1);
    }
}
