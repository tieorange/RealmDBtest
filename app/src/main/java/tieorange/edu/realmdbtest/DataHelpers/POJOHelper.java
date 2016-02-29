package tieorange.edu.realmdbtest.DataHelpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import tieorange.edu.realmdbtest.POJO.BookGoal;
import tieorange.edu.realmdbtest.POJO.ReadingEntry;

/**
 * Created by tieorange on 02/02/16.
 */
public class POJOHelper {

    public static String getReadingEntryString(ReadingEntry readingEntry) {
        return "Current page = " + readingEntry.getCurrentPage() + "\tDate = " + readingEntry.getDate();

    }

    public static String getBookGoalString(BookGoal bookGoal) {
        return "Pages count = " + bookGoal.getPagesCount() + "\tGoal = " + bookGoal.getGoal();
    }

    public static List<ReadingEntry> getDummyEntriesList() {
        // TODO
        List<ReadingEntry> readingEntries = new ArrayList<>();

        readingEntries.add(new ReadingEntry(1, parseDate("01.01.2016"))); // 1st group
        readingEntries.add(new ReadingEntry(10, parseDate("02.01.2016"))); // 2nd group
        readingEntries.add(new ReadingEntry(15, parseDate("02.01.2016")));
        readingEntries.add(new ReadingEntry(20, parseDate("03.01.2016"))); // 3nd group
        readingEntries.add(new ReadingEntry(40, parseDate("04.01.2016"))); // 4th group
        readingEntries.add(new ReadingEntry(45, parseDate("04.01.2016")));

        return readingEntries;
    }

    public static TreeMap<Date, List<ReadingEntry>> getGroupedReadingEntries(List<ReadingEntry> readingEntriesList) {
        TreeMap<Date, List<ReadingEntry>> hashMap = new TreeMap<>();
        for (ReadingEntry entry : readingEntriesList) {
            Date key = entry.getDate();
            if (hashMap.containsKey(key)) { // TODO: check if day is the same (ignoring time)
                List<ReadingEntry> list = hashMap.get(key);
                list.add(entry);
            } else {
                List<ReadingEntry> list = new ArrayList<>();
                list.add(entry);
                hashMap.put(key, list);
            }
        }
        return hashMap;
    }

    public static Date parseDate(String date) {
        try {
            return new SimpleDateFormat("dd.mm.yyyy").parse(date);
        } catch (ParseException e) {
            return null;
        }
    }
}
