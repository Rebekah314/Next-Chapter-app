package org.launchcode.nextchapter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.launchcode.nextchapter.controllers.AuthenticationController;
import org.launchcode.nextchapter.data.UserRepository;
import org.launchcode.nextchapter.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class AuthenticationFilter implements HandlerInterceptor {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AuthenticationController authenticationController;

    private static final List<String> approvedList = Arrays.asList("/login", "/register", "/logout", "/styles", "/home");

    private static boolean isApprovedListed(String path) {
        for (String pathRoot : approvedList) {
            if (path.startsWith(pathRoot)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) throws IOException {

        //Don't require sign-in for approved pages
        if (isApprovedListed(request.getRequestURI())) {
            //returning true indicates that the request may proceed
            return true;
        }

        HttpSession session = request.getSession();
        User user = authenticationController.getUserFromSession(session);

        //The user is logged in
        if (user != null) {
            return true;
        }

        //The user is not logged in
        response.sendRedirect("/home");
        return false;

    }
}
