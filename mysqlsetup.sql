CREATE TABLE `owners` (
  `ID` int(11) NOT NULL AUTO_INCREMENT,
  `FirstName` varchar(30) NOT NULL,
  `LastName` varchar(30) NOT NULL,
  `Age` int(11) NOT NULL,
  `Address` varchar(50) NOT NULL,
  `Phone` varchar(12) NOT NULL,
  `Email` varchar(30) NOT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB

CREATE TABLE `cats` (
  `Id` int(11) NOT NULL AUTO_INCREMENT,
  `OwnerID` int(11) NOT NULL,
  `Name` varchar(20) NOT NULL,
  `Gender` varchar(10) NOT NULL,
  `Breed` varchar(20) NOT NULL,
  `DateOfBirth` timestamp NOT NULL DEFAULT current_timestamp(),
  `Color` varchar(20) NOT NULL,
  `IdentifyingMarkings` varchar(100) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `fk_cats_foreign_key` (`OwnerID`),
  CONSTRAINT `fk_cats_foreign_key` FOREIGN KEY (`OwnerID`) REFERENCES `owners` (`ID`)
) ENGINE=InnoDB;

INSERT INTO `owners` (`ID`, `FirstName`, `LastName`, `Age`, `Address`, `Phone`, `Email`) VALUES
(1, 'Emma', 'Murphy', 30, '12 Maple Street, Dublin, Ireland', '+353 86 123 ', 'emma.murphy@email.ie'),
(2, 'Adam', 'O’Connor', 28, '45 Oak Avenue, Cork, Ireland', '+353 87 234 ', 'adam.oconnor@email.ie'),
(3, 'Sofia', 'Kelly', 65, '78 Pine Road, Galway, Ireland', '+353 85 345 ', 'sofia.kelly@email.ie'),
(4, 'Matthew', 'Byrne', 23, '33 Birch Lane, Limerick, Ireland', '+353 83 456 ', 'matthew.byrne@email.ie'),
(5, 'Lena', 'Walsh', 51, '56 Elm Street, Waterford, Ireland', '+353 89 567 ', 'lena.walsh@email.ie'),
(6, 'James', 'Doyle', 34, '22 River View, Kilkenny, Ireland', '+353 86 678 ', 'james.doyle@email.ie'),
(7, 'Aoife', 'Ryan', 29, '14 Sunset Drive, Sligo, Ireland', '+353 87 789 ', 'aoife.ryan@email.ie'),
(8, 'Cian', 'McCarthy', 42, '9 Harbour Road, Wexford, Ireland', '+353 85 890 ', 'cian.mccarthy@email.ie'),
(9, 'Niamh', 'O’Sullivan', 37, '61 Lake Avenue, Killarney, Ireland', '+353 83 901 ', 'niamh.osullivan@email.ie'),
(10, 'Declan', 'Fitzgerald', 55, '5 Green Park, Athlone, Ireland', '+353 89 012 ', 'declan.fitzgerald@email.ie');

INSERT INTO `cats` (`Id`, `OwnerID`, `Name`, `Gender`, `Breed`, `DateOfBirth`, `Color`, `IdentifyingMarkings`) VALUES
(1, 1, 'Luna', 'Female', 'Maine Coon', '2026-02-24 13:48:47', 'Brown tabby', 'White chest patch and tufted ears'),
(2, 2, 'Oliver', 'Male', 'British Shorthair', '2026-02-24 13:48:48', 'Blue (gray)', 'Small white spot on left paw'),
(3, 3, 'Bella', 'Female', 'Siamese', '2026-02-24 13:48:48', 'Seal point', 'Dark facial mask and darker tail tip'),
(4, 4, 'Leo', 'Male', 'Bengal', '2026-02-24 13:48:48', 'Golden with black ro', 'Distinct large rosette pattern on sides'),
(5, 5, 'Nala', 'Female', 'Ragdoll', '2026-02-24 13:48:48', 'Cream and light gray', 'White blaze on nose and white front socks'),
(6, 1, 'Milo', 'Male', 'Persian', '2026-02-24 13:48:49', 'White', 'Flat face with tear staining under eyes'),
(7, 2, 'Chloe', 'Female', 'Scottish Fold', '2026-02-24 13:48:50', 'Gray tabby', 'Folded ears and white chin patch'),
(8, 3, 'Simba', 'Male', 'Abyssinian', '2026-02-24 13:48:51', 'Ruddy', 'Dark dorsal stripe along back'),
(9, 4, 'Cleo', 'Female', 'Sphynx', '2026-02-24 13:48:52', 'Pinkish gray', 'Wrinkled skin and darker nose bridge'),
(10, 5, 'Oscar', 'Male', 'Norwegian Forest Cat', '2026-02-24 13:48:53', 'Brown and white', 'Thick ruff around neck and white back paws');