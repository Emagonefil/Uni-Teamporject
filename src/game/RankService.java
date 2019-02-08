package game;

import game.entity.PlayerScore;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 * RankService
 * 调用方法
 * @see  RankService#getInstance() {@link #initPlayScore(int)}
 * @date 2019-02-08
 */
public class RankService {

    /**
     * key is  playerId
     * value is score
     */
    private Map<Integer,Integer> scoreMap = new ConcurrentHashMap<>();

    private RankService(){}

    private static RankService instance = new RankService();


    public static RankService getInstance(){
        return instance;
    }

    /**
     * Initialise player's score
     * Add this method when create a new player
     * @param playerId
     */
    public void initPlayScore(int playerId){
        scoreMap.put(playerId,0);
    }


    /**
     * Get current player's score according to playerId
     * @param playerId
     * @return
     */
    public int getPlayerCurrentScore(int playerId){
        return scoreMap.getOrDefault(playerId,0);
    }


    /**
     * Add score to player according to playerId
     * Add this method when hit other player
     * @param playerId
     * @param score
     */
    public void addPlayScore(int playerId,int score){
        scoreMap.put(playerId,getPlayerCurrentScore(playerId) + score);
    }

    /**
     * Remove the player from Ranking page when the player die
     * @param playerId
     */
    public void removePlayer(int playerId){
        scoreMap.remove(playerId);
    }


    /**
     * Get current ranking data according to the scores
     * 用于实施广播
     * @return
     */
    public List<PlayerScore> rankList(){
        List<PlayerScore> rankList = scoreMap.entrySet().stream().sorted((o, o1)->o1.getValue()-o.getValue()).map(v->new PlayerScore(v.getKey(),v.getValue())).collect(Collectors.toList());
        return rankList;
    }

}
