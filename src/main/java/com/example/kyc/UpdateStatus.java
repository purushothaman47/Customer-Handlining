package com.example.kyc;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

@WebServlet("/updateStatus")
public class UpdateStatus extends HttpServlet {

    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws IOException {

        try (Connection con = DBUtil.getConnection()) {
            PreparedStatement ps = con.prepareStatement(
                "UPDATE customer SET status=? WHERE customer_id=?"
            );

            ps.setString(1, req.getParameter("status"));
            ps.setLong(2, Long.parseLong(req.getParameter("id")));

            ps.executeUpdate();
            res.getWriter().println("Status updated successfully");
        } catch (Exception e) {
            res.getWriter().println("Error: " + e.getMessage());
        }
    }
}
