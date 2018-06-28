package ysan.util;

import android.opengl.GLES20;
import android.util.Log;

/**
 * Created by YSAN on 2018/6/25
 *
 * 编译着色器代码
 */
public class ShaderHelper {

    public static int compileVertexShader(String shaderCode) {
        return complieShader(GLES20.GL_VERTEX_SHADER, shaderCode);
    }

    public static int compileFragmentShader(String shaderCode) {
        return complieShader(GLES20.GL_FRAGMENT_SHADER, shaderCode);
    }

    private static int complieShader(int type, String shaderCode) {
        // 创建一个新的着色器对象，
        final int shaderObjectId = GLES20.glCreateShader(type);
        if (shaderObjectId == 0) {
            Log.w("ysan", "Could not create new shader.");
            return 0;
        }
        // 有了着色器对象，通过该方法上传源代码
        GLES20.glShaderSource(shaderObjectId, shaderCode);
        // 编译
        GLES20.glCompileShader(shaderObjectId);
        // 取出编译状态
        final int[] compileStatus = new int[1];
        GLES20.glGetShaderiv(shaderObjectId, GLES20.GL_COMPILE_STATUS, compileStatus, 0);

        if (compileStatus[0] == 0) {
            // 如果失败，就删除它
            GLES20.glDeleteShader(shaderObjectId);
            Log.w("ysan", "Compilation of shader failed.");
            return 0;
        }
        // 编译成功，着色器对象有效，可以在代码中使用它
        return shaderObjectId;
    }

    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        // 新建程序对象
        final int programObjectId = GLES20.glCreateProgram();
        if (programObjectId == 0) {
            Log.w("ysan", "Could not create new program");
            return 0;
        }
        // 附上着色器
        GLES20.glAttachShader(programObjectId, vertexShaderId);
        GLES20.glAttachShader(programObjectId, fragmentShaderId);
        // 着色器联合起来
        GLES20.glLinkProgram(programObjectId);
        // 检查链接是否成功
        final int[] linkStatus = new int[1];
        GLES20.glGetProgramiv(programObjectId, GLES20.GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] == 0) {
            GLES20.glDeleteProgram(programObjectId);
            Log.w("ysan", "Linking of program failed");
            return 0;
        }
        return programObjectId;
    }

    public static boolean validateProgram(int programObjectId) {
        GLES20.glValidateProgram(programObjectId);
        return false;
    }
}
