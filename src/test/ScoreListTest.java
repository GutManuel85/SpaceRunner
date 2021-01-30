package test;

import model.ScoreList;
import model.ScoreListEntry;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ScoreListTest {

    @Test
    void addToScoreList() {
        final ScoreList list = new ScoreList();
        list.addToScoreList(new ScoreListEntry(1, "Manuel"));
        list.addToScoreList(new ScoreListEntry(2, "Robertina"));
        list.addToScoreList(new ScoreListEntry(3, "Joana"));
        list.addToScoreList(new ScoreListEntry(0, "BadCaptain"));
        assertEquals("0 / BadCaptain", list.getList().get(3).toString());
    }

}
