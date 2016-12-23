package com.github.ruihuancao.player;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.pili.pldroid.player.AVOptions;
import com.pili.pldroid.player.PLMediaPlayer;
import com.pili.pldroid.player.widget.PLVideoView;

import static com.pili.pldroid.player.PLMediaPlayer.ERROR_CODE_404_NOT_FOUND;
import static com.pili.pldroid.player.PLMediaPlayer.ERROR_CODE_CONNECTION_REFUSED;
import static com.pili.pldroid.player.PLMediaPlayer.ERROR_CODE_CONNECTION_TIMEOUT;
import static com.pili.pldroid.player.PLMediaPlayer.ERROR_CODE_EMPTY_PLAYLIST;
import static com.pili.pldroid.player.PLMediaPlayer.ERROR_CODE_HW_DECODE_FAILURE;
import static com.pili.pldroid.player.PLMediaPlayer.ERROR_CODE_INVALID_URI;
import static com.pili.pldroid.player.PLMediaPlayer.ERROR_CODE_IO_ERROR;
import static com.pili.pldroid.player.PLMediaPlayer.ERROR_CODE_PREPARE_TIMEOUT;
import static com.pili.pldroid.player.PLMediaPlayer.ERROR_CODE_READ_FRAME_TIMEOUT;
import static com.pili.pldroid.player.PLMediaPlayer.ERROR_CODE_STREAM_DISCONNECTED;
import static com.pili.pldroid.player.PLMediaPlayer.ERROR_CODE_UNAUTHORIZED;
import static com.pili.pldroid.player.PLMediaPlayer.MEDIA_INFO_AUDIO_RENDERING_START;
import static com.pili.pldroid.player.PLMediaPlayer.MEDIA_INFO_BUFFERING_END;
import static com.pili.pldroid.player.PLMediaPlayer.MEDIA_INFO_BUFFERING_START;
import static com.pili.pldroid.player.PLMediaPlayer.MEDIA_INFO_SWITCHING_SW_DECODE;
import static com.pili.pldroid.player.PLMediaPlayer.MEDIA_INFO_UNKNOWN;
import static com.pili.pldroid.player.PLMediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START;
import static com.pili.pldroid.player.PLMediaPlayer.MEDIA_INFO_VIDEO_ROTATION_CHANGED;

