package com.order.processing.service.impl;

import com.order.processing.domain.Order;
import com.order.processing.domain.Product;
import com.order.processing.domain.User;
import com.order.processing.exception.InternalServerErrorException;
import com.order.processing.service.OrderService;
import com.order.processing.service.ProductService;
import com.order.processing.service.UserService;
import com.order.processing.service.component.LineToOrderParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class FileProcessingServiceImplTest {

    @Mock
    private LineToOrderParser lineToOrderParser;

    @Mock
    private OrderService orderService;

    @Mock
    private UserService userService;

    @Mock
    private ProductService productService;

    @InjectMocks
    private FileProcessingServiceImpl fileProcessingService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should process file successfully and persist users, orders and products")
    void shouldProcessFileSuccessfully() throws Exception {
        MultipartFile multipartFile = mock(MultipartFile.class);
        InputStream inputStream = new ByteArrayInputStream("line1\nline2".getBytes());
        when(multipartFile.getInputStream()).thenReturn(inputStream);

        // Irá simular o comportamento do parser
        doAnswer(invocation -> {
            Map<Integer, Order> orderCache = invocation.getArgument(1);
            Map<Integer, User> userCache = invocation.getArgument(2);
            List<Product> products = invocation.getArgument(3);

            User user = new User();
            user.setUserId(1);
            user.setName("Test User");

            Order order = new Order();
            order.setOrderId(1);
            order.setUser(user);

            Product product = new Product();
            product.setProductId(1);
            product.setOrder(order);

            userCache.put(user.getUserId(), user);
            orderCache.put(order.getOrderId(), order);
            products.add(product);

            return null;
        }).when(lineToOrderParser).parseLine(anyString(), anyMap(), anyMap(), anyList());

        fileProcessingService.processFile(multipartFile);

        verify(userService, times(1)).saveAllUsers(anyCollection());
        verify(orderService, times(1)).saveAllOrders(anyCollection());
        verify(productService, times(1)).saveAllProducts(anyList());
        verify(lineToOrderParser, times(2)).parseLine(anyString(), anyMap(), anyMap(), anyList());
    }

    @Test
    @DisplayName("Should throw InternalServerErrorException when file processing fails")
    void shouldThrowInternalServerErrorExceptionWhenFileFails() throws Exception {
        MultipartFile multipartFile = mock(MultipartFile.class);
        when(multipartFile.getInputStream()).thenThrow(new RuntimeException("File error"));

        InternalServerErrorException exception = assertThrows(InternalServerErrorException.class,
                () -> fileProcessingService.processFile(multipartFile));

        assertEquals("File error", exception.getMessage());

        verifyNoInteractions(userService);
        verifyNoInteractions(orderService);
        verifyNoInteractions(productService);
    }


}