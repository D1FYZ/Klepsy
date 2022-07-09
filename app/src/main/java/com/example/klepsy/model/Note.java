package com.example.klepsy.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Objects;

@Entity
public class Note implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int uid;

    @ColumnInfo(name = "mainText")
    public String mainText;

    @ColumnInfo(name = "descriptionText")
    public String descriptionText;

    @ColumnInfo(name = "timeStamp")
    public long timeStamp;

    @ColumnInfo(name = "condNote")
    public boolean condNote;

    public Note() {
    }

    protected Note(Parcel in) {
        uid = in.readInt();
        mainText = in.readString();
        descriptionText = in.readString();
        timeStamp = in.readLong();
        condNote = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(uid);
        dest.writeString(mainText);
        dest.writeString(descriptionText);
        dest.writeLong(timeStamp);
        dest.writeByte((byte) (condNote ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return uid == note.uid && timeStamp == note.timeStamp && condNote == note.condNote && Objects.equals(mainText, note.mainText) && Objects.equals(descriptionText, note.descriptionText);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uid, mainText, descriptionText, timeStamp, condNote);
    }
}
