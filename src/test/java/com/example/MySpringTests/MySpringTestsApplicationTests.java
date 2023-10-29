package com.example.MySpringTests;

import com.example.MySpringTests.Model.Employee;
import com.example.MySpringTests.api.X_clients_service_Impl_OkHTTP;
import com.example.MySpringTests.Model.Company;
import com.example.MySpringTests.db.X_clients_repository_Impl_JDBC;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class MySpringTestsApplicationTests {
	@Autowired
	private X_clients_service_Impl_OkHTTP xClient;
	@Autowired
	private X_clients_repository_Impl_JDBC xClientDB;

	@Test
	@DisplayName("Проверить, что список компаний фильтруется по параметру active")
	public void Test1() throws IOException, SQLException {
//		запрашиваем список всех компаний
		List<Company> companyList=xClient.getAllCompanyList();
		List<Company> companyListFromDB=xClientDB.getAllCompanyList();
		assertEquals(companyListFromDB.size(),companyList.size());
//		запрашиваем список активных компаний
		List<Company> activeCopmanyList = xClient.getActiveCopmanyList();
		List<Company> activeCompanyListFromDB=xClientDB.getActiveCompanyList();
		assertEquals(activeCompanyListFromDB.size(),activeCopmanyList.size());
	}
	@Test
	@DisplayName("Проверить создание сотрудника в несуществующей компании")
	public void Test2() throws SQLException, IOException {
//		добавляем новую компанию в БД, получаем ее id
		int id= xClientDB.addCompany();
//		удаляем новую компанию в БД по ее id
		xClientDB.deleteCompany(id);
//		добавляем нового сотрудника в удаленную компанию
		List<Employee> employeeListBefore=xClientDB.getAllEmployees();
		int employeeId = xClient.addEmployee(id);
		List<Employee> employeeListAfter=xClientDB.getAllEmployees();
		assertEquals(employeeListBefore.size(),employeeListAfter.size());
	}
	@Test
	@DisplayName("Проверить, что неактивный сотрудник не отображается в списке")
	public void Test3() throws SQLException, IOException {
//		добавляем новую компанию в БД, получаем ее id
		int newCompanyId= xClientDB.addCompany();
//		добавляем нового сотрудника в новую компанию через БД
		int employeeId = xClientDB.addEmployee(newCompanyId);
//		изменяем поле is_active на false через БД
		xClientDB.changeEmployeeIsActiveInfo(employeeId);
//		запрашиваем этого неактивного сотрудника по id компании через api
		List<Employee> employeeByCompanyId = xClient.getEmployeeByCompanyId(newCompanyId);
		assertEquals(0,employeeByCompanyId.size(), "Неактивный сотрудник отображается в списке");
	}
	@Test
	@DisplayName("Проверить, что у удаленной компании проставляется в БД поле deletedAt")
	public void Test4() throws SQLException, IOException {
//		добавляем новую компанию в БД, получаем ее id
		int newCompanyId= xClientDB.addCompany();
//		удаляем эту компанию через api
		xClient.deleteCompany(newCompanyId);
//		запрашиваем удаленную компанию из БД
		Company companyById = xClientDB.getCompanyById(newCompanyId);
//		проверяем что поле deleted_at не равно null
		assertTrue(companyById.getDeletedAt()!=null);
	}
}
