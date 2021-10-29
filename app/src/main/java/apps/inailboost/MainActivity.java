package apps.inailboost;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Controller controller = Controller.getInstance();

        final ConstraintLayout layout = findViewById(R.id.layout);
        final SwitchMaterial mode_switch = findViewById(R.id.change_mode_switch);
        final TextView mode = findViewById(R.id.modeTitle);
        final TextView errors = findViewById(R.id.error_count);
        final TextView questions_done = findViewById(R.id.questions_done);
        final Button startButton = findViewById(R.id.start_button);
        final Button nextButton = findViewById(R.id.next);
        final EditText jump_to = findViewById(R.id.jump_to_question);

        final TextView question_text = findViewById(R.id.question_text);
        final Button b1 = findViewById(R.id.answer1);
        final Button b2 = findViewById(R.id.answer2);
        final Button b3 = findViewById(R.id.answer3);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(this);
        databaseAccess.open();
        controller.load_questions(databaseAccess.loadQuestions());
        databaseAccess.close();

        layout.setBackgroundColor(Color.WHITE); // Gave bug on Bruno's device

        b1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (controller.study_mode || !controller.started) return;
                else {
                    if (controller.getAnswer(0).isRight()) {
                        b1.setBackgroundColor(getResources().getColor(R.color.answer_right));
                        controller.succeed();
                    }
                    else {
                        b1.setBackgroundColor(getResources().getColor(R.color.answer_wrong));
                        controller.fail();
                    }
                }
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (controller.study_mode || !controller.started) return;
                else {
                    if (controller.getAnswer(1).isRight()) {
                        b2.setBackgroundColor(getResources().getColor(R.color.answer_right));
                        controller.succeed();
                    }
                    else {
                        b2.setBackgroundColor(getResources().getColor(R.color.answer_wrong));
                        controller.fail();
                    }
                }
            }
        });

        b3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (controller.study_mode || !controller.started) return;
                else {
                    if (controller.getAnswer(2).isRight()) {
                        b3.setBackgroundColor(getResources().getColor(R.color.answer_right));
                        controller.succeed();
                    }
                    else {
                        b3.setBackgroundColor(getResources().getColor(R.color.answer_wrong));
                        controller.fail();
                    }
                }
            }
        });

        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (controller.study_mode &&
                        jump_to.getText().toString().trim().length() > 0 &&
                        Integer.parseInt(jump_to.getText().toString()) <= controller.total_questions + 1 &&
                        Integer.parseInt(jump_to.getText().toString()) > 0) {
                    controller.jumpToStudyQuestion(Integer.parseInt(jump_to.getText().toString()));
                    questions_done.setText(getString(R.string.question_counter,
                            controller.study_counter,
                            controller.total_questions));
                    if (controller.getAnswer(0).isRight())
                        b1.setBackgroundColor(getResources().getColor(R.color.answer_right));
                    else b1.setBackgroundColor(getResources().getColor(R.color.answer_wrong));
                    if (controller.getAnswer(1).isRight())
                        b2.setBackgroundColor(getResources().getColor(R.color.answer_right));
                    else b2.setBackgroundColor(getResources().getColor(R.color.answer_wrong));
                    if (controller.getAnswer(2).isRight())
                        b3.setBackgroundColor(getResources().getColor(R.color.answer_right));
                    else b3.setBackgroundColor(getResources().getColor(R.color.answer_wrong));
                }
                else {
                    controller.newTestSession();
                    startButton.setText(getString(R.string.restart_button));
                    errors.setText(getString(R.string.error_counter, controller.error_counter));
                    questions_done.setText(getString(R.string.question_counter,
                            controller.test_counter,
                            controller.total_questions));
                    b1.setBackgroundColor(getResources().getColor(R.color.answer_button));
                    b2.setBackgroundColor(getResources().getColor(R.color.answer_button));
                    b3.setBackgroundColor(getResources().getColor(R.color.answer_button));
                }
                question_text.setText(controller.getQuestion().text);
                b1.setText(controller.getAnswer(0).text);
                b2.setText(controller.getAnswer(1).text);
                b3.setText(controller.getAnswer(2).text);
            }
        });

        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (controller.study_mode) {
                    controller.nextStudyQuestion();
                    questions_done.setText(getString(R.string.question_counter,
                            controller.study_counter,
                            controller.total_questions));
                    if (controller.getAnswer(0).isRight())
                        b1.setBackgroundColor(getResources().getColor(R.color.answer_right));
                    else b1.setBackgroundColor(getResources().getColor(R.color.answer_wrong));
                    if (controller.getAnswer(1).isRight())
                        b2.setBackgroundColor(getResources().getColor(R.color.answer_right));
                    else b2.setBackgroundColor(getResources().getColor(R.color.answer_wrong));
                    if (controller.getAnswer(2).isRight())
                        b3.setBackgroundColor(getResources().getColor(R.color.answer_right));
                    else b3.setBackgroundColor(getResources().getColor(R.color.answer_wrong));
                }
                else {
                    if (!controller.started) return;
                    controller.newTestQuestion();
                    questions_done.setText(getString(R.string.question_counter,
                            controller.test_counter,
                            controller.total_questions));
                    b1.setBackgroundColor(getResources().getColor(R.color.answer_button));
                    b2.setBackgroundColor(getResources().getColor(R.color.answer_button));
                    b3.setBackgroundColor(getResources().getColor(R.color.answer_button));
                }
                question_text.setText(controller.getQuestion().text);
                b1.setText(controller.getAnswer(0).text);
                b2.setText(controller.getAnswer(1).text);
                b3.setText(controller.getAnswer(2).text);
                errors.setText(getString(R.string.error_counter, controller.error_counter));
            }
        });

        mode_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    controller.study_mode = true;
                    mode.setText(getString(R.string.study_mode));
                    mode.setTextColor(Color.WHITE);
                    jump_to.setVisibility(View.VISIBLE);
                    startButton.setText(getString(R.string.start_during_study));
                    question_text.setTextColor(Color.WHITE);
                    questions_done.setTextColor(Color.WHITE);
                    questions_done.setText(getString(R.string.question_counter,
                            controller.study_counter,
                            controller.total_questions));
                    errors.setText("0");
                    layout.setBackgroundColor(Color.DKGRAY);
                    if (controller.getAnswer(0).isRight())
                        b1.setBackgroundColor(getResources().getColor(R.color.answer_right));
                    else b1.setBackgroundColor(getResources().getColor(R.color.answer_wrong));
                    if (controller.getAnswer(1).isRight())
                        b2.setBackgroundColor(getResources().getColor(R.color.answer_right));
                    else b2.setBackgroundColor(getResources().getColor(R.color.answer_wrong));
                    if (controller.getAnswer(2).isRight())
                        b3.setBackgroundColor(getResources().getColor(R.color.answer_right));
                    else b3.setBackgroundColor(getResources().getColor(R.color.answer_wrong));
                    question_text.setText(controller.getQuestion().text);
                    b1.setText(controller.getAnswer(0).text);
                    b2.setText(controller.getAnswer(1).text);
                    b3.setText(controller.getAnswer(2).text);
                } else {
                    controller.study_mode = false;
                    mode.setText(getString(R.string.test_mode));
                    mode.setTextColor(Color.BLACK);
                    jump_to.setVisibility(View.INVISIBLE);
                    question_text.setTextColor(Color.BLACK);
                    questions_done.setTextColor(Color.BLACK);
                    questions_done.setText(getString(R.string.question_counter,
                            controller.test_counter,
                            controller.total_questions));
                    errors.setText(getString(R.string.error_counter, controller.error_counter));
                    layout.setBackgroundColor(Color.WHITE);
                    b1.setBackgroundColor(getResources().getColor(R.color.answer_button));
                    b2.setBackgroundColor(getResources().getColor(R.color.answer_button));
                    b3.setBackgroundColor(getResources().getColor(R.color.answer_button));
                    if (controller.started) {
                        question_text.setText(controller.getQuestion().text);
                        startButton.setText(getString(R.string.restart_button));
                        b1.setText(controller.getAnswer(0).text);
                        b2.setText(controller.getAnswer(1).text);
                        b3.setText(controller.getAnswer(2).text);
                    }
                    else {
                        question_text.setText(getString(R.string.welcome));
                        startButton.setText(getString(R.string.start_button));
                        b1.setText(getString(R.string.answer_button));
                        b2.setText(getString(R.string.answer_button));
                        b3.setText(getString(R.string.answer_button));
                    }
                }
            }
        });
    }
}