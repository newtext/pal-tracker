package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTimeEntryRepository implements TimeEntryRepository {

    HashMap<Long, TimeEntry> map = new HashMap();

    private long idCounter = 1L;

    @Override
    public TimeEntry create(TimeEntry timeEntry){
        timeEntry.setId(idCounter++);

        map.put(timeEntry.getId(), timeEntry);
        return timeEntry;
    }

    @Override
    public TimeEntry find(long id) {
        TimeEntry timeEntry = map.get(id);
        return timeEntry;
    }

    @Override
    public TimeEntry update(long id, TimeEntry timeEntry) {

        if (map.get(id) != null){
            timeEntry.setId(id);
            map.put(id, timeEntry);
        }

        return map.get(id);
    }

    @Override
    public void delete(long id) {
        map.remove(id);
    }

    @Override
    public List list() {
        List<TimeEntry> timeEntries = new ArrayList<>(map.values());
        return timeEntries;
    }

}
