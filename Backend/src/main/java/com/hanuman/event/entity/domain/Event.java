package com.hanuman.event.entity.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.hanuman.event.entity.enums.EventStatus;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
@Table(name = "events")
// @EntityListeners(AuditingEntityListener.class)
public class Event extends Auditable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private LocalDateTime start;

    private LocalDateTime end;

    @Column(nullable = false)
    private String venue;

    private LocalDateTime salesStart;

    private LocalDateTime salesEnd;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    @Builder.Default
    private EventStatus eventStatus = EventStatus.DRAFT;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organizer_id")
    @JsonBackReference
    private User organizer;

    @Builder.Default
    @ManyToMany(mappedBy = "attendingEvents", fetch = FetchType.LAZY)
    private List<User> attendees = new ArrayList<>();

    @Builder.Default
    @ManyToMany(mappedBy = "staffingEvents", fetch = FetchType.LAZY)
    private List<User> staff = new ArrayList<>();

    @Builder.Default
    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<TicketType> ticketTypes = new ArrayList<>();

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (!super.equals(obj))
            return false;
        if (getClass() != obj.getClass())
            return false;
        Event other = (Event) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (start == null) {
            if (other.start != null)
                return false;
        } else if (!start.equals(other.start))
            return false;
        if (end == null) {
            if (other.end != null)
                return false;
        } else if (!end.equals(other.end))
            return false;
        if (venue == null) {
            if (other.venue != null)
                return false;
        } else if (!venue.equals(other.venue))
            return false;
        if (salesEnd == null) {
            if (other.salesEnd != null)
                return false;
        } else if (!salesEnd.equals(other.salesEnd))
            return false;
        if (eventStatus != other.eventStatus)
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = super.hashCode();
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((start == null) ? 0 : start.hashCode());
        result = prime * result + ((end == null) ? 0 : end.hashCode());
        result = prime * result + ((venue == null) ? 0 : venue.hashCode());
        result = prime * result + ((salesEnd == null) ? 0 : salesEnd.hashCode());
        result = prime * result + ((eventStatus == null) ? 0 : eventStatus.hashCode());
        return result;
    }

}
