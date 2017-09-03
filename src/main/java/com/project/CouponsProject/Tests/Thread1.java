package com.project.CouponsProject.Tests;


import java.sql.Connection;

import com.project.CouponsProject.Dao.ConnectionPool;

public class Thread1 extends Thread  {
	
	public void run(){
		 ConnectionPool cp = ConnectionPool.getInstance( );
	
			Connection con=cp.getConnection();
			System.out.println("Connected");
			
			cp.returnConnection(con);
			System.out.println("Notify");
	
		
	}

}
