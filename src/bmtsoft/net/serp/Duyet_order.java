package bmtsoft.net.serp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Vector;

import bmtsoft.net.serp.R;
import model.LogChinhanh;
import  bmtsoft.net.serp.DBManage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ActionBar.LayoutParams;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
//import android.provider.Telephony.Sms.Conversations;
import android.text.InputType;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
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
import java.math.BigDecimal;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;

public class Duyet_order extends Activity {

	EditText ettimkiem;
	Button btntimkiem, btnnhaplai ,btnthoat,btnok,btnexit;
	Spinner spnhomkh;
	TableLayout tl;
	ArrayList<String> vdoituong = new ArrayList<String>();
	ArrayList<String> Chonhanghoa = new ArrayList<String>();
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
	EditText tv8;
	String Begindate="";
	ArrayList result_chon = new ArrayList();
	int count_chon=1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.test);
		
		 Intent callerIntent=getIntent();
	        Bundle packageFromCaller= callerIntent.getBundleExtra("MyPackage"); 
	        UserName=packageFromCaller.getString("UserName");
	        Begindate=packageFromCaller.getString("Begindate");
		       
	        count_chon=1;
	//	ettimkiem = (EditText) findViewById(R.id.etTimkiemdoituong);
	//	btnnhaplai = (Button) findViewById(R.id.btnclear);
	//	btntimkiem = (Button) findViewById(R.id.btnExit);
	//	tl = (TableLayout) findViewById(R.id.tldoituong);
		spnhomkh = (Spinner) findViewById(R.id.spnhomkh);
		 
	//	btnthoat=(Button)findViewById(R.id.btnExit2);
	//	btnexit=(Button)findViewById(R.id.btnexit);
		// gv = (GridView) findViewById(R.id.gvdoituong);
		btnok=(Button)findViewById(R.id.btncall);
		// get screen size
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		
 
		width = size.x;
		height = size.y;
		btnok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					Intent callerIntent=getIntent();
			        Bundle packageFromCaller= callerIntent.getBundleExtra("MyPackage");

			        int chinhanh2=packageFromCaller.getInt("chinhanh");
			        String DatabaseName2=packageFromCaller.getString("DatabaseName");
			        String UserName2=packageFromCaller.getString("UserName");
			        String sdoituong=packageFromCaller.getString("CustID");
			        
			        Intent myIntent=new Intent(Duyet_order.this, sorderclass.class);
					 
					Bundle bundle = new Bundle();
					bundle.putStringArrayList("result_chon", result_chon); 
					bundle.putString("CustID", sdoituong); 
					bundle.putInt("chinhanh", chinhanh2);
				    bundle.putString("DatabaseName", DatabaseName2);
				    bundle.putString("UserName",UserName2);
				       
					myIntent.putExtra("MyPackage", bundle);
					startActivity(myIntent);
		        } catch (ClassCastException se) {
		            
		        }
			}
		});
	 
		vnhomkh = getGroupItems(UserName);
		//vnhomkh = getGroupCust();
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
				Intent callerIntent=getIntent();
		        Bundle packageFromCaller= callerIntent.getBundleExtra("MyPackage"); 
		       int chinhanh2=packageFromCaller.getInt("chinhanh");
				 db.getData(Begindate,Begindate,"156",UserName,chinhanh2);
				//String str = "Select e.ItemID ,ItemName,cast(UnitCoef as numeric(18, 0)) as UnitCoef, ";

		          // str = str + "cast( (case when i.UnitCoef<>0 then case when (e.CurrentQty/1)< 0 then CEILING((e.CurrentQty/i.UnitCoef)) else floor(round(e.CurrentQty/1,3)) end else 0 end) as numeric(18, 0)) as OpeningQtyC ,";
		           //str = str + " cast( round((case when i.UnitCoef<>0 then (cast(e.CurrentQty as numeric(18, 2)) % cast(i.UnitCoef as numeric(18, 2))) else 0 end),0) as numeric(18, 0)) as  OpeningQtyP,cast(i.price as numeric(18, 0))as price  ";//,'0' as DSLC,'0' as DSLP,0 as AmountD 
		           
		           //    str = str + ",0 as DatT,0 as DatL,cast(0 as numeric(18, 0))  as Amount from StockEntries e inner join Items i on e.ItemID=i.ItemID  inner join stocks st on (st.stockid=e.stockid)";
		           
		          // str = str + "  where  i.Inactive=0 and (((case when i.UnitCoef<>0 then case when (e.CurrentQty/i.UnitCoef)< 0 then CEILING((e.CurrentQty/i.UnitCoef)) else floor(round(e.CurrentQty/1,3)) end else 0 end))>0  or (round((case when i.UnitCoef<>0 then (cast(e.CurrentQty as numeric(18, 2)) % cast(i.UnitCoef as numeric(18, 2))) else 0 end),0))>0) ";

		           //    str = str + " and e.stockid='156'";
				 	String str="Select  e.ItemID ,e.ItemName,cast(e.UniCoef as numeric(18, 0)) as UnitCoef,e.nguyenck as OpeningQtyC,e.leck as OpeningQtyP,cast(i.price as numeric(18, 0))as price ";
				    str = str + " ,0 as DatT,0 as DatL,cast(0 as numeric(18, 0))  as Amount,e.SLOT,e.SLOL  from vwsokhotonghop_web_mobile e inner join Items i on e.ItemID=i.ItemID  where  i.chkmobile=0 and (SLton>0) and i.price>0 and UserID=N'"+ UserName +"'";
		           
		           if (info[0].toString() != "" && info[0].toString() != "0")
		           {
		               str = str + " and i.Groupid like '" + info[0].toString() + "%'";
		           } 
		            
		           String str2="select note from User_Stocks where  userid='" + UserName + "'";
		           String nhom = "";
		          
		           ResultSet rs = db.QuerySELECT(str2);
		           String resn ="";
		   		try {
		   			while (rs.next()) {
		   				 resn = rs.getString("note")  ;
		   			}
		   		} catch (SQLException e) {
		   			 
		   		}
		           if (resn.trim().toString().isEmpty() == true)
		           {}else{
		               str = str + " and i.Groupid in(select Data from dbo.Split('" + resn + "',','))";
		           }
		           
		           str = str + " order by i.Groupid,e.ItemID";
		           
			 
		         /*  String query2 = "Select  count(e.ItemID) as NUM";

		            
		           query2 = query2 + " from StockEntries e inner join Items i on e.ItemID=i.ItemID  inner join stocks st on (st.stockid=e.stockid)";
		           
		           query2 = query2 + "  where  i.Inactive=0 and (((case when i.UnitCoef<>0 then case when (e.CurrentQty/i.UnitCoef)< 0 then CEILING((e.CurrentQty/i.UnitCoef)) else floor(round(e.CurrentQty/1,3)) end else 0 end))>0  or (round((case when i.UnitCoef<>0 then (cast(e.CurrentQty as numeric(18, 2)) % cast(i.UnitCoef as numeric(18, 2))) else 0 end),0))>0) ";

		           query2 = query2 + " and e.stockid='156'";
		           
		           if (info[0].toString() != "" && info[0].toString() != "0")
		           {
		        	   query2 = query2 + " and i.Groupid like '" + info[0].toString() + "%'";
		           }  
		           if (resn != "" && resn != "0")
		           {
		        	   query2 = query2 + " and i.Groupid in(select Data from dbo.Split('" + resn + "',','))";
		           }
		           */
				//String query = "Select TOP 50 CustID, ObjectName, Address, GroupID from objects inner join customers on CustID = ObjectID where GroupID like '"
					//	+ info[0] + "%'";

				//String query2 = "Select count(CustID) as NUM from objects inner join customers on CustID = ObjectID where GroupID like '"
					//	+ info[0] + "%'";
				vdoituong = getResult(str);
				//nor = getNumOfRow(query2);
				//min = 0;
				//max = 50;
				makeTable();
				activity = "group";
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


	 
	}
	public Vector<String> getGroupItems(String UserID) {
		
		String str="select note from User_Stocks where  userid='" + UserID + "'";
        String nhom = "";
       
        ResultSet rs = db.QuerySELECT(str);
        String resn ="";
		try {
			while (rs.next()) {
				 resn = rs.getString("note")  ;
			}
		} catch (SQLException e) {
			 
		}
        
		Vector<String> result = new Vector<String>();
		result.add("~~~Tất cả~~~");
		String stritem="";
		 if (resn.trim().toString().isEmpty() == true)
         {
			 stritem="select GroupID,ParentID,GroupName,GroupLevel,IsLeaf from ItemGroups where Inactive=0 order by GroupID";
         }
         else
         {
        	 stritem="select GroupID,ParentID,GroupName,GroupLevel,IsLeaf from ItemGroups where Inactive=0 and groupid in(select Data from dbo.Split('" + resn + "',',')) order by GroupID";
          }
		rs = db.QuerySELECT(stritem);
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
			//tv3.setText(info[3]);
			tv3.setTextColor(Color.BLACK);
			tv3.setBackgroundColor(Color.WHITE);
 			tv3.setGravity(Gravity.RIGHT);
			tv3.setBackgroundDrawable(gd);
			TableRow.LayoutParams lp3 = new TableRow.LayoutParams(
					 (width / 12), LayoutParams.MATCH_PARENT);
			tv3.setLayoutParams(lp3);
			tv3.setPadding(20, 20, 20, 20);
			try{
			DecimalFormat format2 = new DecimalFormat("#,##0;-#,##0");
			Double value2 = Double.valueOf(info[3]) ;
			String formatted2 = format2.format(value2); 
			tv3.setText(formatted2);
			}catch(Exception e){tv3.setText(info[3]);}
			tr2.addView(tv3);

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
				Double value22 = Double.valueOf(info[4]) ;
				String formatted22 = format21.format(value22); 
				tv4.setText(formatted22);
				}catch(Exception e){tv4.setText(info[4]);}
			
			tr2.addView(tv4);
