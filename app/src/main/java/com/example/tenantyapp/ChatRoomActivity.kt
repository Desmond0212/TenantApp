package com.example.tenantyapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Window
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tenantyapp.model.MessageVO
import com.example.tenantyapp.model.UserVO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.Item
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.activity_chat_room.*
import kotlinx.android.synthetic.main.message_row_receiver.view.*
import kotlinx.android.synthetic.main.message_row_sender.view.*
import java.text.SimpleDateFormat
import java.util.*

class ChatRoomActivity : AppCompatActivity()
{
    companion object
    {
        const val TAG = "ChatRoomActivity"
    }

    val adapter = GroupAdapter<ViewHolder>()
    var user : UserVO? = null


    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat_room)

        val window: Window = this.window
        // clear FLAG_TRANSLUCENT_STATUS flag:
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        // add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        // finally change the color
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorBackground)

        recyclerView_chat_room.adapter = adapter
        recyclerView_chat_room.layoutManager = LinearLayoutManager(this)

        user = intent.getParcelableExtra<UserVO>(NewMessageActivity.USER_KEY)
        lblUnitNumberChatRoom.text = user?.username

        messagesList()

        btn_send_message_chat_room.setOnClickListener {
            if (txt_enter_message_chat_room.text.toString() != "")
            {
                Log.d(TAG, "Sending message....")
                performSendMessage()
            }
        }

        imgButtonBackChatRoom.setOnClickListener {
            this.onBackPressed()
        }
    }

    private fun messagesList()
    {
        val fromId = FirebaseAuth.getInstance().uid
        val toId = user?.uid
        val ref = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId")

        ref.addChildEventListener(object: ChildEventListener
        {
            override fun onChildAdded(p0: DataSnapshot, p1: String?)
            {
                val messageFromChat = p0.getValue(MessageVO::class.java)

                if (messageFromChat != null)
                {
                    Log.d(TAG, messageFromChat.text)
                    Log.d("Desmond Debug: ", messageFromChat.text)

                    if (messageFromChat.fromId == FirebaseAuth.getInstance().uid)
                    {
                        val getCurrentUser = MessageFragment.currentUser ?: return
                        adapter.add(ChatItemSender(messageFromChat.text, getCurrentUser))
                    }
                    else
                    {
                        adapter.add(ChatItemReceiver(messageFromChat.text, user!!))
                    }
                }

                recyclerView_chat_room.scrollToPosition(adapter.itemCount -1)
            }

            override fun onCancelled(p0: DatabaseError) {}

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {}

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {}

            override fun onChildRemoved(p0: DataSnapshot) {}
        })
    }

    private fun performSendMessage()
    {
        //val ref = FirebaseDatabase.getInstance().getReference("/messages").push()

        val text = txt_enter_message_chat_room.text.toString()
        val user = intent.getParcelableExtra<UserVO>(NewMessageActivity.USER_KEY)
        val fromId = FirebaseAuth.getInstance().uid
        val toId = user.uid
        val timestamp = System.currentTimeMillis()

        Log.d("Desmond Debug:", toId)
        val formatter = SimpleDateFormat("hh:mm aa", Locale.ENGLISH)
        val formatted = formatter.format(timestamp)
        Log.d("Desmond Debug TimeStamp: ", formatted)

        if (fromId == null) return

        val refFromSender = FirebaseDatabase.getInstance().getReference("/user-messages/$fromId/$toId").push()
        val refFromReceiver = FirebaseDatabase.getInstance().getReference("/user-messages/$toId/$fromId").push()

        val message = MessageVO(refFromSender.key!!, text, fromId, toId, timestamp)

        refFromSender.setValue(message)
            .addOnSuccessListener{
                Log.d(TAG, "Successfully saved message to Firebase from Sender: ${refFromSender.key}")
                txt_enter_message_chat_room.text.clear()
                recyclerView_chat_room.scrollToPosition(adapter.itemCount -1)
            }
            .addOnFailureListener{
                Log.d(TAG, "Failed to save message to Firebase from Sender: ${refFromSender.key}")
            }

        refFromReceiver.setValue(message)
            .addOnSuccessListener{
                Log.d(TAG, "Successfully saved message to Firebase from Receiver: ${refFromReceiver.key}")
            }
            .addOnFailureListener{
                Log.d(TAG, "Failed to save message to Firebase from Receiver: ${refFromReceiver.key}")
            }

        val latestMessageRefFromId = FirebaseDatabase.getInstance().getReference("/latest-messages/$fromId/$toId")
        latestMessageRefFromId.setValue(message)

        val latestMessageRefToId = FirebaseDatabase.getInstance().getReference("/latest-messages/$toId/$fromId")
        latestMessageRefToId.setValue(message)
    }
}

class ChatItemSender(val text: String, val userProfile: UserVO): Item<ViewHolder>()
{
    override fun bind(viewHolder: ViewHolder, position: Int)
    {
        viewHolder.itemView.txt_message_content_message_row_sender.text = text

        //Retrieving Receiver Profile Image into the Chat
        val url = userProfile.profileimageUrl
        /*val senderProfileImage = viewHolder.itemView.img_message_row_sender
        Picasso.get().load(url).into(senderProfileImage)*/
    }

    override fun getLayout(): Int
    {
        return R.layout.message_row_sender
    }
}

class ChatItemReceiver(val text: String, val userProfile: UserVO): Item<ViewHolder>()
{
    override fun bind(viewHolder: ViewHolder, position: Int)
    {
        viewHolder.itemView.txt_message_content_message_row_receiver.text = text

        //Retrieving User Profile Image into the Chat
        val url = userProfile.profileimageUrl
        /*val senderProfileImage = viewHolder.itemView.img_message_row_receiver
        Picasso.get().load(url).into(senderProfileImage)*/
    }

    override fun getLayout(): Int
    {
        return R.layout.message_row_receiver
    }
}