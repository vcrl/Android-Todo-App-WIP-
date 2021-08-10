package com.example.todoapp.blueprints

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class Todo (
    var content : String?,
    var filename : String?,
    var checked : Boolean,
    var important : Boolean
        ) : Serializable, Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(content)
        parcel.writeString(filename)
        parcel.writeByte(if (checked) 1 else 0)
        parcel.writeByte(if (important) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Todo> {
        override fun createFromParcel(parcel: Parcel): Todo {
            return Todo(parcel)
        }

        override fun newArray(size: Int): Array<Todo?> {
            return arrayOfNulls(size)
        }
    }
}