package com.lzadrija.help.api.resources;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public interface AccountIdResource {

    @ApiModelProperty(required = true, value = "Account ID containing min 3 and max 15 alphanumeric and special characters: _-+!$")
    String getAccountId();
}
