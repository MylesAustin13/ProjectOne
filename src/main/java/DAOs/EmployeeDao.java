package DAOs;

import Entities.Employee;

import java.util.List;

public interface EmployeeDao {

    void addEmployee(Employee e);
    Employee getEmployeeByUsername(String uname);
    Employee getEmployeeByID(int empl_id);
    List<Employee> getAllEmployees();
}
