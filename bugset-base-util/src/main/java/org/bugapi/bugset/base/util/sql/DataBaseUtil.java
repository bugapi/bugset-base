package org.bugapi.bugset.base.util.sql;

import org.apache.commons.dbutils.QueryRunner;
import org.bugapi.bugset.base.constant.SymbolType;
import org.bugapi.bugset.base.util.string.StringUtil;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

/**
 * 使用Apache的DbUtils来封装数据库操作的工具类
 *
 * @author zhangxw
 * @since 0.0.1
 */
public class DataBaseUtil {

	/**
	 * 读取sql文件内容并根据分号转成list
	 *
	 * @param inputStream 字符缓冲输入流
	 * @return List<String> sql语句集合
	 */
	public static List<String> readSql(InputStream inputStream) {
		if (null == inputStream) {
			return null;
		}
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
		StringBuilder stringBuilder = new StringBuilder();
		String line;
		try {
			while ((line = bufferedReader.readLine()) != null) {
				if (StringUtil.isEmpty(line) || line.trim().startsWith("--")) {
					continue;
				}
				if (line.endsWith(SymbolType.SEMICOLON)) {
					stringBuilder.append(line.trim());
				} else {
					stringBuilder.append(line.trim()).append(" ");
				}
			}
			return Arrays.asList(stringBuilder.toString().trim().split(SymbolType.SEMICOLON));
		} catch (IOException e) {
			throw new RuntimeException("解析升级脚本的sql语句报错");
		} finally {
			try {
				bufferedReader.close();
			} catch (IOException e) {
				throw new RuntimeException("解析升级脚本的sql语句关闭缓冲输入流报错");
			}
			try {
				inputStream.close();
			} catch (IOException e) {
				throw new RuntimeException("解析升级脚本的sql语句关闭输入流报错");
			}
		}
	}

	/**
	 * 批量执行sql语句没有事务处理
	 *
	 * @param sqls       sql内容
	 * @param dataSource 数据源
	 */
	public static void batchExecuteSql(List<String> sqls, DataSource dataSource) {
		if (null == dataSource) {
			throw new RuntimeException("批量执行sql语句时，数据源为空");
		}
		try (Connection con = dataSource.getConnection();
			 Statement statement = con.createStatement()) {
			executeBatchSql(sqls, statement);
		} catch (SQLException e) {
			throw new RuntimeException("批量执行sql语句报错", e);
		}
	}

	/**
	 * 批量执行sql语句有事务处理
	 *
	 * @param sqls       sql内容
	 * @param dataSource 数据源
	 */
	public static void batchExecuteSqlWithTransaction(List<String> sqls, DataSource dataSource) {
		if (null == dataSource) {
			throw new RuntimeException("批量执行sql语句时，数据源为空");
		}
		Connection con = null;
		Statement statement = null;
		try {
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			statement = con.createStatement();
			executeBatchSql(sqls, statement);
			con.commit();
		} catch (SQLException e) {
			if (null != con) {
				try {
					con.rollback();
				} catch (SQLException ex) {
					throw new RuntimeException("批量执行sql语句时，事务回滚异常");
				}
			}
		} finally {
			if (null != statement) {
				try {
					statement.close();
				} catch (SQLException ex) {
					throw new RuntimeException("批量执行sql语句时，关闭statement报错");
				}
			}

			if (null != con) {
				try {
					con.close();
				} catch (SQLException ex) {
					throw new RuntimeException("批量执行sql语句时，关闭数据库连接报错");
				}
			}
		}
	}

	/**
	 * 批量执行sql
	 *
	 * @param sqls      sql内容
	 * @param statement 发送sql对象
	 */
	private static void executeBatchSql(List<String> sqls, Statement statement) throws SQLException {

		for (String sql : sqls) {
			if (StringUtil.isNotEmpty(sql)) {
				statement.addBatch(sql);
			}
		}
		statement.executeBatch();
	}

	/**
	 * 使用 dbutils 执行【create、alert、drop、insert、update、delete】等方法
	 *
	 * @param dataSource 数据源
	 * @param sql        sql语句
	 * @param params     参数
	 * @return int 影响行数
	 */
	public static int update(DataSource dataSource, String sql, Object... params) {
		if (null == dataSource) {
			return 0;
		}
		QueryRunner queryRunner = new QueryRunner(dataSource);
		int operateCount;
		try {
			operateCount = queryRunner.update(sql, params);
		} catch (SQLException e) {
			throw new RuntimeException("执行sql语句报错", e);
		}
		return operateCount;
	}
}
