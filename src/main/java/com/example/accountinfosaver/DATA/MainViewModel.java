package com.example.accountinfosaver.DATA;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.accountinfosaver.pojo.Account;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MainViewModel extends AndroidViewModel {

    private static AccountDB accountDB;
    private LiveData<List<Account>> accountsList;

    public MainViewModel(@NonNull Application application) {
        super(application);
        accountDB = AccountDB.getInstance(getApplication());
        accountsList = accountDB.accountDao().getAllAccounts();
    }

    public LiveData<List<Account>> getAccounts() {
        return accountsList;
    }

    public void insertAccount(Account account){
        new InsertAsyncTask().execute(account);
    }

    public Account getAccountById(int id){
        Account account = null;
        try {
            account =  new GetAsyncTask().execute(id).get();
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return account;
    }

    public void deleteAccount(Account account){
        new DeleteAsyncTask().execute(account);
    }

    public void deleteAllAccounts(){
        new DeleteAllAsyncTask().execute();
    }

    private static class InsertAsyncTask extends AsyncTask<Account, Void, Void>{

        @Override
        protected Void doInBackground(Account... accounts) {
            if(accounts != null && accounts.length > 0){
                accountDB.accountDao().insertAccount(accounts[0]);
            }
            return null;
        }
    }

    private static class GetAsyncTask extends AsyncTask<Integer, Void, Account>{

        private Account account;
        @Override
        protected Account doInBackground(Integer... integers) {
            if(integers != null && integers.length > 0){
                account = accountDB.accountDao().getAccountById(integers[0]);
            }
            return account;
        }
    }

    private static class DeleteAsyncTask extends AsyncTask<Account, Void, Void>{

        @Override
        protected Void doInBackground(Account... accounts) {
            if(accounts != null && accounts.length > 0){
                accountDB.accountDao().deleteAccount(accounts[0]);
            }
            return null;
        }
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void, Void, Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            accountDB.accountDao().deleteAllAccount();
            return null;
        }
    }
}
