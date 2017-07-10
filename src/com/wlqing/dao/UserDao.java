package com.wlqing.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDao {
	@Autowired
	//注入此项就是为了获取表名
	private GetTableNameDao getTableNameDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	//获取所有的表的方法，是为zTree的第一节点
	public List getAllTables() {
		List<String> list=new ArrayList<>();
		List<Map<String, Object>> tableList =new ArrayList<>();
		String sql="SELECT table_name FROM USER_TABLES";
		tableList=jdbcTemplate.queryForList(sql);
		int k1=1;
		int k2=0;
		for(int i=0;i<tableList.size();i++){
			Collection value=tableList.get(i).values();
			String str1=""+value+"";
			String str11=str1.substring(1, str1.length()-1);
			list.add("{id:"+k1+",pId:"+k2+",name:'"+str11+"',type:'tableName',isParent:true}");
			//此时list中每一个元素的格式符合json字符串的格式
			k1++;
		}
		return list;
	}
	
	//获取相应表中所有字段名的方法，通过前台传入的name(点击节点名即表名)进行sql语句的书写
	public List getAllColumn(String name,int id){
		List<String> list=new ArrayList<>();
		List<Map<String, Object>> columnList =new ArrayList<>();
		String sql="select column_name from user_tab_columns where table_name='"+name+"'";
		columnList=jdbcTemplate.queryForList(sql);
		//考虑一张表中的字段数目小于100个
		int k1=id*100+1;
		for(int i=0;i<columnList.size();i++){
			Collection value=columnList.get(i).values();
			String str1=""+value+"";
			String str11=str1.substring(1, str1.length()-1);
			list.add("{id:"+k1+",pId:"+id+",name:'"+str11+"',type:'columnName',isParent:true}");
			k1++;
		}
		return list;
	}
	
	//获取点击节点(字段)的所有信息的方法
	//传入的name是字段名称，通过传入的Pid判断该字段对应的表是第几张表，从而通过另一个Dao类中的方法获取表名
	public List getInfo(String name,int pId,int id){
		List<String> list=new ArrayList<>();
		List<Map<String, Object>> infoList =new ArrayList<>();		
		String tableName=""+getTableNameDao.getTableName().get(pId-1).toString()+"";
		String sql="select "+name+" from "+tableName+"";
		infoList=jdbcTemplate.queryForList(sql);
		int k1=id*1000+1;
		for(int i=0;i<infoList.size();i++){
			Collection value=infoList.get(i).values();
			String str1=""+value+"";
			String str11=str1.substring(1, str1.length()-1);
			list.add("{id:"+k1+",pId:"+id+",type:'info',name:'"+str11+"'}");
			k1++;
		}
		return list;
	}
}
