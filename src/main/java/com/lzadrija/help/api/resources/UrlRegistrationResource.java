package com.lzadrija.help.api.resources;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel
public interface UrlRegistrationResource {

    @ApiModelProperty(required = true, value = "Long URL to register and shorten; must be a valid URL prefixed by HTTP protocol")
    String getUrl();

    @ApiModelProperty(required = false, value = "HTTP redirect response status, allowed: MOVED_PERMANENTLY(301) or FOUND(302), defaults to FOUND")
    Integer getRedirectType();

}
