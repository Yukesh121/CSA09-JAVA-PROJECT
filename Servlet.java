import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.*;

public class Project extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {

        // Set response content type
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        // Retrieve form parameters
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        // Database connection parameters
        String url = "jdbc:mysql://localhost:3306/Shoppinglist";
        String dbUsername = "root";
        String dbPassword = "root@123";

        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            
            // Establish a connection to the database
            Connection conn = DriverManager.getConnection(url, dbUsername, dbPassword);
            
            // Prepare a SQL statement to retrieve user from the database
            String sql = "SELECT * FROM users WHERE username=? AND password=?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            
            // Execute the SQL query
            ResultSet result = statement.executeQuery();

            // Check if user exists
            if (result.next()) {
                // Redirect to another page upon successful login
                response.sendRedirect("ViewShoppingList.html");
            } else {
                out.println("<h2>Login failed. Please check your username and password.</h2>");
            }

            // Clean up resources
            statement.close();
            conn.close();
        } catch (SQLException e) {
            // Handle SQL errors
            out.println("<h2>SQL Error: " + e.getMessage() + "</h2>");
            e.printStackTrace(out);
        } catch (ClassNotFoundException e) {
            // Handle ClassNotFound exception
            out.println("<h2>JDBC Driver not found: " + e.getMessage() + "</h2>");
            e.printStackTrace(out);
        } catch (Exception e) {
            // Handle other exceptions
            out.println("<h2>An error occurred: " + e.getMessage() + "</h2>");
            e.printStackTrace(out);
        }
    }
}
