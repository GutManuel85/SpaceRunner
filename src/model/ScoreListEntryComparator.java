package model;

import java.util.Comparator;

public class ScoreListEntryComparator implements Comparator<ScoreListEntry> {

    @Override
    public int compare(ScoreListEntry sle1, ScoreListEntry sle2){
        if(sle1 == sle2){
            return 0;
        }else{
            return sle1.compareTo(sle2);
        }
    }
}
