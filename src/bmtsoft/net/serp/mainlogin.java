package bmtsoft.net.serp;
import bmtsoft.net.serp.DBManage;

import android.app.Activity;
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
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Vector;

import android.widget.EditText;
import 	android.util.*;
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
 
import model.Customer_Model;
public class mainlogin  extends Activity {
	Spinner cbochinhanh;
	DBManage db = new DBManage();
	TextView txtdate1, txtdate2;
	Button btndate1, btndate2, btnOk, btnReport, btnphieuxuat,btncreatedoc,btnexit3;
	Calendar cal;
	Date date1, date2;
	Spinner cusGroup;
	Vector groupIDs = new Vector();
	ArrayList<Customer_Model> clsm;
	 Customer_Model clsm2=new Customer_Model();
	 Vector<String> vnhomkh = new Vector<String>();
	 ArrayList<String> vdoituong = new ArrayList<String>();
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.viewmain);
        Intent callerIntent=getIntent();
        Bundle packageFromCaller= callerIntent.getBundleExtra("MyPackage");

       
		
       int chinhanh=1;//packageFromCaller.getInt("chinhanh");
       String DatabaseName=packageFromCaller.getString("DatabaseName");
       String UserName=packageFromCaller.getString("UserName");
       
       db.databasename=DatabaseName;
	   	txtdate1 = (TextView) findViewById(R.id.txtdate1);
		txtdate2 = (TextView) findViewById(R.id.txtrefdate);
		btndate1 = (Button) findViewById(R.id.btndate1);
		btndate2 = (Button) findViewById(R.id.btnchondate);
		btnOk = (Button)findViewById(R.id.btnrepost);
		btnexit3 = (Button)findViewById(R.id.btnexit3);
		 
		btncreatedoc = (Button)findViewById(R.id.btncreatedoc);
	//	 
	   cusGroup = (Spinner) findViewById(R.id.cusGroup);
	 
		vnhomkh = getGroupCust2();
		ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, vnhomkh);
		aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		cusGroup.setAdapter(aa);
		
	//
	/*	if(chinhanh==0)
		{
			GeneralInfo cls3=new GeneralInfo();
    	 	List<String> list=new ArrayList<String>();
    	 	List<String> list5=new ArrayList<String>();
            db.databasename=DatabaseName;
            list=db.Getbrand(cls3);
            cbochinhanh = (Spinner) findViewById(R.id.cbochinhanh);
            for(int i=0;i<list.size();i++)
            {
                String Name="";
                Name=list.get(i);
                list5.add(Name);
            }
	       ArrayAdapter adapter=new ArrayAdapter<>(mainlogin.this, android.R.layout.simple_spinner_item, list5);
	       adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	       cbochinhanh.setAdapter(adapter);
		}
		else
		{
			  cbochinhanh = (Spinner) findViewById(R.id.cbochinhanh);
			  cbochinhanh.setEnabled(false);
			  
		}
		**/
		setDefaultDate();
		btndate1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDatePickerDialog1();

			}
		});

		btndate2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				showDatePickerDialog2();
			}
		});
		btncreatedoc.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 Intent callerIntent=getIntent();
			        Bundle packageFromCaller= callerIntent.getBundleExtra("MyPackage");

				int chinhanh=1;//packageFromCaller.getInt("chinhanh");
			       String DatabaseName=packageFromCaller.getString("DatabaseName");
			       String UserName=packageFromCaller.getString("UserName");
				doOpenChildActivity(chinhanh,DatabaseName,UserName);
			}
		});
		btnexit3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 finish();
		           // System.exit(0);
		            android.os.Process.killProcess(android.os.Process.myPid());
		            
			}
		});
		btnOk.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 Intent callerIntent=getIntent();
			        Bundle packageFromCaller= callerIntent.getBundleExtra("MyPackage");

				int chinhanh=1;//packageFromCaller.getInt("chinhanh");
			       String DatabaseName=packageFromCaller.getString("DatabaseName");
			       String UserName=packageFromCaller.getString("UserName");
			       doOpenChildActivity_Repors(chinhanh,DatabaseName,UserName);
			}
		});
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
	public Vector getCusGroup() {
		Vector result = new Vector();
		result.add("---Tất cả---");
		String select = "select GroupID from dbo.CustGroups";
		ResultSet rs = db.QuerySELECT(select);
		try {
			while (rs.next()) {
				result.add(rs.getString("GroupID"));
				clsm = new ArrayList<Customer_Model>();
				//clsm2.setGroupID(rs.getString("GroupID"));
				//clsm2.setGroupName(rs.getString("GroupName"));
				//clsm.add(clsm2);
			}
			
			//return result;
		} catch (SQLException e) {
			Log.e("ERRO", e.getMessage());
			e.printStackTrace();
			return null;
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
		// show text
		txtdate1.setText(strDate);
		txtdate2.setText(transformDate2(strDate));
	}
	public String transformDate(String sDate) {
		String strArrtmp[] = sDate.split("/");
		int day = Integer.parseInt(strArrtmp[0]);
		int month = Integer.parseInt(strArrtmp[1]);
		int year = Integer.parseInt(strArrtmp[2]);
		String result = month + "/" + day + "/" + year;
		return result;
	}
	public void showDatePickerDialog2() {
		OnDateSetListener callback = new OnDateSetListener() {
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// Update TextView2
				// Date
				txtdate2.setText((dayOfMonth) + "/" + (monthOfYear + 1) + "/"
						+ year);
				// update date2
				cal.set(year, monthOfYear, dayOfMonth);
				date2 = cal.getTime();
			}
		};

		String s = txtdate2.getText() + "";
		String strArrtmp[] = s.split("/");
		int day = Integer.parseInt(strArrtmp[0]);
		int month = Integer.parseInt(strArrtmp[1]) - 1;
		int year = Integer.parseInt(strArrtmp[2]);
		DatePickerDialog pic = new DatePickerDialog(mainlogin.this,
				callback, year, month, day);
		pic.setTitle("Chọn ngày kết thúc:");
		pic.show();
	}

	public void showDatePickerDialog1() {
		OnDateSetListener callback = new OnDateSetListener() {
			public void onDateSet(DatePicker view, int year, int monthOfYear,
					int dayOfMonth) {
				// Update TextView1
				txtdate1.setText((dayOfMonth) + "/" + (monthOfYear + 1) + "/"
						+ year);
				// update date1
				cal.set(year, monthOfYear, dayOfMonth);
				date1 = cal.getTime();
			}
		};

		String s = txtdate1.getText() + "";
		String strArrtmp[] = s.split("/");
		int day = Integer.parseInt(strArrtmp[0]);
		int month = Integer.parseInt(strArrtmp[1]) - 1;
		int year = Integer.parseInt(strArrtmp[2]);
		DatePickerDialog pic = new DatePickerDialog(mainlogin.this,
				callback, year, month, day);
		pic.setTitle("Chọn ngày bắt đầu:");
		pic.show();
	}
	public void doOpenChildActivity(int chinhanh,String DatabaseName,String UserName)
    {
        Bundle bundle=new Bundle();
        Intent myIntent=new Intent(this, sorderclass.class);
       bundle.putInt("chinhanh", chinhanh);
       bundle.putString("DatabaseName", DatabaseName);
       bundle.putString("UserName", UserName);
       String[] info = cusGroup.getSelectedItem().toString().split("-");
    	 
 		if (info[0].equals("~~~Tất cả~~~")) {
 			info[0] = "";
 		}
        bundle.putString("CustGroup", info[0]);
       myIntent.putExtra("MyPackage", bundle);
       
        startActivity(myIntent);
    }
	public void doOpenChildActivity_Repors(int chinhanh,String DatabaseName,String UserName)
    {
        Bundle bundle=new Bundle();
        Intent myIntent=new Intent(this, Report_em.class);
       bundle.putInt("chinhanh", chinhanh);
       bundle.putString("DatabaseName", DatabaseName);
       bundle.putString("UserName", UserName);
      String  RefDate=txtdate1.getText().toString();
       bundle.putString("Begindate",transformDate(RefDate));
       bundle.putString("Enddate", transformDate(txtdate2.getText().toString()));
       String[] info = cusGroup.getSelectedItem().toString().split("-");
	 
		if (info[0].equals("~~~Tất cả~~~")) {
			info[0] = "";
		}
       bundle.putString("CustGroup", info[0]);
       myIntent.putExtra("MyPackage", bundle);
        startActivity(myIntent);
    }
	
	 
}
