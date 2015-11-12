package com.lzadrija.help.api.resources;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public interface AccountRegistrationResource {

    @ApiModelProperty(required = false, value = "Generated password; contains 8 alphanumeric characters (uppercase/lowercase)")
    String getPassword();

}
