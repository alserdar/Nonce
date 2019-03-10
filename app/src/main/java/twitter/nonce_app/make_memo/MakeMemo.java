package twitter.nonce_app.make_memo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;

import twitter.nonce_app.DetailsActivity;
import twitter.nonce_app.Home;
import twitter.nonce_app.R;
import twitter.nonce_app.toast_package.OurToast;

public class MakeMemo extends AppCompatActivity {

    FloatingActionButton pin ;
    TextInputLayout layOne , layTwo , layThree , layFour , layFive , laySix;
    TextInputEditText memTitle , memOne , memTwo , memThree , memFour , memFive , memSix;
    Button saveMems ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_memo);

        pin = (FloatingActionButton)findViewById(R.id.pinMemory);
        layOne = (TextInputLayout)findViewById(R.id.memoOneLayout);
        layTwo = (TextInputLayout)findViewById(R.id.memoTwoLayout);
        layThree = (TextInputLayout)findViewById(R.id.memoThreeLayout);
        layFour = (TextInputLayout)findViewById(R.id.memoFourLayout);
        layFive = (TextInputLayout)findViewById(R.id.memoFiveLayout);
        laySix = (TextInputLayout)findViewById(R.id.memoSixLayout);
        memTitle = (TextInputEditText)findViewById(R.id.memTitle);
        memOne = (TextInputEditText)findViewById(R.id.memoOne);
        memTwo = (TextInputEditText)findViewById(R.id.memoTwo);
        memThree = (TextInputEditText)findViewById(R.id.memoThree);
        memFour = (TextInputEditText)findViewById(R.id.memoFour);
        memFive = (TextInputEditText)findViewById(R.id.memoFive);
        memSix = (TextInputEditText)findViewById(R.id.memoSix);
        saveMems = (Button) findViewById(R.id.saveMemos);

        memTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                memTitle.setText("");

            }
        });

        pin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layOne.setVisibility(View.VISIBLE);

                pin.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        layTwo.setVisibility(View.VISIBLE);

                        pin.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {

                                layThree.setVisibility(View.VISIBLE);

                                pin.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        layFour.setVisibility(View.VISIBLE);

                                        pin.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {

                                                layFive.setVisibility(View.VISIBLE);

                                                pin.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View view) {

                                                        new OurToast().myToast(getBaseContext() , getString(R.string.maxMemos));
                                                    }
                                                });
                                            }
                                        });
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });


        TextWatcher watcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                if (memTitle.getText().toString().equals("") ||
                        memOne.getText().toString().equals(""))
                {

                    saveMems.setClickable(false);
                    saveMems.setBackgroundResource(R.drawable.gone_grey);
                }else
                {
                     saveMems.setClickable(true);
                    saveMems.setBackgroundResource(R.drawable.button_custom);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        };
        memTitle.addTextChangedListener(watcher);
        memOne.addTextChangedListener(watcher);


        saveMems.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                long id = -1 ;
                Intent i = new Intent(getBaseContext() , DetailsActivity.class);
                i.putExtra("id" ,id);
                i.putExtra("alarmName" , getString(R.string.memo));
                i.putExtra("theEvent", "Memo");
                i.putExtra("memTitle" , memTitle.getText().toString());
                i.putExtra("extraInfo" , memTitle.getText().toString());
                i.putExtra("memOne" , memOne.getText().toString());
                i.putExtra("memTwo" , memTwo.getText().toString());
                i.putExtra("memThree" , memThree.getText().toString());
                i.putExtra("memFour" , memFour.getText().toString());
                i.putExtra("memFive" , memFive.getText().toString());
                i.putExtra("memSix" , memSix.getText().toString());
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getBaseContext() , Home.class);
        startActivity(i);
        super.onBackPressed();
    }
}
