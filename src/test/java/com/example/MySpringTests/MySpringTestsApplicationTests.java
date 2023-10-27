package com.example.MySpringTests;

import com.example.MySpringTests.api.X_clients_Imp_OkHTTP;
import com.example.MySpringTests.Model.Company;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class MySpringTestsApplicationTests {
	@Autowired
	private X_clients_Imp_OkHTTP xClient;

	@Test
	@DisplayName("Проверить, что список компаний фильтруется по параметру active")
	public void Test() throws IOException {
		List<Company> activeCopmanyList = xClient.getActiveCopmanyList();
		for (Company x:activeCopmanyList) {
			System.out.println(x);
		}
	}

}
