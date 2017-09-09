package com.configurations;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import com.daoImpl.DepartmentDaoImpl;
import com.daoImpl.EmployeeDaoImpl;

@Configuration

public class DAOConfig {

	/*
	 * <bean id="dataSource"
	 * class="org.springframework.jdbc.datasource.DriverManagerDataSource">
	 * 
	 * <property name="driverClassName" value="com.mysql.jdbc.Driver" />
	 * <property name="url" value="jdbc:mysql://localhost:3306/TestDB" />
	 * <property name="username" value="pankaj" /> <property name="password"
	 * value="pankaj123" /> </bean>
	 */
	@Bean
	public EmployeeDaoImpl getEmployeeDAO() throws SQLException {
		EmployeeDaoImpl employeeInst = new EmployeeDaoImpl();
		employeeInst.setDataSourceInstance(dataSource());
		return employeeInst;
	}
	
	@Bean
	public DepartmentDaoImpl getdepartmentDaoImpl() throws SQLException {
		DepartmentDaoImpl departmentInst = new DepartmentDaoImpl();
		departmentInst.setDataSourceInstance(dataSource());
		return departmentInst;
	}

	@Bean
	public DataSource dataSource() throws SQLException {
		DriverManagerDataSource ds = new DriverManagerDataSource();
		ds.setDriverClassName("com.mysql.jdbc.Driver");
		ds.setUsername("root");
		ds.setPassword("password");
		ds.setUrl("jdbc:mysql://localhost:3306/employeedb");
		return ds;
	}

}
