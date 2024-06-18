-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Feb 08, 2024 at 10:22 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cms2331506`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `Id` int(11) NOT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `Phone` varchar(20) DEFAULT NULL,
  `Address` varchar(255) DEFAULT NULL,
  `Password` varchar(255) DEFAULT NULL,
  `Date` date DEFAULT curdate()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`Id`, `Name`, `Email`, `Phone`, `Address`, `Password`, `Date`) VALUES
(2, 'Roshan Thapa Magar', 'roshan90@gmail.com', '9742531161', 'Dhading', 'Roshan@123#', '2024-02-05');

-- --------------------------------------------------------

--
-- Table structure for table `course`
--

CREATE TABLE `course` (
  `ID` int(11) NOT NULL,
  `NumberOfYears` int(11) DEFAULT NULL,
  `CourseName` varchar(255) DEFAULT NULL,
  `Seats` int(11) DEFAULT NULL,
  `Batch` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `course`
--

INSERT INTO `course` (`ID`, `NumberOfYears`, `CourseName`, `Seats`, `Batch`) VALUES
(5, 3, 'Bsc.it', 250, '2023'),
(6, 4, 'BIM', 115, '2022'),
(7, 4, 'BBA', 100, '2022'),
(8, 4, 'BCA', 120, '2022');

-- --------------------------------------------------------

--
-- Table structure for table `enrol`
--

CREATE TABLE `enrol` (
  `id` int(11) NOT NULL,
  `name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `course` varchar(255) NOT NULL,
  `level` varchar(255) NOT NULL,
  `chooseOne` varchar(255) NOT NULL,
  `chooseTwo` varchar(255) NOT NULL,
  `chooseThree` varchar(255) NOT NULL,
  `chooseFour` varchar(255) NOT NULL,
  `date` timestamp NOT NULL DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `enrol`
--

INSERT INTO `enrol` (`id`, `name`, `email`, `course`, `level`, `chooseOne`, `chooseTwo`, `chooseThree`, `chooseFour`, `date`) VALUES
(6, 'Roshan Thapa magar', 'rosan123@gmail.com', 'Bsc(hons) computer science', 'Level 6', 'Math', '', 'English', '', '2024-02-06 08:17:03');

-- --------------------------------------------------------

--
-- Table structure for table `instructor`
--

