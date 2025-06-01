package com.order.processing.springdoc;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Order File Processing", description = "General endpoints for uploading and processing files.")
public interface FileProcessingDoc {

    @Operation(summary = "Process Order File", description = "Upload and process a file containing order data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "File processed successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid file or data"),
            @ApiResponse(responseCode = "500", description = "Internal server error during file processing")
    })
    ResponseEntity<?> processFileOrders(
            @Parameter(description = "TXT file containing orders", required = true)
            @RequestParam("file") MultipartFile file
    );
}
