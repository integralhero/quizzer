package quiz;

public class AchievementListener {
	
	public static void createQuiz(User user){
		int number = QuizDao.getUserQuizzes(user.getUserid()).size();
		
		if(!AchievementDao.checkIfUserHasAchievement(user.getUserid(), AchievementTypes.ONE_QUIZ))
			if(number >= 1) AchievementDao.giveAchievement(user.getUserid(), AchievementTypes.ONE_QUIZ);
		if(!AchievementDao.checkIfUserHasAchievement(user.getUserid(), AchievementTypes.FIVE_QUIZZES))
			 if(number >= 5) AchievementDao.giveAchievement(user.getUserid(), AchievementTypes.FIVE_QUIZZES);
		if(!AchievementDao.checkIfUserHasAchievement(user.getUserid(), AchievementTypes.TEN_QUIZZES))
			 if(number >= 10) AchievementDao.giveAchievement(user.getUserid(), AchievementTypes.TEN_QUIZZES);
	}
	
	public static void takeQuiz(User user){
		if(!AchievementDao.checkIfUserHasAchievement(user.getUserid(), AchievementTypes.ONE_QUIZ_TAKEN)) AchievementDao.giveAchievement(user.getUserid(), AchievementTypes.ONE_QUIZ_TAKEN);
	}
	
	public static void highScore(User user){
		if(!AchievementDao.checkIfUserHasAchievement(user.getUserid(), AchievementTypes.HIGHEST_SCORE))AchievementDao.giveAchievement(user.getUserid(), AchievementTypes.HIGHEST_SCORE);
	}
	
	public static void practiceMode(User user){
		if(!AchievementDao.checkIfUserHasAchievement(user.getUserid(), AchievementTypes.PRACTICE_MODE)) AchievementDao.giveAchievement(user.getUserid(), AchievementTypes.PRACTICE_MODE);
	}
}
