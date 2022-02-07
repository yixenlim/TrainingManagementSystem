create database bigbraintms;
-- drop database bigbraintms;

CREATE USER 'bigbrain'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON *.* TO 'bigbrain'@'localhost' WITH GRANT OPTION;

use bigbraintms;

-- drop table ADMINISTRATOR;
Create table ADMINISTRATOR
(
	AdminID char(6) NOT NULL PRIMARY KEY,
	AdminName varchar (100),
	Gender varchar (6),
	Contact char (11),
    AdminPassword char(8)
);
insert into ADMINISTRATOR values
('AD0001','Chua Xin Quan','Male','0127769373','Kodwjfb3'),
('AD0002','Lew Xuan Ning','Female','0179983625','Ieh93731');
select * from ADMINISTRATOR;

-- drop table TRAINER;
Create table TRAINER
(
	TrainerID char(6) NOT NULL PRIMARY KEY,
	TrainerName varchar (100),
	Gender varchar (6),
	Contact char (11),
    TrainerPassword char(8),
    SubjectID char(6)
);
insert into TRAINER values
('TR0001','Wong Phang Wei','Male','0123456789','Geud3913','SUB-DT'),
('TR0002','Wong Jit Chow','Male','0178396683','Quif8102','SUB-EC'),
('TR0003','Sim Shin Xuan','Female','0167948862','Uoejd819','SUB-LT'),
('TR0004','Chia Xin Ying','Female','0136789936','Odwnf432','SUB-PM'),
('TR0005','Lee Jia Huey','Female','0176653427','Dkdow982','SUB-TM');
select * from TRAINER;

-- drop table TRAINEE;
Create table TRAINEE
(
	TraineeID char(6) NOT NULL PRIMARY KEY,
	TraineeName varchar (100),
	Gender varchar (6),
	Contact char (11),
    TraineePassword char(8)
);
insert into TRAINEE values
('TN0001','Lim Yixen','Female','0108195904','Abcd1234'),
('TN0002','Chan Rui Yuan','Male','0128874456','Defg4567');
select * from TRAINEE;

-- drop table SUBJECT;
Create table SUBJECT
(
	SubjectID char(6) NOT NULL PRIMARY KEY ,
	SubjectName varchar(50)
);
insert into SUBJECT values
('SUB-DT','Diversity Training'),
('SUB-EC','Effective Communication'),
('SUB-LT','Leadership Training'),
('SUB-PM','Project Management'),
('SUB-TM','Time Management');
select * from SUBJECT;

-- drop table CLASS;
Create table CLASS
(
	ClassID char(6) NOT NULL PRIMARY KEY,
	SubjectID char(6),
	ClassDate date,
	NameList varchar(500),
    AttendedList varchar(500),
	FOREIGN KEY (SubjectID) references SUBJECT(SubjectID)
);
insert into CLASS values
('CL0001','SUB-DT','2021-01-02','TN0001 TN0002 ',''),
('CL0002','SUB-EC','2021-01-09','TN0001 TN0002 ',''),
('CL0003','SUB-LT','2021-01-16','TN0001 TN0002 ',''),
('CL0004','SUB-PM','2021-01-23','TN0001 TN0002 ',''),
('CL0005','SUB-TM','2021-01-30','TN0001 TN0002 ','');
select * from CLASS;

-- drop table FEEDBACK;
Create table FEEDBACK
(
	FeedbackID char(8) NOT NULL,
	TraineeID char(6),
	Criteria char(6),
	Rating int,
    FeedbackComment varchar(5000),
	FOREIGN KEY (TraineeID) references TRAINEE(TraineeID),
    PRIMARY KEY (FeedbackID,TraineeID)
);
select * from FEEDBACK;

-- drop table REQUEST;
Create table REQUEST
(
	RequestID char(8) NOT NULL,
	RequestStatus varchar(10),
	TraineeID char(6),
	ClassID char(6),
    AdminID char(6),
    Reason varchar(5000),
	FOREIGN KEY (TraineeID) references TRAINEE(TraineeID),
	FOREIGN KEY (ClassID) references CLASS(ClassID),
    FOREIGN KEY (AdminID) references ADMINISTRATOR(AdminID),
    PRIMARY KEY (RequestID,TraineeID,ClassID)
);
select * from REQUEST;

-- drop table SUBJECT_MATERIALS;
-- Create table SUBJECT_MATERIALS
-- (
	-- FileName varchar(100) NOT NULL,
    -- SubjectID char(6)  NOT NULL,
    -- FileContent text,
    -- PRIMARY KEY (FileName,SubjectID)
-- );

-- drop table CERTIFICATE;
Create table CERTIFICATE
(
	CertificateID char(10) NOT NULL PRIMARY KEY ,
	CertificateStatus varchar(5),
    TraineeID char(6),
	AdminID char(6),
	FOREIGN KEY (TraineeID) references TRAINEE(TraineeID),
	FOREIGN KEY (AdminID) references ADMINISTRATOR(AdminID)
);
insert into CERTIFICATE (CertificateID,CertificateStatus,TraineeID ) values
('CT48393181','false','TN0001'),
('CT83975823','false','TN0002');
select * from CERTIFICATE;