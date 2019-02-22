package padev.badhabits.application.ui.activity

import android.os.Bundle
import android.support.v7.widget.Toolbar
import padev.badhabits.R
import padev.badhabits.core.view.BaseActivity

class HabitAddActivity: BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_habit_add)

        val toolbar = findViewById<Toolbar>(R.id.activity_habit_add_appbar_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = getString(R.string.habit_add)
    }
}