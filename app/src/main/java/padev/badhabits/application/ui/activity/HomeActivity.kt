package padev.badhabits.application.ui.activity;

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ProgressBar
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.facebook.stetho.Stetho
import com.mikepenz.iconics.typeface.FontAwesome
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.model.DividerDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import padev.badhabits.R
import padev.badhabits.application.mvp.model.entity.HabitEntity
import padev.badhabits.application.mvp.presenter.home.HomePresenter
import padev.badhabits.application.mvp.view.IHomeView
import padev.badhabits.application.ui.fragment.HabitCardFragment
import padev.badhabits.core.view.BaseActivity

class HomeActivity: BaseActivity(), IHomeView {

    private val TAG = HomeActivity::class.java.simpleName

    private val presenter = HomePresenter(this)

    private lateinit var mDrawerResult: Drawer.Result

    @BindView(R.id.activity_home_fab)
    lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

        ButterKnife.bind(this)

        val toolbar = findViewById<Toolbar>(R.id.activity_home_appbar_toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = getString(R.string.app_name)

        mDrawerResult =  Drawer()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withHeader(R.layout.drawer_header)
                .addDrawerItems(
                        PrimaryDrawerItem().withName(R.string.drawer_item_notification).withIcon(FontAwesome.Icon.faw_envelope).withBadge("1").withIdentifier(1).withIdentifier(1),
                        SecondaryDrawerItem().withName(R.string.drawer_item_help).withIcon(FontAwesome.Icon.faw_cog).withIdentifier(2),
                        SecondaryDrawerItem().withName(R.string.drawer_item_about).withIcon(FontAwesome.Icon.faw_user).withIdentifier(3),
                        DividerDrawerItem(),
                        SecondaryDrawerItem().withName(R.string.drawer_item_exit).withIcon(FontAwesome.Icon.faw_power_off).withIdentifier(4)
                ).withOnDrawerItemClickListener { parent, view, position, id, drawerItem ->

                    when(id) {

                        0.toLong() -> startActivity(Intent(this, NotificationListActivity::class.java))

                        1.toLong() -> startActivity(Intent(this, SettingsActivity::class.java))

                        2.toLong() -> startActivity(Intent(this, AboutActivity::class.java))

                        4.toLong() -> {
                            moveTaskToBack(true)
                            android.os.Process.killProcess(android.os.Process.myPid())
                            System.exit(1)
                        }
                    }

                }

                .build()

        // TODO = remove = Debug: for SQLite view
        Stetho.initializeWithDefaults(this)

        presenter.getAllHabits()
    }

    override fun onBackPressed() {

        if (mDrawerResult.isDrawerOpen) {
            mDrawerResult.closeDrawer()
        } else {
            super.onBackPressed()
        }
    }

    override fun showHabit(habitEntity: HabitEntity) {

        val habitCardFragment = HabitCardFragment()

        habitCardFragment.mHabitEntity = habitEntity

        supportFragmentManager.beginTransaction()
                .add(R.id.activity_home_content, habitCardFragment)
                .commit()
    }

    override fun showLoading() {

        findViewById<ProgressBar>(R.id.activity_home_progress_bar)?.visibility = View.VISIBLE
    }

    override fun hideLoading() {

        findViewById<ProgressBar>(R.id.activity_home_progress_bar)?.visibility = View.GONE
    }

    override fun showAddHabitDialog() {

        /*val promptsView = layoutInflater.inflate(R.layout.input_alert_add_habit, null)

        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setView(promptsView)

        val habitNameEditText = promptsView.findViewById<EditText>(R.id.input_alert_add_habit_name)
        val timeCheckBox = promptsView.findViewById<CheckBox>(R.id.input_alert_add_habit_time)
        val moneyCheckBox = promptsView.findViewById<CheckBox>(R.id.input_alert_add_habit_money)
        val healthCheckBox = promptsView.findViewById<CheckBox>(R.id.input_alert_add_habit_health)

        alertDialogBuilder.setCancelable(false)

        alertDialogBuilder.setPositiveButton("Добавить") { _: DialogInterface, _: Int ->

            presenter.habitAddPositive (
                    habitNameEditText.text.toString(),
                    timeCheckBox.isChecked,
                    moneyCheckBox.isChecked,
                    healthCheckBox.isChecked
            )
        }

        alertDialogBuilder.setNegativeButton("Отмена") { dialog: DialogInterface, _: Int ->
            dialog.cancel()
        }

        val alertDialog = alertDialogBuilder.create()

        alertDialog.show()
        */

        startActivity(Intent(this, HabitAddActivity::class.java))
    }


    @OnClick(R.id.activity_home_fab)
    fun addHabitClicked(view: View) {

       presenter.habitAddStart()
    }

    override fun getContext(): Context {

        return this@HomeActivity
    }
}
