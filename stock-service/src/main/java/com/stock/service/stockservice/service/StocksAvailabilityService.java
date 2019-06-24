package com.stock.service.stockservice.service;

import com.stock.service.stockservice.model.OrderDTO;
import com.stock.service.stockservice.model.ProductDTO;
import com.stock.service.stockservice.model.StocksDTO;

import java.util.List;
import java.util.Optional;

public interface StocksAvailabilityService {

	StocksDTO create(StocksDTO dto);

    StocksDTO getStocksById(String stocksId);

    List<StocksDTO> listAll();

    ProductDTO getStockForProduct(String productId);

    Optional<OrderDTO> patch(OrderDTO orderDTO,String ifMatchValue);

    Optional<StocksDTO> update(StocksDTO stocksDto, String ifMatchValue);

    List<ProductDTO> listAllProducts();
}
