package bmtsoft.net.serp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import bmtsoft.net.serp.R;
import model.LogChinhanh;
import  bmtsoft.net.serp.DBManage;

import android.app.Activity;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

public class Tim_kiem_doi_tuong extends Activity {

	EditText ettimkiem;
	Button btntimkiem, btnnhaplai, btnback, btnnext,btnthoat;
	Spinner spnhomkh;
	TableLayout tl;
	ArrayList<String> vdoituong = new ArrayList<String>();
	Vector<String> vnhomkh = new Vector<String>();
	DBManage db = new DBManage();
	GridView gv;
	int width, height, nor;
	String activity;
	int min = 0, max;
	String currkeyword;
	int chinhanh=0;
	String DatabaseName="";
	String UserName="";
	LogChinhanh clsm4=new LogChinhanh();
	String snhom="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tim_kiem_doi_tuong);
		
		 //Intent callerIntent=getIntent();
	     //   Bundle packageFromCaller= callerIntent.getBundleExtra("MyPackage");

	        // chinhanh=packageFromCaller.getInt("chinhanh");
	         //DatabaseName=packageFromCaller.getString("DatabaseName");
//	         UserName=packageFromCaller.getString("UserName");
	       
		ettimkiem = (EditText) findViewById(R.id.etTimkiemdoituong);
		btnnhaplai = (Button) findViewById(R.id.btnclear);
		btntimkiem = (Button) findViewById(R.id.btnExit);
		tl = (TableLayout) findViewById(R.id.tldoituong);
		spnhomkh = (Spinner) findViewById(R.id.spnhomkh);
		btnback = (Button) findViewById(R.id.btnNext);
		btnnext = (Button) findViewById(R.id.btnnext);
		btnthoat=(Button)findViewById(R.id.btnThoat);
		// gv = (GridView) findViewById(R.id.gvdoituong);

		// get screen size
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		
 
		width = size.x;
		height = size.y;

		btntimkiem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String keywork = ettimkiem.getText().toString();
				String query = "Select top 50  ObjectID as CustID, ObjectName,isnull(c.Address,e.DiaChi) as Address,GroupID from Objects o left join Customers c on c.CustID=o.ObjectID left join Employee e on e.EmployeeID=o.ObjectID  where (ObjectTypeID & 1=1 or ObjectTypeID & 3=3 or ObjectTypeID & 4=4) and (c.Inactive=0 or e.Inactive=0) and CustID like N'%"
						+ keywork
						+ "%' OR ObjectName like N'%"
						+ keywork
						+ "%' OR Address like N'%"
						+ keywork
						+ "%' OR ObjectID like N'%"
						+ keywork
						+ "%' OR GroupID like N'%"
						+ keywork + "%'";
				Intent callerIntent=getIntent();
		        Bundle packageFromCaller= callerIntent.getBundleExtra("MyPackage");

		        String UserName2=packageFromCaller.getString("UserName"); 
			      String EmployeeID2=getDetail("select empsale from users where loginname=N'"+ UserName2 +"'","empsale");
				String str2="select CustID_emp as count from CustID_emps where  EmployeeID=N'" + EmployeeID2 + "'";
		          
		           ResultSet rs = db.QuerySELECT(str2);
		           String resn ="";
		   		try {
		   			while (rs.next()) {
		   				 resn = rs.getString("count")  ;
		   			}
		   		} catch (SQLException e) {
		   			 
		   		}
		   		if (resn.trim().toString().isEmpty() == true)
		           {}else{
		        	   query = query + " and customers.CustID in(select CustID_emp from dbo.CustID_emps where  EmployeeID=N'" + EmployeeID2 + "')";
		           }
				vdoituong = getResult(query);
				String query2 = "Select count(ObjectID) as NUM    from Objects o left join Customers c on c.CustID=o.ObjectID left join Employee e on e.EmployeeID=o.ObjectID  where (ObjectTypeID & 1=1 or ObjectTypeID & 3=3 or ObjectTypeID & 4=4) and (c.Inactive=0 or e.Inactive=0) and CustID like N'%"
						+ keywork
						+ "%' OR ObjectName like N'%"
						+ keywork
						+ "%' OR Address like N'%"
						+ keywork
						+ "%' OR ObjectID like N'%"
						+ keywork
						+ "%' OR GroupID like N'%"
						+ keywork + "%'";
				 
