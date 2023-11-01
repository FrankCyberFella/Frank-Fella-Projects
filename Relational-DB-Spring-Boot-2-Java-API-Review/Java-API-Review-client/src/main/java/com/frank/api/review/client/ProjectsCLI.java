package com.frank.api.review.client;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.dbcp2.BasicDataSource;

import com.frank.api.review.client.model.Department;
import com.frank.api.review.client.model.Employee;
import com.frank.api.review.client.model.EmployeeDAO;
import com.frank.api.review.client.model.Project;
import com.frank.api.review.client.model.ProjectDAO;
import com.frank.api.review.client.model.jdbc.JDBCProjectDAO;
import com.frank.api.review.client.model.jdbc.JDBCEmployeeDAO;
import com.frank.api.review.client.view.Menu;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.client.RestTemplate;

public class ProjectsCLI {
	
	private static final String MAIN_MENU_OPTION_EMPLOYEES = "Employees";
	private static final String MAIN_MENU_OPTION_DEPARTMENTS = "Departments";
	private static final String MAIN_MENU_OPTION_PROJECTS = "Projects";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String[] MAIN_MENU_OPTIONS = new String[] { MAIN_MENU_OPTION_DEPARTMENTS, 
																	 MAIN_MENU_OPTION_EMPLOYEES, 
																	 MAIN_MENU_OPTION_PROJECTS, 
																	 MAIN_MENU_OPTION_EXIT };

	private static final String MENU_OPTION_RETURN_TO_MAIN = "Return to main menu";

	private static final String DEPT_MENU_OPTION_ALL_DEPARTMENTS = "Show all departments";
	private static final String DEPT_MENU_OPTION_SEARCH_BY_NAME = "Department search by name";
	private static final String DEPT_MENU_OPTION_DEPARTMENT_EMPLOYEES = "Show department employees";
	private static final String DEPT_MENU_OPTION_ADD_DEPARTMENT = "Add a new department";
	private static final String DEPT_MENU_OPTION_UPDATE_NAME = "Update department name";
	private static final String[] DEPARTMENT_MENU_OPTIONS = new String[] { DEPT_MENU_OPTION_ALL_DEPARTMENTS,
																		   DEPT_MENU_OPTION_SEARCH_BY_NAME,
																		   DEPT_MENU_OPTION_DEPARTMENT_EMPLOYEES,
																		   DEPT_MENU_OPTION_ADD_DEPARTMENT,
																		   DEPT_MENU_OPTION_UPDATE_NAME,
																		   MENU_OPTION_RETURN_TO_MAIN};
	
	private static final String EMPL_MENU_OPTION_ALL_EMPLOYEES = "Show all employees";
	private static final String EMPL_MENU_OPTION_SEARCH_BY_NAME = "Employee search by name";
	private static final String EMPL_MENU_OPTION_EMPLOYEES_NO_PROJECTS = "Show employees without projects";
	private static final String EMPL_MENU_OPTION_CHANGE_DEPARTMENT = "Change employee's department";
	private static final String[] EMPL_MENU_OPTIONS = new String[] { EMPL_MENU_OPTION_ALL_EMPLOYEES,
																	 EMPL_MENU_OPTION_SEARCH_BY_NAME,
																	 EMPL_MENU_OPTION_EMPLOYEES_NO_PROJECTS,
																	 EMPL_MENU_OPTION_CHANGE_DEPARTMENT,
																	 MENU_OPTION_RETURN_TO_MAIN};
	
	private static final String PROJ_MENU_OPTION_ACTIVE_PROJECTS = "Show active projects";
	private static final String PROJ_MENU_OPTION_PROJECT_EMPLOYEES = "Show project employees";
	private static final String PROJ_MENU_OPTION_ASSIGN_EMPLOYEE_TO_PROJECT = "Assign an employee to a project";
	private static final String PROJ_MENU_OPTION_REMOVE_EMPLOYEE_FROM_PROJECT = "Remove employee from project";
	private static final String[] PROJ_MENU_OPTIONS = new String[] { PROJ_MENU_OPTION_ACTIVE_PROJECTS,
																	 PROJ_MENU_OPTION_PROJECT_EMPLOYEES,
																	 PROJ_MENU_OPTION_ASSIGN_EMPLOYEE_TO_PROJECT,
																	 PROJ_MENU_OPTION_REMOVE_EMPLOYEE_FROM_PROJECT,
																	 MENU_OPTION_RETURN_TO_MAIN };
	
