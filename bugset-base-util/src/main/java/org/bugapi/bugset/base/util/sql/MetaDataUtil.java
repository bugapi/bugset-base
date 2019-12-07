package org.bugapi.bugset.base.util.sql;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.sql.DataSource;
import org.bugapi.bugset.base.constant.DatabaseEnum;

/**
 * 数据库元数据的操作工具类
 *
 * @author zhangxw
 * @since 0.0.1
 */
public final class MetaDataUtil {

	/**
	 * 获取数据库类型方法
	 * @param dataSource 数据源
	 */
	public static DatabaseEnum getDatabaseType(DataSource dataSource) throws SQLException {
		if (dataSource == null) {
			return DatabaseEnum.UNKNOWN;
		}
		try (Connection connection = dataSource.getConnection()) {
			DatabaseMetaData metaData = connection.getMetaData();
			String dataBaseType = metaData.getDatabaseProductName();
			switch (dataBaseType) {
				case "MySQL":
					return DatabaseEnum.MYSQL;
				case "Oracle":
					return DatabaseEnum.ORACLE;
				case "Microsoft SQL Server":
					return DatabaseEnum.SQL_SERVER;
				default:
					return DatabaseEnum.UNKNOWN;
			}
		}
	}

	/**
	 * 根据数据库数据源和表名判断表是否存在
	 *
	 * @param dataSource 数据源
	 * @param tableName  表名
	 * @return boolean 【true：表存在】
	 */
	public static boolean existTable(DataSource dataSource, String tableName) {
		if (null == dataSource) {
			return false;
		}
		ResultSet resultSet = null;
		Connection connection = null;
		try {
			connection = dataSource.getConnection();
			if (null == connection) {
				return false;
			}
			DatabaseMetaData databaseMetaData = connection.getMetaData();
			if (null == databaseMetaData) {
				return false;
			}
			String userName = databaseMetaData.getUserName();
			resultSet = databaseMetaData.getTables(connection.getCatalog(), userName, tableName, new String[]{
					"TABLE"});
			if (null == resultSet) {
				return false;
			}
			return resultSet.next();
		} catch (SQLException e) {
			throw new RuntimeException("判断表" + tableName + "是否存在出错", e);
		} finally {
			// 关闭结果集
			if (null != resultSet) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					throw new RuntimeException("判断表" + tableName + "是否存在时，关闭结果集报错", e);
				}
			}
			// 关闭连接
			if (null != connection) {
				try {
					connection.close();
				} catch (SQLException e) {
					throw new RuntimeException("判断表" + tableName + "是否存在时，关闭连接报错", e);
				}
			}
		}
	}
}
