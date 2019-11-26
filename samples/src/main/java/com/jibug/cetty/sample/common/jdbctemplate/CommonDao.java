package com.jibug.cetty.sample.common.jdbctemplate;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public interface CommonDao {
	
    int batchUpdate(String[] sqls);
	
	int update(String sql);

	int update(String sql, Object[] params);

	List<Map<String, Object>> queryForList(String sql);

	List<Map<String, Object>> queryForList(String sql, Object[] params);

	void executSql(String sql);

	int getTotal(String sql);

	int getTotal(String sql, Object[] params);
	
	int queryForInt(String sql);
	
	int queryForInt(String sql, Object[] params);
	
	SqlRowSet queryForRowSet(String sql, Object[] params);
	
}
