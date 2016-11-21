package cn.ibabygroup.statistic.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Crowhyc on 2016/7/22.
 */
@Data
@AllArgsConstructor
@ApiModel
public class PageDataModel<T> {
    @ApiModelProperty(value = "总共数据量")
    private long totalCount;
    @ApiModelProperty(value = "当前页数")
    private int currentPage;
    @ApiModelProperty(value = "每页数据量")
    private int pageSize;
    @ApiModelProperty(value = "总共页数")
    private long totalPage;
    @ApiModelProperty(value = "数据")
    private List<T> dataList;

    private PageDataModel() {
        dataList = new ArrayList<>();
    }

    public static <T> PageDataModel<T> createModel() {
        return new PageDataModel<>();
    }

    public static <T> PageDataModel<T> createModel(long totalCount, int currentPage, int pageSize, List<T> dataList) {
        PageDataModel<T> newModel = new PageDataModel<>();
        if (pageSize == 0) {
            newModel.totalPage = 1;
        } else {
            newModel.totalPage = totalCount % pageSize == 0 ? totalCount / pageSize : totalCount / pageSize + 1;
        }
        newModel.pageSize = pageSize;
        newModel.totalCount = totalCount;
        newModel.currentPage = currentPage;
        newModel.dataList = dataList;
        return newModel;
    }
}
