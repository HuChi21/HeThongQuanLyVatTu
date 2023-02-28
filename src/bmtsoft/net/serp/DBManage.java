package bmtsoft.net.serp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;
import android.widget.SimpleAdapter;
import android.widget.Toast;
import net.sourceforge.jtds.jdbc.DateTime;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;

public class DBManage {
	Connection connect;
 
 public String databasename="thuctap",Pass="thuctap@123",UserID="thuctap",ip="42.112.21.199";
 
	public DBManage() {
		try {
			connect = CONN(UserID, Pass, databasename, ip);
		} catch (Exception e) {
			Log.e(null, e.toString());
		}
	}

	@SuppressLint("NewApi")
	public Connection CONN(String _user, String _pass, String _DB,
			String _server) {
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
				.permitAll().build();
		StrictMode.setThreadPolicy(policy);
		Connection conn = null;
		String ConnURL = null;
		try {

			Class.forName("net.sourceforge.jtds.jdbc.Driver");
			ConnURL = "jdbc:jtds:sqlserver://" + _server + ";"
					+ "databaseName=" + _DB + ";user=" + _user + ";password="
					+ _pass + ";";
			conn = DriverManager.getConnection(ConnURL);
		} catch (SQLException se) {
			Log.e("ERRO", se.getMessage());
		} catch (ClassNotFoundException e) {
			Log.e("ERRO", e.getMessage());
		} catch (Exception e) {
			Log.e("ERRO", e.getMessage());
		}

		return conn;
	}

	public ResultSet QuerySELECT(String COMANDOSQL) {
		ResultSet rs;
		try {
			if (connect.isClosed()==true)
			{
				try {
					 
					connect = CONN(UserID, Pass, databasename, ip);
				} catch (Exception e) {
					Log.e(null, e.toString());
				}
			}
		} 
		catch (Exception e) {
			Log.e(null, e.toString());
		} 
		try {
			Statement statement = connect.createStatement();
			 
			rs = statement.executeQuery(COMANDOSQL);
			
		} catch (Exception e) {
			Log.e("ERRO", e.getMessage());
			try {
	        	connect.close();
	        }catch (Exception ex){}
			return null;
		}
		 
			 
		return rs;
		

	}

