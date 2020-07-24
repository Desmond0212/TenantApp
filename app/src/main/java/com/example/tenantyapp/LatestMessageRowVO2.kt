package com.example.tenantyapp

import android.view.View
import com.example.tenantyapp.model.MessageVO
import com.example.tenantyapp.model.UserVO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.user_row_latest_message.view.*
import java.text.SimpleDateFormat
import java.util.*

class LatestMessageRowVO2 (val chatMessage: MessageVO, val chatPosition: Int): Item<ViewHolder>()
{
    var chatPartnerUser: UserVO? = null

    override fun bind(viewHolder: ViewHolder, position: Int)
    {
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

                if (chatPartnerUser?.username == "A-13-14 Members")
                {
                    viewHolder.itemView.lblUnitNumberMessage.text = chatPartnerUser?.username
                    viewHolder.itemView.lblMessageContent.text = chatMessage.text
                    val formatter = SimpleDateFormat("hh:mm aa", Locale.ENGLISH)
                    val formatted = formatter.format(chatMessage.timestamp)
                    viewHolder.itemView.lblTimeMessage.text = formatted
                    viewHolder.itemView.lblUnitNumberMessage.visibility = View.VISIBLE
                    viewHolder.itemView.lblMessageContent.visibility = View.VISIBLE
                    viewHolder.itemView.lblTimeMessage.visibility = View.VISIBLE
                    viewHolder.itemView.latestMessageCardView.visibility = View.VISIBLE
                }
                else
                {
                    viewHolder.itemView.lblUnitNumberMessage.visibility = View.GONE
                    viewHolder.itemView.lblMessageContent.visibility = View.GONE
                    viewHolder.itemView.lblTimeMessage.visibility = View.GONE
                    viewHolder.itemView.latestMessageCardView.visibility = View.GONE
                }

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