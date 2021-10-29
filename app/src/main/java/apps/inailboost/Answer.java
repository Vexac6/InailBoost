package apps.inailboost;

public class Answer {

    private final boolean right;
    public final String text;

    public Answer(boolean right, String text) {
        this.right = right;
        this.text = text;
    }

    public boolean isRight() {return right;}
}
