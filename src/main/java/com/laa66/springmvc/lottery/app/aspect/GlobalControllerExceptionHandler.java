package com.laa66.springmvc.lottery.app.aspect;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalControllerExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ModelAndView handleAccessErrorException(HttpServletRequest request, Exception exception) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("url", request.getRequestURL().toString());
        modelAndView.addObject("exception", exception.getMessage());
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
