package com.example.acer.databaseconnection;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.sql.*;
import java.util.Scanner;

public class ConnectToDataBase {

	static Context context;

	public ConnectToDataBase(Context context) {
		this.context = context;
	}



	public static  Connection getDataBaseConnection(String database){
		String driver = "com.mysql.jdbc.Driver";
		String REMOTE_IP = "4907c25a.all123.net:3306";//4907c25a.all123.net
		String url = "jdbc:mysql://"+REMOTE_IP+"//"+database;
		String user = "root";
		String password = "857631";

		try{
			Class.forName(driver);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}

		Connection conn = null;
		try{
			conn = DriverManager.getConnection(url,user,password);
			Toast.makeText(context,"链接数据库成功",Toast.LENGTH_SHORT).show();
			if(!conn.isClosed())
			{
				Toast.makeText(context,"链接数据库成功",Toast.LENGTH_SHORT).show();
				//System.out.println("链接数据库成功");
				Log.d("Connection:","链接数据库成功");

			}
		}catch(SQLException e){
			Log.d("Connection:","连接数据库失败："+e.getMessage());
			Toast.makeText(context,"连接数据库失败："+e.getMessage(),Toast.LENGTH_SHORT).show();
			//System.out.println("连接数据库失败："+e.getMessage());
			conn = null;
		}
		return conn;
	}
	
	public static void query(Connection conn,String sql) {
		if(conn==null) return;
		Statement statement = null;
		ResultSet result = null;
		
		try{
			statement = conn.createStatement();
			result = statement.executeQuery(sql);
			if(result != null&result.first())
			{
				int idColumnIndex = result.findColumn("id");
				int idusername = result.findColumn("name");
				int idpassword = result.findColumn("introduce");
				System.out.println("id\t"+"name\t"+"introduce\t");
				while(!result.isAfterLast()){
					System.out.print(result.getString(idColumnIndex)+"\t");
					System.out.print(result.getString(idusername)+"\t");
					System.out.print(result.getString(idpassword)+"\t");
					System.out.println();

			}
		}
	}catch(SQLException e){
		e.printStackTrace();
	}
	}

	@SuppressWarnings("resource")
	public static boolean logic(Connection conn,String sql){
		Scanner input = new Scanner(System.in);
		if(conn==null) return false;
		Statement statement = null;
		ResultSet result = null;
		try {
			statement = conn.createStatement();
			result = statement.executeQuery(sql);
			if(result != null&result.first()){
				int idusername = result.findColumn("Username");
				int idpassword = result.findColumn("password");
				String name = input.next();
				String password = input.next();
				while(!result.isAfterLast())
				{
					if(name.equals(result.getString(idusername))&&password.equals(result.getString(idpassword)))
					{
						System.out.println("sucecced to logic"); 
						return true;
					}
					result.next();
				}
			}
			else System.out.println("faull to logic");
			input.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

}