public class VideoActivity extends AppCompatActivity implements PLMediaPlayer.OnInfoListener,
        PLMediaPlayer.OnVideoSizeChangedListener, PLMediaPlayer.OnBufferingUpdateListener,
        PLMediaPlayer.OnCompletionListener, PLMediaPlayer.OnSeekCompleteListener,
        PLMediaPlayer.OnErrorListener, PLMediaPlayer.OnPreparedListener {

    private static final String TAG = VideoActivity.class.getSimpleName();
    private static final String LIVE_STREAMING = "liveStreaming";
    private static final String CODE_TYPE = "codeType";
    private static final String AUTO_PLAY = "autoPlay";
    private static final String PLAY_URL = "playUrl";


    private PLVideoView mVideoView;
    private ImageView mCoverView;
    private LinearLayout mTitleLayout;
    private TextView mTitleView;
    private TextView mErrorText;
    private LinearLayout mLoadLayout;
    private ProgressBar mProgressBar;
    private AndroidMediaController mAndroidMediaController;

    private int liveStreaming;
    private int codeType;
    private int autoPlay;
    private String playUrl;
    private Uri uri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_video);
        init();
        initView();
        getExtra();
        setOptions();
        initVideo();
    }

    private void init(){
        // 隐藏nav bar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);
        // 屏幕常亮
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    }

    public static Intent getCallingIntent(Context context, String playUrl, int liveStreaming, int codeType, int autoPlay){
        Intent intent = new Intent(context, VideoActivity.class);
        intent.putExtra(LIVE_STREAMING, liveStreaming);
        intent.putExtra(CODE_TYPE, codeType);
        intent.putExtra(AUTO_PLAY, autoPlay);
        intent.putExtra(PLAY_URL, playUrl);
        return intent;
    }

    private void getExtra(){
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if ("com.github.ruihuancao.player.VIEW".equals(action) && type != null) {
            if (type.startsWith("video/")) {
                uri = intent.getData();
            }
        }
        if(uri == null){
            this.liveStreaming = intent.getIntExtra(LIVE_STREAMING, 0);
            this.autoPlay = intent.getIntExtra(AUTO_PLAY, 0);
            this.codeType = intent.getIntExtra(CODE_TYPE, 0);
            this.playUrl = intent.getStringExtra(PLAY_URL);
        }
    }

    private void initView(){
        mVideoView = (PLVideoView) findViewById(R.id.video_view);
        mCoverView = (ImageView) findViewById(R.id.cover_view);
        mTitleLayout = (LinearLayout)findViewById(R.id.title_layout);
        mTitleView = (TextView)findViewById(R.id.title);
        mErrorText = (TextView)findViewById(R.id.error_text);
        mLoadLayout = (LinearLayout) findViewById(R.id.load_layout);
        mProgressBar = (ProgressBar)findViewById(R.id.progressbar);
        mAndroidMediaController = (AndroidMediaController)findViewById(R.id.controller);
    }

    private void initVideo(){
        if(TextUtils.isEmpty(playUrl)){
            return;
        }
        mVideoView.setBufferingIndicator(mLoadLayout);
        mVideoView.setCoverView(mCoverView);
        mVideoView.setOnInfoListener(this);
        mVideoView.setOnVideoSizeChangedListener(this);
        mVideoView.setOnBufferingUpdateListener(this);
        mVideoView.setOnCompletionListener(this);
        mVideoView.setOnSeekCompleteListener(this);
        mVideoView.setOnErrorListener(this);
        mAndroidMediaController.setDisableProgress(liveStreaming == 1);
        if(uri != null){
            mVideoView.setVideoURI(uri);
        }else{
            mVideoView.setVideoPath(playUrl);
        }
        mVideoView.setMediaController(mAndroidMediaController);
        mVideoView.setDisplayAspectRatio(PLVideoView.ASPECT_RATIO_FIT_PARENT);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mVideoView.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mVideoView.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mVideoView.stopPlayback();
    }

    private void reConnect(){
        mVideoView.setVideoPath(playUrl);
        mVideoView.start();
    }

    private void setOptions() {
        AVOptions options = new AVOptions();
        // 当前播放的是否为在线直播，如果是，则底层会有一些播放优化
        // 默认值是：0
        options.setInteger(AVOptions.KEY_LIVE_STREAMING, liveStreaming);
        // 读取视频流超时时间，单位是 ms
        // 默认值是：10 * 1000
        options.setInteger(AVOptions.KEY_GET_AV_FRAME_TIMEOUT, 10 * 1000);
        // 准备超时时间，包括创建资源、建立连接、请求码流等，单位是 ms
        // 默认值是：无
        options.setInteger(AVOptions.KEY_PREPARE_TIMEOUT, 10 * 1000);
        // 解码方式:
        // codec＝AVOptions.MEDIA_CODEC_HW_DECODE，硬解
        // codec=AVOptions.MEDIA_CODEC_SW_DECODE, 软解
        // codec=AVOptions.MEDIA_CODEC_AUTO, 硬解优先，失败后自动切换到软解
        // 默认值是：MEDIA_CODEC_SW_DECODE
        options.setInteger(AVOptions.KEY_MEDIACODEC, codeType);
        // 是否开启"延时优化"，只在在线直播流中有效
        // 默认值是：0
        options.setInteger(AVOptions.KEY_DELAY_OPTIMIZATION, 0);
        // 默认的缓存大小，单位是 ms
        // 默认值是：2000
        options.setInteger(AVOptions.KEY_CACHE_BUFFER_DURATION, 2000);
        // 最大的缓存大小，单位是 ms
        // 默认值是：4000
        options.setInteger(AVOptions.KEY_MAX_CACHE_BUFFER_DURATION, 4000);
        // 是否自动启动播放，如果设置为 1，则在调用 `prepareAsync` 或者 `setVideoPath` 之后自动启动播放，无需调用 `start()`
        // 默认值是：1
        options.setInteger(AVOptions.KEY_START_ON_PREPARED, autoPlay);
        // 播放前最大探测流的字节数，单位是 byte
        // 默认值是：128 * 1024
        options.setInteger(AVOptions.KEY_PROBESIZE, 128 * 1024);
        mVideoView.setAVOptions(options);
    }

    /**
     * 该对象用于监听播放器的 prepare 过程，该过程主要包括：创建资源、建立连接、请求码流等等，
     * 当 prepare 完成后，SDK 会回调该对象的 onPrepared 接口，下一步则可以调用播放器的 start() 启动播放。
     * @param plMediaPlayer
     */
    @Override
    public void onPrepared(PLMediaPlayer plMediaPlayer) {

    }

    /**
     * 该对象用于监听播放器的状态消息，在播放器启动后，
     * SDK 会在播放器发生状态变化时调用该对象的 onInfo 方法，同步状态信息。
     * @param plMediaPlayer
     * @param what
     * @param extra
     * @return
     */
    @Override
    public boolean onInfo(PLMediaPlayer plMediaPlayer, int what, int extra) {
        String message = null;
        switch (what){
            case MEDIA_INFO_UNKNOWN:
                message = "未知消息";
                break;
            case MEDIA_INFO_VIDEO_RENDERING_START:
                message = "第一帧视频已成功渲染";
                break;
            case MEDIA_INFO_BUFFERING_START:
                message = "开始缓冲";
                mAndroidMediaController.show();
                break;
            case MEDIA_INFO_BUFFERING_END:
                message = "停止缓冲";
                break;
            case MEDIA_INFO_VIDEO_ROTATION_CHANGED:
                message = "获取到视频的播放角度";
                break;
            case MEDIA_INFO_AUDIO_RENDERING_START:
                message = "第一帧音频已成功播放";
                break;
            case MEDIA_INFO_SWITCHING_SW_DECODE:
                message = "硬解失败，自动切换软解";
                break;
        }
        Log.d(TAG, "onInfo: " + message);
        return false;
    }

    /**
     * 该回调用于监听当前播放器已经缓冲的数据量占整个视频时长的百分比，
     * 在播放直播流中无效，仅在播放文件和回放时才有效。
     * @param plMediaPlayer
     * @param percent
     */
    @Override
    public void onBufferingUpdate(PLMediaPlayer plMediaPlayer, int percent) {
        Log.d(TAG, "onBufferingUpdate: " + percent);
    }

    /**
     * 该对象用于监听播放结束的消息，关于该回调的时机，有如下定义：
     如果是播放文件，则是播放到文件结束后产生回调
     如果是在线视频，则会在读取到码流的EOF信息后产生回调，回调前会先播放完已缓冲的数据
     如果播放过程中产生onError，并且没有处理的话，最后也会回调本接口
     如果播放前设置了 setLooping(true)，则播放结束后会自动重新开始，不会回调本接口
     * @param plMediaPlayer
     */
    @Override
    public void onCompletion(PLMediaPlayer plMediaPlayer) {
        Log.d(TAG, "onCompletion");
    }

    /**
     * 该对象用于监听播放器的错误消息，一旦播放过程中产生任何错误信息，SDK 都会回调该接口，
     * 返回值决定了该错误是否已经被处理，如果返回 false，则代表没有被处理，下一步则会触发 onCompletion 消息。
     * @param plMediaPlayer
     * @param errorCode
     * @return
     */
    @Override
    public boolean onError(PLMediaPlayer plMediaPlayer, int errorCode) {
        String message = null;
        switch (errorCode){
            case ERROR_CODE_INVALID_URI:
                message = "无效的 URL";
                break;
            case ERROR_CODE_IO_ERROR:
                message = "网络异常";
                break;
            case ERROR_CODE_STREAM_DISCONNECTED:
                message = "与服务器连接断开";
                break;
            case ERROR_CODE_EMPTY_PLAYLIST:
                message = "空的播放列表";
                break;
            case ERROR_CODE_404_NOT_FOUND:
                message = "播放资源不存在";
                break;
            case ERROR_CODE_CONNECTION_REFUSED:
                message = "服务器拒绝连接";
                break;
            case ERROR_CODE_CONNECTION_TIMEOUT:
                message = "连接超时";
                break;
            case ERROR_CODE_UNAUTHORIZED:
                message = "未授权，播放一个禁播的流";
                break;
            case ERROR_CODE_PREPARE_TIMEOUT:
                message = "播放器准备超时";
                break;
            case ERROR_CODE_READ_FRAME_TIMEOUT:
                message = "读取数据超时";
                break;
            case ERROR_CODE_HW_DECODE_FAILURE:
                message = "硬解码失败";
                break;
            default:
                message = "未知错误";
                break;
        }
        Log.d(TAG, "onError:"+message);
        return false;
    }

    /**
     * 该回调用于监听 seek 完成的消息，当调用的播放器的 seekTo 方法后，SDK 会在 seek 成功后触发该回调。
     * @param plMediaPlayer
     */
    @Override
    public void onSeekComplete(PLMediaPlayer plMediaPlayer) {
        Log.d(TAG, "onSeekComplete");
    }

    /**
     * 该回调用于监听当前播放的视频流的尺寸信息，
     * 在 SDK 解析出视频的尺寸信息后，会触发该回调，开发者可以在该回调中调整 UI 的视图尺寸。
     * @param plMediaPlayer
     * @param width
     * @param height
     */
    @Override
    public void onVideoSizeChanged(PLMediaPlayer plMediaPlayer,  int width, int height) {
        Log.d(TAG, "onVideoSizeChanged width:"+ width+"--height:"+height);
    }
}
