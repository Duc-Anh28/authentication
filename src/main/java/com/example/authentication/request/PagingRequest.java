package com.example.authentication.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Max;

@Data
public class PagingRequest {
    @ApiModelProperty(example = "1")
    private String pageNum;

    @Max(value = 3000, message = "{displayNumber.max-size}")
    @ApiModelProperty(example = "20")
    private String pageSize;

    public Integer getPageNum() {
        try {
            return Integer.parseInt(this.pageNum);
        } catch (Exception e) {
            return 1;
        }
    }

    public Integer getPageSize() {
        try {
            if (this.pageSize == null || this.pageSize.equals("0")) {
                return Integer.MAX_VALUE;
            }
            return Integer.parseInt(this.pageSize);
        } catch (Exception e) {
            return 20;
        }
    }

    public void setPageNum(String pageNum) {
        this.pageNum = pageNum;
    }

    public void setPageSize(String pageSize) {
        this.pageSize = pageSize;
    }
}
