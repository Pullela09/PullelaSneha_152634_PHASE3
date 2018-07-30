package com.cg.paymentwallet.util;

import java.io.File;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

public class PaymentWalletUtil {
	static Connection con;
		static Properties prop;
		
		static
		{
			try
			{
				prop= new Properties();
				File file = new File("jdbc.properties");
				FileInputStream fin = new FileInputStream(file);
				prop.load(fin);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
		}
		
		public static Connection getConnect()
		{
			try
			{
				String driver = prop.getProperty("driver");
				String user = prop.getProperty("username");
				String pass = prop.getProperty("password");
				String url = prop.getProperty("url");
				Class.forName(driver);
				con = DriverManager.getConnection(url,user,pass);
				System.out.println("connection established ");
			}
			catch(Exception e)
			{
				//e.printStackTrace();
				//WalletRepoImpl.log.info(e.getMessage());
				//logging error msg into a file 
				//throw new PaymentWalletException("technical problem");
			e.printStackTrace(); //prints on the console
			}
			return con;
		}
		

	}



