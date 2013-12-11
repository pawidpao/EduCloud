package mobacc.EduCloud.Comment;

import java.util.ArrayList;

import com.google.android.gms.common.AccountPicker;

import mobacc.EduCloud.DAO.AccessDatabaseObject;
import mobacc.EduCloud.DAO.AccountData;
import mobacc.EduCloud.DAO.CourseMaterialsData;
import mobacc.EduCloud.DAO.LessonPlansData;
import mobacc.EduCloud.DAO.LocalDatabaseObject;
import mobacc.EduCloud.Main.R;
import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CommentView extends Activity {
	
	private static final int CHOOSE_ACCOUNT = 0;
	private String[] strList = {"com.google"};
	
	private AccessDatabaseObject myAccessDAO;
	private LocalDatabaseObject myLocalDAO;
	private CommentEntryAdapter myCEA;
	
	private TextView tvCommentsTitle, tvCommentsTags;
	private EditText etComment;
	private Button btnCommentSend;
	private ListView lv;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.comments_view);
		tvCommentsTitle = (TextView)findViewById(R.id.tvCommentsTitle);
		tvCommentsTags = (TextView)findViewById(R.id.tvCommentsTags);
		etComment = (EditText)findViewById(R.id.etComment);
		btnCommentSend = (Button)findViewById(R.id.btnCommentSend);
		btnCommentSend.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				btnCommentSendClicked();
			}
			
		});
		
		lv = (ListView)findViewById(R.id.lvCommentsList);
		myCEA = new CommentEntryAdapter(this);
		lv.setAdapter(myCEA);
		
		myAccessDAO = new AccessDatabaseObject(this);
		myLocalDAO = new LocalDatabaseObject(this);
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// TODO Auto-generated method stub
		return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		String strType = getIntent().getExtras().getString("type");
		if(strType.equals("Course Materials")){
			CourseMaterialsData cmd = (CourseMaterialsData)getIntent().getSerializableExtra("data");
			tvCommentsTitle.setText(cmd.getTitle());
			tvCommentsTags.setText(cmd.getTags());
			ArrayList<CommentData> alCommentList = myAccessDAO.getCMCommentList(cmd);
			myCEA.addMultipleEntries(alCommentList);
		}
		else{
			LessonPlansData lpd = (LessonPlansData)getIntent().getSerializableExtra("data");
			tvCommentsTitle.setText(lpd.getTitle());
			tvCommentsTags.setText(lpd.getTags());
			ArrayList<CommentData> alCommentList = myAccessDAO.getLPCommentList(lpd);
			myCEA.addMultipleEntries(alCommentList);
		}
	}
	
	public void btnCommentSendClicked(){
		if(myLocalDAO.someoneIsLoggedIn()){
			//Proceed to send comment.
			String strType = getIntent().getExtras().getString("type");
			if(strType.equals("Course Materials")){
				commentCourseMaterials();
			}
			else{
				commentLessonPlans();
			}
		}
		else{
			chooseAccount(null);
			Toast.makeText(this,"Please log in first",Toast.LENGTH_SHORT).show();
		}
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
	}
	
	public void commentCourseMaterials(){
		CourseMaterialsData cmd = (CourseMaterialsData)getIntent().getSerializableExtra("data");
		CommentData cd = new CommentData();
		cd.setMaterials_id(cmd.getCm_id());
		cd.setCommentator(myLocalDAO.getAccountLoggedIn().getEmail());
		cd.setComment(etComment.getText().toString());
		myAccessDAO.createNewCommentCM(cd);
		myCEA.addEntry(cd);
	}
	
	public void commentLessonPlans(){
		LessonPlansData lpd = (LessonPlansData)getIntent().getSerializableExtra("data");
		CommentData cd = new CommentData();
		cd.setMaterials_id(lpd.getLp_id());
		cd.setCommentator(myLocalDAO.getAccountLoggedIn().getEmail());
		cd.setComment(etComment.getText().toString());
		myAccessDAO.createNewCommentCM(cd);
		myCEA.addEntry(cd);
	}
	
}
