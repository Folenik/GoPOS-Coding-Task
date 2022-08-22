package com.mosz.goposcodingtask.item

import android.view.View
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.items.AbstractItem
import com.mosz.goposcodingtask.R
import com.mosz.goposcodingtask.databinding.ItemFoodBinding

class FoodItem(
    val name: String,
    val category: String,
    val price: Double,
    val currency: String,
    val tax: String,
    val link: String?
) : AbstractItem<FoodItem.ViewHolder>() {

    override val type = R.id.item_food

    override val layoutRes = R.layout.item_food

    override fun getViewHolder(v: View) = ViewHolder(v)

    class ViewHolder(view: View) : FastAdapter.ViewHolder<FoodItem>(view) {

        private val binding = ItemFoodBinding.bind(view)

        override fun bindView(item: FoodItem, payloads: List<Any>) {
            binding.apply {
                foodName.text = item.name
                foodTax.text = "${itemView.context.getString(R.string.item_tax)}${item.tax}"
                foodPrice.text =
                    "${itemView.context.getString(R.string.item_price)}${item.price} ${item.currency}"

                when (item.category) {
                    itemView.context.getString(R.string.item_category_food) -> foodCategory.setImageDrawable(
                        ContextCompat.getDrawable(itemView.context, R.drawable.category_food)
                    )
                    itemView.context.getString(R.string.item_category_drink) -> foodCategory.setImageDrawable(
                        ContextCompat.getDrawable(itemView.context, R.drawable.category_drink)
                    )
                    itemView.context.getString(R.string.item_category_desserts) -> foodCategory.setImageDrawable(
                        ContextCompat.getDrawable(itemView.context, R.drawable.category_dessert)
                    )
                }

                if (!item.link.isNullOrEmpty()) {
                    Glide.with(itemView.context)
                        .load(item.link)
                        .placeholder(R.drawable.icon_food)
                        .error(R.drawable.icon_food)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .override(200, 200)
                        .centerCrop()
                        .into(foodImage)
                }
            }
        }

        override fun unbindView(item: FoodItem) {
            binding.apply {
                foodName.text = null
                foodTax.text = null
                foodPrice.text = null
                foodImage.setImageDrawable(null)
                Glide.with(itemView.context).clear(foodImage)
            }
        }
    }
}