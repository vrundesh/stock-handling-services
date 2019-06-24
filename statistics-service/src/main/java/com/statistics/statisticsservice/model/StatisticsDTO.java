package com.statistics.statisticsservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;

public class StatisticsDTO {


    @NotNull
    @JsonProperty("requestTimestamp")
    private String requestTimestamp;

    @NotNull
    @JsonProperty("range")
    private String range;

    @NotNull
    @JsonProperty("topAvailableProducts")
    private List<StocksDTO> topAvailableProductsList;

    @JsonProperty("topSellingProducts")
    private List<SellingProductDTO> topSellingProductsList;

    public StatisticsDTO(){}

    public StatisticsDTO(@NotNull String requestTimestamp, @NotNull String range,
            @NotNull List<StocksDTO> topAvailableProductsList, @NotNull List<SellingProductDTO> topSellingProductsList) {
        this.requestTimestamp = requestTimestamp;
        this.range = range;
        this.topAvailableProductsList = topAvailableProductsList;
        this.topSellingProductsList = topSellingProductsList;
    }

    public String getRequestTimestamp() {
        return requestTimestamp;
    }

    public void setRequestTimestamp(String requestTimestamp) {
        this.requestTimestamp = requestTimestamp;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    public List<StocksDTO> getTopAvailableProductsList() {
        return topAvailableProductsList;
    }

    public void setTopAvailableProductsList(List<StocksDTO> topAvailableProductsList) {
        this.topAvailableProductsList = topAvailableProductsList;
    }

    public List<SellingProductDTO> getTopSellingProductsList() {
        return topSellingProductsList;
    }

    public void setTopSellingProductsList(List<SellingProductDTO> topSellingProductsList) {
        this.topSellingProductsList = topSellingProductsList;
    }

    @Override public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof StatisticsDTO))
            return false;
        StatisticsDTO that = (StatisticsDTO) o;
        return Objects.equals(requestTimestamp, that.requestTimestamp) && Objects.equals(range, that.range) && Objects
                .equals(topAvailableProductsList, that.topAvailableProductsList) && Objects
                .equals(topSellingProductsList, that.topSellingProductsList);
    }

    @Override public int hashCode() {
        return Objects.hash(requestTimestamp, range, topAvailableProductsList, topSellingProductsList);
    }

    @Override public String toString() {
        final StringBuilder sb = new StringBuilder("StatisticsDTO{");
        sb.append("requestTimestamp='").append(requestTimestamp).append('\'');
        sb.append(", range='").append(range).append('\'');
        sb.append(", topAvailableProductsList=").append(topAvailableProductsList);
        sb.append(", topSellingProductsList=").append(topSellingProductsList);
        sb.append('}');
        return sb.toString();
    }
}
