package com.xmartlabs.scasas.geoquiz.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.xmartlabs.scasas.geoquiz.R;
import com.xmartlabs.scasas.geoquiz.module.Question;

/**
 * Created by scasas on 1/17/17
 */

public class QuizActivity extends AppCompatActivity {
  private Button falseButton;
  private Button trueButon;
  private Button nextButton;
  private TextView questionTextView;
  private Question[] questionBank = new Question[] {
      new Question.Builder()
          .textResId(R.string.question_oceans)
          .answerTrue(true)
          .build(),
      new Question.Builder()
          .textResId(R.string.question_mideast)
          .answerTrue(false)
          .build(),
      new Question.Builder()
          .textResId(R.string.question_africa)
          .answerTrue(false)
          .build(),
      new Question.Builder()
          .textResId(R.string.question_americas)
          .answerTrue(true)
          .build(),
      new Question.Builder()
          .textResId(R.string.question_asia)
          .answerTrue(true)
          .build()
  };
  private int currentIndex = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz);
    setupQuizButton();
    setupQuestion();
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
        Toast.makeText(QuizActivity.this, checkAnswer(trueButon.getText().toString()), Toast.LENGTH_SHORT).show();
      }
    });
    falseButton = (Button) findViewById(R.id.false_button);
    falseButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(QuizActivity.this, checkAnswer(falseButton.getText().toString()), Toast.LENGTH_SHORT).show();
      }
    });
    nextButton = (Button) findViewById(R.id.next_button);
    nextButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (currentIndex == questionBank.length - 1) {
          Toast.makeText(QuizActivity.this, "No more questions for today :)", Toast.LENGTH_SHORT).show();
        } else {
          currentIndex++;
          questionTextView.setText(questionBank[currentIndex].getTextResId());
        }
      }
    });
  }

  private void setupQuestion() {
    questionTextView = (TextView) findViewById(R.id.question);
    int question = questionBank[currentIndex].getTextResId();
    questionTextView.setText(question);
  }

  private int checkAnswer(String valueButton) {
    if (valueButton.toLowerCase().equals(String.valueOf(questionBank[currentIndex].isAnswerTrue()))) {
      return R.string.correct_toast;
    }
    return R.string.incorrect_toast;
  }

}
