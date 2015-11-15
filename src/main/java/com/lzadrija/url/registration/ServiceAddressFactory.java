package com.lzadrija.url.registration;

import javax.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

@Service
public class ServiceAddressFactory {

    private static final String SHORT_URL_REFERENCE_TEMPLATE = "http://%s:%s";

    public String create(HttpServletRequest request) {

        String serverAddress = String.format(SHORT_URL_REFERENCE_TEMPLATE, request.getServerName(), request.getServerPort());

        return appendContextPathIfNonEmpty(request, serverAddress);
    }

    private String appendContextPathIfNonEmpty(HttpServletRequest request, String serverAddress) {
        return (request.getContextPath() != null && !request.getContextPath().trim().isEmpty())
               ? (serverAddress + request.getContextPath() + "/")
               : serverAddress + "/";
    }
}
