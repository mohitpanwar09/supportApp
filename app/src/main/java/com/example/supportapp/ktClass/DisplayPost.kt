package com.example.supportapp.ktClass

import com.example.supportapp.R
import com.squareup.picasso.Picasso
import com.xwray.groupie.GroupieViewHolder
import com.xwray.groupie.Item
import kotlinx.android.synthetic.main.post_pojo.view.*


class DisplayPost(val postD:PostDetailStr):Item<GroupieViewHolder>() {
    override fun bind(viewHolder: GroupieViewHolder, position: Int) {
            viewHolder.itemView.post_pojo_des.text=postD.postDes
            viewHolder.itemView.post_pojo_likeCount.text=postD.likeCount.toString()
        viewHolder.itemView.post_pojo_name.text=postD.namePost
        Picasso.get().load(postD.postImageUrl).into(viewHolder.itemView.post_pojo_image)
    }

    override fun getLayout(): Int {
        return R.layout.post_pojo
    }
}