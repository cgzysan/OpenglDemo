package ysan.opengldemo.renderer;

import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by YSAN on 2018/8/30
 */
public class TsFanRenderer implements GLSurfaceView.Renderer {

    private float[] lineArray = {

            -0.1f, 0.2f,
            0.2f, -0.2f,
            0.6f, 0.1f,
            0.3f, 0.5f
    };

    private float[] fanArray = {

    };

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }
}
