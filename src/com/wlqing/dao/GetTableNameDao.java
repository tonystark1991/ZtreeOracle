package com.wlqing.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
/*
 * ������ֻ��һ������
 * �˷���ֻ��Ըô���Ŀ�������ǲ��ҵ�Oracle���ݿ������еı�����ƣ��������ת��list��
 * Ŀ�ģ����ڲ�ѯ��Ӧ�ֶ���Ϣʱ��ǰ̨�����name��������ֶε����ƣ���sql����л���Ҫ��Ӧ�ı��������д���������
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
		//list�е�Ԫ���Ǳ�������ʽ��table1��table2...
		return list;
	}
}
