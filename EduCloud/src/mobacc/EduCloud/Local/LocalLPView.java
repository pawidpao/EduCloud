package mobacc.EduCloud.Local;

import java.io.File;
import java.util.ArrayList;

import mobacc.EduCloud.DAO.LessonPlansData;
import mobacc.EduCloud.DAO.LocalDatabaseObject;
import mobacc.EduCloud.Main.R;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ListView;

public class LocalLPView extends Fragment{
	
	private static final String LOCAL_INTERNAL_DIRECTORY = "sdcard/Download/EduCloud/Lesson Plans/";
	
	//private DAOLocalLessonPlans myDBLessonPlans;
	private LocalDatabaseObject myLocalDAO;
	private LocalLPEntryAdapter myLLPEA;
	private ListView lv;
	private GridView gv;
	
	public LocalLPView(){
		createLPDirectory();
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view;
		if(isTablet(getActivity().getApplicationContext())){//Device is tablet
			view = inflater.inflate(R.layout.local_lp_fragment_tablet_view,container,false);
			myLLPEA = new LocalLPEntryAdapter(getActivity().getApplicationContext());
			gv = (GridView)view.findViewById(R.id.gvLocalLP);
			gv.setAdapter(myLLPEA);
		}
		else{
			view = inflater.inflate(R.layout.local_lp_fragment_view,container,false);
			myLLPEA = new LocalLPEntryAdapter(getActivity().getApplicationContext());
			lv = (ListView)view.findViewById(R.id.lvLocalLP);
			lv.setAdapter(myLLPEA);
		}
		myLocalDAO = new LocalDatabaseObject(getActivity().getApplicationContext());
		
		this.getLocalFiles();
		
		return view;
		
	}
	
	private void createLPDirectory(){
		File directory = new File(LOCAL_INTERNAL_DIRECTORY);
		directory.mkdirs();
	}
	
	private void getLocalFiles(){
		//ArrayList<LessonPlansData> alLPDList = myDBLessonPlans.getLocalLPList();
		ArrayList<LessonPlansData> alLPDList = myLocalDAO.getLocalLPList();
		if(alLPDList != null){
			myLLPEA.addMultipleEntries(alLPDList);
		}
	}
	
	public void getSearchedLocalFiles(String keyword){
		ArrayList<LessonPlansData> alLPDList = myLocalDAO.searchLocalLP(keyword);
		if(alLPDList != null){
			myLLPEA.addMultipleEntries(alLPDList);
		}
	}
	
	public boolean isTablet(Context context) {
	    boolean xlarge = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == 4);
	    boolean large = ((context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK) == Configuration.SCREENLAYOUT_SIZE_LARGE);
	    return (xlarge || large);
	}
	
}