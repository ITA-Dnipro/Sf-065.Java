package com.ssjavaacademy.www.messengerattachments;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
@SecurityScheme(
        name = "Authorization",
        description = "A JWT token is required to access this API. Token can be obtained by providing correct username and password. ",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer")

@OpenAPIDefinition(info = @io.swagger.v3.oas.annotations.info.Info(
        title = "My microservice rest api",
        version = "1.0",
        description = "Java Spring microservice implementation of a primitive messenger with attachments",
        contact = @Contact(url = "http://localhost:8093/swagger-ui/index.html", name = "test", email = "test@abv.bg"
        )
)
)
public class SwaggerConfig {
}