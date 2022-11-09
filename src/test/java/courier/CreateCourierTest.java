package courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.MatcherAssert.*;
import static org.hamcrest.Matchers.*;

public class CreateCourierTest {
    private CourierRequests courierRequests;
    private CreateCourier createCourier;
    private CourierCredentials courierCredentials;
    private CourierDelete courierDelete;

    @Before
    public void setUp() {
        courierRequests = new CourierRequests();
    }

    @After
    public void tearDown() {
        courierCredentials = new CourierCredentials("maryPopins", "1234");
        ValidatableResponse loginResponse = courierRequests.login(courierCredentials);
        int loginId = loginResponse.extract().path("id");
        String idLogin = String.valueOf(loginId);
        courierDelete = new CourierDelete(idLogin);
        courierRequests.delete(courierDelete, loginId);
    }

    @Test
    @DisplayName("Courier Account Can Be Created Successfully")
    public void courierAccountCanBeCreatedSuccessfully() {
        createCourier = new CreateCourier("maryPopins", "1234", "Mary");
        ValidatableResponse response = courierRequests.create(createCourier);
        int statusCode = response.extract().statusCode();
        boolean isCourierCreated = response.extract().path("ok");
        assertThat("Courier cannot create", statusCode, equalTo(SC_CREATED));
        assertThat("Courier cannot create", isCourierCreated, is(not(false)));
    }

}