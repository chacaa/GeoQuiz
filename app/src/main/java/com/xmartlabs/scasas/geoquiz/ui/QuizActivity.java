package com.xmartlabs.scasas.geoquiz.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
  private Button falseButton;
  private Button trueButon;
  private Button nextButton;
  private Button prevButton;
  private TextView questionTextView;
  @NonNull
  private List<Question> questionList = new ArrayList<>();
  private int currentIndex = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_quiz);
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
    addQuestion();
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
          questionTextView.setText(questionList.get(currentIndex).getTextResId());
          nextButton.setEnabled(!isLastQuestion());
          prevButton.setEnabled(!isFirstQuestion());
        } else {
          showToast(R.string.no_more);
        }
      }
    });
  }

  private void setupPrevButton() {
    prevButton = (Button) findViewById(R.id.prev_button);
    prevButton.setEnabled(false);
    prevButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!isFirstQuestion()) {
          currentIndex--;
          questionTextView.setText(questionList.get(currentIndex).getTextResId());
          nextButton.setEnabled(!isLastQuestion());
          prevButton.setEnabled(!isFirstQuestion());
        }
      }
    });
  }

  private void setupNextButton() {
    nextButton = (Button) findViewById(R.id.next_button);
    nextButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!isLastQuestion()) {
          currentIndex++;
          questionTextView.setText(questionList.get(currentIndex).getTextResId());
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
    int question = questionList.get(currentIndex).getTextResId();
    questionTextView.setText(question);
  }

  @StringRes
  private int obtainAnswerValue(@NonNull String valueButton) {
    return (valueButton.toLowerCase().equals(String.valueOf(questionList.get(currentIndex).isAnswerTrue()))) ?
        R.string.correct_toast :
        R.string.incorrect_toast;
  }

  private void addQuestion() {
    int listIndex = 0;
    this.questionList.add(listIndex,
        new Question.Builder()
            .textResId(R.string.question_oceans)
            .answerTrue(true)
            .build()
    );
    listIndex++;
    this.questionList.add(listIndex,
        new Question.Builder()
            .textResId(R.string.question_mideast)
            .answerTrue(false)
            .build()
    );
    listIndex++;
    this.questionList.add(listIndex,
        new Question.Builder()
            .textResId(R.string.question_africa)
            .answerTrue(false)
            .build()
    );
    listIndex++;
    this.questionList.add(listIndex,
        new Question.Builder()
            .textResId(R.string.question_americas)
            .answerTrue(true)
            .build()
    );
    listIndex++;
    this.questionList.add(listIndex,
        new Question.Builder()
            .textResId(R.string.question_asia)
            .answerTrue(true)
            .build()
    );
    listIndex++;
  }

  private boolean isLastQuestion() {
    return currentIndex == questionList.size() - 1;
  }

  private boolean isFirstQuestion() {
    return currentIndex == 0;
  }

  private void showToast(@NonNull int msg){
    Toast.makeText(QuizActivity.this, msg, Toast.LENGTH_SHORT).show();
  }
}
