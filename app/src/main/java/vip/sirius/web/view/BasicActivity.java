package vip.sirius.web.view;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import vip.sirius.web.R;


/**
 * Created by Sirius
 * Date: 2017/5/8
 * Time:17:15
 *
 * @author sirius
 */
public abstract class BasicActivity extends AppCompatActivity {

    protected String TAG = "BaseActivity";

    {
        TAG = getClass().getSimpleName();
    }

    protected Activity thisActivity;

    protected ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        thisActivity = this;

        pd = new ProgressDialog(thisActivity);
        pd.setCancelable(false);
    }

    /**
     * 加载提示框
     */
    public void showProgressDialog(int resId) {
        showProgressDialog(getString(resId));
    }

    public void showProgressDialogDirect(String msg) {
        pd.setMessage(msg);
        try {
            pd.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showProgressDialogDirect(int resId) {
        pd.setMessage(getString(resId));
        try {
            pd.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showProgressDialog(final String message) {
        runOnUiThread(() -> {
            pd.setMessage(message);
            try {
                pd.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    public void hideProgressDialogDirect() {
        pd.hide();
    }

    public void dismissProgressDialogDirect() {
        pd.dismiss();
    }

    public void hideProgressDialog() {
        runOnUiThread(() -> {
            if (pd.isShowing()) {
                pd.hide();
            }
        });
    }

    public void showToast(final int resId) {
        showToast(getResources().getString(resId));
    }

    public void showToast(final int resId, Object... formatArgs) {
        Toast.makeText(BasicActivity.this, getString(resId, formatArgs), Toast.LENGTH_SHORT).show();
    }

    public void showToast(final String string) {
        runOnUiThread(() -> Toast.makeText(BasicActivity.this, string, Toast.LENGTH_SHORT).show());
    }

    public void showLongToast(final int string) {
        runOnUiThread(() -> Toast.makeText(BasicActivity.this, string, Toast.LENGTH_LONG).show());
    }

    public void showLongToast(final String string) {
        runOnUiThread(() -> Toast.makeText(BasicActivity.this, string, Toast.LENGTH_LONG).show());
    }

    public interface IAlertButtonClick {
        void onClickPositiveButton(DialogInterface dialog);

        void onClickNegativeButton(DialogInterface dialog);
    }

    public void showNormalAlert(int title, String message, int positive, int negative, final IAlertButtonClick alertButtonClick) {
        try {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setCancelable(false);
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setNegativeButton(negative, (dialog, which) -> alertButtonClick.onClickNegativeButton(dialog));
            alertDialog.setPositiveButton(positive, (dialog, which) -> alertButtonClick.onClickPositiveButton(dialog));
            alertDialog.create().show();
        } catch (Exception e) {
            //启动提示框失败
            showToast(message);
        }
    }

    public void showNormalAlert(int title, int message, int positive, int negative, final IAlertButtonClick alertButtonClick) {
        try {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setCancelable(false);
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setNegativeButton(negative, (dialog, which) -> alertButtonClick.onClickNegativeButton(dialog));
            alertDialog.setPositiveButton(positive, (dialog, which) -> alertButtonClick.onClickPositiveButton(dialog));
            alertDialog.create().show();
        } catch (Exception e) {
            //启动提示框失败
            showToast(message);
        }
    }

    public void showSimpleAlert(int message) {
        showSimpleAlert(message, null);
    }

    public void showSimpleAlert(String message) {
        showSimpleAlert(message, null);
    }

    public void showSimpleAlert(int message, DialogInterface.OnClickListener listener) {
        showSimpleAlert(message, getString(R.string.confirm), listener);
    }

    public void showSimpleAlert(String message, DialogInterface.OnClickListener listener) {
        showSimpleAlert(message, getString(R.string.confirm), listener);
    }

    public void showSimpleAlert(int message, String posBtn, DialogInterface.OnClickListener listener) {
        showSimpleAlert(getString(R.string.tip), getString(message), posBtn, listener);
    }

    public void showSimpleAlert(String message, String posBtn, DialogInterface.OnClickListener listener) {
        showSimpleAlert(getString(R.string.tip), message, posBtn, listener);
    }

    public void showSimpleAlert(String title, String message, String posBtn, DialogInterface.OnClickListener listener) {
        try {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setCancelable(false);
            alertDialog.setTitle(title);
            alertDialog.setMessage(message);
            alertDialog.setPositiveButton(posBtn, listener);
            alertDialog.create().show();
        } catch (Exception e) {
            showToast(message);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pd.dismiss();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


    protected void requestPermission(final String permission, int title, int msg, int positiveButtonText, int negativeButtonText, final int requestCode) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle(title);
            builder.setMessage(msg);
            builder.setPositiveButton(positiveButtonText, (dialogInterface, i) -> ActivityCompat.requestPermissions(thisActivity, new String[]{permission}, requestCode));
            builder.setNegativeButton(negativeButtonText, null);
            builder.show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{permission}, requestCode);
        }
    }

    protected void startActivity(Class cls) {
        startActivity(new Intent(thisActivity, cls));
    }

    protected void startActivityForResult(Class cls, int requestCode) {
        startActivityForResult(new Intent(thisActivity, cls), requestCode);
    }

    protected void startActivityForResult(Class cls, Bundle data, int requestCode) {
        Intent intent = new Intent(thisActivity, cls);
        intent.putExtras(data);
        startActivityForResult(intent, requestCode);
    }

    protected void setStatusBarColor() {
        setStatusBarColor(Color.TRANSPARENT);
    }

    protected void setStatusBarColor(@ColorInt int color) {
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | 0x00002000);
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(color);
        }
    }
}
