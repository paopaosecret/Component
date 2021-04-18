package com.xbing.app.component.ui.activity.layer2;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;

import com.example.api.InjectHelper;
import com.example.lib_annotation.BindView;
import com.tencent.liteav.TXLiteAVCode;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.tencent.trtc.TRTCCloud;
import com.tencent.trtc.TRTCCloudDef;
import com.tencent.trtc.TRTCCloudListener;
import com.xbing.app.component.R;
import com.xbing.app.component.ui.activity.BaseActivity;
import com.xbing.app.component.utils.ToastUtils;
import com.xbing.app.component.utils.trtc.GenerateTestUserSig;

import java.util.ArrayList;
import java.util.List;

import static com.tencent.trtc.TRTCCloudDef.TRTC_APP_SCENE_VIDEOCALL;
import static com.tencent.trtc.TRTCCloudDef.TRTC_BEAUTY_STYLE_SMOOTH;
import static com.tencent.trtc.TRTCCloudDef.TRTC_RECORD_TYPE_BOTH;
import static com.tencent.trtc.TRTCCloudDef.TRTC_VIDEO_RENDER_MODE_FIT;

public class TrtcActivity extends BaseActivity {
    private static final int REQ_PERMISSION_CODE = 0x1000;
    private int                             mGrantedCount = 0;          // 权限个数计数，获取Android系统权限
    private String TAG = TrtcActivity.class.getSimpleName();

    TRTCCloud mTRTCCloud;

    @BindView(R.id.et_roomid)
    EditText etRoomId;

    @BindView(R.id.et_userid)
    EditText etuserId;

