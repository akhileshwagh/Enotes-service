package com.example.scheduler;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.entity.Notes;
import com.example.repository.NotesRepository;



@Component
public class NotesScheduler {

	@Autowired
	private NotesRepository notesRepo;

	@Scheduled(cron = "0 0 0 * * ?") //every day mid night 12
//	@Scheduled(cron = "* * * ? * *") every second
	public void deleteNotesSchdular() {
		// 20-nov -14 nov -7days
		LocalDateTime cutOffDate = LocalDateTime.now().minusDays(7);
		List<Notes> deleteNotes = notesRepo.findAllByIsDeletedAndDeletedOnBefore(true, cutOffDate);
		notesRepo.deleteAll(deleteNotes);
	}

}
