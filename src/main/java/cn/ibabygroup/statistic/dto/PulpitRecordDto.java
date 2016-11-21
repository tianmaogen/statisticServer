package cn.ibabygroup.statistic.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Crowhyc on 2016/7/25.
 */
@Data
public class PulpitRecordDto implements IDto{
    @ApiModelProperty(value = "id")
    private String id;
    @ApiModelProperty(value = "老客户端讲坛在线人数纪录<人数>")
    private List<Integer> oldClientCounts = new ArrayList<>();
    @ApiModelProperty(value = "新客户端讲坛在线人数纪录<人数>")
    private List<Integer> newClientCounts = new ArrayList<>();
    @ApiModelProperty(value = "讲坛在线人数纪录<时间> 2016-08-12")
    private List<String> times = new ArrayList<>();
}
