package com.xmartlabs.scasas.geoquiz;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import timber.log.Timber;

public class CheatActivity extends AppCompatActivity {
  private static final String EXTRA_ANSWER_IS_TRUE = CheatActivity.class.getCanonicalName() + ".answer_is_true";
  private static final String EXTRA_ANSWER_SHOWN = CheatActivity.class.getCanonicalName() + ".answer_shown";
  private static final String EXTRA_IS_CHEATER = "is_cheater";
  private static final String TAG = "CheatActivity";

  private TextView answerTextView;
  private Button showAnswer;

  private boolean answerIsTrue;
  private boolean isCheater;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cheat);
    bindView();
    answerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
    setupShowAnswerButton();
    if (savedInstanceState != null) {
      isCheater = savedInstanceState.getBoolean(EXTRA_IS_CHEATER);
      setAnswerShownResult(isCheater);
      showAnswerInTextView();
      showAnswer.setEnabled(false);
    }
  }

  @Override
  public void onSaveInstanceState(Bundle savedInstanceState) {
    super.onSaveInstanceState(savedInstanceState);
    Timber.i(TAG, "onSaveInstance");
    savedInstanceState.putBoolean(EXTRA_IS_CHEATER, isCheater);
  }

  public static Intent newIntent(Context packageContext, boolean asnwerIsTrue) {
    Intent i = new Intent(packageContext, CheatActivity.class);
    i.putExtra(EXTRA_ANSWER_IS_TRUE, asnwerIsTrue);
    return i;
  }

  private void setupShowAnswerButton() {
    showAnswer.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        isCheater = true;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
          int midWidth = showAnswer.getWidth() / 2;
          int midHeight = showAnswer.getHeight() / 2;
          float radius = showAnswer.getWidth();
          Animator anim = ViewAnimationUtils.createCircularReveal(showAnswer, midWidth, midHeight, radius, 0);
          anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
              super.onAnimationEnd(animation);
              showAnswerInTextView();
              answerTextView.setVisibility(View.VISIBLE);
              showAnswer.setVisibility(View.INVISIBLE);
            }
          });
          anim.start();
        } else {
          showAnswerInTextView();
          showAnswer.setEnabled(false);
        }
        setAnswerShownResult(isCheater);
      }
    });
  }

  private void showAnswerInTextView() {
    answerTextView.setText(String.valueOf(answerIsTrue).toUpperCase());
  }

  private void setAnswerShownResult(boolean isAnswerShown) {
    Intent data = new Intent();
    data.putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown);
    setResult(RESULT_OK, data);
  }

  public static boolean wasAnswerShown(Intent result) {
    return result.getBooleanExtra(EXTRA_ANSWER_SHOWN, false);
  }

  private void bindView() {
    answerTextView = (TextView) findViewById(R.id.answer_text_view);
    showAnswer = (Button) findViewById(R.id.show_answer_button);
  }
}
