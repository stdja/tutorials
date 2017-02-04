package com.baeldung.cachecontrol;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static com.jayway.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = AppRunner.class)
public class ResourceEndpointLiveTest {
        private static final String URL_PREFIX = "http://localhost:8080";

    @Test
    public void givenServiceEndpoint_whenGetRequestForUser_shouldResponseWithCacheControlMaxAge() {
        given()
                .when()
                .get(URL_PREFIX + "/users/Michael")
                .then()
                .contentType(ContentType.JSON).and().statusCode(200).and()
                .header("Cache-Control", "max-age=60");
    }

    @Test
    public void givenServiceEndpoint_whenGetRequestForNotCacheableContent_shouldResponseWithCacheControlNoCache() {
        given()
                .when()
                .get(URL_PREFIX + "/timestamp")
                .then()
                .contentType(ContentType.JSON).and().statusCode(200).and()
                .header("Cache-Control", "no-store");
    }

    @Test
    public void givenServiceEndpoint_whenGetRequestForPrivateUser_shouldResponseWithSecurityDefaultCacheControl() {
        given()
                .when()
                .get(URL_PREFIX + "/private/users/Michael")
                .then()
                .contentType(ContentType.JSON).and().statusCode(200).and()
                .header("Cache-Control", "no-cache, no-store, max-age=0, must-revalidate");
    }

}