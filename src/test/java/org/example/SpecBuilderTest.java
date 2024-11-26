package org.example;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlace;
import pojo.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SpecBuilderTest {

    public static void main(String[] args) {
        // serialization - converting java Object to request json payload
        AddPlace p = new AddPlace();
        p.setAccuracy(50);
        p.setName("Ramesh Kondepati");
        p.setPhone_number("8272772828");
        p.setAddress("55252 dhhdyd st");
        p.setWebsite("http://google.com");
        p.setLanguage("Telugu-IN");
        List<String> myList= new ArrayList<String>();
        myList.add("shoePark");
        myList.add("shop");
        p.setTypes(myList);
        Location l = new Location();
        l.setLat(-43.3536);
        l.setLng(23.4564);
        p.setLocation(l);

        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key","qaclick123")
                .setContentType(ContentType.JSON)
                .build();

        ResponseSpecification resspec = new ResponseSpecBuilder().expectStatusCode(200)
                                            .expectContentType(ContentType.JSON).build();

        RequestSpecification res = given().spec(req).body(p);

        Response response = res.when().post("/maps/api/place/add/json")
                            .then().spec(resspec)
                            .extract().response();

        String responseString = response.asString();

        System.out.println(responseString);














    }
}
