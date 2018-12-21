package ysan.opengldemo.activity;

import android.content.pm.ActivityInfo;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import ysan.opengldemo.game.core.GameSystem;
import ysan.opengldemo.renderer.MyRender;

/**
 * Created by YSAN on 2018/12/21
 */
public class OpenglsActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        onMain();

        GLSurfaceView glSurfaceView = new GLSurfaceView(this);
        glSurfaceView.setRenderer(new MyRender());
        setContentView(glSurfaceView);
        GameSystem.context = getApplicationContext();
        GameSystem.assetManager = getAssets();
        GameSystem.Initializing();
    }

    public void onMain() {
        initialization(true, true, true);
        GameSystem.ShowFPS = true;
        GameSystem.ShowMemory = true;
    }

    public void initialization(boolean isOrientation, boolean FullScreen, boolean HideTitle) {
        if (HideTitle)
            requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (FullScreen)
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        if (isOrientation)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }
}
