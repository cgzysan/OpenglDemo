package ysan.opengldemo.activity;

import android.annotation.SuppressLint;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Method;

import ysan.opengldemo.renderer.TsLineRenderer;

public class MainActivity extends AppCompatActivity {

    private GLSurfaceView mGLSurfaceView;

    @SuppressLint("PrivateApi")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        mGLSurfaceView = new GLSurfaceView(this);

//        final ActivityManager activityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        final ConfigurationInfo configurationInfo = activityManager.getDeviceConfigurationInfo();
//        final boolean supportEs2 = configurationInfo.reqGlEsVersion >= 0x20000;
//        Log.i("ysan", "support = " + supportEs2);
//        mGLSurfaceView.setEGLContextClientVersion(2);
        mGLSurfaceView.setRenderer(new TsLineRenderer());
        setContentView(mGLSurfaceView);
//        jsonTest1();
//        jsonTest2();
    }

    private void jsonTest2() {
        String res = "{\"map\":[{\"x\":11,\"y\":22,\"w\":33,\"number\":123,\"name\":\"办公室\"}]}";
        Log.i("ysan", "res = " + res);

        try {
            JSONObject obj = new JSONObject(res);
            JSONArray map = obj.getJSONArray("map");
            for (int i = 0; i < map.length(); i++) {
                JSONObject bj = map.getJSONObject(i);
                int x = bj.getInt("x");
                int y = bj.getInt("y");
                int w = bj.getInt("w");
                String name = bj.getString("name");
                int num = bj.getInt("number");

                Log.i("ysan", "x = " + x + "\n" + "y = " + y + "\n" + "w = " + w + "\n" + "name = " + name + "\n" +  "number = " + num);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void jsonTest1() {
        try {
            JSONObject object1 = new JSONObject();
            object1.put("x", 11);
            object1.put("y", 22);
            object1.put("w", 33);
            object1.put("number", 123);
            object1.put("name", "办公室");

            JSONArray array1 = new JSONArray();
            JSONObject object = new JSONObject();

            array1.put(object1);
            object.put("map", array1);
            Log.i("ysan", "包装的最后数据 = " + object.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        // Ideally a game should implement
        // onResume() and onPause()
        // to take appropriate action when the
        //activity looses focus
        super.onResume();
        mGLSurfaceView.onResume();
    }
    @Override
    protected void onPause() {
        // Ideally a game should implement onResume()
        //and onPause()
        // to take appropriate action when the
        //activity looses focus
        super.onPause();
        mGLSurfaceView.onPause();
    }

    private String getSystemProp(String key, String defaultValue) {
        try {
            Class<?> aClass = Class.forName("android.os.SystemProperties");
            Method get = aClass.getMethod("get", String.class, String.class);
            String result = (String) get.invoke(null, key, defaultValue);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return defaultValue;
        }
    }
}
