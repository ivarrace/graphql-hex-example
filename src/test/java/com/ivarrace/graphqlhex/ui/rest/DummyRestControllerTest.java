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
import org.springframework.http.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class DummyRestControllerTest {

    @Autowired private ObjectMapper mapper;
    @Autowired private TestRestTemplate restTemplete;
    @LocalServerPort private Integer port;

    @Test
    public void testIfListCommandReturnsOk() throws Exception {
        final HttpEntity<String> entity = new HttpEntity<String>(null, null);
        final ResponseEntity<String> responseEntity = this.restTemplete.exchange("http://localhost:" + port + "/rest/dummy?pageNumber=0&pageSize=10000&orderBy=name&asc=true", HttpMethod.GET, entity, String.class);
        final JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        final List<Dummy> dummies = mapper.readValue(jsonObject.getString("content"), mapper.getTypeFactory().constructParametricType(List.class, Dummy.class));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(dummies);
        assertFalse(dummies.isEmpty());
    }

    @Test
    public void testIfListCommandReturnsOk_par() throws Exception {
        final HttpEntity<String> entity = new HttpEntity<String>(null, null);
        final ResponseEntity<String> responseEntity = this.restTemplete.exchange("http://localhost:" + port + "/rest/dummy?pageNumber=0&pageSize=10000&asc=true", HttpMethod.GET, entity, String.class);
        final JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        final List<Dummy> dummies = mapper.readValue(jsonObject.getString("content"), mapper.getTypeFactory().constructParametricType(List.class, Dummy.class));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(dummies);
        assertFalse(dummies.isEmpty());
    }

    @Test
    public void testIfListCommandReturnsOk2() throws Exception {
        final HttpEntity<String> entity = new HttpEntity<String>(null, null);
        final ResponseEntity<String> responseEntity = this.restTemplete.exchange("http://localhost:" + port + "/rest/dummy?pageNumber=0&pageSize=10000&orderBy=name&asc=false", HttpMethod.GET, entity, String.class);
        final JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        final List<Dummy> dummies = mapper.readValue(jsonObject.getString("content"), mapper.getTypeFactory().constructParametricType(List.class, Dummy.class));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(dummies);
        assertFalse(dummies.isEmpty());
    }

    @Test
    public void testIfListCommandReturnsOk3() throws Exception {
        final HttpEntity<String> entity = new HttpEntity<String>(null, null);
        final ResponseEntity<String> responseEntity = this.restTemplete.exchange("http://localhost:" + port + "/rest/dummy?pageNumber=0&pageSize=10000&orderBy=name&asc=true&dummy.name=111", HttpMethod.GET, entity, String.class);
        final JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        final List<Dummy> dummies = mapper.readValue(jsonObject.getString("content"), mapper.getTypeFactory().constructParametricType(List.class, Dummy.class));
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(dummies);
        assertTrue(dummies.isEmpty());
    }

    @Test
    public void testIfSaveCommandIsOk() throws Exception {
        final SaveCommand command = new SaveCommand();
        command.setName("My First Name");

        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        final HttpEntity<String> entity = new HttpEntity<String>(mapper.writeValueAsString(command), headers);
        final ResponseEntity<String> responseEntity = this.restTemplete.exchange("http://localhost:" + port + "/rest/dummy", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    public void testIfSaveCommandReturnsBadRequest() throws Exception {
        final SaveCommand command = new SaveCommand();
        command.setName("<html>test</html>");

        final HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);

        final HttpEntity<String> httpEntity = new HttpEntity<String>(mapper.writeValueAsString(command), headers);
        final ResponseEntity<String> responseEntity = this.restTemplete.exchange("http://localhost:" + port + "/rest/dummy", HttpMethod.POST, httpEntity, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }

}
