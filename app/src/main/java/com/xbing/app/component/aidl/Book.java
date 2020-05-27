package com.xbing.app.component.aidl;

import android.os.Parcel;
import android.os.Parcelable;

public class Book implements Parcelable {

    public String bookName;

    public Book(String name) {
        bookName = name;
    }

    protected Book(Parcel in) {
        bookName = in.readString();
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public static final Creator<Book> CREATOR = new Creator<Book>() {

        @Override
        public Book createFromParcel(Parcel in) {
            return new Book(in);
        }

        @Override
        public Book[] newArray(int size) {
            return new Book[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(bookName);
    }

    public void readFromParcel(Parcel desc){
        bookName = desc.readString();
    }

    @Override
    public String toString(){
        return "bookName = " + bookName;
    }
}
