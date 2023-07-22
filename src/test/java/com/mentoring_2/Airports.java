package com.mentoring_2;

import com.bases.Airport_base;
import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import utils.API_Calls;
import utils.JSON_EDITOR;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static endpoints.Endpoints.*;
import static locations.JSON_Locations.airPortIDS;
import static utils.API_Calls.POST;
import static utils.JSON_EDITOR.JSON_EDIT;

public class Airports extends Airport_base {


    @Test
    public void validateAirportDistance() throws InterruptedException {

        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = BASE_PATH;

        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get()
                .then().log().body()
                .statusCode(200)
                .extract().response();

        Map<String, Object> parsed = response.as(new TypeRef<Map<String, Object>>() {
        });

        List<Map<String, Object>> data = (List<Map<String, Object>>) parsed.get("data");
        
        List<String> id = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> map = data.get(i);
            id.add((String) map.get("id"));
        }

        // BASE_PATH_DISTANCE

        Map<String, Object> info = new HashMap<>();
        Double max = 0.0;

        for (int i = 0; i < id.size(); i++) {

            for (int j = 0; j < id.size(); j++) {
                Thread.sleep(500);
                RestAssured.baseURI = BASE_URL;
                RestAssured.basePath = BASE_PATH_DISTANCE;
                JSON_EDIT(airPortIDS,id.get(i), id.get(j) );

                response = RestAssured.given().contentType(ContentType.JSON)
                        .when()
                        .body(airPortIDS)
                        .post()
                        .then()
                        .statusCode(200)
                        .extract().response();
              parsed = response.as(new TypeRef<Map<String, Object>>() {
              });

             Map<String, Object> data1 = (Map<String, Object>) parsed.get("data");

            Map<String, Object> attributes = (Map<String, Object>) data1.get("attributes");

            Double actualMiles = (Double) attributes.get("miles");
            info.put((String) data1.get("id"), actualMiles);

            if (actualMiles > max){
                max = actualMiles;
            }
            }

        }



        Map<String, Object> longestDistance = new HashMap<>();

        for (Map.Entry distance:info.entrySet()) {

            longestDistance.put((String) distance.getKey(), distance.getValue());
        }

        System.out.println(longestDistance);
    }

    @Test
    public void valdiateORDDistance(){
        RestAssured.baseURI = BASE_URL;
        RestAssured.basePath = BASE_PATH;

        Response response = RestAssured.given().accept(ContentType.JSON)
                .when().get()
                .then().log().body()
                .statusCode(200)
                .extract().response();

        Map<String, Object> parsed = response.as(new TypeRef<Map<String, Object>>() {
        });

        List<Map<String, Object>> data = (List<Map<String, Object>>) parsed.get("data");

        List<String> id = new ArrayList<>();

        for (int i = 0; i < data.size(); i++) {
            Map<String, Object> map = data.get(i);
            id.add((String) map.get("id"));
        }

        Map<String, Object> info = new HashMap<>();

        for (int i = 0; i < id.size(); i++) {
            RestAssured.baseURI = BASE_URL;
            RestAssured.basePath = BASE_PATH_DISTANCE;
            JSON_EDIT(airPortIDS,"ORD", id.get(i));

            response = POST(airPortIDS);
            parsed = response.as(new TypeRef<Map<String, Object>>() {
            });

            Map<String, Object> data1 = (Map<String, Object>) parsed.get("data");

            Map<String, Object> attributes = (Map<String, Object>) data1.get("attributes");

            Double actualMiles = (Double) attributes.get("miles");
            info.put((String) data1.get("id"), actualMiles);



        }
        System.out.println(info);

    }


}
