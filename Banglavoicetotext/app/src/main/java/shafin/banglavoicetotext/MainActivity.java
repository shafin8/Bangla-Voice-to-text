package shafin.banglavoicetotext;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity{



    TextView output;
    ImageView imageView;
    TextToSpeech textToSpeech;
    String texts;



    private EditText txtText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtText = (EditText) findViewById(R.id.txtText);
        output=(TextView)findViewById(R.id.textView);
        output.setText("");
        imageView=(ImageView)findViewById(R.id.imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnToOpenMic(txtText.getText().toString());
            }
        });




    }

    private void btnToOpenMic(String lan){

        Intent intent =new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        //intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
       // intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,DEFAULT_KEYS_SEARCH_LOCAL );
      //  intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");

if (lan==null){
    lan="en";
}

        String languagePref = lan;   //or, whatever iso code...  hi=hindi , bn= bengali, ur= urdu
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
                    //  String d = voiceInsText.toString();

                     texts =voiceInText.get(0);
                    output.setText(texts);

                   speak(texts);


                }
                break;
            }
        }




    }

    public void speak(final String string){
        textToSpeech=new TextToSpeech(MainActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i==TextToSpeech.SUCCESS){
                      textToSpeech.speak(string,TextToSpeech.QUEUE_FLUSH,null);
                }
                else Toast.makeText(getApplicationContext(),"Feature not supported by your device",Toast.LENGTH_LONG).show();

            }
        });
    }



}
