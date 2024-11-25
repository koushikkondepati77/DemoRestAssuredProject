package org.example;

import files.PayLoad;
import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class Basics {

    public static void main(String[] args) {
        //VALIDATE IF ADD PLACE API IS WORKING AS EXPECTED

        //REST ASSURED WORKS ON THREE PRINCIPLES
        //given - take all input details - ex: queryParam, header
        //when - submit the api -ex: resourse and http method will always goes on when() method
        //then - validate the response

        //ANY REST ASSURED TEST SHOULD BE WRITTEN IN THESE 3 METHODS ONLY

        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String response = given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body(PayLoad.AddPlace()).when().post("maps/api/place/add/json")
                .then().assertThat().statusCode(200)
                .body("scope",equalTo("APP")).header("Server","Apache/2.4.52 (Ubuntu)")
                .extract().response().asString();

        System.out.println(response);

        JsonPath js = new JsonPath(response);
        String placeId = js.getString("place_id");

        //UPDATE PLACE WITH NEW ADDRESS
        String newAddress = "Newyork Times Square";

        given().log().all().queryParam("key","qaclick123").header("Content-Type","application/json")
                .body("{\n" +
                        "\"place_id\":\""+placeId+"\",\n" +
                        "\"address\":\""+newAddress+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}").when().put("maps/api/place/update/json")
                .then().log().all().body("msg",equalTo("Address successfully updated"));

        String getPlace = given().log().all().queryParam("key","qaclick123")
                .queryParam("place_id",""+placeId+"")
                .when().get("maps/api/place/get/json")
                .then().log().all().assertThat().statusCode(200).extract().response().asString();

        //JsonPath js1 = new JsonPath(getPlace);
        JsonPath js1 = ReUsableMethods.rawToJson(getPlace);
        String actualAddress = js1.getString("address");

        System.out.println(actualAddress);

        Assert.assertEquals(actualAddress,newAddress);












    }
}
