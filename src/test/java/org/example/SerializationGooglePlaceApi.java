package org.example;

import files.PayLoad;
import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import pojo.AddPlace;
import pojo.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

import static org.hamcrest.Matchers.equalTo;

public class SerializationGooglePlaceApi {

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


        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key","qaclick123")
                .body(p).when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200).extract().response().asString();


        System.out.println(response);














    }
}
