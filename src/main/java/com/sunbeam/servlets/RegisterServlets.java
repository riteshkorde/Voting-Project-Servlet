package com.sunbeam.servlets;

import com.sunbeam.daos.UserDao;
import com.sunbeam.daos.UserDaoImpl;
import com.sunbeam.entities.User;

import java.io.IOException;
import java.sql.Date;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/register")
public class RegisterServlets extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        try {
            // 1. Get form values
            String firstName = req.getParameter("firstName");
            String lastName = req.getParameter("lastName");
            String email = req.getParameter("email");
            String password = req.getParameter("password");
            String birthStr = req.getParameter("birth");
            String statusStr = req.getParameter("status");
            String role = req.getParameter("role");

            // 2. Convert date and status
            Date birth = Date.valueOf(birthStr); // format: yyyy-MM-dd
            int status = Integer.parseInt(statusStr);

            // 3. Set user object
            User user = new User();
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setPassword(password);
            user.setBirth(birth);
            user.setStatus(status);
            user.setRole(role);

            // 4. Save using DAO
            try (UserDao userDao = new UserDaoImpl()) {
             userDao.save(user);
             
            }

            // 5. Redirect (optional)
            resp.sendRedirect("index.html");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
