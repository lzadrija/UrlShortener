package com.lzadrija.help.api.resources;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public interface ResultDescriptionResource {

    @ApiModelProperty(required = true, value = "Is operation successful")
    boolean isSuccess();

    @ApiModelProperty(position = 2, required = true, value = "Result message, possibly validation or exception message")
    String getDescription();

}
