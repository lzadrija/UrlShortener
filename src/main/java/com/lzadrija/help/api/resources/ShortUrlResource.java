package com.lzadrija.help.api.resources;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public interface ShortUrlResource {

    @ApiModelProperty(required = true, value = "Generated short URL; contains alphanumeric characters (uppercase/lowercase) and/or digits")
    String getShortUrl();

}
