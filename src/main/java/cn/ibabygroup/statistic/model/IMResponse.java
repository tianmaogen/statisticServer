package cn.ibabygroup.statistic.model;

import lombok.Data;

/**
 * Created by tianmaogen on 2016/9/12.
 * IMService接口返回消息
 */
@Data
public class IMResponse {

    public static final int okCode = 200;
    //返回消息
    private String description;
    //返回的数据
    private Object data;
    //执行结果 200 为正常
    private Integer code;
}
