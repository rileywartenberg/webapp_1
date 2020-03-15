package edu.calpoly.csc365.example1.service;

import edu.calpoly.csc365.example1.dao.AdminDao;
import edu.calpoly.csc365.example1.dao.UserDaoImpl;
import edu.calpoly.csc365.example1.entity.Admin;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

public class AuthenticationAdmin {
    AdminDao adminDao = null;

    public AuthenticationAdmin(AdminDao adminDao) {
        this.adminDao = adminDao;
    }

    public Boolean authenticate(String name, String pass) {
        Boolean authenticated = false;
        authenticated = adminDao.authenticate(name, pass);
        return authenticated;
    }

    public static Cookie getLoginCookie(HttpServletRequest request) {
        Cookie loginCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("user")) {
                    loginCookie = cookie;
                    break;
                }
            }
        }
        return loginCookie;
    }

    public static Cookie createLoginCookie(String name) {
        // create cookie
        Cookie loginCookie = new Cookie("user", name);
        //setting cookie to expire in 5 mins
        loginCookie.setMaxAge(5*60);
        return loginCookie;
    }
}