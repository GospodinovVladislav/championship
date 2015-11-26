package bg.proxiad.demo.championship.model;

public interface ScoreDao {

	void saveOrUpdate(Score score);

	Score load(Long id);
	
	void deleteScore(Long id);
	
}
