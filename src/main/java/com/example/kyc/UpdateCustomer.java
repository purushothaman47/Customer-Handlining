package com.example.kyc;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/updateCustomer")
public class UpdateCustomer extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "UPDATE customer SET mobile=?, email=? WHERE customer_id=?"
            );

            ps.setString(1, req.getParameter("mobile"));
            ps.setString(2, req.getParameter("email"));
            ps.setLong(3, Long.parseLong(req.getParameter("id")));

            ps.executeUpdate();
            res.getWriter().println("Customer updated successfully");
        } catch (Exception e) {
            res.getWriter().println("Error: " + e.getMessage());
        }
    }
}
