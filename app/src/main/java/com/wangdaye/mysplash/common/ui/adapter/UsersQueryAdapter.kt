package com.wangdaye.mysplash.common.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.wangdaye.mysplash.R
import com.wangdaye.mysplash.common._basic.FooterAdapter
import com.wangdaye.mysplash.common._basic.activity.MysplashActivity
import com.wangdaye.mysplash.common.data.entity.unsplash.ResultsItem
import com.wangdaye.mysplash.common.ui.widget.CircleImageView
import com.wangdaye.mysplash.common.utils.DisplayUtils
import com.wangdaye.mysplash.common.utils.widget.glide.CircleTransformation

/**
 * Created by hoanghiep on 11/29/17.
 */
class UsersQueryAdapter : FooterAdapter<RecyclerView.ViewHolder>() {
    val listUsers: MutableList<ResultsItem> = ArrayList()
    private var context: Context? = null
    init {
        isHasHeader = false
    }
    fun setActivity(activity: MysplashActivity) {
        this.context = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): RecyclerView.ViewHolder? {
        when (viewType) {
            CONTENT_TYPE -> {
                return UsersViewHolder(LayoutInflater.from(parent?.context).inflate(R.layout.item_users_query, parent, false))
            }
            else -> {
                return FooterHolder.buildInstance(parent)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        if (holder is UsersViewHolder && position < listUsers.size) {
            holder.titleUser.text = listUsers.get(position).name
            holder.nickName.text = "@" + listUsers.get(position).username
            holder.imgUser.let {
                Glide.with(it.context)
                        .load(listUsers.get(position).profileImage?.large)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .override(128, 128)
                        .transform(CircleTransformation(it.context))
                        .into(it)
            }

        }
    }

    override fun hasFooter(): Boolean {
        return !DisplayUtils.isLandscape(context) && DisplayUtils.getNavigationBarHeight(context?.getResources()) != 0
    }

    override fun getRealItemCount(): Int {
        return listUsers.size
    }

    fun clearItem() {
        listUsers.clear()
        notifyDataSetChanged()
    }

    fun insertItem(users: ResultsItem) {
        listUsers.add(users)
        notifyItemInserted(listUsers.size - 1)
    }

    fun setUserData(list: List<ResultsItem>) {
        listUsers.clear()
        listUsers.addAll(list)
        notifyDataSetChanged()
    }

    class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgUser: CircleImageView = itemView.findViewById(R.id.item_users_avatar)
        val titleUser: TextView = itemView.findViewById(R.id.item_user_title)
        val nickName: TextView = itemView.findViewById(R.id.item_user_nick)
    }
}