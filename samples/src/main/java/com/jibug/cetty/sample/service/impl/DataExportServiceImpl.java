package com.jibug.cetty.sample.service.impl;

import com.jibug.cetty.core.log.LogUtil;
import com.jibug.cetty.core.utils.DateUtil;
import com.jibug.cetty.core.utils.FastJsonUtil;
import com.jibug.cetty.sample.common.jdbctemplate.ResultSetIterator;
import com.jibug.cetty.sample.common.poi.POIUtil;
import com.jibug.cetty.sample.dao.TableManageDao;
import com.jibug.cetty.sample.entity.TableManage;
import com.jibug.cetty.sample.service.DataExportService;
import com.jibug.cetty.sample.vo.ExportDataResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 数据导出
 */
@Service
@EnableAsync
public class DataExportServiceImpl implements DataExportService {

    @Value("${poi.export.file.path}")
    private String exportFilePath;

    @Autowired
    protected JdbcTemplate jdbcTemplate;
    @Autowired
    private DataExportService dataExportService;
    @Autowired
    private TableManageDao tableManageDao;
    /**
     * 数据库连接操作
     *
     * @throws Exception
     */
    public Connection getConnection() throws Exception {
        // 获取数据库连接
        Connection conn = jdbcTemplate.getDataSource().getConnection();
        return conn;
    }

