package com.example.accountinfosaver.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accountinfosaver.R;
import com.example.accountinfosaver.pojo.Setting;

import java.util.ArrayList;

public class SettingsAdapter extends RecyclerView.Adapter<SettingsAdapter.SettingsViewHolder> {


    private ArrayList<Setting> settings;
    private Context context;


    public interface SettingsOnClickListener{
        void onSettingClick(int position, Context context);
    }

    public SettingsOnClickListener settingsOnClickListener;

    public void setSettingsOnClickListener(SettingsOnClickListener settingsOnClickListener) {
        this.settingsOnClickListener = settingsOnClickListener;
    }

    public SettingsAdapter(Context context) {
        this.context = context;
        settings = new ArrayList();
    }

    public ArrayList<Setting> getSettings() {
        return settings;
    }

    public void setSettings(ArrayList<Setting> settings) {
        this.settings = settings;
    }

    @NonNull
    @Override
    public SettingsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.settings_item, parent, false);
        return new SettingsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SettingsViewHolder holder, int position) {
        Setting setting = settings.get(position);
        holder.icon.setImageResource(setting.getIcon());
        holder.title.setText(setting.getTitle());
    }

    @Override
    public int getItemCount() {
        return settings.size();
    }

    class SettingsViewHolder extends RecyclerView.ViewHolder{

        private ImageView icon;
        private TextView title;

        public SettingsViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.settingsIcon);
            title = itemView.findViewById(R.id.settingsItem);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    settingsOnClickListener.onSettingClick(getAdapterPosition(), context);
                }
            });
        }
    }


}
