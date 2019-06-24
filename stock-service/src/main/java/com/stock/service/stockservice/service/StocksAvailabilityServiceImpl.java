package com.stock.service.stockservice.service;


import com.stock.service.stockservice.data.exception.DuplicateEntityException;
import com.stock.service.stockservice.data.exception.EntityNotFoundException;
import com.stock.service.stockservice.data.exception.NoStocksAvailableException;
import com.stock.service.stockservice.entity.Product;
import com.stock.service.stockservice.entity.Stocks;
import com.stock.service.stockservice.model.OrderDTO;
import com.stock.service.stockservice.model.ProductDTO;
import com.stock.service.stockservice.model.StocksDTO;
import com.stock.service.stockservice.repository.ProductRepository;
import com.stock.service.stockservice.repository.StockRepository;
import com.stock.service.stockservice.web.StocksAvailabilityController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

;import static org.springframework.util.Assert.notNull;

@Component
public class StocksAvailabilityServiceImpl implements StocksAvailabilityService {

    private Logger log = LoggerFactory.getLogger(StocksAvailabilityServiceImpl.class);
    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    @Autowired
    public StocksAvailabilityServiceImpl(ProductRepository productRepository,StockRepository stockRepository) {
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public StocksDTO create(StocksDTO dto) {

        Optional<Stocks> _stocksOptional=stockRepository.findByStockId(dto.getStockid());

        if(_stocksOptional.isPresent())
            throw new DuplicateEntityException("Stock ID : " + dto.getStockid() + " already exists");

        Optional<Product> _productOptional=productRepository.findByProductName(dto.getProductId());

        if(_productOptional.isPresent())
            throw new DuplicateEntityException("Product  : " + dto.getProductId() + " already exists");

        Product product=buildProduct(dto);
        productRepository.save(product);
        log.isDebugEnabled();
        log.debug("Inside create :: Stored Product is "+product);
        return buildStocksDTO(product);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public StocksDTO getStocksById(String stocksId) {
        Optional<Stocks> _stocksOptional=stockRepository.findByStockId(stocksId);

        if(_stocksOptional.isPresent()) {
            Stocks stocks = _stocksOptional.get();
            return new StocksDTO(stocks.getStockId(), stocks.getTimeStamp().toString(),
                    stocks.getProduct().getProductName(), stocks.getQuantity()) ;
        }
        log.isDebugEnabled();
        log.debug("Stock ID : " + stocksId + " not exists in database");
        throw new EntityNotFoundException("Stock ID : " + stocksId + " not exists in database");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<StocksDTO> listAll() {

        return  stockRepository.findAll()
                .parallelStream()
                .map(this::buildStockDTO).collect(Collectors.toList());

    }
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public List<ProductDTO> listAllProducts() {

        List<ProductDTO> productDTOList=new ArrayList<ProductDTO>();
        productDTOList= productRepository.findAll()
                .parallelStream()
                .map(this::buildProductDTO)
                .collect(Collectors.toList());
        return productDTOList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public ProductDTO getStockForProduct(String productId) {
        Optional<Product>  _productOptional=productRepository.findByProductName(productId);
        if(_productOptional.isPresent()){
            Product product = _productOptional.get();
            return buildProductDTO(product);
        }
        log.debug("Product : " + productId + " not exists");
        throw new EntityNotFoundException("Product : " + productId + " not exists");
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Optional<OrderDTO> patch(OrderDTO orderDTO,String ifMatchValue) {
        notNull(orderDTO, "Order Info cannot be null!");

        Optional<Product> _productOptional = productRepository.findByProductName(orderDTO.getProductId());

        if(_productOptional.isPresent()) {

            Product product=productRepository.getOne(_productOptional.get().getId());
            if(!product.getStock().getVersion().equals(Long.valueOf(ifMatchValue))) {
                throw new DuplicateEntityException("Received Same Request Twice");
            }else {
                BiFunction<Integer, Integer, Integer> function = (o, o2) -> o - o2;
                Integer newQuantity = function.apply(product.getStock().getQuantity(), orderDTO.getItemSold());
                if (product.getStock().getQuantity() == 0)
                    throw new NoStocksAvailableException("No Stocks Are available for this product");
                else if (newQuantity < 0)
                    throw new NoStocksAvailableException(
                            "Less Stocks are available than requested: Available quantity is " + product.getStock().getQuantity());

                product.getStock().setItemSold(orderDTO.getItemSold());
                product.getStock().setQuantity(newQuantity);
                productRepository.save(product);
                return Optional.of(new OrderDTO(product.getProductName(), product.getStock().getItemSold()));
            }
        }
        return Optional.empty();
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Optional<StocksDTO> update(StocksDTO stocksDto,String ifMatchValue) {
        notNull(stocksDto, "Stocks Info cannot be null!");
        Optional<Product> _productOptional = productRepository.findByProductName(stocksDto.getProductId());

        if(_productOptional.isPresent()) {
            Product product=productRepository.getOne(_productOptional.get().getId());
            if(!product.getStock().getVersion().equals(Long.valueOf(ifMatchValue))) {
                   throw new DuplicateEntityException("Received Same Request Twice");
            }
            else if(stocksDto.getStockid().equalsIgnoreCase(product.getStock().getStockId())){
                   product.getStock().setQuantity(stocksDto.getQuantity());
                   productRepository.save(product);
                   return Optional.of(buildStocksDTO(product));
            }
            throw new EntityNotFoundException("Stocks ID : " + stocksDto.getStockid() +  " does not belongs to Product ID: " + stocksDto.getProductId());
        }
        return Optional.empty();
    }

    private StocksDTO buildStocksDTO(Product product){
        return new StocksDTO(product.getStock().getStockId(), product.getStock().getTimeStamp().toString(),
                product.getProductName(), product.getStock().getQuantity()) ;
    }

    private Product buildProduct(StocksDTO dto){
        Product product=new Product();
        Stocks stocks=new Stocks();
        stocks.setStockId(dto.getStockid());
        stocks.setQuantity(dto.getQuantity());
        stocks.setTimeStamp(Instant.now());
        stocks.setProduct(product);
        product.setProductName(dto.getProductId());
        product.setStock(stocks);
        return product;
    }

    private ProductDTO buildProductDTO(Product product){

        ProductDTO productDTO=new ProductDTO();
        StocksDTO stocksDTO=new StocksDTO();
        stocksDTO.setStockid(product.getStock().getStockId());
        stocksDTO.setQuantity(product.getStock().getQuantity());
        stocksDTO.setTimeStamp(product.getStock().getTimeStamp().toString());
        stocksDTO.setVersion(product.getStock().getVersion());
        productDTO.setStock(stocksDTO);
        productDTO.setProductId(product.getProductName());
        productDTO.setRequestTimestamp(Instant.now().toString());
        return productDTO;
    }

    public StocksDTO buildStockDTO(Stocks stocks){
        return new StocksDTO(stocks.getStockId(), stocks.getTimeStamp().toString(),
                stocks.getProduct().getProductName(), stocks.getQuantity()) ;
    }


}
