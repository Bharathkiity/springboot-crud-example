package com.bharath.controller;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.anyOf;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import com.fasterxml.jackson.databind.ObjectMapper;

import com.bharath.dto.EmployeeDTO;
import com.bharath.entites.Employee;
import com.bharath.exception.EmployeeNotFoundException;
import com.bharath.exception.GlobalExceptionHandler;
import com.bharath.service.EmployeeService;

@WebMvcTest(controllers = EmployeeController.class)
@Import(GlobalExceptionHandler.class)
class EmployeeControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private EmployeeService employeeService;

	@Test
	void createEmployee_returns201() throws Exception {
		Employee request = new Employee();
		request.setName("Alice");
		request.setSalary(50000);

		Employee saved = new Employee();
		saved.setId(1L);
		saved.setName("Alice");
		saved.setSalary(50000);
		saved.setProjects(Collections.emptyList());

		when(employeeService.saveEmployee(any(Employee.class))).thenReturn(saved);

		mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is("Alice")))
				.andExpect(jsonPath("$.salary", anyOf(is(50000.0), is(50000))));
	}

	@Test
	void getEmployee_returnsDto() throws Exception {
		EmployeeDTO dto = new EmployeeDTO();
		dto.setId(1L);
		dto.setName("Alice");
		dto.setSalary(50000);
		dto.setDepartmentName("Engineering");

		when(employeeService.getEmployeeById(1L)).thenReturn(dto);

		mockMvc.perform(get("/employees/1"))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is("Alice")))
				.andExpect(jsonPath("$.salary", anyOf(is(50000.0), is(50000))))
				.andExpect(jsonPath("$.departmentName", is("Engineering")));
	}

	@Test
	void getEmployee_whenNotFound_returns404WithErrorResponse() throws Exception {
		when(employeeService.getEmployeeById(1L))
				.thenThrow(new EmployeeNotFoundException("Employee not found with id 1"));

		mockMvc.perform(get("/employees/1"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", is("Employee not found with id 1")))
				.andExpect(jsonPath("$.status", is(404)));
	}

	@Test
	void updateEmployee_returnsUpdatedEmployee() throws Exception {
		Employee request = new Employee();
		request.setName("Alice Updated");
		request.setSalary(60000);

		Employee updated = new Employee();
		updated.setId(1L);
		updated.setName("Alice Updated");
		updated.setSalary(60000);
		updated.setProjects(Collections.emptyList());

		when(employeeService.updateEmployee(eq(1L), any(Employee.class))).thenReturn(updated);

		mockMvc.perform(put("/employees/1").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(1)))
				.andExpect(jsonPath("$.name", is("Alice Updated")))
				.andExpect(jsonPath("$.salary", anyOf(is(60000.0), is(60000))));
	}

	@Test
	void updateEmployee_whenNotFound_returns404WithErrorResponse() throws Exception {
		when(employeeService.updateEmployee(eq(1L), any(Employee.class)))
				.thenThrow(new EmployeeNotFoundException("Employee not found with id 1"));

		Employee request = new Employee();
		request.setName("Doesn't matter");

		mockMvc.perform(put("/employees/1").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", is("Employee not found with id 1")))
				.andExpect(jsonPath("$.status", is(404)));
	}

	@Test
	void deleteEmployee_returnsOkString() throws Exception {
		// Controller returns fixed success string after service call.
		mockMvc.perform(delete("/employees/1"))
				.andExpect(status().isOk())
				.andExpect(content().string("Employee Deleted Successfully"));
	}

	@Test
	void deleteEmployee_whenNotFound_returns404WithErrorResponse() throws Exception {
		doThrow(new EmployeeNotFoundException("Employee not found with id 1")).when(employeeService)
				.deleteEmployee(1L);

		mockMvc.perform(delete("/employees/1"))
				.andExpect(status().isNotFound())
				.andExpect(jsonPath("$.message", is("Employee not found with id 1")))
				.andExpect(jsonPath("$.status", is(404)));
	}

	@Test
	void createEmployee_whenServiceThrows_returns500WithErrorResponse() throws Exception {
		Employee request = new Employee();
		request.setName("Alice");
		request.setSalary(50000);

		when(employeeService.saveEmployee(any(Employee.class))).thenThrow(new RuntimeException("db down"));

		mockMvc.perform(post("/employees").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
				.andExpect(status().isInternalServerError())
				.andExpect(jsonPath("$.message", is("db down")))
				.andExpect(jsonPath("$.status", is(500)));
	}
}

