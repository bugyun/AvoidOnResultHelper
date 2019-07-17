package vip.ruoyun.helper.avoid;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;

/**
 * Created by ruoyun on 2019-07-16.
 * Author:若云
 * Mail:zyhdvlp@gmail.com
 * Depiction:
 */
public class AvoidOnResultFragment extends Fragment {

    private static int mRequestCodeCounter = 66;
    private SparseArray<AvoidOnResultHelper.ActivityCallback> mActivityCallbacks = new SparseArray<>();
    private SparseArray<AvoidOnResultHelper.PermissionsCallBack> mPermissionsCallbacks = new SparseArray<>();

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
        mRequestCodeCounter++;
        mActivityCallbacks.append(mRequestCodeCounter, activityCallback);
        startActivityForResult(intent, mRequestCodeCounter, options);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        AvoidOnResultHelper.ActivityCallback activityCallback = mActivityCallbacks.get(requestCode);
        if (activityCallback != null) {
            activityCallback.onActivityResult(resultCode, data);
        }
    }

    /**
     * 请求权限
     *
     * @param permissions
     * @param permissionsCallBack
     */
    public void requestPermissions(@NonNull String[] permissions, AvoidOnResultHelper.PermissionsCallBack permissionsCallBack) {
        mRequestCodeCounter++;
        mPermissionsCallbacks.append(mRequestCodeCounter, permissionsCallBack);
        requestPermissions(permissions, mRequestCodeCounter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        AvoidOnResultHelper.PermissionsCallBack permissionsCallBack = mPermissionsCallbacks.get(requestCode);
        if (permissionsCallBack != null) {
            permissionsCallBack.onRequestPermissionsResult(permissions, grantResults);
        }
    }

    @Override
    public void onDestroy() {
        mActivityCallbacks = null;
        mPermissionsCallbacks = null;
        super.onDestroy();
    }
}