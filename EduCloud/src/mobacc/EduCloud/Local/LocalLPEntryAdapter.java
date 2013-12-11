package mobacc.EduCloud.Local;

import java.io.File;
import java.util.ArrayList;

import mobacc.EduCloud.DAO.LessonPlansData;
import mobacc.EduCloud.Main.R;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LocalLPEntryAdapter extends BaseAdapter{
	
	private ArrayList<LessonPlansData> alLPList = new ArrayList<LessonPlansData>();
	
	private Context myContext;
	
	public LocalLPEntryAdapter(Context myContext){
		this.myContext = myContext;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return alLPList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return alLPList.get(arg0);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view = convertView;
		if(view == null){
			LayoutInflater inflater = (LayoutInflater)myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.lp_file_choice_layout,parent,false);
			
		}
		
		view.setTag(position);
		view.setOnClickListener(new LocalLessonPlansEntryClickListener());
		TextView tvTitle = (TextView)view.findViewById(R.id.tvTitle);
		tvTitle.setText(alLPList.get(position).getTitle());
		TextView tvTags = (TextView)view.findViewById(R.id.tvTags);
		tvTags.setText(alLPList.get(position).getTags());
		
		return view;
	}
	
	public void addEntry(LessonPlansData newCMData){
		alLPList.add(newCMData);
		notifyDataSetChanged();
	}
	
	public void addMultipleEntries(ArrayList<LessonPlansData> newLPList){
		alLPList = newLPList;
		notifyDataSetChanged();
	}
	
	public void removeAll(){
		alLPList.clear();
		notifyDataSetChanged();
	}
	
	public class LocalLessonPlansEntryClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int position = Integer.parseInt(String.valueOf(v.getTag()));
			File targetFile = new File(alLPList.get(position).getPath());
		    Uri targetUri = Uri.fromFile(targetFile);
		      
		    Intent intent;
		    intent = new Intent(Intent.ACTION_VIEW).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		    intent.setDataAndType(targetUri, "application/pdf");
		      
		    myContext.startActivity(intent);
			
		}
		
	}
	
}
