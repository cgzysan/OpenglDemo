package ysan.opengldemo.renderer;

import android.opengl.GLSurfaceView.Renderer;
import android.opengl.GLU;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import ysan.opengldemo.game.core.GameSystem;
import ysan.opengldemo.game.core.SystemTimer;
import ysan.opengldemo.game.geom.RectBox;
import ysan.opengldemo.utils.GLEx;
import ysan.opengldemo.utils.GLHelper;

public class MyRender implements Renderer {

    public void onDrawFrame(GL10 gl) {
        // TODO Auto-generated method stub
        GLHelper.clear(gl);
        GLHelper.openTransparent(gl);
        GameSystem.setDrawingStart();
        SystemTimer.run();
        GameSystem.update(SystemTimer.elapsedTime);

        GameSystem.paint(glex);
        GLHelper.disableBlend(gl);


        GameSystem.setDrawingEnd();
    }

    GLEx glex;

    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // TODO Auto-generated method stub
        gl.glViewport(0, 0, width, height);
        gl.glMatrixMode(GL10.GL_PROJECTION);
        gl.glLoadIdentity();
        //		gl.glOrthof(0, width, height, 0, 1, -100);
        GLU.gluOrtho2D(gl, 0, width, height, 0);
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glLoadIdentity();
        GameSystem.screenBox = new RectBox(0, 0, width, height);
        glex = new GLEx(gl);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig arg1) {
        // TODO Auto-generated method stub

        gl.glClearColor(0, 0, 0, 1);
        gl.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);
        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glClearDepthf(1);
        gl.glEnable(GL10.GL_DEPTH_TEST);
        gl.glDepthFunc(GL10.GL_LEQUAL);
    }

}
