package com.daoImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import com.entities.Department;

public class DepartmentDaoImpl {

	@Autowired
	private DataSource dataSourceInstance;

	public DataSource getDataSourceInstance() {
		return dataSourceInstance;
	}

	public void setDataSourceInstance(DataSource dataSourceInstance) {
		this.dataSourceInstance = dataSourceInstance;
	}

	Department dept;

	public Department getDepartment(int deptNo) {
		try (Connection con = dataSourceInstance.getConnection();
				PreparedStatement stmt = con.prepareStatement("select * from department where deptNo = ?")) {
			stmt.setInt(1, deptNo);
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				dept = new Department(deptNo, rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return dept;

	}
	
	public List<Department> getAllDepartments(){
		List<Department> deptList = new ArrayList<>();
		try (Connection con = dataSourceInstance.getConnection();
				PreparedStatement stmt = con.prepareStatement("select * from department")) {
			ResultSet rs = stmt.executeQuery();
			while(rs.next()){
				deptList.add(new Department(rs.getInt(1),rs.getString(2)));
			}
		} catch (SQLException e) {
		e.printStackTrace();
	}
		return deptList;
	}
}