	public void QueryEx(String COMANDOSQL) {
		Statement statement;
		try {
			try {
				if (connect.isClosed()==true)
				{
					try {
						String UserID2=UserID;
						connect = CONN(UserID2, Pass, databasename, ip);
					} catch (Exception e) {
						Log.e(null, e.toString());
					}
				}
			} 
			catch (Exception e) {
				Log.e(null, e.toString());
			} 
			statement = connect.createStatement();
			statement.executeQuery(COMANDOSQL);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public HashMap<String, String> getAllEmployee() {
		String queryAllEmployee = "select ObjectID,ObjectName FROM Objects Where ObjectTypeID = 4";
		HashMap<String, String> result = new HashMap<String, String>();

		ResultSet rs = QuerySELECT(queryAllEmployee);

		try {
			while (rs.next()) {
				result.put(rs.getString("ObjectID"), rs.getString("ObjectName"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public List exedatatable(String UserName,String pass,int chinhanh)
    {
        Statement stmt = null;
        ResultSet rs = null;
        try {String UserID2=UserID;
			connect = CONN(UserID2, Pass, databasename, ip);
		} catch (Exception e) {
			Log.e(null, e.toString());
		}
        List<UserModer> list=new ArrayList<UserModer>();
        String SQL = "select * from Users where LoginName=N'"+ UserName +"' and Password=N'"+ pass +"' and chinhanh=N'"+ chinhanh +"'";
        try {
            stmt = connect.createStatement();
            rs = stmt.executeQuery(SQL);
            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                UserModer cls=new UserModer();
                cls.setLoginName(rs.getString("LoginName"));
                cls.setPassword(rs.getString("Password"));
                cls.setRoleID(rs.getInt("RoleID"));
                cls.setModifyCost(rs.getInt("ModifyCost"));
                cls.setViewCost(rs.getInt("ViewCost"));
                cls.setviewhh(rs.getInt("viewhh"));
                cls.setModifyCK(rs.getInt("ModifyCK"));
                cls.setViewRef(rs.getInt("ViewRef"));
                cls.setXuatkhoam(rs.getInt("Xuatkhoam"));
                cls.setXuatkhoam(rs.getInt("ISKM"));
                cls.setXuatkhoam(rs.getInt("Chinhanh"));
                list.add(cls);

            }
        }catch (Exception e){}
        try {
        	connect.close();
        }catch (Exception ex){}
        return list;
    }
	 public List Getbrand(GeneralInfo cls)
	    {
	        Statement stmt = null;
	        ResultSet rs = null;
	        try {
				connect = CONN(UserID, Pass, databasename, ip);
			} catch (Exception e) {
				Log.e(null, e.toString());
			}
	        List<GeneralInfo> list=new ArrayList<GeneralInfo>();
	        List<String> list2=new ArrayList<String>();
	        String SQL = "select * from GeneralInfo";
	        try {
	            stmt = connect.createStatement();
	            rs = stmt.executeQuery(SQL);
	            // Iterate through the data in the result set and display it.
	            while (rs.next()) {
	                cls.setChinhanh(rs.getInt("Chinhanh"));
	                cls.setTenVT(rs.getString("TenVT"));
	                list.add(cls);
	                list2.add(rs.getString("TenVT"));

	            }
	        }catch (Exception e){}
	        try {
	        	connect.close();
	        }catch (Exception ex){}
	        return list2;
	    }
	 public void getData(String  startdate ,String enddate,String idkhohang,String UserID,int chinhanh) {
		//	ResultSet rs = null;
			try {
					if (connect.isClosed()==true)
					{
						try {
							String UserID2=UserID;
							connect = CONN(UserID2, Pass, databasename, ip);
						} catch (Exception e) {
							Log.e(null, e.toString());
						}
					}
			} 
			catch (Exception e) {
				Log.e(null, e.toString());
			} 
			try {
				
				CallableStatement cal = connect.prepareCall("{Call Tinhsokhotonghop_web_mobile (?,?,?,?,?,?)}");
				cal.setString(1, startdate);
				cal.setString(2, enddate); 
				cal.setString(3, idkhohang);
				cal.setInt(4, chinhanh);
				cal.setString(5, UserID);
				cal.setString(6, "");
				cal.execute();
				//rs = QuerySELECT(query);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 try{
			connect.close();} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	 public int AddRefs(String  RefDate ,String RefType,String RefNo,String CustID,String Contact,String Addr,String Memo,String UserID,String MasterAccount,double FCAmount,String EmployeeID2,int Chinhanh ) {
		 int outputValue=0;	
		// ResultSet rs = null;
			try {
					if (connect.isClosed()==true)
					{
						try {
							String UserID2=UserID;
							connect = CONN(UserID2, Pass, databasename, ip);
						} catch (Exception e) {
							Log.e(null, e.toString());
						}
					}
			} 
			catch (Exception e) {
				Log.e(null, e.toString());
			} 
			try {
				
				CallableStatement cal = connect.prepareCall("{Call RefAdd (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				cal.registerOutParameter(1,java.sql.Types.INTEGER); 
				cal.setString(2, RefNo);
				cal.setString(3, RefType); 
				cal.setString(4, RefDate);
				cal.setDouble(5, FCAmount); 
				cal.setString(6, CustID);
				cal.setString(7, "11312");
				cal.setString(8, Contact );
				cal.setString(9, Addr );
				cal.setString(10, Memo);
				cal.setString(11, null);
				cal.setString(12, "VNĐ");
				cal.setInt(13, 1);
				cal.setString(14, null);
				cal.setString(15, null);
				cal.setString(16, null);
				cal.setDouble(17, FCAmount);
				cal.setString(18, UserID);
				cal.setDouble(19,0);
				cal.setString(20, null);
				cal.setString(21, EmployeeID2);
				cal.setDouble(22, 0);
				cal.setString(23, null);
				cal.setDouble(24, 0);
				cal.setDouble(25, 0);
				cal.setDouble(26, 0);
				cal.setDouble(27, 0);
				cal.setString(28, RefDate);
				cal.setInt(29,1);
				cal.setString(30, "");
				cal.setInt(31,Chinhanh);
				cal.setInt(32,0);
				cal.setString(33, "");
				cal.setInt(34,0);
				cal.setString(35, "");
				cal.setInt(36,0);
				
				 boolean hadResults =	cal.execute(); 
				   outputValue = cal.getInt(1);  
				   

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 try{
			connect.close();} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 return outputValue;
		}
	 public void UpdateRefItem(int  RefID ,String RefType,String InOutTypeID,String StockID,String Stock2ID,String TaxNK,String TaxDB,String EmployeeID,String EmployeeID2,double DiscountRate,double DiscountNum,double TaxRate,double TaxNum,double TaxNKRate,double TaxNKNum,double TaxDBRate,double TaxDBNum,double OtherExpenses,int Paymethod,String ExpDate,String TermID ) {
			ResultSet rs = null;
			try {
					if (connect.isClosed()==true)
					{
						try {
							String UserID2=UserID;
							connect = CONN(UserID2, Pass, databasename, ip);
						} catch (Exception e) {
							Log.e(null, e.toString());
						}
					}
			} 
			catch (Exception e) {
				Log.e(null, e.toString());
			} 
			try {
				
				CallableStatement cal = connect.prepareCall("{Call RefItemAdd (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				cal.setInt(1, RefID);
				cal.setString(2, RefType);
				cal.setString(3, null); 
				cal.setString(4, EmployeeID);
				cal.setString(5, StockID); 
				cal.setString(6, Stock2ID);
				cal.setDouble(7, DiscountRate);
				cal.setDouble(8, DiscountNum );
				cal.setString(9, TaxNK);
				cal.setString(10, TaxDB);
				cal.setDouble(11, TaxRate);
				cal.setDouble(12, TaxNum);
				cal.setDouble(13, TaxNKRate);
				cal.setDouble(14, TaxNKNum);
				cal.setDouble(15, TaxDBRate);
				cal.setDouble(16, TaxDBNum);
				cal.setDouble(17, OtherExpenses);
				cal.setInt(18,Paymethod);
				cal.setString(19, ExpDate);
				cal.setString(20, TermID);
				cal.setString(21,EmployeeID2); 
				
				cal.execute();
				//rs = QuerySELECT(query);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 try{
			connect.close();} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	 public void AddRefItemDet(int  RefID ,String ItemID,float Quantity,float Price,float OpeningQtyC,float Cost,double Amount,int DetailID,String StockID3 ) {
			ResultSet rs = null;
			try {
					if (connect.isClosed()==true)
					{
						try {
							String UserID2=UserID;
							connect = CONN(UserID2, Pass, databasename, ip);
						} catch (Exception e) {
							Log.e(null, e.toString());
						}
					}
			} 
			catch (Exception e) {
				Log.e(null, e.toString());
			} 
			try {
				
				CallableStatement cal = connect.prepareCall("{Call EntryItemAdd (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");//,?,?,?
				cal.setInt(1, RefID);
				cal.setString(2, ItemID);
				cal.setDouble(3, Quantity); 
				cal.setDouble(4, Price);
				cal.setDouble(5, OpeningQtyC); 
				cal.setDouble(6, Cost);
				cal.setDouble(7, Amount);
				cal.setDouble(8, 0 );//Expenses
				cal.setString(9, "");//ControlNo
				cal.setString(10, "");//ExpDate
				cal.setString(11, "");//TaxDetID
				cal.setDouble(12, Amount);//SCostXNK
				cal.setDouble(13, Cost);//CostXNK
				cal.registerOutParameter(14,java.sql.Types.INTEGER); //cal.setInt(14, DetailID);
				cal.setDouble(15, 0);//CurrentQty
				cal.setDouble(16, 0);//tile
				cal.setDouble(17, 0);//Costtile
				cal.setString(18,"");//ObjectID
				cal.setDouble(19, 0);//TLHH
				cal.setDouble(20, 0);//CostHH
				cal.setBoolean(21, false);//iskm
				cal.setString(22, "");//KhuyenMaiID
				cal.setString(23, "");//DiscountMainKMITID
				cal.setString(24, "");//EmployeeID3
				cal.setString(25, "");//ObjectName3
				cal.setString(26, StockID3);
				cal.setString(27, "");//StockID3Name
				cal.setDouble(28,0);//Chietkhau3
				cal.setDouble(29, 0);//Tienchietkhau3
				cal.setDouble(30,0);//soluongkm
				cal.setBoolean(31, false);//(31,IsKmTay);
				cal.setDouble(32,0);//SLKMTay
				cal.setDouble(33,0);//PTTienKM
				cal.setDouble(34,0);//PriceUnit
				cal.setDouble(35,0);//Trongluong
				cal.setDouble(36,0);//TileKmcty
				cal.setDouble(37,0);//TileKmctyTien
				cal.setDouble(38,0);//TileKm2
				cal.setDouble(39,0);//TileKm2Tien
				cal.setDouble(40,0);//Price_Dis
				cal.setDouble(41,0);//Dis_Amount
			//	cal.setBoolean(42,false);//Is2km
				//cal.setString(43,"");//KhuyenMai2ID
				//cal.setString(44,"");//DiscountMain2KMITID
				cal.execute();
				//rs = QuerySELECT(query);

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			 try{
			connect.close();} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	 public String GetNextRefNo(String  RefDate ,String RefType,String RefNo,int chinhanh,int Month,int Year)
	 {
		 String refNo="";
		 ResultSet rs = null;
			try {
					if (connect.isClosed()==true)
					{
						try {
							String UserID2=UserID;
							connect = CONN(UserID2, Pass, databasename, ip);
						} catch (Exception e) {
							Log.e(null, e.toString());
						}
					}
			} 
			catch (Exception e) {
				Log.e(null, e.toString());
			} 
			try {
				
				CallableStatement cal = connect.prepareCall("{Call RefGetNextRefNo (?,?,?,?,?)}");
				cal.setString(1, RefType);
				cal.registerOutParameter(2,java.sql.Types.VARCHAR); 
				cal.setInt(3,Month);
				cal.setInt(4,Year);
				cal.setInt(5, chinhanh); 
					boolean hadResults =	cal.execute();
				 
				 
				 //refNo = cal.getInt(2);  

				 refNo = cal.getString(2);

			}catch(Exception e){}
			
			
		 String as=getRefno("Select top 1 Refno from refs where refno='"+refNo +"'","refNo");
		 if(as!="")
		 {
			 refNo=GetNextRefNo_tem(RefDate,RefType,RefNo,chinhanh,Month,Year);
		 }
		 return refNo;
	 }
	 public String getRefno(String query, String column) {
			String result = "";
			ResultSet rs =  QuerySELECT(query);
			try {
				while (rs.next()) {
					result = rs.getString(column);
				}

			} catch (SQLException e) {
				// TODO Auto-generated catch block
				result="";
				e.printStackTrace();
			}
			try{
				 connect.close();
				} 
			catch (SQLException e) {
					// TODO Auto-generated catch block
				result="";
					e.printStackTrace();
				}
			return result;
		}
	 public String GetNextRefNo_tem(String  RefDate ,String RefType,String RefNo,int chinhanh,int Month,int Year)
	 {
		 String refNo="";
		 ResultSet rs = null;
			try {
					if (connect.isClosed()==true)
					{
						try {
							String UserID2=UserID;
							connect = CONN(UserID2, Pass, databasename, ip);
						} catch (Exception e) {
							Log.e(null, e.toString());
						}
					}
			} 
			catch (Exception e) {
				Log.e(null, e.toString());
			} 
			try {
				
				CallableStatement cal = connect.prepareCall("{Call RefGetNextRefNotemp (?,?,?,?,?)}");
				cal.setString(1, RefType);
				cal.registerOutParameter(2,java.sql.Types.VARCHAR); 
				cal.setInt(3,Month);
				cal.setInt(4,Year);
				cal.setInt(5, chinhanh); 
				boolean hadResults =	cal.execute();
				 
				 refNo = cal.getString(2);
				
			}catch(Exception e){} 
		 return refNo;
	 }

	 public void DeleteRefError(int Refid)
	 {
		 String refNo="";
		 ResultSet rs = null;
			try {
					if (connect.isClosed()==true)
					{
						try {
							String UserID2=UserID;
							connect = CONN(UserID2, Pass, databasename, ip);
						} catch (Exception e) {
							Log.e(null, e.toString());
						}
					}
			} 
			catch (Exception e) {
				Log.e(null, e.toString());
			} 
			try {
				
				CallableStatement cal = connect.prepareCall("{Call RefDeleteError (?)}");
				cal.setInt(1, Refid);
				 
					boolean hadResults =	cal.execute(); 

			}catch(Exception e){}
			
		 
	 }
	 public void RefSearch2_web_chuaduyet(String  BeginDate ,String EndDate,String ObjectID,String UserID,int chinhanh,String CustGroup)
	 {
		 String refNo="";
		 ResultSet rs = null;
			try {
					if (connect.isClosed()==true)
					{
						try {
							String UserID2=UserID;
							connect = CONN(UserID2, Pass, databasename, ip);
						} catch (Exception e) {
							Log.e(null, e.toString());
						}
					}
			} 
			catch (Exception e) {
				Log.e(null, e.toString());
			} 
			try {
				
				CallableStatement cal = connect.prepareCall("{Call RefSearch2_web_chuaduyet (?,?,?,?,?,?,?)}");
				cal.setString(1, BeginDate);
				cal.setString(2, EndDate);
				cal.setString(3, ObjectID);
				cal.setString(4, UserID);
				cal.setString(5, "BG");
				cal.setInt(6, chinhanh);
				cal.setString(7, CustGroup); 
				boolean hadResults =	cal.execute();
				 
			 
			}catch(Exception e){} 
		  
	 }
	 public void RefSearch2_web_daduyet(String  BeginDate ,String EndDate,String ObjectID,String UserID,int chinhanh,String CustGroup)
	 {
		 String refNo="";
		 ResultSet rs = null;
			try {
					if (connect.isClosed()==true)
					{
						try {
							String UserID2=UserID;
							connect = CONN(UserID2, Pass, databasename, ip);
						} catch (Exception e) {
							Log.e(null, e.toString());
						}
					}
			} 
			catch (Exception e) {
				Log.e(null, e.toString());
			} 
			try {
				
				CallableStatement cal = connect.prepareCall("{Call RefSearch2_web_daduyet (?,?,?,?,?,?,?)}");
			 
				
				cal.setString(1, BeginDate);
				cal.setString(2, EndDate);
				cal.setString(3, ObjectID);
				cal.setString(4, UserID);
				cal.setString(5, "BG");
				cal.setInt(6, chinhanh);
				cal.setString(7, CustGroup); 
				boolean hadResults =	cal.execute();
				 
			 
			}catch(Exception e)
			{
				
			} 
		  
	 }
	 public void RefSearch2_web_All(String  BeginDate ,String EndDate,String ObjectID,String UserID,int chinhanh,String CustGroup)
	 {
		 String refNo="";
		 ResultSet rs = null;
			try {
					if (connect.isClosed()==true)
					{
						try {
							String UserID2=UserID;
							connect = CONN(UserID2, Pass, databasename, ip);
						} catch (Exception e) {
							Log.e(null, e.toString());
						}
					}
			} 
			catch (Exception e) {
				Log.e(null, e.toString());
			} 
			try {
				
				CallableStatement cal = connect.prepareCall("{Call RefSearch2_web (?,?,?,?,?,?,?)}");
				cal.setString(1, BeginDate);
				cal.setString(2, EndDate);
				cal.setString(3, ObjectID);
				cal.setString(4, UserID);
				cal.setString(5, "BG");
				cal.setInt(6, chinhanh);
				cal.setString(7, CustGroup); 
				boolean hadResults =	cal.execute();
				 
			 
			}catch(Exception e){} 
		  
	 }

}
