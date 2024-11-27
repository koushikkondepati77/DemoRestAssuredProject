package org.example;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import pojo.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.*;

public class ECommerce {

    //ECommerce Website End-To-End Flow
    //Login Api -> Create Product -> Purchase Order on Created Product -> Delete Order -> Delete Product
    //login api call -> https://rahulshettyacademy.com/api/ecom/auth/login
    //create product -> https://rahulshettyacademy.com/api/ecom/product/add-product
    //create order -> https://rahulshettyacademy.com/api/ecom/order/create-order
    //view order details -> https://rahulshettyacademy.com/api/ecom/order/get-orders-details?id=order-id
    //delete product -> https://rahulshettyacademy.com/api/ecom/product/delete-product/product-id

    public static void main(String[] args) {
        RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON).build();

        ////login
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUserEmail("koushikkondepati@gmail.com");
        loginRequest.setUserPassword("Koushik@123#");

        RequestSpecification reqLogin = given().spec(req).log().all().body(loginRequest);
        LoginResponse loginResponse = reqLogin.when().post("/api/ecom/auth/login")
                .then().log().all().extract().response().as(LoginResponse.class);

        String token = loginResponse.getToken();
        String userId = loginResponse.getUserId();
        System.out.println(userId);
        System.out.println(token);

        //create product
        RequestSpecification addProductBaseSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addHeader("Authorization",token)
                .build();

        RequestSpecification reqAddProduct = given().log().all().spec(addProductBaseSpec).param("productName","Nike")
                .param("productAddedBy",userId)
                .param("productCategory","fashion")
                .param("productSubCategory","shoes")
                .param("productPrice","999")
                .param("productDescription","Original Reebok")
                .param("productFor","men")
                .multiPart("productImage",new File("C:\\Users\\koush\\Downloads\\Reebok.jpg"));

        AddProductResponse addProductResponse = reqAddProduct.when().post("/api/ecom/product/add-product")
                .then().log().all().extract().response().as(AddProductResponse.class);

        String productId = addProductResponse.getProductId();

        // Create Order or Purchase Product
        RequestSpecification createOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .setContentType(ContentType.JSON).addHeader("Authorization",token).build();


        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setCountry("India");
        orderDetail.setProductOrderedId(productId);

        List<OrderDetail>  orderDetailList = new ArrayList<OrderDetail>();
        orderDetailList.add(orderDetail);

        Orders orders = new Orders();
        orders.setOrders(orderDetailList);

        RequestSpecification createOrderReq = given().log().all().spec(createOrderBaseReq).body(orders);

        String ordersResponse = createOrderReq.when().post("/api/ecom/order/create-order")
                .then().log().all().extract().response().asString();




    }

    //delete product
//    RequestSpecification deleteProdBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
//            .addHeader("Authorization",token).setContentType(ContentType.JSON).build();
//
//    RequestSpecification deleteProdReq = given().log().all().spec(deleteProdBaseReq)
//            .pathParam("productId",productId);
//
//    String deleteProductResponse = deleteProdReq.when().delete("/api/ecom/product/delete-product/{productId}")
//    .then().log().all().extract().response().asString();






}
