package com.rf.marvelapitest.models.character

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.Expose

class CharactersResult() : Parcelable {

    @Expose
    var id: String? = null
    @Expose
    var name: String? = null
    @Expose
    var description: String? = null
    @Expose
    var modified: String? = null
    @Expose
    var resourceURI: String? = null
    @Expose
    lateinit var thumbnail: Thumbnail

    constructor(parcel: Parcel) : this() {
        id = parcel.readString()
        name = parcel.readString()
        description = parcel.readString()
        modified = parcel.readString()
        resourceURI = parcel.readString()
        thumbnail = parcel.readParcelable(Thumbnail::class.java.classLoader)!!
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(modified)
        parcel.writeString(resourceURI)
        parcel.writeParcelable(thumbnail, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CharactersResult> {
        override fun createFromParcel(parcel: Parcel): CharactersResult {
            return CharactersResult(parcel)
        }

        override fun newArray(size: Int): Array<CharactersResult?> {
            return arrayOfNulls(size)
        }
    }

}