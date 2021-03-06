# AvoidOnResultHelper
避免 onActivityResult 和 onRequestPermissionsResult 烦恼requestCode的问题，帮助快速开发~~

## 原理讲解

[原理讲解，点我！](https://blog.ruoyun.vip/post/%E5%A4%A7%E8%83%B8%E5%BC%9F-requestcode%E8%BF%98%E5%9C%A8%E5%9B%B0%E6%89%B0%E4%BD%A0%E5%98%9B/)

## 使用方法
在子项目中的 build.gradle 文件中添加

```java
dependencies {
    implementation 'vip.ruoyun.helper:avoid-onresult-helper:1.0.4'
}
```

## 开启 Activity

```java
Intent intent = new Intent();
AvoidOnResultHelper.startActivityForResult(this, intent, new AvoidOnResultHelper.ActivityCallback() {
    @Override
    public void onActivityResult(int resultCode, Intent data) {
        //接收数据
        //Log.d("MainActivity", "resultCode:" + resultCode);
        //Log.d("MainActivity", "Intent data:" + data.getStringExtra("text"));
    }
});
```

## 关闭 Activity，并回传值

在新的Activity中，通过下面的方法来快速关闭界面并传回值。
```java
AvoidOnResultHelper.finishWithResult(activity, Activity.RESULT_OK, intent);
AvoidOnResultHelper.finishWithResult(activity, Activity.RESULT_OK, bundle);
//相当于
//Intent intent = new Intent();
//intent.putExtra("text", "返回数据");
//setResult(Activity.RESULT_OK, intent);
//finish();
```

## 请求权限

```java
String[] permissions = {};
AvoidOnResultHelper.requestPermissions(this, permissions, new AvoidOnResultHelper.PermissionsCallBack() {
    @Override
    public void onRequestPermissionsResult(@NonNull String[] permissions, @NonNull int[] grantResults) {

    }
});
```

## 设置 RequestCodeRange 范围
如果在使用的过程中,发现 RequestCode 和原有的值发生冲突,那么可以通过下面的方法,进行设置
默认值为:

- start:65000
- end:65535

```java
AvoidOnResultHelper.setRequestCodeRange(65000, 65535);
```

## 生命周期
监听当前 activity 的生命周期.
```java
public interface LifecycleListener {

    void onStart();//开始

    void onStop();//结束

    void onDestroy();//销毁
}
```
LifecycleListenerWrapper 是此接口的空实现.

使用
```java
//添加监听事件
LifecycleListener lifecycleListener = new LifecycleListener.LifecycleListenerWrapper() {
    @Override
    public void onStart() {
    }
};
//添加监听事件
AvoidOnResultHelper.addLifecycleListener(this, lifecycleListener);

//粘性通知,当第一次添加的时候,就执行相应的回调方法
AvoidOnResultHelper.addLifecycleListener(this, lifecycleListener, true);
```

可选操作
```java
//移除监听事件,默认可以不用移除lifecycleListener,除非在当前界面存在时候,想要不让某些类监听,那么可以手动进行移除(removeLifecycleListener)
AvoidOnResultHelper.removeLifecycleListener(this, lifecycleListener);
```

## 原理

### 问题

当在一个界面中打开另一个界面的时候，如果想要新界面传回值，那么就需要执行下面的方法
```java
//定义 requestCode 
startActivityForResult(Intent intent, int requestCode);
```
然后在要接受数据的界面添加如下方法
```java
@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    //判断 requestCode
}
```

比如打开相册，要写如下的代码。
```java
private final static int REQUEST_CODE = 100;

private void choosePhoto(){
    //省略权限检测和申请
    ...
    Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
    // 如果限制上传到服务器的图片类型时可以直接写如："image/jpeg 、 image/png等的类型" 所有类型则写 "image/*"
    intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/jpeg");
    startActivityForResult(intentToPickPic, REQUEST_CODE);
}

//当拍摄照片完成时会回调到onActivityResult 在这里处理照片的裁剪
@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode == RESULT_OK) {
        switch (requestCode) {
            case REQUEST_CODE: {
               //处理图片逻辑
               ...
               break;
            }
        }
    }
    super.onActivityResult(requestCode, resultCode, data);
}
```

所以，我们想要做这些操作的时候，要写至少 18 行代码 和定义一个 code 码。如果快速开的话，这些操作还挺烦的。
那么我们应该怎么来进行简单呢？

### 解决方法

当阅读 glide 源码的时候，发现它可以监听声明周期，让图片随着界面的声明周期进行下载和展示，那么它是如何来解决这个问题的呢？

它是在 调用 with 的时候，传入的上下文，来监听界面的事件。
```java
Glide.with(this).load("http://goo.gl/gEgYUd").into(imageView);
```

### 分析 FragmentActivity 源码
```java
final FragmentManagerImpl mFragments = new FragmentManagerImpl();

protected void onCreate(@Nullable Bundle savedInstanceState) {
    this.mFragments.attachHost((Fragment)null);
    super.onCreate(savedInstanceState);
    ...//省略不必要的代码
    
    this.mFragments.dispatchCreate();
}

protected void onStart() {
    super.onStart();
    ...//省略不必要的代码

    this.mFragments.noteStateNotSaved();
    this.mFragments.execPendingActions();
    this.mFragments.dispatchStart();
}

protected void onStop() {
    super.onStop();
    ...//省略不必要的代码
    this.mFragments.dispatchStop();
}


protected void onPause() {
    super.onPause();
    ...//省略不必要的代码
    
    this.mFragments.dispatchPause();
}

protected void onDestroy() {
    super.onDestroy();
    ...//省略不必要的代码

    this.mFragments.dispatchDestroy();
}

public void onLowMemory() {
    super.onLowMemory();
    this.mFragments.dispatchLowMemory();
}

protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    ...//省略不必要的代码
    Fragment targetFragment = mFragments.findFragmentByWho(who);
    targetFragment.onActivityResult(requestCode & 0xffff, resultCode, data);
    ...//省略不必要的代码
}

public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
        @NonNull int[] grantResults) {
        ...//省略不必要的代码
        final List<Fragment> activeFragments =
                mFragments.getActiveFragments(new ArrayList<Fragment>(activeFragmentsCount));
        Fragment frag = activeFragments.get(index);
        if (frag == null) {
            Log.w(TAG, "Activity result no fragment exists for index: 0x"
                    + Integer.toHexString(requestCode));
        } else {
            frag.onRequestPermissionsResult(requestCode&0xff, permissions, grantResults);
        }
        ...//省略不必要的代码
    }
}
```

我们在来看  FragmentManagerImpl 的源码，就知道 FragmentActivity 的声明周期方法在执行的时候，都会执行包含的 fragment 的声明周期方法。
```java
public void dispatchCreate() {
    mStateSaved = false;
    moveToState(Fragment.CREATED, false);
}

public void dispatchActivityCreated() {
    mStateSaved = false;
    moveToState(Fragment.ACTIVITY_CREATED, false);
}

public void dispatchStart() {
    mStateSaved = false;
    moveToState(Fragment.STARTED, false);
}

void moveToState(int newState, boolean always) {
    moveToState(newState, 0, 0, always);
}

void moveToState(int newState, int transit, int transitStyle, boolean always) {
    ...//省略不必要的代码
    if (mActive != null) {
        boolean loadersRunning = false;
        for (int i=0; i<mActive.size(); i++) {
            Fragment f = mActive.get(i);
            if (f != null) {
                moveToState(f, newState, transit, transitStyle, false);// 执行声明周期方法
                ...//省略不必要的代码
            }
        }
        ...//省略不必要的代码
    }
}

void moveToState(Fragment f, int newState, int transit, int transitionStyle,
        boolean keepActive) {
        ...//省略不必要的代码
        switch (f.mState) {
            case Fragment.INITIALIZING:
                ...//省略不必要的代码
                f.onAttach(mActivity);
                ...//省略不必要的代码
                if (f.mFromLayout) {
                    ...//省略不必要的代码
                    if (f.mView != null) {
                        ...//省略不必要的代码
                        f.onViewCreated(f.mView, f.mSavedFragmentState);
                    } else {
                        f.mInnerView = null;
                    }
                }
            case Fragment.CREATED:
                    ...//省略不必要的代码
                    f.onViewCreated(f.mView, f.mSavedFragmentState);
                    ...//省略不必要的代码
            case Fragment.ACTIVITY_CREATED:
            case Fragment.STOPPED:
                ...//省略不必要的代码
                f.performStart();
            case Fragment.STARTED:
                ...//省略不必要的代码
                f.performResume();
        }
    } else if (f.mState > newState) {
        switch (f.mState) {
            case Fragment.RESUMED:
                ...//省略不必要的代码
                    f.performPause();
            case Fragment.STARTED:
                    f.performStop();
            case Fragment.STOPPED:
                    f.performReallyStop();
            case Fragment.ACTIVITY_CREATED:
                    f.performDestroyView();
            case Fragment.CREATED:
                ...//省略不必要的代码
        }
    }
    f.mState = newState;
}
```

这里，我们就可以 创建一个 没有界面的 fragment 添加到 activity 中，就可以完美监听界面的声明周期 和回调方法。到此原理讲解结束。

### 知识拓展

比如我们现在 很多第三方的库，glide  、Android Architecture Component 等等这些库都是使用这些原理。








