-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 08, 2023 at 05:20 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

--
-- Database: `library_project`
--

-- --------------------------------------------------------

--
-- Table structure for table `members`
--

CREATE TABLE `members` (
                           `user_id` int(4) UNSIGNED NOT NULL COMMENT 'User ID',
                           `id_number` int(8) NOT NULL COMMENT 'ID card number',
                           `registration_date` date DEFAULT NULL COMMENT 'Registration date'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `members`
--

INSERT INTO `members` (`user_id`, `id_number`, `registration_date`) VALUES
    (3, 12345678, '2023-04-05');

-- --------------------------------------------------------

--
-- Table structure for table `admins`
--

CREATE TABLE `admins` (
                          `user_id` int(4) UNSIGNED NOT NULL COMMENT 'User ID',
                          `department` varchar(50) DEFAULT NULL COMMENT 'Department',
                          `email` varchar(255) DEFAULT NULL COMMENT 'Email'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admins`
--

INSERT INTO `admins` (`user_id`, `department`, `email`) VALUES
    (1, 'IT', 'ibrahim.houssem@test.com');

-- --------------------------------------------------------

--
-- Table structure for table `librarians`
--

CREATE TABLE `librarians` (
                              `user_id` int(4) UNSIGNED NOT NULL COMMENT 'User ID',
                              `hire_date` date DEFAULT NULL COMMENT 'Hire date',
                              `salary` float DEFAULT NULL COMMENT 'Salary'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `librarians`
--

INSERT INTO `librarians` (`user_id`, `hire_date`, `salary`) VALUES
    (2, '2023-04-10', 2150.75);

-- --------------------------------------------------------

--
-- Table structure for table `documents`
--

CREATE TABLE `documents` (
                             `document_id` int(10) UNSIGNED NOT NULL COMMENT 'Document ID',
                             `reference` varchar(5) NOT NULL COMMENT 'Document reference',
                             `title` varchar(255) NOT NULL COMMENT 'Document title',
                             `author` varchar(255) DEFAULT NULL COMMENT 'Document author'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `documents`
--

INSERT INTO `documents` (`document_id`, `reference`, `title`, `author`) VALUES
                                                                            (1, 'ABC45', 'Les miserables', 'Victor Hugo'),
                                                                            (2, 'JKL78', 'Test', 'Victor Hugo'),
                                                                            (3, 'gfg44', 'Sample Title', 'Sample Author');

-- --------------------------------------------------------

--
-- Table structure for table `loans`
--

CREATE TABLE `loans` (
                         `loan_id` int(4) UNSIGNED NOT NULL COMMENT 'Loan ID',
                         `loan_date` date NOT NULL COMMENT 'Loan date',
                         `return_date` date NOT NULL COMMENT 'Return deadline',
                         `member_id` int(4) UNSIGNED NOT NULL COMMENT 'Member ID',
                         `librarian_id` int(4) UNSIGNED NOT NULL COMMENT 'Librarian ID',
                         `document_id` int(4) UNSIGNED NOT NULL COMMENT 'Document ID',
                         `is_returned` tinyint(1) NOT NULL COMMENT 'Is the document returned'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `loans`
--

INSERT INTO `loans` (`loan_id`, `loan_date`, `return_date`, `member_id`, `librarian_id`, `document_id`, `is_returned`) VALUES
                                                                                                                           (1, '2023-04-02', '2023-04-23', 3, 2, 1, 0),
                                                                                                                           (3, '2023-04-20', '2023-05-04', 3, 2, 1, 1),
                                                                                                                           (7, '2023-04-30', '2023-05-14', 3, 2, 2, 1),
                                                                                                                           (8, '2023-05-06', '2023-05-20', 3, 2, 3, 0);

-- --------------------------------------------------------

--
-- Table structure for table `reservations`
--

CREATE TABLE `reservations` (
                                `reservation_id` int(4) UNSIGNED NOT NULL COMMENT 'Reservation ID',
                                `document_id` int(4) UNSIGNED NOT NULL COMMENT 'Document ID',
                                `member_id` int(4) UNSIGNED NOT NULL COMMENT 'Member ID',
                                `reservation_date` date NOT NULL COMMENT 'Reservation date',
                                `is_validated` tinyint(1) NOT NULL COMMENT 'Is it validated by librarian'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reservations`
--

INSERT INTO `reservations` (`reservation_id`, `document_id`, `member_id`, `reservation_date`, `is_validated`) VALUES
                                                                                                                  (1, 1, 3, '2023-04-02', 0),
                                                                                                                  (3, 2, 3, '2023-04-20', 0),
                                                                                                                  (4, 2, 3, '2023-04-20', 0),
                                                                                                                  (5, 2, 3, '2023-04-20', 0),
                                                                                                                  (7, 2, 3, '2023-04-20', 0),
                                                                                                                  (8, 2, 3, '2023-04-20', 0),
                                                                                                                  (9, 3, 3, '2023-04-30', 0);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
                         `user_id` int(4) UNSIGNED NOT NULL COMMENT 'User ID',
                         `username` varchar(100) NOT NULL COMMENT 'Username',
                         `password` varchar(100) NOT NULL COMMENT 'Password',
                         `last_name` varchar(50) NOT NULL COMMENT 'Last name',
                         `first_name` varchar(50) NOT NULL COMMENT 'First name',
                         `address` varchar(255) DEFAULT NULL COMMENT 'Address',
                         `phone` int(8) UNSIGNED NOT NULL COMMENT 'Phone number'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`, `last_name`, `first_name`, `address`, `phone`) VALUES
                                                                                                           (1, 'ibrahimdev', 'Test1234', 'Ibrahim', 'Houssem', 'Raoued', 54385290),
                                                                                                           (2, 'Hlel', 'mdp1234', 'Hlel', 'Mohammed Aziz', 'Ghazela', 52789456),
                                                                                                           (3, 'Nour', 'mdp0000', 'Testou', 'Test', 'Tunis', 24567891),
                                                                                                           (4, 'Moetez', 'Test0000', 'Smith', 'John', 'Nabeul', 98456123),
                                                                                                           (6, 'test', 'Test1234', 'Hlfvfel', 'Mohame', 'Raoued', 54385290),
--
-- Indexes for dumped tables
--

--
-- Indexes for table `members`
--
ALTER TABLE `members`
    ADD PRIMARY KEY (`user_id`),
    ADD UNIQUE KEY `id_number_unique` (`id_number`);

--
-- Indexes for table `admins`
--
ALTER TABLE `admins`
    ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `librarians`
--
ALTER TABLE `librarians`
    ADD PRIMARY KEY (`user_id`);

--
-- Indexes for table `documents`
--
ALTER TABLE `documents`
    ADD PRIMARY KEY (`document_id`);

--
-- Indexes for table `loans`
--
ALTER TABLE `loans`
    ADD PRIMARY KEY (`loan_id`),
    ADD KEY `loans_ibfk_2` (`librarian_id`),
    ADD KEY `loans_ibfk_1` (`member_id`),
    ADD KEY `loans_ibfk_3` (`document_id`);

--
-- Indexes for table `reservations`
--
ALTER TABLE `reservations`
    ADD PRIMARY KEY (`reservation_id`),
    ADD KEY `member_id` (`member_id`),
    ADD KEY `document_id` (`document_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
    ADD PRIMARY KEY (`user_id`),
    ADD UNIQUE KEY `username_unique` (`username`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `documents`
--
ALTER TABLE `documents`
    MODIFY `document_id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Document ID', AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `loans`
--
ALTER TABLE `loans`
    MODIFY `loan_id` int(4) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Loan ID', AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT for table `reservations`
--
ALTER TABLE `reservations`
    MODIFY `reservation_id` int(4) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'Reservation ID', AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
    MODIFY `user_id` int(4) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT 'User ID', AUTO_INCREMENT=15;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `members`
--
ALTER TABLE `members`
    ADD CONSTRAINT `members_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `admins`
--
ALTER TABLE `admins`
    ADD CONSTRAINT `admins_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `librarians`
--
ALTER TABLE `librarians`
    ADD CONSTRAINT `librarians_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `loans`
--
ALTER TABLE `loans`
    ADD CONSTRAINT `loans_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `members` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `loans_ibfk_2` FOREIGN KEY (`librarian_id`) REFERENCES `librarians` (`user_id`) ON DELETE CASCADE ON UPDATE CASCADE,
    ADD CONSTRAINT `loans_ibfk_3` FOREIGN KEY (`document_id`) REFERENCES `documents` (`document_id`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `reservations`
--
ALTER TABLE `reservations`
    ADD CONSTRAINT `reservations_ibfk_1` FOREIGN KEY (`member_id`) REFERENCES `members` (`user_id`),
    ADD CONSTRAINT `reservations_ibfk_2` FOREIGN KEY (`document_id`) REFERENCES `documents` (`document_id`);

COMMIT;