CREATE TABLE `instructor` (
  `Id` int(11) NOT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `Phone` varchar(20) DEFAULT NULL,
  `Address` varchar(255) DEFAULT NULL,
  `Password` varchar(255) DEFAULT NULL,
  `Date` date DEFAULT curdate()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `instructor`
--

INSERT INTO `instructor` (`Id`, `Name`, `Email`, `Phone`, `Address`, `Password`, `Date`) VALUES
(2, 'Roshan Thapa Magar', 'roshan90@gmail.com', '9742531161', 'Dhading', 'Roshan@88#', '2024-02-05');

-- --------------------------------------------------------

--
-- Table structure for table `modules`
--

CREATE TABLE `modules` (
  `MID` int(11) NOT NULL,
  `MName` varchar(255) NOT NULL,
  `academic_year` varchar(20) DEFAULT NULL,
  `Tutor` varchar(255) DEFAULT NULL,
  `module_level` varchar(20) DEFAULT NULL,
  `module_leader` varchar(255) DEFAULT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  `updated_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `modules`
--

INSERT INTO `modules` (`MID`, `MName`, `academic_year`, `Tutor`, `module_level`, `module_leader`, `created_at`, `updated_at`) VALUES
(258, 'Big data', 'Third', 'Samesh', 'L6', 'Rami', '2024-02-05 17:20:30', '2024-02-05 17:20:30'),
(456, 'Java', 'First', 'samir', 'L4', 'Rame', '2024-02-05 17:15:02', '2024-02-05 17:15:02'),
(457, 'C Programming', 'Second', 'Saurab', 'L5', 'Ram', '2024-02-05 17:18:36', '2024-02-05 17:18:36');

-- --------------------------------------------------------

--
-- Table structure for table `student`
--

CREATE TABLE `student` (
  `Id` int(11) NOT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `Phone` varchar(20) DEFAULT NULL,
  `Address` varchar(255) DEFAULT NULL,
  `Password` varchar(255) DEFAULT NULL,
  `Date` date DEFAULT curdate(),
  `Course` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`Id`, `Name`, `Email`, `Phone`, `Address`, `Password`, `Date`, `Course`) VALUES
(13, 'Roshan Thapa Magar', 'roshan90@gmail.com', '9874564', 'Dhading', 'Roshan@98#', '2024-02-07', 'Bsc.it'),
(14, 'Ram', 'ram123@gmail.com', '9874562142', 'Kathmandu', 'Ram@123#', '2024-02-08', 'Bsc.it'),
(15, 'Ranju magar', 'ranju123@gmail.com', '987432512', 'Kathmandu', 'Ranju@98#', '2024-02-08', 'Bsc.it'),
(16, 'Sundar magar', 'sundar98@gmail.com', '98423662', 'Pokhara', 'Sundar@90#', '2024-02-08', 'Bsc.it');

-- --------------------------------------------------------

--
-- Table structure for table `studentdetails`
--

CREATE TABLE `studentdetails` (
  `ID` int(11) NOT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `Phone` varchar(20) DEFAULT NULL,
  `Age` int(11) DEFAULT NULL,
  `Gender` varchar(10) DEFAULT NULL,
  `Course` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `studentdetails`
--

INSERT INTO `studentdetails` (`ID`, `Name`, `Email`, `Phone`, `Age`, `Gender`, `Course`) VALUES
(1, 'Roshan Magar', 'roshan123@gmail.com', '9742531161', 20, 'Male', 'Bsc.it'),
(2, 'Mahamat', 'mahamat123@gmail.com', '987525523', 19, 'Male', 'BBA'),
(3, 'Ranju magar', 'ranju12@gmail.com', '987522223', 22, 'Female', 'HM');

-- --------------------------------------------------------

--
-- Table structure for table `studentresult`
--

CREATE TABLE `studentresult` (
  `ID` int(11) DEFAULT NULL,
  `Name` varchar(255) DEFAULT NULL,
  `Course` varchar(255) DEFAULT NULL,
  `Module` varchar(255) DEFAULT NULL,
  `Percentage` decimal(5,2) DEFAULT NULL,
  `Grade` varchar(2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `studentresult`
--

INSERT INTO `studentresult` (`ID`, `Name`, `Course`, `Module`, `Percentage`, `Grade`) VALUES
(2, 'Suman', 'History', 'American History', 80.00, 'B'),
(2, 'Suman', 'History', 'European History', 75.00, 'C'),
(2, 'Suman', 'History', 'Asian History', 88.00, 'A'),
(2, 'Suman', 'History', 'Ancient History', 92.00, 'A'),
(1, 'Roshan', 'Bsc.IT', 'OODP', 85.00, 'A'),
(6, 'Ram hari', 'BCA', 'ABC', 30.00, 'E'),
(4, 'Rubin', 'CSIT', 'ABC', 50.00, 'C');

-- --------------------------------------------------------

--
-- Table structure for table `tutor`
--

CREATE TABLE `tutor` (
  `ID` int(11) NOT NULL,
  `TutorName` varchar(255) DEFAULT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `Phone` varchar(20) DEFAULT NULL,
  `Date` date DEFAULT curdate()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tutor`
--

INSERT INTO `tutor` (`ID`, `TutorName`, `Email`, `Phone`, `Date`) VALUES
(5, 'Roshan Thapa Magar', 'roshan98@gmail.com', '9742531161', '2024-02-05'),
(7, 'Sundar Thapa Magar', 'sundar12@gmail.com', '987463226', '2024-02-08'),
(8, 'Dipa Thapa Magar', 'dipa123@gmail.com', '987426225', '2024-02-08'),
(9, 'Ramesh', 'ramesh123@gmail.com', '988756252', '2024-02-08'),
(10, 'Saurab', 'saurab123@gmail.com', '98754142', '2024-02-08');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `course`
--
ALTER TABLE `course`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `enrol`
--
ALTER TABLE `enrol`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `instructor`
--
ALTER TABLE `instructor`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `modules`
--
ALTER TABLE `modules`
  ADD PRIMARY KEY (`MID`);

--
-- Indexes for table `student`
--
ALTER TABLE `student`
  ADD PRIMARY KEY (`Id`);

--
-- Indexes for table `studentdetails`
--
ALTER TABLE `studentdetails`
  ADD PRIMARY KEY (`ID`);

--
-- Indexes for table `tutor`
--
ALTER TABLE `tutor`
  ADD PRIMARY KEY (`ID`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `admin`
--
ALTER TABLE `admin`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `course`
--
ALTER TABLE `course`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `enrol`
--
ALTER TABLE `enrol`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `instructor`
--
ALTER TABLE `instructor`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `student`
--
ALTER TABLE `student`
  MODIFY `Id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT for table `studentdetails`
--
ALTER TABLE `studentdetails`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `tutor`
--
ALTER TABLE `tutor`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
