-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 11, 2025 at 10:09 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `ticket_project_db`
--

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` bigint(20) NOT NULL,
  `username` varchar(50) DEFAULT NULL COMMENT 'ชื่อผู้ใช้งาน',
  `password` varchar(100) DEFAULT NULL COMMENT 'รหัสผ่านผู้ใช้งาน',
  `email` varchar(100) DEFAULT NULL COMMENT 'อีเมลผู้ใช้งาน',
  `first_name` varchar(50) DEFAULT NULL COMMENT 'ชื่อ',
  `last_name` varchar(50) DEFAULT NULL COMMENT 'นามสกุล',
  `phone_number` varchar(20) DEFAULT NULL COMMENT 'เบอร์โทรศัพท์',
  `role` varchar(20) DEFAULT NULL COMMENT 'ยศผู้ใช้งาน',
  `last_login` datetime DEFAULT NULL COMMENT 'เวลาที่ login ล่าสุด',
  `update_date` datetime DEFAULT NULL COMMENT 'เวลาที่แก้ไขข้อมูล',
  `update_by` int(11) DEFAULT NULL COMMENT 'user_id ของ ผู้ที่แก้ไขข้อมูล',
  `create_date` datetime DEFAULT NULL COMMENT 'เวลาที่สร้าง',
  `create_by` int(11) DEFAULT NULL,
  `status_id` int(11) DEFAULT NULL COMMENT 'สถานะผู้ใช้งาน'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`, `email`, `first_name`, `last_name`, `phone_number`, `role`, `last_login`, `update_date`, `update_by`, `create_date`, `create_by`, `status_id`) VALUES
('1','admin', '$2a$10$AlLC7qpMOI4Slm8AfdZgBOz8ar9.3Q7kffQ0Aj6jViYODvA.IV8E6', 'ITsupport.abc@abc.com', 'ผู้ดูแล', '', '', 'Admin', '2568-05-11 14:41:05', '2568-04-19 03:07:17', 1, NULL, 1, 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `user_id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=491;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
