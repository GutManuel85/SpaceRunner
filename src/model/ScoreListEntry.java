package model;

import java.util.Objects;

public class ScoreListEntry implements Comparable<ScoreListEntry> {

    private static int amountOfEntries = 0;

    private int score;
    private String name;
    private int id;

    public ScoreListEntry(int score, String captainName){
        this.score = score;
        this.name = captainName;
        this.id = amountOfEntries;
        amountOfEntries++;
    }

    @Override
    public String toString(){
        return score + " / " + name;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj){
            return true;
        }
        if(!(obj instanceof ScoreListEntry)){
            return false;
        }
        ScoreListEntry other = (ScoreListEntry) obj;
        return this.name.equals(other.name) && this.score == other.score && this.id == other.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.name, this.score, this.id);
    }

    @Override
    public int compareTo(ScoreListEntry other){
        if(this == other){
            return 0;
        }
        return Integer.compare(this.score,other.score);
    }
}
