package vip.sirius.web.view;

import android.content.Intent;
import android.text.TextUtils;

import com.yanzhenjie.permission.runtime.Permission;

import vip.sirius.web.R;


public class SplashActivity extends SimpleSplashActivity {

    @Override
    public int getLayoutResourceId() {
        return R.layout.activity_splash;
    }

    @Override
    protected String[] getRequestPermissions() {
        return new String[]{
                Permission.READ_EXTERNAL_STORAGE,
                Permission.WRITE_EXTERNAL_STORAGE,
                Permission.ACCESS_FINE_LOCATION,
                Permission.READ_PHONE_STATE
        };
    }

    @Override
    protected void onPermissionInitSuccess() {
        checkInitByService();
    }

    @Override
    protected void onPermissionInitFail(String errMsg) {
        if (!TextUtils.isEmpty(errMsg)) {
            showToast(errMsg);
        }
        finish();
    }

    private void checkInitByService() {
        onMyselfInitSuccess();
    }


    @Override
    protected void startMainActivity() {
        goToMainActivity();
    }

    private void goToMainActivity() {
        Intent intent = new Intent(thisActivity, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
