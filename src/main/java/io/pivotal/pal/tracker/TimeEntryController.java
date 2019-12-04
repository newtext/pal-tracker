package io.pivotal.pal.tracker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping(value="/time-entries")
public class TimeEntryController {

    TimeEntryRepository timeEntryRepository;
    Logger logger = LoggerFactory.getLogger(TimeEntryController.class);

    public TimeEntryController(TimeEntryRepository timeEntryRepository) {
        this.timeEntryRepository = timeEntryRepository;
    }

    @RequestMapping(method= RequestMethod.POST)
    public ResponseEntity create(@RequestBody TimeEntry timeEntryToCreate) {
        return new ResponseEntity(timeEntryRepository.create(timeEntryToCreate), HttpStatus.CREATED);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity read(@PathVariable("id") long timeEntryId) {
        TimeEntry timeEntry = timeEntryRepository.find(timeEntryId);

        if (timeEntry == null)
            return new ResponseEntity(timeEntry, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity(timeEntry, HttpStatus.OK);
    }

    @RequestMapping(method=RequestMethod.GET)
    public ResponseEntity list() {
        return new ResponseEntity(timeEntryRepository.list(), HttpStatus.OK);
    }

    @RequestMapping(value="/{id}", method=RequestMethod.PUT)
    public ResponseEntity update(@PathVariable("id") long timeEntryId,@RequestBody TimeEntry expected) {
        TimeEntry timeEntry = timeEntryRepository.update(timeEntryId, expected);

        if (timeEntry == null)
            return new ResponseEntity(timeEntry, HttpStatus.NOT_FOUND);
        else
            return new ResponseEntity(timeEntry, HttpStatus.OK);
    }

    @RequestMapping(value="/{id}",method=RequestMethod.DELETE)
    public ResponseEntity delete(@PathVariable("id") long timeEntryId) {
        timeEntryRepository.delete(timeEntryId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
