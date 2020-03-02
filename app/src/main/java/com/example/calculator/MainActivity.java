package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
    private Stack<Character> operatorStack;
    private Stack<String> valueStack;
    private TextView displayTextView;
    private int count;
    private int openingBracketCount;
    private int closingBracketCount;
    private int digitsAfterDecimal;
    private boolean equalsPressed = false;
    private boolean decimalStarted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        expressionStack = new Stack<>();
        operatorStack = new Stack<>();
        valueStack = new Stack<>();
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
        Button bracketButton = findViewById(R.id.bracketButton);
        Button modButton = findViewById(R.id.modButton);
        Button multiplyButton = findViewById(R.id.multiplyButton);
        Button divButton = findViewById(R.id.divideButton);
        Button addButton = findViewById(R.id.addButton);
        Button subtractButton = findViewById(R.id.subtractButton);
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
        bracketButton.setOnClickListener(this);
        modButton.setOnClickListener(this);
        multiplyButton.setOnClickListener(this);
        divButton.setOnClickListener(this);
        addButton.setOnClickListener(this);
        subtractButton.setOnClickListener(this);
        equalsButton.setOnClickListener(this);
        dotButton.setOnClickListener(this);
        clearButton.setOnClickListener(this);
        backspace.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String numOrOp = " ";

        switch (v.getId()) {

            case R.id.buttonZero:
                numOrOp = "0";

                break;

            case R.id.buttonOne:
                numOrOp = "1";

                break;

            case R.id.buttonTwo:
                numOrOp = "2";

                break;

            case R.id.buttonThree:
                numOrOp = "3";

                break;

            case R.id.buttonFour:
                numOrOp = "4";

                break;

            case R.id.buttonFive:
                numOrOp = "5";

                break;

            case R.id.buttonSix:
                numOrOp = "6";

                break;

            case R.id.buttonSeven:
                numOrOp = "7";

                break;

            case R.id.buttonEight:
                numOrOp = "8";

                break;

            case R.id.buttonNine:
                numOrOp = "9";

                break;

            case R.id.bracketButton:
                if (!expressionStack.isEmpty() && expressionStack.peek() == '.')
                    return;
                numOrOp = insertBracket();
                if (decimalStarted) {
                    decimalStarted = false;
                    digitsAfterDecimal = 0;
                }
                equalsPressed = false;

                break;

            case R.id.modButton:
                numOrOp = "%";
                equalsPressed = false;

                break;

            case R.id.multiplyButton:
                numOrOp = "x";
                equalsPressed = false;

                break;

            case R.id.divideButton:
                numOrOp = "/";
                equalsPressed = false;

                break;

            case R.id.addButton:
                numOrOp = "+";
                equalsPressed = false;

                break;

            case R.id.subtractButton:
                numOrOp = "-";
                equalsPressed = false;

                break;

            case R.id.equalsButton:

                numOrOp = null;

                if (!equalsPressed) {
                    if (getExpression() == null)
                        Toast.makeText(this, "Invalid expression!", Toast.LENGTH_SHORT).show();
                    else {
                        equalsPressed = true;
                        solveExpression(getExpression());
                        expressionStack.clear();
                        String result = valueStack.toString();
                        int i = 1;
                        while (i < result.length() - 1)
                            addToStack(result.charAt(i++));
                        displayTextView.setText(result);
                        clear(1);
                    }
                }

                break;

            case R.id.clearButton:
                displayTextView.setText("");
                clear(2);
                numOrOp = null;

                break;

            case R.id.dotButton:
                numOrOp = insertDecimalPoint();
                if (!decimalStarted)
                    decimalStarted = true;

                break;

            case R.id.backspace:
                numOrOp = null;
                backspace();

        }

        if (numOrOp == null)
            return;

        if (isOperator(numOrOp.charAt(0))) {
            if (decimalStarted) {
                decimalStarted = false;
                digitsAfterDecimal = 0;
            }
            count = 0;
        }

        if (count == 15) {
            Toast.makeText(this, "Maximum 15 digits allowed!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (digitsAfterDecimal == 10) {
            Toast.makeText(this, "Maximum 10 digits allowed after decimal point!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (numOrOp.length() > 1) {
            displayTextView.append(numOrOp);
            addToStack(numOrOp.charAt(0));
            addToStack(numOrOp.charAt(1));
        } else {
            if (!expressionStack.isEmpty()) {

                if (isNumber(numOrOp.charAt(0))) {

                    if (equalsPressed){
                        displayTextView.setText(numOrOp);
                        expressionStack.clear();
                        addToStack(numOrOp.charAt(0));
                        equalsPressed = false;
                        return;
                    }

                    if (isNumber(expressionStack.peek()))
                        count++;

                    if (expressionStack.peek() == ')') {
                        displayTextView.append("x" + numOrOp);
                        addToStack('x');
                        addToStack(numOrOp.charAt(0));
                        return;
                    }

                    if (decimalStarted)
                        digitsAfterDecimal++;
                }

                if (isOperator(numOrOp.charAt(0))) {

                    if (isOperator(expressionStack.peek()))
                        return;

                    if (expressionStack.peek() == '.')
                        return;

                }

                displayTextView.append(numOrOp + "");
                addToStack(numOrOp.charAt(0));

            } else {

                if (isOperator(numOrOp.charAt(0)))
                    return;

                displayTextView.append(numOrOp + "");
                addToStack(numOrOp.charAt(0));
                count++;
            }
        }

//        if (!(isOperator(numOrOp) && isOperator(expressionStack.peek())) && !expressionStack.isEmpty()){
//            displayTextView.append(numOrOp+"");
//            addToStack(numOrOp);
//        }
    }


    void clear(int i){

        if (i == 2)
            expressionStack.clear();
        operatorStack.clear();
        valueStack.clear();
        count = 0;
        openingBracketCount = 0;
        closingBracketCount = 0;
        digitsAfterDecimal = 0;
        decimalStarted = false;

    }

    @SuppressLint("SetTextI18n")
    void backspace(){

        if (!expressionStack.isEmpty()) {

            char temp = expressionStack.pop();

            if (temp == ')')
                --closingBracketCount;

            if (temp == '(')
                --openingBracketCount;

            if (isNumber(temp) && decimalStarted)
                --digitsAfterDecimal;

            if (isNumber(temp) && !decimalStarted)
                --count;

            if (!expressionStack.isEmpty()){
                for (int i = 0; i < expressionStack.size(); i++){
                    if (i == 0)
                        displayTextView.setText(expressionStack.get(i) + "");
                    else
                        displayTextView.append(expressionStack.get(i) + "");
                }
            }
            else
                displayTextView.setText("");
        }
    }

    void addToStack(char i) {
        expressionStack.push(i);
    }

    boolean isOperator(char operator) {

        boolean flag;

        switch (operator) {

            case '+':
            case '-':
            case 'x':
            case '/':
            case '%':
                flag = true;

                break;

            default:
                flag = false;
        }

        return flag;
    }


    boolean isNumber(char operator) {

        boolean flag;

        switch (operator) {

            case '0':
            case '1':
            case '2':
            case '3':
            case '4':
            case '5':
            case '6':
            case '7':
            case '8':
            case '9':
                flag = true;

                break;

            default:
                flag = false;
        }

        return flag;
    }


    String insertBracket() {

        String bracket = "";

        if (expressionStack.isEmpty()) {
            bracket = "(";
            openingBracketCount++;
        } else {

            if (expressionStack.contains('(')) {

                if (expressionStack.peek() == '(') {
                    bracket = "(";
                    openingBracketCount++;
                } else {

                    if (isOperator(expressionStack.peek())) {
                        bracket = "(";
                        openingBracketCount++;
                    }

                    if (expressionStack.peek() == ')') {
                        if (openingBracketCount == closingBracketCount) {
                            bracket = "x(";
                            openingBracketCount++;
                        } else {
                            bracket = ")";
                            closingBracketCount++;
                        }
                    }

                    if (isNumber(expressionStack.peek())) {
                        if (openingBracketCount == closingBracketCount) {
                            bracket = "x(";
                            openingBracketCount++;
                        } else {
                            bracket = ")";
                            closingBracketCount++;
                        }
                    }
                }
            } else {
                if (isNumber(expressionStack.peek())) {
                    bracket = "x(";
                    openingBracketCount++;
                }

                if (isOperator(expressionStack.peek())) {
                    bracket = "(";
                    openingBracketCount++;
                }
            }
        }

        return bracket;
    }


    private String insertDecimalPoint() {

        String point = null;

        if (expressionStack.isEmpty()) {
            point = "0.";
        } else {

            if (isOperator(expressionStack.peek()))
                point = "0.";

            if (isNumber(expressionStack.peek())) {

                if (!decimalStarted)
                    point = ".";

                if (decimalStarted)
                    return point;

            }

            if (expressionStack.peek() == '(')
                point = "0.";

            if (expressionStack.peek() == ')')
                point = "x0.";

            if (expressionStack.peek() == '.')
                return point;

        }

        return point;
    }


    String getExpression() {

        if (expressionStack.isEmpty() || openingBracketCount != closingBracketCount ||
                isOperator(expressionStack.peek()) || expressionStack.peek() == '.')
            return null;
        else {
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < expressionStack.size(); i++)
                builder.append(expressionStack.elementAt(i));
            return builder.toString();
        }
    }


    int getPrecedence(char op) {

        int precedence = 0;

        switch (op) {

            case '+':
            case '-':
                precedence = 1;

                break;

            case 'x':
            case '/':
            case '%':
                precedence = 2;

        }

        return precedence;

    }


    void solveExpression(String expression) {

        StringBuilder builder;
        int i = 0;

        while (i < expression.length()) {

            builder = new StringBuilder();

            char temp = expression.charAt(i);

            if (isNumber(temp)) {

                builder.append(temp);
                i++;

                while (i < expression.length()) {

                    temp = expression.charAt(i);

                    if (isNumber(temp) || temp == '.')
                        builder.append(temp);
                    else
                        break;
                    i++;
                }

                valueStack.push(builder.toString());

            } else if (isOperator(temp)) {

                if (operatorStack.isEmpty() || operatorStack.peek() == '(') {
                    operatorStack.push(temp);
                    i++;
                } else {

                    int stackElementPrecedence = getPrecedence(operatorStack.peek());
                    int tempPrecedence = getPrecedence(temp);

                    if (stackElementPrecedence >= tempPrecedence) {

                        valueStack.push(operatorStack.pop() + "");
                        operatorStack.push(temp);
                        eval();
                        i++;

                    } else {
                        operatorStack.push(temp);
                        i++;
                    }
                }

            } else if (temp == '(') {
                operatorStack.push(temp);
                i++;
            } else {
                char temp2;
                while ((temp2 = operatorStack.pop()) != '(') {
                    valueStack.push(temp2 + "");
                    eval();
                }
                i++;
            }
        }

        while (!operatorStack.isEmpty()) {
            valueStack.push(operatorStack.pop() + "");
            eval();
        }
    }

    void eval() {

        String operator = valueStack.pop();
        double num2 = Double.parseDouble(valueStack.pop());
        double num1 = Double.parseDouble(valueStack.pop());
        double result;

        switch (operator) {

            case "+":
                result = num1 + num2;
                valueStack.push(result + "");

                break;

            case "-":
                result = num1 - num2;
                valueStack.push(result + "");

                break;

            case "/":
                result = num1 / num2;
                valueStack.push(result + "");

                break;

            case "x":
                result = num1 * num2;
                valueStack.push(result + "");

                break;

            case "%":
                result = num1 % num2;
                valueStack.push(result + "");
        }
    }
}