	private Menu menu;
	private ProjectDAO projectDAO;
	private EmployeeDAO employeeDAO;

	// Define a RestTemplate object to call the API Server
	RestTemplate callApi = new RestTemplate();

	// Define Base URL for API Server
	final String API_BASE_URL = "http://localhost:8080";

	public static void main(String[] args) {
		ProjectsCLI application = new ProjectsCLI();
		application.run();
	}
	
	public ProjectsCLI() {
		this.menu = new Menu(System.in, System.out);
		// postgres is a Data Base Mangager which contains many databases which contains many tables
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/apireview");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		projectDAO = new JDBCProjectDAO(dataSource);
		employeeDAO = new JDBCEmployeeDAO(dataSource);
	}

	private void run() {
		displayApplicationBanner();	
		while(true) {
			printHeading("Main Menu");
			String choice = (String)menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);
			if(choice.equals(MAIN_MENU_OPTION_DEPARTMENTS)) {
				handleDepartments();
			} else if(choice.equals(MAIN_MENU_OPTION_EMPLOYEES)) {
				handleEmployees();
			} else if(choice.equals(MAIN_MENU_OPTION_PROJECTS)) {
				handleProjects();
			} else if(choice.equals(MAIN_MENU_OPTION_EXIT)) {
				System.exit(0);
			}
		}
	}

	private void handleDepartments() {
		printHeading("Departments");
		String choice = (String)menu.getChoiceFromOptions(DEPARTMENT_MENU_OPTIONS);
		if(choice.equals(DEPT_MENU_OPTION_ALL_DEPARTMENTS)) {
			handleListAllDepartments();
		} else if(choice.equals(DEPT_MENU_OPTION_SEARCH_BY_NAME)) {
			handleDepartmentSearch();
		} else if(choice.equals(DEPT_MENU_OPTION_DEPARTMENT_EMPLOYEES)) {
			handleDepartmentEmployeeList();
		} else if(choice.equals(DEPT_MENU_OPTION_ADD_DEPARTMENT)) {
			handleAddDepartment();
		} else if(choice.equals(DEPT_MENU_OPTION_UPDATE_NAME)) {
			handleUpdateDepartmentName();
		} 
	}

	private void handleAddDepartment() {
		printHeading("Add New Department");
		String newDepartmentName = getUserInput("Enter new Department name");
		Department newDepartment = new Department();
		newDepartment.setName(newDepartmentName);
		// Replace the DAO call to add the department with API call
		// newDepartment = departmentDAO.createDepartment(newDepartment);
		//

		// Because are need to do an HTTP POST:
		//
		//     We need header to define the content typewe are sending - HttpHeaders
		//     We to create a request withthe headers and new department - HttpEntity
		//     We need to send the request (HttpEntity) to the POST

		HttpHeaders theHeaders = new HttpHeaders();            // Define request headers
		theHeaders.setContentType(MediaType.APPLICATION_JSON); // Set the content type
		HttpEntity theRequest = new HttpEntity(newDepartment, theHeaders);  // Create a request

		//                      postForObject(                url                 , request   , class-of-object-to-be-returned);
		newDepartment = callApi.postForObject("http://localhost:8080/department", theRequest, Department.class);

		System.out.println("\n*** "+newDepartment.getName()+" created ***");
	}
	
	private void handleUpdateDepartmentName() {
		printHeading("Update Department Name");
		// Replace the DAO call to add the department with API call
		// List<Department> allDepartments = departmentDAO.getAllDepartments();
		Department[] allDepartments;
		allDepartments = callApi.getForObject(API_BASE_URL+"/department", Department[].class);

		if(allDepartments.length > 0) {
			System.out.println("\n*** Choose a Department ***");
			Department selectedDepartment = (Department)menu.getChoiceFromOptions(allDepartments);
			String newDepartmentName = getUserInput("Enter new Department name");
			selectedDepartment.setName(newDepartmentName);
			// Replace the DAO call to add the department with API call
			//			departmentDAO.saveDepartment(selectedDepartment);

			// Define HttpHeaders object to hold header info for HTTP Request
			HttpHeaders requestHeaders = new HttpHeaders();
			requestHeaders.setContentType(MediaType.APPLICATION_JSON);

			// Define an HttpEntity object to hold properly formatted HTTPRequest with data and headers
			HttpEntity httpRequest = new HttpEntity(selectedDepartment,requestHeaders);

			// Call the API server with an HTTP PUT request
			callApi.put(API_BASE_URL + "/department", httpRequest);
		} else {
			System.out.println("\n*** No results ***");
		}
	}

	private void handleListAllDepartments() {
		printHeading("All Departments");
		// Replace the call to the DAO with a call to our API server
		// List<Department> allDepartments = departmentDAO.getAllDepartments();
		RestTemplate callApi = new RestTemplate();  // Define a RestTemplate object to make and manage API call

		// RestTemplate can return an array of objects automatically for us, so here is an array to hold the returned objects
		Department[] deptArray;   // Hold the Department objects returned from the API the call
		                          // There is no easy way to have a RestTemplate method return a Java List object

		// Call the API to get all the departments ans store them in our array
		//  RestTemplate    getForObject(               URL                , data-type-to-return)
		deptArray = callApi.getForObject("http://localhost:8080/department", Department[].class);

		// Call our method to list the departments with the data returned from API call
		listDepartments(Arrays.asList(deptArray));   // convert the array we have to a List for the method
	}

	private void handleDepartmentSearch() {
		printHeading("Department Search");
		String departmentSearch = getUserInput("Enter department name to search for");
		// Replace the DAO call to add the department with API call
		//			List<Department> departments = departmentDAO.searchDepartmentsByName(departmentSearch);

		// Define array reference to hold result from API call - need this since the API inetrface doe snot return List objects
		Department[] theDepartments;

		// Call the API server with an HTTP PUT request
		theDepartments = callApi.getForObject(API_BASE_URL+"/department/namesearch?name="+departmentSearch
				                             ,Department[].class);

        // Call method to list departments was API results as a List
		listDepartments(Arrays.asList(theDepartments));
	}
	
	private void handleDepartmentEmployeeList() {
		printHeading("Department Employee List");
		// Replace the DAO call to add the department with API call
		// List<Department> allDepartments = departmentDAO.getAllDepartments();
		Department[] theDepts;

		// Call the API server with an HTTP GET request to retrieve all departments
		theDepts = callApi.getForObject(API_BASE_URL + "/department", Department[].class);

		if (theDepts.length > 0) {
			System.out.println("\n*** Choose a Department ***");
			Department selectedDepartment = (Department) menu.getChoiceFromOptions(theDepts);

			// Replace the DAO call to add the department with API call
			//  List<Employee> departmentEmployees = employeeDAO.getEmployeesByDepartmentId(selectedDepartment.getId());
			Employee[] deptEmployees;  // Hold employees returned from API call

			deptEmployees = callApi.getForObject(API_BASE_URL + "/employee/department/" + selectedDepartment.getId(), Employee[].class);
			listEmployees(Arrays.asList(deptEmployees));
		} else {
			System.out.println("\n*** No results ***");
		    }
	}

	private void listDepartments(List<Department> departments) {
		System.out.println();
		if(departments.size() > 0) {
			for(Department dept : departments) {
				System.out.println(dept.getName());
			}
		} else {
			System.out.println("\n*** No results ***");
		}
	}
	
	private void handleEmployees() {
		printHeading("Employees");
		String choice = (String)menu.getChoiceFromOptions(EMPL_MENU_OPTIONS);
		if(choice.equals(EMPL_MENU_OPTION_ALL_EMPLOYEES)) {
			handleListAllEmployees();
		} else if(choice.equals(EMPL_MENU_OPTION_SEARCH_BY_NAME)) {
			handleEmployeeSearch();
		} else if(choice.equals(EMPL_MENU_OPTION_EMPLOYEES_NO_PROJECTS)) {
			handleUnassignedEmployeeSearch();
		} else if(choice.equals(EMPL_MENU_OPTION_CHANGE_DEPARTMENT)) {
			handleChangeEmployeeDepartment();
		}
	}

	private void handleListAllEmployees() {
		printHeading("All Employees");
		// Replace the DAO call to add the department with API call
		// List<Employee> allEmployees = employeeDAO.getAllEmployees();

		// Call the API server with an HTTP GET request to retrieve all departments
		Employee[] allEmployees =  callApi.getForObject(API_BASE_URL + "/employee",Employee[].class);
		
		listEmployees(Arrays.asList(allEmployees));
	}

	private void handleEmployeeSearch() {
		printHeading("Employee Search");
		String firstNameSearch = getUserInput("Enter first name to search for");
		String lastNameSearch = getUserInput("Enter last name to search for");
		// Replace the DAO call to add the department with API call
		//			List<Employee> employees = employeeDAO.searchEmployeesByName(firstNameSearch, lastNameSearch);
		Employee[] employees = callApi.getForObject(API_BASE_URL + "/employee?firstname=" + firstNameSearch + "&lastname=" + lastNameSearch,Employee[].class);
		listEmployees(Arrays.asList(employees));
	}

	private void handleUnassignedEmployeeSearch() {
		printHeading("Unassigned Employees");
		// Replace the DAO call to add the department with API call
		//			List<Employee> employees = employeeDAO.getEmployeesWithoutProjects();
		Employee[] employees = callApi.getForObject(API_BASE_URL + "/employee/project/none",Employee[].class);;
		listEmployees(Arrays.asList(employees));
	}
	
	private void listEmployees(List<Employee> employees) {
		System.out.println();
		if(employees.size() > 0) {
			for(Employee emp : employees) {
				System.out.println(emp.getLastName() + ", " + emp.getFirstName());
			}
		} else {
			System.out.println("\n*** No results ***");
		}
	}
	
	private void handleChangeEmployeeDepartment() {
		printHeading("Change Employee Department");
		
		System.out.println("Choose an employee to transfer:");
		// Replace the DAO call to add the department with API call
		//			List<Employee> employees = employeeDAO.getAllEmployees();
		//List<Employee> allEmployees = employeeDAO.getAllEmployees();
		Employee[] allEmps =  callApi.getForObject(API_BASE_URL + "/employee",Employee[].class);

		Employee selectedEmployee = (Employee)menu.getChoiceFromOptions(allEmps);

		System.out.println("Choose the new department:");
		// Replace the DAO call to add the department with API call
		//           List<Department> allDepartments = departmentDAO.getAllDepartments();
		Department[] allDepts = callApi.getForObject(API_BASE_URL +"/department", Department[].class);

		Department selectedDepartment = (Department)menu.getChoiceFromOptions(allDepts);
		// Replace the DAO call to add the department with API call
		//      employeeDAO.changeEmployeeDepartment(selectedEmployee.getId(), selectedDepartment.getId());
		HttpHeaders theHeaders = new HttpHeaders();
		theHeaders.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity theRequest = new HttpEntity(null, theHeaders);
		callApi.put(API_BASE_URL + "/employee/"+selectedEmployee.getId()+"?newdept="+selectedDepartment.getId(), theRequest);
	}

	private void handleProjects() {
		printHeading("Projects");
		String choice = (String)menu.getChoiceFromOptions(PROJ_MENU_OPTIONS);
		if(choice.equals(PROJ_MENU_OPTION_ACTIVE_PROJECTS)) {
			handleListActiveProjects();
		} else if(choice.equals(PROJ_MENU_OPTION_PROJECT_EMPLOYEES)) {
			handleProjectEmployeeList();
		} else if(choice.equals(PROJ_MENU_OPTION_ASSIGN_EMPLOYEE_TO_PROJECT)) {
			handleEmployeeProjectAssignment();
		}  else if(choice.equals(PROJ_MENU_OPTION_REMOVE_EMPLOYEE_FROM_PROJECT)) {
			handleEmployeeProjectRemoval();
		}
	}

	private void handleListActiveProjects() {
		printHeading("Active Projects");
		// Replace the DAO call to add the department with API call
		//List<Project> projects = projectDAO.getAllActiveProjects();

		Project[] theProjects = null;

		theProjects = callApi.getForObject(API_BASE_URL+"/project", Project[].class);

		listProjects(Arrays.asList(theProjects));
	}

	private void handleEmployeeProjectRemoval() {
		printHeading("Remove Employee From Project");
		
		Project selectedProject = getProjectSelectionFromUser();
		
		System.out.println("Choose an employee to remove:");
		List<Employee> projectEmployees = employeeDAO.getEmployeesByProjectId(selectedProject.getId());
		if(projectEmployees.size() > 0) {
			Employee selectedEmployee = (Employee)menu.getChoiceFromOptions(projectEmployees.toArray());
			projectDAO.removeEmployeeFromProject(selectedProject.getId(), selectedEmployee.getId());
			System.out.println("\n*** "+selectedEmployee+" removed from "+selectedProject+" ***");
		} else {
			System.out.println("\n*** No results ***");
		}
	}

	private void handleEmployeeProjectAssignment() {
		printHeading("Assign Employee To Project");
		
		Project selectedProject = getProjectSelectionFromUser();
		
		System.out.println("Choose an employee to add:");
		List<Employee> allEmployees = employeeDAO.getAllEmployees();
		Employee selectedEmployee = (Employee)menu.getChoiceFromOptions(allEmployees.toArray());
		
		projectDAO.addEmployeeToProject(selectedProject.getId(), selectedEmployee.getId());
		System.out.println("\n*** "+selectedEmployee+" added to "+selectedProject+" ***");
	}
	
	private void handleProjectEmployeeList() {
		Project selectedProject = getProjectSelectionFromUser();
		List<Employee> projectEmployees = employeeDAO.getEmployeesByProjectId(selectedProject.getId());
		listEmployees(projectEmployees);
	}

	private Project getProjectSelectionFromUser() {
		System.out.println("Choose a project:");
		List<Project> allProjects = projectDAO.getAllActiveProjects();
		return (Project)menu.getChoiceFromOptions(allProjects.toArray());
	}
	
	private void listProjects(List<Project> projects) {
		System.out.println();
		if(projects.size() > 0) {
			for(Project proj : projects) {
				System.out.println(proj.getName());
			}
		} else {
			System.out.println("\n*** No results ***");
		}
	}

	private void printHeading(String headingText) {
		System.out.println("\n"+headingText);
		for(int i = 0; i < headingText.length(); i++) {
			System.out.print("-");
		}
		System.out.println();
	}
	
	@SuppressWarnings("resource")
	private String getUserInput(String prompt) {
		System.out.print(prompt + " >>> ");
		return new Scanner(System.in).nextLine();
	}

	private void displayApplicationBanner() {
		System.out.println(" ______                 _                         _____           _           _     _____  ____");
		System.out.println("|  ____|               | |                       |  __ \\         (_)         | |   |  __ \\|  _ \\");
		System.out.println("| |__   _ __ ___  _ __ | | ___  _   _  ___  ___  | |__) | __ ___  _  ___  ___| |_  | |  | | |_) |");
		System.out.println("|  __| | '_ ` _ \\| '_ \\| |/ _ \\| | | |/ _ \\/ _ \\ |  ___/ '__/ _ \\| |/ _ \\/ __| __| | |  | |  _ <");
		System.out.println("| |____| | | | | | |_) | | (_) | |_| |  __/  __/ | |   | | | (_) | |  __/ (__| |_  | |__| | |_) |");
		System.out.println("|______|_| |_| |_| .__/|_|\\___/ \\__, |\\___|\\___| |_|   |_|  \\___/| |\\___|\\___|\\__| |_____/|____/");
		System.out.println("                 | |             __/ |                          _/ |");
		System.out.println("                 |_|            |___/                          |__/");
		System.out.println();
	}
}
