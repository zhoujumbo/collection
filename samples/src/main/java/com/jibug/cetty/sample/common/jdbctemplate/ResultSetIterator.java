package com.jibug.cetty.sample.common.jdbctemplate;

import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.sql.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Data
public class ResultSetIterator implements Iterator<Map<String, Object>> {
    private ResultSet resultSet; // 查询出来的结果集
    private List<String> fieldNameList; // 需要查询的字段的集合

    public ResultSetIterator(Connection connection, String querySql) throws SQLException {
        // 初始化 Statement 防止又一次查询把之前的结果集关闭
        Statement statement = JdbcUtil.createStatement(connection, 10000, 0);
        // 执行查询
        this.resultSet = JdbcUtil.executeStatement(statement, querySql);
        // 设置字段
        this.fieldNameList = JdbcUtil.getFieldNameList(this.resultSet.getMetaData());
    }

    // batcheSize 如果<=0 采用默认500  否则使用自定义  最多不超过1.5W
    public ResultSetIterator(Connection connection, String querySql, int batcheSize) throws SQLException {
        if(batcheSize<=0){
            batcheSize = 500;
        }else if(batcheSize>15000){
            batcheSize = 15000;
        }
        // 初始化 Statement 防止又一次查询把之前的结果集关闭
        PreparedStatement statement = JdbcUtil.createPreparedStatement(connection,querySql, batcheSize, 0);
        // 执行查询
        this.resultSet = JdbcUtil.executeStatement(statement, querySql);
        // 设置字段
        this.fieldNameList = JdbcUtil.getFieldNameList(this.resultSet.getMetaData());
    }

    @Override
    public boolean hasNext() {
        try {
            return resultSet.next();
        } catch (SQLException e) {
            // 关闭链接
            return false;
        }
    }

    /**
     * 返回当前结果集的一条
     *
     * @Title next
     * @author 于国帅
     * @date 2019年2月21日 下午4:09:05
     * @return
     * @see java.util.Iterator#next()
     */
    @Override
    public Map<String, Object> next() {
        Map<String, Object> rowMap = new HashMap<>();
        try {
            for (String fieldName : this.fieldNameList) {
                // Use underlying database's type information except for BigDecimal and BigInteger
                // which cannot be serialized by JavaBin/XML. See SOLR-6165
                Object value;
                value = this.resultSet.getObject(fieldName);
                if (value instanceof BigDecimal || value instanceof BigInteger) {
                    rowMap.put(fieldName, value.toString());
                } else {
                    rowMap.put(fieldName, value);
                }
            }
        } catch (SQLException e) {
            return rowMap;
        }
        return rowMap;
    }

}