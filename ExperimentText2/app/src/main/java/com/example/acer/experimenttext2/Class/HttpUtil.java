package com.example.acer.experimenttext2.Class;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * Created by acer on 2017/3/11.
 */

public class HttpUtil {

    public static void sendOKHttpRequest(String address,okhttp3.Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void postOKHttpRequest(String address, RequestBody requestBody, Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public static void sendHttpRequest(final String address,final HttpCallbackListener listener){
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null){
                        response.append(line);
                    }
                    if (listener != null){
                        listener.onFinish(response.toString());
                    }
                } catch (Exception e){
                    if (listener != null){
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null){
                        connection.disconnect();
                    }
                }
            }
        });

    }


}







//         import java.sql.Connection;
//                 import java.sql.PreparedStatement;
//         import java.sql.ResultSet;
//         import java.util.ArrayList;
//         import java.util.List;
//         import javax.sql.PooledConnection;
//         import oracle.jdbc.pool.OracleConnectionPoolDataSource;
//public class JDBCTest {
//    private String url = null;       /*       *        */
//    public JDBCTest(String sHostName, String sPortNumber, String sSid) {
//        url = "jdbc:oracle:thin:@" + sHostName + ":" + sPortNumber + ":" + sSid;
//        // if JDK1.6 you also can use as
//         url = "jdbc:oracle:thin:@" + sHostName + ":" + sPortNumber + "/" + sSid;
//           }
//           public List<String> getList(String sUsrName, String sPassword, String sql) {
//               List<String> resultList = new ArrayList<String>();
//               try {
//                   OracleConnectionPoolDataSource ocpds = new OracleConnectionPoolDataSource();
//                   String url1 = System.getProperty("JDBC_URL");
//                   if (url1 != null)
//                       url = url1;
//                   ocpds.setURL(url);
//                   ocpds.setUser(sUsrName);
//                   ocpds.setPassword(sPassword);
//                   PooledConnection pc = ocpds.getPooledConnection();
//                   Connection conn = pc.getConnection();
//                   PreparedStatement pstmt = conn.prepareStatement(sql);
//                   ResultSet rset = pstmt.executeQuery();
//                   while (rset.next()) {
//                       resultList.add(rset.getString(1));
//                   }
//                   rset.close();
//                   pstmt.close();
//                   conn.close();
//                   pc.close();
//               } catch (Exception e)
//               {           }
//               return resultList;
//           }       /**       * @param args       */
//           public static void main(String[] args) {
//               // use you real info
//                      String sUsrName = "";
//               String sPassword = "";
//               String sql = "";
//               JDBCTest jdbctest = new JDBCTest("localhost", "1521", "orcl");
//               List<String> list = jdbctest.getList(sUsrName, sPassword, sql);
//           System.out.println(list.size());      }   }
