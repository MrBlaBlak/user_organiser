package pl.coderslab.users;

import pl.coderslab.entity.User;
import pl.coderslab.entity.UserDao;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet("/users/edit")
public class UserEdit extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao userDao = new UserDao();
        User user = userDao.readUser(Integer.parseInt(request.getParameter("id")));
        request.setAttribute("user", user);
        getServletContext().getRequestDispatcher("/users/edit.jsp")
                .forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        UserDao userDao = new UserDao();
        userDao.update(new User(Integer.parseInt(request.getParameter("id")), request.getParameter("name"), request.getParameter("email"), request.getParameter("password")));
        response.sendRedirect(request.getContextPath() + "/user/list");
    }
}