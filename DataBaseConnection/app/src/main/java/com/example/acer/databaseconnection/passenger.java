package com.example.acer.databaseconnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class passenger {

	    //注册
		public static int register(){
			int i = 0;
			int j = 0;
			Scanner input = new Scanner(System.in);
			String database1 = "users_database";
			String database2 = "passenger_databases";
			//////////////////////////////////////////////////////////////
			String username = input.next();
			String password = input.next();
			String name = input.next();
			String info = input.next();
			String sql1 = "insert into user_logic(Username,Password) values(?,?)";
			String sql2 = "insert into passenger(name,introduce) values(?,?)";
			Connection connLogic = ConnectToDataBase.getDataBaseConnection(database1);
			Connection connInfo = ConnectToDataBase.getDataBaseConnection(database2);
			
			try {
				PreparedStatement logic = connLogic.prepareStatement(sql1);
				PreparedStatement Info = connInfo.prepareStatement(sql2);
				logic.setString(1, username);
				logic.setString(2, password);
				Info.setString(1, name);
				Info.setString(2, info);
				i = logic.executeUpdate();
				j = Info.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(i==j&&i==1) return 1;
			else return 0;	
		}

		//判断登陆系统
		public static boolean logic(){
			Scanner input = new Scanner(System.in);
			Connection conn = ConnectToDataBase.getDataBaseConnection("users_database");
			String sql = "select * from User_logic";
			if(conn==null) return false;
			Statement statement = null;
			ResultSet result = null;
			try {
				statement = conn.createStatement();
				result = statement.executeQuery(sql);
				if(result != null&result.first()){
					int idusername = result.findColumn("Username");
					int idpassword = result.findColumn("password");
					///////////////////////////////////////////////////////////////////////
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
		
		//显示信息
		public static String query(int id) {
			StringBuilder builder = new StringBuilder();
			Connection conn = ConnectToDataBase.getDataBaseConnection("passenger_databases");
			String sql = "select * from passenger";
			if(conn==null) return null;
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
//					System.out.println("id\t"+"name\t"+"introduce\t");
					while(!result.isAfterLast()){
						if(result.getInt(idColumnIndex)==id)
						{
//							System.out.print(result.getString(idColumnIndex)+"\t");
//							System.out.print(result.getString(idusername)+"\t");
//							System.out.print(result.getString(idpassword)+"\t");
//							System.out.println();
							builder.append(result.getString(idColumnIndex)).append("\n")
									.append(result.getString(idusername)).append("\n")
									.append(result.getString(idpassword)).append("\n");
						}
						result.next();
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
			try {
				conn.close();
				statement.close();
				result.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return builder.toString();
		}
				
		//路线显示
		public static void displayWay(){
			
		}
		//司机信息显示
		public static void showDriver(int id){
			Connection conn = ConnectToDataBase.getDataBaseConnection("drivers_database");
			String sql = "select * from driver";
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
						if(result.getInt(idColumnIndex)==id)
						{
							System.out.print(result.getString(idColumnIndex)+"\t");
							System.out.print(result.getString(idusername)+"\t");
							System.out.print(result.getString(idpassword)+"\t");
							System.out.println();
						}
						result.next();
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
			try {
				conn.close();
				statement.close();
				result.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		}

