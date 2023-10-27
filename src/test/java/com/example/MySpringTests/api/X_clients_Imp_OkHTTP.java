package com.example.MySpringTests.api;

import api.LogInterceptor;
import com.example.MySpringTests.Model.Company;
import com.example.MySpringTests.Model.Employee;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
@Component
public class X_clients_Imp_OkHTTP implements X_clients {
    private final OkHttpClient client;
    @Value("${base_url}")
    private String BASE_URL;
    @Value("${company_path}")
    private String COMPANY_PATH;
    private ObjectMapper mapper;
    public X_clients_Imp_OkHTTP(){
        client=new OkHttpClient().newBuilder().addNetworkInterceptor(new LogInterceptor()).build();
        mapper=new ObjectMapper();
    }

    @Override
    public List<Company> getActiveCopmanyList() throws IOException {
        HttpUrl url=HttpUrl.parse(BASE_URL)
                .newBuilder()
                .addPathSegment(COMPANY_PATH)
                .addQueryParameter("active","true").build();
        Request request=new Request.Builder().get().url(url).build();
        Response response=client.newCall(request).execute();
        List<Company> activeCompanyList=mapper.readValue(response.body().string(),new TypeReference<List<Company>>() {});
        return activeCompanyList;
    }

    @Override
    public void createEmployee() {

    }

    @Override
    public List<Employee> getEmployeeList() {
        return null;
    }

    @Override
    public void deleteCompany() {

    }
}
