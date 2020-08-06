package demo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

	@Autowired
	  private DataSource dataSource;
	
	@GetMapping("/db")
	public String db(Map<String, Object> model) {
	    try (Connection connection = dataSource.getConnection()) {
	        Statement stmt = connection.createStatement();
	        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Employee (tick timestamp)");
	        stmt.executeUpdate("INSERT INTO Employee VALUES (now())");
	        ResultSet rs = stmt.executeQuery("SELECT tick FROM Employee");

	        ArrayList<String> output = new ArrayList<String>();
	        while (rs.next()) {
	          output.add("Read from DB: " + rs.getTimestamp("tick"));
	        }

	        model.put("records", output);
	        return "db";
	      } catch (Exception e) {
	        model.put("message", e.getMessage());
	        return "error";
	      }
	    }

	
	@GetMapping("/employee")
	public String getEmployee() {
		return "Hello World!";
	}
}
