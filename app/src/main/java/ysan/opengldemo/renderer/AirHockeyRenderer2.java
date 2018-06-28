package ysan.opengldemo.renderer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import ysan.opengldemo.R;
import ysan.util.ShaderHelper;
import ysan.util.TextResourceReader;

/**
 * Created by YSAN on 2018/6/23
 */
public class AirHockeyRenderer2 implements GLSurfaceView.Renderer {

    //TODO 加更多的三角形，使边缘不要太明显。
    //TODO 调整正交矩阵，让桌子看起来更大或者更小，并在屏幕上平移 >>> 需要调整传递给 orthoM() 的左，右，顶和底的值。


    private final Context mContext;
    private static final int BYTES_PER_FLOAT = 4;
    private static final int POSITION_COMPONENT_COUNT = 2;
    private FloatBuffer vertexData;

    private static final String U_MATRIX = "u_Matrix";
    private final float[] projectionMatrix = new float[16];
    private int uMatrixLocation;
    private static final String A_POSITION = "a_Position";
    private int aPositionLocation;
    private static final String A_COLOR = "a_Color";
    private int aColorLocation;
    private static final int COLOR_COMPONENT_COUNT = 3;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + COLOR_COMPONENT_COUNT) * BYTES_PER_FLOAT;

    private int mProgram;

    float[] tableVerticesWithTriangles = {
            // Order of coordinates: X, Y, R, G, B

            // Triangle fan
            0, 0, 1f, 1f, 1f,
            -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
            0.5f, -0.8f, 0.7f, 0.7f, 0.7f,
            0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
            -0.5f, 0.8f, 0.7f, 0.7f, 0.7f,
            -0.5f, -0.8f, 0.7f, 0.7f, 0.7f,

            // Line 1
            -0.5f, 0f, 1f, 0f, 0f,
            0.5f, 0f, 1f, 0f, 0f,

            // Mallets
            0f, -0.25f, 0f, 0f, 1f,
            0f, 0.25f, 1f, 0f, 0f
    };

    public AirHockeyRenderer2(Context context) {
        this.mContext = context;
    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.i("ysan", "onSurfaceCreated");

        vertexData = ByteBuffer
                .allocateDirect(tableVerticesWithTriangles.length * BYTES_PER_FLOAT)    // 该方法分配了一块本地内存，这块内存不会被垃圾回收器管理，传入分配多少字节的内存块
                .order(ByteOrder.nativeOrder())     // 本地字节序，一个平台要使用同样的排序
                .asFloatBuffer();   // 得到一个反映底层字节的 FloatBuffer 类实例

        // 把数据从 Dalvik 的内存复制到本地内存
        vertexData.put(tableVerticesWithTriangles);

        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        // 读取着色器代码
        String vertexShaderSource = TextResourceReader.readTextFileFromResource(mContext, R.raw.simple_vertex_shader2);
        String fragmentShaderSource = TextResourceReader.readTextFileFromResource(mContext, R.raw.simple_fragment_shader2);
        // 编译源代码
        int vertexShader = ShaderHelper.compileVertexShader(vertexShaderSource);
        int fragmentShader = ShaderHelper.compileFragmentShader(fragmentShaderSource);
        // 存储链接程序的ID
        mProgram = ShaderHelper.linkProgram(vertexShader, fragmentShader);
//         使用创建的 OpenGL 程序
        GLES20.glUseProgram(mProgram);
        // 获取属性位置
        aPositionLocation = GLES20.glGetAttribLocation(mProgram, A_POSITION);
        // 保证在开头
        vertexData.position(0);
        // 告诉OpenGL可以在缓冲区 vertexData 中找到顶点数据
        GLES20.glVertexAttribPointer(aPositionLocation, POSITION_COMPONENT_COUNT, GLES20.GL_FLOAT, false, STRIDE, vertexData);
        // 告诉OpenGL可以在缓冲区 vertexData 中找到颜色数据
        aColorLocation = GLES20.glGetAttribLocation(mProgram, A_COLOR);
        vertexData.position(POSITION_COMPONENT_COUNT);
        GLES20.glVertexAttribPointer(aColorLocation, COLOR_COMPONENT_COUNT, GLES20.GL_FLOAT, false, STRIDE, vertexData);

        uMatrixLocation = GLES20.glGetUniformLocation(mProgram, U_MATRIX);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.i("ysan", "onSurfaceChanged width = " + width + ", height = " + height);
        // 设置视口的尺寸，告诉 OpenGL 可以用来渲染的 surface 的大小
        GLES20.glViewport(0, 0, width, height);

        final float aspectRatio = width > height ? ( float ) width / ( float )height : ( float ) height / ( float ) width;
        if (width > height) {
            // Landscape
            Matrix.orthoM(projectionMatrix, 0, -aspectRatio, aspectRatio, -1f, 1f, -1f, 1f);
        } else {
            // Portrait or square
            Matrix.orthoM(projectionMatrix, 0, -1f, 1f, -aspectRatio, aspectRatio, -1f, 1f);
        }
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        Log.i("ysan", "onDrawFrame");
        // 清空屏幕，这会擦除屏幕上的所有颜色，并用之前glClearColor()调用定义的颜色填充整个屏幕
        GLES20.glClear(GL10.GL_COLOR_BUFFER_BIT);
        // 将正交投影矩阵传递给着色器
        GLES20.glUniformMatrix4fv(uMatrixLocation, 1, false, projectionMatrix, 0);
        // 使能数据属性
        GLES20.glEnableVertexAttribArray(aPositionLocation);
        GLES20.glEnableVertexAttribArray(aColorLocation);
        // 绘制三角形, index 从 0 到 6
        GLES20.glDrawArrays(GLES20.GL_TRIANGLE_FAN, 0, 6);
        // 绘制直线
        GLES20.glDrawArrays(GLES20.GL_LINES, 6, 2);
        // 画第一个点
        GLES20.glDrawArrays(GLES20.GL_POINTS, 8, 1);
        // 画第二个点
        GLES20.glDrawArrays(GLES20.GL_POINTS, 9, 1);
        GLES20.glDisableVertexAttribArray(aPositionLocation);
        GLES20.glEnableVertexAttribArray(aColorLocation);
    }
}
