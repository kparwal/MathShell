package com.example.keshav.firstapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {
    public final static String EXTRA_MESSAGE = "com.mycompany.myfirstapp.MESSAGE";
    public final static String EQUATION_MESSAGE = "com.mycompany.myfirstapp.EQUATION";
    public final static String OPERATION = "com.mycompany.myfirstapp.OPERATION";
    public ArrayAdapter<String> adapter;
    public ArrayList<String> expressions;
    public MathEval m;
    public ListView l;
    public int op;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        m = new MathEval();
        expressions = new ArrayList<>();
        l = (ListView) findViewById(R.id.myList);
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, expressions);
        l.setAdapter(adapter);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();

        }
        l.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                EditText e = (EditText) findViewById(R.id.eqn);
                e.setText(((String) l.getItemAtPosition(position)).split("\\s")[0]);
            }
        });
    }

    public void eval(View view) {
        try {
            EditText t = (EditText) findViewById(R.id.eqn);
            String query = t.getText().toString();
            //expressions.add(query);
            double ans = 0;
            query = query.replaceAll("\\s+","");
            //Log.e("QUERY" ,query);
            String[] split = query.split("=");
            if (split.length == 2) {
                String var = split[0];
                String exp = split[1];
                ans = m.evaluate(exp);
                m.setVariable(var, ans);
            }
            else {
                ans = m.evaluate(query);
            }
        /*TextView n = new TextView(this);
        n.setText(query);
        l.addFooterView(n);*/
            t.setText("");
            adapter.add(query + " = " + ans);
            scrollListViewToBottom();
        } catch (NumberFormatException e) {
            showAlert("Error", "Error in expression");
        } catch (ArithmeticException e) {
            showAlert("Error", "Error in expression");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    private void showAlert(String title, String data) {
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(data);
        alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.cancel();
            }
        });
        alertDialog.show();
    }

    private void scrollListViewToBottom() {
        final ListView myListView = (ListView) findViewById(R.id.myList);
        myListView.post(new Runnable() {
            @Override
            public void run() {
                // Select the last row so it will scroll into view...
                myListView.setSelection(myListView.getAdapter().getCount() - 1);
            }
        });
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            return rootView;
        }
    }
}
