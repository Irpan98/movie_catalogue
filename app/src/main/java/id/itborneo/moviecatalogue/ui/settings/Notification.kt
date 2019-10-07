package id.itborneo.moviecatalogue.ui.settings

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.MutableLiveData
import id.itborneo.moviecatalogue.R
import id.itborneo.moviecatalogue.data.network.ApiClient
import id.itborneo.moviecatalogue.data.response.MoviesResponse
import id.itborneo.moviecatalogue.ui.model.MovieTv
import id.itborneo.moviecatalogue.utils.mapper.getMovieMapper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*
import id.itborneo.moviecatalogue.utils.NotificationID
class Notification : BroadcastReceiver(){
    
    companion object {
        val TYPE_DAILY_REMINDER = "daily_reminder"
        val TYPE_RELEASE_REMINDER = "release_reminder"
        val EXTRA_TYPE = "extra_reminder"
        val EXTRA_MESSAGE = "extra_message"
        val EXTRA_ID = "extra_id"
    }
    
    lateinit var alarmManager : AlarmManager
    lateinit var pendingIntent: PendingIntent
    
    
    val ID_DAILY_REMINDER = 100
    val ID_RELEASE_REMINDER = 101
    
    
    var listItems = ArrayList<MovieTv>()
//    private val movies = MutableLiveData<ArrayList<MovieTv>>()
    
    @SuppressLint("SimpleDateFormat")
    fun getReleaseMovie(mContext: Context, title: String): MutableList<MovieTv>{
        val calender = Calendar.getInstance()
    
    
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val dateToday = sdf.format(calender.time)
    
        Log.d("SettingActivityD", dateToday)
        
        
        ApiClient.create().movieReleaseToday(dateToday,dateToday).enqueue(object : Callback<MoviesResponse> {
            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Toast.makeText(mContext, "No Internet",Toast.LENGTH_SHORT).show()
            }
        
            override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                val movie = response.body() as MoviesResponse
    
                val movieResult = movie.results
                listItems = getMovieMapper(movieResult) as ArrayList<MovieTv>
                
                listItems.forEach {
                    showNotification(mContext , title, "${it.titleMovieTv } is Release Today", it.idMovieTv)
    
                }
                Log.d("Notification_Broadcast", "movie result $listItems")
       
            }
            
            
        
        })
        showToast(mContext, title, "There is Movie Release Today")
    
        return listItems
    }
    
    override fun onReceive(context: Context?, intent: Intent?) {
        val type =intent?.getStringExtra(EXTRA_TYPE)
        var message =intent?.getStringExtra(EXTRA_MESSAGE)

        
        if(type !=null ){
            val title = "Catalogue Movie"
    
            val notifId: Int
            if(type == TYPE_DAILY_REMINDER){
                notifId = ID_DAILY_REMINDER
                showNotification(context as Context, title, message as String, notifId)
                showToast(context, title, message)
            } else{
                notifId =ID_RELEASE_REMINDER
                Log.d("Notification_onRecive","Reminder called")
    
                getReleaseMovie(context as Context,title ).forEach {
//                    Log.d("Notification_onRecive",it.titleMovieTv)
//
//                    val messageNotif = "${it.titleMovieTv} is Release Today"
//
//                    showNotification(context , title, messageNotif, notifId)
                }
//
//                listItems.forEach {
//
//                }

//                val id = intent.getIntExtra(EXTRA_ID, 0)
//
//                Log.d("NotificationLog",id.toString())
//                id
            }
    

    
        }
    
    }
    
    private fun showToast(context: Context, title: String, message: String){
        Toast.makeText(context,"$title : $message", Toast.LENGTH_LONG).show()
    }
    
    private fun showNotification(context: Context, title: String, message: String, notifId: Int) {
        val CHANNEL_ID ="channel_1"
        val CHANNEL_NAME = "channelNotif"
        
        val notification =context.getSystemService(
            Context.NOTIFICATION_SERVICE) as NotificationManager
        
        val builder = NotificationCompat.Builder(context , CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
        
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            val channel  = NotificationChannel(CHANNEL_ID,CHANNEL_NAME,
                NotificationManager.IMPORTANCE_LOW)
            
            channel.enableVibration(true)
            builder.setChannelId(CHANNEL_ID)
            
            notification.createNotificationChannel(channel)
        }
        
        val notificationActive =builder.build()
        
        notification.notify(notifId,notificationActive)
        
        
    }
    
    
    
    fun dailyReminder(context: Context, type: String, time: String, message: String){
        
        
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        
        val intent =Intent(context, this::class.java)
        intent.putExtra(EXTRA_TYPE,type)
        intent.putExtra(EXTRA_MESSAGE, message)
        
        
    
        val timeArray = time.split(":")
    
        val calender = Calendar.getInstance()
        calender.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calender.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calender.set(Calendar.SECOND, 0)
        
        pendingIntent = PendingIntent.getBroadcast(context,ID_DAILY_REMINDER, intent, 0)
        
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calender.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent)
        
        Toast.makeText(context, "Daily Reminder is Active in $time",Toast.LENGTH_LONG).show()
    }
    
    
    
    fun releaseReminder(context: Context){
        val type = TYPE_RELEASE_REMINDER
        val time = "8:00"
//        var message = "Release Today"
        
        val id = NotificationID.id
//        listItems.forEach {
//             message = "${it.titleMovieTv} is release today"
//
//        }
        
        alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        
        val intent =Intent(context, this::class.java)
        intent.putExtra(EXTRA_TYPE,type)
//        intent.putExtra(EXTRA_MESSAGE, message)
        intent.putExtra(EXTRA_ID,id)
        
        val timeArray = time.split(":")
        
        val calender = Calendar.getInstance()
        calender.set(Calendar.HOUR_OF_DAY, Integer.parseInt(timeArray[0]))
        calender.set(Calendar.MINUTE, Integer.parseInt(timeArray[1]))
        calender.set(Calendar.SECOND, 0)
        
        pendingIntent = PendingIntent.getBroadcast(context,id, intent, 0)
        
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calender.timeInMillis,
            AlarmManager.INTERVAL_DAY, pendingIntent)
        
        Toast.makeText(context, "Release Reminder is Active in $time",Toast.LENGTH_LONG).show()
    }}