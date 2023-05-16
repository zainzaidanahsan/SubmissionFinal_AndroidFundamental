package com.zain.submissionfinal_androidfundamental.room.database

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity
@Parcelize
data class Favorite(
    @PrimaryKey
    var id: Int = 0,
    @ColumnInfo
    var avatarUrl : String? = null,
    @ColumnInfo
    var username: String? = null,
): Parcelable
