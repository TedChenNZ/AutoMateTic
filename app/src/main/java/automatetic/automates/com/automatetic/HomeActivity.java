package automatetic.automates.com.automatetic;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;


public class HomeActivity extends Activity {

    long oneDay = 86400000;
    long oneHour = 3600000;
    long oneMinute = 60000;
    long oneSecond = 1000;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
//        updateTimeView();
    }

//    private void updateTimeView() {
//        findViewById(R.drawable.)
//    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
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
            View rootView = inflater.inflate(R.layout.fragment_home, container, false);
            TextView time = (TextView) rootView.findViewById(R.id.timeDisp);

            Calendar c = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("E dd/MMM/yyyy HH:mm");
            time.setText(sdf.format(c.getTime()));

            return rootView;
        }
    }

    private void updateTimeView(){

        Calendar c = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("E dd/MMM/yyyy HH:mm");
        TextView time = (TextView) findViewById(R.id.timeDisp);
        time.setText(sdf.format(c.getTime()));
    }

    private void setTime(long diff){
        ShellInterface.isSuAvailable();

        if (ShellInterface.isSuAvailable()) {
            ShellInterface.runCommand("chmod 666 /dev/alarm");
            Calendar c = Calendar.getInstance();
            SystemClock.setCurrentTimeMillis(c.getTimeInMillis() + diff);
            ShellInterface.runCommand("chmod 664 /dev/alarm");
        }
        updateTimeView();
    }

    public void dayMinus(View v) {
        setTime(oneDay * -1);
        Toast.makeText(this.getBaseContext(),"Day Subtracted", Toast.LENGTH_SHORT).show();
    }

    public void dayPlus(View v) {
        setTime(oneDay);
        Toast.makeText(this.getBaseContext(),"Day Added", Toast.LENGTH_SHORT).show();
    }


    public void hourMinus(View v) {
        setTime(oneHour * -1);
        Toast.makeText(this.getBaseContext(),"Hour Subtracted", Toast.LENGTH_SHORT).show();

    }

    public void hourPlus(View v) {
        setTime(oneHour);
        Toast.makeText(this.getBaseContext(),"Hour Added", Toast.LENGTH_SHORT).show();
    }

    public void deleteDb(View v) {
//        Context context = getApplicationContext();
//        context.deleteDatabase("PatternDB");
//        context.deleteDatabase("RoutineDB");
//        Toast.makeText(this.getBaseContext(),"PatternDB and RoutineDB deleted", Toast.LENGTH_SHORT).show();

        ShellInterface.isSuAvailable();

        if (ShellInterface.isSuAvailable()) {
            ShellInterface.runCommand("chmod 666 /dev/alarm");

            Settings.System.putInt(getContentResolver(),
                    android.provider.Settings.System.AUTO_TIME, 0);
            Settings.System.putInt(getContentResolver(),
                    android.provider.Settings.System.AUTO_TIME, 1);
            ShellInterface.runCommand("chmod 664 /dev/alarm");
        }
    }
}
