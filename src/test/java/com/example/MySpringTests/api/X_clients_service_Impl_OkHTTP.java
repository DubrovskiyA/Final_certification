package com.example.MySpringTests.api;

import com.example.MySpringTests.Model.Company;
import com.example.MySpringTests.Model.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
@Component
public class X_clients_service_Impl_OkHTTP implements X_clients_service {
    @Value("${base_url}")
    private String BASE_URL;
    @Value("${company_path1}")
    private String COMPANY_PATH1;
    @Value("${company_path2}")
    private String COMPANY_PATH2;
    @Value("${employee_path}")
    private String EMPLOYEE_PATH;
    @Autowired
    private OkHTTP_Client client;
    @Autowired
    private Mapper mapper;
    @Autowired
    private X_clients_auth_Impl_OkHTTP auth;
    private MediaType APPLICATION_JSON=MediaType.parse("application/json; charset=utf-8");
    private final String company_name="AAAAAAA";
    private final String company_desc="bbbbbbb";
    private final String firstName="Greg";
    private final String lastName="Bill";
    private final String phone="9098089988";

    @Override
    public List<Company> getAllCompanyList() throws IOException {
        HttpUrl url=HttpUrl
                .parse(BASE_URL)
                .newBuilder()
                .addPathSegment(COMPANY_PATH1)
                .build();
        Request request=new Request.Builder().get().url(url).build();
        Response response=client.getClient().newCall(request).execute();
        List<Company> list=mapper.getMapper().readValue(response.body().string(), new TypeReference<List<Company>>() {});
        return list;
    }

    @Override
    public List<Company> getActiveCopmanyList() throws IOException {
        HttpUrl url=HttpUrl.parse(BASE_URL)
                .newBuilder()
                .addPathSegment(COMPANY_PATH1)
                .addQueryParameter("active","true").build();
        Request request=new Request.Builder().get().url(url).build();
        Response response=client.getClient().newCall(request).execute();
        List<Company> activeCompanyList=mapper.getMapper().readValue(response.body().string(),new TypeReference<List<Company>>() {});
        return activeCompanyList;
    }

    @Override
    public int addCompany() throws IOException {
//        авторизация
        String userToken = auth.auth().getUserToken();
//        добавление новой компании авторизованным пользователем (админом)
        HttpUrl url=HttpUrl.parse(BASE_URL).newBuilder().addPathSegment(COMPANY_PATH1).build();
        RequestBody body=RequestBody
                .create("{\"name\":\""+company_name+"\",\"description\":\""+company_desc+"\"}",APPLICATION_JSON);
        Request request=new Request.Builder().post(body).url(url).addHeader("x-client-token",userToken).build();
        Response response=client.getClient().newCall(request).execute();
        Company company=mapper.getMapper().readValue(response.body().string(), Company.class);
        return company.getId();
    }

    @Override
    public int addEmployee(int companyId) throws IOException {
//        авторизация
        String userToken = auth.auth().getUserToken();
//        добавление нового сотрудника авторизованным пользователем (админом)
        HttpUrl url=HttpUrl.parse(BASE_URL).newBuilder().addPathSegment(EMPLOYEE_PATH).build();
        RequestBody body=RequestBody
                .create("{\"firstName\":\""+firstName+"\",\"lastName\":\""+lastName+"\",\"companyId\":"+companyId+",\"phone\":\""+phone+"\"}",APPLICATION_JSON);
        Request request=new Request.Builder().post(body).url(url).addHeader("x-client-token", userToken).build();
        Response response=client.getClient().newCall(request).execute();
        Employee employee=mapper.getMapper().readValue(response.body().string(), Employee.class);
        return employee.getId();
    }

    @Override
    public List<Employee> getEmployeeList() {
        return null;
    }

    @Override
    public List<Employee> getEmployeeByCompanyId(int companyId) throws IOException {
        HttpUrl url=HttpUrl
                .parse(BASE_URL)
                .newBuilder()
                .addPathSegment(EMPLOYEE_PATH)
                .addQueryParameter("company", String.valueOf(companyId))
                .build();
        Request request=new Request.Builder().get().url(url).build();
        Response response=client.getClient().newCall(request).execute();
        List<Employee> list=mapper.getMapper().readValue(response.body().string(), new TypeReference<List<Employee>>() {});
        return list;
    }

    @Override
    public void deleteCompany(int companyId) throws IOException {
//        авторизация
        String userToken=auth.auth().getUserToken();
//        удаление компании
        HttpUrl url=HttpUrl
                .parse(BASE_URL)
                .newBuilder()
                .addPathSegment(COMPANY_PATH1)
                .addPathSegment(COMPANY_PATH2)
                .addPathSegment(String.valueOf(companyId))
                .build();
        Request request=new Request.Builder().get().url(url).addHeader("x-client-token",userToken).build();
        client.getClient().newCall(request).execute();
    }
}
