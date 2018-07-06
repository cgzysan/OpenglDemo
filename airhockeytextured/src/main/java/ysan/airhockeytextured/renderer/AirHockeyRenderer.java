package ysan.airhockeytextured.renderer;

import android.content.Context;
import android.opengl.GLES20;
import android.opengl.GLSurfaceView;
import android.opengl.Matrix;
import android.util.Log;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import ysan.airhockeytextured.program.ColorShaderProgram;
import ysan.airhockeytextured.program.TextureShaderProgram;
import ysan.airhockeytextured.shape.Mallet;
import ysan.airhockeytextured.shape.Table;
import ysan.util.TextureHelper;

/**
 * Created by YSAN on 2018/6/28
 */
public class AirHockeyRenderer implements GLSurfaceView.Renderer {
    private final Context mContext;

    private final float[] projectionMatrix = new float[16];
    private final float[] modelMatrix = new float[16];

    private Table mTable;
    private Mallet mMallet;

    private TextureShaderProgram mTextureShaderProgram;
    private ColorShaderProgram mColorShaderProgram;

    private int texture;


    @Override
    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
        Log.i("ysan", "onSurfaceCreated");
        GLES20.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);

        mTable = new Table();
        mMallet = new Mallet();

        mTextureShaderProgram = new TextureShaderProgram(mContext);
        mColorShaderProgram = new ColorShaderProgram(mContext);

        texture = TextureHelper.loadTexture(mContext, )
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        Log.i("ysan", "onSurfaceChanged width = " + width + ", height = " + height);
        // 设置视口的尺寸，告诉 OpenGL 可以用来渲染的 surface 的大小
        GLES20.glViewport(0, 0, width, height);
        Matrix.perspectiveM(projectionMatrix, 0, 45, (float) width / (float) height, 1f, 10f);
        //        MatrixHelper.perspectiveM(projectionMatrix, 45, (float) width / (float) height, 1f, 10f);
        Matrix.setIdentityM(modelMatrix, 0);
        Matrix.translateM(modelMatrix, 0, 0f, 0f, -3f);
        Matrix.rotateM(modelMatrix, 0, -60f, 1f, 0f, 0f);

        final float[] temp = new float[16];
        Matrix.multiplyMM(temp, 0, projectionMatrix, 0, modelMatrix, 0);
        System.arraycopy(temp, 0, projectionMatrix, 0, temp.length);
    }

    @Override
    public void onDrawFrame(GL10 gl) {
        Log.i("ysan", "onDrawFrame");
    }
}
