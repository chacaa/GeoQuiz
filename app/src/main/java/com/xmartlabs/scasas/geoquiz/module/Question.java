package com.xmartlabs.scasas.geoquiz.module;

import android.support.annotation.StringRes;

/**
 * Created by scasas on 1/25/17.
 */
public class Question {
  @StringRes
  private int textResId;
  private boolean answerTrue;

  @StringRes
  public int getTextResId() {
    return textResId;
  }

  public boolean isAnswerTrue() {
    return answerTrue;
  }

  public static final class Builder {
    private int textResId;
    private boolean answerTrue;

    public Builder textResId(int textResId) {
      this.textResId = textResId;
      return this;
    }

    public Builder answerTrue(boolean answerTrue) {
      this.answerTrue = answerTrue;
      return this;
    }

    public Question build() {
      return new Question(this);
    }
  }

  private Question(Builder builder) {
    textResId = builder.textResId;
    answerTrue = builder.answerTrue;
  }
}
