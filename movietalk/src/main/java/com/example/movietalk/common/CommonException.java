package com.example.movietalk.common;



import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import ch.qos.logback.core.model.Model;
import lombok.extern.log4j.Log4j2;

@ControllerAdvice
@Log4j2
public class CommonException {

    @ExceptionHandler(NoResourceFoundException.class)
    public String getError() {
        log.info("404에러 처리");
        return "/except/url404";
    }
    @ExceptionHandler(Exception.class)
    public String error(Exception e, Model m) {
        log.info("404에러 처리");
        return "/except/url404";
    }
}
