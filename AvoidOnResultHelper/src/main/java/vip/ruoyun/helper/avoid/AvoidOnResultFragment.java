package vip.ruoyun.helper.avoid;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import java.lang.ref.WeakReference;

/**
 * Created by ruoyun on 2019-07-16.
 * Author:若云
 * Mail:zyhdvlp@gmail.com
 * Depiction:
 */
public class AvoidOnResultFragment extends Fragment {

    private static int mActivityCallbackCode = 108;
    private static int mPermissionsCallBackCode = 208;
    private WeakReference<AvoidOnResultHelper.ActivityCallback> mActivityCallback;
    private WeakReference<AvoidOnResultHelper.PermissionsCallBack> mPermissionsCallback;

    public AvoidOnResultFragment() {
    }

    /**
     * 打开 activity
     *
     * @param intent
     * @param options
     * @param activityCallback
     */
    public void startActivityForResult(Intent intent, @Nullable Bundle options, AvoidOnResultHelper.ActivityCallback activityCallback) {
        mActivityCallback = new WeakReference<>(activityCallback);
        startActivityForResult(intent, mActivityCallbackCode, options);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == mActivityCallbackCode) {
            AvoidOnResultHelper.ActivityCallback activityCallback = mActivityCallback.get();
            if (activityCallback != null) {
                activityCallback.onActivityResult(resultCode, data);
            }
        }
    }

    /**
     * 请求权限
     *
     * @param permissions
     * @param permissionsCallBack
     */
    public void requestPermissions(@NonNull String[] permissions, AvoidOnResultHelper.PermissionsCallBack permissionsCallBack) {
        mPermissionsCallback = new WeakReference<>(permissionsCallBack);
        requestPermissions(permissions, mPermissionsCallBackCode);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == mPermissionsCallBackCode) {
            AvoidOnResultHelper.PermissionsCallBack permissionsCallBack = mPermissionsCallback.get();
            if (permissionsCallBack != null) {
                permissionsCallBack.onRequestPermissionsResult(permissions, grantResults);
            }
        }
    }

    @Override
    public void onDestroy() {
        mActivityCallback = null;
        mPermissionsCallback = null;
        super.onDestroy();
    }
}