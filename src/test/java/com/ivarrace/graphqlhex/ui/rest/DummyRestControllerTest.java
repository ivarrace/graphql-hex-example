package com.ivarrace.graphqlhex.ui.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivarrace.graphqlhex.application.command.dummy.SaveCommand;
import com.ivarrace.graphqlhex.domain.model.Dummy;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class DummyRestControllerTest {

    @Autowired private ObjectMapper mapper;
    @Autowired private TestRestTemplate restTemplete;
    @LocalServerPort private Integer port;

    @Test
    void findALl_default_pagination_values() throws Exception {
        final HttpEntity<String> entity = new HttpEntity<String>(null, null);
        final ResponseEntity<String> responseEntity = this.restTemplete.exchange("http://localhost:" + port + "/rest/dummy", HttpMethod.GET, entity, String.class);
        final JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        final List<Dummy> dummies = mapper.readValue(jsonObject.getString("content"), mapper.getTypeFactory().constructParametricType(List.class, Dummy.class));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(dummies);
        assertFalse(dummies.isEmpty());
        assertEquals(25, jsonObject.get("size"));
        assertEquals(0, jsonObject.get("number"));
        assertTrue(jsonObject.getString("sort").contains("\"unsorted\":true"));
    }

    @Test
    void findALl_sorted() throws Exception {
        final HttpEntity<String> entity = new HttpEntity<String>(null, null);
        final ResponseEntity<String> responseEntity = this.restTemplete.exchange("http://localhost:" + port + "/rest/dummy?orderBy=name&asc=true", HttpMethod.GET, entity, String.class);
        final JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        final List<Dummy> dummies = mapper.readValue(jsonObject.getString("content"), mapper.getTypeFactory().constructParametricType(List.class, Dummy.class));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(dummies);
        assertFalse(dummies.isEmpty());
        assertEquals(25, jsonObject.get("size"));
        assertEquals(0, jsonObject.get("number"));
        assertTrue(jsonObject.getString("sort").contains("\"sorted\":true"));
    }

    @Test
    void findALl_paginated() throws Exception {
        final HttpEntity<String> entity = new HttpEntity<String>(null, null);
        final ResponseEntity<String> responseEntity = this.restTemplete.exchange("http://localhost:" + port + "/rest/dummy?pageNumber=0&pageSize=10000", HttpMethod.GET, entity, String.class);
        final JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        final List<Dummy> dummies = mapper.readValue(jsonObject.getString("content"), mapper.getTypeFactory().constructParametricType(List.class, Dummy.class));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(dummies);
        assertFalse(dummies.isEmpty());
        assertEquals(10000, jsonObject.get("size"));
        assertEquals(0, jsonObject.get("number"));
    }

    @Test
    void findALl_empty_page() throws Exception {
        final HttpEntity<String> entity = new HttpEntity<String>(null, null);
        final ResponseEntity<String> responseEntity = this.restTemplete.exchange("http://localhost:" + port + "/rest/dummy?pageNumber=100&pageSize=10000", HttpMethod.GET, entity, String.class);
        final JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        final List<Dummy> dummies = mapper.readValue(jsonObject.getString("content"), mapper.getTypeFactory().constructParametricType(List.class, Dummy.class));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(dummies);
        assertTrue(dummies.isEmpty());
        assertEquals(100, jsonObject.get("number"));
    }

    @Test
    void findALl_filter_name() throws Exception {
        final HttpEntity<String> entity = new HttpEntity<String>(null, null);
        final ResponseEntity<String> responseEntity = this.restTemplete.exchange("http://localhost:" + port + "/rest/dummy?dummy.name=test-dummy", HttpMethod.GET, entity, String.class);
        final JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        final List<Dummy> dummies = mapper.readValue(jsonObject.getString("content"), mapper.getTypeFactory().constructParametricType(List.class, Dummy.class));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(dummies);
        assertFalse(dummies.isEmpty());
        assertEquals(1, dummies.size());
    }

    @Test
    void findALl_filter_name_empty() throws Exception {
        final HttpEntity<String> entity = new HttpEntity<String>(null, null);
        final ResponseEntity<String> responseEntity = this.restTemplete.exchange("http://localhost:" + port + "/rest/dummy?dummy.name=111", HttpMethod.GET, entity, String.class);
        final JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        final List<Dummy> dummies = mapper.readValue(jsonObject.getString("content"), mapper.getTypeFactory().constructParametricType(List.class, Dummy.class));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(dummies);
        assertTrue(dummies.isEmpty());
    }

    @Test
    void save() throws Exception {
        final SaveCommand command = new SaveCommand();
        command.setName("My First Name");

        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        final HttpEntity<String> entity = new HttpEntity<String>(mapper.writeValueAsString(command), headers);
        final ResponseEntity<String> responseEntity = this.restTemplete.exchange("http://localhost:" + port + "/rest/dummy", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void save_bad_request() throws Exception {
        final SaveCommand command = new SaveCommand();
        command.setName("<html>test</html>");

        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        final HttpEntity<String> httpEntity = new HttpEntity<String>(mapper.writeValueAsString(command), headers);
        final ResponseEntity<String> responseEntity = this.restTemplete.exchange("http://localhost:" + port + "/rest/dummy", HttpMethod.POST, httpEntity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

    @Test
    void findOne() throws Exception {
        final String url = "http://localhost:" + port + "/rest/dummy/test-dummy";
        final HttpEntity<String> entity = new HttpEntity<String>(null, null);
        final ResponseEntity<String> responseEntity = this.restTemplete.exchange(url, HttpMethod.GET, entity, String.class);
        final JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        final List<Dummy> dummies = mapper.readValue(jsonObject.getString("content"), mapper.getTypeFactory().constructParametricType(List.class, Dummy.class));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(dummies);
        assertFalse(dummies.isEmpty());
        assertEquals(25, jsonObject.get("size"));
        assertEquals(0, jsonObject.get("number"));
        assertTrue(jsonObject.getString("sort").contains("\"unsorted\":true"));
    }

    @Test
    void findOne_not_found() throws Exception {
        final HttpEntity<String> entity = new HttpEntity<String>(null, null);
        final ResponseEntity<String> responseEntity = this.restTemplete.exchange("http://localhost:" + port + "/rest/dummy", HttpMethod.GET, entity, String.class);
        final JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        final List<Dummy> dummies = mapper.readValue(jsonObject.getString("content"), mapper.getTypeFactory().constructParametricType(List.class, Dummy.class));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(dummies);
        assertFalse(dummies.isEmpty());
        assertEquals(25, jsonObject.get("size"));
        assertEquals(0, jsonObject.get("number"));
        assertTrue(jsonObject.getString("sort").contains("\"unsorted\":true"));
    }

}
