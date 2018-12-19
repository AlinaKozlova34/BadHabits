package padev.badhabits.application.ui.activity;

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.EditText
import butterknife.BindView
import butterknife.OnClick
import com.arellomobile.mvp.presenter.InjectPresenter
import com.facebook.stetho.Stetho
import padev.badhabits.Data.CRUD.HabitCRUD
import padev.badhabits.Data.Habit
import padev.badhabits.R
import padev.badhabits.application.mvp.presenter.home.HomePresenter
import padev.badhabits.application.mvp.view.IHomeView
import padev.badhabits.application.ui.fragment.HabitCardFragment
import padev.badhabits.core.view.BaseActivity
import com.mikepenz.iconics.typeface.FontAwesome
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem
import com.mikepenz.materialdrawer.Drawer
import com.mikepenz.materialdrawer.model.DividerDrawerItem


class HomeActivity: BaseActivity(), IHomeView {

    @InjectPresenter
    lateinit var  mHomePresenter: HomePresenter

    lateinit var mDrawerResult: Drawer.Result

    private val TAG = HomeActivity::class.java.simpleName

    private lateinit var habitCRUD: HabitCRUD

    @BindView(R.id.activity_home_fab)
    lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_home)

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

        // for SQLite view
        Stetho.initializeWithDefaults(this)


        //TODO presenter.getHabits

        habitCRUD = HabitCRUD(this)

        val habits =  habitCRUD.selectAllData()

        for (data in habits) {

            val habit = data as Habit
            createHabitCard(habit)
        }
    }

    override fun onBackPressed() {

        if (mDrawerResult.isDrawerOpen) {
            mDrawerResult.closeDrawer()
        } else {
            super.onBackPressed()
        }
    }

    override fun showAddHabitDialog() {

        val promptsView = layoutInflater.inflate(R.layout.activity_main_input_alert, null)

        val alertDialogBuilder = AlertDialog.Builder(this)

        alertDialogBuilder.setView(promptsView)

        val habitName = promptsView.findViewById<EditText>(R.id.habit_name_input_alert_habit_name_edit_text)

        alertDialogBuilder.setCancelable(false)

        alertDialogBuilder.setPositiveButton("Добавить") { _: DialogInterface, _: Int ->

            val hNameStr = habitName.text.toString()

            val habit = Habit(hNameStr)

            // TODO presenter.addHabit(...)

            habitCRUD.insertData(habit)

            createHabitCard(habit)
        }

        alertDialogBuilder.setNegativeButton("Отмена") { dialog: DialogInterface, _: Int ->
            dialog.cancel()
        }

        val alertDialog = alertDialogBuilder.create()

        alertDialog.show()
    }


    @SuppressLint("InflateParams")
    @OnClick(R.id.activity_home_fab)
    fun addHabitClicked(view: View) {
        // TODO:
        // mHomePresenter.habitAddStart()

       showAddHabitDialog()

    }

    private fun createHabitCard(habit: Habit) {

        val habitCardFragment = HabitCardFragment()

        habitCardFragment.mHabit = habit

        supportFragmentManager.beginTransaction()
                .add(R.id.activity_home_content, habitCardFragment)
                .commit()
    }
}
