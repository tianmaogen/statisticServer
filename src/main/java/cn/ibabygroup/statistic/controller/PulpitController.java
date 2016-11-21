package cn.ibabygroup.statistic.controller;

import cn.ibabygroup.statistic.constant.AppConstant;
import cn.ibabygroup.statistic.dto.PageDataDto;
import cn.ibabygroup.statistic.dto.PulpitInfoDto;
import cn.ibabygroup.statistic.dto.PulpitRecordDto;
import cn.ibabygroup.statistic.model.PageDataModel;
import cn.ibabygroup.statistic.model.PulpitInfo;
import cn.ibabygroup.statistic.model.PulpitRecord;
import cn.ibabygroup.statistic.service.PageDataService;
import cn.ibabygroup.statistic.service.PulpitRecordService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Crowhyc on 2016/7/25.
 */
@RestController
@RequestMapping(AppConstant.API_WEBAPI_PRIFEX + "pulpit")
@Api(value = AppConstant.API_WEBAPI_PRIFEX + "pulpit", description = "讲坛统计接口")
public class PulpitController {

    @Autowired
    private PulpitRecordService pulpitRecordService;

    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, false));
    }

    @RequestMapping(value = "/getPulpitInfo", method = RequestMethod.GET)
    @ApiOperation(value = "（后台）获取对应时间段内的讲坛数据", notes = "获取对应时间段内讲坛数据")
    public PageDataDto<PulpitInfoDto> getPulpitInfo(
            @ApiParam(name = "startDate", value = "开始时间")
            @RequestParam Date startDate,
            @ApiParam(name = "endDate", value = "结束时间")
            @RequestParam Date endDate,
            @ApiParam(name = "pageIndex", value = "当前页")
            @RequestParam int pageIndex,
            @ApiParam(name = "pageSize", value = "每页显示数量")
            @RequestParam int pageSize
    ) throws IllegalAccessException {
        PageDataModel<PulpitInfo> infos = pulpitRecordService.getPulpitInfoByTime(startDate, endDate, pageIndex, pageSize);
        PageDataDto<PulpitInfoDto> infoDtos = PageDataService.convertToDto(infos, PulpitInfoDto.class);
        if (infoDtos == null) {
            return PageDataDto.createNewDto();
        }
        for (PulpitInfoDto dto : infoDtos.getDataList()) {
            dto.setMaxCount(pulpitRecordService.getMaxCountById(dto.getId()));
            dto.setAttendanceMaxCount(pulpitRecordService.getAttendanceMaxCountById(dto.getId()));
        }
        return infoDtos;
    }

    @RequestMapping(value = "/getPulpitMaxOnlineCountRecord", method = RequestMethod.GET)
    @ApiOperation(value = "（后台）获取对应讲坛在线人数纪录", notes = "获取对应讲坛在线人数纪录")
    public PulpitRecordDto getPulpitMaxOnlineCountRecord(@ApiParam(name = "id", value = "讲坛编号")
                                           @RequestParam String id) {
        return pulpitRecordService.getPulpitMaxOnlineCountDto(id);
    }

    @RequestMapping(value = "/getPulpitAttendanceRecord", method = RequestMethod.GET)
    @ApiOperation(value = "（后台）获取对应讲坛到课人数纪录", notes = "获取对应讲坛到课人数纪录")
    public PulpitRecordDto getPulpitAttendanceRecord(@ApiParam(name = "id", value = "讲坛编号")
                                                     @RequestParam String id) {
        return pulpitRecordService.getPulpitAttendanceDto(id);
    }

}
