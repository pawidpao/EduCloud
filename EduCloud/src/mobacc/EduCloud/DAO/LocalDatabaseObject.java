package mobacc.EduCloud.DAO;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LocalDatabaseObject extends SQLiteOpenHelper {

	private final static String DATABASE_NAME = "EduCloudLocal";
	private final static int DATABASE_VERSION = 1;
	
	//Account Database Table
	private final static String DATABASE_TABLE_ACCOUNT = "Account";
	private final static String COLUMN_ACCOUNT_ID = "id";//primary key.
	private final static String COLUMN_ACCOUNT_EMAIL = "email";//Email.
	private final static String COLUMN_ACCOUNT_STATUS = "status";//logged-in or logged-out.
	private final static String ACCOUNT_STATUS_LOGGED_IN = "logged-in";
	private final static String ACCOUNT_STATUS_LOGGED_OUT = "logged-out";
	private final static String CREATE_TABLE_ACCOUNT = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE_ACCOUNT + "(" 
			+ COLUMN_ACCOUNT_ID + " integer primary key autoincrement, " + COLUMN_ACCOUNT_EMAIL + " text not null, "
			+ COLUMN_ACCOUNT_STATUS + " text not null)";
	
	//Local Course Materials Database Table
	private final static String DATABASE_TABLE_LOCALCM = "LocalCourseMaterials";
	private final static String COLUMN_LOCALCM_ID = "lcm_id";//primary key.
	private final static String COLUMN_LOCALCM_TITLE = "title";//title of cm.
	private final static String COLUMN_LOCALCM_DESCRIPTION = "description";//description of cm.
	private final static String COLUMN_LOCALCM_TAGS = "tags";//Tags of the cm.
	private final static String COLUMN_LOCALCM_PATH = "path";//file path of cm.
	private final static String COLUMN_LOCALCM_UPLOADER = "uploader";//email of the uploader of the cm. Gotten when downloaded or uploaded.
	private final static String CREATE_TABLE_LOCALCM = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE_LOCALCM + "("
			+ COLUMN_LOCALCM_ID + " integer primary key autoincrement, " + COLUMN_LOCALCM_TITLE + " text not null, "
			+ COLUMN_LOCALCM_DESCRIPTION + " text not null, " + COLUMN_LOCALCM_TAGS + " text not null, "
			+ COLUMN_LOCALCM_PATH + " text not null, " + COLUMN_LOCALCM_UPLOADER + " text not null)";

	//Local Lesson Plans Database Table
	private final static String DATABASE_TABLE_LOCALLP = "LocalLessonPlans";
	private final static String COLUMN_LOCALLP_ID = "llp_id";//primary key.
	private final static String COLUMN_LOCALLP_TITLE = "title";//title of cm.
	private final static String COLUMN_LOCALLP_DESCRIPTION = "description";//description of cm.
	private final static String COLUMN_LOCALLP_TAGS = "tags";//Tags of the cm.
	private final static String COLUMN_LOCALLP_PATH = "path";//file path of cm.
	private final static String COLUMN_LOCALLP_UPLOADER = "uploader";//email of the uploader of the cm. Gotten when downloaded or uploaded.
	private final static String CREATE_TABLE_LOCALLP = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE_LOCALLP + "("
			+ COLUMN_LOCALLP_ID + " integer primary key autoincrement, " + COLUMN_LOCALLP_TITLE + " text not null, "
			+ COLUMN_LOCALLP_DESCRIPTION + " text not null, " + COLUMN_LOCALLP_TAGS + " text not null, "
			+ COLUMN_LOCALLP_PATH + " text not null, " + COLUMN_LOCALLP_UPLOADER + " text not null)";
	
	public LocalDatabaseObject(Context context){
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_ACCOUNT);
		db.execSQL(CREATE_TABLE_LOCALCM);
		db.execSQL(CREATE_TABLE_LOCALLP);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_ACCOUNT);
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_LOCALCM);
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_LOCALLP);
		onCreate(db);
	}
	/* ACCOUNT FUNCTIONS START */
	//Creates a new row whenever someone logs in.
	public void createNewAccountInstance(AccountData myAcctData){
		myAcctData.setStatus(ACCOUNT_STATUS_LOGGED_IN);
		SQLiteDatabase myDB = this.getWritableDatabase();
		ContentValues myCV = new ContentValues();
		myCV.put(COLUMN_ACCOUNT_EMAIL, myAcctData.getEmail());
		myCV.put(COLUMN_ACCOUNT_STATUS, myAcctData.getStatus());
		myDB.insert(DATABASE_TABLE_ACCOUNT, null, myCV);
		myDB.close();
	}
	
	//Returns true if someone is logged in.
	public boolean someoneIsLoggedIn(){
		SQLiteDatabase myDB = this.getReadableDatabase();
		String strQuery = "SELECT " + COLUMN_ACCOUNT_STATUS + " FROM " + DATABASE_TABLE_ACCOUNT + ";";
		Cursor myCursor = myDB.rawQuery(strQuery,null);
		boolean check = false;
		
		if(myCursor.moveToFirst()){
			while(!myCursor.isAfterLast() && !check){
				if(myCursor.getString(myCursor.getColumnIndex(COLUMN_ACCOUNT_STATUS)).equals(ACCOUNT_STATUS_LOGGED_IN)){
					check = true;
				}
				myCursor.moveToNext();
			}
		}
		myDB.close();
		return check;
	}
	
	//Returns the Account that is logged in.
	public AccountData getAccountLoggedIn(){
		SQLiteDatabase myDB = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + DATABASE_TABLE_ACCOUNT + " WHERE " + COLUMN_ACCOUNT_STATUS + " = \"" + ACCOUNT_STATUS_LOGGED_IN + "\";";
		Cursor myCursor = myDB.rawQuery(strQuery, null);
		AccountData myAcctData = null;
		if(myCursor.moveToFirst()){
			myAcctData = new AccountData();
			myAcctData.setEmail(myCursor.getString(myCursor.getColumnIndex(COLUMN_ACCOUNT_EMAIL)));
			myAcctData.setStatus(myCursor.getString(myCursor.getColumnIndex(COLUMN_ACCOUNT_STATUS)));
		}
		myDB.close();
		return myAcctData;
	}
	
	//Returns the ID of the account that is logged in.
	public int getLogInID(){
		SQLiteDatabase myDB = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + DATABASE_TABLE_ACCOUNT + " WHERE " + COLUMN_ACCOUNT_STATUS + " = \"" + ACCOUNT_STATUS_LOGGED_IN + "\";";
		Cursor myCursor = myDB.rawQuery(strQuery, null);
		int nID = -1;
		if(myCursor.moveToFirst()){
			nID = myCursor.getInt(myCursor.getColumnIndex("id"));
		}
		myDB.close();
		return nID;
	}
	
	//Logs out the account that is logged in.
	public void accountLogOut(){
		int nID = getLogInID();
		SQLiteDatabase myDB = this.getWritableDatabase();
		String strQuery = "UPDATE " + DATABASE_TABLE_ACCOUNT + " SET " + COLUMN_ACCOUNT_STATUS + " = \"" + ACCOUNT_STATUS_LOGGED_OUT + "\" WHERE id = " + nID +";";
		myDB.execSQL(strQuery);
		myDB.close();
	}
	/* ACCOUNT FUNCTIONS END */
	
	/* LOCALCM FUNCTIONS START */
	//Adds a new course material instance in the local database.
	public void createNewLocalCM(CourseMaterialsData newCMD){
		SQLiteDatabase myDB = this.getWritableDatabase();
		ContentValues myCV = new ContentValues();
		myCV.put(COLUMN_LOCALCM_TITLE, newCMD.getTitle());
		myCV.put(COLUMN_LOCALCM_DESCRIPTION, newCMD.getDescription());
		myCV.put(COLUMN_LOCALCM_TAGS, newCMD.getTags());
		myCV.put(COLUMN_LOCALCM_PATH, newCMD.getPath());
		myCV.put(COLUMN_LOCALCM_UPLOADER, newCMD.getUploader());
		myDB.insert(DATABASE_TABLE_LOCALCM, null, myCV);
		myDB.close();
		
	}
	//Retrieves all course materials in the local database.
	public ArrayList<CourseMaterialsData> getLocalCMList(){
		SQLiteDatabase myDB = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + DATABASE_TABLE_LOCALCM;
		Cursor c = myDB.rawQuery(strQuery, null);
		ArrayList<CourseMaterialsData> alCMList = new ArrayList<CourseMaterialsData>();
		if(c.moveToFirst()){
			while(!c.isAfterLast()){
				CourseMaterialsData cmd = new CourseMaterialsData();
				cmd.setCm_id(c.getInt(c.getColumnIndex(COLUMN_LOCALCM_ID)));
				cmd.setTitle(c.getString(c.getColumnIndex(COLUMN_LOCALCM_TITLE)));
				cmd.setDescription(c.getString(c.getColumnIndex(COLUMN_LOCALCM_DESCRIPTION)));
				cmd.setTags(c.getString(c.getColumnIndex(COLUMN_LOCALCM_TAGS)));
				cmd.setPath(c.getString(c.getColumnIndex(COLUMN_LOCALCM_PATH)));
				cmd.setUploader(c.getString(c.getColumnIndex(COLUMN_LOCALCM_UPLOADER)));
				alCMList.add(cmd);
				c.moveToNext();
			}
		}
		c.close();
		myDB.close();
		return alCMList;
	}
	
	public ArrayList<CourseMaterialsData> searchLocalCM(String keyword){
		SQLiteDatabase myDB = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + DATABASE_TABLE_LOCALCM + " WHERE " + COLUMN_LOCALCM_TAGS + " LIKE \'%" + keyword + "%\'";
		Cursor c = myDB.rawQuery(strQuery, null);
		ArrayList<CourseMaterialsData> alCMList = new ArrayList<CourseMaterialsData>();
		if(c.moveToFirst()){
			while(!c.isAfterLast()){
				CourseMaterialsData cmd = new CourseMaterialsData();
				cmd.setCm_id(c.getInt(c.getColumnIndex(COLUMN_LOCALCM_ID)));
				cmd.setTitle(c.getString(c.getColumnIndex(COLUMN_LOCALCM_TITLE)));
				cmd.setDescription(c.getString(c.getColumnIndex(COLUMN_LOCALCM_DESCRIPTION)));
				cmd.setTags(c.getString(c.getColumnIndex(COLUMN_LOCALCM_TAGS)));
				cmd.setPath(c.getString(c.getColumnIndex(COLUMN_LOCALCM_PATH)));
				cmd.setUploader(c.getString(c.getColumnIndex(COLUMN_LOCALCM_UPLOADER)));
				alCMList.add(cmd);
				c.moveToNext();
			}
		}
		c.close();
		myDB.close();
		return alCMList;
	}
	/* LOCALCM FUNCTIONS END */
	
	/* LOCALLP FUNCTIONS START */
	//Adds new lesson plans to the local database when upload.
	public void createNewLocalLP(LessonPlansData newLPD){
		SQLiteDatabase myDB = this.getWritableDatabase();
		ContentValues myCV = new ContentValues();
		myCV.put(COLUMN_LOCALLP_TITLE, newLPD.getTitle());
		myCV.put(COLUMN_LOCALLP_DESCRIPTION, newLPD.getDescription());
		myCV.put(COLUMN_LOCALLP_TAGS,newLPD.getTags());
		myCV.put(COLUMN_LOCALLP_PATH, newLPD.getPath());
		myCV.put(COLUMN_LOCALLP_UPLOADER, newLPD.getUploader());
		myDB.insert(DATABASE_TABLE_LOCALLP, null, myCV);
		myDB.close();
		
	}
	//Retrieves all lesson plans in the local database.
	public ArrayList<LessonPlansData> getLocalLPList(){
		SQLiteDatabase myDB = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + DATABASE_TABLE_LOCALLP;
		Cursor c = myDB.rawQuery(strQuery, null);
		ArrayList<LessonPlansData> alLPList = new ArrayList<LessonPlansData>();
		if(c.moveToFirst()){
			while(!c.isAfterLast()){
				LessonPlansData lpd = new LessonPlansData();
				lpd.setLp_id(c.getInt(c.getColumnIndex(COLUMN_LOCALLP_ID)));
				lpd.setTitle(c.getString(c.getColumnIndex(COLUMN_LOCALLP_TITLE)));
				lpd.setDescription(c.getString(c.getColumnIndex(COLUMN_LOCALLP_DESCRIPTION)));
				lpd.setTags(c.getString(c.getColumnIndex(COLUMN_LOCALLP_TAGS)));
				lpd.setPath(c.getString(c.getColumnIndex(COLUMN_LOCALLP_PATH)));
				lpd.setUploader(c.getString(c.getColumnIndex(COLUMN_LOCALLP_UPLOADER)));
				alLPList.add(lpd);
				c.moveToNext();
			}
		}
		c.close();
		myDB.close();
		return alLPList;
	}
	
	public ArrayList<LessonPlansData> searchLocalLP(String keyword){
		SQLiteDatabase myDB = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + DATABASE_TABLE_LOCALLP + " WHERE " + COLUMN_LOCALLP_TAGS + " LIKE \'%" + keyword + "%\'";
		Cursor c = myDB.rawQuery(strQuery, null);
		ArrayList<LessonPlansData> alLPList = new ArrayList<LessonPlansData>();
		if(c.moveToFirst()){
			while(!c.isAfterLast()){
				LessonPlansData lpd = new LessonPlansData();
				lpd.setLp_id(c.getInt(c.getColumnIndex(COLUMN_LOCALLP_ID)));
				lpd.setTitle(c.getString(c.getColumnIndex(COLUMN_LOCALLP_TITLE)));
				lpd.setDescription(c.getString(c.getColumnIndex(COLUMN_LOCALLP_DESCRIPTION)));
				lpd.setTags(c.getString(c.getColumnIndex(COLUMN_LOCALLP_TAGS)));
				lpd.setPath(c.getString(c.getColumnIndex(COLUMN_LOCALLP_PATH)));
				lpd.setUploader(c.getString(c.getColumnIndex(COLUMN_LOCALLP_UPLOADER)));
				alLPList.add(lpd);
				c.moveToNext();
			}
		}
		c.close();
		myDB.close();
		return alLPList;
	}
	/* LOCALLP FUNCTIONS END */

	
}
