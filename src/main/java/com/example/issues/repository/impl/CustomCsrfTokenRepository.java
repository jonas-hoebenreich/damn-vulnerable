package com.example.issues.repository.impl;

import com.example.issues.security.utils.CustomCsrfToken;
import com.example.issues.utils.Rng;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Component
@Slf4j
public class CustomCsrfTokenRepository implements CsrfTokenRepository {
    static final String DEFAULT_CSRF_PARAMETER_NAME = "_csrf";
    static final String DEFAULT_CSRF_HEADER_NAME = "X-XSRF-TOKEN";

    private String parameterName = DEFAULT_CSRF_PARAMETER_NAME;

    private String headerName = DEFAULT_CSRF_HEADER_NAME;

    private String cookieName = "USER_INFO";

    private static final String DEFAULT_CSRF_TOKEN_ATTR_NAME = "_csrf";

    private String sessionAttributeName = DEFAULT_CSRF_TOKEN_ATTR_NAME;
    Rng rng;
    @Autowired
    public void setRng(Rng rng) {
        this.rng = rng;
    }

    @Override
    public CustomCsrfToken generateToken(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, this.cookieName);
        if (cookie == null) {
            return new CustomCsrfToken(rng.next());
        }
        String cookieValue = cookie.getValue();
        String token = cookieValue.split("\\|")[0];
        if (!StringUtils.hasLength(token)) {
            return new CustomCsrfToken(rng.next());
        }
        return new CustomCsrfToken(token);
    }

    @Override
    public void saveToken(CsrfToken token, HttpServletRequest request, HttpServletResponse httpServletResponse) {
        if (token == null) {
            HttpSession session = request.getSession(false);
            if (session != null) {
                session.removeAttribute(this.sessionAttributeName);
            }
        }
        else {
            HttpSession session = request.getSession();
            session.setAttribute(this.sessionAttributeName, token);
        }
    }

    @Override
    public CsrfToken loadToken(HttpServletRequest httpServletRequest) {
        HttpSession session = httpServletRequest.getSession(false);
        if (session == null) {
            return null;
        }
        return (CustomCsrfToken) session.getAttribute(this.sessionAttributeName);
    }


}
