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
public class Reports2_detail extends Activity {
	//Spinner cbochinhanh;
	  TabHost tabHost;
	TableLayout tl;
	int width, height, nor;
	DBManage db = new DBManage();
	 TextView txtrefdate2,spdoituong;
	Button btndate1, btndate2, btnOk, btnselectcust, btnaddnew,btnExit,btBitems,btndoituong,btnRefesh2,btnsave,btnAddnew;
		Calendar cal;
		Date date1, date2;
 	TextView txtcustid,txtcontact,txtadd,txtnote2,txtsumamount,txttongtien;
	String sdoituong = "";
	//Spinner spdoituong;
	Vector<String> vDoituong;
	TextView tvnguoinhan, tvdiachi;
	LogChinhanh clsm4=new LogChinhanh();
	ArrayList<String> vhanghoa = new ArrayList<String>();
	ArrayList<String> vhanghoa2 = new ArrayList<String>();
	String Refno="";
	Vector<String> vnhomkh = new Vector<String>();
	
	//EditText tv8;
	ArrayList result_chon_ban = new ArrayList();
	int count_chon2=1;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.report2_detail);
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
       
        
        Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		width = size.x;
		height = size.y;
		
        txtrefdate2 = (TextView) findViewById(R.id.txtrefdate);
   	 	setDefaultDate();
   	 	tl = (TableLayout) findViewById(R.id.tldoituong);
   	 
   	 
   	 	spdoituong = (TextView) findViewById(R.id.spDoituong);
		btndoituong = (Button) findViewById(R.id.btnselectcust);
		tvnguoinhan = (TextView) findViewById(R.id.tvnguoinhan);
		tvdiachi = (TextView) findViewById(R.id.tvdiachi);
		txtnote2 = (TextView) findViewById(R.id.txtnote);
		
		txtsumamount = (TextView) findViewById(R.id.txttongcong);
		txttongtien=(TextView) findViewById(R.id.txttongtien2);
		Refno=packageFromCaller.getString("RefNo");
		 
		String refdate2 = packageFromCaller.getString("refdate");
		txtrefdate2.setText(refdate2); 
		
		String id = packageFromCaller.getString("CustID");
		spdoituong.setText(id);
		String nguoinhan = packageFromCaller.getString("CustName");
		tvnguoinhan.setText(nguoinhan);
		String diachi = getDetail("Select Address From Customers where CustID = N'" + id + "'","Address");
		tvdiachi.setText(diachi); 
   	 	 
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
   		   
        
       	try{
       	 
       		String str2="select d.Openingqtyc,d.Itemid,d.Amount,d.price,i.ItemName,d.stockid3,d.Quantity,i.Unitcoef  from Refs r inner join refitemdet d on r.refid=d.refid inner join items i on i.itemid=d.itemid where  Refno='" + Refno + "'";
       		result_chon_ban = getResult_DO(str2);
       		makeTable();
				
			 
		}catch(Exception e){}
        
	}
	public ArrayList getResult_DO(String query) {
		ArrayList result = new ArrayList();
		int count = 0;

		ResultSet rs = db.QuerySELECT(query);
		try {
			int count_chon=1;
			while (rs.next()) {
				String res = count_chon + "_" + rs.getString("Itemid") + "_"
						+ rs.getString("ItemName") + "_"
								+ rs.getString("Unitcoef") + "_"
						+ rs.getString("price") + "_"
						+ rs.getString("Openingqtyc") + "_"
								+ rs.getString("Quantity") + "_"
										+ rs.getString("Amount") + "_"
												+ rs.getString("stockid3");
				
				result.add(res);
		 
				count_chon++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
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
		finish();
       
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
		DatePickerDialog pic = new DatePickerDialog(Reports2_detail.this,
				callback, year, month, day);
		pic.setTitle("Chọn ngày:");
		pic.show();
	}
	public Vector<String> getGroupCust2() {
		Vector<String> result = new Vector<String>();
		result.add("~~~Tất cả~~~");
		ResultSet rs = db.QuerySELECT("Select * from custgroups");
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
        String query="Select CustID, ObjectName from objects inner join customers on CustID = ObjectID";
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
        	   query = query + " and customers.CustID in(select CustID_emp from dbo.CustID_emps where  EmployeeID=N'" + EmployeeID2 + "')";
           }
   		
		ResultSet rs = db.QuerySELECT(query);
		try {
			while (rs.next()) {
				result.add(rs.getString("CustID") + "-"
						+ rs.getString("ObjectName"));
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
		String nguoinhan = selecteditem[1];
		tvnguoinhan.setText(nguoinhan);
		String diachi = getDetail("Select Address From Customers where CustID = N'" + id + "'","Address");
		tvdiachi.setText(diachi);
	 	
		Intent callerIntent=getIntent();
        Bundle packageFromCaller= callerIntent.getBundleExtra("MyPackage");

        int chinhanh2=packageFromCaller.getInt("chinhanh");
        String DatabaseName2=packageFromCaller.getString("DatabaseName");
        String UserName2=packageFromCaller.getString("UserName");
        ArrayList result_chon = new ArrayList();
        result_chon=packageFromCaller.getStringArrayList("result_chon");
        Intent myIntent=new Intent(this, Reports2_detail.class);
        sdoituong=id;
		Bundle bundle = new Bundle();
		bundle.putString("CustID", id); 
		bundle.putStringArrayList("result_chon", result_chon); 
		  bundle.putInt("chinhanh", chinhanh2);
	       bundle.putString("DatabaseName", DatabaseName2);
	        bundle.putString("UserName",UserName2);
	       
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
        AlertDialog.Builder builder = new AlertDialog.Builder(Reports2_detail.this);

        builder.setMessage("Cảnh báo: " + phone);
 
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

		String[] columnname = { "N", "Mã", "Tên","HSQĐ","Giá","Đặt.T","Đ.Lẻ","T.T","Kho"};//"HSQĐ",

		TableRow.LayoutParams lp = new TableRow.LayoutParams(width / 20,
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp1 = new TableRow.LayoutParams((width / 10),
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp2 = new TableRow.LayoutParams(3 * (width / 10),
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp3 = new TableRow.LayoutParams( (width /10),
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp4 = new TableRow.LayoutParams((width / 8),
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp5 = new TableRow.LayoutParams(width /12,
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp6 = new TableRow.LayoutParams(width /12,
				LayoutParams.WRAP_CONTENT);
		 
		TableRow.LayoutParams lp7 = new TableRow.LayoutParams(width /10,
				LayoutParams.WRAP_CONTENT);
		
		TableRow.LayoutParams lp8 = new TableRow.LayoutParams(width /10,
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
		tv5.setWidth( (width / 8));
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
		tv6.setWidth(width /10);
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
		 tv8.setWidth(width /10);
		tv8.setTextColor(Color.BLACK);
		tv8.setGravity(Gravity.LEFT);
		tv8.setBackgroundDrawable(gd);
		 tv8.setLayoutParams(lp6);
		 tv8.setHeight(100);
		tr.addView(tv8);
  
		 
		TextView tv7 = new TextView(this);
		tv7.setPadding(20, 20, 20, 20);
		tv7.setBackgroundColor(Color.WHITE);
		tv7.setText(columnname[7]);
		tv7.setWidth(width /10);
		tv7.setTextColor(Color.BLACK);
		tv7.setGravity(Gravity.CENTER);
		tv7.setBackgroundDrawable(gd);
		tv7.setLayoutParams(lp7);
		tv7.setHeight(100);
		tr.addView(tv7);
		
		TextView tv9 = new TextView(this);
		tv9.setPadding(20, 20, 20, 20);
		tv9.setBackgroundColor(Color.WHITE);
		tv9.setText(columnname[8]);
		tv9.setWidth(width /10);
		tv9.setTextColor(Color.BLACK);
		tv9.setGravity(Gravity.CENTER);
		tv9.setBackgroundDrawable(gd);
		tv9.setLayoutParams(lp7);
		tv9.setHeight(100);
		tr.addView(tv9);
		
		tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
	}
	public void makeTable() {
		// make column index
		tl.removeAllViews();
		FixTableForm();
		vhanghoa=result_chon_ban;
		double newSum=0;
		
		for (int i = 0; i < vhanghoa.size(); i++) {
			TableRow tr2 = new TableRow(this);
			tr2.setBackgroundColor(Color.WHITE);
			final String[] info = vhanghoa.get(i).split("_");
			try{
				  final double value = Double.parseDouble(info[7]);
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
 //
			TextView tv11 = new TextView(this);
			tv11.setText(info[3]);
			tv11.setWidth(width /10);
			tv11.setTextColor(Color.BLACK);
			tv11.setBackgroundColor(Color.WHITE);
			tv11.setGravity(Gravity.RIGHT);
			tv11.setBackgroundDrawable(gd);
			TableRow.LayoutParams lp1tv11 = new TableRow.LayoutParams((width /10),
					LayoutParams.MATCH_PARENT);
			tv11.setLayoutParams(lp1tv11);
			tv11.setPadding(20, 20, 20, 20);
			tr2.addView(tv11);
			// nhom kh
			TextView tv4 = new TextView(this);
			//tv4.setText(info[4]);
			tv4.setTextColor(Color.BLACK);
			tv4.setBackgroundColor(Color.WHITE);
			tv4.setGravity(Gravity.RIGHT);
			tv4.setBackgroundDrawable(gd);
			TableRow.LayoutParams lp4 = new TableRow.LayoutParams((width / 8),
					LayoutParams.MATCH_PARENT);
			tv4.setLayoutParams(lp4);
			tv4.setPadding(20, 20, 20, 20);
			
			try{
				DecimalFormat format21 = new DecimalFormat("#,##0;-#,##0");
				Double value22 = Double.valueOf(info[4]) ;
				String formatted22 = format21.format(value22); 
				tv4.setText(formatted22);
				}catch(Exception e){tv4.setText(info[4]);}
			
			tr2.addView(tv4);
 
 
			TextView tv8 = new TextView(this);
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
							Double value21 = Double.valueOf(info[5]) ;
							String formatted21 = format21.format(value21); 
							tv8.setText(formatted21);
							 
						 
						 tv8.setLayoutParams(lp6);
						tr2.addView(tv8);
			 		
						TextView tv9 = new TextView(this);
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
							Double value211 = Double.valueOf(info[6]) ;
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
						TableRow.LayoutParams lp10 = new TableRow.LayoutParams((width /8),
								LayoutParams.MATCH_PARENT);
						tv10.setLayoutParams(lp10);
						
						 DecimalFormat format2111 = new DecimalFormat("#,##0;-#,##0");
							Double value2111 = Double.valueOf(info[7]) ;
							String formatted2112 = format2111.format(value2111); 
							tv10.setText(formatted2112);
						tv10.setPadding(20, 20, 20, 20);
						tr2.addView(tv10);
						
						 
						
						
			tl.addView(tr2, new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		}
	}

  
	private void clearText()
	{
		 
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
