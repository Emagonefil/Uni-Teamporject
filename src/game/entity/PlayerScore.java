package game.entity;

/**
 * PlayerScore
 *
 * @date 2019-02-08
 */
public class PlayerScore {

    /**
     * playerId
     */
    private int id;

    /**
     * score
     */
    private int score;


    public PlayerScore(int id, int score) {
        this.id = id;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
