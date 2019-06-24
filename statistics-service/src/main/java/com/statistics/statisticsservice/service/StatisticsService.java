package com.statistics.statisticsservice.service;





import com.statistics.statisticsservice.model.StatisticsDTO;

import java.util.List;

public interface StatisticsService {

    StatisticsDTO getStatistics(List<String> range);

}
