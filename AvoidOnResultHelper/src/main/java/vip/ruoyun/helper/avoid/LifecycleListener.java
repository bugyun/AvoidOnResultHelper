package vip.ruoyun.helper.avoid;

/**
 * Created by ruoyun on 2019-08-11.
 * Author:若云
 * Mail:zyhdvlp@gmail.com
 * Depiction:
 */
public interface LifecycleListener {

    /**
     * 开始
     */
    void onStart();

    /**
     * 结束
     */
    void onStop();

    /**
     * 销毁
     */
    void onDestroy();

    class LifecycleListenerWrapper implements LifecycleListener {

        @Override
        public void onStart() {

        }

        @Override
        public void onStop() {

        }

        @Override
        public void onDestroy() {

        }
    }
}