    /**
     *
     * @Title: WriteMxExcel
     * @Description: 执行导出Excel操作
     * @param
     * @return boolean
     * @throws
     */
    @Override
    public void WriteMxExcel( String area,String sql) {
        if(!StringUtils.isNotEmpty(sql)){
            LogUtil.error("WriteMxExcel sql is null");
            return;
        }
        String excelFile = exportFilePath;
        // 内存中只创建1000个对象，写临时文件，当超过100条，就将内存中不用的对象释放。
        SXSSFWorkbook wb = new SXSSFWorkbook(1000);
        Sheet sheet = null; // 工作表对象
        Row nRow = null; // 行对象
        Cell nCell = null; // 列对象

        // 设置样式以及字体样式
        CellStyle titleCellStyle = POIUtil.createTitleCellStyle(wb);
        CellStyle headCellStyle = POIUtil.createHeadCellStyle(wb);
        CellStyle cellStyle = POIUtil.createCellStyle(wb);

        try (
                Connection conn = getConnection();
                FileOutputStream fOut = new FileOutputStream(excelFile+"/"+area+"_"+ DateUtil.ymdhmsFormatEasy()+".xlsx")
               ){
            Iterator<Map<String, Object>> iterator = new ResultSetIterator(
                    jdbcTemplate.getDataSource().getConnection(), sql,10000);

            long startTime = System.currentTimeMillis();
            System.out.println("开始执行时间 : " + startTime / 1000 + "m");

            int rowNo = 0; // 总行号
            int pageRowNo = 0; // 页行号

            while (iterator.hasNext()) {
                // 打印300000条后切换到下个工作表，可根据需要自行拓展，2百万，3百万...数据一样操作，只要不超过1048576就可以
                if (rowNo % 500000 == 0) {
                    System.out.println("当前sheet页为:" + rowNo / 500000 );
//                    sheet = wb.createSheet("result" + (rowNo / 500000 + 1) + "");// 建立新的sheet对象
                    wb.createSheet("result" + (rowNo / 500000 + 1) + "");// 建立新的sheet对象
                    sheet = wb.getSheetAt(rowNo / 500000); // 动态指定当前的工作表
                    pageRowNo = 1; // 每当新建了工作表就将当前工作表的行号重置为1

                    sheet.setColumnWidth(0,  252*20+323);
                    sheet.setColumnWidth(1, 252*60+323);
                    sheet.setColumnWidth(2, 252*20+323);
                    sheet.setColumnWidth(3, 252*10+323);
                    sheet.setColumnWidth(4, 252*15+323);
                    sheet.setColumnWidth(5, 252*10+323);
                    sheet.setColumnWidth(6, 252*15+323);
                    sheet.setColumnWidth(7, 252*10+323);
                    sheet.setColumnWidth(8, 252*10+323);
                    sheet.setColumnWidth(9, 252*10+323);
                    sheet.setColumnWidth(10, 252*10+323);
                    String[] heads = {"商品ID","标题","类型","价格(USD)","日期(开始)","销量(开始)","日期(结束)","销量(结束)","销量增长","是否免邮","是否有海外仓"};
                    //定义表头
                    nRow = sheet.createRow(0);
                    for (int i = 0; i < heads.length; i++) {
                        Cell tempCell = nRow.createCell(i);
                        tempCell.setCellValue(heads[i]);
                        tempCell.setCellStyle(headCellStyle);
                    }
                }
                rowNo++;
                nRow = sheet.createRow(pageRowNo++); // 新建行对象

                Map<String, Object> obj = iterator.next();
                ExportDataResponse response = FastJsonUtil.map2Bean(obj,ExportDataResponse.class);
                Cell cell0 = nRow.createCell(0);cell0.setCellValue(response.getGoods_id());
                Cell cell1 = nRow.createCell(1);cell1.setCellValue(response.getTitle());
                Cell cell2 = nRow.createCell(2);cell2.setCellValue(response.getSeries());
                Cell cell3 = nRow.createCell(3);cell3.setCellValue(response.getActual_price());cell3.setCellStyle(cellStyle);
                Cell cell4 = nRow.createCell(4);cell4.setCellValue(response.getMin_time());cell4.setCellStyle(cellStyle);
                Cell cell5 = nRow.createCell(5);cell5.setCellValue(response.getMin());cell5.setCellStyle(cellStyle);
                Cell cell6 = nRow.createCell(6);cell6.setCellValue(response.getMax_time());cell6.setCellStyle(cellStyle);
                Cell cell7 = nRow.createCell(7);cell7.setCellValue(response.getMax());cell7.setCellStyle(cellStyle);
                Cell cell8 = nRow.createCell(8);cell8.setCellValue(response.getSub());cell8.setCellStyle(cellStyle);
                Cell cell9 = nRow.createCell(9);cell9.setCellValue(response.getPostFree());cell9.setCellStyle(cellStyle);
                Cell cell10 = nRow.createCell(10);cell10.setCellValue(response.getOsWarehouse());cell10.setCellStyle(cellStyle);

                if (rowNo % 10000 == 0) {
                    LogUtil.info("row no: " + rowNo);
                }
            }

            long finishedTime = System.currentTimeMillis(); // 处理完成时间
            System.out.println("数据读取完成耗时 : " + (finishedTime - startTime) / 1000 + "m");

            wb.write(fOut);
//            fOut.flush(); // 刷新缓冲区
//            fOut.close();
//            long stopTime = System.currentTimeMillis(); // 写文件时间
//            System.out.println("数据写入Excel表格中耗时 : " + (stopTime - startTime) / 1000 + "m");

//            if (isClose) {
//                this.close(rs, stmt, conn);
//            }

        } catch (Exception e) {
            e.printStackTrace();
        }
//        return false;
    }


