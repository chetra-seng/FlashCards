package quizcardbuilder;

public class QuizCard {
	private String question;
	private String answer;
	
	public String getQuestion() {
		return question;
	}
	
	public String getAnswer() {
		return answer;
	}
	
	public QuizCard(String q, String a) {
		question = q;
		answer = a;
	}
}
