package cn.ibabygroup.statistic.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

/**
 * Created by Crowhyc on 2016-01-15.
 * 用于发布的分页Dto
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class PageDataDto<T extends IDto> {
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


    public static <T extends IDto> PageDataDto<T> createNewDto() {
        return new PageDataDto<>();
    }
}
