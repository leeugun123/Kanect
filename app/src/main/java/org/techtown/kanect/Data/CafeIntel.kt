package org.techtown.kanect.Data

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class CafeIntel(

    val cafeImg : String,
    //카페 로고 이미지
    //내 카페 찜하기 확인
    val cafeName : String,
    //카페 이름
    val seat : String,
    //카페 전체 좌석
    val allHours : Boolean,
    //24시간 유무
    val opExist : Boolean,
    //현재 영업 우무
    val cur_seat : Int,
    //현재 카페 좌석
    val myCafe : Boolean
    //내 카페 확인 유무

) : Parcelable {
    constructor(parcel: Parcel) : this(

        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte(),
        parcel.readInt(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(cafeImg)
        parcel.writeString(cafeName)
        parcel.writeString(seat)
        parcel.writeByte(if (allHours) 1 else 0)
        parcel.writeByte(if (opExist) 1 else 0)
        parcel.writeInt(cur_seat)
        parcel.writeByte(if (myCafe) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CafeIntel> {
        override fun createFromParcel(parcel: Parcel): CafeIntel {
            return CafeIntel(parcel)
        }

        override fun newArray(size: Int): Array<CafeIntel?> {
            return arrayOfNulls(size)
        }
    }
}

