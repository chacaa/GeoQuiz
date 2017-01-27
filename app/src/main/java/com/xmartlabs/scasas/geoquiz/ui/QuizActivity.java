package com.xmartlabs.scasas.geoquiz.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.xmartlabs.scasas.geoquiz.R;
import com.xmartlabs.scasas.geoquiz.module.Question;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by scasas on 1/17/17
 */

public class QuizActivity extends AppCompatActivity {
  private static final String TAG = "QuizActivity";
  private static final String KEY_INDEX = "index";
  private Button falseButton;
  private Button trueButon;
  private ImageButton nextButton;
  private ImageButton prevButton;
  private TextView questionTextView;
  @NonNull
  private List<Question> questions = new ArrayList<>();
  private int currentIndex = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz);
    if (savedInstanceState != null) {
      currentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
    }
    setupQuizButton();
    setupQuestionText();
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
    setupQuestions();
    setupTrueButton();
    setupFalseButton();
    setupNextButton();
    setupPrevButton();
    setupClickableTextView();
  }

  private void setupClickableTextView() {
    questionTextView = (TextView) findViewById(R.id.question);
    questionTextView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!isLastQuestion()) {
          currentIndex++;
          questionTextView.setText(questions.get(currentIndex).getTextResId());
          nextButton.setEnabled(!isLastQuestion());
          prevButton.setEnabled(!isFirstQuestion());
        } else {
          showToast(R.string.no_more);
        }
      }
    });
  }

  private void setupPrevButton() {
    prevButton = (ImageButton) findViewById(R.id.prev_button);
    if (isFirstQuestion()) {
      prevButton.setEnabled(false);
    }
    prevButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!isFirstQuestion()) {
          currentIndex--;
          questionTextView.setText(questions.get(currentIndex).getTextResId());
          nextButton.setEnabled(!isLastQuestion());
          prevButton.setEnabled(!isFirstQuestion());
        }
      }
    });
  }

  private void setupNextButton() {
    nextButton = (ImageButton) findViewById(R.id.next_button);
    nextButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!isLastQuestion()) {
          currentIndex++;
          questionTextView.setText(questions.get(currentIndex).getTextResId());
          prevButton.setEnabled(!isFirstQuestion());
          nextButton.setEnabled(!isLastQuestion());
        }
      }
    });
  }

  private void setupFalseButton() {
    falseButton = (Button) findViewById(R.id.false_button);
    falseButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showToast(obtainAnswerValue(falseButton.getText().toString()));
      }
    });
  }

  private void setupTrueButton() {
    trueButon = (Button) findViewById(R.id.true_button);
    trueButon.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showToast(obtainAnswerValue(trueButon.getText().toString()));
      }
    });
  }

  private void setupQuestionText() {
    questionTextView = (TextView) findViewById(R.id.question);
    int question = questions.get(currentIndex).getTextResId();
    questionTextView.setText(question);
  }

  @StringRes
  private int obtainAnswerValue(@NonNull String valueButton) {
    return valueButton.toLowerCase().equals(String.valueOf(questions.get(currentIndex).isAnswerTrue()))
        ? R.string.correct_toast
        : R.string.incorrect_toast;
  }

  private void setupQuestions() {
    this.questions.add(
        new Question.Builder()
            .textResId(R.string.question_oceans)
            .answerTrue(true)
            .build()
    );
    this.questions.add(
        new Question.Builder()
            .textResId(R.string.question_mideast)
            .answerTrue(false)
            .build()
    );
    this.questions.add(
        new Question.Builder()
            .textResId(R.string.question_africa)
            .answerTrue(false)
            .build()
    );
    this.questions.add(
        new Question.Builder()
            .textResId(R.string.question_americas)
            .answerTrue(true)
            .build()
    );
    this.questions.add(
        new Question.Builder()
            .textResId(R.string.question_asia)
            .answerTrue(true)
            .build()
    );
  }

  private boolean isLastQuestion() {
    return currentIndex == questions.size() - 1;
  }

  private boolean isFirstQuestion() {
    return currentIndex == 0;
  }

  private void showToast(@NonNull int msg) {
    Toast.makeText(QuizActivity.this, msg, Toast.LENGTH_SHORT).show();
  }

  @Override
  public void onSaveInstanceState(Bundle savedInstanceState) {
    super.onSaveInstanceState(savedInstanceState);
    Log.i(TAG, "onSaveInstance");
    savedInstanceState.putInt(KEY_INDEX, currentIndex);
  }
}