//
			// nhom kh
						TextView tv5 = new TextView(this);
						//tv5.setText(info[5]);
						  tv5.setWidth(width /8);
						tv5.setTextColor(Color.BLACK);
						tv5.setBackgroundColor(Color.WHITE);
						tv5.setGravity(Gravity.RIGHT);
						tv5.setBackgroundDrawable(gd);
						TableRow.LayoutParams lp5 = new TableRow.LayoutParams((width /8),
								LayoutParams.MATCH_PARENT);
						tv5.setLayoutParams(lp5);
						tv5.setPadding(20, 20, 20, 20);
						
						DecimalFormat format = new DecimalFormat("#,##0;-#,##0");
						Double value = Double.valueOf(info[5]) ;
						String formatted = format.format(value); 
						tv5.setText(formatted);
						 
						tr2.addView(tv5);
 
						 tv8 = new EditText(this);
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
							Double value21 = Double.valueOf(info[6]) ;
							String formatted21 = format21.format(value21); 
							tv8.setText(formatted21);
							 
						 
						 tv8.setLayoutParams(lp6);
						tr2.addView(tv8);
			 		
						EditText tv9 = new EditText(this);
						//tv9.setInputType(InputType.TYPE_NULL);
						//tv9.setInputType(InputType.TYPE_NULL);
						//tv9.requestFocus();
			           // InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
			           // mgr.showSoftInput(tv9, InputMethodManager.SHOW_FORCED);
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
							Double value211 = Double.valueOf(info[7]) ;
							String formatted211 = format211.format(value211); 
							tv9.setText(formatted211);
							
						//	InputMethodManager  inputMethodManager =
							//		(InputMethodManager)this.getSystemService(Context.INPUT_METHOD_SERVICE);
								//	inputMethodManager.hideSoftInputFromWindow(tv9.getWindowToken(),0);
						 tv9.setLayoutParams(lp8);
						tr2.addView(tv9);
						 
						
						Button btn = new Button(this);
						if(value211>0 || value21>0)
						{
						btn.setText("Xóa");
						}
						else
						{btn.setText("Chọn");
							
						}
							
						btn.setBackgroundDrawable(gd);
						TableRow.LayoutParams lp7 = new TableRow.LayoutParams(width /9,
								LayoutParams.MATCH_PARENT);
						btn.setLayoutParams(lp7);
						btn.setPadding(20, 20, 20, 20);
						tr2.addView(btn);
			 
						btn.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								
								String ItemName = info[2];
								String id = info[1];
								Bundle bundle = new Bundle(); 
								 TableRow tablerow = (TableRow)v.getParent();
								 Button bn = (Button)tablerow.getChildAt(8);
							 if(bn.getText()=="Chọn")
							 {
								//số lượng lẻ
								 TextView items = (TextView) tablerow.getChildAt(7);
								 String str = items.getText().toString();
								 //thùng
								 TextView sl = (TextView) tablerow.getChildAt(6);
								 String str2 = sl.getText().toString();
								 
								 //TextView gia = (TextView) tablerow.getChildAt(5);
								 String gia2 = info[5];
								 //String gia2 = gia.getText().toString();
								 
								 TextView hs = (TextView) tablerow.getChildAt(10);
								 String hs3 = hs.getText().toString();
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
								 }catch(Exception e)
								 {
									 sl.setText("0");
								 }
								 try{
									 slp=Float.parseFloat(str);
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
									/*if(sltong<sldat)
									{
										items.setText("0");
										sl.setText("0");
										
										 try
							                {
							                    showDialog("Số lượng lớn hơn tồn");
							                }
							                catch (Exception e)
							                {
							                    e.printStackTrace();
							                }
										return;
									}
									**/
								// float a=((slc+(slp/hsqd))*price);
								 try{
									 amount=(double) Math.round(((slc+(slp/hsqd))*price));
								 }catch(Exception e)
								 {}
								 try{
									 TextView am = (TextView) tablerow.getChildAt(9);
									 
									 
									//am.setText(String.format("%.2f", amount)  );
									 DecimalFormat format = new DecimalFormat("#,##0;-#,##0");
										
										Double value = Double.valueOf(amount) ;
										String formatted = format.format(value); 
										am.setText(formatted);
										
									 //  am.setText(new BigDecimal(Double.toString(amount)).toPlainString());
								 }catch(Exception e)
								 {}
								 //sử lý kho
								// String str21="Select st.* from stockentries st inner join stocks s on s.stockid=st.stockid where st.itemid=N'" + id + "' and st.currentqty>0";
						          
						          
						       //    ResultSet rs = db.QuerySELECT(str21);
						           String Stockid ="156";
						   	//	try {
						   		//	while (rs.next()) {
						   		//		Stockid = rs.getString("StockID")  ;
						   		//	}
						   	//	} catch (SQLException e) {
						   			 
						   	//	}
						   		//
								 String res2 =count_chon + "_" + id + "_"
											+ ItemName + "_" 											 
											+ price + "_"
													+ slc + "_"
															+  slp + "_"
															+ new BigDecimal(Double.toString(amount)).toPlainString() + "_"
																	+  Stockid + "_"
															+ hsqd;
								 result_chon.add(res2);
								 Chonhanghoa=result_chon;
								 bn.setText("Xóa");
								 count_chon++;
							 }
							 else  if(bn.getText()=="Xóa")
							 {
								 for(int i=0; i < result_chon.size(); i++)
								 {
									 String mang="",itemid="";
									 mang=result_chon.get(i).toString();
									 final String[] info2 = mang.split("_");
									 itemid=info2[1].toString();
									 if(itemid.trim().toUpperCase().compareTo(id.trim().toUpperCase())==0)
									 {
										 try{
										 result_chon.remove(i);
										 count_chon--;
										 
										 Chonhanghoa=result_chon;
										 }catch(Exception e){}
										 bn.setText("Chọn");
										 try{
											 TextView am = (TextView) tablerow.getChildAt(9);
											   am.setText("0");
										 }catch(Exception e)
										 {}
										 try{
											 TextView am = (TextView) tablerow.getChildAt(6);
											   am.setText("0");
										 }catch(Exception e)
										 {}
										 try{
											 TextView am = (TextView) tablerow.getChildAt(7);
											   am.setText("0");
										 }catch(Exception e)
										 {}
									 }
									 
								 }
									 
								
							 }
								 
							}
						});
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
							Double value2111 = Double.valueOf(info[8]) ;
							String formatted2112 = format2111.format(value2111); 
							tv10.setText(formatted2112);
						tv10.setPadding(20, 20, 20, 20);
						tr2.addView(tv10);
						
						TextView tv11 = new TextView(this);
						tv11.setText(info[9]);
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
						
						//
						TextView tv12 = new TextView(this);
						//tv10.setText(info[8]);
						tv12.setWidth(width /8);
						tv12.setTextColor(Color.BLACK);
						tv12.setBackgroundColor(Color.WHITE);
						tv12.setGravity(Gravity.RIGHT);
						tv12.setBackgroundDrawable(gd);
						TableRow.LayoutParams lp1221 = new TableRow.LayoutParams((width /10),
								LayoutParams.MATCH_PARENT);
						tv12.setLayoutParams(lp1221);
						
						 DecimalFormat format21111 = new DecimalFormat("#,##0;-#,##0");
							Double value21111 = Double.valueOf(info[10]) ;
							String formatted21121 = format21111.format(value21111); 
							tv12.setText(formatted21121);
							tv12.setPadding(20, 20, 20, 20);
						tr2.addView(tv12);
						//
						//
						TextView tv13 = new TextView(this);
						//tv10.setText(info[8]);
						tv13.setWidth(width /8);
						tv13.setTextColor(Color.BLACK);
						tv12.setBackgroundColor(Color.WHITE);
						tv13.setGravity(Gravity.RIGHT);
						tv13.setBackgroundDrawable(gd);
						TableRow.LayoutParams lp12433 = new TableRow.LayoutParams((width /10),
								LayoutParams.MATCH_PARENT);
						tv13.setLayoutParams(lp12433);
						
						 DecimalFormat format211115 = new DecimalFormat("#,##0;-#,##0");
							Double value211115 = Double.valueOf(info[11]) ;
							String formatted211216 = format211115.format(value211115); 
							tv13.setText(formatted211216);
							tv13.setPadding(20, 20, 20, 20);
						tr2.addView(tv13);
						//
			tl.addView(tr2, new TableLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		}
	}
	public void showDialog(final String phone) throws Exception
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(Duyet_order.this);

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
	public ArrayList getResult(String query) {
		ArrayList result = new ArrayList();
		int count = 0;

		ResultSet rs = db.QuerySELECT(query);
		 
		try {
			while (rs.next()) {
				String slp="0";
				String slc="0";
				String Am="0";
				if(result_chon.size()>0)
				{
				for(int i=0; i < result_chon.size(); i++)
				 {
					 String mang="",itemid="";
					 mang=result_chon.get(i).toString();
					 final String[] info2 = mang.split("_");
					 itemid=info2[1].toString();
					 if(itemid.trim().toUpperCase().compareTo(rs.getString("ItemID").toUpperCase())==0)
					 {
						 try{
							 slc =info2[4].toString();
							 slp =info2[5].toString();
							 Am =info2[6].toString();
						 }catch(Exception e){}
						 
						 
					 }else
					 {
						 try{
							 slc =rs.getString("DatT");
							 slp =rs.getString("DatL");
							 Am =rs.getString("Amount");
						 }catch(Exception e){}
					 }
					 
				 }
				}else
				{
					 try{
						 slc =rs.getString("DatT");
						 slp =rs.getString("DatL");
						 Am =rs.getString("Amount");
					 }catch(Exception e){}
				}
				String res = count + "_" + rs.getString("ItemID") + "_"
						+ rs.getString("ItemName") + "_"
						//+ rs.getString("Unitcoef") + "_"
						+ rs.getString("OpeningQtyC") + "_"
				+ rs.getString("OpeningQtyP") + "_"
						+ rs.getString("Price") + "_"
								+ slc + "_"
										+ slp + "_"
										+ Am + "_"
										+ rs.getString("Unitcoef") + "_"
										+ rs.getString("SLOT") + "_"
										+ rs.getString("SLOL");
				result.add(res);
				 
				count++;
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
	
	 

	public Vector<String> getGroupCust() {
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

	public void FixTableForm() {
		TableRow tr = new TableRow(this);
		tr.setBackgroundColor(Color.WHITE);

		String[] columnname = { "#", "Mã", "Tên",  "TồnT", "TồnL","Giá","Đặt.T","Đ.Lẻ","Chọn","T.T","HSQĐ","ĐơnT","ĐơnL"};//"HSQĐ",

		TableRow.LayoutParams lp = new TableRow.LayoutParams(width / 20,
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp1 = new TableRow.LayoutParams((width / 10),
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp2 = new TableRow.LayoutParams(3 * (width / 10),
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp3 = new TableRow.LayoutParams( (width /12),
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp4 = new TableRow.LayoutParams((width / 12),
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp5 = new TableRow.LayoutParams(width / 9,
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp6 = new TableRow.LayoutParams(width /10,
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp7 = new TableRow.LayoutParams(width / 10,
				LayoutParams.WRAP_CONTENT);
		TableRow.LayoutParams lp8 = new TableRow.LayoutParams(width /9,
				LayoutParams.WRAP_CONTENT);
		
		TableRow.LayoutParams lp10 = new TableRow.LayoutParams(width /7,
				LayoutParams.WRAP_CONTENT);
		
		TableRow.LayoutParams lp11 = new TableRow.LayoutParams(width /10,
				LayoutParams.WRAP_CONTENT);
		
		TableRow.LayoutParams lp12 = new TableRow.LayoutParams(width /10,
				LayoutParams.WRAP_CONTENT);
		
		TableRow.LayoutParams lp13 = new TableRow.LayoutParams(width /10,
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
		 
		TextView tv7 = new TextView(this);
		tv7.setPadding(20, 20, 20, 20);
		tv7.setBackgroundColor(Color.WHITE);
		tv7.setText(columnname[8]);
		tv7.setWidth(width /9);
		tv7.setTextColor(Color.BLACK);
		tv7.setGravity(Gravity.CENTER);
		tv7.setBackgroundDrawable(gd);
		tv7.setLayoutParams(lp8);
		tv7.setHeight(100);
		tr.addView(tv7);
		
		TextView tv10 = new TextView(this);
		tv10.setPadding(20, 20, 20, 20);
		tv10.setBackgroundColor(Color.WHITE);
		tv10.setText(columnname[9]);
		tv10.setWidth(width /7);
		tv10.setTextColor(Color.BLACK);
		tv10.setGravity(Gravity.CENTER);
		tv10.setBackgroundDrawable(gd);
		tv10.setLayoutParams(lp10);
		tv10.setHeight(100);
		tr.addView(tv10);
		
		TextView tv11 = new TextView(this);
		tv11.setPadding(20, 20, 20, 20);
		tv11.setBackgroundColor(Color.WHITE);
		tv11.setText(columnname[10]);
		tv11.setWidth(width /9);
		tv11.setTextColor(Color.BLACK);
		tv11.setGravity(Gravity.CENTER);
		tv11.setBackgroundDrawable(gd);
		tv11.setLayoutParams(lp11);
		tv11.setHeight(100);
		tr.addView(tv11);
		
		TextView tv12 = new TextView(this);
		tv12.setPadding(20, 20, 20, 20);
		tv12.setBackgroundColor(Color.WHITE);
		tv12.setText(columnname[11]);
		tv12.setWidth(width /9);
		tv12.setTextColor(Color.BLACK);
		tv12.setGravity(Gravity.CENTER);
		tv12.setBackgroundDrawable(gd);
		tv12.setLayoutParams(lp12);
		tv12.setHeight(100);
		tr.addView(tv12);
		
		TextView tv13 = new TextView(this);
		tv13.setPadding(20, 20, 20, 20);
		tv13.setBackgroundColor(Color.WHITE);
		tv13.setText(columnname[12]);
		tv13.setWidth(width /9);
		tv13.setTextColor(Color.BLACK);
		tv13.setGravity(Gravity.CENTER);
		tv13.setBackgroundDrawable(gd);
		tv13.setLayoutParams(lp13);
		tv13.setHeight(100);
		tr.addView(tv13);
		
		
		tl.addView(tr, new TableLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
	}
}
