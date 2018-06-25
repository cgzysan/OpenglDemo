package ysan.opengldemo.renderer;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import ysan.opengldemo.R;
import ysan.opengldemo.util.ShaderHelper;
import ysan.opengldemo.util.TextResourceReader;

import static android.opengl.GLES20.*;

/**
 * Created by YSAN on 2018/6/23
 */
public class AirHockeyRenderer implements GLSurfaceView.Renderer {

    private final Context mContext;
    private static final int BYTES_PER_FLOAT = 4;
    private static final int POSITION_COMPONENT_COUNT = 2;
    private final FloatBuffer vertexData;

    private static final String U_COLOR = "u_Color";
    private int uColorLocation;
    private static final String A_POSITION = "a_Position";
    private int aPositionLocation;
    private int mProgram;

    public AirHockeyRenderer(Context context) {
        float[] tableVerticesWithTriangles = {
                // Triangle 1
                -0.5f, -0.5f,
                0.5f, 0.5f,
                -0.5f, 0.5f,

                // Triangle 2
                -0.5f, -0.5f,
                0.5f, -0.5f,
                0.5f, 0.5f,

                // Line 1
                -0.5f, 0f,
                0.5f, 0f,

                // Mallets
                0f, -0.25f,
                0f, 0.25f
        };
        this.mContext = context;
        this.vertexData = ByteBuffer
                .allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT)    // 该方法分配了一块本地内存，这块内存不会被垃圾回收器管理，传入分配多少字节的内存块
                .order(ByteOrder.nativeOrder())     // 本地字节序，一个平台要使用同样的排序
                .asFloatBuffer();   // 得到一个反映底层字节的 FloatBuffer 类实例

        // 把数据从 Dalvik 的内存复制到本地内存
        vertexData.put(tableVerticesWithTriangles);
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.i("ysan", "onSurfaceCreated");
        glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        // 读取着色器代码
        String vertexShaderSource = TextResourceReader.readTextFileFromResource(mContext, R.raw.simple_vertex_shader);
        Log.i("ysan", "shader source = " + vertexShaderSource);
        String fragmentShaderSource = TextResourceReader.readTextFileFromResource(mContext, R.raw.simple_fragment_shader);
        // 编译源代码
        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);
        // 存储链接程序的ID
        mProgram = ShaderHelper.linkProgram(vertexShader, fragmentShader);
        // 获取uniform值的位置，存入uColorLocation
        uColorLocation = glGetUniformLocation(mProgram, U_COLOR);
        // 获取属性位置
        aPositionLocation = glGetAttribLocation(mProgram, A_POSITION);
        // 保证在开头
        vertexData.position(0);
        // 告诉OpenGL可以在缓冲区 vertexData 中找到数据
        glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GL_FLOAT, false, 0, vertexData);
        // 使能数据属性
        glEnableVertexAttribArray(aPositionLocation);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.i("ysan", "onSurfaceChanged width = " + width + ", height = " + height);
        // 设置视口的尺寸，告诉 OpenGL 可以用来渲染的 surface 的大小
        glViewport(0, 0, width, height);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        Log.i("ysan", "onDrawFrame");
        // 清空屏幕，这会擦除屏幕上的所有颜色，并用之前glClearColor()调用定义的颜色填充整个屏幕
        glClear(GL10.GL_COLOR_BUFFER_BIT);
        // 更新着色器代码中的 u_Color 的值
        // 绘制三角形, index 从 0 到 6
        glUniform4f(uColorLocation, 1.0f, 1.0f, 1.0f, 1.0f);
        glDrawArrays(GL_TRIANGLES, 0, 6);
        // 绘制直线
        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_LINES, 6, 2);
        // 画第一个点
        glUniform4f(uColorLocation, 0.0f, 0.0f, 1.0f, 1.0f);
        glDrawArrays(GL_POINTS, 8, 1);
        // 画第二个点
        glUniform4f(uColorLocation, 1.0f, 0.0f, 0.0f, 1.0f);
        glDrawArrays(GL_POINTS, 9, 1);
    }
}
