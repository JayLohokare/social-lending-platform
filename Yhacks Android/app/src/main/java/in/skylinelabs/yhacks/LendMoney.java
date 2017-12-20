package in.skylinelabs.yhacks;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jay Lohokare on 02-Dec-17.
 */

public class LendMoney extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, AsyncTaskComplete {


    private LendAdapter adapter;
    private List<LendDetails> parts = new ArrayList<LendDetails>();

    android.widget.RelativeLayout RelativeLayout;

    private ActionHandler actionHandler;
    TextView wallet_bal;
    String username;

    View.OnClickListener snackaction;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lend);

        final String PREFS_NAME = "Kym_App_Preferences";
        SharedPreferences preferences = getSharedPreferences(PREFS_NAME, 0);
        username = preferences.getString("name","null");


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        snackaction = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        };

        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        actionHandler = new ActionHandler(this, LendMoney.this);

        final ListView listview = (ListView) findViewById(R.id.list);
        listview.setAdapter(adapter);

        loadList();

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                TextView tv  = (TextView)v.findViewById(R.id.name);
                final String name = tv.getText().toString();
                TextView amt = (TextView)v.findViewById(R.id.amount);
                final int amount = Integer.parseInt(amt.getText().toString());

                AlertDialog alertDialog = new AlertDialog.Builder(LendMoney.this).create();
                alertDialog.setTitle("Alert");
                alertDialog.setMessage("Do you approve the payment?");
                alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                actionHandler.giveLoan(username, name, amount);
                                dialog.dismiss();
                            }
                        });
                alertDialog.show();


                //Perform action using name
            }
        });



    }


    public void loadList() {
        actionHandler.getAllLoans(username);

        final ListView listview = (ListView) findViewById(R.id.list);
        listview.setAdapter(adapter);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.home) {
            // Handle the camera action
        } else if (id == R.id.chat_bot) {
            Intent i = new Intent(this, ChatActivity.class);
            startActivity(i);

        } else if (id == R.id.tutorial) {
            Intent i = new Intent(this, App_intro.class);
            startActivity(i);

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        //actionHandler.getAllLoans(username);

    }

    @Override
    protected void onResume() {
        super.onResume();
       // actionHandler.getAllLoans(username);
    }

    @Override
    public void handleResult(JsonObject result, String action) throws JSONException {

        switch (action) {


            case "Loan":
                Log.e("Wooohoooo",result.toString());
                JsonArray jsonArray = result.getAsJsonArray("data");

                try{
                    for(int i=0;i<jsonArray.size();i++) {
                        LendDetails temp = new LendDetails();
                       // Log.e("Errrrrorr", jsonArray.get(i).getAsJsonObject().get("loan_amount").getAsString());
                        temp.set_name(jsonArray.get(i).getAsJsonObject().get("requester_name").getAsString());
                        temp.set_amount(jsonArray.get(i).getAsJsonObject().get("loan_amount").getAsInt());
                        temp.set_rating(jsonArray.get(i).getAsJsonObject().get("requester_credit_score").getAsFloat());

                        parts.add(temp);
                    }
                } catch (Exception e) {
                    Log.d("Error parsing", e.toString());
                }
                adapter = new LendAdapter(getApplicationContext(), R.layout.lend_list_element, parts);
                final ListView listview = (ListView) findViewById(R.id.list);
                listview.setAdapter(adapter);
                break;


            case "Lend":

                int output = result.get("success").getAsInt();
                if (output == 1){
                    Snackbar.make(findViewById(android.R.id.content), "Payment successful", Snackbar.LENGTH_INDEFINITE)
                            .setAction("Ok", snackaction)
                            .setActionTextColor(Color.WHITE)
                            .show();
                }

                break;


        }
    }
}
