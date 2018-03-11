package com.gupaoedu.vip.pattern.template;

import javax.sql.DataSource;
import javax.swing.plaf.SliderUI;
import javax.xml.transform.Result;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JdbcTemplate {

    private DataSource dataSource;
    public JdbcTemplate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    private Connection getConnection() throws Exception {
        return this.dataSource.getConnection();
    }

    private PreparedStatement createPreparedStatement(Connection connection, String sql) throws Exception {
        return connection.prepareStatement(sql);
    }

    private ResultSet executeQuery(PreparedStatement preparedStatement, Object[] values) throws Exception {
        for(int i=0;i<values.length;i++) {
            preparedStatement.setObject(i, values[i]);
        }
        return preparedStatement.executeQuery();
    }

    private void closeStatement(Statement statement) throws Exception {
        statement.close();
    }

    private void closeResult(ResultSet resultSet) throws Exception {
        resultSet.close();
    }

    private void closeConnection(Connection connection) throws Exception {
        //通常把它放回连接池回收
    }

    private List<?> parseResultSet(ResultSet resultSet, RowMapper rowMapper) throws Exception {
        List<?> result = new ArrayList<?>();
        int rowNumber = 1;
        while (resultSet.next()) {
            rowNumber++;
            result.add(rowMapper.mapRow(resultSet, rowNumber));
        }
        return result;
    }

    public List<Object> executeQuery(String sql, RowMapper<?> rowMapper, Object[] values) {

        try {
            //1.获取连接
            Connection connection = this.getConnection();

            //2.创建语句集
            PreparedStatement preparedStatement = this.createPreparedStatement(connection, sql);

            //3.执行语句集，并且获得结果集
            ResultSet resultSet = this.executeQuery(preparedStatement, values);

            //4.解析语句集
            List<?> result = this.parseResultSet();

            //5.关闭结果集
            this.closeResult(resultSet);

            //6.关闭语句集
            this.closeStatement(preparedStatement);

            //7.关闭连接
            this.closeConnection(connection);

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public Object processResult(ResultSet resultSet, int rowNumber) throws Exception;

}
