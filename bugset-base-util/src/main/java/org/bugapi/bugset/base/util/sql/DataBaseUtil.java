package org.bugapi.bugset.base.util.sql;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
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
	 * @param path 文件路径
	 * @return List<String> 文件行内容集合
	 * @throws IOException 文件读异常
	 */
	public static List<String> readSql(Path path) throws IOException {
		StringBuilder stringBuilder = new StringBuilder();
		String line;
		try (InputStream inputStream = Files.newInputStream(path, StandardOpenOption.READ);
				BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
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
		}
	}

	/**
	 * 批量执行sql语句没有事务处理
	 *
	 * @param sqls       sql内容
	 * @param dataSource 数据源
	 * @throws SQLException SQL执行异常
	 */
	public static void batchExecuteSql(List<String> sqls, DataSource dataSource) throws SQLException {
		if (null == dataSource) {
			throw new RuntimeException("批量执行sql语句时，数据源为空");
		}
		try (Connection con = dataSource.getConnection();
			 Statement statement = con.createStatement()) {
			executeBatchSql(sqls, statement);
		}
	}

	/**
	 * 批量执行sql语句有事务处理
	 *
	 * @param sqls       sql内容
	 * @param dataSource 数据源
	 * @throws SQLException SQL执行异常
	 */
	public static void batchExecuteSqlWithTransaction(List<String> sqls, DataSource dataSource)
			throws SQLException {
		if (null == dataSource) {
			throw new RuntimeException("批量执行sql语句时，数据源为空");
		}
		try(Connection con = dataSource.getConnection();
				Statement statement = con.createStatement()) {
			con.setAutoCommit(false);
			executeBatchSql(sqls, statement);
			con.commit();
		}
	}

	/**
	 * 批量执行sql
	 *
	 * @param sqls      sql内容
	 * @param statement 发送sql对象
	 * @throws SQLException SQL执行异常
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
	 * @throws SQLException SQL执行异常
	 */
	public static int update(DataSource dataSource, String sql, Object... params)
			throws SQLException {
		if (null == dataSource) {
			return 0;
		}
		QueryRunner queryRunner = new QueryRunner(dataSource);
		return queryRunner.update(sql, params);
	}
}
