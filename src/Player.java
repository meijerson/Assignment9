/**
 * Created by Kenzie on 4/25/2017.
 */
public class Player {
    private int playerScore;
    private String name;

    public Player(String name) {
        this.name = name;
    }

    public void setPlayerScore(int playerScore) {
        this.playerScore = playerScore;
    }

    public int getPlayerScore() {
        return playerScore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
