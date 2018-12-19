package padev.badhabits.application.ui.activity

import android.os.Bundle
import android.support.v7.widget.Toolbar
import padev.badhabits.R
import padev.badhabits.core.view.BaseActivity

class NotificationListActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_notification_list)

        val toolbar = findViewById<Toolbar>(R.id.activity_notification_list_appbar_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = getString(R.string.notifications)
    }
}