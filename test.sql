-- XÓA VÀ TẠO LẠI DATABASE
DROP DATABASE IF EXISTS test;
CREATE DATABASE test;
USE test;

-- ========== BẢNG PHIM ==========
CREATE TABLE movies (
    movie_id BIGINT PRIMARY KEY, -- ID từ API ngoài
    title VARCHAR(255),
    original_title VARCHAR(255),
    release_date DATE,
    overview TEXT,  -- Bổ sung: Mô tả phim từ API
    poster_path VARCHAR(255),  -- Bổ sung: Đường dẫn ảnh poster từ API
    genre_ids JSON,  -- Bổ sung: Danh sách genre_ids dưới dạng JSON
    popularity FLOAT,  -- Bổ sung: Độ phổ biến từ API
    vote_average FLOAT,  -- Bổ sung: Điểm đánh giá trung bình
    vote_count INT  -- Bổ sung: Số lượng đánh giá
);

-- Rạp chiếu (giữ nguyên)
CREATE TABLE theaters (
    theater_id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(255) NOT NULL,
    location VARCHAR(255) NOT NULL
);

-- Phòng chiếu (giữ nguyên)
CREATE TABLE rooms (
    room_id INT PRIMARY KEY AUTO_INCREMENT,
    theater_id INT NOT NULL,
    room_name VARCHAR(100) NOT NULL,
    total_seats INT NOT NULL,
    FOREIGN KEY (theater_id) REFERENCES theaters(theater_id) ON DELETE CASCADE
);

-- Suất chiếu (giữ nguyên)
CREATE TABLE showtimes (
    showtime_id INT PRIMARY KEY AUTO_INCREMENT,
    movie_id BIGINT,
    room_id INT,
    show_time DATETIME,
    price DECIMAL(10,2),
    FOREIGN KEY (movie_id) REFERENCES movies(movie_id) ON DELETE CASCADE,
    FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE CASCADE
);

-- Người dùng (giữ nguyên)
CREATE TABLE Users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(50) DEFAULT 'customer'
);

-- Vé đã đặt (giữ nguyên)
CREATE TABLE tickets (
    ticket_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    showtime_id INT,
    seat_number VARCHAR(10),
    booking_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES Users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (showtime_id) REFERENCES showtimes(showtime_id) ON DELETE CASCADE
);

-- Ghế cố định (giữ nguyên)
CREATE TABLE seats (
    seat_id INT PRIMARY KEY AUTO_INCREMENT,
    room_id INT,
    seat_number VARCHAR(10),
    FOREIGN KEY (room_id) REFERENCES rooms(room_id) ON DELETE CASCADE
);

-- ========== DỮ LIỆU MẪU ==========

-- USERS (giữ nguyên)
INSERT INTO Users (full_name, email, password, role) VALUES
('Nguyen Van A', 'a@gmail.com', '123456', 'customer'),
('Tran Thi B', 'b@gmail.com', '654321', 'admin');

-- MOVIES (bổ sung dữ liệu mẫu từ API, sử dụng một vài phim từ JSON)
INSERT INTO movies (movie_id, title, original_title, release_date, overview, poster_path, genre_ids, popularity, vote_average, vote_count) VALUES
(755898, 'Cuộc Chiến Giữa Các Thế Giới', 'War of the Worlds', '2025-07-29', 'Will Radford — chuyên gia phân tích an ninh mạng hàng đầu của Bộ An ninh Nội địa — chuyên theo dõi các mối đe dọa đến an ninh quốc gia thông qua một chương trình giám sát diện rộng. Nhưng một cuộc tấn công bất ngờ từ một thực thể bí ẩn đã khiến anh bắt đầu nghi ngờ: phải chăng chính phủ đang che giấu điều gì đó… không chỉ với anh mà với toàn thế giới?', '/yvirUYrva23IudARHn3mMGVxWqM.jpg', '[878, 53]', 1554.6094, 4.167, 297),
(1234821, 'Thế Giới Khủng Long: Tái Sinh', 'Jurassic World Rebirth', '2025-07-01', 'Thế Giới Khủng Long: Tái Sinh lấy bối cảnh 5 năm sau phần phim Thế Giới Khủng Long: Lãnh Địa, môi trường Trái đất đã chứng tỏ phần lớn là không phù hợp với khủng long. Nhiều loài thằn lằn tiền sử được tái sinh đã chết. Những con chưa chết đã rút lui đến một vùng nhiệt đới hẻo lánh gần phòng thí nghiệm. Địa điểm đó chính là nơi bộ ba Scarlett Johansson, Mahershala Ali và Jonathan Bailey dấn thân vào một nhiệm vụ cực kỳ hiểm nguy.', '/2IVVciw7dPhUlNmYIaz0s1d56SZ.jpg', '[878, 12, 28]', 939.9753, 6.39, 1483);

-- THEATERS (giữ nguyên)
INSERT INTO theaters (name, location) VALUES
('Beta Giai Phong', 'Hà Nội'),
('Galaxy Nguyễn Du', 'TP. Hồ Chí Minh');

-- ROOMS (giữ nguyên)
INSERT INTO rooms (theater_id, room_name, total_seats) VALUES
(1, 'Phòng 1', 80),
(1, 'Phòng 2', 80),
(1, 'Phòng 3', 80),
(1, 'Phòng 4', 80),
(1, 'Phòng 5', 80),
(1, 'Phòng 6', 80);

-- SHOWTIMES (giữ nguyên)
INSERT INTO showtimes (movie_id, room_id, show_time, price) VALUES
(755898, 1, '2025-08-15 18:00:00', 75000),  -- Sử dụng movie_id từ API
(1234821, 2, '2025-08-15 20:30:00', 90000);

-- SEATS (10 ghế cho phòng 1 - giữ nguyên)
INSERT INTO seats (room_id, seat_number) VALUES
(1, 'A1'), (1, 'A2'), (1, 'A3'), (1, 'A4'), (1, 'A5'),
(1, 'B1'), (1, 'B2'), (1, 'B3'), (1, 'B4'), (1, 'B5');

-- TICKETS (user 1 đặt 2 vé - giữ nguyên)
INSERT INTO tickets (user_id, showtime_id, seat_number) VALUES
(1, 1, 'A1'),
(1, 1, 'A2');