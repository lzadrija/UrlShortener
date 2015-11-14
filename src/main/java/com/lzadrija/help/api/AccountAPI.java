package com.lzadrija.help.api;

import com.lzadrija.ResultDescription;
import com.lzadrija.account.registration.AccountId;
import com.lzadrija.account.registration.AccountRegistration;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import java.util.Map;
import org.springframework.http.ResponseEntity;

@Api(value = "account")
public interface AccountAPI {

    @ApiOperation(tags = "account", value = "Open an account")
    @ApiResponses(value = {
        @ApiResponse(code = 201, message = "Account created"),
        @ApiResponse(code = 400, message = "Invalid account ID supplied", response = ResultDescription.class),
        @ApiResponse(code = 409, message = "Account ID already exists", response = ResultDescription.class)
    })
    ResponseEntity<AccountRegistration> createAccount(@ApiParam(required = true) AccountId accountId);

    @ApiOperation(tags = "account-URL", value = "Retrieve short URL usage statistics",
                  authorizations = {
                      @Authorization(value = "basicAuth")})
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Retrieved statistic as [long URL to hit count]", response = Long.class, responseContainer = "Map"),
        @ApiResponse(code = 401, message = "Bad cedentials, invalid Account info supplied", response = ResultDescription.class),
        @ApiResponse(code = 404, message = "Account ID is not registered", response = ResultDescription.class)
    })
    ResponseEntity<Map<String, Long>> getUrlStatistic(@ApiParam(required = true) String accountId);
}
