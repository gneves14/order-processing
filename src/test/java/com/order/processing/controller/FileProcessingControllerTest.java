package com.order.processing.controller;

import com.order.processing.exception.BadRequestException;
import com.order.processing.exception.InternalServerErrorException;
import com.order.processing.service.FileProcessingService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(FileProcessingController.class)
class FileProcessingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FileProcessingService fileProcessingService;

    @Test
    @DisplayName("Should return 201 CREATED when file is processed successfully")
    void shouldReturn201CreatedWhenFileIsProcessedSuccessfully() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "orders.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "0000000070                              Palmer Prosacco00000007530000000003     1836.7420210308".getBytes()
        );

        mockMvc.perform(multipart("/file-processing/process-file-orders")
                        .file(file))
                .andExpect(status().isCreated())
                .andExpect(content().string("File processed successfully!"));

        verify(fileProcessingService, times(1)).processFile(file);
    }

    @Test
    @DisplayName("Should return 400 BAD REQUEST when BadRequestException is thrown during file processing")
    void shouldReturn400BadRequestWhenBadRequestExceptionIsThrown() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "invalid.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "invalid".getBytes()
        );

        doThrow(new BadRequestException("Invalid file")).when(fileProcessingService).processFile(file);

        mockMvc.perform(multipart("/file-processing/process-file-orders")
                        .file(file))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid file"));

        verify(fileProcessingService, times(1)).processFile(file);
    }

    @Test
    @DisplayName("Should return 500 INTERNAL SERVER ERROR when InternalServerErrorException is thrown during file processing")
    void shouldReturn500InternalServerErrorWhenInternalServerErrorExceptionIsThrown() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "orders.txt",
                MediaType.TEXT_PLAIN_VALUE,
                "0000000070                              Palmer Prosacco00000007530000000003     1836.7420210308".getBytes()
        );

        doThrow(new InternalServerErrorException("Internal Error")).when(fileProcessingService).processFile(file);

        mockMvc.perform(multipart("/file-processing/process-file-orders")
                        .file(file))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("Error during process of file!"));

        verify(fileProcessingService, times(1)).processFile(file);
    }
}
