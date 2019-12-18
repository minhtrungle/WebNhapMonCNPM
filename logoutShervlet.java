package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Customer;

/**
 *
 * @author Admin
 */
public class LogOutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        Customer cus = (Customer) session.getAttribute("customer");
        Customer admin = (Customer) session.getAttribute("admin");
        if (cus != null) {
            session.invalidate();
            req.setAttribute("message", "Đăng xuất thành công");
            req.getRequestDispatcher("message.jsp").forward(req, resp);
        } else {
            if (admin != null) {
                session.invalidate();
                resp.sendRedirect("admin/login.jsp");
            }else{
                resp.sendRedirect("index.jsp");
            }
        }



    }
