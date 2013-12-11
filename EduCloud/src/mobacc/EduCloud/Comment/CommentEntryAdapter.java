package mobacc.EduCloud.Comment;

import java.util.ArrayList;

import mobacc.EduCloud.Main.R;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class CommentEntryAdapter extends BaseAdapter {

private ArrayList<CommentData> alCDList = new ArrayList<CommentData>();
	
	private Context myContext;
	
	public CommentEntryAdapter(Context myContext){
		this.myContext = myContext;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return alCDList.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return alCDList.get(arg0);
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
			view = inflater.inflate(R.layout.comment_layout,parent,false);
			
		}
		
		TextView tvCommentName = (TextView)view.findViewById(R.id.tvCommentName);
		tvCommentName.setText(alCDList.get(position).getCommentator());
		TextView tvCommentContent = (TextView)view.findViewById(R.id.tvCommentContent);
		tvCommentContent.setText(alCDList.get(position).getComment());
		
		return view;
	}
	
	public void addEntry(CommentData newCDData){
		alCDList.add(newCDData);
		notifyDataSetChanged();
	}
	
	public void addMultipleEntries(ArrayList<CommentData> newCDList){
		for(int i = 0;i < newCDList.size();i++){
			alCDList.add(newCDList.get(i));
		}
		notifyDataSetChanged();
	}
	
	public void removeAll(){
		alCDList.clear();
		notifyDataSetChanged();
	}

}
