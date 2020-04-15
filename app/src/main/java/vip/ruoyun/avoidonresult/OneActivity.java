package vip.ruoyun.avoidonresult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import vip.ruoyun.helper.avoid.AvoidOnResultHelper;
import vip.ruoyun.helper.avoid.LifecycleListener;

public class OneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one);

        Button mButton = findViewById(R.id.mButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("text", "返回数据");
//                setResult(Activity.RESULT_OK, intent);
//                finish();
                AvoidOnResultHelper.finishWithResult(OneActivity.this, Activity.RESULT_OK, intent);
//                AvoidOnResultHelper.finishWithResult(OneActivity.this, Activity.RESULT_OK, intent.getBundleExtra(""));
            }
        });


        LifecycleListener lifecycleListener = new LifecycleListener() {


            @Override
            public void onStart() {
                Log.e("OneActivity", "onStart");

            }


            @Override
            public void onStop() {
                Log.e("OneActivity", "onStop");

            }

            @Override
            public void onDestroy() {
                Log.e("OneActivity", "onDestroy");
            }
        };


//        AvoidOnResultHelper.addLifecycleListener(this, lifecycleListener);
        AvoidOnResultHelper.addLifecycleListener(this, lifecycleListener, true);
//        AvoidOnResultHelper.removeLifecycleListener(this, lifecycleListener);
    }


}
