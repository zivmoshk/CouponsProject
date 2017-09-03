package com.project.CouponsProject.Dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map.Entry;
import java.util.Queue;



// the connectionPool class open connections using the getConnection() function and store them in  conMap HashMap(storing the time the connection was requested) 
//maxConnections is a final attribute so it can not be change by an unauthorized user.      
public class ConnectionPool {
	private static String jdbcDriver = "com.mysql.cj.jdbc.Driver";
	private static String dbUrl = "jdbc:mysql://localhost:3306/Coupons?useSSL=false";
	static String userName = "root";
	static String password = "1234";
	private final static String CONNECTION_STRING = "jdbc:mysql://localhost:3306/Coupons;user=root;password=1234?useSSL=false";
	private final static int maxConnections=4;
	private static Queue<Long> waitList = new LinkedList<Long>();
	private DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
	private Date date = new Date();
	private static HashMap <Connection,Date> conMap= new HashMap<Connection,Date>();
	
		
	private ConnectionPool(){	
	}
	
	//Singleton
	private static ConnectionPool instance= null;
	
	public static ConnectionPool getInstance(){
		
		if (instance == null) {
			instance = new ConnectionPool();
		}
		
		return instance;
	}
	
	// creating the connection to the DB ,if the pool is full, the connection request enters a queue( using the wait() function and add to waitList), 
	// when a connection becomes available, the returnConnection function is used to close the connection,  the queue is released by order using the 
	//notifyAll function releasing all requests, if the request is the one in the head of the waitList it will be completed and get the connection.
	//if not the connection will enter the waitList again .
	private  Connection createConnection(){
		Connection con;
		try {
			Class.forName(jdbcDriver);
			con= DriverManager.getConnection(dbUrl, userName, password);
			Date currentDate=dateFormat.parse(dateFormat.format(date));
			conMap.put(con, currentDate);
		} catch (Exception e) {
			throw new RuntimeException("Can not open connection to DB");
		}

		return con;
	}
	public  Connection getConnection(){
		if(conMap.size()<maxConnections){
			 return createConnection();

		}
		else{
			try {
				synchronized (this){
				waitList.add(Thread.currentThread().getId());
			
				while(waitList.element()!=Thread.currentThread().getId()){
					wait();	
				}
				waitList.remove();
				
				return createConnection();
				}
			}
			 catch (InterruptedException e) {
				throw new RuntimeException("Failed to add connection requast to waiting list");
			}
		}
	}
	
	public void returnConnection(Connection con){
		try {
			synchronized (this){
			con.close();
			conMap.remove(con);
			notifyAll();
			}
		} catch (Exception e) {
			
			throw new RuntimeException("Can not close connection. Connection detalis:"+CONNECTION_STRING);
		}
		
	}
	
	// the method is used to close all open connections, it uses an iterator to "move" through the conMap and implements the return Connection method on them.
	public void closeAllConections(){
		java.util.Iterator<Entry<Connection, Date>> it = conMap.entrySet().iterator();
		Connection con;
	    while (it.hasNext()){
	    	con= it.next().getKey();
	    	returnConnection(con);
	    	it = conMap.entrySet().iterator();
	    }
	}	
		
}
	


