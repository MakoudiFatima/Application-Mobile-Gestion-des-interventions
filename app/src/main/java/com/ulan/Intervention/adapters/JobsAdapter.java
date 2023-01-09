package com.ulan.Intervention.adapters;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.PopupMenu;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.ulan.Intervention.model.Job;
import com.ulan.Intervention.R;
import com.ulan.Intervention.utils.AlertDialogsHelper;
import com.ulan.Intervention.utils.DbHelper;

import java.util.ArrayList;
import java.util.Objects;

public class JobsAdapter extends ArrayAdapter<Job> {

    private Activity mActivity;
    private int mResource;
    private ArrayList<Job> joblist;
    private Job job;
    private ListView mListView;

    private static class ViewHolder {
        TextView nameV;
        TextView postV;
        TextView phonenumberV;
        TextView emailV;
        CardView cardView;
        ImageView popup;
    }

    public JobsAdapter(Activity activity, ListView listView, int resource, ArrayList<Job> objects) {
        super(activity, resource, objects);
        mActivity = activity;
        mListView = listView;
        mResource = resource;
        joblist = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        String name = Objects.requireNonNull(getItem(position)).getName();
        String post = Objects.requireNonNull(getItem(position)).getPost();
        String phonenumber = Objects.requireNonNull(getItem(position)).getPhonenumber();
        String email = Objects.requireNonNull(getItem(position)).getEmail();
        int color = Objects.requireNonNull(getItem(position)).getColor();

       job = new Job(name, post, phonenumber, email, color);
        final ViewHolder holder;

        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(mActivity);
            convertView = inflater.inflate(mResource, parent, false);
            holder= new ViewHolder();
            holder.nameV = convertView.findViewById(R.id.nameEmp);
            holder.postV = convertView.findViewById(R.id.postEmp);
            holder.phonenumberV = convertView.findViewById(R.id.numberEmp);
            holder.emailV = convertView.findViewById(R.id.emailEmp);
            holder.cardView = convertView.findViewById(R.id.job_cardview);
            holder.popup = convertView.findViewById(R.id.popupbtn);
            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.nameV.setText(job.getName());
        holder.postV.setText(job.getPost());
        holder.phonenumberV.setText(job.getPhonenumber());
        holder.emailV.setText(job.getEmail());
        holder.cardView.setCardBackgroundColor(job.getColor());
        holder.popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popup = new PopupMenu(mActivity, holder.popup);
                final DbHelper db = new DbHelper(mActivity);
                popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.delete_popup:
                                db.deleteJobById(getItem(position));
                                db.updateJob(getItem(position));
                                joblist.remove(position);
                                notifyDataSetChanged();
                                return true;

                            case R.id.edit_popup:
                                final View alertLayout = mActivity.getLayoutInflater().inflate(R.layout.dialog_add_job, null);
                                AlertDialogsHelper.getEditJobDialog(mActivity, alertLayout, joblist, mListView, position);
                                notifyDataSetChanged();
                                return true;
                            default:
                                return onMenuItemClick(item);
                        }
                    }
                });
                popup.show();
            }
        });

        hidePopUpMenu(holder);

        return convertView;
    }

    public ArrayList<Job> getTeacherList() {
        return joblist;
    }

    public Job getTeacher() {
        return job;
    }

     private void hidePopUpMenu(ViewHolder holder) {
        SparseBooleanArray checkedItems = mListView.getCheckedItemPositions();
        if (checkedItems.size() > 0) {
            for (int i = 0; i < checkedItems.size(); i++) {
                int key = checkedItems.keyAt(i);
                if (checkedItems.get(key)) {
                    holder.popup.setVisibility(View.INVISIBLE);
                    }
            }
        } else {
            holder.popup.setVisibility(View.VISIBLE);
        }
    }
}