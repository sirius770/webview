package vip.sirius.web.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import androidx.annotation.NonNull;

import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

import java.util.List;

import vip.sirius.web.R;


/**
 * Created with IntelliJ IDEA.
 * Author: sirius
 * Date: 2020/2/6
 * Time: 12:21
 */
public abstract class SimpleSplashActivity extends CommActivity {

    private static final int REQUEST_CODE_REQUEST_PERMISSION = 200;
    /**
     * 用户自己去设置
     */
    private static final int REQUEST_CODE_SETTING_BY_USER_SELF = 201;


    private static final int MSG_WHAT_CHECK_PERMISSION = 101;

    private static final int MSG_WHAT_PERMISSION_INIT_SUCCESS = 110;

    private static final int MSG_WHAT_INIT_FAIL = 120;

    private static final int MSG_WHAT_ALL_INIT_SUCCESS = 130;


    protected Handler splashHandler = new SplashHandler();

    private class SplashHandler extends Handler {
        @Override
        public final void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_WHAT_CHECK_PERMISSION:
                    checkPermission();
                    break;
                case MSG_WHAT_PERMISSION_INIT_SUCCESS:
                    onPermissionInitSuccess();
                    break;
                case MSG_WHAT_INIT_FAIL:
                    onPermissionInitFail((String) msg.obj);
                    break;
                case MSG_WHAT_ALL_INIT_SUCCESS:
                    startMainActivity();
                    break;
                default:
                    break;
            }
        }
    }


    @Override
    public void initView() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void initData() {
        splashHandler.sendEmptyMessageDelayed(MSG_WHAT_CHECK_PERMISSION, 60L);
    }

    protected abstract String[] getRequestPermissions();

    protected abstract void onPermissionInitSuccess();

    protected abstract void onPermissionInitFail(String errMsg);

    /**
     * 继承者调用
     * 当自己的初始化完成时调用该方法
     */
    protected void onMyselfInitSuccess() {
        splashHandler.sendEmptyMessage(MSG_WHAT_ALL_INIT_SUCCESS);
    }

    protected abstract void startMainActivity();

    private void checkPermission() {
        String[] requestPermissions = getRequestPermissions();

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            AndPermission.with(this)
                    .runtime()
                    .permission(requestPermissions)
                    .rationale((context, data, executor) -> {
                        List<String> permissionNames = Permission.transformText(thisActivity, data);
                        showNormalAlert(R.string.warm_info, getString(R.string.request_permission_by_user_self, TextUtils.join("\n", permissionNames)), R.string.accept, R.string.reject, new IAlertButtonClick() {
                            @Override
                            public void onClickPositiveButton(DialogInterface dialog) {
                                executor.execute();
                            }

                            @Override
                            public void onClickNegativeButton(DialogInterface dialog) {
                                executor.cancel();
                                splashHandler.sendMessage(splashHandler.obtainMessage(MSG_WHAT_INIT_FAIL, "授权取消,程序退出!"));
                            }
                        });
                    })
                    .onGranted(permissions -> {
                        splashHandler.sendEmptyMessageDelayed(MSG_WHAT_PERMISSION_INIT_SUCCESS, 10);
                    })
                    .onDenied(permissions -> {
                        List<String> permissionNames = Permission.transformText(thisActivity, permissions);
                        String errMsg = TextUtils.join("\n", permissionNames);

                        showToast("缺少权限:" + errMsg);

                        if (AndPermission.hasAlwaysDeniedPermission(thisActivity, permissions)) {
                            AndPermission.with(this)
                                    .runtime()
                                    .setting()
                                    .start(REQUEST_CODE_SETTING_BY_USER_SELF);
                        } else {
                            AndPermission.with(thisActivity).runtime().setting().start(REQUEST_CODE_SETTING_BY_USER_SELF);
                        }
                    })
                    .start();
        } else {
            splashHandler.sendEmptyMessageDelayed(MSG_WHAT_PERMISSION_INIT_SUCCESS, 10);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_REQUEST_PERMISSION) {
            splashHandler.sendEmptyMessageDelayed(MSG_WHAT_CHECK_PERMISSION, 10);
        } else if (requestCode == REQUEST_CODE_SETTING_BY_USER_SELF) {
            //授权结果，再次检测下
            splashHandler.sendEmptyMessageDelayed(MSG_WHAT_CHECK_PERMISSION, 10);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        splashHandler.sendEmptyMessageDelayed(MSG_WHAT_CHECK_PERMISSION, 10);
    }
}
