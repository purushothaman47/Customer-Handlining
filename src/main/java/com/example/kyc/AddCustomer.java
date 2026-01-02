package com.example.kyc;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/addCustomer")
public class AddCustomer extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.setContentType("text/html");
        res.getWriter().println("""
            <h2>Add Customer</h2>
            <form method='post'>
                Name: <input name='name'><br>
                Mobile: <input name='mobile'><br>
                Email: <input name='email'><br>
                Address: <input name='address'><br><br>
                <button>Add</button>
            </form>
        """);
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "INSERT INTO customer(full_name,mobile,email,address,status) VALUES (?,?,?,?,?)"
            );

            ps.setString(1, req.getParameter("name"));
            ps.setString(2, req.getParameter("mobile"));
            ps.setString(3, req.getParameter("email"));
            ps.setString(4, req.getParameter("address"));
            ps.setString(5, "ACTIVE");

            ps.executeUpdate();
            res.getWriter().println("Customer added successfully");
        } catch (Exception e) {
            res.getWriter().println("Error: " + e.getMessage());
        }
    }
}