    @Override
    public String exportMx(Integer page){
        // 查询最近一次统计结果
        List<TableManage> tableManage = tableManageDao.getTopOne("MX");
        if(tableManage==null || tableManage.size()==0){
            LogUtil.warn("MX 未发现待导出结果");
            return "MX 未发现待导出结果";
        }
        StringBuffer sqlParaBuffer = new StringBuffer();
        sqlParaBuffer.append("SELECT result.goods_id,result.title,result.series,result.actual_price, ");
        sqlParaBuffer.append(" date_format(result.min_time ,'%Y-%m-%d' ) min_time,  CONCAT(result.min,'') min,");
        sqlParaBuffer.append("  date_format(result.max_time ,'%Y-%m-%d' ) max_time, CONCAT(result.max,'') max, CONCAT(result.sub,'') sub,types.entry   ");
        sqlParaBuffer.append(" ,IFNULL(result.post_free,'') post_free,IFNULL(result.os_warehouse,'') os_warehouse FROM  ");
        sqlParaBuffer.append(tableManage.stream().findFirst().get().getTable_name());
        sqlParaBuffer.append(" as result");
        sqlParaBuffer.append(" LEFT  JOIN");
        sqlParaBuffer.append(" (SELECT a5.entry FROM");
        sqlParaBuffer.append(" (");
        sqlParaBuffer.append(" SELECT DISTINCT(entry)  FROM  ");
        sqlParaBuffer.append(" ml_goods_type ");
        sqlParaBuffer.append("  WHERE area='MX' AND series ");
        sqlParaBuffer.append(" in ('Accesorios para Vehículos', 'Autos, Motos y Otros', 'Cámaras y Accesorios',");
        sqlParaBuffer.append(" 'Celulares y Telefonía' ,'Computación' , 'Electrodomésticos' , ");
        sqlParaBuffer.append(" 'Hogar, Muebles y Jardín', 'Juegos y Juguetes') ");
        sqlParaBuffer.append(" ) as a5  ) as types");
        sqlParaBuffer.append(" ON result.series = types.entry");
        sqlParaBuffer.append(" WHERE types.entry is NOT NULL");
        sqlParaBuffer.append(" ORDER BY result.series ASC,result.sub DESC ");
        if(page!=null){
            sqlParaBuffer.append(" limit  ");
            sqlParaBuffer.append(page);
            sqlParaBuffer.append("  ,300000");
        }

        System.out.println(sqlParaBuffer.toString());

        dataExportService.WriteMxExcel("MX",sqlParaBuffer.toString());
        return "正在导出，请稍后查看文件夹："+exportFilePath;
    }

    @Override
    public String exportBr(Integer page){
        // 查询最近一次统计结果
        List<TableManage> tableManage = tableManageDao.getTopOne("BR");
        if(tableManage==null || tableManage.size()==0){
            LogUtil.warn("BR 未发现待导出结果");
            return "BR 未发现待导出结果";
        }
        StringBuffer sqlParaBuffer = new StringBuffer();
        sqlParaBuffer.append("SELECT result.goods_id,result.title,result.series,result.actual_price, ");
        sqlParaBuffer.append(" date_format(result.min_time ,'%Y-%m-%d' ) min_time,  CONCAT(result.min,'') min,");
        sqlParaBuffer.append("  date_format(result.max_time ,'%Y-%m-%d' ) max_time, CONCAT(result.max,'') max, CONCAT(result.sub,'') sub,types.entry   ");
        sqlParaBuffer.append(" ,IFNULL(result.post_free,'') post_free,IFNULL(result.os_warehouse,'') os_warehouse FROM  ");
        sqlParaBuffer.append(tableManage.stream().findFirst().get().getTable_name());
        sqlParaBuffer.append(" as result");
        sqlParaBuffer.append(" LEFT  JOIN");
        sqlParaBuffer.append(" (SELECT a5.entry FROM");
        sqlParaBuffer.append(" (");
        sqlParaBuffer.append(" SELECT DISTINCT(entry)  FROM  ");
        sqlParaBuffer.append(" ml_goods_type ");
        sqlParaBuffer.append("  WHERE area='BR' AND series ");
        sqlParaBuffer.append(" in ('Acessórios para Veículos', 'Brinquedos e Hobbies', 'Câmeras e Acessórios',");
        sqlParaBuffer.append(" 'Carros, Motos e Outros' ,'Casa, Móveis e Decoração' , 'Celulares e Telefones' , ");
        sqlParaBuffer.append(" 'Eletrodomésticos', 'Informática') ");
        sqlParaBuffer.append(" ) as a5  ) as types");
        sqlParaBuffer.append(" ON result.series = types.entry");
        sqlParaBuffer.append(" WHERE types.entry is NOT NULL");
        sqlParaBuffer.append(" ORDER BY result.series ASC,result.sub DESC ");
        if(page!=null){
            sqlParaBuffer.append(" limit  ");
            sqlParaBuffer.append(page);
            sqlParaBuffer.append("  ,300000");
        }

        System.out.println(sqlParaBuffer.toString());

        dataExportService.WriteMxExcel("BR",sqlParaBuffer.toString());
        return "正在导出，请稍后查看文件夹："+exportFilePath;
    }

}
