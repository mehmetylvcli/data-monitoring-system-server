package com.dataMonitoringSystemServer.util;


import com.dataMonitoringSystemServer.dto.ResponseDto;
import com.dataMonitoringSystemServer.dto.ResultCodes;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.MediaType;
import org.springframework.transaction.TransactionException;
import org.springframework.util.Assert;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;
import java.util.Optional;

public class Helper {

    private static Logger logger = LoggerFactory.getLogger(Helper.class);
    public static final String TIME_ZONE = "Europe/Istanbul";
    public static final String MAX_PAGE_SIZE = "10000";


    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_X_FORWARDED_FOR",
            "HTTP_X_FORWARDED",
            "HTTP_X_CLUSTER_CLIENT_IP",
            "HTTP_CLIENT_IP",
            "HTTP_FORWARDED_FOR",
            "HTTP_FORWARDED",
            "HTTP_VIA",
            "REMOTE_ADDR"};


    public static String getClientIpAddress(HttpServletRequest request) {
        for (String header : IP_HEADER_CANDIDATES) {
            String ip = Optional.ofNullable(request.getHeader(header)).orElse(request.getRemoteAddr());
            if (ip.equals("0:0:0:0:0:0:0:1")) ip = "127.0.0.1";
            Assert.isTrue(ip.chars().filter($ -> $ == '.').count() == 3, "Illegal IP: " + ip);
            return ip;
        }
        return request.getRemoteAddr();
    }

    public static void fillResponse(ResponseDto<?> response, int resultCode, String errorMessage) {

        response.setResultCode(resultCode);
        response.setErrorMessage(errorMessage);
    }

    public static String getUsername(HttpServletRequest req) {
        Principal principal = req.getUserPrincipal();
        if (principal != null)
            return principal.getName();
        else
            return (String) req.getAttribute("username");
    }

    public static void handleException(ResponseDto<?> response, Exception e) {
        try {
            if (e instanceof DataIntegrityViolationException) {
                DataIntegrityViolationException ex = (DataIntegrityViolationException) e;
                fillResponse(response, ResultCodes.DATABASE_EXCEPTION, ex.getCause().getCause().toString());
            } else if (e instanceof DataAccessException) {
                DataAccessException ex = (DataAccessException) e;
                fillResponse(response, ResultCodes.DATABASE_EXCEPTION, ex.getCause().getCause().toString());
            } else if (e instanceof TransactionException) {
                TransactionException ex = (TransactionException) e;
                fillResponse(response, ResultCodes.TRANSACTION_EXCEPTION, ex.getCause().getCause().toString());
            } else if (e instanceof IOException) {
                IOException ex = (IOException) e;
                fillResponse(response, ResultCodes.IO_EXCEPTION, ex.getCause().getCause().toString());
            } else {
                if (e.getMessage() == null)
                    fillResponse(response, ResultCodes.UNEXPECTED_ERROR, e.toString());
                else
                    fillResponse(response, ResultCodes.UNEXPECTED_ERROR, e.getMessage());
                logger.error(e.toString(), e);
            }

        } catch (Exception e1) {
            if (e.getMessage() == null)
                fillResponse(response, ResultCodes.UNEXPECTED_ERROR, e.toString());
            else
                fillResponse(response, ResultCodes.UNEXPECTED_ERROR, e.getMessage());
            logger.error(e.toString(), e);
        }
    }


    public static void fillAuthExceptionResponse(HttpServletResponse response, int httpStatus, String errorMessage)
            throws IOException {
        response.setStatus(httpStatus);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        ResponseDto<?> responseDto = new ResponseDto<>();
        Helper.fillResponse(responseDto, ResultCodes.UNEXPECTED_ERROR, errorMessage);
        final ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getWriter(), responseDto);

    }


}
