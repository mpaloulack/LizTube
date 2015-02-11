package com.liztube.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 * Default controller for the SPA application : all pages redirect to the single page index.jsp
 */
@Controller
@RequestMapping("/")
public class IndexController {
    @RequestMapping(method = {RequestMethod.GET, RequestMethod.HEAD}, value = "*")
    public ModelAndView SingleApplicationPage() {
        ModelAndView modelAndView = new ModelAndView("index");

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.writerWithDefaultPrettyPrinter();

        return modelAndView;
    }
}
