package ysan.airhockeytextured.shape;

import android.view.TextureView;

import ysan.airhockeytextured.data.VertexArray;
import ysan.util.Constants;

/**
 * Created by YSAN on 2018/6/28
 */
public class Table {
    private static final int POSITION_COMPONENT_COUNT = 2;
    private static final int TEXTURE_COORDINATES_COMPONENT_COUNT = 2;
    private static final int STRIDE = (POSITION_COMPONENT_COUNT + TEXTURE_COORDINATES_COMPONENT_COUNT) * Constants.BYTES_PER_FLOAT;

    // 顶点数据
    private static final float[] VERTEX_DATA = {
            // Order of coordinates: X, Y, S, T

            // Triangle fan
            0f, 0f, 0.5f, 0.5f,
            -0.5f, -0.8f, 0f, 0.9f,
            0.5f, -0,8f, 1f, 0.9f,
            0.5f, 0.8f, 1f, 0.1f,
            -0.5f, 0.8f, 0f, 0.1f,
            -0.5f, -0.8f, 0f, 0.9f
    };

    private final VertexArray mVertexArray;

    public Table() {
        this.mVertexArray = new VertexArray(VERTEX_DATA);
    }

    public void bindData()
}
