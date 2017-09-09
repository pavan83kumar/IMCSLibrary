import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.configurations.DAOConfig;
import com.daoImpl.EmployeeDaoImpl;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DAOConfig.class)
public class EmployeeDaoTest {

	@Autowired
	EmployeeDaoImpl instance; 
	
	@Test
	public void getData(){
		assert(instance.getEmployeeById(104)!=null);
	//System.out.println(instance.getEmployeeById(108).toString());
	}
	
	
	
}
