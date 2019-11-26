package com.jibug.cetty.sample.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class ExportDataResponse {

    private Long id;
    private String goods_id;
    private String title;
    private String series;
    private String actual_price;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private String min_time;
    private String min;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private String max_time;
    private String max;
    private String sub;
    private String postFree;
    private String osWarehouse;


    public ExportDataResponse setId(Long id) {
        this.id = id;
        return this;
    }

    public ExportDataResponse setGoods_id(String goods_id) {
        this.goods_id = goods_id == null ? null : goods_id.trim();
        return this;
    }

    public ExportDataResponse setTitle(String title) {
        this.title = title == null ? null : title.trim();
        return this;
    }

    public ExportDataResponse setSeries(String series) {
        this.series = series == null ? null : series.trim();
        return this;
    }

    public ExportDataResponse setActual_price(String actual_price) {
        this.actual_price = actual_price == null ? null : actual_price.trim();
        return this;
    }

    public ExportDataResponse setMin_time(String min_time) {
        this.min_time = min_time == null ? null : min_time.trim();
        return this;
    }

    public ExportDataResponse setMin(String min) {
        this.min = min == null ? null : min.trim();
        return this;
    }

    public ExportDataResponse setMax_time(String max_time) {
        this.max_time = max_time == null ? null : max_time.trim();
        return this;
    }

    public ExportDataResponse setMax(String max) {
        this.max = max == null ? null : max.trim();
        return this;
    }

    public ExportDataResponse setSub(String sub) {
        this.sub = sub == null ? null : sub.trim();
        return this;
    }

    public ExportDataResponse setPostFree(String postFree) {
        this.postFree = postFree == null ? null : postFree.trim();
        return this;
    }

    public ExportDataResponse setOsWarehouse(String osWarehouse) {
        this.osWarehouse = osWarehouse == null ? null : osWarehouse.trim();
        return this;
    }
}
