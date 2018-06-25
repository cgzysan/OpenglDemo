package ysan.opengldemo.widget;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * Created by YSAN on 2018/6/1
 */
public class MyGLSurfaceView extends GLSurfaceView {

    private final MyGLRenderer mRenderer;
    private final GLRender mGlRender;

    public MyGLSurfaceView(Context context) {
        super(context);

        setEGLContextClientVersion(2);

        mRenderer = new MyGLRenderer();
        mGlRender = new GLRender();

        setRenderer(mRenderer);
    }
}
