-- Reset lại DB
DROP DATABASE IF EXISTS test;
CREATE DATABASE IF NOT EXISTS test;
USE test;

-- ========== BẢNG MOVIES RÚT GỌN ==========
CREATE TABLE Movies (
    MovieID INT PRIMARY KEY, -- ID từ API ngoài
    Title VARCHAR(255),
    OriginalTitle VARCHAR(255),
    ReleaseDate DATE
);

-- Rạp chiếu
CREATE TABLE Theaters (
    TheaterID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(255) NOT NULL,
    Location VARCHAR(255) NOT NULL
);

-- Phòng chiếu
CREATE TABLE Rooms (
    RoomID INT PRIMARY KEY AUTO_INCREMENT,
    TheaterID INT,
    RoomName VARCHAR(100),
    TotalSeats INT,
    FOREIGN KEY (TheaterID) REFERENCES Theaters(TheaterID)
);

-- Suất chiếu
CREATE TABLE Showtimes (
    ShowtimeID INT PRIMARY KEY AUTO_INCREMENT,
    MovieID INT,
    RoomID INT,
    ShowTime DATETIME,
    Price DECIMAL(10,2),
    FOREIGN KEY (MovieID) REFERENCES Movies(MovieID),
    FOREIGN KEY (RoomID) REFERENCES Rooms(RoomID)
);

-- Người dùng
CREATE TABLE Users (
    UserID INT PRIMARY KEY AUTO_INCREMENT,
    FullName VARCHAR(255),
    Email VARCHAR(255) UNIQUE,
    Phone VARCHAR(20)
);

-- Vé đã đặt
CREATE TABLE Tickets (
    TicketID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    ShowtimeID INT,
    SeatNumber VARCHAR(10),
    BookingDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (ShowtimeID) REFERENCES Showtimes(ShowtimeID)
);

-- Ghế cố định
CREATE TABLE Seats (
    SeatID INT PRIMARY KEY AUTO_INCREMENT,
    RoomID INT,
    SeatNumber VARCHAR(10),
    FOREIGN KEY (RoomID) REFERENCES Rooms(RoomID)
);

-- ========== DỮ LIỆU MẪU ==========

-- Phim (rút gọn)
INSERT INTO Movies (MovieID, Title, OriginalTitle, ReleaseDate)
VALUES
(552524, 'Lilo & Stitch', 'Lilo & Stitch', '2025-05-17');

-- Rạp chiếu
INSERT INTO Theaters (Name, Location)
VALUES 
('CGV Times City', 'Hà Nội'),         -- ID 1
('Lotte Cinema Landmark', 'TP.HCM');  -- ID 2

-- Phòng chiếu
INSERT INTO Rooms (TheaterID, RoomName, TotalSeats)
VALUES
(1, 'Room A', 100),  -- ID 1
(1, 'Room B', 80),   -- ID 2
(2, 'Room C', 120);  -- ID 3

-- Suất chiếu
INSERT INTO Showtimes (MovieID, RoomID, ShowTime, Price)
VALUES
(552524, 1, '2025-06-04 19:00:00', 90000),
(552524, 2, '2025-06-04 21:30:00', 95000),
(552524, 3, '2025-06-04 18:00:00', 85000);

-- Người dùng
INSERT INTO Users (FullName, Email, Phone)
VALUES
('Nguyễn Chí Thành', 'nchithanh170904@gmail.com', '09712823000'),
('Nguyễn Đức Trí', 'nguyentri1o2.z@gmail.com', '0932123456');

-- Vé đã đặt
INSERT INTO Tickets (UserID, ShowtimeID, SeatNumber)
VALUES
(1, 1, 'A1'),
(2, 1, 'A2'),
(1, 3, 'C5');

-- Ghế cố định
INSERT INTO Seats (RoomID, SeatNumber)
VALUES
(1, 'A1'), (1, 'A2'), (1, 'A3'), (1, 'A4'), (1, 'A5'),
(2, 'B1'), (2, 'B2'), (2, 'B3'),
(3, 'C1'), (3, 'C2'), (3, 'C3'), (3, 'C4'), (3, 'C5');
