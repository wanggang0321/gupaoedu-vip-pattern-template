package com.gupaoedu.vip.pattern.template.dao;

import com.gupaoedu.vip.pattern.template.JdbcTemplate;
import com.gupaoedu.vip.pattern.template.RowMapper;
import com.gupaoedu.vip.pattern.template.entity.Member;

import java.sql.ResultSet;
import java.util.List;

public class MemberDao {

    //为什么不继承JdbcTemplate，主要是为了解耦
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(null);

    public List<Object> query() {
        String sql = "select * from t_member";

        return jdbcTemplate.executeQuery(sql, new RowMapper<Member>() {

            public Member mapRow(ResultSet resultSet, int rowNum) throws Exception {
                Member member = new Member();
                member.setUsername(resultSet.getString("username"));
                member.setPassword(resultSet.getString("password"));
                member.setAge(resultSet.getInt("age"));

                return member;
            }
        }, null);
    }

    //public Object processResult(ResultSet resultSet, int rowNumber) throws SQLException {
    //
    //    Member member = new Member();
    //    member.setUsername(resultSet.getString("username"));
    //    member.setPassword(resultSet.getString("password"));
    //    member.setAge(resultSet.getInt("age"));
    //
    //    return member;
    //}

}
