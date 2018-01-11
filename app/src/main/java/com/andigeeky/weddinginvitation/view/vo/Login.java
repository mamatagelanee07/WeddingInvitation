package com.andigeeky.weddinginvitation.view.vo;

public class Login {
    public String textButton;
    public String textToggle;
    public boolean isVisible;
    @Action.IAction
    public int action = 1;

    public String getTextButton() {
        return textButton;
    }

    public void setTextButton(String textButton) {
        this.textButton = textButton;
    }

    public String getTextToggle() {
        return textToggle;
    }

    public void setTextToggle(String textToggle) {
        this.textToggle = textToggle;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public void setVisible(boolean visible) {
        isVisible = visible;
    }

    public int getAction() {
        return action;
    }

    public void setAction(@Action.IAction int action) {
        this.action = action;
    }
}
