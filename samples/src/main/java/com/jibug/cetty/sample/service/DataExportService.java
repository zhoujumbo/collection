package com.jibug.cetty.sample.service;

public interface DataExportService {
    void WriteMxExcel(String area,String sql);

    String exportMx(Integer page);

    String exportBr(Integer page);
}
