package it.polimi.activities;

public class ListItem {
    private String playerName;
    private Integer value;

    public ListItem (String name, Integer value){
        this.playerName = name;
        this.value = value;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
