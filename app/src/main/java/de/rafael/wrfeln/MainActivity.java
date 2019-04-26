package de.rafael.wrfeln;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Scroller;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static Random r = new Random(System.nanoTime());

    public int würfel (int augen) {
        return r.nextInt(augen + 1) & Integer.MAX_VALUE;
    }


    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        Spinner s = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dropdownitems, android.R.layout.simple_spinner_dropdown_item);
        s.setAdapter(adapter);
        Button b = findViewById(R.id.würfeln);
        TextView result_text = findViewById(R.id.result_field);
        result_text.setFocusable(false);

        b.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Spinner s = findViewById(R.id.spinner);
                EditText editText = findViewById(R.id.menge);
                TextView result_text = findViewById(R.id.result_field);
                result_text.setTextColor(Color.BLACK);
                result_text.setText("");
                try {
                    Integer augen = Integer.valueOf(s.getSelectedItem().toString());

                    if (editText.getText().toString() == "") {
                        result_text.setTextColor(Color.RED);
                        result_text.setText("Du musst schon eine Zahl angeben...");
                        return;
                    }

                    Integer menge = Integer.parseInt(editText.getText().toString());
                    r.nextInt();//Well if i do this may he first one will not be 0?
                    if (menge >= 50000) {
                        result_text.setTextColor(Color.RED);
                        result_text.setText("Number too large");
                        return;
                    }

                    int[] ergebnisse = new int[menge];
                    boolean count_together = false;
                    int count = 0;
                    if (augen != 6) {
                        count_together = true;
                    }
                    StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < menge; i++) {
                        int w = würfel(augen);
                        while (w == 0) {
                            w = würfel(augen);
                        }
                        ergebnisse[i] = w;
                        sb.append("Würfel " + (i + 1)+ ": " + ergebnisse[i] + "\n");
                        ergebnisse[i] = w;
                        if (count_together) {
                            count += ergebnisse[i];
                        }
                    }

                    if (augen == 6) {
                        int ende[] = new int[7];
                        for (int i = 0; i < menge; i++) {
                            ende[ergebnisse[i]]++;
                        }
                        ende[0] = ende[5] + ende[6];
                        sb.append("\n1: " + ende[1] + "\n");
                        sb.append("2: " + ende[2] + "\n");
                        sb.append("3: " + ende[3] + "\n");
                        sb.append("4: " + ende[4] + "\n");
                        sb.append("5: " + ende[5] + "\n");
                        sb.append("6: " + ende[6] + "\n");
                        sb.append("Erfolge: " + ende[0] + "\n");
                    } else if (augen == 20) {
                        int erfolg = 0, keiner = 0;
                        for (int i = 0; i < ergebnisse.length; i++) {
                            if (ergebnisse[i] == 1) {
                                erfolg++;
                            } else if (ergebnisse[i] == 20) {
                                keiner++;
                            }
                        }
                        sb.append("Erfolge: " + erfolg + "\n");
                        sb.append("Misserfolge: " + keiner + "\n");
                    }

                    result_text.setText(sb.toString());
                    if (count_together) {
                        result_text.append("\nInsgesamt: " + count);
                    }
                } catch (NumberFormatException e) {
                    result_text.setText("There was an error inside of the onClick Method:\n" + e.getMessage() + "\n" + e.getStackTrace());
                    result_text.setTextColor(Color.RED);
                }
            }
        });
    }

}
