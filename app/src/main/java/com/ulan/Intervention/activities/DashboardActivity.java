package com.ulan.Intervention.activities;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.ulan.Intervention.R;
import com.ulan.Intervention.adapters.FragmentsTabAdapter;
import com.ulan.Intervention.fragments.QuotidienneFragment;
import com.ulan.Intervention.fragments.AnnuelFragment;
import com.ulan.Intervention.fragments.HebdomadaireFragment;
import com.ulan.Intervention.fragments.MensuelFragment;
import com.ulan.Intervention.utils.AlertDialogsHelper;
import com.ulan.Intervention.utils.DailyReceiver;

import java.util.Calendar;


public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentsTabAdapter adapter;
    private ViewPager viewPager;
    private boolean switchSevenDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        initAll();
    }

    private void initAll() {
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
        Toolbar toolbar = findViewById(R.id.toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        setupFragments();

        setupSevenDaysPref();



        setDailyAlarm();
    }

    private void setupFragments() {
        adapter = new FragmentsTabAdapter(getSupportFragmentManager());
        viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabLayout);
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_WEEK);
        adapter.addFragment(new QuotidienneFragment(), getResources().getString(R.string.quotidiennes));
        adapter.addFragment(new HebdomadaireFragment(), getResources().getString(R.string.hebdomadaires));
        adapter.addFragment(new MensuelFragment(), getResources().getString(R.string.mensuelles));
        adapter.addFragment(new AnnuelFragment(), getResources().getString(R.string.annuelles));

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(day == 1 ? 6 : day-2, true);
        tabLayout.setupWithViewPager(viewPager);
    }


    


    private void setupSevenDaysPref() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        switchSevenDays = sharedPref.getBoolean(SettingsActivity.KEY_SEVEN_DAYS_SETTING, false);
    }

    private void setDailyAlarm() {
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        Calendar cur = Calendar.getInstance();

        if (cur.after(calendar)) {
            calendar.add(Calendar.DATE, 1);
        }

        Intent myIntent = new Intent(this, DailyReceiver.class);
        int ALARM1_ID = 10000;
        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, ALARM1_ID, myIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        AlarmManager alarmManager = (AlarmManager) this.getSystemService(ALARM_SERVICE);
        if (alarmManager != null) {
            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        }

    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settings = new Intent(DashboardActivity.this, SettingsActivity.class);
                startActivity(settings);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        final NavigationView navigationView = findViewById(R.id.nav_view);
        switch (item.getItemId()) {

            case R.id.interventions:
                Intent intervention  = new Intent(DashboardActivity.this, InterventionsActivity.class);
                startActivity(intervention);
                return true;
            case R.id.jobs:
                Intent job = new Intent(DashboardActivity.this, JobsActivity.class);
                startActivity(job);
                return true;
            case R.id.notes:
                Intent note = new Intent(DashboardActivity.this, NotesActivity.class);
                startActivity(note);
                return true;
            case R.id.settings:
                Intent settings = new Intent(DashboardActivity.this, SettingsActivity.class);
                startActivity(settings);
                return true;
            case R.id.interventionsFait:
                Intent Faits = new Intent(DashboardActivity.this, InterventionsFaitActivity.class);
                startActivity(Faits);
                return true;

            case R.id.interventionsAnnuler:
                Intent Annulers = new Intent(DashboardActivity.this, InterventionsAnnuleActivity.class);
                startActivity(Annulers);
                return true;

            case R.id.interventionsreporter:
                Intent Repoters = new Intent(DashboardActivity.this, InterventionsReporteActivity.class);
                startActivity(Repoters);
                return true;

            case R.id.logout:
                Intent logout = new Intent(DashboardActivity.this, MainActivity.class);
                startActivity(logout);
                return true;

            default:
                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
        }
    }
}
