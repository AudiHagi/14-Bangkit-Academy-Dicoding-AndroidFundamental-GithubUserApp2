package com.submission.dicoding.githubuser.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.submission.dicoding.githubuser.R
import com.submission.dicoding.githubuser.alarm.AlarmReceiver
import com.submission.dicoding.githubuser.databinding.ActivityRepeatingNotifBinding

class RepeatingNotifActivity : AppCompatActivity(), View.OnClickListener {
    private var binding: ActivityRepeatingNotifBinding? = null
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val actionbar = supportActionBar
        actionbar!!.title = getString(R.string.menu_notif)
        actionbar.setDisplayHomeAsUpEnabled(true)
        binding = ActivityRepeatingNotifBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        // Listener repeating alarm
        binding?.btnSetRepeatingAlarm?.setOnClickListener(this)
        binding?.btnCancelRepeatingAlarm?.setOnClickListener(this)
        alarmReceiver = AlarmReceiver()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onClick(v: View) {
        val repeatMessage = getString(R.string.alarm_message)
        when (v.id) {
            R.id.btn_set_repeating_alarm -> alarmReceiver.setRepeatingAlarm(this, repeatMessage)
            R.id.btn_cancel_repeating_alarm -> alarmReceiver.cancelAlarm(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}