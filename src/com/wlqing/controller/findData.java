package com.wlqing.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.wlqing.dao.UserDao;

@Controller
@RequestMapping("/ztree")
public class findData {
	@Autowired
	private UserDao userDao;
	@RequestMapping("/find.do")
	
	public void testy(HttpServletRequest request,HttpServletResponse response) throws IOException{
		List<String> zTreeList=new ArrayList<>();
		//获取前台传入的参数
		String type=request.getParameter("type");
		String name=request.getParameter("name");
		String id=request.getParameter("id");
		String pId=request.getParameter("pId");
		PrintWriter out = response.getWriter(); 
		//判断前台的参数并进行调用相应的方法
		if("tableName".equals(type)){
			int id1=Integer.parseInt(id);
			zTreeList=userDao.getAllColumn(name, id1);
		}
		else if ("columnName".equals(type)){
			int id1=Integer.parseInt(id);
			int pId1=Integer.parseInt(pId);
			zTreeList=userDao.getInfo(name, pId1, id1);
		}
		else if("info".equals(type)){
		}
		else{
			zTreeList=userDao.getAllTables();
		}
		
		//将得到的list遍历拼接成json形式的字符串
		String str = "["; 
		for(int i=0;i<zTreeList.size();i++){
			str=str+zTreeList.get(i).toString()+",";
		}
		String str1=str.substring(0, str.length()-1);
		str1=str1+"]";
        out.print(str1);
	}
}
