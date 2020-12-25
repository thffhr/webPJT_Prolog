CREATE TABLE `user` (
  `uid` varchar(45) NOT NULL,
  `email` varchar(128) DEFAULT NULL,
  `password` varchar(128) DEFAULT NULL,
  `nickname` varchar(45) DEFAULT NULL,
  `pic_byte` longblob DEFAULT NULL,
  `create_date` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`uid`),
  UNIQUE KEY `user_idx_unique_email` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `portfolio` (
  `pid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(45) DEFAULT NULL,
  `title` varchar(100) DEFAULT NULL,
  `start_date` date DEFAULT current_timestamp(),
  `end_date` date DEFAULT current_timestamp(),
  `contents` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`pid`),
  KEY `portfolio_ibfk_1_idx` (`uid`),
  CONSTRAINT `portfolio_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;

CREATE TABLE `experience` (
  `exid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(45) DEFAULT NULL,
  `title` varchar(255) DEFAULT NULL,
  `start_date` date DEFAULT current_timestamp(),
  `end_date` date DEFAULT current_timestamp(),
  `contents` varchar(1000) DEFAULT NULL,
  PRIMARY KEY (`exid`),
  KEY `uid` (`uid`),
  CONSTRAINT `experience_ibfk_1` FOREIGN KEY (`uid`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

CREATE TABLE `tag` (
  `tid` int(11) NOT NULL AUTO_INCREMENT,
  `tag_name` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`tid`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4;


CREATE TABLE `mail` (
  `id` bigint(8) NOT NULL AUTO_INCREMENT,
  `email` varchar(128) DEFAULT NULL,
  `code` varchar(128) DEFAULT NULL,
  `create_date` datetime DEFAULT current_timestamp(),
  `authorized` int(1) DEFAULT 0,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8;


CREATE TABLE `upload_file` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `file_name` varchar(255) NOT NULL,
  `insert_date` timestamp NULL DEFAULT NULL,
  `size` bigint(20) DEFAULT 0,
  `mime_type` varchar(255) DEFAULT '',
  PRIMARY KEY (`id`),
  UNIQUE KEY `file_name_UNIQUE` (`file_name`),
  KEY `pid` (`pid`),
  CONSTRAINT `upload_file_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `portfolio` (`pid`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;


CREATE TABLE `ex_tag` (
  `extid` int(11) NOT NULL AUTO_INCREMENT,
  `exid` int(11) DEFAULT NULL,
  `tid` int(11) DEFAULT NULL,
  PRIMARY KEY (`extid`),
  KEY `exid` (`exid`),
  KEY `tid` (`tid`),
  CONSTRAINT `ex_tag_ibfk_1` FOREIGN KEY (`exid`) REFERENCES `experience` (`exid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `ex_tag_ibfk_2` FOREIGN KEY (`tid`) REFERENCES `tag` (`tid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;


CREATE TABLE `p_tag` (
  `ptid` int(11) NOT NULL AUTO_INCREMENT,
  `pid` int(11) DEFAULT NULL,
  `tid` int(11) DEFAULT NULL,
  PRIMARY KEY (`ptid`),
  KEY `pid` (`pid`),
  KEY `tid` (`tid`),
  CONSTRAINT `p_tag_ibfk_1` FOREIGN KEY (`pid`) REFERENCES `portfolio` (`pid`) ON DELETE CASCADE,
  CONSTRAINT `p_tag_ibfk_2` FOREIGN KEY (`tid`) REFERENCES `tag` (`tid`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `apply` (
  `apid` int(11) NOT NULL AUTO_INCREMENT,
  `uid` varchar(45) NOT NULL,
  `ap_title` varchar(45) DEFAULT NULL,
  `ap_company` varchar(45) DEFAULT NULL,
  `ap_desc` varchar(255) DEFAULT NULL,
  `ap_term` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`apid`),
  KEY `apply_uid_fk_idx` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `apply_experience` (
  `ap_exid` int(11) NOT NULL AUTO_INCREMENT,
  `apid` int(11) DEFAULT NULL,
  `exid` int(11) DEFAULT NULL,
  PRIMARY KEY (`ap_exid`),
  KEY `apply_exp_apid_fk` (`apid`),
  KEY `apply_exp_exid_fk` (`exid`),
  CONSTRAINT `apply_exp_apid_fk` FOREIGN KEY (`apid`) REFERENCES `apply` (`apid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `apply_exp_exid_fk` FOREIGN KEY (`exid`) REFERENCES `experience` (`exid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `apply_portfolio` (
  `ap_pid` int(11) NOT NULL AUTO_INCREMENT,
  `apid` int(11) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  PRIMARY KEY (`ap_pid`),
  KEY `apply_port_apid_fk` (`apid`),
  KEY `apply_port_pid_fk` (`pid`),
  CONSTRAINT `apply_port_apid_fk` FOREIGN KEY (`apid`) REFERENCES `apply` (`apid`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `apply_port_pid_fk` FOREIGN KEY (`pid`) REFERENCES `portfolio` (`pid`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

CREATE TABLE `applyterm` (
  `aptid` int(11) NOT NULL AUTO_INCREMENT,
  `ap_term` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`aptid`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;