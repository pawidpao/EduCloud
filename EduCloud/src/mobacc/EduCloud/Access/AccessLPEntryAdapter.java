package mobacc.EduCloud.Access;

import java.util.ArrayList;

import mobacc.EduCloud.Comment.CommentView;
import mobacc.EduCloud.DAO.LessonPlansData;
import mobacc.EduCloud.Main.R;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AccessLPEntryAdapter extends BaseAdapter{
	
	private Context myContext;
	private ArrayList<LessonPlansData> alLPList = new ArrayList<LessonPlansData>();
	
	public AccessLPEntryAdapter(Context context){
		myContext = context;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return alLPList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return alLPList.get(position);
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
		TextView tvTitle = (TextView)view.findViewById(R.id.tvTitle);
		tvTitle.setText(alLPList.get(position).getTitle());
		TextView tvTags = (TextView)view.findViewById(R.id.tvTags);
		tvTags.setText(alLPList.get(position).getTags());
		view.setOnClickListener(new AccessLessonPlanChoiceClickListener());
		
		return view;
	}
	
	public void addEntry(LessonPlansData newLPData){
		alLPList.add(newLPData);
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
	
	public class AccessLessonPlanChoiceClickListener implements OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			int position = Integer.parseInt(String.valueOf(v.getTag()));
			LessonPlansData lpd = alLPList.get(position);
			Intent myIntent = new Intent(myContext,CommentView.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			myIntent.putExtra("type","Lesson Plans");
			myIntent.putExtra("data",lpd);
			myContext.startActivity(myIntent);
		}
		
	}

}
