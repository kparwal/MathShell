package com.example.keshav.firstapp;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


public class AdderActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String parseThis = intent.getStringExtra(MainActivity.EQUATION_MESSAGE);
        String [] nums = parseThis.split("[\\+\\*/-]");
        Double [] ints = new Double[nums.length];
        for (int i = 0; i < nums.length; i++) {
            ints[i] = Double.parseDouble(nums[i]);
        }
        Double answer;
        int op = intent.getIntExtra(MainActivity.OPERATION, 1);
        switch (op) {
            case 0:
                answer = new Double(ints[0] + ints[1]);
                break;
            case 1:
                answer = new Double(ints[0] - ints[1]);
                break;
            case 2:
                answer = new Double(1.0 * ints[0] * ints[1]);
                break;
            case 3:
                answer = new Double(1.0 * ints[0] / ints[1]);
                break;
            default:
                answer = new Double(0);
        }
        TextView t = new TextView(this);
        t.setTextSize(40);
        t.setText(answer.toString());
        setContentView(t);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_adder, menu);
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
}
