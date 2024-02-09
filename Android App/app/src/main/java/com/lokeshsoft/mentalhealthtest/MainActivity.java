package com.lokeshsoft.mentalhealthtest;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity
        implements View.OnClickListener {

    // creating variables
    private Button falseButton;
    private Button trueButton;
    private TextView questionTextView;

    String url = "https://mentalhealthtestapi.herokuapp.com/predict";

    // To store the number of correct answers


    // to keep current question track
    private int currentQuestionIndex = 0;

    int[] questionAnswers = new int[15];

    private final Question[] questionBank = new Question[] {
            // array of objects of class Question
            // providing questions from string
            // resource and the correct ans
            new Question(R.string.q1, true),
            new Question(R.string.q2, false),
            new Question(R.string.q3, true),
            new Question(R.string.q4, true),
            new Question(R.string.q5, true),
            new Question(R.string.q6, false),
            new Question(R.string.q7, true),
            new Question(R.string.q8, true),
            new Question(R.string.q9, true),
            new Question(R.string.q10, true),
            new Question(R.string.q11, true),
            new Question(R.string.q12, true),
            new Question(R.string.q13, true),
            new Question(R.string.q14, true),
            new Question(R.string.q15, true)

    };

    void modelPrediction()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("result");
                            System.out.println(result);
                            int predictedResult = Integer.parseInt(result)*10;
                            String predictedResultText = "Chance of having mental health issues: " + Integer.toString(predictedResult) + "%";
                            questionTextView.setText(predictedResultText);
                            System.out.println(predictedResult);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String,String> getParams()
            {
                Map<String,String> params = new HashMap<String,String>();
                for(int i=1; i<16; i++)
                {
                    params.put("q"+ i,Integer.toString(questionAnswers[i-1]));
                }
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        queue.add(stringRequest);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // setting up the buttons
        // associated with id
        falseButton = findViewById(R.id.false_button);
        trueButton = findViewById(R.id.true_button);


        // register our buttons to listen to
        // click events
        questionTextView = findViewById(R.id.answer_text_view);
        falseButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);
    }

    @SuppressLint({"SetTextI18n", "NonConstantResourceId"})
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View v)
    {
        // checking which button is
        // clicked by user
        // in this case user choose false
        switch (v.getId()) {
            case R.id.false_button:
                checkAnswer(false);

                if (currentQuestionIndex < 16) {
                    currentQuestionIndex
                            = currentQuestionIndex + 1;
                    // we are safe now!
                    // last question reached
                    // making buttons
                    // invisible
                    if (currentQuestionIndex == 15) {
                        trueButton.setVisibility(
                                View.INVISIBLE);
                        falseButton.setVisibility(
                                View.INVISIBLE);
                        modelPrediction();
                    }
                    else {
                        updateQuestion();
                    }
                }
                break;

            case R.id.true_button:
                checkAnswer(true);

                // go to next question
                // limiting question bank range
                if (currentQuestionIndex < 16) {
                    currentQuestionIndex
                            = currentQuestionIndex + 1;
                    // we are safe now!
                    // last question reached
                    // making buttons
                    // invisible
                    if (currentQuestionIndex == 15) {
                        trueButton.setVisibility(
                                View.INVISIBLE);
                        falseButton.setVisibility(
                                View.INVISIBLE);
                        modelPrediction();
                    }
                    else {
                        updateQuestion();
                    }
                }
                break;
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void updateQuestion()
    {
        Log.d("Current", "onClick: " + currentQuestionIndex);

        questionTextView.setText(questionBank[currentQuestionIndex].getAnswerResId());
    }

    private void checkAnswer(boolean userChooseCorrect)
    {
        boolean answerIsTrue = questionBank[currentQuestionIndex].isAnswerTrue();

        if (userChooseCorrect == answerIsTrue)
        {
            questionAnswers[currentQuestionIndex] = 1;
        }
        else
        {
            questionAnswers[currentQuestionIndex] = 0;
        }
    }
}
