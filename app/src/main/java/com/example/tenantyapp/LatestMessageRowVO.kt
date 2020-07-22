package com.example.tenantyapp

import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import com.example.tenantyapp.model.MessageVO
import com.example.tenantyapp.model.UserVO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.user_row_latest_message.view.*

class LatestMessageRowVO (val chatMessage: MessageVO): Item<ViewHolder>()
{
    var chatPartnerUser: UserVO? = null

    override fun bind(viewHolder: ViewHolder, position: Int)
    {
        viewHolder.itemView.lblMessageContent.text = chatMessage.text

        val chatPartnerId: String
        if (chatMessage.fromId == FirebaseAuth.getInstance().uid)
        {
            chatPartnerId = chatMessage.toId
        }
        else
        {
            chatPartnerId = chatMessage.fromId
        }

        val ref = FirebaseDatabase.getInstance().getReference("/users/$chatPartnerId")

        ref.addListenerForSingleValueEvent(object: ValueEventListener
        {
            override fun onDataChange(p0: DataSnapshot)
            {
                chatPartnerUser = p0.getValue(UserVO::class.java)
                viewHolder.itemView.lblUnitNumberMessage.text = chatPartnerUser?.username

                /*val targetImageView = viewHolder.itemView.img_profile_image_latest_message
                Picasso.get().load(chatPartnerUser?.profileimageUrl).into(targetImageView)*/
            }

            override fun onCancelled(p0: DatabaseError) {}
        })
    }

    override fun getLayout(): Int
    {
        return R.layout.user_row_latest_message
    }
}