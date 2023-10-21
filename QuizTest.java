import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

// Multiple-choice question
class Question {
    private String question;
    private String[] options;
    private int correctOption;

    public Question(String question, String[] options, int correctOption) {
        this.question = question;
        this.options = options;
        this.correctOption = correctOption;
    }

    public String getQuestion() {
        return question;
    }

    public String[] getOptions() {
        return options;
    }

    public int getCorrectOption() {
        return correctOption;
    }
}

// True and false questions
class TrueFalseQuestion {
    private String question;
    private boolean correctAnswer;

    public TrueFalseQuestion(String question, boolean correctAnswer) {
        this.question = question;
        this.correctAnswer = correctAnswer;
    }

    public String getQuestion() {
        return question;
    }

    public boolean getCorrectAnswer() {
        return correctAnswer;
    }
}

// Wrapper class for questions
class QuestionWrapper {
    private Question question;
    private TrueFalseQuestion trueFalseQuestion;

    public QuestionWrapper(Question question) {
        this.question = question;
    }

    public QuestionWrapper(TrueFalseQuestion trueFalseQuestion) {
        this.trueFalseQuestion = trueFalseQuestion;
    }

    public String getQuestion() {
        if (question != null) {
            return question.getQuestion();
        } else {
            return trueFalseQuestion.getQuestion();
        }
    }

    public String[] getOptions() {
        if (question != null) {
            return question.getOptions();
        } else {
            return new String[]{"True", "False"};
        }
    }

    public int getCorrectOption() {
        if (question != null) {
            return question.getCorrectOption();
        } else {
            return trueFalseQuestion.getCorrectAnswer() ? 0 : 1;
        }
    }
}

public class QuizTest extends JFrame implements ActionListener {
    JLabel label;
    JRadioButton radioButton[] = new JRadioButton[2]; // 2 radio buttons for True and False
    JButton btnNext, btnResult;
    ButtonGroup bg;
    int count = 0, current = 0;

    List<QuestionWrapper> questionsList = new ArrayList<>();

    QuizTest(String s) {
        super(s);
        label = new JLabel();
        add(label);
        bg = new ButtonGroup();
        for (int i = 0; i < 2; i++) { // Use 2 radio buttons for True and False
            radioButton[i] = new JRadioButton();
            add(radioButton[i]);
            bg.add(radioButton[i]);
        }
        btnNext = new JButton("Next");
        btnResult = new JButton("Result");
        btnResult.setVisible(false);
        btnResult.addActionListener(this);
        btnNext.addActionListener(this);
        add(btnNext);
        add(btnResult);
        label.setBounds(40, 40, 400, 70);

        // Create and add questions to the list
        questionsList.add(new QuestionWrapper(new Question("Q 1: Which is the official language for Android development?", new String[]{"Java", "Kotlin", "C++", "JavaScript"}, 1)));
        questionsList.add(new QuestionWrapper(new Question("Q 2: What is the main purpose of the public static void main(String[] args) method in a Java program?", new String[]{"To initialize variables", "Start the execution of the program", "Define a method", }, 1)));
        questionsList.add(new QuestionWrapper(new Question("Q 3: Which of the following is not a primitive data type in Java?", new String[]{"int", "String"}, 1)));
        questionsList.add(new QuestionWrapper(new Question("Q 4: What keyword is used to indicate that a variable or method cannot be overridden in a subclass?", new String[]{"final", "static", "abstract", "private"}, 0)));
        questionsList.add(new QuestionWrapper(new Question("Q 5: In Java, what is the result of the expression '5 + 3 * 2'?", new String[]{"16", "11",  "13"}, 0)));
        questionsList.add(new QuestionWrapper(new Question("Q 6: What is the purpose of the super keyword in Java?", new String[]{"To call the superclass constructor", "To access a static method", "To define a new class", "To declare a variable"}, 0)));
        questionsList.add(new QuestionWrapper(new Question("Q 7: Which Java access modifier makes a member (variable or method) accessible only within the same class?", new String[]{"public", "private", "protected", "default"}, 1)));

        // Add true/false questions
        questionsList.add(new QuestionWrapper(new TrueFalseQuestion("Q 8: Java is an interpreted language.", false)));
        questionsList.add(new QuestionWrapper(new TrueFalseQuestion("Q 9: In Java, '==' compares the content of two strings.", true)));
        questionsList.add(new QuestionWrapper(new TrueFalseQuestion("Q 10: Java supports multiple inheritance.", false)));

        for (int i = 0, j = 0; i < 2; i++, j++) {
            radioButton[j].setBounds(80, 110 + i * 30, 400, 40);
        }
        btnNext.setBounds(300, 300, 100, 50);
        btnResult.setBounds(550, 300, 100, 50);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocation(250, 50);
        setVisible(true);
        setSize(1000, 650);
        setData();
//        label.setFont(label.getFont().deriveFont(10));
//        setBounds(100,100,300,30);
//        setFont(new Font("Arial", Font.PLAIN, 50));
    }

    void setData() {
        if (current < questionsList.size()) {
            QuestionWrapper currentQuestion = questionsList.get(current);
            label.setText(currentQuestion.getQuestion());
            String[] options = currentQuestion.getOptions();
            for (int i = 0; i < 2; i++) {
                radioButton[i].setText(options[i]);
            }
            bg.clearSelection(); // Clear the selection for the new question
        }
    }

    boolean checkAns() {
        if (current < questionsList.size()) {
            QuestionWrapper currentQuestion = questionsList.get(current);
            int correctOption = currentQuestion.getCorrectOption();
            return radioButton[correctOption].isSelected();
        }
        return false;
    }

    public static void main(String[] args) {
        new QuizTest("Simple quiz app");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == btnNext) {
            if (checkAns()) {
                count = count + 1;
            }
            current++;
            if (current < questionsList.size()) {
                setData();
            }
            if (current == questionsList.size() - 1) {
                btnNext.setEnabled(false);
                btnResult.setVisible(true);
                btnResult.setText("Result");
            }
        }
        if (e.getActionCommand().equals("Result")) {
            if (checkAns()) {
                count = count + 1;
            }
            current++;
            // Display the result along with the correct answers
            String resultMessage = "Correct answers are " + count + "\n\n";
            for (int i = 0; i < questionsList.size(); i++) {
                QuestionWrapper question = questionsList.get(i);
                resultMessage += question.getQuestion() + "\n";
                int correctOption = question.getCorrectOption();
                if (i < questionsList.size() - 3) {
                    resultMessage += "Correct Answer: " + question.getOptions()[correctOption] + "\n\n";
                } else {
                    resultMessage += "Correct Answer: " + (correctOption == 0 ? "True" : "False") + "\n\n";
                }
            }
            JOptionPane.showMessageDialog(this, resultMessage);
            System.exit(0);
        }
    }
}