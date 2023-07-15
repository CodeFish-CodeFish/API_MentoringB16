package com.mentoring_1;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import org.junit.Assert;
import org.junit.Test;
import utils.API_Calls;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static utils.API_Calls.GET;

public class InterviewTask {

    private String expectedCapital = "Bishkek";
    private String actualCapital = "";

    // https://restcountries.com/v3.1/all

    @Test
    public void validateCapital() {

        RestAssured.baseURI = "https://restcountries.com";
        RestAssured.basePath = "v3.1/all";


        Response response = RestAssured.given().accept("application/json")
                .when()
                .get()
                .then()
                .statusCode(200)
                .extract().response();

        List<Map<String, Object>> parsed = response.as(new TypeRef<List<Map<String, Object>>>() {
        });
        List<String> capital = new ArrayList<>();
        for (int i = 0; i < parsed.size(); i++) {
            Map<String, Object> outerMap = parsed.get(i);
            Map<String, Object> innerMap = (Map<String, Object>) outerMap.get("name");
            if (innerMap.get("common").equals("Kyrgyzstan")) {
                capital = (List<String>) outerMap.get("capital");
                actualCapital = capital.get(0);

                break;
            }


        }

        System.out.println(capital);
        System.out.println(actualCapital);
        System.out.println(expectedCapital);
        Assert.assertEquals(expectedCapital, actualCapital);

    }

    @Test
    public void validateBreakingBadQuotes() {

        //https://api.breakingbadquotes.xyz/v1/quotes
        RestAssured.baseURI = "https://api.breakingbadquotes.xyz";
        RestAssured.basePath = "v1/quotes";

        Response re = GET("/10");

        List<Map<String, Object>> parsed = re.as(new TypeRef<List<Map<String, Object>>>() {
        });

        for (int i = 0; i < parsed.size(); i++) {

            Map<String, Object> map = parsed.get(i);
            if (map.get("author").equals("Jesse Pinkman")) {
                System.out.println(map.get("quote"));


            }


        }


    }


}
