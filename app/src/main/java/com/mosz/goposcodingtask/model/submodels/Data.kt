package com.mosz.goposcodingtask.model.submodels

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class Data(
    val barcodes: List<String>? = null,
    @Transient
    val category: Category? = null,
    val category_id: Int? = null,
    val color: String? = null,
    @Transient
    val custom_fields: List<CustomField>? = null,
    val description: String? = null,
    @Id
    var id: Long = 0,
    @Transient
    val image_link: ImageLinkX? = null,
    val item_group_id: Int? = null,
    val joint_id: String? = null,
    val label: String? = null,
    val name: String? = null,
    @Transient
    val price: Price? = null,
    val reference_id: String? = null,
    val sku: String? = null,
    val status: String? = null,
    @Transient
    val tax: Tax? = null,
    val tax_id: Int? = null,
    @Transient
    val translations: List<TranslationX>? = null,
    val updated_at: String? = null
)