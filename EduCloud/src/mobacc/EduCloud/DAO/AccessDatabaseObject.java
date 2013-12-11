package mobacc.EduCloud.DAO;

import java.util.ArrayList;

import mobacc.EduCloud.Comment.CommentData;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class AccessDatabaseObject extends SQLiteOpenHelper {
	
	private final static String DATABASE_NAME = "EduCloudAccess";
	private final static int DATABASE_VERSION = 2;
	
	//Access Course Materials Database Table
	private final static String DATABASE_TABLE_ACCESSCM = "AccessCourseMaterials";
	private final static String COLUMN_ACCESSCM_ID = "acm_id";//primary key.
	private final static String COLUMN_ACCESSCM_TITLE = "title";//title of cm.
	private final static String COLUMN_ACCESSCM_DESCRIPTION = "description";//description of cm.
	private final static String COLUMN_ACCESSCM_TAGS = "tags";//Tags of the cm.
	private final static String COLUMN_ACCESSCM_UPLOADER = "uploader";//email of the uploader of the cm. Gotten when downloaded or uploaded.
	private final static String CREATE_TABLE_ACCESSCM = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE_ACCESSCM + "("
			+ COLUMN_ACCESSCM_ID + " integer primary key autoincrement, " + COLUMN_ACCESSCM_TITLE + " text not null, "
			+ COLUMN_ACCESSCM_DESCRIPTION + " text not null, " + COLUMN_ACCESSCM_TAGS + " text not null, "
			+ COLUMN_ACCESSCM_UPLOADER + " text not null)";
	
	//Access Lesson Plans Database Table
	private final static String DATABASE_TABLE_ACCESSLP = "AccessLessonPlans";
	private final static String COLUMN_ACCESSLP_ID = "alp_id";//primary key.
	private final static String COLUMN_ACCESSLP_TITLE = "title";//title of cm.
	private final static String COLUMN_ACCESSLP_DESCRIPTION = "description";//description of cm.
	private final static String COLUMN_ACCESSLP_TAGS = "tags";//Tags of the cm.
	private final static String COLUMN_ACCESSLP_UPLOADER = "uploader";//email of the uploader of the cm. Gotten when downloaded or uploaded.
	private final static String CREATE_TABLE_ACCESSLP = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE_ACCESSLP + "("
			+ COLUMN_ACCESSLP_ID + " integer primary key autoincrement, " + COLUMN_ACCESSLP_TITLE + " text not null, "
			+ COLUMN_ACCESSLP_DESCRIPTION + " text not null, " + COLUMN_ACCESSLP_TAGS + " text not null, "
			+ COLUMN_ACCESSLP_UPLOADER + " text not null)";
	
	//Comments Course Materials Database Table
	private final static String DATABASE_TABLE_COMMENTSCM = "CommentsCourseMaterials";
	private final static String COLUMN_COMMENTSCM_ID = "ccm_id";//primary key
	private final static String COLUMN_COMMENTSCM_MATERIALS = "materials_id";//id of the course material foreign key in accesscm
	private final static String COLUMN_COMMENTSCM_COMMENTATOR = "commentator";//email
	private final static String COLUMN_COMMENTSCM_COMMENT = "comment";//comment
	private final static String CREATE_TABLE_COMMENTSCM = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE_COMMENTSCM + "("
			+ COLUMN_COMMENTSCM_ID + " integer primary key autoincrement, " + COLUMN_COMMENTSCM_MATERIALS + " integer not null, "
			+ COLUMN_COMMENTSCM_COMMENTATOR + " text not null, " + COLUMN_COMMENTSCM_COMMENT + " text not null, "
			+ "FOREIGN KEY (" + COLUMN_COMMENTSCM_MATERIALS + ") REFERENCES " + DATABASE_TABLE_ACCESSCM + "(" + COLUMN_ACCESSCM_ID + "))";
	
	//Comments Course Materials Database Table
	private final static String DATABASE_TABLE_COMMENTSLP = "CommentsCourseMaterials";
	private final static String COLUMN_COMMENTSLP_ID = "ccm_id";//primary key
	private final static String COLUMN_COMMENTSLP_MATERIALS = "materials_id";//id of the course material foreign key in accesslp
	private final static String COLUMN_COMMENTSLP_COMMENTATOR = "commentator";//email
	private final static String COLUMN_COMMENTSLP_COMMENT = "comment";//comment
	private final static String CREATE_TABLE_COMMENTSLP = "CREATE TABLE IF NOT EXISTS " + DATABASE_TABLE_COMMENTSLP + "("
			+ COLUMN_COMMENTSLP_ID + " integer primary key autoincrement, " + COLUMN_COMMENTSLP_MATERIALS + " integer not null, "
			+ COLUMN_COMMENTSLP_COMMENTATOR + " text not null, " + COLUMN_COMMENTSLP_COMMENT + " text not null, "
			+ "FOREIGN KEY (" + COLUMN_COMMENTSLP_MATERIALS + ") REFERENCES " + DATABASE_TABLE_ACCESSLP + "(" + COLUMN_ACCESSLP_ID + "))";
	
	public AccessDatabaseObject(Context context){
		super(context,DATABASE_NAME,null,DATABASE_VERSION);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(CREATE_TABLE_ACCESSCM);
		db.execSQL(CREATE_TABLE_ACCESSLP);
		db.execSQL(CREATE_TABLE_COMMENTSCM);
		db.execSQL(CREATE_TABLE_COMMENTSLP);
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_ACCESSCM);
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_ACCESSLP);
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_COMMENTSCM);
		db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_COMMENTSLP);
		onCreate(db);
	}

	/* ACCESSCM FUNCTIONS START */
	//Adds a new course material instance in the access database.
	public void createNewAccessCM(CourseMaterialsData newCMD){
		SQLiteDatabase myDB = this.getWritableDatabase();
		ContentValues myCV = new ContentValues();
		myCV.put(COLUMN_ACCESSCM_TITLE, newCMD.getTitle());
		myCV.put(COLUMN_ACCESSCM_DESCRIPTION, newCMD.getDescription());
		myCV.put(COLUMN_ACCESSCM_TAGS, newCMD.getTags());
		myCV.put(COLUMN_ACCESSCM_UPLOADER, newCMD.getUploader());
		myDB.insert(DATABASE_TABLE_ACCESSCM, null, myCV);
		myDB.close();
		
	}
	//Retrieves all course materials in the access database.
	public ArrayList<CourseMaterialsData> getAccessCMList(){
		SQLiteDatabase myDB = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + DATABASE_TABLE_ACCESSCM;
		Cursor c = myDB.rawQuery(strQuery, null);
		ArrayList<CourseMaterialsData> alCMList = new ArrayList<CourseMaterialsData>();
		if(c.moveToFirst()){
			while(!c.isAfterLast()){
				CourseMaterialsData cmd = new CourseMaterialsData();
				cmd.setCm_id(c.getInt(c.getColumnIndex(COLUMN_ACCESSCM_ID)));
				cmd.setTitle(c.getString(c.getColumnIndex(COLUMN_ACCESSCM_TITLE)));
				cmd.setDescription(c.getString(c.getColumnIndex(COLUMN_ACCESSCM_DESCRIPTION)));
				cmd.setTags(c.getString(c.getColumnIndex(COLUMN_ACCESSCM_TAGS)));
				cmd.setUploader(c.getString(c.getColumnIndex(COLUMN_ACCESSCM_UPLOADER)));
				alCMList.add(cmd);
				c.moveToNext();
			}
		}
		c.close();
		myDB.close();
		return alCMList;
	}
	
	public ArrayList<CourseMaterialsData> searchAccessCM(String keyword){
		SQLiteDatabase myDB = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + DATABASE_TABLE_ACCESSCM + " WHERE " + COLUMN_ACCESSCM_TAGS + " LIKE \'%" + keyword + "%\'";
		Cursor c = myDB.rawQuery(strQuery, null);
		ArrayList<CourseMaterialsData> alCMList = new ArrayList<CourseMaterialsData>();
		if(c.moveToFirst()){
			while(!c.isAfterLast()){
				CourseMaterialsData cmd = new CourseMaterialsData();
				cmd.setCm_id(c.getInt(c.getColumnIndex(COLUMN_ACCESSCM_ID)));
				cmd.setTitle(c.getString(c.getColumnIndex(COLUMN_ACCESSCM_TITLE)));
				cmd.setDescription(c.getString(c.getColumnIndex(COLUMN_ACCESSCM_DESCRIPTION)));
				cmd.setTags(c.getString(c.getColumnIndex(COLUMN_ACCESSCM_TAGS)));
				cmd.setUploader(c.getString(c.getColumnIndex(COLUMN_ACCESSCM_UPLOADER)));
				alCMList.add(cmd);
				c.moveToNext();
			}
		}
		c.close();
		myDB.close();
		return alCMList;
	}
	/* ACCESSCM FUNCTIONS END */
	
	/* ACCESSLP FUNCTIONS START */
	//Adds new lesson plans to the access database when upload.
	public void createNewAccessLP(LessonPlansData newLPD){
		SQLiteDatabase myDB = this.getWritableDatabase();
		ContentValues myCV = new ContentValues();
		myCV.put(COLUMN_ACCESSLP_TITLE, newLPD.getTitle());
		myCV.put(COLUMN_ACCESSLP_DESCRIPTION, newLPD.getDescription());
		myCV.put(COLUMN_ACCESSLP_TAGS,newLPD.getTags());
		myCV.put(COLUMN_ACCESSLP_UPLOADER, newLPD.getUploader());
		myDB.insert(DATABASE_TABLE_ACCESSLP, null, myCV);
		myDB.close();
		
	}
	//Retrieves all lesson plans in the access database.
	public ArrayList<LessonPlansData> getAccessLPList(){
		SQLiteDatabase myDB = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + DATABASE_TABLE_ACCESSLP;
		Cursor c = myDB.rawQuery(strQuery, null);
		ArrayList<LessonPlansData> alLPList = new ArrayList<LessonPlansData>();
		if(c.moveToFirst()){
			while(!c.isAfterLast()){
				LessonPlansData lpd = new LessonPlansData();
				lpd.setLp_id(c.getInt(c.getColumnIndex(COLUMN_ACCESSLP_ID)));
				lpd.setTitle(c.getString(c.getColumnIndex(COLUMN_ACCESSLP_TITLE)));
				lpd.setDescription(c.getString(c.getColumnIndex(COLUMN_ACCESSLP_DESCRIPTION)));
				lpd.setTags(c.getString(c.getColumnIndex(COLUMN_ACCESSLP_TAGS)));
				lpd.setUploader(c.getString(c.getColumnIndex(COLUMN_ACCESSLP_UPLOADER)));
				alLPList.add(lpd);
				c.moveToNext();
			}
		}
		c.close();
		myDB.close();
		return alLPList;
	}
	
	public ArrayList<LessonPlansData> searchAccessLP(String keyword){
		SQLiteDatabase myDB = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + DATABASE_TABLE_ACCESSLP + " WHERE " + COLUMN_ACCESSCM_TAGS + " LIKE \'%" + keyword + "%\'";
		Cursor c = myDB.rawQuery(strQuery, null);
		ArrayList<LessonPlansData> alLPList = new ArrayList<LessonPlansData>();
		if(c.moveToFirst()){
			while(!c.isAfterLast()){
				LessonPlansData lpd = new LessonPlansData();
				lpd.setLp_id(c.getInt(c.getColumnIndex(COLUMN_ACCESSLP_ID)));
				lpd.setTitle(c.getString(c.getColumnIndex(COLUMN_ACCESSLP_TITLE)));
				lpd.setDescription(c.getString(c.getColumnIndex(COLUMN_ACCESSLP_DESCRIPTION)));
				lpd.setTags(c.getString(c.getColumnIndex(COLUMN_ACCESSLP_TAGS)));
				lpd.setUploader(c.getString(c.getColumnIndex(COLUMN_ACCESSLP_UPLOADER)));
				alLPList.add(lpd);
				c.moveToNext();
			}
		}
		c.close();
		myDB.close();
		return alLPList;
	}
	/* ACCESSLP FNCTIONS END */
	
	/* COMMENTSCM FUNCTIONS START */
	//Create new comment instance
	public void createNewCommentCM(CommentData newCD){
		SQLiteDatabase myDB = this.getWritableDatabase();
		ContentValues myCV = new ContentValues();
		myCV.put(COLUMN_COMMENTSCM_MATERIALS, newCD.getMaterials_id());
		myCV.put(COLUMN_COMMENTSCM_COMMENTATOR, newCD.getCommentator());
		myCV.put(COLUMN_COMMENTSCM_COMMENT, newCD.getComment());
		myDB.insert(DATABASE_TABLE_COMMENTSCM,null,myCV);
		myDB.close();
	}
	
	//Get comments of the course materials
	public ArrayList<CommentData> getCMCommentList(CourseMaterialsData newCMD){
		SQLiteDatabase myDB = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + DATABASE_TABLE_COMMENTSCM + " WHERE " + COLUMN_COMMENTSCM_MATERIALS + " = " + newCMD.getCm_id();
		Cursor c = myDB.rawQuery(strQuery, null);
		ArrayList<CommentData> alCommentList = new ArrayList<CommentData>();
		if(c.moveToFirst()){
			while(!c.isAfterLast()){
				CommentData cd = new CommentData();
				cd.setId(c.getInt(c.getColumnIndex(COLUMN_COMMENTSCM_ID)));
				cd.setMaterials_id(c.getInt(c.getColumnIndex(COLUMN_COMMENTSCM_MATERIALS)));
				cd.setCommentator(c.getString(c.getColumnIndex(COLUMN_COMMENTSCM_COMMENTATOR)));
				cd.setComment(c.getString(c.getColumnIndex(COLUMN_COMMENTSCM_COMMENT)));
				alCommentList.add(cd);
				c.moveToNext();
			}
		}
		c.close();
		myDB.close();
		return alCommentList;
	}
	/* COMMENTSCM FUNCTIONS END */
	
	/* COMMENTSLP FUNCTIONS START */
	//Create new comment instance
	public void createNewCommentLP(CommentData newCD){
		SQLiteDatabase myDB = this.getWritableDatabase();
		ContentValues myCV = new ContentValues();
		myCV.put(COLUMN_COMMENTSLP_MATERIALS, newCD.getMaterials_id());
		myCV.put(COLUMN_COMMENTSLP_COMMENTATOR, newCD.getCommentator());
		myCV.put(COLUMN_COMMENTSLP_COMMENT, newCD.getComment());
		myDB.insert(DATABASE_TABLE_COMMENTSLP,null,myCV);
		myDB.close();
	}
		
	//Get comments of the course materials
	public ArrayList<CommentData> getLPCommentList(LessonPlansData newLPD){
		SQLiteDatabase myDB = this.getReadableDatabase();
		String strQuery = "SELECT * FROM " + DATABASE_TABLE_COMMENTSLP + " WHERE " + COLUMN_COMMENTSLP_MATERIALS + " = " + newLPD.getLp_id();
		Cursor c = myDB.rawQuery(strQuery, null);
		ArrayList<CommentData> alCommentList = new ArrayList<CommentData>();
		if(c.moveToFirst()){
			while(!c.isAfterLast()){
				CommentData cd = new CommentData();
				cd.setId(c.getInt(c.getColumnIndex(COLUMN_COMMENTSLP_ID)));
				cd.setMaterials_id(c.getInt(c.getColumnIndex(COLUMN_COMMENTSLP_MATERIALS)));
				cd.setCommentator(c.getString(c.getColumnIndex(COLUMN_COMMENTSLP_COMMENTATOR)));
				cd.setComment(c.getString(c.getColumnIndex(COLUMN_COMMENTSLP_COMMENT)));
				alCommentList.add(cd);
				c.moveToNext();
			}
		}
		c.close();
		myDB.close();
		return alCommentList;
	}
	/* COMMENTSLP FUNCTIONS END */
}
