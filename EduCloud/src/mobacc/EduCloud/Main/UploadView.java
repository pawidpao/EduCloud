package mobacc.EduCloud.Main;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import com.google.android.gms.common.AccountPicker;

import mobacc.EduCloud.DAO.AccessDatabaseObject;
import mobacc.EduCloud.DAO.AccountData;
import mobacc.EduCloud.DAO.CourseMaterialsData;
import mobacc.EduCloud.DAO.LessonPlansData;
import mobacc.EduCloud.DAO.LocalDatabaseObject;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class UploadView extends Activity {
	
	private static final int CHOOSE_ACCOUNT = 0;
	private static final int PICKFILE_RESULT_CODE = 1;
	private String[] strList = {"com.google"};
	
	private static final String LOCAL_LESSONPLANS_DIRECTORY = "sdcard/Download/EduCloud/Lesson Plans/";
	private static final String LOCAL_COURSEMATERIALS_DIRECTORY = "sdcard/Download/EduCloud/Course Materials/";
	private static final String ACCESS_LESSONPLANS_DIRECTORY = "sdcard/Download/EduCloud/Access/Lesson Plans/";
	private static final String ACCESS_COURSEMATERIALS_DIRECTORY = "sdcard/Download/EduCloud/Access/Course Materials/";
	
	private LocalDatabaseObject myLocalDAO;
	private AccessDatabaseObject myAccessDAO;
	
	private EditText etUploadTitle, etFilePath, etFileName, etUploadTags, etUploadDescription;
	private Spinner spinFileType;
	private Button btnUpload;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.upload_view);
		
		myLocalDAO = new LocalDatabaseObject(this);
		myAccessDAO = new AccessDatabaseObject(this);
		
		etUploadTitle = (EditText)findViewById(R.id.etUploadTitle);
		etFilePath = (EditText)findViewById(R.id.etFilePath);
		etFilePath.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				etFileFieldClicked();
			}
			
		});
		etFileName = (EditText)findViewById(R.id.etFileName);
		etFileName.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				etFileFieldClicked();
			}
			
		});
		etUploadDescription = (EditText)findViewById(R.id.etUploadDescription);
		etUploadTags = (EditText)findViewById(R.id.etUploadTags); 
		spinFileType = (Spinner)findViewById(R.id.spinFileType);
		btnUpload = (Button)findViewById(R.id.btnUpload);
		btnUpload.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btnUploadClicked();
			}
			
		});
		
		if(myLocalDAO.someoneIsLoggedIn()){
			//Someone is logged in.
		}
		else{
			//Ask for email.
			chooseAccount(null);
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		getMenuInflater().inflate(R.menu.upload_menu, menu);
		return true;
		
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		super.onOptionsItemSelected(item);
		switch(item.getItemId()){
			case R.id.upload_log_out: upload_log_outClicked();
		}
		
		return true;
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
			myAcctData.setEmail(data.getStringExtra(AccountManager.KEY_ACCOUNT_NAME));
			myAcctData.setStatus("logged-in");
			myLocalDAO.createNewAccountInstance(myAcctData);
		}
		else if(requestCode == PICKFILE_RESULT_CODE && data != null){
			String FilePath = data.getData().getPath();
			etFilePath.setText(FilePath);
			File file = new File(FilePath);
			etFileName.setText(file.getName());
		}
	}
	
	private void upload_log_outClicked() {
		// TODO Auto-generated method stub
		myLocalDAO.accountLogOut();
		if(myLocalDAO.someoneIsLoggedIn()){
			
		}
		else{
			chooseAccount(null);
		}
		
	}
	
	private boolean copyLocalLessonPlan(LessonPlansData lpd){
		InputStream in = null;
	    OutputStream out = null;
	    boolean check = true;
	    try {
	        //create output directory if it doesn't exist
	        File dir = new File (LOCAL_LESSONPLANS_DIRECTORY); 
	        if (!dir.exists()){
	            dir.mkdirs();
	        }
	        in = new FileInputStream(etFilePath.getText().toString());        
	        out = new FileOutputStream(LOCAL_LESSONPLANS_DIRECTORY + lpd.getFilename());

	        byte[] buffer = new byte[1024];
	        int read;
	        while ((read = in.read(buffer)) != -1) {
	            out.write(buffer, 0, read);
	        }
	        in.close();
	        in = null;
	        // write the output file
	        out.flush();
	        out.close();
	        out = null;
	        check = true;
	    }catch (FileNotFoundException fnfe1) {
	        Log.e("tag", fnfe1.getMessage());
	        Toast.makeText(this, "File not Found", Toast.LENGTH_SHORT).show();
	        check = false;
	    }catch (Exception e) {
	        Log.e("tag", e.getMessage());
	        check = false;
	    }
	    return check;
	}
	
	private boolean copyAccessLessonPlan(LessonPlansData lpd){
		InputStream in = null;
	    OutputStream out = null;
	    boolean check = true;
	    try {
	        //create output directory if it doesn't exist
	        File dir = new File (ACCESS_LESSONPLANS_DIRECTORY); 
	        if (!dir.exists()){
	            dir.mkdirs();
	        }
	        in = new FileInputStream(etFilePath.getText().toString());        
	        out = new FileOutputStream(ACCESS_LESSONPLANS_DIRECTORY + lpd.getFilename());

	        byte[] buffer = new byte[1024];
	        int read;
	        while ((read = in.read(buffer)) != -1) {
	            out.write(buffer, 0, read);
	        }
	        in.close();
	        in = null;
	        // write the output file
	        out.flush();
	        out.close();
	        out = null;
	        check = true;
	    }catch (FileNotFoundException fnfe1) {
	        Log.e("tag", fnfe1.getMessage());
	        Toast.makeText(this, "File not Found", Toast.LENGTH_SHORT).show();
	        check = false;
	    }catch (Exception e) {
	        Log.e("tag", e.getMessage());
	        check = false;
	    }
	    return check;
	}
	
	private boolean copyLocalCourseMaterial(CourseMaterialsData cmd){
		InputStream in = null;
	    OutputStream out = null;
	    boolean check = true;
	    try {
	        //create output directory if it doesn't exist
	        File dir = new File (LOCAL_COURSEMATERIALS_DIRECTORY); 
	        if (!dir.exists()){
	            dir.mkdirs();
	        }
	        in = new FileInputStream(etFilePath.getText().toString());        
	        out = new FileOutputStream(LOCAL_COURSEMATERIALS_DIRECTORY + cmd.getFilename());

	        byte[] buffer = new byte[1024];
	        int read;
	        while ((read = in.read(buffer)) != -1) {
	            out.write(buffer, 0, read);
	        }
	        in.close();
	        in = null;
	        // write the output file
	        out.flush();
	        out.close();
	        out = null;
	        check = true;
	    }catch (FileNotFoundException fnfe1) {
	        Log.e("tag", fnfe1.getMessage());
	        Toast.makeText(this, "File not Found", Toast.LENGTH_SHORT).show();
	        check = false;
	    }catch (Exception e) {
	        Log.e("tag", e.getMessage());
	        check = false;
	    }
	    return check;
	}
	
	private boolean copyAccessCourseMaterial(CourseMaterialsData cmd){
		InputStream in = null;
	    OutputStream out = null;
	    boolean check = true;
	    try {
	        //create output directory if it doesn't exist
	        File dir = new File (ACCESS_COURSEMATERIALS_DIRECTORY); 
	        if (!dir.exists()){
	            dir.mkdirs();
	        }
	        in = new FileInputStream(etFilePath.getText().toString());        
	        out = new FileOutputStream(ACCESS_COURSEMATERIALS_DIRECTORY + cmd.getFilename());

	        byte[] buffer = new byte[1024];
	        int read;
	        while ((read = in.read(buffer)) != -1) {
	            out.write(buffer, 0, read);
	        }
	        in.close();
	        in = null;
	        // write the output file
	        out.flush();
	        out.close();
	        out = null;
	        check = true;
	    }catch (FileNotFoundException fnfe1) {
	        Log.e("tag", fnfe1.getMessage());
	        Toast.makeText(this, "File not Found", Toast.LENGTH_SHORT).show();
	        check = false;
	    }catch (Exception e) {
	        Log.e("tag", e.getMessage());
	        check = false;
	    }
	    return check;
	}
	
	public void btnUploadClicked(){
		String strFileType = spinFileType.getSelectedItem().toString();
		if(strFileType.equals("Course Materials")){
			uploadCourseMaterials();
		}
		else if(strFileType.equals("Lesson Plans")){
			uploadLessonPlans();
		}
		
	}
	
	private void uploadLessonPlans(){
		LessonPlansData lpd = new LessonPlansData();
		lpd.setTitle(etUploadTitle.getText().toString());
		lpd.setFilename(etFileName.getText().toString());
		lpd.setDescription(etUploadDescription.getText().toString());
		lpd.setTags(etUploadTags.getText().toString());
		lpd.setPath(LOCAL_LESSONPLANS_DIRECTORY + etFileName.getText().toString());
		if(myLocalDAO.someoneIsLoggedIn()){
			lpd.setUploader(myLocalDAO.getAccountLoggedIn().getEmail());
			if(copyLocalLessonPlan(lpd)){
				myLocalDAO.createNewLocalLP(lpd);
				Toast.makeText(this,"File copy successful", Toast.LENGTH_SHORT).show();
				if(copyAccessLessonPlan(lpd)){
					myAccessDAO.createNewAccessLP(lpd);
					Toast.makeText(this,"Upload successful", Toast.LENGTH_SHORT).show();
					deleteFile();
					clearFields();
				}	
				else{
					Toast.makeText(this,"Upload Fail",Toast.LENGTH_SHORT).show();
				}
			}
			else{
				Toast.makeText(this,"File not copied",Toast.LENGTH_SHORT).show();
			}
		}
		else{
			Toast.makeText(this, "Please Log in first", Toast.LENGTH_SHORT).show();
			chooseAccount(null);
		}
	}
	
	private void uploadCourseMaterials(){
		CourseMaterialsData cmd = new CourseMaterialsData();
		cmd.setTitle(etUploadTitle.getText().toString());
		cmd.setFilename(etFileName.getText().toString());
		cmd.setDescription(etUploadDescription.getText().toString());
		cmd.setTags(etUploadTags.getText().toString());
		cmd.setPath(LOCAL_COURSEMATERIALS_DIRECTORY + etFileName.getText().toString());
		if(myLocalDAO.someoneIsLoggedIn()){
			cmd.setUploader(myLocalDAO.getAccountLoggedIn().getEmail());
			if(copyLocalCourseMaterial(cmd)){
				myLocalDAO.createNewLocalCM(cmd);
				Toast.makeText(this,"File copy successful", Toast.LENGTH_SHORT).show();
				if(copyAccessCourseMaterial(cmd)){
					myAccessDAO.createNewAccessCM(cmd);
					Toast.makeText(this, "Upload successful", Toast.LENGTH_SHORT).show();
					deleteFile();
					clearFields();
				}
				else{
					Toast.makeText(this,"Upload Fail",Toast.LENGTH_SHORT).show();
				}
			}
			else{
				Toast.makeText(this,"File not copied",Toast.LENGTH_SHORT).show();
			}
		}
		else{
			Toast.makeText(this, "Please Log in first", Toast.LENGTH_SHORT).show();
			chooseAccount(null);
		}
	}
	
	private void deleteFile(){
		new File(etFilePath.getText().toString()).delete();
	}
	
	public void clearFields(){
		etFileName.setText("");
		etUploadTags.setText("");
		etUploadDescription.setText("");
		etUploadTitle.setText("");
	}
	
	public void etFileFieldClicked(){
		Intent fileintent = new Intent(Intent.ACTION_GET_CONTENT);
        fileintent.setType("gagt/sdf");
        try {
            startActivityForResult(fileintent, PICKFILE_RESULT_CODE);
        } catch (ActivityNotFoundException e) {
            Log.e("tag", "No activity can handle picking a file. Showing alternatives.");
        }
	}
	
}
