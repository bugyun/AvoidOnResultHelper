# AvoidOnResultHelper
避免 onActivityResult 和 onRequestPermissionsResult 烦恼requestCode的问题，帮助快速开发~~


## 使用方法
在子项目中的 build.gradle 文件中添加

```java
dependencies {
    implementation 'vip.ruoyun.helper:avoid-onresult-helper:1.0.0'
}
```

## 开启新界面

```java
Intent intent = new Intent();
AvoidOnResultManager.startActivityForResult(this, intent, new AvoidOnResultManager.ActivityCallback() {
    @Override
    public void onActivityResult(int resultCode, Intent data) {
        //新界面
        //val intent = Intent()
        //intent.putExtra("text",text.text.toString())
        //setResult(Activity.RESULT_OK,intent)
        //finish();

    }
});
```

## 请求权限

```java
String[] permissions = {};
AvoidOnResultManager.requestPermissions(this, permissions, new AvoidOnResultManager.PermissionsCallBack() {
    @Override
    public void onRequestPermissionsResult(@NonNull String[] permissions, @NonNull int[] grantResults) {

    }
});
```
