package com.xmartlabs.scasas.geoquiz;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by scasas on 17/01/2017
 */
public class QuizActivity extends AppCompatActivity {
  private Button falseButton;
  private Button trueButon;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz);
    setupQuizButton();
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.quiz, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();
    if (id == R.id.action_settings) {
      // TODO: 1/24/17
      return true;
    }
    return super.onOptionsItemSelected(item);
  }

  private void setupQuizButton() {
    trueButon = (Button) findViewById(R.id.true_button);
    trueButon.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(QuizActivity.this, R.string.incorrect_toast, Toast.LENGTH_SHORT).show();
      }
    });
    falseButton = (Button) findViewById(R.id.false_button);
    falseButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(QuizActivity.this, R.string.correct_toast, Toast.LENGTH_SHORT).show();
      }
    });
  }
}
