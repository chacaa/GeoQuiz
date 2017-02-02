package com.xmartlabs.scasas.geoquiz.module;

import android.support.annotation.StringRes;

import java.io.Serializable;

/**
 * Created by scasas on 1/25/17.
 */
public class Question implements Serializable {
  @StringRes
  private int textResId;
  private boolean answerTrue;
  private boolean cheated;

  @StringRes
  public int getTextResId() {
    return textResId;
  }

  public boolean isCheater() {
    return cheated;
  }

  public void setCheated(boolean cheated) {
    this.cheated = cheated;
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
