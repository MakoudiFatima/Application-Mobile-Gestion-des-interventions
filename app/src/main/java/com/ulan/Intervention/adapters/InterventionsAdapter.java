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

import com.ulan.Intervention.model.Intervention;
import com.ulan.Intervention.R;
import com.ulan.Intervention.utils.AlertDialogsHelper;
import com.ulan.Intervention.utils.DbHelper;

import java.util.ArrayList;
import java.util.Objects;


public class InterventionsAdapter extends ArrayAdapter<Intervention> {

    private Activity mActivity;
    private int mResource;
    private ArrayList<Intervention> interventionList;
    private Intervention intervention;
    private ListView mListView;

    private static class ViewHolder {
        TextView typeV;
        TextView clientV;
        TextView recetteV;
        TextView adresseV;
        TextView statusV;
        TextView dateV;
        TextView timeV;
        CardView cardView;
        ImageView popup;
    }

    public InterventionsAdapter(Activity activity, ListView listView, int resource, ArrayList<Intervention> objects) {
        super(activity, resource, objects);
        mActivity = activity;
        mListView = listView;
        mResource = resource;
        interventionList = objects;
    }

    @NonNull
    @Override
    public View getView(final int position, View convertView, @NonNull ViewGroup parent) {
        String type = Objects.requireNonNull(getItem(position)).getType();
        String client = Objects.requireNonNull(getItem(position)).getClient();
        String number = Objects.requireNonNull(getItem(position)).getNumber();
        String adresse = Objects.requireNonNull(getItem(position)).getAddress();
        String prix = Objects.requireNonNull(getItem(position)).getPrix();
        String recette = Objects.requireNonNull(getItem(position)).getRecette();
        String status = Objects.requireNonNull(getItem(position)).getStatus();
        String date = Objects.requireNonNull(getItem(position)).getDate();
        String time = Objects.requireNonNull(getItem(position)).getTime();
        int color = Objects.requireNonNull(getItem(position)).getColor();

        intervention = new Intervention(type, client,number,adresse,prix,recette,status,time,date,color);
        final ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(mActivity);
            convertView = inflater.inflate(mResource, parent, false);
            holder = new ViewHolder();
            holder.typeV = convertView.findViewById(R.id.typeinterventions);
            holder.clientV = convertView.findViewById(R.id.clients);
            holder.adresseV = convertView.findViewById(R.id.addressinterventions);
            holder.recetteV = convertView.findViewById(R.id.recetteinterventions);
            holder.statusV = convertView.findViewById(R.id.statusinteventions);
            holder.dateV = convertView.findViewById(R.id.dateinterventions);
            holder.timeV = convertView.findViewById(R.id.timeinteventions);
            holder.cardView = convertView.findViewById(R.id.interventions_cardview);
            holder.popup = convertView.findViewById(R.id.popupbtn);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.typeV.setText(intervention.getType());
        holder.clientV.setText(intervention.getClient());

        holder.adresseV.setText(intervention.getAddress());
        holder.recetteV .setText(intervention.getRecette());
        holder.statusV.setText(intervention.getStatus());
        holder.dateV.setText(intervention.getDate());
        holder.timeV.setText(intervention.getTime());
        holder.clientV.setText(intervention.getClient());
        holder.cardView.setCardBackgroundColor(intervention.getColor());
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
                                db.deleteInerventionById(getItem(position));
                                db.updateIntervention(getItem(position));
                                interventionList.remove(position);
                                notifyDataSetChanged();
                                return true;

                            case R.id.edit_popup:
                                final View alertLayout = mActivity.getLayoutInflater().inflate(R.layout.dialog_add_intervention, null);
                                AlertDialogsHelper.getEditInterventionDialog(mActivity, alertLayout, interventionList, mListView, position);
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

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public ArrayList<Intervention> getInterventionList() {
        return interventionList;
    }

    public Intervention getIntervention() {
        return intervention;
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
