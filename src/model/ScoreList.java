package model;

import java.util.*;

public class ScoreList {

    private ArrayList<ScoreListEntry> originList;

    public ScoreList() {
        this.originList = new ArrayList<>();
    }

    public void addToScoreList(ScoreListEntry entry) {
        originList.add(entry);
    }

    public ArrayList getRanking() {
        originList.sort(new ScoreListEntryComparator());
        return originList;
    }

    public ArrayList<ScoreListEntry> getList() {
        return originList;
    }
}
