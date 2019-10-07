package id.itborneo.moviecatalogue.ui.settings

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import id.itborneo.moviecatalogue.R
import id.itborneo.moviecatalogue.data.network.ApiClient
import id.itborneo.moviecatalogue.data.response.MoviesResponse
import id.itborneo.moviecatalogue.utils.NotificationID
import kotlinx.android.synthetic.main.activity_settings.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class SettingsActivity : AppCompatActivity() {
    
    lateinit var notificationReminder: Notification
    
    private val SHARED_PREF_DAILY = "shared_Pref_daily"
    private val SHARED_PREF_RELEASE = "shared_pref_release"
    private lateinit var sharedPref : SharedPreferences
    
    private var dailyReminder: Boolean = false
    private var releaseReminder: Boolean = false
    
    
    
    //TODO - mau di hapus
    @SuppressLint("SimpleDateFormat")
    private fun movieReleaseToday(){
        val calender = Calendar.getInstance()
        
        
        val sdf = SimpleDateFormat("yyyy-MM-dd")
        val dateToday = sdf.format(calender.time)
        
        Log.d("SettingActivityD", dateToday)
        
        
        ApiClient.create().movieReleaseToday(dateToday,dateToday).enqueue(object : Callback<MoviesResponse>{
            override fun onFailure(call: Call<MoviesResponse>, t: Throwable) {
                Toast.makeText(this@SettingsActivity, "No Internet",Toast.LENGTH_SHORT).show()
            }
    
            override fun onResponse(call: Call<MoviesResponse>, response: Response<MoviesResponse>) {
                val movie = response.body() as MoviesResponse
                val movieResult = movie.results
                Log.d("SettingActivity", "movie result $movieResult")
                var i = 0
                movieResult.forEach {
                    i += 1

                }

            }
    
        })
    }
    

    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
    
        supportActionBar?.title = "Settings"
    
        //simpan switch ketika turn off turn on
         sharedPref = this.getPreferences(Context.MODE_PRIVATE) ?: return
        dailyReminder = sharedPref.getBoolean(SHARED_PREF_DAILY, false)
        releaseReminder = sharedPref.getBoolean(SHARED_PREF_RELEASE, false)
    
        swh_daily.isChecked = dailyReminder
        swh_release.isChecked = releaseReminder
    
        notificationReminder = Notification()
        //swith daily reminder
        swh_daily.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                dailyReminder = true
                notificationReminder.dailyReminder(this,Notification.TYPE_DAILY_REMINDER,
                    "7:00","Catalogue Movie missing you!")
            }else{
                dailyReminder = false
            }
            
        }
        
        ll_set_languange.setOnClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }
        //swith release reminder
        swh_release.setOnCheckedChangeListener { _, isChecked ->
            if(isChecked){
                releaseReminder = true
    
                notificationReminder.releaseReminder(this@SettingsActivity)
            }else{
                
                releaseReminder = false
            }
        }
    

        
    }
    
    override fun onDestroy() {
        with(sharedPref.edit()){
            putBoolean(SHARED_PREF_DAILY,dailyReminder)
            putBoolean(SHARED_PREF_RELEASE,releaseReminder)
            
            apply()
        }
        super.onDestroy()
    }
    

}
