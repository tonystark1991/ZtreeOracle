package com.wlqing.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
/*
 * 该类中只有一个方法
 * 此方法只针对该次项目，过程是查找到Oracle数据库下所有的表的名称，并将其封转至list中
 * 目的：由于查询对应字段信息时，前台传入的name代表的是字段的名称，而sql语句中还需要相应的表名，因此写了这个方法
 */
public class GetTableNameDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}
	public List getTableName(){
		List<String> list=new ArrayList<>();
		List<Map<String, Object>> nameList =new ArrayList<>();
		String sql="SELECT table_name FROM USER_TABLES";
		nameList=jdbcTemplate.queryForList(sql);
		for(int i=0;i<nameList.size();i++){
			Collection value=nameList.get(i).values();
			String str1=""+value+"";
			String str11=str1.substring(1, str1.length()-1);
			list.add(""+str11+"");
		}
		//list中的元素是表名，格式是table1，table2...
		return list;
	}
}
