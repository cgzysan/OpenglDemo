package ysan.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLES20;
import android.opengl.GLUtils;
import android.util.Log;

/**
 * Created by YSAN on 2018/6/28
 */
public class TextureHelper {

    /**
     * 加载纹理
     * @param context       上下文
     * @param resourceId    资源ID
     * @return              加载图像后的 OpenGL 纹理的 ID
     */
    public static int loadTexture(Context context, int resourceId) {
        final int[] textureObjectIds = new int[1];
        GLES20.glGenTextures(1, textureObjectIds, 0);
        if (textureObjectIds[0] == 0) {
            Log.w("ysan", "Could not generate a new OpenGL texture object.");
            return 0;
        }

        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inScaled = false;

        final Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), resourceId, options);
        if (bitmap == null) {
            Log.w("ysan", "Resource ID " + resourceId + " could not be decoded.");
            GLES20.glDeleteTextures(1, textureObjectIds, 0);
            return 0;
        }
        // OpenGL 绑定纹理ID，第一个参数告诉 OpenGL 这是一个2D纹理
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, textureObjectIds[0]);
        // 设置纹理过滤
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MIN_FILTER, GLES20.GL_LINEAR_MIPMAP_LINEAR);
        GLES20.glTexParameteri(GLES20.GL_TEXTURE_2D, GLES20.GL_TEXTURE_MAG_FILTER, GLES20.GL_LINEAR);
        // 加载位图数据到 OpenGL
        GLUtils.texImage2D(GLES20.GL_TEXTURE_2D, 0, bitmap, 0);
        // 既然数据已经加载进 OpenGL，就不再需要持有 Android 的位图了，直接调用 bitmap 的 recycle() 方法立即释放这些数据
        bitmap.recycle();
        // 生成 MIP 贴图
        GLES20.glGenerateMipmap(GLES20.GL_TEXTURE_2D);
        // 既然完成了纹理的加载，就可以解除与这个纹理的绑定，这样就不会用其他纹理方法调用意外地改变这个纹理
        GLES20.glBindTexture(GLES20.GL_TEXTURE_2D, 0);
        // 返回纹理对象 ID
        return textureObjectIds[0];
    }
}
