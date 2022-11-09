package courier;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Before;
import org.junit.After;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CourierCreateWrongTest {
    private CreateCourier createCourier;
    private CourierRequests courierRequests;

    @Before
    public void setUp() {
        courierRequests = new CourierRequests();
    }

    @Test
    @DisplayName("Not enough data for create account")
    public void notEnoughDataForCreateCourierAccount() {
        createCourier = new CreateCourier("", "1234", "madara");
        ValidatableResponse createResponse = courierRequests.create(createCourier);
        int statusCode = createResponse.extract().statusCode();
        String textMessage = createResponse.extract().path("message");
        assertThat("Courier created", statusCode, equalTo(SC_BAD_REQUEST));
        assertThat("Courier created", textMessage, is("Недостаточно данных для создания учетной записи"));

    }
    @Test
    @DisplayName("Data already in use")
    public void loginForCreateCourierAccountAlreadyInUse() {
        createCourier = new CreateCourier("maryPopins", "1234", "Mary");
        ValidatableResponse firstCreateResponse = courierRequests.create(createCourier);
        ValidatableResponse secondCreateResponse = courierRequests.create(createCourier);
        int statusCode = secondCreateResponse.extract().statusCode();
        String textMessage = secondCreateResponse.extract().path("message");
        assertThat("Courier created", statusCode, equalTo(SC_CONFLICT));
        assertThat("Courier created", textMessage, is("Этот логин уже используется. Попробуйте другой."));
    }
}

