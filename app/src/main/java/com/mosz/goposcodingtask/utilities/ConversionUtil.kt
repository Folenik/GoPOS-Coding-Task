package com.mosz.goposcodingtask.utilities

import com.mosz.goposcodingtask.model.submodels.Data
import io.objectbox.converter.PropertyConverter

abstract class BaseMapConverter<V>(private val property: String) :
    PropertyConverter<Map<String, V?>, V?> {
    override fun convertToDatabaseValue(entityProperty: Map<String, V?>): V? {
        return entityProperty[property]
    }

    override fun convertToEntityProperty(databaseValue: V?): Map<String, V?> {
        return if (databaseValue != null) {
            mapOf(Pair(property, databaseValue))
        } else {
            mapOf(Pair(property, null))
        }
    }
}

class MapToStringConverter : BaseMapConverter<String>("name")

class LinksConverter : PropertyConverter<Data.Link, String> {
    override fun convertToDatabaseValue(entityProperty: Data.Link?): String? {
        return entityProperty?.smallLink
    }

    override fun convertToEntityProperty(databaseValue: String?): Data.Link? {
        return if (databaseValue != null) {
            Data.Link(databaseValue)
        } else {
            Data.Link(null)
        }
    }
}