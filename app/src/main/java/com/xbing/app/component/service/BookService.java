package com.xbing.app.component.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.xbing.app.component.aidl.Book;
import com.xbing.app.component.aidl.BookManager;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.Nullable;

public class BookService extends Service {

    private final String TAG = BookService.class.getSimpleName();
    private List<Book> bookList;

    @Override
    public void onCreate() {
        Log.d(TAG, "onCreate()");
        super.onCreate();
        bookList = new ArrayList<>();
        for(int i = 0; i < 5; i++){
            bookList.add(new Book("第" + i + "本书"));
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind()");
        return bookManager;
    }

    @Override
    public void unbindService(ServiceConnection connection){
        Log.d(TAG, "unbindService()");
        super.unbindService(connection);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy()");
        super.onDestroy();
    }

    private BookManager.Stub bookManager = new BookManager.Stub() {
        @Override
        public void basicTypes(int anInt, long aLong, boolean aBoolean, float aFloat, double aDouble, String aString) throws RemoteException {

        }

        @Override
        public List<Book> getBookList() throws RemoteException {
            Log.d(TAG, "getBookList size:" + bookList.size());
            return bookList;
        }

        @Override
        public void addBook(Book book) throws RemoteException {
            Log.d(TAG, "addBook（）");
            if(book != null){
                bookList.add(book);
                Log.d(TAG, book.toString());
            }
        }
    };
}
