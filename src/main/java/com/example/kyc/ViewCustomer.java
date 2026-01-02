package com.example.kyc;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/viewCustomer")
public class ViewCustomer extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        res.setContentType("text/html");
        long id = Long.parseLong(req.getParameter("id"));

        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "SELECT * FROM customer WHERE customer_id=?"
            );
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                res.getWriter().println(String.format("""
                    <h2>Customer Profile</h2>
                    ID: %d<br>
                    Name: %s<br>
                    Mobile: %s<br>
                    Email: %s<br>
                    Status: %s<br>
                """,
                        rs.getLong("customer_id"),
                        rs.getString("full_name"),
                        rs.getString("mobile"),
                        rs.getString("email"),
                        rs.getString("status")
                ));
            } else {
                res.getWriter().println("Customer not found");
            }
        } catch (Exception e) {
            res.getWriter().println("Error: " + e.getMessage());
        }
    }
}
