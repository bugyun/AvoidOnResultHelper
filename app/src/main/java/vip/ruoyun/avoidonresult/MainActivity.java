package vip.ruoyun.avoidonresult;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import vip.ruoyun.helper.avoid.AvoidOnResultHelper;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Intent intent = new Intent();
        AvoidOnResultHelper.startActivityForResult(this, intent, new AvoidOnResultHelper.ActivityCallback() {
            @Override
            public void onActivityResult(int resultCode, Intent data) {
                //新界面
                //val intent = Intent()
                //intent.putExtra("text",text.text.toString())
                //setResult(Activity.RESULT_OK,intent)
                //finish();

            }
        });
        String[] permissions = {};
        AvoidOnResultHelper.requestPermissions(this, permissions, new AvoidOnResultHelper.PermissionsCallBack() {
            @Override
            public void onRequestPermissionsResult(@NonNull String[] permissions, @NonNull int[] grantResults) {

            }
        });
    }
}
