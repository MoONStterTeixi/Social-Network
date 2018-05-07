package mo0nstterinc.epidemicloginz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void goLogin (View v){
        Intent Intent = new Intent(this, LoginActivity.class);
        startActivity(Intent);
    }

    public void goRegister (View v){
        Intent Intent = new Intent(this, RegisterActivity.class);
        startActivity(Intent);
    }
}
