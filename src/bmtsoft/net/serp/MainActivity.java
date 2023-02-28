package bmtsoft.net.serp;

import bmtsoft.net.serp.DBManage;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import android.widget.EditText;
import 	android.util.*;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.widget.EditText;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;



public class MainActivity extends Activity {
	DBManage db = new DBManage();
	EditText txtDatabase, txtUserID,txtpassword,txtnote;
	Button btnLogin, btnExit, btnRefesh;
	Spinner cbochinhanh;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		txtDatabase = (EditText) findViewById(R.id.txtdatabase);
		txtUserID = (EditText) findViewById(R.id.txtUserID);
		txtpassword = (EditText) findViewById(R.id.txtpassword);
		//txtnote = (EditText) findViewById(R.id.txtNote);
		btnLogin = (Button) findViewById(R.id.btnlogin);
		btnExit = (Button) findViewById(R.id.btnexit2);
		btnRefesh= (Button) findViewById(R.id.btnlamtuoi);
		Readlogon("serp","Logon.txt",txtUserID.getText().toString(),txtpassword.getText().toString());
		
		btnLogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 
				 
	                List<String> list=new ArrayList<String>();
	                db.databasename=txtDatabase.getText().toString();
	                int Chinhanh=1; 
	                list=db.exedatatable(txtUserID.getText().toString(),txtpassword.getText().toString(),Chinhanh);
	                if(list.size()>0)
	                {
	                	writelogon("serp","Logon.txt",txtUserID.getText().toString(),txtpassword.getText().toString(),txtDatabase.getText().toString());
			            finish();
			         //   doOpenChildActivity_call(Chinhanh, txtDatabase.getText().toString(),txtUserID.getText().toString());
		                doOpenChildActivity(Chinhanh, txtDatabase.getText().toString(),txtUserID.getText().toString());
		                
		           //   doOpenChildActivity_msg(Chinhanh, txtDatabase.getText().toString(),txtUserID.getText().toString());
	                }
	               
			}
		});
		txtDatabase.addTextChangedListener(new TextWatcher() {

		        
		        @Override
		        public void onTextChanged(CharSequence s, int start, int before, int count) {
		        		GeneralInfo cls3=new GeneralInfo();
		        	 	List<String> list=new ArrayList<String>();
		        	 	List<String> list5=new ArrayList<String>();
		                db.databasename=txtDatabase.getText().toString();
		                list=db.Getbrand(cls3);
		                
		        }

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count, int after) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void afterTextChanged(Editable s) {
					// TODO Auto-generated method stub
					
				} 

		    });
		btnExit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 finish();
		            System.exit(0);

			}
		});
		btnRefesh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 
				try {
					loadnote();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
	}
	public void doOpenChildActivity(int chinhanh,String DatabaseName,String UserName)
    {
        Bundle bundle=new Bundle();
        Intent myIntent=new Intent(this, mainlogin.class);
       bundle.putInt("chinhanh", chinhanh);
       bundle.putString("DatabaseName", DatabaseName);
       bundle.putString("UserName", UserName);
       myIntent.putExtra("MyPackage", bundle);
        startActivity(myIntent);
    }
	public void loadnote() throws SQLException
{	
		String query = "select * from items";
		db.databasename=txtDatabase.getText().toString();
		 
		ResultSet data = db.QuerySELECT(query);
		 String note="";
		while (data.next()) {
		        
			note=note+data.getString("ItemID"); 
		   
		    
		}
		txtnote.setText(note);
	}
 	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
 
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public boolean isExternalStorageWritable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state)) {
	        return true;
	    }
	    return false;
	}

	/* Checks if external storage is available to at least read */
	public boolean isExternalStorageReadable() {
	    String state = Environment.getExternalStorageState();
	    if (Environment.MEDIA_MOUNTED.equals(state) ||
	        Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
	        return true;
	    }
	    return false;
	}

  private void writelogon(String file_path,String file_name,String UserID,String Pass,String Database)
{
	//File sdcard = Environment.getExternalStorageDirectory();
	// to this path add a new directory path
	//File dir = new File(sdcard.getAbsolutePath() + "/SERP/");
	// create this directory if not already created
	//dir.mkdir();
	
	File External_File ;
	ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext()); //getappcontext for just this activity context get
	 File file = contextWrapper.getDir(file_path, Context.MODE_PRIVATE);  
	 if (!isExternalStorageWritable() || isExternalStorageReadable()) 
	 { 
	 // saveToExternalStorage.setEnabled(false);
		 External_File=new File(getExternalFilesDir(file_path), file_name);
	 }
	 else
	 {
		   External_File = new File(getExternalFilesDir(file_path), file_name);//if ready then create a file for external 
	 }
	 try
	  {
		 //File file = new File(dir, “My-File-Name.txt”);
		 FileOutputStream os = new FileOutputStream(External_File);
		// String data = “This is the content of my file”;
		 os.write( UserID.getBytes());
		 os.write(System.getProperty("line.separator").getBytes());
		 os.write(Pass.getBytes());
		 os.write(System.getProperty("line.separator").getBytes());
		 os.write( Database.getBytes());
		
		 
		 os.close();
		/* 
	   FileInputStream fis = new FileInputStream(External_File);
	   DataInputStream in = new DataInputStream(fis);
	   BufferedReader br =new BufferedReader(new InputStreamReader(in));
	   String strLine;
	   while ((strLine = br.readLine()) != null) 
	   {
	    myData = myData + strLine;
	   }
	   in.close();
	   */
	  }
	  catch (IOException e) 
	  {
	   e.printStackTrace();
	  }
	  //InputText.setText("Save data of External file::::  "+myData);
	}
  private void Readlogon(String file_path,String file_name,String UserID,String Pass)
  {
   
  	File External_File ;
  	ContextWrapper contextWrapper = new ContextWrapper(getApplicationContext()); //getappcontext for just this activity context get
  	 File file = contextWrapper.getDir(file_path, Context.MODE_PRIVATE);  
  	 if (!isExternalStorageWritable() || isExternalStorageReadable()) 
  	 { 
  	 // saveToExternalStorage.setEnabled(false);
  		 External_File=new File(getExternalFilesDir(file_path), file_name);
  	 }
  	 else
  	 {
  		   External_File = new File(getExternalFilesDir(file_path), file_name);//if ready then create a file for external 
  	 }
  	 try
  	  {
  		  
  	   FileInputStream fis = new FileInputStream(External_File);
  	   DataInputStream in = new DataInputStream(fis);
  	   BufferedReader br =new BufferedReader(new InputStreamReader(in));
  	   String strLine;
  	   int i=0;
  	   while ((strLine = br.readLine()) != null) 
  	   {
  		   if(i==0)
  		   {txtUserID.setText(strLine);}
  		   else if(i==1)
  		   {txtpassword.setText(strLine);}
  		   else if(i==2)
  		   {txtDatabase.setText(strLine);}
  	       i=i+1;
  	   }
  	   in.close();
  	  
  	  }
  	  catch (IOException e) 
  	  {
  	   e.printStackTrace();
  	  }
  	  //InputText.setText("Save data of External file::::  "+myData);
  	}
  
  
  public void doOpenChildActivity_itemsearch(int chinhanh,String DatabaseName,String UserName)
  {
      Bundle bundle=new Bundle();
      Intent myIntent=new Intent(this, clsseachitems.class);
     bundle.putInt("chinhanh", chinhanh);
     bundle.putString("DatabaseName", DatabaseName);
     bundle.putString("UserName", UserName);
     myIntent.putExtra("MyPackage", bundle);
      startActivity(myIntent);
  }
  public void doOpenChildActivity_call(int chinhanh,String DatabaseName,String UserName)
  {
      Bundle bundle=new Bundle();
      Intent myIntent=new Intent(this, clscall.class);
     bundle.putInt("chinhanh", chinhanh);
     bundle.putString("DatabaseName", DatabaseName);
     bundle.putString("UserName", UserName);
     myIntent.putExtra("MyPackage", bundle);
      startActivity(myIntent);
  }
} 
