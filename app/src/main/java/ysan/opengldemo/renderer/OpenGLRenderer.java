package ysan.opengldemo.renderer;

import android.opengl.GLSurfaceView;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by YSAN on 2018/6/20
 */
public class OpenGLRenderer implements GLSurfaceView.Renderer {

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.i("ysan", "onSurfaceCreated");
        gl.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.i("ysan", "onSurfaceChanged width = " + width + ", height = " + height);
        // 设置视口的尺寸，告诉 OpenGL 可以用来渲染的 surface 的大小
        gl.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        Log.i("ysan", "onDrawFrame");
        // 清空屏幕，这会擦除屏幕上的所有颜色，并用之前glClearColor()调用定义的颜色填充整个屏幕
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
    }
}
