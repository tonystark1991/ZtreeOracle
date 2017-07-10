package com.wlqing.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDao {
	@Autowired
	//ע��������Ϊ�˻�ȡ����
	private GetTableNameDao getTableNameDao;
	@Autowired
	private JdbcTemplate jdbcTemplate;

	public JdbcTemplate getJdbcTemplate() {
		return jdbcTemplate;
	}

	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	//��ȡ���еı�ķ�������ΪzTree�ĵ�һ�ڵ�
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
			//��ʱlist��ÿһ��Ԫ�صĸ�ʽ����json�ַ����ĸ�ʽ
			k1++;
		}
		return list;
	}
	
	//��ȡ��Ӧ���������ֶ����ķ�����ͨ��ǰ̨�����name(����ڵ���������)����sql������д
	public List getAllColumn(String name,int id){
		List<String> list=new ArrayList<>();
		List<Map<String, Object>> columnList =new ArrayList<>();
		String sql="select column_name from user_tab_columns where table_name='"+name+"'";
		columnList=jdbcTemplate.queryForList(sql);
		//����һ�ű��е��ֶ���ĿС��100��
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
	
	//��ȡ����ڵ�(�ֶ�)��������Ϣ�ķ���
	//�����name���ֶ����ƣ�ͨ�������Pid�жϸ��ֶζ�Ӧ�ı��ǵڼ��ű��Ӷ�ͨ����һ��Dao���еķ�����ȡ����
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
