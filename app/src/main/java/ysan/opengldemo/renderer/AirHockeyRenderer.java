package ysan.opengldemo.renderer;

import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by YSAN on 2018/6/23
 */
public class AirHockeyRenderer implements GLSurfaceView.Renderer {

    private static final int BYTES_PER_FLOAT = 4;
    private final FloatBuffer vertexData;

    private float[] tableVerticesWithTriangles = {
            // Triangle 1
            0.0f, 0.0f,
            9.0f, 14.0f,
            0f, 14.0f,

            // Triangle 2
            0.0f, 0.0f,
            9.0f, 0.0f,
            9.0f, 14.0f,

            // Line 1
            0.0f, 7.0f,
            9.0f, 7.0f,

            // Mallets
            4.5f, 2.0f,
            4.5f, 12.0f
    };

    public AirHockeyRenderer() {
        this.vertexData = ByteBuffer
                .allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT)    // 该方法分配了一块本地内存，这块内存不会被垃圾回收器管理，传入分配多少字节的内存块
                .order(ByteOrder.nativeOrder())     // 本地字节序，一个平台要使用同样的排序
                .asFloatBuffer();   // 得到一个反映底层字节的 FloatBuffer 类实例

        // 把数据从 Dalvik 的内存复制到本地内存
        vertexData.put(tableVerticesWithTriangles);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {

    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {

    }

    @Override
    public void onDrawFrame(GL10 gl) {

    }
}
