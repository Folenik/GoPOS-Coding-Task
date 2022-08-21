package com.mosz.goposcodingtask.model.submodels

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

//@Entity
data class Data(
    //@Transient
    val category: Category,
    val category_id: Int,
    //@Id
    val id: Int,
    //@Transient
    val image_link: ImageLink?,
    val item_group_id: Int,
    val joint_id: String,
    val name: String,
    //@Transient
    val price: Price,
    val status: String,
    //@Transient
    val tax: Tax,
    val tax_id: Int,
    val updated_at: String
)