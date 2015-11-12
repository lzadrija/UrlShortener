package com.lzadrija.help.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.servlet.ModelAndView;

@Api(value = "help")
public interface HelpAPI {

    @ApiOperation(tags = "help-page", value = "Open Help page")
    @ApiResponses(value = {
        @ApiResponse(code = 302, message = "Redirected to help page")
    })
    ModelAndView redirectToHelpPage();

}
