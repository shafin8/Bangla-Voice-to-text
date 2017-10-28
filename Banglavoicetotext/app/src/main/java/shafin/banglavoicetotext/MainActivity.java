package shafin.banglavoicetotext;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {



    TextView output;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        output=(TextView)findViewById(R.id.textView);
        output.setText("");
        imageView=(ImageView)findViewById(R.id.imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnToOpenMic();
            }
        });


    }

    private void btnToOpenMic(){

        Intent intent =new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
       // intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,DEFAULT_KEYS_SEARCH_LOCAL );
      //  intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");



        String languagePref = "bn";//or, whatever iso code...
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, languagePref);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, languagePref);
        intent.putExtra(RecognizerIntent.EXTRA_ONLY_RETURN_LANGUAGE_PREFERENCE, languagePref);

        intent.putExtra("android.speech.extra.EXTRA_ADDITIONAL_LANGUAGES", new String[]{"en"});



        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Speak to search");

        try {
            startActivityForResult(intent,143);
        }
        catch (ActivityNotFoundException e){
            Toast.makeText(getApplicationContext(),
                    "Sorry! Your device doesn\\'t support speech input",
                    Toast.LENGTH_SHORT).show();

        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch ( requestCode){
            case 143:{
                if (resultCode == RESULT_OK && null != data){

                    ArrayList<String> voiceInText=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    // ArrayList<String> v=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //  String d = voiceInText.toString();

                    String text =voiceInText.get(0);
                    output.setText(text);

                }
                break;
            }
        }




    }

}
