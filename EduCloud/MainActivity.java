package mobacc.EduCloud.Main;

import java.io.File;

import com.google.android.gms.common.AccountPicker;
import com.google.api.client.googleapis.extensions.android.gms.auth.GoogleAccountCredential;
import com.google.api.services.drive.DriveScopes;

import mobacc.EduCloud.DAO.AccountData;
import mobacc.EduCloud.DAO.DAOAccount;
import mobacc.EduCloud.Local.LocalView;
import android.R;
import android.os.Bundle;
import android.os.Environment;
import android.preference.Preference;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	
	private static final int CHOOSE_ACCOUNT = 0;
	private String[] strList = {"com.google"};
	private Button btnLogIn, btnExit;
	private DAOAccount myDBAcct;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		btnLogIn=(Button) findViewById(R.id.button1);
		  Typeface typeface = Typeface.createFromAsset(getAssets(), "Lato-Lig.ttf");
		  btnLogIn.setTypeface(typeface);
		
		
			
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		createInternalDirectory();
		
		btnLogIn = (Button)findViewById(R.id.button1);
		btnLogIn.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btnLogInClicked();
			}
		});
		
		btnExit = (Button)findViewById(R.id.btnExit);
		btnExit.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btnExitClicked();
			}
		});
		
		myDBAcct = new DAOAccount(this);
		if(myDBAcct.someoneIsLoggedIn()){
			AccountData myAcctData = myDBAcct.getAccountLoggedIn();
			Intent myIntent = new Intent(this,LocalView.class);
			startActivity(myIntent);
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
	}
	
	private void chooseAccount(Account savedAccount) {
		// TODO Auto-generated method stub
		startActivityForResult(AccountPicker.newChooseAccountIntent(savedAccount, null, strList, false, null, null, null, null),CHOOSE_ACCOUNT);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if(requestCode == CHOOSE_ACCOUNT && resultCode == RESULT_OK && data != null){
			AccountData myAcctData = new AccountData();
			myAcctData.setName(data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME));
			myAcctData.setStatus("logged-in");
			myDBAcct.createNewAccountInstance(myAcctData);
			Intent myIntent = new Intent(this,LocalView.class);
			startActivity(myIntent);
		}
		
	}
	
	public void btnExitClicked(){
		System.exit(0);
	}
	
	public void btnLogInClicked(){
		chooseAccount(null);
	}
	
	public void createInternalDirectory(){
		//ContextWrapper cw = new ContextWrapper(this);
		String strDirectory = "/sdcard/Download/EduCloud/";
		File directory = new File(strDirectory);
		if(!directory.exists()){
			if(directory.mkdir()){
				Log.d("Successful",directory.getAbsolutePath());
			}
			else{
				Log.d("Status", "directory fails");
			}
			Log.d("Found","Found");
		}
		else{
			
					
		}
	}

}
