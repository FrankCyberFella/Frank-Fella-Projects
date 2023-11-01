package com.frank.api.review.client.model.jdbc;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.frank.api.review.client.model.Employee;
import com.frank.api.review.client.model.EmployeeDAO;

public class JDBCEmployeeDAO implements EmployeeDAO {

	private JdbcTemplate jdbcTemplate;  // Define an object to access the JDBC DAO methods

	public JDBCEmployeeDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);  // Assign the JdbcTemplate object the data sourec sent in from the user
	}
	
	@Override
	public List<Employee> getAllEmployees() {
		List<Employee> allEmployees = new ArrayList<>();
		String sqlGetAllEmployees = "SELECT employee_id, department_id, first_name, last_name, birth_date, gender, hire_date FROM employee";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllEmployees);
		while (results.next()) {
			Employee employeeResult = mapRowToEmployee(results);
			allEmployees.add(employeeResult);
		}
		return allEmployees;
	}

	@Override
	public List<Employee> searchEmployeesByName(String firstNameSearch, String lastNameSearch) {
		List<Employee> allEmployees = new ArrayList<>();
		String sqlGetAllEmployeesByName = "SELECT employee_id, department_id, first_name, last_name, birth_date, gender, hire_date FROM employee "+
				"WHERE first_name LIKE ? AND last_name LIKE ?";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllEmployeesByName, "%" + firstNameSearch + "%", "%" + lastNameSearch + "%");
		while (results.next()) {
			Employee employeeResult = mapRowToEmployee(results);
			allEmployees.add(employeeResult);
		}
		return allEmployees;
	}

	@Override
	public List<Employee> getEmployeesByDepartmentId(long id) {
		List<Employee> allEmployees = new ArrayList<>();
		String sqlGetAllEmployeesFromDepartment = "SELECT employee_id, department_id, first_name, last_name, birth_date, gender, hire_date FROM employee "+
				"WHERE department_id=?";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllEmployeesFromDepartment, id);
		while (results.next()) {
			Employee employeeResult = mapRowToEmployee(results);
			allEmployees.add(employeeResult);
		}
		return allEmployees;
	}

	@Override
	public List<Employee> getEmployeesWithoutProjects() {
		List<Employee> allEmployees = new ArrayList<>();
		String sqlGetEmployeesByProject = "SELECT employee_id, department_id, first_name, last_name, birth_date, gender, hire_date FROM employee e "+
				"WHERE employee_id NOT IN (SELECT DISTINCT employee_id FROM project_employee)";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetEmployeesByProject);
		while (results.next()) {
			Employee employeeResult = mapRowToEmployee(results);
			allEmployees.add(employeeResult);
		}
		return allEmployees;
	}

	@Override
	public List<Employee> getEmployeesByProjectId(Long projectId) {
		List<Employee> allEmployees = new ArrayList<>();
		String sqlGetEmployeesByProject = "SELECT e.employee_id, e.department_id, e.first_name, e.last_name, e.birth_date, e.gender, e.hire_date FROM employee e "+
				"JOIN project_employee pe ON e.employee_id = pe.employee_id "+
				"WHERE pe.project_id = ?";
		
		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetEmployeesByProject, projectId);
		while (results.next()) {    // like scannerObject.nextLine() - get the next line
			Employee employeeResult = mapRowToEmployee(results);
			allEmployees.add(employeeResult);
		}
		return allEmployees;
	}

	@Override
	public void changeEmployeeDepartment(Long employeeId, Long departmentId) {
		String sqlUpdateEmployeeDepartment = "UPDATE employee SET department_id=? WHERE employee_id=?";
		jdbcTemplate.update(sqlUpdateEmployeeDepartment, departmentId, employeeId);
	}
	// result represents one line (set of columns) from the table
	// A metjod to create a Java object from a table row
	private Employee mapRowToEmployee(SqlRowSet result) {  // result has the row to be mapped
		Employee employee = new Employee();                // Create a Java object
		employee.setId(result.getLong("employee_id"));     //     Copy the data
		employee.setDepartmentId(result.getLong("department_id"));   // from teh row
		employee.setFirstName(result.getString("first_name"));       //   into the
		employee.setLastName(result.getString("last_name"));         //      Java Object
		employee.setBirthDay(result.getDate("birth_date").toLocalDate());
		employee.setGender(result.getString("gender").charAt(0));
		employee.setHireDate(result.getDate("hire_date").toLocalDate());
		return employee;   // Return the Java object containing the data from the row
	}

}
