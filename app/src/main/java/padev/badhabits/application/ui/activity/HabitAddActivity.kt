package padev.badhabits.application.ui.activity

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.Toolbar
import android.text.Layout
import android.view.View
import android.widget.*
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

        val unnecessaryThingsRadioButton = findViewById<RadioButton>(R.id.activity_habit_add_radio_button_unnecessary_things)

        unnecessaryThingsRadioButton.setOnCheckedChangeListener { buttonView, isChecked ->
            val unnecessaryThingsLongTextView = findViewById<TextView>(R.id.activity_habit_add_text_view_unnecessary_things_long)
            if (isChecked) unnecessaryThingsLongTextView.visibility = View.VISIBLE
            else unnecessaryThingsLongTextView.visibility = View.GONE
        }

        val nailBitingRadioButton = findViewById<RadioButton>(R.id.activity_habit_add_radio_button_nail_biting)

        nailBitingRadioButton.setOnCheckedChangeListener { buttonView, isChecked ->
            val nailBitingLongTextView = findViewById<TextView>(R.id.activity_habit_add_text_view_nail_biting_long)
            if (isChecked) nailBitingLongTextView.visibility = View.VISIBLE
            else nailBitingLongTextView.visibility = View.GONE
        }

        val otherRadioButton = findViewById<RadioButton>(R.id.activity_habit_add_radio_button_other)

        otherRadioButton.setOnCheckedChangeListener { buttonView, isChecked ->
            val otherLayout = findViewById<LinearLayout>(R.id.activity_habit_add_layout_other)
            if (isChecked) otherLayout.visibility = View.VISIBLE
            else otherLayout.visibility = View.GONE
        }

        val floatingButton= findViewById<FloatingActionButton>(R.id.activity_habit_add_fab)

        floatingButton.setOnClickListener {
            val radioGroup = findViewById <RadioGroup> (R.id.activity_habit_add_radio_group)
            val radioButton = findViewById <RadioButton> (radioGroup.checkedRadioButtonId)

            var name = radioButton.text.toString()
            var time = false
            var money = false
            var health = false

            if (name == getString(R.string.habit_add_other)) {

                val textView = findViewById<TextView>(R.id.activity_add_habit_name)
                name = textView.text.toString()
                val timeCheckBox = findViewById<CheckBox>(R.id.activity_add_habit_time)
                val moneyCheckBox = findViewById<CheckBox>(R.id.activity_add_habit_money)
                val healthCheckBox = findViewById<CheckBox>(R.id.activity_add_habit_health)

                time = timeCheckBox.isChecked
                money = moneyCheckBox.isChecked
                health = healthCheckBox.isChecked
            }

            else if (name == getString(R.string.habit_add_smoke)) {
                time = true
                money = true
                health = true
            }
            else if (name == getString(R.string.habit_add_alcoholism)) {
                time = true
                money = true
                health = true
            }
            else if (name == getString(R.string.habit_add_junk_food)) {
                time = false
                money = true
                health = true
            }
            else if (name == getString(R.string.habit_add_gambling_addiction)) {
                time = true
                money = true
                health = true
            }
            else if (name == getString(R.string.habit_add_foul_language)) {
                time = true
                money = false
                health = false
            }
            else if (name == getString(R.string.habit_add_shoping)) {
                time = true
                money = true
                health = false
            }
            else if (name == getString(R.string.habit_add_laziness)) {
                time = true
                money = false
                health = true
            }
            else if (name == getString(R.string.habit_add_lie)) {
                time = true
                money = false
                health = false
            }
            else if (name == getString(R.string.habit_add_unnecessary_things)) {
                time = true
                money = true
                health = true
            }
            else if (name == getString(R.string.habit_add_nail_biting)) {
                time = false
                money = false
                health = true
            }

        }

    }
}