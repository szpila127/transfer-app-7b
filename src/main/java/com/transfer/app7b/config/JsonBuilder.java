package com.transfer.app7b.config;

import com.google.gson.Gson;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class JsonBuilder<T> {

    private T dto;

    public HttpEntity<String> prepareJson (T dto) {
        Gson gson = new Gson();
        String jsonContent = gson.toJson(dto);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> httpEntity = new HttpEntity<>(jsonContent,headers);
        return httpEntity;
    }
}
