-- phpMyAdmin SQL Dump
-- version 2.11.4
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Sep 16, 2015 at 08:40 AM
-- Server version: 5.1.57
-- PHP Version: 5.2.17

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

--
-- Database: `a9747610_0`
--

-- --------------------------------------------------------

--
-- Table structure for table `movie`
--

CREATE TABLE `movie` (
  `id` varchar(10) COLLATE latin1_general_ci NOT NULL,
  `title` varchar(100) COLLATE latin1_general_ci NOT NULL,
  `year` varchar(4) COLLATE latin1_general_ci NOT NULL,
  `rated` varchar(10) COLLATE latin1_general_ci NOT NULL,
  `released` varchar(20) COLLATE latin1_general_ci NOT NULL,
  `genre` varchar(20) COLLATE latin1_general_ci NOT NULL,
  `director` varchar(50) COLLATE latin1_general_ci NOT NULL,
  `writer` varchar(200) COLLATE latin1_general_ci NOT NULL,
  `actors` varchar(500) COLLATE latin1_general_ci NOT NULL,
  `plot` varchar(500) COLLATE latin1_general_ci NOT NULL,
  `poster` varchar(500) COLLATE latin1_general_ci NOT NULL,
  `runtime` varchar(20) COLLATE latin1_general_ci NOT NULL,
  `rating` varchar(20) COLLATE latin1_general_ci NOT NULL,
  `votes` varchar(20) COLLATE latin1_general_ci NOT NULL,
  `imdb` varchar(20) COLLATE latin1_general_ci NOT NULL,
  `tstamp` varchar(20) COLLATE latin1_general_ci NOT NULL
) ENGINE=MyISAM DEFAULT CHARSET=latin1 COLLATE=latin1_general_ci;

--
-- Dumping data for table `movie`
--

INSERT INTO `movie` VALUES('1001', 'My test movie', '2012', '6.7', '13 Aug 2012', 'Drama', 'Alex Black', 'Alex Black (Screen Play)', 'Mario Alan, Klint Eastwood, Carl White', '-', 'http://webneel.com/daily/sites/default/files/images/daily/02-2013/24-silence-of-the-lambs-creative-movie-poster-design.jpg', '123', '2', '123', '', '');
