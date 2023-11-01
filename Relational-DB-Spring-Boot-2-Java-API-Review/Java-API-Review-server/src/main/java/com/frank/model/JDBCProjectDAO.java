package com.frank.model;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import org.springframework.stereotype.Component;

@Component
public class JDBCProjectDAO implements ProjectDAO {

	private JdbcTemplate jdbcTemplate;

	public JDBCProjectDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Project> getAllActiveProjects() {
		List<Project> allProjects = new ArrayList<>();
		String sqlGetAllActiveProjects = "SELECT project_id, name, from_date, to_date FROM project WHERE "
				+ "(now() BETWEEN from_date AND to_date) OR "
				+ "(now() > from_date AND to_date IS NULL) OR "
				+ "(now() < to_date AND from_date IS NULL)";

		SqlRowSet results = jdbcTemplate.queryForRowSet(sqlGetAllActiveProjects);
		while (results.next()) {
			Project projectResult = mapRowToProject(results);
			allProjects.add(projectResult);
		}
		return allProjects;
	}

	@Override
	public void removeEmployeeFromProject(Long projectId, Long employeeId) {
		String sqlRemoveEmployeeFromProject = "DELETE FROM project_employee WHERE project_id=? AND employee_id=?";
		jdbcTemplate.update(sqlRemoveEmployeeFromProject, projectId, employeeId);
	}

	@Override
	public void addEmployeeToProject(Long projectId, Long employeeId) {
		String sqlAddEmployeeToProject = "INSERT INTO project_employee (project_id, employee_id) VALUES (?, ?)";
		jdbcTemplate.update(sqlAddEmployeeToProject, projectId, employeeId);
	}
	
	private Project mapRowToProject(SqlRowSet results) {
		Project project = new Project();
		project.setId(results.getLong("project_id"));
		project.setName(results.getString("name"));
		if(results.getDate("from_date") != null) {
			project.setStartDate(results.getDate("from_date").toLocalDate());
		}
		if(results.getDate("to_date") != null) {
			project.setEndDate(results.getDate("to_date").toLocalDate());
		}
		return project;
	}

}