		   		if (resn.trim().toString().isEmpty() == true)
		           {}else{
		        	   query2 = query2 + " and customers.CustID in(select CustID_emp from dbo.CustID_emps where  EmployeeID=N'" + EmployeeID2 + "')";
		           }
				nor = getNumOfRow(query2);
				min = 0;
				max = 5;
				activity = "search";
				makeTable();
				checkNor();
				try{
					db.connect.close();
					} 
				catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});

		btnnhaplai.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ettimkiem.setText("");
			}
		});

		vnhomkh = getGroupCust();
		ArrayAdapter<String> aa = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, vnhomkh);
		aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spnhomkh.setAdapter(aa);

		spnhomkh.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				String[] info = vnhomkh.get(position).split("-");
				currkeyword = info[0];
				if (info[0].equals("~~~Tất cả~~~")) {
					info[0] = "";
				}
				String query = "Select TOP 50 CustID, ObjectName, Address, GroupID from objects inner join customers on CustID = ObjectID where customers.Inactive =0 and  GroupID like N'"
						+ info[0] + "%'";

				String query2 = "Select count(CustID) as NUM from objects inner join customers on CustID = ObjectID where customers.Inactive =0 and  GroupID like N'"
						+ info[0] + "%'";
				
				Intent callerIntent=getIntent();
		        Bundle packageFromCaller= callerIntent.getBundleExtra("MyPackage");

		        String UserName2=packageFromCaller.getString("UserName"); 
			      String EmployeeID2=getDetail("select empsale from users where loginname=N'"+ UserName2 +"'","empsale");
				String str2="select CustID_emp as count from CustID_emps where  EmployeeID='" + EmployeeID2 + "'";
		          
		           ResultSet rs = db.QuerySELECT(str2);
		           String resn ="";
		   		try {
		   			while (rs.next()) {
		   				 resn = rs.getString("count")  ;
		   			}
		   		} catch (SQLException e) {
		   			 
		   		}
		   		if (resn.trim().toString().isEmpty() == true)
		           {}else{
		        	   query = query + " and customers.CustID in(select CustID_emp from dbo.CustID_emps where  EmployeeID=N'" + EmployeeID2 + "')";
		           }
		           
				vdoituong = getResult(query);
				nor = getNumOfRow(query2);
				min = 0;
				max = 500;
				makeTable();
				activity = "group";
				checkNor();
				try{
					db.connect.close();
					} 
				catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
				// TODO Auto-generated method stub

			}
		});


		btnback.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (activity.equals("search")) {
					max = min;
					min = min - 50;
					if (min < 0) {
						min = 0;
					}
					String keywork = ettimkiem.getText().toString();
					String query = "Select objectid as CustID, ObjectName, ,isnull(c.Address,e.DiaChi) as Address, GroupID from ("
							+ "SELECT ROW_NUMBER() OVER(ORDER BY c.GROUPID) AS NUM,"
							+ "* FROM  Objects o left join Customers c on c.CustID=o.ObjectID left join Employee e on e.EmployeeID=o.ObjectID  where (ObjectTypeID & 1=1 or ObjectTypeID & 3=3 or ObjectTypeID & 4=4) and (c.Inactive=0 or e.Inactive=0)  "
							+ "and   CustID like '"
							+ keywork
							+ "%' OR ObjectName like N'"
							+ keywork
							+ "%' OR Address like N'"
							+ keywork
							+ "%' OR ObjectID like N'%"
							+ keywork
							+ "%' OR GroupID like N'"
							+ keywork
							+ "%' AND NUM > "
							+ min + " AND NUM <= " + max;
					
					Intent callerIntent=getIntent();
			        Bundle packageFromCaller= callerIntent.getBundleExtra("MyPackage");

			        String UserName2=packageFromCaller.getString("UserName"); 
				      String EmployeeID2=getDetail("select empsale from users where loginname=N'"+ UserName2 +"'","empsale");
					String str2="select CustID_emp as count from CustID_emps where  EmployeeID='" + EmployeeID2 + "'";
			          
			           ResultSet rs = db.QuerySELECT(str2);
			           String resn ="";
			   		try {
			   			while (rs.next()) {
			   				 resn = rs.getString("count")  ;
			   			}
			   		} catch (SQLException e) {
			   			 
			   		}
			   		if (resn.trim().toString().isEmpty() == true)
			           {}else{
			        	   query = query + " and c.CustID in(select CustID_emp from dbo.CustID_emps where  EmployeeID=N'" + EmployeeID2 + "')";
			           }
					vdoituong = getResult(query);
					makeTable();
				} else if (activity.equals("group")) {
					max = min;
					min = min - 50;
					if (min < 0) {
						min = 0;
					}

					if (currkeyword.equals("~~~Tất cả~~~")) {
						currkeyword = "";
					}
					String query = "Select ObjectID as CustID, ObjectName, ,isnull(c.Address,e.DiaChi) as Address, GroupID from ("
							+ " SELECT ROW_NUMBER() OVER(ORDER BY c.GROUPID) AS NUM,"
							+ "* FROM    Objects o left join Customers c on c.CustID=o.ObjectID left join Employee e on e.EmployeeID=o.ObjectID  where (ObjectTypeID & 1=1 or ObjectTypeID & 3=3 or ObjectTypeID & 4=4) and (c.Inactive=0 or e.Inactive=0) "
							+ ") A where  and GroupID like N'"
							+ currkeyword
							+ "%' AND NUM > " + min + " AND NUM <=" + max;
					Intent callerIntent=getIntent();
			        Bundle packageFromCaller= callerIntent.getBundleExtra("MyPackage");

			        String UserName2=packageFromCaller.getString("UserName"); 
				      String EmployeeID2=getDetail("select empsale from users where loginname=N'"+ UserName2 +"'","empsale");
					String str2="select CustID_emp as count from CustID_emps where  EmployeeID='" + EmployeeID2 + "'";
			          
			           ResultSet rs = db.QuerySELECT(str2);
			           String resn ="";
			   		try {
			   			while (rs.next()) {
			   				 resn = rs.getString("count")  ;
			   			}
			   		} catch (SQLException e) {
			   			 
			   		}
			   		if (resn.trim().toString().isEmpty() == true)
			           {}else{
			        	   query = query + " and customers.CustID in(select CustID_emp from dbo.CustID_emps where  EmployeeID=N'" + EmployeeID2 + "')";
			           }
					vdoituong = getResult(query);
					makeTable();
				}
				checkNor();
				try{
					db.connect.close();
					} 
				catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
		btnnext.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (activity.equals("search")) {
					min = max;
					max = max + 50;
					String keywork = ettimkiem.getText().toString();
					String query = "Select ObjectID as CustID, ObjectName, ,isnull(c.Address,e.DiaChi) as Address, GroupID from ("
							+ "SELECT ROW_NUMBER() OVER(ORDER BY c.GROUPID) AS NUM,"
							+ "* FROM    Objects o left join Customers c on c.CustID=o.ObjectID left join Employee e on e.EmployeeID=o.ObjectID  where (ObjectTypeID & 1=1 or ObjectTypeID & 3=3 or ObjectTypeID & 4=4) and (c.Inactive=0 or e.Inactive=0) ) A "
							+ "and    ObjectID like '"
							+ keywork
							+ "%' OR ObjectName like '"
							+ keywork
							+ "%' OR Address like '"
							+ keywork
							+ "%' OR GroupID like '"
							+ keywork
							+ "%' AND NUM > "
							+ min + " AND NUM <= " + max;
					Intent callerIntent=getIntent();
			        Bundle packageFromCaller= callerIntent.getBundleExtra("MyPackage");

			        String UserName2=packageFromCaller.getString("UserName"); 
				      String EmployeeID2=getDetail("select empsale from users where loginname=N'"+ UserName2 +"'","empsale");
					String str2="select CustID_emp as count from CustID_emps where  EmployeeID='" + EmployeeID2 + "'";
			          
			           ResultSet rs = db.QuerySELECT(str2);
			           String resn ="";
			   		try {
			   			while (rs.next()) {
			   				 resn = rs.getString("count")  ;
			   			}
			   		} catch (SQLException e) {
			   			 
			   		}
			   		if (resn.trim().toString().isEmpty() == true)
			           {}else{
			        	   query = query + " and c.CustID in(select CustID_emp from dbo.CustID_emps where  EmployeeID='" + EmployeeID2 + "')";
			           }
					vdoituong = getResult(query);
					makeTable();
				} else if (activity.equals("group")) {
					min = max;
					max = max + 50;

					if (currkeyword.equals("~~~Tất cả~~~")) {
						currkeyword = "";
					}
					String query = "Select ObjectID as CustID, ObjectName, ,isnull(c.Address,e.DiaChi) as Address, GroupID from ("
							+ " SELECT ROW_NUMBER() OVER(ORDER BY c.GROUPID) AS NUM,"
							+ "* FROM    Objects o left join Customers c on c.CustID=o.ObjectID left join Employee e on e.EmployeeID=o.ObjectID  where (ObjectTypeID & 1=1 or ObjectTypeID & 3=3 or ObjectTypeID & 4=4) and (c.Inactive=0 or e.Inactive=0) "
							+ ") A where GroupID like '"
							+ currkeyword
							+ "%' AND NUM > " + min + " AND NUM <=" + max;
					Intent callerIntent=getIntent();
			        Bundle packageFromCaller= callerIntent.getBundleExtra("MyPackage");

			        String UserName2=packageFromCaller.getString("UserName"); 
				      String EmployeeID2=getDetail("select empsale from users where loginname=N'"+ UserName2 +"'","empsale");
					String str2="select CustID_emp as count from CustID_emps where  EmployeeID='" + EmployeeID2 + "'";
			          
			           ResultSet rs = db.QuerySELECT(str2);
			           String resn ="";
			   		try {
			   			while (rs.next()) {
			   				 resn = rs.getString("count")  ;
			   			}
			   		} catch (SQLException e) {
			   			 
			   		}
			   		if (resn.trim().toString().isEmpty() == true)
			           {}else{
			        	   query = query + " and c.CustID in(select CustID_emp from dbo.CustID_emps where  EmployeeID='" + EmployeeID2 + "')";
			           }
					vdoituong = getResult(query);
					makeTable();
				}
				checkNor();
				try{
					db.connect.close();
					} 
				catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			}
		});
		btnthoat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 finish();
		            System.exit(0);

			}
		});
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
			TableRow.LayoutParams lp5 = new TableRow.LayoutParams(width / 6,
					LayoutParams.MATCH_PARENT);
			btn.setLayoutParams(lp5);
			btn.setPadding(20, 20, 20, 20);
			tr2.addView(btn);
			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					
			       
					
					
					Intent callerIntent=getIntent();
			        Bundle packageFromCaller= callerIntent.getBundleExtra("MyPackage");

			        int chinhanh2=packageFromCaller.getInt("chinhanh");
			        String DatabaseName2=packageFromCaller.getString("DatabaseName");
			        String UserName2=packageFromCaller.getString("UserName");
			        ArrayList result_chon = new ArrayList();
			        result_chon=packageFromCaller.getStringArrayList("result_chon");
			        Intent myIntent=new Intent(Tim_kiem_doi_tuong.this, sorderclass.class);
					String id = info[1];
					Bundle bundle = new Bundle();
					bundle.putString("CustID", id); 
					bundle.putStringArrayList("result_chon", result_chon); 
					  bundle.putInt("chinhanh", chinhanh2);
				       bundle.putString("DatabaseName", DatabaseName2);
				        bundle.putString("UserName",UserName2);
				        String snhom="";
						 try{
								if (getIntent().hasExtra("MyPackage")) {
									Intent Caller = getIntent();
									Bundle bundle3 = Caller.getBundleExtra("MyPackage"); 
									snhom=bundle3.getString("CustGroup");
								}
							}catch(Exception e){}
						 bundle.putString("CustGroup", snhom);  
					myIntent.putExtra("MyPackage", bundle);
					startActivity(myIntent);
					 
				}
			});

			tl.addView(tr2, new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		}
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
				// result.add("" + (count + 1));
				// result.add(rs.getString("CustID"));
				// result.add(rs.getString("ObjectName"));
				// result.add(rs.getString("Address"));
				// result.add(rs.getString("GroupID"));
				// result.add("Chọn");
				count++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
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
	
	public void checkNor(){
		if (nor <= 50) {
			btnback.setVisibility(android.view.View.INVISIBLE);
			btnnext.setVisibility(android.view.View.INVISIBLE);
		}
		if (nor > 50) {
			if (min >= 50 && max < nor) {
				btnback.setVisibility(android.view.View.VISIBLE);
				btnnext.setVisibility(android.view.View.VISIBLE);
			} else if (min <= 50 && max < nor) {
				btnback.setVisibility(android.view.View.INVISIBLE);
				btnnext.setVisibility(android.view.View.VISIBLE);
			} else if (min >= 50 && max >= nor) {
				btnback.setVisibility(android.view.View.VISIBLE);
				btnnext.setVisibility(android.view.View.INVISIBLE);
			}
		}
	}

	public Vector<String> getGroupCust() {
		Vector<String> result = new Vector<String>();
	//	result.add("~~~Tất cả~~~");
		
		String snhom="";
		Intent callerIntent=getIntent();
		  try{
				if (getIntent().hasExtra("MyPackage")) {
					Intent Caller = getIntent();
					Bundle bundle3 = Caller.getBundleExtra("MyPackage"); 
					snhom=bundle3.getString("CustGroup");
				}
			}catch(Exception e){}
			 
		  String sql="Select * from custgroups where Inactive=0";
			//if(snhom!="0" && snhom!="" && snhom!=null )
			//{
			//	sql=sql+"  and  groupID=N'"+ snhom  +"'";
			
			//}
			//else
			//{
				result.add("~~~Tất cả~~~");
			//}
			ResultSet rs = db.QuerySELECT(sql); 
		//ResultSet rs = db.QuerySELECT("Select * from custgroups");
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

	public void FixTableForm() {
		TableRow tr = new TableRow(this);
		tr.setBackgroundColor(Color.WHITE);

		String[] columnname = { "N", "Mã", "Tên KH", "Địa chỉ", "Nhóm",
				"Chọn" };

		TableRow.LayoutParams lp = new TableRow.LayoutParams(width / 20,
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
		tv.setWidth(width / 20);
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
}
