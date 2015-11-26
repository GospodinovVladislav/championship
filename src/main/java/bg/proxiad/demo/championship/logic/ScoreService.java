package bg.proxiad.demo.championship.logic;

import bg.proxiad.demo.championship.model.Score;

public interface ScoreService {

	Score loadScore(Long id);

	void saveOrUpdateScore(Score participant);

	void deleteScore(Long id);
}
