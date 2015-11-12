package com.lzadrija.help.api;

import com.lzadrija.ResultDescription;
import com.lzadrija.url.ShortUrl;
import com.lzadrija.url.registration.UrlRegistrationData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

@Api(value = "url")
public interface UrlAPI {

    @ApiOperation(tags = "url-register", value = "Register long URL",
                  authorizations = {
                      @Authorization(value = "basicAccountId")})
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Long URL registered"),
        @ApiResponse(code = 400, message = "Invalid registration data supplied", response = ResultDescription.class),
        @ApiResponse(code = 409, message = "URL is an already registered short URL", response = ResultDescription.class)
    })
    ResponseEntity<ShortUrl> register(@ApiParam(required = true) UrlRegistrationData data,
                                      HttpServletRequest request);

    @ApiOperation(tags = "url-redirect", value = "Redirect to long URL using short URL")
    @ApiResponses(value = {
        @ApiResponse(code = 301, message = "Redirected to long URL"),
        @ApiResponse(code = 302, message = "Redirected to long URL"),
        @ApiResponse(code = 404, message = "Short URL is not registered", response = ResultDescription.class)
    })
    ModelAndView redirectUsingShortUrl(@ApiParam(required = true) String shortUrl);

}
