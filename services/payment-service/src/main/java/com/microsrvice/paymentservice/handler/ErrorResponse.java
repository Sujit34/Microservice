package com.microsrvice.paymentservice.handler;

import java.util.Map;

public record ErrorResponse(
        Map<String, String> errors
) {

}
