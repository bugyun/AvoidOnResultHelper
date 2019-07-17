package vip.ruoyun.avoidonresult;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import vip.ruoyun.helper.avoid.AvoidOnResultHelper;


/**
 * A simple {@link Fragment} subclass.
 */
public class OneFragment extends Fragment {


    public OneFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_one, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button mButton = view.findViewById(R.id.mButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getActivity(), OneActivity.class);
                AvoidOnResultHelper.startActivityForResult(getActivity(), intent, new AvoidOnResultHelper.ActivityCallback() {
                    @Override
                    public void onActivityResult(int resultCode, Intent data) {
                        //新界面
                        Log.d("MainActivity", "resultCode:" + resultCode);
                        Log.d("MainActivity", "Intent data:" + data.getStringExtra("text"));
                    }
                });
            }
        });
    }

    private void test() {
        Intent intent = new Intent(getActivity(), OneActivity.class);
        AvoidOnResultHelper.startActivityForResult(getActivity(), intent, new AvoidOnResultHelper.ActivityCallback() {
            @Override
            public void onActivityResult(int resultCode, Intent data) {
                //新界面
                //val intent = Intent()
                //intent.putExtra("text",text.text.toString())
                //setResult(Activity.RESULT_OK,intent)
                //finish();
            }
        });
    }
}
