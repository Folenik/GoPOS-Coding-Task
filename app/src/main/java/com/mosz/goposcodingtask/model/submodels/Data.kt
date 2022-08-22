package com.mosz.goposcodingtask.model.submodels

import com.google.gson.annotations.SerializedName
import com.mosz.goposcodingtask.utilities.LinksConverter
import com.mosz.goposcodingtask.utilities.MapToStringConverter
import io.objectbox.annotation.*

@Entity
data class Data(
    @Id
    var dbId: Long = 0,

    @Unique(onConflict = ConflictStrategy.REPLACE)
    @SerializedName("id")
    var itemId: Int? = 0,

    @SerializedName("name")
    var itemName: String? = "",

    @SerializedName("category")
    @Convert(converter = MapToStringConverter::class, dbType = String::class)
    val itemCategory: Map<String, String?>?,

    @SerializedName("tax")
    @Convert(converter = MapToStringConverter::class, dbType = String::class)
    val itemTax: Map<String, String?>?,

    @SerializedName("price")
    val itemPriceAmount: MutableMap<String, String>? = mutableMapOf(),

    @SerializedName("image_link")
    @Convert(converter = LinksConverter::class, dbType = String::class)
    val itemImageLink: Link?
) {
    data class Link(
        @SerializedName("small") val smallLink: String?,
    )
}