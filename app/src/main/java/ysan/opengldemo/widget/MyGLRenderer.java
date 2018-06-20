package ysan.opengldemo.widget;

import android.opengl.GLES20;
import android.opengl.GLSurfaceView;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by YSAN on 2018/6/1
 */
public class MyGLRenderer implements GLSurfaceView.Renderer {

    private Triangle mTriangle;
    private Square mSquare;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // 调用一次，配置View的OpenGL ES环境
        // 设置刷新颜色缓冲区时所用的颜色
//        GLES20.glClearColor(0.0f, 0.0f, 1.0f, 1.0f);
        mTriangle = new Triangle();
        mSquare = new Square();
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // 如果View的几何形状发生变化时会被调用.
        // 决定视口区域
        GLES20.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // 每次重新绘制View时被调用
        //
         GLES20.glClear(GLES20.GL_COLOR_BUFFER_BIT);
        mTriangle.draw();
    }

    // 着色器包含了OpenGL Shading Language（GLSL）代码，它必须先被编译然后才能在OpenGL环境中使用。
    public static int loadShader(int type, String shaderCode) {
        int shader = GLES20.glCreateShader(type);

        GLES20.glShaderSource(shader, shaderCode);
        GLES20.glCompileShader(shader);

        return shader;
    }
}
