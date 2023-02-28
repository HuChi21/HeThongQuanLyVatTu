package bmtsoft.net.serp;
import bmtsoft.net.serp.DBManage;
import model.Customer_Model;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.util.Log;
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
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.EditText;
import android.widget.GridView;
import 	android.util.*;
import android.content.Intent;
import android.widget.EditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.graphics.Color;
import android.app.Activity;
import android.app.ActionBar.LayoutParams;

import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;


public class popupcustomer extends Activity {
	DBManage db = new DBManage();
	TextView txtrefdate2;
	Button btnsearch,btnExit2;
	Spinner cusGroup;
	 Vector groupIDs = new Vector();
	 ArrayList<Customer_Model> clsm;
	 Customer_Model clsm2=new Customer_Model();
	 GridView grcust;
	 TableLayout tl;
	 ArrayList<String> vdoituong = new ArrayList<String>();
	 int width, height, nor;
	 static final String[] MOBILE_OS = new String[] { 
				"Android", "iOS","Windows", "Blackberry" };
	  Vector<String> vnhomkh = new Vector<String>();
 		String activity;
		int min = 0, max;
		String currkeyword;
		 Vector custlist = new Vector();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.popupcust);
       Intent callerIntent=getIntent();
      Bundle packageFromCaller= callerIntent.getBundleExtra("MyPackage");

       int chinhanh=packageFromCaller.getInt("chinhanh");
      String DatabaseName=packageFromCaller.getString("DatabaseName");
      String UserName=packageFromCaller.getString("UserName");
      db.databasename=DatabaseName;
     // get screen size
        
        String query22 = "Select top 50 ObjectID as CustID, ObjectName from Objects o left join Customers c on c.CustID=o.ObjectID left join Employee e on e.EmployeeID=o.ObjectID  where (ObjectTypeID & 1=1 or ObjectTypeID & 3=3 or ObjectTypeID & 4=4) and (c.Inactive=0 or e.Inactive=0)";
        int count = 0;	 
       
        
      // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
		// 		android.R.layout.simple_list_item_1, );

     //  grcust.setAdapter(adapter);
     		
      //  Display display = getWindowManager().getDefaultDisplay();
     	//	Point size = new Point();
     		//  display.getSize(size);
     		width = 40;//size.x;
     		height =20;// size.y; 
        btnsearch = (Button) findViewById(R.id.btnsearch);
        grcust = (GridView) findViewById(R.id.Grcust); 
       

        
         cusGroup = (Spinner) findViewById(R.id.cbonhom);
		 groupIDs = getCusGroup();
		 ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
		 		android.R.layout.simple_spinner_item, groupIDs);

	 	aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		 cusGroup.setAdapter(aa);
		 
		 custlist = getListCust();
		 
		 makeTable();
			activity = "group";
			 
        btnExit2 = (Button) findViewById(R.id.btnExit3);
       // vnhomkh = getGroupCust2();
        btnExit2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 finish();
		            System.exit(0);

			}
		});
         
       /* grcust.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String[] info = vnhomkh.get(position).split("-");
				currkeyword = info[0];
				if (info[0].equals("~~~Tất cả~~~")) {
					info[0] = "";
				}
				String query = "Select TOP 5 CustID, ObjectName, Address, GroupID from objects inner join customers on CustID = ObjectID where GroupID like '"
						+ info[0] + "%'";

				String query2 = "Select count(CustID) as NUM from objects inner join customers on CustID = ObjectID where GroupID like '"
						+ info[0] + "%'";
				vdoituong = getResult(query);
				nor = getNumOfRow(query2);
				min = 0;
				max = 5;
				makeTable();
				activity = "group"; 
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});
	*/	 
	}
	public Vector<String> getListCust() {
		Vector<String> result = new Vector<String>();
		result.add("~~~Tất cả~~~");
		int count = 0;
		String query = "Select ObjectID as CustID, ObjectName,isnull(c.Address,e.Address) as Address,c.GroupID from Objects o left join Customers c on c.CustID=o.ObjectID left join Employee e on e.EmployeeID=o.ObjectID  where (ObjectTypeID & 1=1 or ObjectTypeID & 3=3 or ObjectTypeID & 4=4) and (c.Inactive=0 or e.Inactive=0)";
				
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
		String select = "select (GroupID +'-'+ GroupName) as GroupID  from dbo.CustGroups";
		ResultSet rs = db.QuerySELECT(select);
		try {
			while (rs.next()) {
				result.add(rs.getString("GroupID"));
				clsm = new ArrayList<Customer_Model>();
				// clsm2.setGroupID(rs.getString("GroupID3"));
				 //clsm2.setGroupName(rs.getString("GroupName"));
				//clsm.add(clsm2);
			}
			return result;
		} catch (SQLException e) {
			Log.e("ERRO", e.getMessage());
			e.printStackTrace();
			return null;
		}
	}
	public void makeTable() {

		// make column index
		tl.removeAllViews();
		FixTableForm();

		for (int i = 0; i < vdoituong.size(); i++) {
			TableRow tr2 = new TableRow(this);
			tr2.setBackgroundColor(Color.WHITE);
			final String[] info = vdoituong.get(i).split("_");

			// stt
			TextView tv = new TextView(this);
			tv.setText(info[0]);
			tv.setTextColor(Color.BLACK);
			tv.setBackgroundColor(Color.WHITE);
			tv.setGravity(Gravity.LEFT);
			GradientDrawable gd = new GradientDrawable();
			gd.setStroke(1, Color.BLACK);
			tv.setBackgroundDrawable(gd);
			TableRow.LayoutParams lp = new TableRow.LayoutParams(width / 10,
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

			// dia chi
			TextView tv3 = new TextView(this);
			tv3.setText(info[3]);
			tv3.setTextColor(Color.BLACK);
			tv3.setBackgroundColor(Color.WHITE);
			tv3.setGravity(Gravity.LEFT);
			tv3.setBackgroundDrawable(gd);
			TableRow.LayoutParams lp3 = new TableRow.LayoutParams(
					3 * (width / 10), LayoutParams.MATCH_PARENT);
			tv3.setLayoutParams(lp3);
			tv3.setPadding(20, 20, 20, 20);
			tr2.addView(tv3);

			// nhom kh
			TextView tv4 = new TextView(this);
			tv4.setText(info[4]);
			tv4.setTextColor(Color.BLACK);
			tv4.setBackgroundColor(Color.WHITE);
			tv4.setGravity(Gravity.LEFT);
			tv4.setBackgroundDrawable(gd);
			TableRow.LayoutParams lp4 = new TableRow.LayoutParams((width / 10),
					LayoutParams.MATCH_PARENT);
			tv4.setLayoutParams(lp4);
			tv4.setPadding(20, 20, 20, 20);
			tr2.addView(tv4);

			// button
			Button btn = new Button(this);
			btn.setText("Chọn");
			btn.setBackgroundDrawable(gd);
			TableRow.LayoutParams lp5 = new TableRow.LayoutParams(width / 10,
					LayoutParams.MATCH_PARENT);
			btn.setLayoutParams(lp5);
			btn.setPadding(20, 20, 20, 20);
			tr2.addView(btn);
			 
			 
			tl.addView(tr2, new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		}
	}
	public void FixTableForm() {
		TableRow tr = new TableRow(this);
		tr.setBackgroundColor(Color.WHITE);

		String[] columnname = { "STT", "Mã KH", "Tên KH", "Địa chỉ", "Nhóm KH",
				"Chọn" };

		TableRow.LayoutParams lp = new TableRow.LayoutParams(width / 10,
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp1 = new TableRow.LayoutParams((width / 10),
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp2 = new TableRow.LayoutParams(3 * (width / 10),
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp3 = new TableRow.LayoutParams(3 * (width / 10),
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp4 = new TableRow.LayoutParams((width / 10),
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp5 = new TableRow.LayoutParams(width / 10,
				LayoutParams.WRAP_CONTENT);

		TextView tv = new TextView(this);
		tv.setPadding(20, 20, 20, 20);
		tv.setBackgroundColor(Color.WHITE);
		tv.setText(columnname[0]);
		tv.setWidth(width / 10);
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
		tv2.setWidth(2 * (width / 10));
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
		tv3.setLayoutParams(lp2);
		tr.addView(tv3);

		TextView tv4 = new TextView(this);
		tv4.setPadding(20, 20, 20, 20);
		tv4.setBackgroundColor(Color.WHITE);
		tv4.setText(columnname[3]);
		tv4.setWidth(3 * (width / 10));
		tv4.setTextColor(Color.BLACK);
		tv4.setGravity(Gravity.CENTER);
		tv4.setBackgroundDrawable(gd);
		tv4.setLayoutParams(lp3);
		tr.addView(tv4);

		TextView tv5 = new TextView(this);
		tv5.setPadding(20, 20, 20, 20);
		tv5.setBackgroundColor(Color.WHITE);
		tv5.setText(columnname[4]);
		tv5.setWidth(2 * (width / 10));
		tv5.setTextColor(Color.BLACK);
		tv5.setGravity(Gravity.CENTER);
		tv5.setBackgroundDrawable(gd);
		tv5.setLayoutParams(lp4);
		tr.addView(tv5);

		TextView tv6 = new TextView(this);
		tv6.setPadding(20, 20, 20, 20);
		tv6.setBackgroundColor(Color.WHITE);
		tv6.setText(columnname[5]);
		tv6.setWidth(width / 10);
		tv6.setTextColor(Color.BLACK);
		tv6.setGravity(Gravity.CENTER);
		tv6.setBackgroundDrawable(gd);
		tv6.setLayoutParams(lp5);
		tr.addView(tv6);

		tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
	}
	public int getNumOfRow(String query) {
		int result = 0;
		ResultSet rs = db.QuerySELECT(query);
		try {
			while (rs.next()) {
				result = rs.getInt("NUM");
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
	
}
