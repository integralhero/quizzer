package quiz;

public class AchievementListener {
	
	public void createQuiz(User user){
		int number = user.getQuizzesMade().size();
		if(number == 1) AchievementDao.giveAchievement(user.getUserid(), AchievementTypes.ONE_QUIZ);
		else if(number == 5) AchievementDao.giveAchievement(user.getUserid(), AchievementTypes.FIVE_QUIZZES);
		else if(number == 10) AchievementDao.giveAchievement(user.getUserid(), AchievementTypes.TEN_QUIZZES);
	}
	
	public void takeQuiz(User user){
		int number = user.getQuizzesTaken().size();
		if(number == 1) AchievementDao.giveAchievement(user.getUserid(), AchievementTypes.ONE_QUIZ_TAKEN);
	}
	
	public void highScore(User user){
		AchievementDao.giveAchievement(user.getUserid(), AchievementTypes.HIGHEST_SCORE);
	}
	
	public void practiceMode(User user){
		AchievementDao.giveAchievement(user.getUserid(), AchievementTypes.PRACTICE_MODE);
	}
}
