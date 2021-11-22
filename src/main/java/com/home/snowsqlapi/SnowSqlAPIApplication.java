package com.home.snowsqlapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@SpringBootApplication
public class SnowSqlAPIApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnowSqlAPIApplication.class, args);

    }
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder.build();
    }
    @Bean
    public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.add("User-Agent", "myApplicationName/1.0");
        headers.add("Authorization","Bearer ver:1-hint:9557381129-ETMsDgAAAX1DVBzJABRBRVMvQ0JDL1BLQ1M1UGFkZGluZwEAABAAEAB4gWMj4ERoM9MryYVtsNIAAABQXY1kRUHi8bIoAH+C10OGOBmUIloX5t3nksL4nFCykbhvFnpJ2MGUkJNo/HXogR/qTnMrkeSLnIbbKIkrICtqCMr/aXifjZbeX+Gb8wj8tRAAFKeYIcXpAjSM3s0mA9vRXBttYPGZ");
        headers.add("X-Snowflake-Authorization-Token-Type", "OAUTH");

        HttpEntity<RequestPayload> entity = new HttpEntity<>(prepareRequestPayload(), headers);

        ResponseEntity<String> str = restTemplate.exchange("https://og87845.ap-south-1.snowflakecomputing.com/api/statements", HttpMethod.POST, entity, String.class);
        System.out.println(str);
        /*return args -> {
            Quote quote = restTemplate.getForObject(
                    "https://quoters.apps.pcfone.io/api/random", Quote.class);
            log.info(quote.toString());
        };*/
        return null;
    }

    private RequestPayload prepareRequestPayload() {
        RequestPayload payload = new RequestPayload();
        payload.setStatement("select * from NATION");
        payload.setTimeout(60);
        ResultSetMetaData res = new ResultSetMetaData();
        res.format = "jsonv2";
        payload.setResultSetMetaData(res);
        payload.setDatabase("SNOWFLAKE_SAMPLE_DATA");
        payload.setSchema("TPCH_SF1");
        payload.setWarehouse("COMPUTE_WH");
        payload.setRole("SYSADMIN");

        return payload;
    }
}
