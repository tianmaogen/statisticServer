package cn.ibabygroup.statistic.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.Date;

/**
 * Created by Crowhyc on 2016/7/25.
 */
@Data
public class PulpitInfoDto implements IDto {
    @ApiModelProperty(value = "编号")
    private String id;
    @ApiModelProperty(value = "讲坛名称")
    private String pulpitName;
    @ApiModelProperty(value = "讲坛创建时间")
    private Date createTime;
    @ApiModelProperty(value = "讲坛计划开始时间")
    private Date planStartTime;
    @ApiModelProperty(value = "讲坛计划结束时间")
    private Date planEndTime;
    @ApiModelProperty(value = "讲坛实际开始时间")
    private Date startTime;
    @ApiModelProperty(value = "讲坛实际结束时间")
    private Date endTime;
    @ApiModelProperty(value = "讲坛报名人数上限")
    private int registerLimit;
    @ApiModelProperty(value = "讲坛报名人数")
    private int registerCount;
    @ApiModelProperty(value = "讲坛价格")
    private int price;
    @ApiModelProperty(value = "讲坛最高同时在线人数")
    private int maxCount;
    @ApiModelProperty(value = "讲坛到课总人数")
    private int attendanceMaxCount;
    @ApiModelProperty(value = "讲坛类型1孕妈2医生")
    private int pulpitType;
}
