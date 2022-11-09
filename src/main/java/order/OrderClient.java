package order;

import courier.RestClient;
import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient {
    private static final String ORDER_PATH = "https://qa-scooter.praktikum-services.ru/api/v1/orders";
    @Step("Create order with parameters {order}")
    public ValidatableResponse create(OrderCreate orderCreate) {
        return given()
                .spec(getBaseSpec())
                .body(orderCreate)
                .when()
                .post(ORDER_PATH)
                .then();
    }

    @Step("Get order list")
    public ValidatableResponse getList() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH)
                .then();
    }
}

