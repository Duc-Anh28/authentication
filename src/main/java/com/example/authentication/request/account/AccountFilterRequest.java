package com.example.authentication.request.account;

import com.example.authentication.request.PagingRequest;
import com.example.authentication.type.OrderByType;
import com.example.authentication.type.SortByFieldType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AccountFilterRequest extends PagingRequest {

    private String keyword;
    private String sortBy;
    private String orderBy;

    @ApiModelProperty(hidden = true)
    public OrderByType getOrderByType() {
        return OrderByType.getInstance(orderBy);
    }

    @ApiModelProperty(hidden = true)
    public SortByFieldType getSortByType() {
        return SortByFieldType.getInstance(sortBy);
    }
}
