-- XÓA VÀ TẠO LẠI DATABASE
DROP DATABASE IF EXISTS test;
CREATE DATABASE test;
USE test;

-- ========== BẢNG PHIM ==========
CREATE TABLE movies (
    movie_id BIGINT PRIMARY KEY, -- ID từ API ngoài
    title VARCHAR(255),
    original_title VARCHAR(255),
    release_date DATE
);

-- Rạp chiếu
CREATE TABLE theaters (
    theater_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL
);

-- Phòng chiếu
CREATE TABLE rooms (
    room_id INT PRIMARY KEY AUTO_INCREMENT,
    theater_id INT,
    room_name VARCHAR(100),
    total_seats INT,
    FOREIGN KEY (theater_id) REFERENCES theaters(theater_id) ON DELETE CASCADE
);

-- Suất chiếu
CREATE TABLE showtimes (
    showtime_id INT PRIMARY KEY AUTO_INCREMENT,
    movie_id BIGINT,
    room_id INT,
    show_time DATETIME,
    price DECIMAL(10,2),
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE CASCADE
);

-- Người dùng
CREATE TABLE Users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) DEFAULT 'customer'
);

-- Vé đã đặt
CREATE TABLE tickets (
    ticket_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    showtime_id INT,
    seat_number VARCHAR(10),
    booking_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (showtime_id) REFERENCES showtimes(showtime_id) ON DELETE CASCADE
);

-- Ghế cố định
CREATE TABLE seats (
    seat_id INT PRIMARY KEY AUTO_INCREMENT,
    room_id INT,
    seat_number VARCHAR(10),
    FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE CASCADE
);

-- ========== DỮ LIỆU MẪU ==========

-- USERS
INSERT INTO Users (full_name, email, password, role) VALUES
('Nguyen Van A', 'a@gmail.com', '123456', 'customer'),
('Tran Thi B', 'b@gmail.com', '654321', 'admin');

-- MOVIES
INSERT INTO movies (movie_id, title, original_title, release_date) VALUES
(1001, 'Avengers: Endgame', 'Avengers: Endgame', '2019-04-26'),
(1002, 'Inception', 'Inception', '2010-07-16');

-- THEATERS
INSERT INTO theaters (name, location) VALUES
('CGV Vincom', 'Hà Nội'),
('Galaxy Nguyễn Du', 'TP. Hồ Chí Minh');

-- ROOMS
INSERT INTO rooms (theater_id, room_name, total_seats) VALUES
(1, 'Phòng 1', 100),
(1, 'Phòng 2', 80),
(2, 'Phòng 3', 120);

-- SHOWTIMES
INSERT INTO showtimes (movie_id, room_id, show_time, price) VALUES
(1001, 1, '2025-06-07 18:00:00', 75000),
(1002, 2, '2025-06-07 20:30:00', 90000);

-- SEATS (10 ghế cho phòng 1)
INSERT INTO seats (room_id, seat_number) VALUES
(1, 'A1'), (1, 'A2'), (1, 'A3'), (1, 'A4'), (1, 'A5'),
(1, 'B1'), (1, 'B2'), (1, 'B3'), (1, 'B4'), (1, 'B5');

-- TICKETS (user 1 đặt 2 vé)
INSERT INTO tickets (user_id, showtime_id, seat_number) VALUES
(1, 1, 'A1'),
(1, 1, 'A2');
