package com.example.issues.controller.html;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Locale;

@CrossOrigin
@Slf4j
@Controller
@RequiredArgsConstructor
public class CustomErrorController implements ErrorController {

    @RequestMapping(value = "error", method = RequestMethod.GET)
    public String handleError(HttpServletRequest request, Model model) throws UnsupportedEncodingException {
        //todo add logging
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);

        model.addAttribute("url", request.getAttribute(RequestDispatcher.FORWARD_REQUEST_URI));
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value() &&(String) request.getAttribute(RequestDispatcher.FORWARD_QUERY_STRING) !=  null) {
                model.addAttribute("params", URLDecoder.decode((String) request.getAttribute(RequestDispatcher.FORWARD_QUERY_STRING), "UTF-8"));
            }
        }
        return "error";
    }

    @Override
    public String getErrorPath() {
        return "error";
    }
}
