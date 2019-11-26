package com.jibug.cetty.sample.common.jdbctemplate;

import java.util.Vector;
/**
 * 数据库语句参数化工具类
 * @author
 */
public class SqlParaBuffer {
	private StringBuffer buffer;
	@SuppressWarnings("rawtypes")
	private Vector paramter;

	@SuppressWarnings("rawtypes")
	public SqlParaBuffer() {
		buffer = new StringBuffer();
		paramter = new Vector();
	}

	/**
	 * 一般参数方法调用 field 参数值 str 要拼接的SQL语句 注意like用法 %放在field值中
	 * 如buf.append("%"+volId+"%"," and WORK_CONTENT like ? ");
	 * 
	 * 不执行？代换，参数放在paramter数组里
	 *  String bs = buffer.toString(); 
	 *  str = str.replaceFirst("\\?", "'" + field + "'");
	 */
	@SuppressWarnings("unchecked")
	public void append(String field, String str) {
		if (field == null || "".equals(field) || "null".equals(field)|| "undefined".equals(field)) {
			return;
		}
		if (field.indexOf('%') > -1
				&& (field.equalsIgnoreCase("%null%")
						|| field.equalsIgnoreCase("null%")
						|| field.equalsIgnoreCase("%null") || field.replaceAll(
						"%", "").length() < 1)) {
			return;
		}

		// 去掉头尾单引号
		int st = 0;
		int off = field.length();
		if (field.startsWith("'")) {
			st++;
		}
		if (field.endsWith("'")) {
			off--;
		}

		if (str.indexOf("?") < 0) {
			buffer.append(str);
			return;
		}


		if (str.indexOf('#') > -1) {
			str = str.replaceAll("#", "'||chr(35)||'");
		}
		if (str.indexOf('&') > -1) {
			str = str.replaceAll("&", "||chr(38)||'");
		}

		buffer.append(str);
		paramter.add(field.substring(st, off));

	}

	/**
	 * in参数方法调用 field 参数值 str 要拼接的SQL语句 separator 参数值分隔符 null时默认为,
	 */
	@SuppressWarnings("unchecked")
	public void append(String field, String str, String separator) {
		if (field == null || "".equals(field) || "null".equals(field)) {
			return;
		}

		if (str.indexOf("?") < 0) {
			buffer.append(str);
			return;
		}

		// 不执行？代换，参数放在paramter数组里
		// String bs = buffer.toString();
		// str = str.replaceFirst("\\?", "'" + field + "'");

		if (str.indexOf('#') > -1) {
			str = str.replaceAll("#", "'||chr(35)||'");
		}
		if (str.indexOf('&') > -1) {
			str = str.replaceAll("&", "||chr(38)||'");
		}
		if (separator == null || "".equals(separator)) {
			separator = ",";
		}
		String[] paramArray = field.split(separator);

		// 大于20个时用非参数化
		if (paramArray.length > 20) {
			StringBuffer valParam = new StringBuffer();
			for (String param : paramArray) {
				if (param == null || "".equals(param) || "'".equals(param)) {
					break;
				}
				if (!param.startsWith("'")) {
					param = "'" + param.trim();
				}
				if (!param.endsWith("'")) {
					param = param.trim() + "'";
				}
				valParam.append(",").append(param);
			}
			str = str.replaceFirst("\\?", valParam.toString().substring(1));
			buffer.append(str);
		} else {
			// 少于20个时用参数化
			for (String param : paramArray) {
				if (param == null || "".equals(param) || "'".equals(param)) {
					break;
				}
				// 去掉头尾单引号
				int st = 0;
				int off = param.length();
				if (param.startsWith("'")) {
					st++;
				}
				if (param.endsWith("'")) {
					off--;
				}
				paramter.add(param.substring(st, off));
			}
			buffer.append(str.replace("?", makePlaceholders(paramArray.length)));
		}
	}

	public static String makePlaceholders(int paramInt) {
		String str = "";
		for (int i = 0; i < paramInt; ++i) {
			str = str + "?";
			if (i >= paramInt - 1)
				continue;
			str = str + ",";
		}
		return str;
	}

	public void append(String str) {
		buffer.append(str);
	}

	public String toString(Object[] objs) {
		String result = this.getSql();
		for (int i = 0; i < objs.length; i++) {
			result = result.replaceFirst("\\?", "'" + objs[i] + "'");
		}
		return result;
	}

	// 完整SQL语句--用于打印出SQL语句，方便在PL/SQL上执行
	public String toString() {
		return this.toString(paramter.toArray());
	}

	// 参数值
	public Object[] getParamter() {
		return paramter.toArray();
	}

	// 带?号的SQL语句
	public String getSql() {
		return buffer.toString();
	}

	public static void main(String[] args) {
		SqlParaBuffer buf = new SqlParaBuffer();
		String volId = "%123%";
		buf.append("select * from pw.pw_weekplan_baseinfo where 1=1");
		buf.append("DELETE", " AND RECORD_FLAG <> ? ");
		buf.append(volId, " and WORK_CONTENT like ? ");
		buf.append("APPLY", " and state_Id=? ");
		buf.append(" ORDER BY WORK_DATE DESC ");
	}
}
