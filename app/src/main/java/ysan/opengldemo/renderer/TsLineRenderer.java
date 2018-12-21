package ysan.opengldemo.renderer;

import android.opengl.GLSurfaceView;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by YSAN on 2018/7/19
 */
public class TsLineRenderer implements GLSurfaceView.Renderer {

    private float[] lineArray = {
            -0.1f, 0.2f,
            0.2f, -0.2f,
            0.6f, 0.1f,
            0.3f, 0.5f,
            -0.5f, -0.2f,
            -0.8f, -0.4f,
            //            -0.1f, -0.7f,
            //            -0.05f, -0.1f
    };
    private FloatBuffer line_vertexBuffer;

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        ByteBuffer byteBuffer =
                ByteBuffer.allocateDirect(lineArray.length * Float.SIZE / 8);
        byteBuffer.order(ByteOrder.nativeOrder());
        line_vertexBuffer = byteBuffer.asFloatBuffer();
        line_vertexBuffer.put(lineArray);
        line_vertexBuffer.position(0);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        gl.glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        // 清除屏幕和深度缓存
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        // 重置当前的模型观察矩阵
        gl.glLoadIdentity();
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, line_vertexBuffer);
        gl.glColor4f(0f, 1.0f, 0f, 0.9f);
        Log.i("ysan", "total =  " + line_vertexBuffer.limit());
        gl.glDrawArrays(GL10.GL_TRIANGLE_FAN, 0, line_vertexBuffer.limit() / 2);
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
    }
}
