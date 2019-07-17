package vip.ruoyun.avoidonresult;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import vip.ruoyun.helper.avoid.AvoidOnResultHelper;


public class MainActivity extends AppCompatActivity {

    private AvoidOnResultHelper.ActivityCallback activityCallback = new AvoidOnResultHelper.ActivityCallback() {
        @Override
        public void onActivityResult(int resultCode, Intent data) {
            //新界面
            Log.d("MainActivity", "resultCode:" + resultCode);
            Log.d("MainActivity", "Intent data:" + data.getStringExtra("text"));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Button mButton = findViewById(R.id.mButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(MainActivity.this, OneActivity.class);
                AvoidOnResultHelper.startActivityForResult(MainActivity.this, intent, activityCallback);

            }
        });

        FragmentManager fragmentManager = getSupportFragmentManager();
//        Fragment oneFragment = fragmentManager.findFragmentByTag("OneFragment");
//        if (oneFragment != null) {
//
//        } else {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        OneFragment fragment = new OneFragment();
        fragmentTransaction.replace(R.id.mFrameLayout, fragment, "OneFragment");
        fragmentTransaction.commit();
//        }
    }

    private void test() {
        String[] permissions = {};
        AvoidOnResultHelper.requestPermissions(this, permissions, new AvoidOnResultHelper.PermissionsCallBack() {
            @Override
            public void onRequestPermissionsResult(@NonNull String[] permissions, @NonNull int[] grantResults) {

            }
        });
    }
}
