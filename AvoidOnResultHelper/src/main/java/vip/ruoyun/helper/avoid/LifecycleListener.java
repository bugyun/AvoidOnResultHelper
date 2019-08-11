package vip.ruoyun.helper.avoid;

/**
 * Created by ruoyun on 2019-08-11.
 * Author:若云
 * Mail:zyhdvlp@gmail.com
 * Depiction:
 */
public interface LifecycleListener {

    void onCreate();

    void onStart();

    void onResume();

    void onPause();

    void onStop();

    void onDestroy();

    class LifecycleListenerWrapper implements LifecycleListener {

        @Override
        public void onCreate() {

        }

        @Override
        public void onStart() {

        }

        @Override
        public void onResume() {

        }

        @Override
        public void onPause() {

        }

        @Override
        public void onStop() {

        }

        @Override
        public void onDestroy() {

        }
    }
}
