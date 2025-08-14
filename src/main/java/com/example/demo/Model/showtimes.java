package com.example.demo.Model;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "showtimes")
public class showtimes {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "showtime_id")
    private Integer showtimeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "movie_id", nullable = false)
    private movies movie;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private rooms room;

    @Column(name = "show_time", nullable = false)
    private LocalDateTime showtime;

    @Column(name = "price", nullable = false)
    private BigDecimal price;

    public showtimes() {
    }

    public showtimes(movies movie, rooms room, LocalDateTime showtime, BigDecimal price) {
        this.movie = movie;
        this.room = room;
        this.showtime = showtime;
        this.price = price;
    }
    public Integer getShowtimeId() {
        return showtimeId;
    }
    public void setShowtimeId(Integer showtimeId) {
        this.showtimeId = showtimeId;
    }
    public movies getMovie() {
        return movie;
    }
    public void setMovie(movies movie) {
        this.movie = movie;
    }
    public rooms getRoom() {
        return room;
    }
    public void setRoom(rooms room) {
        this.room = room;
    }
    public LocalDateTime getShowtime() {
        return showtime;
    }
    public void setShowtime(LocalDateTime showtime) {
        this.showtime = showtime;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
