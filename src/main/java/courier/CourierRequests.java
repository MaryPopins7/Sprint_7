package courier;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import static io.restassured.RestAssured.given;

public class CourierRequests extends RestClient {
    private static final String COURIER_PATH_LOGIN = "https://qa-scooter.praktikum-services.ru/api/v1/courier/login";
    private static final String COURIER_PATH = "https://qa-scooter.praktikum-services.ru/api/v1/courier/";

    //login
    @Step("Courier authorization")
    public ValidatableResponse login(CourierCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .body(credentials)
                .when()
                .post(COURIER_PATH_LOGIN)
                .then();
    }

    @Step("Create courier account")
    public ValidatableResponse create(CreateCourier createCourier) {
        return given()
                .spec(getBaseSpec())
                .body(createCourier)
                .when()
                .post(COURIER_PATH)
                .then();
    }

    @Step("Delete courier account")
    public ValidatableResponse delete(CourierDelete courierDelete, int idCourier) {
        String str = Integer.toString(idCourier);
        return given()
                .spec(getBaseSpec())
                .body(courierDelete)
                .when()
                .delete(COURIER_PATH + str)
                .then();
    }

    @Step("Courier authorization with invalid request")
    public ValidatableResponse invalidLogin(CourierInvalidCredentials invalidCredentials) {
        return given()
                .spec(getBaseSpec())
                .body(invalidCredentials)
                .when()
                .post(COURIER_PATH_LOGIN)
                .then();
    }
}