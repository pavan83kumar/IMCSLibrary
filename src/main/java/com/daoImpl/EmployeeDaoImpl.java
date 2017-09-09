package com.daoImpl;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.entities.Employee;

public class EmployeeDaoImpl {

	@Autowired
	private DataSource dataSourceInstance;

	// final static Logger logger = Logger.getLogger(EmployeeDaoImpl.class);

	public void loadEmployee(List<Employee> employee) throws SQLException {

		// Loading employee data from file to database.
		try (Connection con = dataSourceInstance.getConnection();
				Statement s = con.createStatement();
				PreparedStatement stmt = con.prepareStatement("insert into employee values(?,?,?,?,?,?)")) {
			// con.setAutoCommit(false);
			s.execute("truncate employee");

			for (Employee emp : employee) {
				stmt.setInt(1, emp.getNumber());
				stmt.setInt(2, emp.getDeptNumber());
				stmt.setDate(3, new Date(emp.getDoJ().getTime()));
				stmt.setDate(4, new Date(emp.getDoB().getTime()));
				stmt.setInt(5, emp.getSalary());
				stmt.setInt(6, emp.getSalGrade());
				stmt.executeUpdate();

			}
		}

	}

	public void loadEmployeeWithOutId(Employee employee)  {

		try (Connection con = dataSourceInstance.getConnection();
				Statement s = con.createStatement();
				PreparedStatement stmt = con.prepareStatement("insert into employee values(?,?,?,?,?,?)")) {
			stmt.setInt(1, generateId());
			stmt.setInt(2, employee.getDeptNumber());
			stmt.setDate(3, new Date(employee.getDoJ().getTime()));
			stmt.setDate(4, new Date(employee.getDoB().getTime()));
			stmt.setInt(5, employee.getSalary());
			stmt.setInt(6, employee.getSalGrade());
			stmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private int generateId() {
		final String sql = "SELECT MAX(empNo) FROM employee";
		ResultSet rs = null;
		int id = 0;
		try (Connection con = dataSourceInstance.getConnection(); PreparedStatement st = con.prepareStatement(sql)) {
			rs = st.executeQuery(sql);
			while (rs.next()) {
				id = rs.getInt(1);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return id + 1;
	}

	public Employee getEmployeeById(int empNo) {

		Employee emp = null;

		try (Connection con = dataSourceInstance.getConnection();
				PreparedStatement stmt = con.prepareStatement("select * from employee where empNo = ?")) {
			stmt.setInt(1, empNo);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				emp = new Employee(empNo, rs.getInt(2), rs.getDate(3), rs.getDate(4), rs.getInt(5), rs.getShort(6));
			}

		} catch (SQLException e) {
			e.printStackTrace();

		}
		return emp;
	}

	public boolean deleteEmployee(int empNo) {

		try (Connection con = dataSourceInstance.getConnection();
				PreparedStatement stmt = con.prepareStatement("delete from employee where empNo = ?")) {
			stmt.setInt(1, empNo);
			int result= stmt.executeUpdate();
			if (result > 0) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}

	public List<Employee> getEmployees(int deptNo, String type) throws SQLException {

		// logger.info("Loading the employees based on the deptNo");
		List<Employee> empList = new ArrayList<Employee>();

		try (Connection con = dataSourceInstance.getConnection();
				PreparedStatement stmt = con
						.prepareStatement("select * from employee where deptNo = ? order by " + type)) {
			stmt.setInt(1, deptNo);

			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				empList.add(new Employee(rs.getInt(1), rs.getInt(2), rs.getDate(3), rs.getDate(4), rs.getInt(5),
						rs.getInt(6)));
			}
		}
		return empList;

	}

	public boolean updateEmployee(int empNo, Employee emp) {
		try (Connection con = dataSourceInstance.getConnection();
				PreparedStatement stmt = con.prepareStatement(
						"update employee set deptNo=?,DoB=?,DoJ=?,salary=?,salGrade=? where empNo=?")) {
			stmt.setInt(1, emp.getDeptNumber());
			long ms = emp.getDoB().getTime();
			java.sql.Date sqlDob = new java.sql.Date(ms);
			java.sql.Date sqlDoj = new java.sql.Date(ms);
			stmt.setDate(2, sqlDob);
			stmt.setDate(3, sqlDoj);
			stmt.setInt(4, emp.getSalary());
			stmt.setInt(5, emp.getSalGrade());
			stmt.setInt(6, empNo);

			int result = stmt.executeUpdate();
			if (result > 0) {
				return true;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;

	}

	public List<Employee> getEmployeesByDeptId(int deptId) {
		List<Employee> empList = new ArrayList<>();
		try (Connection con = dataSourceInstance.getConnection();
				PreparedStatement stmt = con.prepareStatement("select * from employee where deptNo = ?")) {
			stmt.setInt(1, deptId);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				empList.add(
						new Employee(rs.getInt(1), deptId, rs.getDate(3), rs.getDate(4), rs.getInt(5), rs.getInt(6)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return empList;

	}

	public DataSource getDataSourceInstance() {
		return dataSourceInstance;
	}

	public void setDataSourceInstance(DataSource dataSourceInstance) {
		this.dataSourceInstance = dataSourceInstance;
	}

}
