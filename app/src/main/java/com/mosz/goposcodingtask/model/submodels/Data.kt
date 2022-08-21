package com.mosz.goposcodingtask.model.submodels

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Data(
    @Transient
    val category: Category? = null,
    val category_id: Int? = null,
    @Id
    var id: Long? = 0,
    @Transient
    val image_link: ImageLink? = null,
    val item_group_id: Int? = null,
    val joint_id: String? = null,
    val name: String? = null,
    @Transient
    val price: Price? = null,
    val status: String? = null,
    @Transient
    val tax: Tax? = null,
    val tax_id: Int? = null,
    val updated_at: String? = null
)