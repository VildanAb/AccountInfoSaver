package com.example.accountinfosaver.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.accountinfosaver.R;
import com.example.accountinfosaver.pojo.Account;
import com.squareup.picasso.Picasso;


import java.util.ArrayList;

public class AccountAdapter extends RecyclerView.Adapter<AccountAdapter.AccountViewHolder> {

    ArrayList<Account> accounts;
    private Context context;
    public AccountAdapter(Context context) {
        accounts = new ArrayList<>();
        this.context = context;
    }

    public interface AccountOnClickListener{
        void onAccountClick(int position);
    }

    public AccountOnClickListener accountOnClickListener;

    public AccountOnClickListener getAccountOnClickListener() {
        return accountOnClickListener;
    }

    public void setAccountOnClickListener(AccountOnClickListener accountOnClickListener) {
        this.accountOnClickListener = accountOnClickListener;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AccountViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.account_item, parent, false);
        return new AccountViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AccountViewHolder holder, int position) {
        Account account = accounts.get(position);
        holder.login.setText(account.getLogin());

        if (account.getImageResource() != null) {
            Uri uri = Uri.parse(Uri.decode(account.getImageResource()));
//            Glide.with(context).load(uri).into(holder.icon);
            Picasso.get().load(uri).into(holder.icon);
            int color = holder.login.getResources().getColor(R.color.colorGoogle);
            holder.constraintLayout.setBackgroundColor(color);
        } else {
            if (account.getAccountType().equals("Steam")) {
                holder.icon.setImageResource(R.drawable.steam);
                int color = holder.login.getResources().getColor(R.color.colorGoogle);
                holder.constraintLayout.setBackgroundColor(color);
            } else if (account.getAccountType().equals("Google")) {
                holder.icon.setImageResource(R.drawable.google);
                int color = holder.login.getResources().getColor(R.color.colorGoogle);
                holder.constraintLayout.setBackgroundColor(color);
            } else if (account.getAccountType().equals("VK")) {
                holder.icon.setImageResource(R.drawable.vk);
                int color = holder.login.getResources().getColor(R.color.colorVK);
                holder.constraintLayout.setBackgroundColor(color);
            } else if (account.getAccountType().equals("Instagram")) {
                holder.icon.setImageResource(R.drawable.instagram);
                int color = holder.login.getResources().getColor(R.color.colorGoogle);
                holder.constraintLayout.setBackgroundColor(color);
            } else if (account.getAccountType().equals("Telegram")) {
                holder.icon.setImageResource(R.drawable.telegram);
                int color = holder.login.getResources().getColor(R.color.colorGoogle);
                holder.constraintLayout.setBackgroundColor(color);
            } else if (account.getAccountType().equals("Epic Games Store")) {
                holder.icon.setImageResource(R.drawable.epic);
                int color = holder.login.getResources().getColor(R.color.colorGoogle);
                holder.constraintLayout.setBackgroundColor(color);
            } else if (account.getAccountType().equals("Yandex")) {
                holder.icon.setImageResource(R.drawable.yandex);
                int color = holder.login.getResources().getColor(R.color.colorGoogle);
                holder.constraintLayout.setBackgroundColor(color);
            } else if (account.getAccountType().equals("Facebook")) {
                holder.icon.setImageResource(R.drawable.facebook);
                int color = holder.login.getResources().getColor(R.color.colorGoogle);
                holder.constraintLayout.setBackgroundColor(color);
            } else if (account.getAccountType().equals("PornHub")) {
                holder.icon.setImageResource(R.drawable.ph);
                int color = holder.login.getResources().getColor(R.color.colorGoogle);
                holder.constraintLayout.setBackgroundColor(color);
            } else if (account.getAccountType().equals("Другое")) {
                holder.icon.setImageResource(R.drawable.other);
                int color = holder.login.getResources().getColor(R.color.colorGoogle);
                holder.constraintLayout.setBackgroundColor(color);
            }
        }

    }



    @Override
    public int getItemCount() {
        return accounts.size();
    }

    class AccountViewHolder extends RecyclerView.ViewHolder{

        private TextView login;
        private ImageView icon;
        private ConstraintLayout constraintLayout;

        public AccountViewHolder(@NonNull View itemView) {
            super(itemView);
            login = itemView.findViewById(R.id.loginTextView);
            icon = itemView.findViewById(R.id.imageViewAccountType);
            constraintLayout = itemView.findViewById(R.id.constraint);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    accountOnClickListener.onAccountClick(getAdapterPosition());
                }
            });
        }
    }
}
