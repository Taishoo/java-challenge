package jp.co.axa.apidemo;

import jp.co.axa.apidemo.controllers.EmployeeController;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiDemoApplicationTests {

	@Autowired
	private EmployeeController employeeController;

	@Test
	public void contextLoads() throws Exception {
		assertThat(employeeController).isNotNull();
	}

}
