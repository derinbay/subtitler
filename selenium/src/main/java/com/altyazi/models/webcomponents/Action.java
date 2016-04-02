package com.altyazi.models.webcomponents;

import com.altyazi.models.WebComponent;

public abstract class Action {

    public static Action add() {
        return new AddAction();
    }

    public static Action save() {
        return new SaveAction();
    }

    public static Action send() {
        return new SendAction();
    }

    public static Action approve() {
        return new ApproveAction();
    }

    public static Action approveWithTrackingNumber() {
        return new ApproveWithTrackingNumberAction();
    }

    public static Action answer() {
        return new AnswerAction();
    }

    public static Action cancel() {
        return new CancelAction();
    }

    public static Action yes() {
        return new YesAction();
    }

    public abstract void execIn(WebComponent component);
}
