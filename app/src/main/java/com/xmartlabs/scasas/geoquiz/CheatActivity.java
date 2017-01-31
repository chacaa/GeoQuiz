package com.xmartlabs.scasas.geoquiz;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends AppCompatActivity {
  private static final String TAG = "CheatActivity";
  private static final String EXTRA_ANSWER_IS_TRUE = CheatActivity.class.getCanonicalName() + ".answer_is_true";
  private static final String EXTRA_ANSWER_SHOWN = CheatActivity.class.getCanonicalName() + ".answer_shown";
  private static final String IS_CHEATER = "is_cheater";
  private boolean answerIsTrue;
  private TextView answerTextView;
  private Button showAnswer;
  private boolean isCheater;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_cheat);
    answerIsTrue = getIntent().getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false);
    setupShowAnswerButton();
    if (savedInstanceState != null) {
      isCheater = savedInstanceState.getBoolean(IS_CHEATER);
      setAnswerShownResult(isCheater);
      showAnswerInTextView();
      showAnswer.setEnabled(false);
    }
  }

  @Override
  public void onSaveInstanceState(Bundle savedInstanceState) {
    super.onSaveInstanceState(savedInstanceState);
    Log.i(TAG, "onSaveInstance");
    savedInstanceState.putBoolean(IS_CHEATER, isCheater);
  }

  public static Intent newIntent(Context packageContext, boolean asnwerIsTrue) {
    Intent i = new Intent(packageContext, CheatActivity.class);
    i.putExtra(EXTRA_ANSWER_IS_TRUE, asnwerIsTrue);
    return i;
  }

  private void setupShowAnswerButton() {
    showAnswer = (Button) findViewById(R.id.show_answer_button);
    showAnswer.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        isCheater = true;
        showAnswerInTextView();
        setAnswerShownResult(isCheater);
        showAnswer.setEnabled(false);
      }
    });
  }

  private void showAnswerInTextView() {
    answerTextView = (TextView) findViewById(R.id.answer_text_view);
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
}
