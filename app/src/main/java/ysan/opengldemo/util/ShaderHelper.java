package ysan.opengldemo.util;

import android.util.Log;

import static android.opengl.GLES20.*;

/**
 * Created by YSAN on 2018/6/25
 *
 * 编译着色器代码
 */
public class ShaderHelper {

    public static int compileVertexShader(String shaderCode) {
        return complieShader(GL_VERTEX_SHADER, shaderCode);
    }

    public static int compileFragmentShader(String shaderCode) {
        return complieShader(GL_FRAGMENT_SHADER, shaderCode);
    }

    private static int complieShader(int type, String shaderCode) {
        // 创建一个新的着色器对象，
        final int shaderObjectId = glCreateShader(type);
        if (shaderObjectId == 0) {
            Log.w("ysan", "Could not create new shader.");
            return 0;
        }
        // 有了着色器对象，通过该方法上传源代码
        glShaderSource(shaderObjectId, shaderCode);
        // 编译
        glCompileShader(shaderObjectId);
        // 取出编译状态
        final int[] compileStatus = new int[1];
        glGetShaderiv(shaderObjectId, GL_COMPILE_STATUS, compileStatus, 0);

        if (compileStatus[0] == 0) {
            // 如果失败，就删除它
            glDeleteShader(shaderObjectId);
            Log.w("ysan", "Compilation of shader failed.");
            return 0;
        }
        // 编译成功，着色器对象有效，可以在代码中使用它
        return shaderObjectId;
    }

    public static int linkProgram(int vertexShaderId, int fragmentShaderId) {
        // 新建程序对象
        final int programObjectId = glCreateProgram();
        if (programObjectId == 0) {
            Log.w("ysan", "Could not create new program");
            return 0;
        }
        // 附上着色器
        glAttachShader(programObjectId, vertexShaderId);
        glAttachShader(programObjectId, fragmentShaderId);
        // 着色器联合起来
        glLinkProgram(programObjectId);
        // 检查链接是否成功
        final int[] linkStatus = new int[1];
        glGetProgramiv(programObjectId, GL_LINK_STATUS, linkStatus, 0);
        if (linkStatus[0] == 0) {
            glDeleteProgram(programObjectId);
            Log.w("ysan", "Linking of program failed");
            return 0;
        }
        return programObjectId;
    }

    public static boolean validateProgram(int programObjectId) {
        glValidateProgram(programObjectId);
        return false;
    }
}
