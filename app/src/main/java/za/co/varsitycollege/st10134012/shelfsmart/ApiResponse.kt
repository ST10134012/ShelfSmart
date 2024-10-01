package za.co.varsitycollege.st10134012.shelfsmart

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

final data class ApiResponse (
    @SerializedName("books" ) var books : ArrayList<Books> = arrayListOf()

)
data class Books(
    @SerializedName("id") var id: Int? = null,
    @SerializedName("title") var title: String? = null,
    @SerializedName("author") var author: String? = null,
    @SerializedName("genre") var genre: String? = null,
    @SerializedName("publication_year") var publicationYear: Int? = null,
    @SerializedName("isbn") var isbn: String? = null,
    @SerializedName("description") var description: String? = null,
    @SerializedName("image") var image: String? = null
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeString(title)
        parcel.writeString(author)
        parcel.writeString(genre)
        parcel.writeValue(publicationYear)
        parcel.writeString(isbn)
        parcel.writeString(description)
        parcel.writeString(image)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Books> {
        override fun createFromParcel(parcel: Parcel): Books {
            return Books(parcel)
        }

        override fun newArray(size: Int): Array<Books?> {
            return arrayOfNulls(size)
        }
    }
}