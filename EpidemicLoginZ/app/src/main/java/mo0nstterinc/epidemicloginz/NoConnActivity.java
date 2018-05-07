package mo0nstterinc.epidemicloginz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NoConnActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_conn);
    }

    public void goLoading (View v){
        Intent Intent = new Intent(this, LoadingActivity.class);
        startActivity(Intent);
    }
}
