package com.order.processing.springdoc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Tag(name = "Orders", description = "APIs for querying and managing order data.")
public interface OrderDoc {

    @Operation(summary = "Find Orders", description = "Retrieve orders filtered by order ID, start date, and end date.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Orders successfully retrieved"),
            @ApiResponse(responseCode = "400", description = "Invalid request parameters"),
            @ApiResponse(responseCode = "500", description = "Internal server error while retrieving orders")
    })
    ResponseEntity<?> findOrders(
            @Parameter(description = "Order ID to filter the results", example = "123")
            @RequestParam(required = false) Integer orderId,
            @Parameter(description = "Start date for filtering orders (format: yyyy-MM-dd)", example = "2025-01-01")
            @RequestParam(required = false) LocalDate startDate,
            @Parameter(description = "End date for filtering orders (format: yyyy-MM-dd)", example = "2025-12-31")
            @RequestParam(required = false) LocalDate endDate
    );
}