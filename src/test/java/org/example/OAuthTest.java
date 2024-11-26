package org.example;

import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import pojo.GetCourse;
import pojo.WebAutomation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;

public class OAuthTest {

    public static void main(String[] args) {

        String response = given().formParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .formParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
                .formParams("grant_type","client_credentials")
                .formParams("scope","trust")
                .when().log().all()
                .post("https://rahulshettyacademy.com/oauthapi/oauth2/resourceOwner/token").asString();

        JsonPath jsonPath = new JsonPath(response);
        String accessToken = jsonPath.getString("access_token");

        System.out.println(accessToken);

        GetCourse gc = given().queryParam("access_token",accessToken)
                .when().log().all().get("https://rahulshettyacademy.com/oauthapi/getCourseDetails").as(GetCourse.class);

        System.out.println(gc.getLinkedIn());
        System.out.println(gc.getInstructor());

        //print all the courseTitles names in webAutomation
        String[] courseTitles = {"Selenium Webdriver Java","Cypress","Protractor"};
        ArrayList<String> a = new ArrayList<String>();
        List<WebAutomation> webAutomationCourses = gc.getCourses().getWebAutomation();

        for(int i=0;i<webAutomationCourses.size();i++){
            a.add(webAutomationCourses.get(i).getCourseTitle());
        }

        List<String> expectedList = Arrays.asList(courseTitles);

        Assert.assertTrue(a.equals(expectedList));



    }
}
