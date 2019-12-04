package io.pivotal.pal.tracker;

import java.util.ArrayList;
import java.util.List;

public interface TimeEntryRepository {


    TimeEntry create(TimeEntry timeEntry);

    TimeEntry find(long id);

    TimeEntry update(long id, TimeEntry timeEntry);

    void delete(long id);

    List list();
}
