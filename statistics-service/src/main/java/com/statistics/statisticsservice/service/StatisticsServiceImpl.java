package com.statistics.statisticsservice.service;


import com.statistics.statisticsservice.data.exception.ValidDateNotProvidedException;
import com.statistics.statisticsservice.entity.Stocks;
import com.statistics.statisticsservice.model.SellingProductDTO;
import com.statistics.statisticsservice.model.StatisticsDTO;
import com.statistics.statisticsservice.model.StocksDTO;
import com.statistics.statisticsservice.repository.ProductRepository;
import com.statistics.statisticsservice.repository.StockRepository;
import com.sun.jmx.remote.internal.IIOPHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class StatisticsServiceImpl implements StatisticsService {


    private final ProductRepository productRepository;
    private final StockRepository stockRepository;

    @Autowired
    public StatisticsServiceImpl(ProductRepository productRepository,StockRepository stockRepository) {
        this.productRepository = productRepository;
        this.stockRepository = stockRepository;
    }

    @Override
    public StatisticsDTO getStatistics(List<String> range)  {

        StatisticsDTO statisticsDTO=new StatisticsDTO();
        statisticsDTO.setRequestTimestamp(DateTimeFormatter.ISO_INSTANT.format(Instant.now()));
        statisticsDTO.setTopAvailableProductsList(getTopThreeAvailableProducts(stockRepository));
        statisticsDTO.setTopSellingProductsList(getTopThreeSellingProductDTOList(stockRepository,range,statisticsDTO));
        return statisticsDTO;
    }


    List<StocksDTO>  getTopThreeAvailableProducts(StockRepository stockRepository){

        return stockRepository.findAll()
                .parallelStream()
                .sorted((o1, o2) -> o2.getQuantity().compareTo(o1.getQuantity()))
                .map(this::buildStockDto).limit(3).collect(Collectors.toList());

    }

    List<SellingProductDTO> getTopThreeSellingProductDTOList(StockRepository stockRepository,List<String> range,StatisticsDTO statisticsDTO){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime startDateTime = LocalDateTime.parse(range.get(0), formatter);
        LocalDateTime endDateTime = LocalDateTime.parse(range.get(1), formatter);
        statisticsDTO.setRange( startDateTime+"==" +endDateTime);
        return  getProductDTOList(stockRepository,endDateTime,endDateTime);

    }

    public List<SellingProductDTO> getProductDTOList(StockRepository stockRepository,LocalDateTime startDate, LocalDateTime endDate){

        List<SellingProductDTO> sellingProductDTOList=new ArrayList<>();
        sellingProductDTOList=stockRepository.findAll()
                .parallelStream()
                .filter(stocks -> stocks.getTimeStamp().isBefore(endDate.toInstant(ZoneOffset.UTC)) ||
                        stocks.getTimeStamp().isAfter(startDate.toInstant(ZoneOffset.UTC)))
                .filter(stocks -> stocks.getItemSold()!=null)
                .map(stocks -> {
                    SellingProductDTO sellingProductDTO = new SellingProductDTO();
                    sellingProductDTO.setProductId(stocks.getProduct().getProductName());
                    sellingProductDTO.setItemsSold(stocks.getItemSold());
                    return sellingProductDTO;
                })
                .sorted(Comparator.comparing(SellingProductDTO::getItemsSold,
                        Comparator.nullsLast(Comparator.reverseOrder())))
                .limit(3)
                .collect(Collectors.toList());

        return  sellingProductDTOList;

    }

    public StocksDTO buildStockDto(Stocks stocks){
        StocksDTO stocksDTO=new StocksDTO();
        stocksDTO.setStockid(stocks.getStockId());
        stocksDTO.setTimeStamp(stocks.getTimeStamp().toString());
        stocksDTO.setQuantity(stocks.getQuantity());
        stocksDTO.setProductId(stocks.getProduct().getProductName());
        return stocksDTO;
    }


}
