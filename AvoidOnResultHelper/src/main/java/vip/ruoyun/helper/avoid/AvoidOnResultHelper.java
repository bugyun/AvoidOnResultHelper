package vip.ruoyun.helper.avoid;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.util.Log;

/**
 * Created by ruoyun on 2019-07-16.
 * Author:若云
 * Mail:zyhdvlp@gmail.com
 * Depiction:
 */
public class AvoidOnResultHelper {
    private static final String TAG = "RYAvoidOnResultHelper";


    private AvoidOnResultHelper() {
    }

    /**
     * 设置 requestCode 的使用范围
     *
     * @param start 默认 65000
     * @param end   默认 65535
     */
    public static void setRequestCodeRange(@IntRange(from = 0, to = 65535) int start, @IntRange(from = 0, to = 65535) int end) {
        if (start >= end) {
            Log.e(TAG, "start(" + start + ") must less than end(" + end + ") , Now use the default values !");
            return;
        }
        AvoidOnResultFragment.setRequestCodeRange(start, end);
    }

    private static AvoidOnResultFragment getAvoidOnResultFragment(FragmentActivity activity) {
        FragmentManager supportFragmentManager = activity.getSupportFragmentManager();
        AvoidOnResultFragment avoidOnResultFragment = (AvoidOnResultFragment) supportFragmentManager.findFragmentByTag(TAG);
        if (avoidOnResultFragment == null) {
            avoidOnResultFragment = new AvoidOnResultFragment();
            supportFragmentManager.beginTransaction()
                    .add(avoidOnResultFragment, TAG)
                    .commitNowAllowingStateLoss();
        }
        return avoidOnResultFragment;
    }

    /**
     * 打开 activity
     *
     * @param activity
     * @param intent
     * @param callback
     */
    public static void startActivityForResult(@NonNull FragmentActivity activity, @NonNull Intent intent, @NonNull ActivityCallback callback) {
        startActivityForResult(activity, intent, null, callback);
    }

    public static void startActivityForResult(@NonNull FragmentActivity activity, @NonNull Intent intent, Bundle options, @NonNull ActivityCallback callback) {
        AvoidOnResultFragment avoidOnResultFragment = getAvoidOnResultFragment(activity);
        avoidOnResultFragment.startActivityForResult(intent, options, callback);
    }

    public interface ActivityCallback {
        void onActivityResult(int resultCode, Intent data);
    }

    /**
     * 请求权限
     *
     * @param activity
     * @param permissions
     * @param permissionsCallBack
     */
    public static void requestPermissions(@NonNull FragmentActivity activity, @NonNull String[] permissions, @NonNull PermissionsCallBack permissionsCallBack) {
        AvoidOnResultFragment avoidOnResultFragment = getAvoidOnResultFragment(activity);
        avoidOnResultFragment.requestPermissions(permissions, permissionsCallBack);
    }

    public interface PermissionsCallBack {
        void onRequestPermissionsResult(@NonNull String[] permissions, @NonNull int[] grantResults);
    }


    /**
     * 生命周期,非粘性,注册的时候不调用回调函数
     */
    public static void addLifecycleListener(@NonNull FragmentActivity activity, @NonNull LifecycleListener listener) {
        addLifecycleListener(activity, listener, false);
    }

    /**
     * 粘性,注册的时候调用回调函数
     *
     * @param activity
     * @param listener
     */
    public static void addLifecycleListener(@NonNull FragmentActivity activity, @NonNull LifecycleListener listener, boolean isSticky) {
        AvoidOnResultFragment avoidOnResultFragment = getAvoidOnResultFragment(activity);
        avoidOnResultFragment.addLifecycleListener(listener, isSticky);
    }


    public static void removeLifecycleListener(@NonNull FragmentActivity activity, @NonNull LifecycleListener listener) {
        AvoidOnResultFragment avoidOnResultFragment = getAvoidOnResultFragment(activity);
        avoidOnResultFragment.removeLifecycleListener(listener);
    }
}
