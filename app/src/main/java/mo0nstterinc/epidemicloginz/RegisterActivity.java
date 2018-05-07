package mo0nstterinc.epidemicloginz;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

public class RegisterActivity extends AppCompatActivity {
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Reference();;
    }

    public void Reference(){

    }
    public void goLogin (View v){
        Intent Intent = new Intent(this, LoginActivity.class);
        startActivity(Intent);
        finish();
    }

    public void goBack (View v){
        Intent Intent = new Intent(this, StartActivity.class);
        startActivity(Intent);
    }

}
