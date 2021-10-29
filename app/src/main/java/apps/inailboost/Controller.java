package apps.inailboost;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Controller {

    private static Controller instance = null;

    private Controller() {
        error_counter = 0;
        test_counter = 0;
        study_counter = 0;
        answered = false;
        started = false;
        study_mode = false;
        total_questions = 1189;
        current_test_question = null;
        current_study_question = null;
    }

    public static Controller getInstance() {
        if (instance == null)
            instance = new Controller();
        return instance;
    }

    public int error_counter;
    public int test_counter;
    public int study_counter;
    public boolean answered;
    public boolean started;
    public boolean study_mode;
    public final int total_questions;

    public List<Question> db_questions;

    public ArrayList<Question> study_questions;
    public Queue<Question> test_questions;
    public Question current_test_question;
    public Question current_study_question;

    public void load_questions(List<Question> list_from_db) {
        db_questions = list_from_db;
        study_questions = new ArrayList<>(db_questions);
        current_study_question = study_questions.get(0);
        study_counter++;
    }

    public Question getQuestion() {
        return study_mode ? current_study_question : current_test_question;
    }

    public Answer getAnswer(int index) {
        return study_mode ? current_study_question.answers.get(index) : current_test_question.answers.get(index);
    }

    public void newTestSession() {
        started = true;
        Collections.shuffle(db_questions);
        test_questions = new LinkedList<>(db_questions);
        error_counter = 0;
        test_counter = 0;
        newTestQuestion();
    }

    public void newTestQuestion() {
        current_test_question = test_questions.poll();
        current_test_question.shuffle_answers();
        test_counter++;
        answered = false;
    }

    public void nextStudyQuestion() {
        study_counter++;
        current_study_question = study_questions.get(study_counter);
    }

    public void jumpToStudyQuestion(int id) {
        current_study_question = study_questions.get(id - 1);
        study_counter = id;
    }

    public void fail() {
        if (!answered) {
            error_counter++;
            answered = true;
        }
    }

    public void succeed() { if (!answered) answered = true; }

}