    TXCloudVideoView previewVideo;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trtc);
        InjectHelper.inject(this);

        initView();
        initData();
    }

    private void initData() {

        mTRTCCloud = TRTCCloud.sharedInstance(getApplicationContext());
        mTRTCCloud.setListener(new TRTCCloudListener(){
            // 错误通知监听，错误通知意味着 SDK 不能继续运行
            @Override
            public void onError(int errCode, String errMsg, Bundle extraInfo) {
                Log.d(TAG, "sdk onError：" + errCode + " ,errmsg:" + errMsg);

                Toast.makeText(TrtcActivity.this, "onError: " + errMsg + "[" + errCode+ "]" , Toast.LENGTH_SHORT).show();
                if (errCode == TXLiteAVCode.ERR_ROOM_ENTER_FAIL) {
//                    TrtcActivity.this.exitRoom();
                }

            }

            @Override
            public void onEnterRoom(long l) {
                Log.d(TAG, "sdk onEnterRoom result：" + l);
                if(l > 0){
                    ToastUtils.show("进房成功");
                }else{
                    ToastUtils.show("进房失败");
                }
            }

            @Override
            public void onUserVideoAvailable(String userId, boolean available) {
                Log.d(TAG, "sdk onUserVideoAvailable userId：" + userId + ",available:" + available);
                if (available) {
//                    mTRTCCloud.startRemoteSubStreamView(userId, previewVideo);
                    mTRTCCloud.startRemoteView(userId, previewVideo);
                    mTRTCCloud.setRemoteViewFillMode(userId, TRTC_VIDEO_RENDER_MODE_FIT);
                } else {
                    mTRTCCloud.stopRemoteView(userId);
                }
            }

            @Override
            public void onExitRoom(int i) {
                Log.d(TAG, "sdk onExitRoom result：" + i);
            }

            @Override
            public void onScreenCaptureStarted() {
                super.onScreenCaptureStarted();
                Log.d(TAG, "sdk onScreenCaptureStarted");
            }

            @Override
            public void onScreenCaptureStopped(int i) {
                Log.d(TAG, "sdk onScreenCaptureStopped");
                super.onScreenCaptureStopped(i);
            }
        });


    }

    private void initView() {
        findViewById(R.id.btn_enterroom).setOnClickListener(this);
        findViewById(R.id.btn_exitRoom).setOnClickListener(this);
        findViewById(R.id.btn_startPlayer).setOnClickListener(this);
        findViewById(R.id.btn_stopPlayer).setOnClickListener(this);
        findViewById(R.id.btn_addRoom).setOnClickListener(this);
        findViewById(R.id.btn_exitAdd).setOnClickListener(this);
        findViewById(R.id.btn_screenShared).setOnClickListener(this);
        findViewById(R.id.btn_exitScreenShared).setOnClickListener(this);
        previewVideo = findViewById(R.id.trtc_tc_cloud_view_main);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_enterroom:
                if(checkPermission()){
                    enterRoom(TRTC_APP_SCENE_VIDEOCALL);
                }
                break;
            case R.id.btn_exitRoom:
                mTRTCCloud.exitRoom();
                break;

            case R.id.btn_startPlayer:
                if(checkPermission()){
                    startPlayer();
                }
                break;
            case R.id.btn_stopPlayer:
                mTRTCCloud.exitRoom();
                break;

            case R.id.btn_addRoom:
                addRoom();
                break;
            case R.id.btn_exitAdd:
                mTRTCCloud.exitRoom();
                break;

            case R.id.btn_screenShared:
                startScreenCapture();
                break;

            case R.id.btn_exitScreenShared:
                mTRTCCloud.stopScreenCapture();
                break;
        }
    }

    private void startScreenCapture() {
        TRTCCloudDef.TRTCVideoEncParam param = new TRTCCloudDef.TRTCVideoEncParam();
        param.videoFps = 10;
        param.videoResolution = TRTCCloudDef.TRTC_VIDEO_RESOLUTION_1280_720;
        param.videoResolutionMode = TRTCCloudDef.TRTC_VIDEO_RESOLUTION_MODE_PORTRAIT;
        param.videoBitrate = 1600;
        param.enableAdjustRes = false;
        mTRTCCloud.startScreenCapture(param, new TRTCCloudDef.TRTCScreenShareParams());
    }

    private void addRoom() {
        if(TextUtils.isEmpty(etRoomId.getText().toString())){
            ToastUtils.show("请输入房间id");
            return;
        }

        if(TextUtils.isEmpty(etuserId.getText().toString())){
            ToastUtils.show("请输入用户id");
            return;
        }
        //TODO 1构建观众参数
        TRTCCloudDef.TRTCParams params = new TRTCCloudDef.TRTCParams();
        params.sdkAppId = 1400502298;
        params.userSig = GenerateTestUserSig.genTestUserSig(etuserId.getText().toString());
        params.userId = etuserId.getText().toString();
        params.roomId = Integer.parseInt(etRoomId.getText().toString());
        params.role = TRTCCloudDef.TRTCRoleAudience;

        //TODO 2进入房间
        mTRTCCloud.enterRoom(params, TRTCCloudDef.TRTC_APP_SCENE_LIVE);

        //TODO 3观看直播
        mTRTCCloud.startRemoteView(etuserId.getText().toString(), previewVideo);
    }

    private void startPlayer() {
        //示例代码：发布本地的音视频流
        mTRTCCloud.setLocalViewFillMode(TRTC_VIDEO_RENDER_MODE_FIT);
        mTRTCCloud.startLocalPreview(true, previewVideo);

        //设置本地视频编码参数
        TRTCCloudDef.TRTCVideoEncParam encParam = new TRTCCloudDef.TRTCVideoEncParam();
        encParam.videoResolution = TRTCCloudDef.TRTC_VIDEO_RESOLUTION_960_540;
        encParam.videoFps = 15;
        encParam.videoBitrate = 1200;
        encParam.videoResolutionMode = TRTCCloudDef.TRTC_VIDEO_RESOLUTION_MODE_PORTRAIT;
        mTRTCCloud.setVideoEncoderParam(encParam);
        mTRTCCloud.startLocalAudio();

        //设置美颜
        mTRTCCloud.getBeautyManager().setBeautyStyle(TRTC_BEAUTY_STYLE_SMOOTH);
        mTRTCCloud.getBeautyManager().setBeautyLevel(5);
        mTRTCCloud.getBeautyManager().setWhitenessLevel(5);

        //创建房间并进入房间
        enterRoom(TRTCCloudDef.TRTC_APP_SCENE_LIVE);
    }

    private void enterRoom(int type) {
        if(TextUtils.isEmpty(etRoomId.getText().toString())){
            ToastUtils.show("请输入房间id");
            return;
        }

        if(TextUtils.isEmpty(etuserId.getText().toString())){
            ToastUtils.show("请输入用户id");
            return;
        }
        TRTCCloudDef.TRTCParams params = new TRTCCloudDef.TRTCParams();
        params.sdkAppId = 1400502298;
        params.userSig = GenerateTestUserSig.genTestUserSig(etuserId.getText().toString());
        params.userId = etuserId.getText().toString();
        params.roomId = Integer.parseInt(etRoomId.getText().toString());

        mTRTCCloud.enterRoom(params, type);

        // 开启本地声音采集并上行
        mTRTCCloud.startLocalAudio();
        // 开启本地画面采集并上行
        mTRTCCloud.startLocalPreview(true, previewVideo);

    }

    @Override
    protected void onStop() {
        super.onStop();
    }


    //////////////////////////////////    Android动态权限申请   ////////////////////////////////////////

    private boolean checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            List<String> permissions = new ArrayList<>();
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA)) {
                permissions.add(Manifest.permission.CAMERA);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)) {
                permissions.add(Manifest.permission.RECORD_AUDIO);
            }
            if (PackageManager.PERMISSION_GRANTED != ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE);
            }
            if (permissions.size() != 0) {
                ActivityCompat.requestPermissions(TrtcActivity.this,
                        permissions.toArray(new String[0]),
                        REQ_PERMISSION_CODE);
                return false;
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQ_PERMISSION_CODE) {
            for (int ret : grantResults) {
                if (PackageManager.PERMISSION_GRANTED == ret) mGrantedCount++;
            }
            if (mGrantedCount == permissions.length) {
                enterRoom(TRTC_APP_SCENE_VIDEOCALL); //首次启动，权限都获取到，才能正常进入通话
            } else {
                Toast.makeText(this, "用户没有允许需要的权限，加入通话失败", Toast.LENGTH_SHORT).show();
            }
            mGrantedCount = 0;
        }
    }
}
