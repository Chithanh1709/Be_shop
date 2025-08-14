package com.example.demo.Model;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "seats")
public class seats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int seatId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private rooms room;

    @Column(name = "seat_number", length = 10, nullable = false)
    private String seatNumber;

    // @Column(name = "is_available", nullable = false)
    // private boolean isAvailable;

    // Add constructors, getters, and setters as needed
    public seats() {}

    public int getSeatId() {
        return seatId;
    }

    public void setSeatId(int seatId) {
        this.seatId = seatId;
    }

    public rooms getRoom() {
        return room;
    }

    public void setRoom(rooms room) {
        this.room = room;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    // public boolean isAvailable() {
    //     return isAvailable;
    // }

    // public void setAvailable(boolean isAvailable) {
    //     this.isAvailable = isAvailable;
    // }

}
