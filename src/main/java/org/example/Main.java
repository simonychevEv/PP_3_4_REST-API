package org.example;

import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;

public class Main {

    private static String URL = "http://94.198.50.185:7081/api/users";

    private static final RestTemplate restTemplate = new RestTemplate();

    public static void main(String[] args) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> requestEntity = new HttpEntity<>(headers);

        ResponseEntity<List> responseEntity = getAllUsers(requestEntity);

        headers.set("Cookie", responseEntity.getHeaders().get("Set-Cookie")
                .stream().collect(Collectors.joining(";")));

        User user1 = new User();
        user1.setId(3L);
        user1.setName("James");
        user1.setLastName("Brown");
        user1.setAge((byte) 24);

        HttpEntity<User> entity = new HttpEntity<>(user1, headers);
        createUser(entity);

        user1.setName("Thomas");
        user1.setLastName("Shelby");
        entity = new HttpEntity<>(user1, headers);
        updateUser(entity);

        deleteUser(entity);
    }

    private static ResponseEntity<List> getAllUsers(HttpEntity<Object> requestEntity) {
        ResponseEntity<List> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, requestEntity, List.class);
        return responseEntity;
    }
    private static void createUser(HttpEntity<User> entity) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);
        System.out.println(responseEntity.getBody());
    }

    private static void updateUser(HttpEntity<User> entity) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class);
        System.out.println(responseEntity.getBody());
    }
    private static void deleteUser(HttpEntity<User> entity) {
        ResponseEntity<String> responseEntity = restTemplate.exchange(URL + "/3", HttpMethod.DELETE, entity, String.class);
        System.out.println(responseEntity.getBody());
    }
}