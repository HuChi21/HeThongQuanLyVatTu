package bmtsoft.net.serp;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
public class clscall  extends Activity {
	 private Button button;
	 private EditText etPhoneno;

	  public void onCreate(Bundle savedInstanceState) {

	   super.onCreate(savedInstanceState);
	  setContentView(R.layout.callphone);
	  button = (Button) findViewById(R.id.btncall);
	  etPhoneno = (EditText) findViewById(R.id.phone);
	  // add button listener
	  button.setOnClickListener(new OnClickListener() {

	    @Override
	   public void onClick(View arg0) {
	    String phnum = etPhoneno.getText().toString();
	    Intent callIntent = new Intent(Intent.ACTION_CALL);
	    callIntent.setData(Uri.parse("tel:" + phnum));
	    startActivity(callIntent);
	   }
	  });
	 }


}
