/**
 * 
 */
package com.ptsoft.common.base;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.TypeHandler;

/**
 * @author Kingdom
 *
 */
public class EmptyStringIfNull implements TypeHandler<Object>{

	public Object getResult(ResultSet rs, String columnName) throws SQLException {
		return (rs.getString(columnName) == null) ? " " : rs.getString(columnName); 
	}

	public Object getResult(CallableStatement cs, int columnIndex)
			throws SQLException {
		 return (cs.getString(columnIndex) == null) ? " " : cs.getString(columnIndex);
	}

	public void setParameter(PreparedStatement ps, int arg1, Object obj,
			JdbcType jdbcType) throws SQLException {
	}

	@Override
	public Object getResult(ResultSet arg0, int arg1) throws SQLException {
		return null;
	}

}
