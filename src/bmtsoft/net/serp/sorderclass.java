package bmtsoft.net.serp;
import bmtsoft.net.serp.DBManage;
import model.Customer_Model;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ActionBar.LayoutParams;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import java.math.BigDecimal;
import java.nio.channels.SelectableChannel;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;

 
 
//import com.example.quanlygiaohang.R;

import android.widget.EditText;
import 	android.util.*;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.widget.EditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import model.LogChinhanh;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TabHost;
public class sorderclass extends Activity {
	//Spinner cbochinhanh;
	  TabHost tabHost;
	TableLayout tl;
	int width, height, nor;
	DBManage db = new DBManage();
	 TextView txtrefdate2;
	Button btndate1, btndate2, btnOk, btnselectcust, btnaddnew,btnExit,btBitems,btndoituong,btnRefesh2,btnsave,btnAddnew;
		Calendar cal;
		Date date1, date2;
	//Spinner cusGroup;
	//Vector groupIDs = new Vector();
	Spinner cusGroup;
	Vector groupIDs = new Vector();
	ArrayList<Customer_Model> clsm;
	 Customer_Model clsm2=new Customer_Model();
	TextView txtcustid,txtcontact,txtadd,txtnote2,txtsumamount,txttongtien,txtrefno;
	String sdoituong = "",nhom2;
	Spinner spdoituong;
	Vector<String> vDoituong;
	TextView tvnguoinhan, tvdiachi;
	LogChinhanh clsm4=new LogChinhanh();
	ArrayList<String> vhanghoa = new ArrayList<String>();
	ArrayList<String> vhanghoa2 = new ArrayList<String>();
	
