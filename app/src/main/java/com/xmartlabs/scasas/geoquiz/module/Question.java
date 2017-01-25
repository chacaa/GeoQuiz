package com.xmartlabs.scasas.geoquiz.module;

/**
 * Created by scasas on 1/25/17.
 */

public class Question {
  private int textResId;
  private boolean answerTrue;

  public int getTextResId() {
    return textResId;
  }

  public boolean isAnswerTrue() {
    return answerTrue;
  }

  public static final class Builder {
    private int textResId;
    private boolean answerTrue;

    public Builder() {
    }

    public Builder textResId(int val) {
      textResId = val;
      return this;
    }

    public Builder answerTrue(boolean val) {
      answerTrue = val;
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
