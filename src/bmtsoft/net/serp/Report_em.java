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
import android.widget.RadioButton;
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
public class Report_em  extends Activity  {
	 Vector<String> vnhomkh = new Vector<String>();
	 TableLayout tl;
	 int width, height, nor;
	 int min = 0, max;
	 ArrayList<String> vdoituong = new ArrayList<String>();
	 RadioButton dongy,khongdongy;
	 EditText ettimkiem;
	 Button btntimkiem,btnexit;
	 DBManage db = new DBManage();
	 
	 String DatabaseName="";
		String UserName="";
		String Begindate="";
		String Enddate="";
		String CustGroup="";
		ArrayList result_chon_select = new ArrayList();
		
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.repost_em);
		
		 Display display = getWindowManager().getDefaultDisplay();
			Point size = new Point();
			display.getSize(size); 
			width = size.x;
			height = size.y;
			
			tl = (TableLayout) findViewById(R.id.tldoituong);
			 Intent callerIntent=getIntent();
		        Bundle packageFromCaller= callerIntent.getBundleExtra("MyPackage"); 
		        UserName=packageFromCaller.getString("UserName");
		        Begindate=packageFromCaller.getString("Begindate"); 
		        Enddate=packageFromCaller.getString("Enddate"); 
		        CustGroup=packageFromCaller.getString("CustGroup"); 
		        
			ettimkiem = (EditText) findViewById(R.id.etTimkiemdoituong);
			dongy = (RadioButton) findViewById(R.id.radok);
			khongdongy = (RadioButton) findViewById(R.id.radnotok);
			
			khongdongy.setOnClickListener(first_radio_listener);
			dongy.setOnClickListener(first_radio_khongdongy);
			btnexit=(Button)findViewById(R.id.btnExit);
			btnexit.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					 finish();
					 try {
							Intent callerIntent=getIntent();
					        Bundle packageFromCaller= callerIntent.getBundleExtra("MyPackage");

					        int chinhanh2=packageFromCaller.getInt("chinhanh");
					        String DatabaseName2=packageFromCaller.getString("DatabaseName");
					        String UserName2=packageFromCaller.getString("UserName");
					        String sdoituong=packageFromCaller.getString("CustID");
					        
					        Intent myIntent=new Intent(Report_em.this, mainlogin.class);
							 
							Bundle bundle = new Bundle();
						 
							bundle.putInt("chinhanh", chinhanh2);
						    bundle.putString("DatabaseName", DatabaseName2);
						    bundle.putString("UserName",UserName2);
						       
							myIntent.putExtra("MyPackage", bundle);
							startActivity(myIntent);
				        } catch (ClassCastException se) {
				            
				        }

				}
			});
			btntimkiem = (Button) findViewById(R.id.btnSearch);
			btntimkiem.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					String keywork = ettimkiem.getText().toString();
					
					int selectedId =0;
					if(khongdongy.isChecked()==true)
					{
						selectedId=1;
						db.RefSearch2_web_chuaduyet(Begindate, Enddate, "", UserName, 1, CustGroup);
					}else if(dongy.isChecked()==true)
					{
						db.RefSearch2_web_daduyet(Begindate, Enddate, "", UserName, 1, CustGroup);
						selectedId=2;
					}else
					{
						db.RefSearch2_web_All(Begindate, Enddate, "", UserName, 1, CustGroup);
						selectedId=0;
						
					}
						
						
 
					 String str = "Select distinct d.RefID, CONVERT(VARCHAR(20), CONVERT(datetime, r.refdate,   103), 103) as refdate,r.refno,d.CustID,r.CustName,r.amount,(case when isnull(r2.refno,'')='' then N'Chưa copy' else N'Hoàn thành'end) as sta,d.UserID as UserCreate,d.UserM,d.IsValid from RefSearch r left join refs d on r.refno=d.refno left join items i on i.itemid=r.custid left join refs r2 on r2.refrefid=d.refid   where r.UserID=N'" + UserName + "' ";
			            	   str = str + " and (" 				 
								+ " r.Refno like '%" 
								+ keywork + "%' or d.CustID like '%"+ keywork + "%'  or r.CustName like '%"+ keywork + "%') and r.refdate  is not null"; //
					vdoituong = getResult(str);
					makeTable(); 
					try{
						db.connect.close();
						} 
					catch (SQLException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				}
			});
	}
	
	OnClickListener first_radio_listener = new OnClickListener (){
		 public void onClick(View v) {
			 dongy.setChecked(false);
		 }
		};
		OnClickListener first_radio_khongdongy = new OnClickListener (){
			 public void onClick(View v) {
				 khongdongy.setChecked(false);
			 }
			};
			
	public ArrayList getResult(String query) {
		ArrayList result = new ArrayList();
		int count = 1;

		ResultSet rs = db.QuerySELECT(query);
		try {
			while (rs.next()) {
				 
				String res = count + "_" 
						+ rs.getString("sta") + "_" + rs.getString("refno") + "_"
						+ rs.getString("refdate") + "_"
				+ rs.getString("CustID") + "_"
						+ rs.getString("CustName") + "_" 
										+ rs.getString("amount");
				result.add(res);
				 
				count++;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	public void doOpenChildActivity_Order(int chinhanh,String DatabaseName,String UserName,String RefNo,String refdate,String CustID,String CustName)
    {
        Bundle bundle=new Bundle();
        Intent myIntent=new Intent(this, Reports2_detail.class);
       bundle.putInt("chinhanh", chinhanh);
       bundle.putString("DatabaseName", DatabaseName);
       bundle.putString("UserName", UserName);
       bundle.putString("RefNo", RefNo);
       bundle.putString("CustID", CustID);
       bundle.putString("refdate", refdate);
       bundle.putString("CustName", CustName);
       myIntent.putExtra("MyPackage", bundle);
        startActivity(myIntent);
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
	public void FixTableForm() {
		TableRow tr = new TableRow(this);
		tr.setBackgroundColor(Color.WHITE);

		String[] columnname = { "Chọn","N", "T.thái", "Số CT", "Ngày", "Mã", "Tên KH",
				"Thành tiền"};

		TableRow.LayoutParams lp = new TableRow.LayoutParams(width / 10,
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp1 = new TableRow.LayoutParams((width / 10),
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp2 = new TableRow.LayoutParams( (width / 8),
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp3 = new TableRow.LayoutParams((width / 10),
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp4 = new TableRow.LayoutParams((width / 8),
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp5 = new TableRow.LayoutParams(3*(width / 10),
				LayoutParams.WRAP_CONTENT);

		TableRow.LayoutParams lp51 = new TableRow.LayoutParams(width / 20,
				LayoutParams.WRAP_CONTENT);

		TableRow.LayoutParams lp81 = new TableRow.LayoutParams(width /8,
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


		TextView tv21 = new TextView(this);
		tv21.setPadding(20, 20, 20, 20);
		tv21.setBackgroundColor(Color.WHITE);
		tv21.setText(columnname[1]);
		tv21.setWidth((width / 20));
		tv21.setTextColor(Color.BLACK);
		tv21.setGravity(Gravity.CENTER);
		tv21.setBackgroundDrawable(gd);
		tv21.setLayoutParams(lp51);
		tr.addView(tv21);
		
		TextView tv2 = new TextView(this);
		tv2.setPadding(20, 20, 20, 20);
		tv2.setBackgroundColor(Color.WHITE);
		tv2.setText(columnname[2]);
		tv2.setWidth(  (width / 12));
		tv2.setTextColor(Color.BLACK);
		tv2.setGravity(Gravity.CENTER);
		tv2.setBackgroundDrawable(gd);
		tv2.setLayoutParams(lp1);
		tr.addView(tv2);

		TextView tv3 = new TextView(this);
		tv3.setPadding(20, 20, 20, 20);
		tv3.setBackgroundColor(Color.WHITE);
		tv3.setText(columnname[3]);
		tv3.setWidth( (width / 8));
		tv3.setTextColor(Color.BLACK);
		tv3.setGravity(Gravity.CENTER);
		tv3.setBackgroundDrawable(gd);
		tv3.setLayoutParams(lp2);
		tr.addView(tv3);

		TextView tv4 = new TextView(this);
		tv4.setPadding(20, 20, 20, 20);
		tv4.setBackgroundColor(Color.WHITE);
		tv4.setText(columnname[4]);
		tv4.setWidth( (width / 10));
		tv4.setTextColor(Color.BLACK);
		tv4.setGravity(Gravity.CENTER);
		tv4.setBackgroundDrawable(gd);
		tv4.setLayoutParams(lp3);
		tr.addView(tv4);

		TextView tv5 = new TextView(this);
		tv5.setPadding(20, 20, 20, 20);
		tv5.setBackgroundColor(Color.WHITE);
		tv5.setText(columnname[5]);
		tv5.setWidth(  (width / 8));
		tv5.setTextColor(Color.BLACK);
		tv5.setGravity(Gravity.CENTER);
		tv5.setBackgroundDrawable(gd);
		tv5.setLayoutParams(lp4);
		tr.addView(tv5);

		TextView tv6 = new TextView(this);
		tv6.setPadding(20, 20, 20, 20);
		tv6.setBackgroundColor(Color.WHITE);
		tv6.setText(columnname[6]);
		tv6.setWidth(3*(width / 10));
		tv6.setTextColor(Color.BLACK);
		tv6.setGravity(Gravity.CENTER);
		tv6.setBackgroundDrawable(gd);
		tv6.setLayoutParams(lp5);
		tr.addView(tv6);

		TextView tv71 = new TextView(this);
		tv71.setPadding(20, 20, 20, 20);
		tv71.setBackgroundColor(Color.WHITE);
		tv71.setText(columnname[7]);
		tv71.setWidth(width /8);
		tv71.setTextColor(Color.BLACK);
		tv71.setGravity(Gravity.CENTER);
		tv71.setBackgroundDrawable(gd);
		tv71.setLayoutParams(lp81);
		tv71.setHeight(100);
		tr.addView(tv71);
		
		tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
	}
	public void makeTable() {
		// make column index
		tl.removeAllViews();
		FixTableForm();

		for (int i = 0; i < vdoituong.size(); i++) {
			TableRow tr2 = new TableRow(this);
			tr2.setBackgroundColor(Color.WHITE);
			final String[] info = vdoituong.get(i).split("_");
//
			Button btn = new Button(this);
			 btn.setText("Chi tiết");						
			 GradientDrawable gd = new GradientDrawable();
				gd.setStroke(1, Color.BLACK);
			btn.setBackgroundDrawable(gd);
			TableRow.LayoutParams lp7 = new TableRow.LayoutParams(width /10,
					LayoutParams.MATCH_PARENT);
			btn.setLayoutParams(lp7);
			btn.setPadding(20, 20, 20, 20);
			tr2.addView(btn);
			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					TableRow tablerow = (TableRow)v.getParent();
					TextView items = (TextView) tablerow.getChildAt(3);
					 String str = items.getText().toString();
					 try{
					// showDialog(str);
					 Intent callerIntent=getIntent();
				        Bundle packageFromCaller= callerIntent.getBundleExtra("MyPackage");

				        int chinhanh=1;
				       String DatabaseName=packageFromCaller.getString("DatabaseName");
				       String UserName=packageFromCaller.getString("UserName");
				       
				       
				       TextView Refdate1 = (TextView) tablerow.getChildAt(4);
						 String Refdate12 = Refdate1.getText().toString();
						 
				       TextView Custid = (TextView) tablerow.getChildAt(5);
						 String Custids = Custid.getText().toString();
						 
						 TextView Custname = (TextView) tablerow.getChildAt(6);
						 String Custnames = Custname.getText().toString();
						 
				       doOpenChildActivity_Order(chinhanh,DatabaseName,UserName,str,Refdate12,Custids,Custnames);
					
					 }catch(Exception e){}
				}
			});
			// stt
			TextView tv = new TextView(this);
			tv.setText(info[0]);
			tv.setTextColor(Color.BLACK);
			tv.setBackgroundColor(Color.WHITE);
			tv.setGravity(Gravity.LEFT);
			
			tv.setBackgroundDrawable(gd);
			TableRow.LayoutParams lp = new TableRow.LayoutParams(width / 30,
					LayoutParams.MATCH_PARENT);
			tv.setLayoutParams(lp);
			tv.setPadding(20, 20, 20, 20);
			tr2.addView(tv);

			// ma kh
			TextView tv12 = new TextView(this);
			tv12.setText(info[1]);
			tv12.setTextColor(Color.BLACK);
			tv12.setBackgroundColor(Color.WHITE);
			tv12.setGravity(Gravity.LEFT);
			tv12.setBackgroundDrawable(gd);
			TableRow.LayoutParams lp12 = new TableRow.LayoutParams((width / 10),
					LayoutParams.MATCH_PARENT);
			tv12.setLayoutParams(lp12);
			tv12.setPadding(20, 20, 20, 20);
			tr2.addView(tv12);
			// ma kh
			TextView tv1 = new TextView(this);
			tv1.setText(info[2]);
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
			tv2.setText((info[3]));
			tv2.setTextColor(Color.BLACK);
			tv2.setBackgroundColor(Color.WHITE);
			tv2.setGravity(Gravity.LEFT);
			tv2.setBackgroundDrawable(gd);
			TableRow.LayoutParams lp2 = new TableRow.LayoutParams(
					 (width / 10), LayoutParams.MATCH_PARENT);
			tv2.setLayoutParams(lp2);
			tv2.setPadding(20, 20, 20, 20);
			tr2.addView(tv2);

			// dia chi
			TextView tv3 = new TextView(this);
			tv3.setText(info[4]);
			tv3.setTextColor(Color.BLACK);
			tv3.setBackgroundColor(Color.WHITE);
			tv3.setGravity(Gravity.LEFT);
			tv3.setBackgroundDrawable(gd);
			TableRow.LayoutParams lp3 = new TableRow.LayoutParams(
					  (width / 10), LayoutParams.MATCH_PARENT);
			tv3.setLayoutParams(lp3);
			tv3.setPadding(20, 20, 20, 20);
			tr2.addView(tv3);

			// nhom kh
			TextView tv4 = new TextView(this);
			tv4.setText(info[5]);
			tv4.setTextColor(Color.BLACK);
			tv4.setBackgroundColor(Color.WHITE);
			tv4.setGravity(Gravity.LEFT);
			tv4.setBackgroundDrawable(gd);
			TableRow.LayoutParams lp4 = new TableRow.LayoutParams(3*(width / 10),
					LayoutParams.MATCH_PARENT);
			tv4.setLayoutParams(lp4);
			tv4.setPadding(20, 20, 20, 20);
			tr2.addView(tv4);
 
			// nhom kh
						TextView tv5 = new TextView(this);
						 DecimalFormat format21 = new DecimalFormat("#,##0;-#,##0");
							Double value21 = Double.valueOf(info[6]) ;
							String formatted21 = format21.format(value21); 
							tv5.setText(formatted21);
						//tv5.setText(info[6]);
						tv5.setTextColor(Color.BLACK);
						tv5.setBackgroundColor(Color.WHITE);
						tv5.setGravity(Gravity.LEFT);
						tv5.setBackgroundDrawable(gd);
						TableRow.LayoutParams lp5 = new TableRow.LayoutParams((width /8),
								LayoutParams.MATCH_PARENT);
						tv5.setLayoutParams(lp5);
						tv5.setPadding(20, 20, 20, 20);
						tr2.addView(tv5);

						
						
			tl.addView(tr2, new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		}
	}
	public void showDialog(final String phone) throws Exception
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(Report_em.this);

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
}