	Vector<String> vnhomkh = new Vector<String>();
	String snhom = "";
	//EditText tv8;
	ArrayList result_chon_ban = new ArrayList();
	int count_chon2=1;
	ArrayAdapter<String> adgroupCust,adapcust;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sorder);
        TabHost host = (TabHost)findViewById(R.id.tabHost);
        host.setup();
        
        TabHost.TabSpec spec = host.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("Thông tin chung");
        host.addTab(spec);

        //Tab 2
        spec = host.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Hàng hóa");
        host.addTab(spec);
        
        Intent callerIntent=getIntent();
        Bundle packageFromCaller= callerIntent.getBundleExtra("MyPackage");

      //  int chinhanh=packageFromCaller.getInt("chinhanh");
       // String DatabaseName=packageFromCaller.getString("DatabaseName");
       // String UserName=packageFromCaller.getString("UserName");
        
        Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;
		
        txtrefdate2 = (TextView) findViewById(R.id.txtrefdate);
   	 	setDefaultDate();
   	 	tl = (TableLayout) findViewById(R.id.tldoituong);
   	 
   	  cusGroup = (Spinner) findViewById(R.id.cusGroup);
	   	vnhomkh = getGroupCust2();
	   	adgroupCust = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, vnhomkh);
	   	adgroupCust.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		cusGroup.setAdapter(adgroupCust);
	
   	 	spdoituong = (Spinner) findViewById(R.id.spDoituong);
		btndoituong = (Button) findViewById(R.id.btnselectcust);
		tvnguoinhan = (TextView) findViewById(R.id.tvnguoinhan);
		tvdiachi = (TextView) findViewById(R.id.tvdiachi);
		txtnote2 = (TextView) findViewById(R.id.txtnote);
		
		txtsumamount = (TextView) findViewById(R.id.txttongcong);
		txttongtien=(TextView) findViewById(R.id.txttongtien2);
		txtrefno=(TextView) findViewById(R.id.txtRefno);
		vDoituong = getDoituong();
		adapcust = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, vDoituong);
		adapcust.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spdoituong.setAdapter(adapcust);
		try{
			if (getIntent().hasExtra("MyPackage")) {
				Intent Caller = getIntent();
				Bundle bundle3 = Caller.getBundleExtra("MyPackage");
				sdoituong = bundle3.getString("CustID");
				nhom2 = bundle3.getString("Nhom");
				
			}
		}catch(Exception e){}
		
		try{
		if(!sdoituong.equals("")){
			int count = 0;
			for(int i=0;i<vDoituong.size();i++){
				String[] info = vDoituong.get(i).split("-");
				if(info[0].equals(sdoituong)){
					count = i;
					break;
				}
			}
			spdoituong.setSelection(count);
		}
		}catch(Exception e){}
	/*	
		try{
			if(!nhom2.equals("")){
				int count = 0;
				for(int i=0;i<vnhomkh.size();i++){
					String[] info = vnhomkh.get(i).split("-");
					if(info[0].equals(nhom2)){
						count = i;
						break;
					}
				}
				cusGroup.setSelection(count);
			}
			}catch(Exception e){}
		**/
		spdoituong.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				changeSpinnerSelectedItem(position);
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
	/*	 cusGroup.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> parent, View view,
						int position, long id) {
					// TODO Auto-generated method stub
					String[] info = vnhomkh.get(position).split("-");
				//	currkeyword = info[0];
					if (info[0].equals("~~~Tất cả~~~")) {
						info[0] = "";
					}
				 
			   		Vector<String> result = new Vector<String>();
			   		result.add("~~~Tất cả~~~");
					Intent callerIntent=getIntent();
			        Bundle packageFromCaller= callerIntent.getBundleExtra("MyPackage");
			        String query="Select o.ObjectID as CustID, ObjectName,isnull(c.phone,e.SoDienThoai) as phone,isnull(c.address,e.DiaChi) as address from objects o inner join customers c on CustID = ObjectID left join Employee e on o.ObjectID=e.EmployeeID where (c.Inactive=0 or e.Inactive=0) and  GroupID like N'"
							+ info[0] + "%'";
			       
			   		
					ResultSet rs = db.QuerySELECT(query);
					try {
						while (rs.next()) {
							result.add(rs.getString("CustID") + "-"
									+ rs.getString("ObjectName") + "-" 
									+ rs.getString("phone") + "-" 
									+ rs.getString("address"));
						}

					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					try{
						db.connect.close();
						} 
					catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					
					vDoituong = result; 
					adapcust.clear();
					adapcust.notifyDataSetChanged();
					adapcust.addAll(vDoituong); 
					try{
						if(!sdoituong.equals("")){
							int count = 0;
							for(int i=0;i<vDoituong.size();i++){
								 info = vDoituong.get(i).split("-");
								if(info[0].equals(sdoituong)){
									count = i;
									break;
								}
							}
							spdoituong.setSelection(count);
						}
						}catch(Exception e){} 
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {
					// TODO Auto-generated method stub

				}
			});
		 **/
   	 	btndate2 = (Button) findViewById(R.id.btnchondate);
   	 	btndate2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showDatePickerDialog1();
			}
   	 	});
   		 
   		btnExit = (Button) findViewById(R.id.btnexit10);
   		
   		btnExit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 
		           // System.exit(0);
				finish();
				Intent callerIntent=getIntent();
		        Bundle packageFromCaller= callerIntent.getBundleExtra("MyPackage");
		        int chinhanh=packageFromCaller.getInt("chinhanh");
		        String DatabaseName=packageFromCaller.getString("DatabaseName");
		        String UserName=packageFromCaller.getString("UserName");
		        doOpenChildActivity_exit(chinhanh, DatabaseName,UserName);
			        
			}
		});
   		btnRefesh2 = (Button) findViewById(R.id.btnRefesh);
   		btnRefesh2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				txtsumamount.setText("0");
				txttongtien.setText("0");
				try{
					vhanghoa.clear();
					
				}catch(Exception e){}
				try{
					vhanghoa2.clear();
					
				}catch(Exception e){}
				try{
					result_chon_ban.clear();
					
				}catch(Exception e){}
				
				try{makeTable();}catch(Exception e){}
			}
		});
        btndoituong.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent callerIntent=getIntent();
		        Bundle packageFromCaller= callerIntent.getBundleExtra("MyPackage");

		        int chinhanh=packageFromCaller.getInt("chinhanh");
		        String DatabaseName=packageFromCaller.getString("DatabaseName");
		        String UserName=packageFromCaller.getString("UserName");
		        try{
					if (getIntent().hasExtra("MyPackage")) {
						Intent Caller = getIntent();
						Bundle bundle3 = Caller.getBundleExtra("MyPackage"); 
						snhom=bundle3.getString("CustGroup");
					}
				}catch(Exception e){}
		        
		    	
		        Bundle bundle=new Bundle();
		        Intent myintent = new Intent(sorderclass.this,
						Tim_kiem_doi_tuong.class);
		    	   bundle.putStringArrayList("result_chon", result_chon_ban); 
		    	   bundle.putString("CustID", sdoituong); 
			       bundle.putInt("chinhanh", chinhanh);
			       bundle.putString("DatabaseName", DatabaseName);
			       bundle.putString("UserName", UserName);
			       bundle.putString("CustGroup", snhom); 
			       
			       myintent.putExtra("MyPackage", bundle);
			       
				startActivity(myintent);
			}
		});
    
        btBitems = (Button) findViewById(R.id.btnchonhh);
   		
       	btBitems.setOnClickListener(new OnClickListener() {

    			@Override
    			public void onClick(View v) {
    				// TODO Auto-generated method stub
    				
    				Intent callerIntent=getIntent();
    		        Bundle packageFromCaller= callerIntent.getBundleExtra("MyPackage");
    		       // String sdoituong2=packageFromCaller.getString("CustID");
  			        
    		        int chinhanh=packageFromCaller.getInt("chinhanh");
    		        String DatabaseName=packageFromCaller.getString("DatabaseName");
    		        String UserName=packageFromCaller.getString("UserName");
    		        Bundle bundle=new Bundle();
    				Intent myintent = new Intent(sorderclass.this,
    						Tim_hanghoa.class); 
    				  bundle.putStringArrayList("result_chon", result_chon_ban); 
    				 
   		    	   		bundle.putString("CustID", sdoituong);
    			       bundle.putInt("chinhanh", chinhanh);
    			       bundle.putString("DatabaseName", DatabaseName);
    			       bundle.putString("UserName", UserName);
    			       
    			       bundle.putString("Begindate", transformDate(txtrefdate2.getText().toString()));
    			       
    			       myintent.putExtra("MyPackage", bundle);
    			       
    				startActivity(myintent);
    			}
    		});
        
       	try{
			if (getIntent().hasExtra("MyPackage")) {
				Intent Caller = getIntent();
				Bundle bundle3 = Caller.getBundleExtra("MyPackage");
				result_chon_ban = bundle3.getStringArrayList("result_chon");
				if(result_chon_ban.size()>0)
				{
				makeTable();
				}
			}
		}catch(Exception e){}
        
       	btnsave = (Button) findViewById(R.id.btnok10);
       	btnsave.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
			 
				//setDefaultDate();
				String RefNo="",CustID,Contact,Addr,Memo,UserID,MasterAccount="1131",EmployeeID2,RefDate;
				double FCAmount=0;
				Intent callerIntent=getIntent();
		        Bundle packageFromCaller= callerIntent.getBundleExtra("MyPackage");

		       int Chinhanh=packageFromCaller.getInt("chinhanh");
		       String UserName=packageFromCaller.getString("UserName");
		       EmployeeID2=getDetail("select empsale from users where loginname=N'"+ UserName +"'","empsale");
		       Addr=tvdiachi.getText().toString();
		       Contact=tvnguoinhan.getText().toString();
		       Memo=txtnote2.getText().toString();
		       CustID=spdoituong.getSelectedItem().toString();
		       try{
		    	   FCAmount= Double.parseDouble(txttongtien.getText().toString());
		       }catch(Exception e){}
		       try{
		    	   final String[] info2 = CustID.split("-");
		       CustID=info2[0].toString();
		       }catch(Exception e){}
		       RefDate=txtrefdate2.getText().toString();
		       int Refid=1;  
		       String RefType="BG";
		       String  entr2="";
				 entr2=" select Refno from refs where RefType='BG' and refno=N'"+ txtrefno.getText() +"' and CustID=N'"+ CustID +"' and Refdate=N'"+ transformDate(RefDate) +"'";
				 String Tontai= "";
				 try{
	    		  Tontai=   db.getRefno(entr2,"Refno");
	    		  }catch(Exception e){
	    			  Tontai= "";
	    		  }
				 
				 if(Tontai=="")
	    		   {
				       RefNo=db.GetNextRefNo(RefDate, RefType, RefNo, Chinhanh, month(RefDate), year(RefDate));
				       try{
				       txtrefno.setText(RefNo);
				       }catch(Exception e){}
				       try{
				       Refid=db.AddRefs(transformDate(RefDate), RefType, RefNo, CustID, Contact, Addr, Memo, UserName, MasterAccount, FCAmount, EmployeeID2, Chinhanh);
				       if(Refid>0)
				       {
				    	   db.UpdateRefItem(Refid, RefType, "", "156", "", "", "", EmployeeID2, EmployeeID2, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, transformDate(RefDate), "");
				       
				    	   vhanghoa2=result_chon_ban;
				    	 
							for (int i = 0; i < vhanghoa2.size(); i++) 
							{
								final String[] info2 = vhanghoa2.get(i).split("_");
								String ItemID="";
								try{
									ItemID=info2[1].toString();
								}catch(Exception e){}
								float Price = Float.parseFloat(info2[3]) ;
								float Quantity = Float.parseFloat(info2[5]) ;
								float OpeningQtyC =Float.parseFloat(info2[4]) ;
								double Amount = Double.valueOf(info2[6]) ;
								float Cost = Float.parseFloat(info2[3]) ;
								int DetailID=0;
								String StockID3="";
								//StockID3 = info2[7].toString(); ;
								
								db.AddRefItemDet(Refid, ItemID, Quantity, Price, OpeningQtyC, Cost, Amount, DetailID, StockID3);
							}
							EnableTextE();
				       }
				       }catch(Exception e)
				       {
				    	   try
			                {
			                    showDialog("Lỗi lưu phiếu,phiếu này sẽ bị xóa.Bạn cần tạo lại!");
			                }
			                catch (Exception e2)
			                {
			                    e2.printStackTrace();
			                }
				    	   db.DeleteRefError(Refid);
				       }
				       
	    		   }
		       
			}
		});
   		
       	btnAddnew = (Button) findViewById(R.id.btnAddNew);
   		
       	btnAddnew.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 clearText();
				 EnableTextA();
			}
		});
	}
	
	public ArrayList getResult(String query) {
		ArrayList result = new ArrayList();
		int count = 0;

		ResultSet rs = db.QuerySELECT(query);
		try {
			while (rs.next()) {
				String res = count + "_" + rs.getString("CustID") + "_"
						+ rs.getString("ObjectName") + "_"
						+ rs.getString("Address") + "_"
						+ rs.getString("GroupID");
				result.add(res);
		 
				count++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public void doOpenChildActivity_exit(int chinhanh,String DatabaseName,String UserName)
    {
        Bundle bundle=new Bundle();
        Intent myIntent=new Intent(this, mainlogin.class);
       bundle.putInt("chinhanh", chinhanh);
       bundle.putString("DatabaseName", DatabaseName);
       bundle.putString("UserName", UserName);
       myIntent.putExtra("MyPackage", bundle);
        startActivity(myIntent);
    }
	public int month(String sDate) {
		String strArrtmp[] = sDate.split("/");
		int day = Integer.parseInt(strArrtmp[0]);
		int month = Integer.parseInt(strArrtmp[1]);
		int year = Integer.parseInt(strArrtmp[2]);
		int result = month ;
		return result;
	}
	public int year(String sDate) {
		String strArrtmp[] = sDate.split("/");
		int day = Integer.parseInt(strArrtmp[0]);
		int month = Integer.parseInt(strArrtmp[1]);
		int year = Integer.parseInt(strArrtmp[2]);
		int result = year;
		return result;
	}
	public String transformDate(String sDate) {
		String strArrtmp[] = sDate.split("/");
		int day = Integer.parseInt(strArrtmp[0]);
		int month = Integer.parseInt(strArrtmp[1]);
		int year = Integer.parseInt(strArrtmp[2]);
		String result = month + "/" + day + "/" + year;
		return result;
	}
	public String transformDate2(String sDate) {
		String strArrtmp[] = sDate.split("/");
		int day = Integer.parseInt(strArrtmp[0])+1;
		int month = Integer.parseInt(strArrtmp[1]);
		int year = Integer.parseInt(strArrtmp[2]);
		String result="";
		if(day<10 && month<10  ){ 
		  result = "0"+ day + "/" + "0"+ month + "/" + year;
		}else if(day<10 && month>10  ){ 
			  result = "0"+ day + "/" + month + "/" + year;
			}
		else if(day>10 && month<10  ){ 
		  result =  day + "/" +"0"+ month + "/" + year;
		}else  {
			  result =  day + "/" + month + "/" + year;
			}
		return result;
	}
	public void setDefaultDate() {
		 
		cal = Calendar.getInstance();
		SimpleDateFormat dft = null;
		// form day/month/year
		dft = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
		String strDate = dft.format(cal.getTime());
		strDate=transformDate2(strDate);
		// show text
		txtrefdate2.setText(strDate); 
	}
	public void showDatePickerDialog1() {
		OnDateSetListener callback = new OnDateSetListener() {
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// Update TextView1
				txtrefdate2.setText((dayOfMonth) + "/" + (monthOfYear + 1) + "/"
						+ year);
				// update date1
				cal.set(year, monthOfYear, dayOfMonth);
				date1 = cal.getTime();
			}
		};

		String s = txtrefdate2.getText() + "";
		String strArrtmp[] = s.split("/");
		int day = Integer.parseInt(strArrtmp[0]);
		int month = Integer.parseInt(strArrtmp[1]) - 1;
		int year = Integer.parseInt(strArrtmp[2]);
		DatePickerDialog pic = new DatePickerDialog(sorderclass.this,
				callback, year, month, day);
		pic.setTitle("Chọn ngày:");
		pic.show();
	}
	public Vector<String> getGroupCust2() {
		Vector<String> result = new Vector<String>();
		//result.add("~~~Tất cả~~~");
		snhom="";
		  Intent callerIntent=getIntent();
		  try{
				if (getIntent().hasExtra("MyPackage")) {
					Intent Caller = getIntent();
					Bundle bundle3 = Caller.getBundleExtra("MyPackage"); 
					try{
					snhom=bundle3.getString("CustGroup").toString();
					}catch(Exception e){snhom="";}
				}
			}catch(Exception e){}
			 
		 String sql="Select * from custgroups where Inactive=0";
		//if(snhom.contains("HUYNH")==true ||snhom=="" || snhom==null )
		//{
		result.add("~~~Tất cả~~~");
		//}
		//else
		//{
			//sql=sql+"  and  groupID=N'"+ snhom  +"'";
		//}
		ResultSet rs = db.QuerySELECT(sql);
		try {
			while (rs.next()) {
				String res = rs.getString("GroupID") + "-"
						+ rs.getString("GroupName");
				result.add(res);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	public void doOpenChildActivity(int chinhanh,String DatabaseName,String UserName)
    {
        Bundle bundle=new Bundle();
        Intent myIntent=new Intent(this, Tim_kiem_doi_tuong.class);
       bundle.putInt("chinhanh", chinhanh);
       bundle.putString("DatabaseName", DatabaseName);
       bundle.putString("UserName", UserName);
       myIntent.putExtra("MyPackage", bundle);
        startActivity(myIntent);
    }
	public Vector<String> getDoituong() {
		Vector<String> result = new Vector<String>();
		
		Intent callerIntent=getIntent();
        Bundle packageFromCaller= callerIntent.getBundleExtra("MyPackage");
        String query="Select ObjectID as CustID, ObjectName,c.phone,c.address from Objects o left join Customers c on c.CustID=o.ObjectID left join Employee e on e.EmployeeID=o.ObjectID  where (ObjectTypeID & 1=1 or ObjectTypeID & 3=3 or ObjectTypeID & 4=4) and (c.Inactive=0 or e.Inactive=0)   ";
        String UserName2=packageFromCaller.getString("UserName"); 
	      String EmployeeID2=getDetail("select empsale from users where loginname=N'"+ UserName2 +"'","empsale");
		String str2="select CustID_emp as count from CustID_emps where  EmployeeID='" + EmployeeID2 + "'";
          
           ResultSet rs2 = db.QuerySELECT(str2);
           String resn ="";
   		try {
   			while (rs2.next()) {
   				 resn = rs2.getString("count")  ;
   			}
   		} catch (SQLException e) {
   			 
   		}
   		if (resn.trim().toString().isEmpty() == true)
           {}else{
        	   query = query + " and ObjectID in(select CustID_emp from dbo.CustID_emps where  EmployeeID=N'" + EmployeeID2 + "')";
           }
   		
		ResultSet rs = db.QuerySELECT(query);
		try {
			while (rs.next()) {
				result.add(rs.getString("CustID") + "-"
						+ rs.getString("ObjectName") + "-" 
						+ rs.getString("phone") + "-" 
						+ rs.getString("address"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			db.connect.close();
			} 
		catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return result;
	}
	public void doOpenChildActivity2(int chinhanh,String DatabaseName,String UserName)
	{
        Bundle bundle=new Bundle();
        Intent myIntent=new Intent(this, MainActivity.class);
       bundle.putInt("chinhanh", chinhanh);
       bundle.putString("DatabaseName", DatabaseName);
       bundle.putString("UserName", UserName);
       myIntent.putExtra("MyPackage", bundle);
        startActivity(myIntent);
    }
	public void changeSpinnerSelectedItem(int i) {
		String[] selecteditem = vDoituong.get(i).split("-");
		String id = selecteditem[0];
		try{
			String nguoinhan = selecteditem[1];
			tvnguoinhan.setText(nguoinhan);
			}
			catch(Exception x){}
		String diachi = getDetail("Select Address From Customers where CustID = N'" + id + "'","Address");
		if(diachi=="")
		{
			 diachi = getDetail("Select diachi as Address From Employee where EmployeeID = N'" + id + "'","Address");
		}
		tvdiachi.setText(diachi);
	 	
		Intent callerIntent=getIntent();
        Bundle packageFromCaller= callerIntent.getBundleExtra("MyPackage");

        int chinhanh2=packageFromCaller.getInt("chinhanh");
        String DatabaseName2=packageFromCaller.getString("DatabaseName");
        String UserName2=packageFromCaller.getString("UserName");
        ArrayList result_chon = new ArrayList();
        result_chon=packageFromCaller.getStringArrayList("result_chon");
        Intent myIntent=new Intent(this, sorderclass.class);
        sdoituong=id;
		Bundle bundle = new Bundle();
		bundle.putString("CustID", id); 
		bundle.putStringArrayList("result_chon", result_chon); 
		  bundle.putInt("chinhanh", chinhanh2);
	       bundle.putString("DatabaseName", DatabaseName2);
	        bundle.putString("UserName",UserName2);
	    
	       try{  
	    String Nhom=cusGroup.getSelectedItem().toString();
		       
		      
		    	   final String[] info2 = Nhom.split("-");
		    	   Nhom=info2[0].toString();
		      
		   	bundle.putString("Nhom", Nhom); 
		   	 }catch(Exception e){}
		myIntent.putExtra("MyPackage", bundle);
		
	}
	public String getDetail(String query, String column) {
		String result = "";
		ResultSet rs = db.QuerySELECT(query);
		try {
			while (rs.next()) {
				result = rs.getString(column);
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try{
			db.connect.close();
			} 
		catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return result;
	}

	public void showDialog(final String phone) throws Exception
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(sorderclass.this);

        builder.setMessage("Cảnh báo: " + phone);
/*
        builder.setPositiveButton("Thoát", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);// (Intent.ACTION_CALL);

                callIntent.setData(Uri.parse("Thoát" + phone));

                startActivity(callIntent);

                dialog.dismiss();
            }
        });
*/
        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });

        builder.show();
    }
	
	public void FixTableForm() {
		TableRow tr = new TableRow(this);
		tr.setBackgroundColor(Color.WHITE);

		String[] columnname = { "#", "Mã", "Tên","Giá","Đặt.T","Đ.Lẻ","T.T","Sửa","Kho","Xóa"};//"HSQĐ",

		TableRow.LayoutParams lp = new TableRow.LayoutParams(width / 20,
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp1 = new TableRow.LayoutParams((width / 10),
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp2 = new TableRow.LayoutParams(3 * (width / 10),
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp3 = new TableRow.LayoutParams( (width /9),
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp4 = new TableRow.LayoutParams((width / 10),
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp5 = new TableRow.LayoutParams(width /10,
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp6 = new TableRow.LayoutParams(width /7,
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp7 = new TableRow.LayoutParams(width / 8,
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp8 = new TableRow.LayoutParams(width /9,
				LayoutParams.WRAP_CONTENT);
		 
		TableRow.LayoutParams lp9 = new TableRow.LayoutParams(width /9,
				LayoutParams.WRAP_CONTENT);
	 
		TextView tv = new TextView(this);
		tv.setPadding(20, 20, 20, 20);
		tv.setBackgroundColor(Color.WHITE);
		tv.setText(columnname[0]);
		tv.setWidth(width / 20);
		tv.setHeight(100);
		tv.setTextColor(Color.BLACK);
		tv.setGravity(Gravity.CENTER);
		// make border
		GradientDrawable gd = new GradientDrawable();
		gd.setStroke(1, Color.BLACK);
		tv.setBackgroundDrawable(gd);
		tv.setLayoutParams(lp);
		tr.addView(tv);

		TextView tv2 = new TextView(this);
		tv2.setPadding(20, 20, 20, 20);
		tv2.setBackgroundColor(Color.WHITE);
		tv2.setText(columnname[1]);
		tv2.setHeight(100);
		tv2.setWidth((width / 10));
		tv2.setTextColor(Color.BLACK);
		tv2.setGravity(Gravity.CENTER);
		tv2.setBackgroundDrawable(gd);
		tv2.setLayoutParams(lp1);
		tr.addView(tv2);

		TextView tv3 = new TextView(this);
		tv3.setPadding(20, 20, 20, 20);
		tv3.setBackgroundColor(Color.WHITE);
		tv3.setText(columnname[2]);
		tv3.setWidth(3 * (width / 10));
		tv3.setTextColor(Color.BLACK);
		tv3.setGravity(Gravity.CENTER);
		tv3.setBackgroundDrawable(gd);
		tv3.setHeight(100);
		tv3.setLayoutParams(lp2);
		tr.addView(tv3);

		TextView tv4 = new TextView(this);
		tv4.setPadding(20, 20, 20, 20);
		tv4.setBackgroundColor(Color.WHITE);
		tv4.setText(columnname[3]);
		tv4.setWidth( (width /12));
		tv4.setTextColor(Color.BLACK);
		tv4.setGravity(Gravity.CENTER);
		tv4.setBackgroundDrawable(gd);
		tv4.setLayoutParams(lp3);
		tv4.setHeight(100);
		tr.addView(tv4);

		TextView tv5 = new TextView(this);
		tv5.setPadding(20, 20, 20, 20);
		tv5.setBackgroundColor(Color.WHITE);
		tv5.setText(columnname[4]);
		tv5.setWidth( (width / 12));
		tv5.setTextColor(Color.BLACK);
		tv5.setGravity(Gravity.CENTER);
		tv5.setBackgroundDrawable(gd);
		tv5.setLayoutParams(lp4);
		tv5.setHeight(100);
		tr.addView(tv5);

		TextView tv6 = new TextView(this);
		tv6.setPadding(20, 20, 20, 20);
		tv6.setBackgroundColor(Color.WHITE);
		tv6.setText(columnname[5]);
		tv6.setWidth(width /7);
		tv6.setTextColor(Color.BLACK);
		tv6.setGravity(Gravity.CENTER);
		tv6.setBackgroundDrawable(gd);
		tv6.setLayoutParams(lp5);
		tv6.setHeight(100);
		tr.addView(tv6);
		 	
	 	TextView tv8 = new TextView(this);
		tv8.setPadding(20, 20, 20, 20);
		tv8.setBackgroundColor(Color.BLUE);
		tv8.setText(columnname[6]);
		 tv8.setWidth(width /9);
		tv8.setTextColor(Color.BLACK);
		tv8.setGravity(Gravity.LEFT);
		tv8.setBackgroundDrawable(gd);
		 tv8.setLayoutParams(lp6);
		 tv8.setHeight(100);
		tr.addView(tv8);
 
		TextView tv9 = new TextView(this);
		tv9.setPadding(20, 20, 20, 20);
		tv9.setBackgroundColor(Color.BLUE);
		tv9.setText(columnname[7]);
		tv9.setWidth(width / 10);
		tv9.setTextColor(Color.BLACK);
		tv9.setGravity(Gravity.LEFT);
		tv9.setBackgroundDrawable(gd);
		tv9.setLayoutParams(lp7);
		tv9.setHeight(100);
		tr.addView(tv9);
		 
				
		TextView tv10 = new TextView(this);
		tv10.setPadding(20, 20, 20, 20);
		tv10.setBackgroundColor(Color.WHITE);
		tv10.setText(columnname[8]);
		tv10.setWidth(width /9);
		tv10.setTextColor(Color.BLACK);
		tv10.setGravity(Gravity.CENTER);
		tv10.setBackgroundDrawable(gd);
		tv10.setLayoutParams(lp8);
		tv10.setHeight(100);
		tr.addView(tv10);
		
		TextView tv11 = new TextView(this);
		tv11.setPadding(20, 20, 20, 20);
		tv11.setBackgroundColor(Color.WHITE);
		tv11.setText(columnname[9]);
		tv11.setWidth(width /9);
		tv11.setTextColor(Color.BLACK);
		tv11.setGravity(Gravity.CENTER);
		tv11.setBackgroundDrawable(gd);
		tv11.setLayoutParams(lp9);
		tv11.setHeight(100);
		tr.addView(tv11);
		
		tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
	}
		public void makeTable() {
		// make column index
		tl.removeAllViews();
		FixTableForm();
		vhanghoa=result_chon_ban;
		double newSum=0;
		int i = 0;
		for ( i = 0; i < vhanghoa.size(); i++) {
			TableRow tr2 = new TableRow(this);
			tr2.setBackgroundColor(Color.WHITE);
			final String[] info = vhanghoa.get(i).split("_");
			try{
				  final double value = Double.parseDouble(info[6]);
			        newSum +=value;// Math.round(value * 100) / 100.0;
			}catch(Exception e)
			{}
			 DecimalFormat format211213 = new DecimalFormat("#,##0;");
				 
				String formatted211 = format211213.format(newSum); 
				
			txtsumamount.setText(formatted211);
			
			txttongtien.setText(String.valueOf(newSum) );
			// stt
			TextView tv = new TextView(this);
			tv.setText(info[0]);
			tv.setTextColor(Color.BLACK);
			tv.setBackgroundColor(Color.WHITE);
			tv.setGravity(Gravity.LEFT);
			GradientDrawable gd = new GradientDrawable();
			gd.setStroke(1, Color.BLACK);
			tv.setBackgroundDrawable(gd);
			TableRow.LayoutParams lp = new TableRow.LayoutParams(width / 20,
					LayoutParams.MATCH_PARENT);
			tv.setLayoutParams(lp);
			tv.setPadding(20, 20, 20, 20);
			tr2.addView(tv);

			// ma kh
			TextView tv1 = new TextView(this);
			tv1.setText(info[1]);
			tv1.setTextColor(Color.BLACK);
			tv1.setBackgroundColor(Color.WHITE);
			tv1.setGravity(Gravity.LEFT);
			tv1.setBackgroundDrawable(gd);
			TableRow.LayoutParams lp1 = new TableRow.LayoutParams((width / 10),
					LayoutParams.MATCH_PARENT);
			tv1.setLayoutParams(lp1);
			tv1.setPadding(20, 20, 20, 20);
			tr2.addView(tv1);

			// tenkh
			TextView tv2 = new TextView(this);
			tv2.setText(info[2]);
			tv2.setTextColor(Color.BLACK);
			tv2.setBackgroundColor(Color.WHITE);
			tv2.setGravity(Gravity.LEFT);
			tv2.setBackgroundDrawable(gd);
			TableRow.LayoutParams lp2 = new TableRow.LayoutParams(
					3 * (width / 10), LayoutParams.MATCH_PARENT);
			tv2.setLayoutParams(lp2);
			tv2.setPadding(20, 20, 20, 20);
			tr2.addView(tv2);
 
			// nhom kh
			TextView tv4 = new TextView(this);
			//tv4.setText(info[4]);
			tv4.setTextColor(Color.BLACK);
			tv4.setBackgroundColor(Color.WHITE);
			tv4.setGravity(Gravity.RIGHT);
			tv4.setBackgroundDrawable(gd);
			TableRow.LayoutParams lp4 = new TableRow.LayoutParams((width / 14),
					LayoutParams.MATCH_PARENT);
			tv4.setLayoutParams(lp4);
			tv4.setPadding(20, 20, 20, 20);
			
			try{
				DecimalFormat format21 = new DecimalFormat("#,##0;-#,##0");
				Double value22 = Double.valueOf(info[3]) ;
				String formatted22 = format21.format(value22); 
				tv4.setText(formatted22);
				}catch(Exception e){tv4.setText(info[3]);}
			
			tr2.addView(tv4);
 
 
			EditText tv8 = new EditText(this);
						tv8.setPadding(20, 20, 20, 20);
						tv8.setBackgroundColor(Color.BLUE);
					 	//tv8.setText(info[6]);
						  tv8.setWidth(width /10);
						tv8.setTextColor(Color.BLACK);
						tv8.setGravity(Gravity.RIGHT);
						tv8.setBackgroundDrawable(gd);
						 TableRow.LayoutParams lp6 = new TableRow.LayoutParams((width /10),
							  LayoutParams.MATCH_PARENT);
						 
						 DecimalFormat format21 = new DecimalFormat("#,##0;-#,##0");
							Double value21 = Double.valueOf(info[4]) ;
							String formatted21 = format21.format(value21); 
							tv8.setText(formatted21);
							 
						 
						 tv8.setLayoutParams(lp6);
						tr2.addView(tv8);
			 		
						EditText tv9 = new EditText(this);
						tv9.setPadding(20, 20, 20, 20);
						tv9.setBackgroundColor(Color.BLUE);
						//tv9.setText(info[7]);
						tv9.setWidth(width / 12);
						tv9.setTextColor(Color.BLACK);
						tv9.setGravity(Gravity.RIGHT);
						tv9.setBackgroundDrawable(gd);
					 
						 TableRow.LayoutParams lp8 = new TableRow.LayoutParams((width / 12),
							 LayoutParams.MATCH_PARENT);

						 DecimalFormat format211 = new DecimalFormat("#,##0;-#,##0");
							Double value211 = Double.valueOf(info[5]) ;
							String formatted21133 = format211.format(value211); 
							tv9.setText(formatted21133);
							
						 tv9.setLayoutParams(lp8);
						tr2.addView(tv9);
						 
						TextView tv10 = new TextView(this);
						//tv10.setText(info[8]);
						tv10.setWidth(width /8);
						tv10.setTextColor(Color.BLACK);
						tv10.setBackgroundColor(Color.WHITE);
						tv10.setGravity(Gravity.RIGHT);
						tv10.setBackgroundDrawable(gd);
						TableRow.LayoutParams lp10 = new TableRow.LayoutParams((width /7),
								LayoutParams.MATCH_PARENT);
						tv10.setLayoutParams(lp10);
						
						 DecimalFormat format2111 = new DecimalFormat("#,##0;-#,##0");
							Double value2111 = Double.valueOf(info[6]) ;
							String formatted2112 = format2111.format(value2111); 
							tv10.setText(formatted2112);
						tv10.setPadding(20, 20, 20, 20);
						tr2.addView(tv10);
						Button btnupdate = new Button(this); 
						btnupdate.setText("Sửa"); 
						btnupdate.setBackgroundDrawable(gd);
						TableRow.LayoutParams lp71 = new TableRow.LayoutParams(width /9,
								LayoutParams.MATCH_PARENT);
						btnupdate.setLayoutParams(lp71);
						btnupdate.setPadding(20, 20, 20, 20);
						tr2.addView(btnupdate);
						btnupdate.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								
								String ItemName = info[2];
								String id = info[1];
								String Stockid2 = info[7];
								Bundle bundle = new Bundle(); 
								 TableRow tablerow = (TableRow)v.getParent();
								 Button bn = (Button)tablerow.getChildAt(7);
							 if(bn.getText()=="Sửa")
							 {							
								 

								//số lượng lẻ
								 TextView items = (TextView) tablerow.getChildAt(5);
								 String str = items.getText().toString();
								 //thùng
								 TextView sl = (TextView) tablerow.getChildAt(4);
								 String str2 = sl.getText().toString();
								 
								 //TextView gia = (TextView) tablerow.getChildAt(5);
								 String gia2 = info[3];
								 //String gia2 = gia.getText().toString();
								 
								// TextView hs = (TextView) tablerow.getChildAt(8);
								 String hs3 = info[8];//hs.getText().toString();
								 //Tồn kho
								 TextView slthung = (TextView) tablerow.getChildAt(3);
								 String stslthung = slthung.getText().toString();
								 //thùng
								 TextView slle = (TextView) tablerow.getChildAt(4);
								 String sllele = slle.getText().toString();
								 
								
								 float price=0,slc=0,slp=0,hsqd=0;
								 double amount=0;
								 try{
									 price=Float.parseFloat(gia2);
								 }catch(Exception e)
								 {}
								 try{
									 slc=Float.parseFloat(str2);
								//	 vhanghoa.set(4,str2);
									// info[4]=str2;
									 //info[6]=slc.toString();
								 }catch(Exception e)
								 {
									 sl.setText("0");
								 }
								 try{
									 slp=Float.parseFloat(str);
									// vhanghoa.set(5,str);
									// info[5]=str;
								 }catch(Exception e)
								 {
									 items.setText("0");
									 
									 }
								 try{
									 hsqd=Float.parseFloat(hs3);
								 }catch(Exception e)
								 {}
								 float sltong=0,sldat=0; 
									try{
										sltong=hsqd*Float.parseFloat(stslthung)+Float.parseFloat(sllele);
									}catch(Exception e)
									{}
									try{
										sldat=hsqd*slc+slp;
									}catch(Exception e)
									{}
									 
									
								// float a=((slc+(slp/hsqd))*price);
								 try{
									 amount=(double) Math.round(((slc+(slp/hsqd))*price));
								 }catch(Exception e)
								 {}
								 try{
									 TextView am = (TextView) tablerow.getChildAt(6);
									 
									 
									//am.setText(String.format("%.2f", amount)  );
									 DecimalFormat format = new DecimalFormat("#,##0;-#,##0");
										
										Double value = Double.valueOf(amount) ;
										String formatted = format.format(value); 
										am.setText(formatted);
										 info[6]=value.toString();
										// vhanghoa.set(6, value.toString());
									 //  am.setText(new BigDecimal(Double.toString(amount)).toPlainString());
										// vhanghoa.set(6, value.toString());
										 
										 for(int j2=0; j2 < result_chon_ban.size(); j2++)
										 {
											 String mang="",itemid="";
											 mang=result_chon_ban.get(j2).toString();
											 final String[] info2 = mang.split("_");
											 itemid=info2[1].toString();
											 if(itemid.trim().toUpperCase().compareTo(id.trim().toUpperCase())==0)
											 {
												 try{
													 float count_chon=0;
													 try{count_chon=Float.parseFloat(info[0].toString());
													 }catch(Exception e){}
													 String res2 =count_chon + "_" + itemid + "_"
																+ ItemName + "_" 											 
																+ price + "_"
																		+ slc + "_"
																				+  slp + "_"
																				+ new BigDecimal(Double.toString(amount)).toPlainString() + "_"
																						+  Stockid2 + "_"
																				+ hsqd;
													 result_chon_ban.set(j2,res2);
													// count_chon2--; 
												 }catch(Exception e){}
												// bn.setText("Chọn");
												
												makeTable();
													 
											 }
											 
										 }
										 
								 }catch(Exception e)
								 {}
								 
							// vhanghoa.set(i,  info);
							 }
							 
								 
							}
						});
						
						TextView tv11 = new TextView(this);
						tv11.setText(info[7]);
						tv11.setWidth(width /8);
						tv11.setTextColor(Color.BLACK);
						tv11.setBackgroundColor(Color.WHITE);
						tv11.setGravity(Gravity.RIGHT);
						tv11.setBackgroundDrawable(gd);
						TableRow.LayoutParams lp1tv11 = new TableRow.LayoutParams((width /10),
								LayoutParams.MATCH_PARENT);
						tv11.setLayoutParams(lp1tv11);
						tv11.setPadding(20, 20, 20, 20);
						tr2.addView(tv11);
						
						Button btn = new Button(this);
						btn.setText("Xóa");						
						
						TableRow.LayoutParams lp7 = new TableRow.LayoutParams(width /9,
								LayoutParams.MATCH_PARENT);
						btn.setLayoutParams(lp7);
						  btn.setBackgroundDrawable(gd);
						btn.setPadding(20, 20, 20, 20);
						tr2.addView(btn);
			 
						btn.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								
								String ItemName = info[2];
								String id = info[1];
								Bundle bundle = new Bundle(); 
								 TableRow tablerow = (TableRow)v.getParent();
								 Button bn = (Button)tablerow.getChildAt(9);
							  if(bn.getText()=="Xóa")
							 {
								 for(int i=0; i < result_chon_ban.size(); i++)
								 {
									 String mang="",itemid="";
									 mang=result_chon_ban.get(i).toString();
									 final String[] info2 = mang.split("_");
									 itemid=info2[1].toString();
									 if(itemid.trim().toUpperCase().compareTo(id.trim().toUpperCase())==0)
									 {
										 try{
											 result_chon_ban.remove(i);
											 count_chon2--; 
										 }catch(Exception e){}
										// bn.setText("Chọn");
										
										makeTable();
											 
									 }
									 
								 }
									 
								
							 }
								 
							}
						});
						
						
					
						
			tl.addView(tr2, new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		}
	}
	
	private void clearText()
	{
		String RefNo="";
		String  RefDate=txtrefdate2.getText().toString();
		  RefNo=db.GetNextRefNo(RefDate, "BG", RefNo, 1, month(RefDate), year(RefDate));
		   try{
		       txtrefno.setText(RefNo);
		       }catch(Exception e){}
		txtnote2.setText("");;
		txtsumamount.setText("0");
		txttongtien.setText("0");
		try{
			vhanghoa.clear();
			
		}catch(Exception e){}
		try{
			vhanghoa2.clear();
			
		}catch(Exception e){}
		try{
			result_chon_ban.clear();
			
		}catch(Exception e){}
		try{
			ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
					android.R.layout.simple_spinner_item, vDoituong);
			aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			spdoituong.setAdapter(aa);
			
		}catch(Exception e){}
		try{makeTable();}catch(Exception e){}
	}

	private void EnableTextE()
	{
		//txtcontact.setable;
	 
		txtnote2.setEnabled(false); 
		try{
			 
			spdoituong.setEnabled(false);
			btndoituong.setEnabled(false);
			btnsave.setEnabled(false);
			btBitems.setEnabled(false);
			btndate2.setEnabled(false);
			btnRefesh2.setEnabled(false);
			
		}catch(Exception e){}
	}
	private void EnableTextA()
	{
		//txtcontact.setable;
		 
		txtnote2.setEnabled(true);
	 
		try{
			 
			spdoituong.setEnabled(true);
			btndoituong.setEnabled(true);
			btnsave.setEnabled(true);
			btBitems.setEnabled(true);
			btndate2.setEnabled(true);
			btnRefesh2.setEnabled(true);
		}catch(Exception e){}
	}
}
