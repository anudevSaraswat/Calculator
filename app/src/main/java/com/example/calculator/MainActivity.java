package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Stack;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private Stack<Character> expressionStack;
    private TextView displayTextView;
    private int count;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        expressionStack = new Stack<>();
        setContentView(R.layout.activity_main);
        displayTextView = findViewById(R.id.displayTextView);
        Button buttonZero = findViewById(R.id.buttonZero);
        Button buttonOne = findViewById(R.id.buttonOne);
        Button buttonTwo = findViewById(R.id.buttonTwo);
        Button buttonThree = findViewById(R.id.buttonThree);
        Button buttonFour = findViewById(R.id.buttonFour);
        Button buttonFive = findViewById(R.id.buttonFive);
        Button buttonSix = findViewById(R.id.buttonSix);
        Button buttonSeven = findViewById(R.id.buttonSeven);
        Button buttonEight = findViewById(R.id.buttonEight);
        Button buttonNine = findViewById(R.id.buttonNine);
        Button addButton = findViewById(R.id.addButton);
        Button subtractButton = findViewById(R.id.subtractButton);
        Button multiplyButton = findViewById(R.id.multiplyButton);
        Button divButton = findViewById(R.id.divideButton);
        Button modButton = findViewById(R.id.modButton);
        Button equalsButton = findViewById(R.id.equalsButton);
        Button dotButton = findViewById(R.id.dotButton);
        Button clearButton = findViewById(R.id.clearButton);
        ImageButton backspace = findViewById(R.id.backspace);
        buttonZero.setOnClickListener(this);
        buttonOne.setOnClickListener(this);
        buttonTwo.setOnClickListener(this);
        buttonThree.setOnClickListener(this);
        buttonFour.setOnClickListener(this);
        buttonFive.setOnClickListener(this);
        buttonSix.setOnClickListener(this);
        buttonSeven.setOnClickListener(this);
        buttonEight.setOnClickListener(this);
        buttonNine.setOnClickListener(this);
        addButton.setOnClickListener(this);
        subtractButton.setOnClickListener(this);
        multiplyButton.setOnClickListener(this);
        divButton.setOnClickListener(this);
        modButton.setOnClickListener(this);
        equalsButton.setOnClickListener(this);
        dotButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        backspace.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        char numOrOp = ' ';

        switch (v.getId()) {

            case R.id.buttonZero: numOrOp = '0';

                break;

            case R.id.buttonOne: numOrOp = '1';

                break;

            case R.id.buttonTwo: numOrOp = '2';

                break;

            case R.id.buttonThree: numOrOp = '3';

                break;

            case R.id.buttonFour: numOrOp = '4';

                break;

            case R.id.buttonFive: numOrOp = '5';

                break;

            case R.id.buttonSix: numOrOp = '6';

                break;

            case R.id.buttonSeven: numOrOp = '7';

                break;

            case R.id.buttonEight: numOrOp = '8';

                break;

            case R.id.buttonNine: numOrOp = '9';

                break;

            case R.id.addButton: numOrOp = '+';

                break;

            case R.id.subtractButton: numOrOp = '-';

                break;

            case R.id.multiplyButton: numOrOp = '*';

                break;

            case R.id.divideButton: numOrOp = '/';

                break;

            case R.id.modButton: numOrOp = '%';

                break;

            case R.id.equalsButton: //Log.e("anudevExpression", getExpression()+"");

                break;

            case R.id.clearButton:
                displayTextView.setText("");
                count = 0;

                break;

            case R.id.dotButton: numOrOp = '.';

                break;

            case R.id.backspace:
        }

        if (count == 15)
            Toast.makeText(this, "Maximum 15 digits allowed!", Toast.LENGTH_SHORT).show();

        if (!(isOperator(numOrOp) && isOperator(expressionStack.peek())) && !expressionStack.isEmpty()){
            displayTextView.append(numOrOp+"");
            addToStack(numOrOp);
        }
    }

    void addToStack(char i) {
        expressionStack.push(i);
    }

//    String getExpression() {
//        StringBuilder builder = new StringBuilder();
//        for (int i = 0; i < expressionStack.size(); i++)
//            builder.append(expressionStack.elementAt(i));
//        return builder.toString();
//    }

    void clearStack(){
        expressionStack.removeAllElements();
    }

    boolean isOperator(char operator){

        boolean flag;

        switch (operator){

            case '+':
            case '-':
            case '*':
            case '/':
            case '%':
            case '.': flag = true;

                break;

            default: flag =  false;
        }

        return flag;
    }

    void solveExpression(){

        Stack<String> numberStack = new Stack<>();
        Stack<Character> operatorStack = new Stack<>();

        for (int i = 0; i < expressionStack.size(); i++){
            if (isOperator(expressionStack.get(i)))
                operatorStack.push(expressionStack.get(i));
            //else

        }



    }

}