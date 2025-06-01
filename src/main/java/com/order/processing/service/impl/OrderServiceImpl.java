package com.order.processing.service.impl;

import com.order.processing.domain.Order;
import com.order.processing.domain.Product;
import com.order.processing.domain.User;
import com.order.processing.domain.dto.OrderDTO;
import com.order.processing.domain.dto.OrderResponseDTO;
import com.order.processing.domain.dto.ProductDTO;
import com.order.processing.exception.BadRequestException;
import com.order.processing.exception.InternalServerErrorException;
import com.order.processing.repository.OrderRepository;
import com.order.processing.service.OrderService;
import com.order.processing.service.ProductService;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ProductService productService;

    public OrderServiceImpl(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    @Override
    public List<Order> saveAllOrders(Collection<Order> orders) {
        return orderRepository.saveAllAndFlush(orders);
    }

    @Override
    public List<OrderResponseDTO> findOrders(Integer orderId, LocalDate startDate, LocalDate endDate) throws BadRequestException, InternalServerErrorException {
        try {
            List<Product> products = productService.findProducts(orderId, startDate, endDate);

            Map<User, Map<Order, List<Product>>> groupedProducts = groupProducts(products);

            return buildOrderResponse(groupedProducts);
        } catch (BadRequestException e) {
            throw new BadRequestException(e.getMessage());
        } catch (Exception e) {
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    /**
     * Agrupa produtos por cliente e por pedido
     *
     * @param products lista de produtos para ser agrupada
     * @return um map onde a chave é o usuário, e o valor é outro map que tem como chave o pedido
     *         e como valor a lista de produtos pertencentes a esse pedido
     */
    private static Map<User, Map<Order, List<Product>>> groupProducts(List<Product> products) {
        return products.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getOrder().getUser(),
                        Collectors.groupingBy(Product::getOrder)
                ));
    }

    /**
     * Monta uma lista de OrderResponseDTO pelo map agrupado
     *
     * @param groupedProducts lista de produtos agrupados por cliente e pedido
     * @return uma lista de OrderResponseDTO com clientes, pedidos e produtos
     */
    private List<OrderResponseDTO> buildOrderResponse(Map<User, Map<Order, List<Product>>> groupedProducts) {
        return groupedProducts.entrySet().stream().sorted(Comparator.comparing(entry -> entry.getKey().getUserId())).map(userEntry -> {
                    User user = userEntry.getKey();
                    List<OrderDTO> orderDTOs = buildOrderDTO(userEntry.getValue());
                    return new OrderResponseDTO(user.getUserId(), user.getName(), orderDTOs);
                }).toList();
    }

    /**
     * Monta uma lista de OrderDTO para um cliente
     *
     * @param ordersWithProducts map onde a chave é um pedido e o valor é a lista de produtos desse pedido
     * @return uma lista de OrderDTO contendo os dados do pedido, com lista de produtos convertida para DTO
     */
    private List<OrderDTO> buildOrderDTO(Map<Order, List<Product>> ordersWithProducts) {
        return ordersWithProducts.entrySet().stream()
                .map(orderEntry -> {
                    Order order = orderEntry.getKey();
                    List<Product> products = orderEntry.getValue();
                    List<ProductDTO> productDTOs = products.stream().map(p -> new ProductDTO(p.getProductId(), p.getValue())).toList();

                    BigDecimal total = productDTOs.stream().map(ProductDTO::getValue).filter(Objects::nonNull).reduce(BigDecimal.ZERO, BigDecimal::add);

                    return new OrderDTO(order.getOrderId(), total, order.getDate(), productDTOs);
                }).toList();
    }
}
