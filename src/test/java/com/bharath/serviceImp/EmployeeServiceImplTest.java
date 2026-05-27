package com.bharath.serviceImp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.bharath.dto.EmployeeDTO;
import com.bharath.entites.Address;
import com.bharath.entites.Department;
import com.bharath.entites.Employee;
import com.bharath.exception.EmployeeNotFoundException;
import com.bharath.repo.EmployeeRepository;

class EmployeeServiceImplTest {
	private AutoCloseable mocks;

	@Mock
	private EmployeeRepository employeeRepository;

	@Mock
	private ModelMapper modelMapper;

	@InjectMocks
	private EmployeeServiceImpl employeeService;

	@BeforeEach
	void setUp() {
		mocks = MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	void tearDown() throws Exception {
		if (mocks != null) {
			mocks.close();
		}
	}

	@Test
	void createEmployee_savesViaRepository() {
		Employee emp = new Employee();
		emp.setName("Alice");
		emp.setSalary(50000);

		Employee saved = new Employee();
		saved.setId(1L);
		saved.setName(emp.getName());
		saved.setSalary(emp.getSalary());

		when(employeeRepository.save(emp)).thenReturn(saved);

		Employee result = employeeService.saveEmployee(emp);

		assertSame(saved, result);
		verify(employeeRepository).save(emp);
	}

	@Test
	void createEmployee_whenRepositoryThrows_propagatesException() {
		Employee emp = new Employee();
		emp.setName("Alice");
		emp.setSalary(50000);

		when(employeeRepository.save(emp)).thenThrow(new RuntimeException("db down"));

		assertThrows(RuntimeException.class, () -> employeeService.saveEmployee(emp));
		verify(employeeRepository).save(emp);
	}

	@Test
	void getEmployeeById_returnsDtoAndSetsDepartmentName() {
		Department dept = new Department();
		dept.setDeptName("Engineering");

		Employee emp = new Employee();
		emp.setId(1L);
		emp.setName("Alice");
		emp.setSalary(50000);
		emp.setDepartment(dept);

		EmployeeDTO mapped = new EmployeeDTO();
		mapped.setId(1L);
		mapped.setName("Alice");
		mapped.setSalary(50000);
		// service sets departmentName from department.getDeptName()
		mapped.setDepartmentName(null);

		when(employeeRepository.findById(1L)).thenReturn(Optional.of(emp));
		when(modelMapper.map(emp, EmployeeDTO.class)).thenReturn(mapped);

		EmployeeDTO result = employeeService.getEmployeeById(1L);

		assertNotNull(result);
		assertEquals(1L, result.getId());
		assertEquals("Alice", result.getName());
		assertEquals(50000, result.getSalary());
		assertEquals("Engineering", result.getDepartmentName());
	}

	@Test
	void getEmployeeById_whenNotFound_throwsEmployeeNotFoundException() {
		when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

		EmployeeNotFoundException ex = assertThrows(EmployeeNotFoundException.class,
				() -> employeeService.getEmployeeById(99L));

		assertEquals("Employee not found with id 99", ex.getMessage());
	}

	@Test
	void updateEmployee_updatesFieldsAndSavesExisting() {
		Employee existing = new Employee();
		existing.setId(1L);
		existing.setName("Old");
		existing.setSalary(1000);

		Address existingAddress = new Address();
		existingAddress.setCity("Old City");
		existing.setAddress(existingAddress);

		List<com.bharath.entites.Task> oldTasks = new ArrayList<>();
		existing.setTasks(oldTasks);

		List<com.bharath.entites.Project> oldProjects = new ArrayList<>();
		existing.setProjects(oldProjects);

		Department newDept = new Department();
		newDept.setDeptName("Engineering");

		Address newAddress = new Address();
		newAddress.setCity("New City");

		List<com.bharath.entites.Task> newTasks = new ArrayList<>();
		newTasks.add(new com.bharath.entites.Task());

		List<com.bharath.entites.Project> newProjects = new ArrayList<>();
		newProjects.add(new com.bharath.entites.Project());

		Employee updateRequest = new Employee();
		updateRequest.setName("Alice Updated");
		updateRequest.setSalary(60000);
		updateRequest.setAddress(newAddress);
		updateRequest.setDepartment(newDept);
		updateRequest.setTasks(newTasks);
		updateRequest.setProjects(newProjects);

		when(employeeRepository.findById(1L)).thenReturn(Optional.of(existing));
		when(employeeRepository.save(existing)).thenReturn(existing);

		Employee result = employeeService.updateEmployee(1L, updateRequest);

		assertSame(existing, result);
		assertEquals("Alice Updated", existing.getName());
		assertEquals(60000, existing.getSalary());
		assertEquals("New City", existing.getAddress().getCity());
		assertEquals("Engineering", existing.getDepartment().getDeptName());
		assertSame(newTasks, existing.getTasks());
		assertSame(newProjects, existing.getProjects());
		verify(employeeRepository).save(existing);
	}

	@Test
	void updateEmployee_whenNotFound_throwsEmployeeNotFoundException() {
		when(employeeRepository.findById(99L)).thenReturn(Optional.empty());

		EmployeeNotFoundException ex = assertThrows(EmployeeNotFoundException.class,
				() -> employeeService.updateEmployee(99L, new Employee()));

		assertEquals("Employee not found with id 99", ex.getMessage());
	}

	@Test
	void deleteEmployee_deletesWhenExists() {
		when(employeeRepository.existsById(1L)).thenReturn(true);
		doNothing().when(employeeRepository).deleteById(1L);

		employeeService.deleteEmployee(1L);

		verify(employeeRepository).existsById(1L);
		verify(employeeRepository).deleteById(1L);
	}

	@Test
	void deleteEmployee_whenNotFound_throwsEmployeeNotFoundException() {
		when(employeeRepository.existsById(1L)).thenReturn(false);

		EmployeeNotFoundException ex = assertThrows(EmployeeNotFoundException.class,
				() -> employeeService.deleteEmployee(1L));

		assertEquals("Employee not found with id 1", ex.getMessage());
		verify(employeeRepository, never()).deleteById(1L);
	}
}

