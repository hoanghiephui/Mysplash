package com.wallpapers.unsplash.common.ui.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.wallpapers.unsplash.R
import com.wallpapers.unsplash.UnsplashApplication
import com.wallpapers.unsplash.common.basic.FooterAdapter
import com.wallpapers.unsplash.common.basic.activity.BaseActivity
import com.wallpapers.unsplash.common.data.entity.unsplash.User
import com.wallpapers.unsplash.common.ui.widget.CircleImageView
import com.wallpapers.unsplash.common.utils.DisplayUtils
import com.wallpapers.unsplash.common.utils.helper.IntentHelper
import com.wallpapers.unsplash.common.utils.widget.glide.CircleTransformation
import com.wallpapers.unsplash.user.view.activity.UserActivity

/**
 * Created by hoanghiep on 11/29/17.
 */
class UsersQueryAdapter(private val callBack: CallBack) : FooterAdapter<RecyclerView.ViewHolder>() {
    val listUsers: MutableList<User> = ArrayList()
    private var context: Context? = null

    init {
        isHasHeader = false
    }

    fun setActivity(activity: BaseActivity) {
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
                        .load(listUsers.get(position).profile_image?.large)
                        .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                        .override(128, 128)
                        .transform(CircleTransformation(it.context))
                        .into(it)
            }
            holder.itemView?.setOnClickListener({
                clickAvatar(holder.imgUser, listUsers.get(position))
            })
            callBack.onHeader()
        }
        if (hasFooter() && holder is FooterHolder) {
            callBack.onFooter()
        }
    }

    internal fun clickAvatar(avatar: CircleImageView, user: User) {
        IntentHelper.startUserActivity(
                UnsplashApplication.getInstance().topActivity,
                avatar,
                user,
                UserActivity.PAGE_PHOTO)
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

    fun insertItem(users: User) {
        listUsers.add(users)
        notifyItemInserted(listUsers.size - 1)
    }

    fun setUserData(list: List<User>) {
        listUsers.clear()
        listUsers.addAll(list)
        notifyDataSetChanged()
    }

    class UsersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgUser: CircleImageView = itemView.findViewById(R.id.item_users_avatar)
        val titleUser: TextView = itemView.findViewById(R.id.item_user_title)
        val nickName: TextView = itemView.findViewById(R.id.item_user_nick)
    }

    interface CallBack {
        fun onHeader()
        fun onFooter()
    }
}