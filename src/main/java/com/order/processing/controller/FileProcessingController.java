package com.order.processing.controller;

import com.order.processing.exception.BadRequestException;
import com.order.processing.exception.InternalServerErrorException;
import com.order.processing.service.FileProcessingService;
import com.order.processing.springdoc.FileProcessingDoc;
import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file-processing")
public class FileProcessingController implements FileProcessingDoc {

    private static final Logger LOGGER = LoggerFactory.getLogger(FileProcessingController.class);

    private final FileProcessingService fileProcessingService;

    public FileProcessingController(FileProcessingService fileProcessingService) {
        this.fileProcessingService = fileProcessingService;
    }

    @Override
    @PostMapping(value = "/process-file-orders", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> processFileOrders(@RequestParam("file") MultipartFile file) {
        try {
            fileProcessingService.processFile(file);
            LOGGER.info("File processing completed successfully - File: {}", file.getOriginalFilename());
            return ResponseEntity.status(HttpStatus.CREATED).body("File processed successfully!");
        } catch (BadRequestException e) {
            LOGGER.warn("Error during process of file of orders - Type: {} - Message: {}", e.getClass().getSimpleName(), e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (InternalServerErrorException e) {
            LOGGER.info("Error during process of file of orders - Type: {} - Message: {}", e.getClass().getSimpleName(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error during process of file!");
        }
    }
}
