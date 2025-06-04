-- Xóa DB cũ và tạo DB mới
DROP DATABASE IF EXISTS test;
CREATE DATABASE IF NOT EXISTS test;
USE test;

-- Phim --
CREATE TABLE Movies (
    MovieID INT PRIMARY KEY AUTO_INCREMENT,
    Title VARCHAR(255) NOT NULL,
    Genre VARCHAR(100),
    Duration INT, -- phút
    Description TEXT,
    ReleaseDate DATE,
    PosterURL VARCHAR(500)
);


-- Rap chieu --
CREATE TABLE Theaters (
    TheaterID INT PRIMARY KEY AUTO_INCREMENT,
    Name VARCHAR(255) NOT NULL,
    Location VARCHAR(255) NOT NULL
);


-- phong --
CREATE TABLE Rooms (
    RoomID INT PRIMARY KEY AUTO_INCREMENT,
    TheaterID INT,
    RoomName VARCHAR(100),
    TotalSeats INT,
    FOREIGN KEY (TheaterID) REFERENCES Theaters(TheaterID)
);

-- Suat chieu --
CREATE TABLE Showtimes (
    ShowtimeID INT PRIMARY KEY AUTO_INCREMENT,
    MovieID INT,
    RoomID INT,
    ShowTime DATETIME,
    Price DECIMAL(10,2),
    FOREIGN KEY (MovieID) REFERENCES Movies(MovieID),
    FOREIGN KEY (RoomID) REFERENCES Rooms(RoomID)
);


-- Nguoi dung --
CREATE TABLE Users (
    UserID INT PRIMARY KEY AUTO_INCREMENT,
    FullName VARCHAR(255),
    Email VARCHAR(255) UNIQUE,
    Phone VARCHAR(20)
);


-- ve da dat --
CREATE TABLE Tickets (
    TicketID INT PRIMARY KEY AUTO_INCREMENT,
    UserID INT,
    ShowtimeID INT,
    SeatNumber VARCHAR(10),
    BookingDate DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (UserID) REFERENCES Users(UserID),
    FOREIGN KEY (ShowtimeID) REFERENCES Showtimes(ShowtimeID)
);


-- Ghe co dinh trong phong --
CREATE TABLE Seats (
    SeatID INT PRIMARY KEY AUTO_INCREMENT,
    RoomID INT,
    SeatNumber VARCHAR(10),
    FOREIGN KEY (RoomID) REFERENCES Rooms(RoomID)
);


INSERT INTO Movies (Title, Genre, Duration, Description, ReleaseDate, PosterURL)
VALUES 
('Godzilla x Kong: The New Empire', 'Action', 115, 'Godzilla và Kong hợp tác chống lại mối đe dọa mới.', '2024-03-29', 'https://image.tmdb.org/t/p/w500/abcd123.jpg'),
('Inside Out 2', 'Animation', 100, 'Câu chuyện tiếp theo của các cảm xúc trong đầu cô bé Riley.', '2024-06-14', 'https://image.tmdb.org/t/p/w500/xyz456.jpg');


INSERT INTO Theaters (Name, Location)
VALUES 
('CGV Times City', 'Hà Nội'),
('Lotte Cinema Landmark', 'TP. Hồ Chí Minh');


INSERT INTO Rooms (TheaterID, RoomName, TotalSeats)
VALUES
(1, 'Room A', 100),
(1, 'Room B', 80),
(2, 'Room C', 120);


INSERT INTO Showtimes (MovieID, RoomID, ShowTime, Price)
VALUES
(1, 1, '2025-06-04 19:00:00', 90000),
(1, 2, '2025-06-04 21:30:00', 95000),
(2, 3, '2025-06-04 18:00:00', 85000);


INSERT INTO Users (FullName, Email, Phone)
VALUES
('Nguyễn Chí Thành', 'nchithanh170904@gmail.com', '09712823000'),
('Nguyễn Đức Trí', 'nguyentri1o2.z@gmail.com', '0932123456');


INSERT INTO Tickets (UserID, ShowtimeID, SeatNumber)
VALUES
(1, 1, 'A1'),
(2, 1, 'A2'),
(1, 3, 'C5');


INSERT INTO Seats (RoomID, SeatNumber)
VALUES
(1, 'A1'), (1, 'A2'), (1, 'A3'), (1, 'A4'), (1, 'A5'),
(2, 'B1'), (2, 'B2'), (2, 'B3'),
(3, 'C1'), (3, 'C2'), (3, 'C3'), (3, 'C4'), (3, 'C5');


