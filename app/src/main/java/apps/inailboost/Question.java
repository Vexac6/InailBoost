package apps.inailboost;

import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

public class Question {

    public final int id;
    public final String text;
    public List<Answer> answers;

    public Question(int id, String text, String right, String wrong1, String wrong2) {
        this.id = id;
        this.text = text;
        answers = new ArrayList<>();
        answers.add(new Answer(true, right));
        answers.add(new Answer(false, wrong1));
        answers.add(new Answer(false, wrong2));
    }

    public void shuffle_answers() {
        Collections.shuffle(answers);
    }
}
