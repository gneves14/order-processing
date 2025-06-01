package com.order.processing.service;

import com.order.processing.exception.InternalServerErrorException;
import org.springframework.web.multipart.MultipartFile;

public interface FileProcessingService {

    void processFile(MultipartFile file) throws InternalServerErrorException;
}
