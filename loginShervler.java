package controller;

import DAO.CustomerDAO;
import DAO.CustomerOrderDAO;
import DAO.ProductDAO;
import DAO.ProductDetailDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Customer;
import model.Product;

/**
 *
 * @author Admin
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session.getAttribute("customer") == null || session.getAttribute("admin") == null) {
            String email = req.getParameter("Email");
            String password = req.getParameter("Password");
            Customer customer = (new CustomerDAO()).findCustomer(email, password);
            if (customer != null) {
                if (customer.isAdmin()) {
                    //Phan dang nhap admin
                    session.setAttribute("admin", customer);
                    ArrayList<Product> listProduct = (new ProductDAO()).findAll();
                    session.setAttribute("listProduct", listProduct);
                    session.setAttribute("listProductDetail", (new ProductDetailDAO()).findProductDetails(listProduct));
                    session.setAttribute("listCustomer", (new CustomerDAO()).findAll());
                    session.setAttribute("listOrder", (new CustomerOrderDAO()).findAll());
                    session.setAttribute("listActiveOrder", (new CustomerOrderDAO()).findAllActiveOrder());
                    
                    resp.sendRedirect("admin/index.jsp");
                } else {
                    session.setAttribute("customer", customer);
                    resp.sendRedirect("index.jsp");
                }
            } else {
                req.setAttribute("message", "Tài khoản hoặc mật khẩu sai xin vui lòng đăng nhập lại");
                req.getRequestDispatcher("message.jsp").forward(req, resp);
            }
        } else {
            resp.sendRedirect("index.jsp");
        }

    }

}
