package ysan.opengldemo.widget;

import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by YSAN on 2018/6/2
 */
public class GLRender implements GLSurfaceView.Renderer {

    float rotateTri, rotateQuad;
    int one = 0x10000;


    //三角形的一个顶点
    private int[] trigger = {
            0, one, 0,     //上顶点
            -one, -one, 0,    //左顶点
            one, -one, 0    //右下点
    };

    //正方形的四个顶点
    private int[] quate = {
            one, one, 0,
            -one, -one, 0,
            one, -one, 0,
            -one, -one, 0
    };


    private int[] color = {
            one, 0, 0, one,
            0, one, 0, one,
            0, 0, one, one
    };


    @Override
    public void onDrawFrame(GL10 gl) {
        // TODO Auto-generated method stub
        // 将三角形的三个定点的颜色数组包装成IntBuffer
        IntBuffer.wrap(trigger);
        ByteBuffer triggerBB = ByteBuffer.allocateDirect(trigger.length * 4);
        triggerBB.order(ByteOrder.nativeOrder());
        IntBuffer triggerBuffer = triggerBB.asIntBuffer();
        triggerBuffer.put(trigger);
        triggerBuffer.position(0);

        IntBuffer.wrap(quate);
        ByteBuffer quateBB = ByteBuffer.allocateDirect(quate.length * 4);
        quateBB.order(ByteOrder.nativeOrder());
        IntBuffer quateBuffer = quateBB.asIntBuffer();
        quateBuffer.put(quate);
        quateBuffer.position(0);

        IntBuffer.wrap(color);
        ByteBuffer colorBB = ByteBuffer.allocateDirect(color.length * 4);
        colorBB.order(ByteOrder.nativeOrder());
        IntBuffer colorBuffer = colorBB.asIntBuffer();
        colorBuffer.put(color);
        colorBuffer.position(0);

        // 清除屏幕和深度缓存
        gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        // 重置当前的模型观察矩阵
        gl.glLoadIdentity();

        // 左移 1.5 单位，并移入屏幕 6.0
        gl.glTranslatef(-1.5f, 0.0f, -6.0f);
        //设置旋转
        gl.glRotatef(rotateTri, 0.0f, 1.0f, 0.0f);

        //设置定点数组
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        //设置颜色数组
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        gl.glColorPointer(4, GL10.GL_FIXED, 0, colorBuffer);
        // 设置三角形顶点
        gl.glVertexPointer(3, GL10.GL_FIXED, 0, triggerBuffer);
        //绘制三角形
        gl.glDrawArrays(GL10.GL_TRIANGLES, 0, 3);

        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);

        //绘制三角形结束
        gl.glFinish();

        /***********************/
        /* 渲染正方形 */
        // 重置当前的模型观察矩阵
        gl.glLoadIdentity();

        // 左移 1.5 单位，并移入屏幕 6.0
        gl.glTranslatef(1.5f, 0.0f, -6.0f);

        // 设置当前色为蓝色
        gl.glColor4f(0.5f, 0.5f, 1.0f, 1.0f);
        //设置旋转
        gl.glRotatef(rotateQuad, 1.0f, 0.0f, 0.0f);

        //设置和绘制正方形
        gl.glVertexPointer(3, GL10.GL_FIXED, 0, quateBuffer);
        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

        //绘制正方形结束
        gl.glFinish();

        //取消顶点数组
        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);

        //改变旋转的角度
        rotateTri += 0.5f;
        rotateQuad -= 0.5f;
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // TODO Auto-generated method stub

        float ratio = (float) width / height;
        //设置OpenGL场景的大小
        gl.glViewport(0, 0, width, height);
        //设置投影矩阵
        gl.glMatrixMode(GL10.GL_PROJECTION);
        //重置投影矩阵
        gl.glLoadIdentity();
        // 设置视口的大小
        gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
        // 选择模型观察矩阵
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        // 重置模型观察矩阵
        gl.glLoadIdentity();

    }

    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        // TODO Auto-generated method stub
        // 启用阴影平滑
        gl.glShadeModel(GL10.GL_SMOOTH);

        // 黑色背景
        gl.glClearColor(0, 0, 0, 0);

        // 设置深度缓存
        gl.glClearDepthf(1.0f);
        // 启用深度测试
        gl.glEnable(GL10.GL_DEPTH_TEST);
        // 所作深度测试的类型
        gl.glDepthFunc(GL10.GL_LEQUAL);

        // 告诉系统对透视进行修正
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
    }
}
