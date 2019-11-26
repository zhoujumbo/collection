package com.jibug.cetty.sample.web.controller;


import com.jibug.cetty.sample.common.ClassPathTxt;
import com.jibug.cetty.sample.dao.TableManageDao;
import com.jibug.cetty.sample.service.DataExportService;
import com.jibug.cetty.sample.service.MlGoodsMxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/len")
public class DateTestController {
    @Value("${poi.export.file.path}")
    private String exportFilePath;

    @Autowired
    MlGoodsMxService mlGoodsMxService;
    @Autowired
    private DataExportService dataExportService;
    @Autowired
    private ClassPathTxt classPathTxt;

    @GetMapping("/export/mx/{page:\\d+}")
    public String exportMx(@PathVariable Integer page){
        if(!classPathTxt.isInvalid()){
            return "导出服务异常";
        }
        return dataExportService.exportMx(page);
    }

    @GetMapping("/export/br/{page:\\d+}")
    public String exportBr(@PathVariable Integer page){
        if(!classPathTxt.isInvalid()){
            return "导出服务异常";
        }
        return dataExportService.exportBr(page);
    }

}
