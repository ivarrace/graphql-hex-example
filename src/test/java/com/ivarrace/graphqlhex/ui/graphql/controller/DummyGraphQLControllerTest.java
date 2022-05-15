package com.ivarrace.graphqlhex.ui.graphql.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ivarrace.graphqlhex.domain.model.Dummy;
import org.json.JSONArray;
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
class DummyGraphQLControllerTest {

    @Autowired private ObjectMapper mapper;
    @Autowired private TestRestTemplate restTemplete;
    @LocalServerPort private Integer port;

    @Test
    void findAll() throws Exception{
        String body = "{\"query\":\"query{\\n  findAll(pageNumber:0, pageSize:10000, orderBy: \\\"name\\\", asc: true){\\n    content{\\n      id,\\n      name\\n    },\\n    totalElements\\n  }\\n}\",\"variables\":null}";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        final HttpEntity<String> entity = new HttpEntity<>(body, headers);
        final ResponseEntity<String> responseEntity = this.restTemplete.exchange("http://localhost:" + port + "/graphql/dummy", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        final JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        final JSONObject dataJSONObject = jsonObject.getJSONObject("data");
        final JSONObject findAllJSONObject = dataJSONObject.getJSONObject("findAll");
        final List<Dummy> dummies = mapper.readValue(findAllJSONObject.getString("content"), mapper.getTypeFactory().constructParametricType(List.class, Dummy.class));
        final Integer totalElements = findAllJSONObject.getInt("totalElements");
        assertNotNull(dummies);
        assertFalse(dummies.isEmpty());
        assertTrue(totalElements > 0);
    }

    @Test
    void findAll_error() throws Exception{
        String body = "{\"query\":\"query{\\n  findAll(){\\n    content{\\n      id,\\n      name\\n    },\\n    totalElements\\n  }\\n}\",\"variables\":null}";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", MediaType.APPLICATION_JSON_VALUE);
        final HttpEntity<String> entity = new HttpEntity<>(body, headers);
        final ResponseEntity<String> responseEntity = this.restTemplete.exchange("http://localhost:" + port + "/graphql/dummy", HttpMethod.POST, entity, String.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        final JSONObject jsonObject = new JSONObject(responseEntity.getBody());
        final JSONArray errors = jsonObject.getJSONArray("errors");
        assertTrue(errors.length() > 0);
    }

}
