package org.example.usermanager.controller;

import org.example.usermanager.model.User;
import org.example.usermanager.model.UserDAO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "UserServlet", urlPatterns = "/users")
public class UserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDAO userDAO;

    public void init() {
        userDAO = new UserDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    insertUser(req, resp);
                    break;
                case "edit":
                    updateUser(req, resp);
                    break;
                case "sort":
                    sortUserByName(req, resp);
                    break;
                case "search":
                    searchUserByCountry(req, resp);
                    break;
                case "update-user-with-proc":
                    updateUserWithProc(req, resp);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }

    }

    private void searchUserByCountry(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        String country = req.getParameter("country");
        List<User> users = userDAO.findByCountry(country);
        req.setAttribute("listUser", users);
        RequestDispatcher dispatcher = req.getRequestDispatcher("user/list.jsp");
        dispatcher.forward(req, resp);
    }

    private void sortUserByName(HttpServletRequest req, HttpServletResponse resp) throws SQLException, ServletException, IOException {
        List<User> users=userDAO.sortByName();
        req.setAttribute("listUser", users);
        RequestDispatcher dispatcher = req.getRequestDispatcher("user/list.jsp");
        dispatcher.forward(req, resp);
    }

    private void updateUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String country = req.getParameter("country");
        User user = new User(id, name, email, country);
        try {
            userDAO.updateUser(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        RequestDispatcher dispatcher = req.getRequestDispatcher("user/edit.jsp");
        dispatcher.forward(req, resp);
    }

    private void updateUserWithProc(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String country = req.getParameter("country");
        User user = new User(id, name, email, country);
        userDAO.updateUserProc(user);
        resp.sendRedirect("/users");
    }
    private void insertUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException, SQLException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String country = req.getParameter("country");

        String add = req.getParameter("add");
        String edit = req.getParameter("edit");
        String delete = req.getParameter("delete");
        String view = req.getParameter("view");
        List<Integer> permissions = new ArrayList<>();

        if(add!=null){
            permissions.add(1);
        }
        if(edit!=null){
            permissions.add(2);
        }
        if(delete!=null){
            permissions.add(3);
        }
        if(view!=null){
            permissions.add(4);
        }

        User newUser = new User(name, email, country);
        //userDAO.insertUser(newUser);
        //userDAO.insertUserStore(newUser);
        userDAO.addUserTransaction(newUser, permissions);
        RequestDispatcher dispatcher = req.getRequestDispatcher("user/create.jsp");
        dispatcher.forward(req, resp);

    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if (action == null) {
            action = "";
        }
        try {
            switch (action) {
                case "create":
                    showCreateForm(req, resp);
                    break;
                case "edit":
                    showEditForm(req, resp);
                    break;
                case "delete":
                    deleteUser(req, resp);
                    break;
                case "test-without-tran":
                    testWithoutTran(req, resp);
                    break;
                case "test-use-tran":
                    testUseTran(req, resp);
                    break;
                case "get-all-proc":
                    getAllUserWithProc(req, resp);
                    break;
                case "delete-proc":
                    deleteUserWithProc(req, resp);
                    break;
                default:
                    listUser(req, resp);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }

    }

    private void deleteUserWithProc(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        int id = Integer.parseInt(req.getParameter("id"));
        userDAO.deleteUserProc(id);
        resp.sendRedirect("/users");
    }


    private void getAllUserWithProc(HttpServletRequest req, HttpServletResponse resp) {
        userDAO.getAllUserProc();
    }

    private void testUseTran(HttpServletRequest req, HttpServletResponse resp) {
        userDAO.insertUpdateUseTransaction();
    }

    private void testWithoutTran(HttpServletRequest req, HttpServletResponse resp) {
        userDAO.insertUpdateWithoutTransaction();
    }

    private void listUser(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<User> listUser = userDAO.selectAllUsers();
        req.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = req.getRequestDispatcher("user/list.jsp");
        dispatcher.forward(req, resp);

    }

    private void deleteUser(HttpServletRequest req, HttpServletResponse resp) throws SQLException, IOException, ServletException {
        int id = Integer.parseInt(req.getParameter("id"));
        userDAO.deleteUser(id);
        List<User> listUser = userDAO.selectAllUsers();
        req.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = req.getRequestDispatcher("user/list.jsp");
        dispatcher.forward(req, resp);
    }

    private void showEditForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        //User existingUser = userDAO.selectUser(id);
        User existingUser = userDAO.getUserById(id);
        RequestDispatcher dispatcher = req.getRequestDispatcher("user/edit.jsp");
        req.setAttribute("existingUser", existingUser);
        dispatcher.forward(req, resp);
    }

    private void showCreateForm(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher dispatcher = req.getRequestDispatcher("user/create.jsp");
        dispatcher.forward(req, resp);
    }
}
