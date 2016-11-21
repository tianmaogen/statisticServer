package cn.ibabygroup.statistic.controller;

import cn.ibabygroup.statistic.constant.AppConstant;
import cn.ibabygroup.statistic.model.PulpitInfo;
import cn.ibabygroup.statistic.service.PulpitInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created with IntelliJ IDEA.
 * User: Crowhyc
 * Date: 2016/11/2
 * Time: 11:42
 */
@Api(value = "statistic-server-api", description = "讲坛信息管理接口", protocols = "application/json")
@RestController
@RequestMapping(AppConstant.API_WEBAPI_PRIFEX + "pulpitInfo")
public class PulpitInfoController {
    @Autowired
    private PulpitInfoService service;

    @ApiOperation(value = "通过id获取讲坛详情", notes = "通过id获取讲坛详情")
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public PulpitInfo getInfo(@ApiParam(name = "id", value = "讲坛id") @PathVariable("id") String id) {
        return service.getPulpitInfoById(id);
    }

}
