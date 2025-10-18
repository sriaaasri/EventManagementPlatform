package com.hanuman.event.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hanuman.event.entity.domain.Event;
import com.hanuman.event.entity.domain.User;
import com.hanuman.event.entity.enums.EventStatus;

@Repository
public interface EventRepo extends JpaRepository<Event , Long> {

    List<Event> findAllByOrganizer(User organizer);
    Optional<Event> findByOrganizerAndId(User organizer , Long id);
    List<Event> findAllByOrganizerAndEventStatus(User organizer , EventStatus eventStatus , Pageable pageable);
    List<Event> findAllByEventStatus(EventStatus eventStatus , Pageable pageable);

    @Query("SELECT e  FROM Event e where ( e.name LIKE %:searchTerm% OR e.venue LIKE %:searchTerm%) AND e.eventStatus = EventStatus.PUBLISHED")
    // @Query(value = "select * from events where event_status = 'PUBLISHED' AND name like CONCAT('%',:searchTerm ,'%')" , nativeQuery = true)
    List<Event> findPublishedEventsFromQuery(@Param(value = "searchTerm") String searchTerm);
    
}
