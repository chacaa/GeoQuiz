package com.xmartlabs.scasas.geoquiz.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.crashlytics.android.Crashlytics;
import com.xmartlabs.scasas.geoquiz.CheatActivity;
import com.xmartlabs.scasas.geoquiz.R;
import com.xmartlabs.scasas.geoquiz.module.Question;

import java.util.ArrayList;

import io.fabric.sdk.android.Fabric;
import timber.log.Timber;

/**
 * Created by scasas on 1/17/17
 */
public class QuizActivity extends AppCompatActivity {
  private static final String KEY_CHEATER = "cheater";
  private static final String KEY_INDEX = "index";
  private static final String KEY_LIST = "list";
  private static final int REQUEST_CODE_CHEAT = 0;

  private Button cheatButton;
  private Button falseButton;
  private ImageButton nextButton;
  private ImageButton prevButton;
  private TextView questionTextView;
  private Button trueButton;

  private int currentIndex = 0;
  private boolean isCheater;
  @NonNull
  private ArrayList<Question> questions = new ArrayList<>();

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    initializeFabric();
    setContentView(R.layout.activity_quiz);
    bindViews();
    if (savedInstanceState != null) {
      currentIndex = savedInstanceState.getInt(KEY_INDEX, 0);
      isCheater = savedInstanceState.getBoolean(KEY_CHEATER, false);
      questions = (ArrayList<Question>) savedInstanceState.getSerializable(KEY_LIST);
    }
    if (questions.isEmpty()) {
      setupQuestions();
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
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (resultCode != Activity.RESULT_OK) {
      return;
    }
    if (requestCode == REQUEST_CODE_CHEAT) {
      if (data == null) {
        return;
      }
      isCheater = CheatActivity.wasAnswerShown(data);
    }
  }

  @Override
  public void onSaveInstanceState(Bundle savedInstanceState) {
    super.onSaveInstanceState(savedInstanceState);
    Timber.i("onSaveInstance");
    savedInstanceState.putInt(KEY_INDEX, currentIndex);
    savedInstanceState.putBoolean(KEY_CHEATER, isCheater);
    savedInstanceState.putSerializable("list", questions);
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
    setupTrueButton();
    setupFalseButton();
    setupCheatButton();
    setupNextButton();
    setupPrevButton();
    setupClickableTextView();
  }

  private void startCheatActivity() {
    Intent intent = CheatActivity.newIntent(QuizActivity.this, getCurrentQuestionValue());
    startActivityForResult(intent, REQUEST_CODE_CHEAT);
  }

  private void setupCheatButton() {
    cheatButton.setOnClickListener(
        new View.OnClickListener() {
          @Override
          public void onClick(View v) {
            startCheatActivity();
          }
        }
    );
  }

  private boolean getCurrentQuestionValue() {
    return questions.get(currentIndex).isAnswerTrue();
  }

  private void setupClickableTextView() {
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
    if (isFirstQuestion()) {
      prevButton.setEnabled(false);
    }
    prevButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!isFirstQuestion()) {
          decideIfTheUserCheated();
          currentIndex--;
          questionTextView.setText(questions.get(currentIndex).getTextResId());
          nextButton.setEnabled(!isLastQuestion());
          prevButton.setEnabled(!isFirstQuestion());
        }
      }
    });
  }

  private void setupNextButton() {
    nextButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (!isLastQuestion()) {
          decideIfTheUserCheated();
          currentIndex++;
          questionTextView.setText(questions.get(currentIndex).getTextResId());
          prevButton.setEnabled(!isFirstQuestion());
          nextButton.setEnabled(!isLastQuestion());
        }
      }
    });
  }

  private void setupFalseButton() {
    falseButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showToast(obtainAnswerValue(falseButton.getText().toString()));
      }
    });
  }

  private void setupTrueButton() {
    trueButton.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        showToast(obtainAnswerValue(trueButton.getText().toString()));
      }
    });
  }

  private void setupQuestionText() {
    int question = questions.get(currentIndex).getTextResId();
    questionTextView.setText(question);
  }

  @StringRes
  private int obtainAnswerValue(@NonNull String valueButton) {
    decideIfTheUserCheated();
    if (questions.get(currentIndex).isCheater()) {
      return valueButton.equalsIgnoreCase(String.valueOf(questions.get(currentIndex).isAnswerTrue()))
          ? R.string.judgment_toast
          : R.string.dumbest_person;
    }
    return valueButton.equalsIgnoreCase(String.valueOf(questions.get(currentIndex).isAnswerTrue()))
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

  private void decideIfTheUserCheated() {
    if (isCheater) {
      isCheater = false;
      questions.get(currentIndex).setCheated(true);
    }
  }

  private void bindViews() {
    cheatButton = (Button) findViewById(R.id.cheat_button);
    falseButton = (Button) findViewById(R.id.false_button);
    nextButton = (ImageButton) findViewById(R.id.next_button);
    prevButton = (ImageButton) findViewById(R.id.prev_button);
    questionTextView = (TextView) findViewById(R.id.question);
    trueButton = (Button) findViewById(R.id.true_button);
  }

  private void initializeFabric() {
    Fabric.with(this, new Crashlytics());
  }
}
