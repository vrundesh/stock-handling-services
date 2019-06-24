package com.statistics.statisticsservice.repository;


import com.statistics.statisticsservice.entity.Stocks;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface StockRepository extends JpaRepository<Stocks, Long> {

    Optional<Stocks>  findByStockId(String stockId);

}
