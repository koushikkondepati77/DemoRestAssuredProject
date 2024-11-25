package org.example;

import files.PayLoad;
import files.ReUsableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;

public class DynamicJson {

    @Test(dataProvider = "BooksData") //AddBook
    public void addBook(String isbn,String aisle){
        RestAssured.baseURI = "http://216.10.245.166";
        String response = given().header("Content-Type","application/json")
                .body(PayLoad.AddBook(isbn,aisle))
                .when().post("/Library/Addbook.php")
                .then().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js = ReUsableMethods.rawToJson(response);
        String id = js.get("ID");
        System.out.println(id);

    }

    @Test(dataProvider = "BooksData") //DeleteBook
    public void deleteBook(String isbn,String aisle){
        RestAssured.baseURI = "http://216.10.245.166";
        String response = given().header("Content-Type","application/json")
                .body(PayLoad.DeleteBook(isbn,aisle))
                .when().post("/Library/DeleteBook.php")
                .then().assertThat().statusCode(200)
                .extract().response().asString();

        JsonPath js = ReUsableMethods.rawToJson(response);
        String msg = js.get("msg");
        System.out.println(msg);
    }


    @DataProvider(name="BooksData")
    public Object[][] getData(){

        //array - collection of elements
        //multidimentional array - collection of arrays
        return new Object[][] {
                {"tqdwqs","5142"},
                {"gfhgf","7747"},
                {"kskks","8484"},
//                {"kxjxj","9696"},
//                {"kciudu","674655"},
//                {"jcjjd","87664"},
//                {"kxkxj","77565"},
//                {"hfgfh","8675"},
//                {"kdjdk","98742"},
//                {"vcgdf","43544"}
        };
    }
}
