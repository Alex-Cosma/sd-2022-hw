INSERT INTO `users` (noSqlId, firstName, lastName, username, passwordHash, email, role, birthDate, profileImageName)
VALUES ('5d41402abc4b2a', 'Tudorel Alexandru', 'Blidea', 'customer', '12345678', 'customer@revolut.com', 'CUSTOMER',
        '2000-08-29', 'tuddi.jpg'),
       ('e67a5909377685', 'Tudorel Adminisandru', 'Blidea', 'admin', '12345678', 'admin@revolut.com', 'ADMIN',
        '2000-08-29', 'adminu.jpg'),
       ('fea4aea0886064', 'Lana', 'Del Rey', 'lana', '12345678', 'lana@revolut.com', 'CUSTOMER',
        '2000-08-29', 'lana.jpg'),
       ('e0eed308f2cde2', 'Florin', 'Salam', 'fs_briliantul', '12345678', 'florin_salam@revolut.com', 'CUSTOMER',
        '2000-08-29', 'fs.jpg'),
       ('ab17850978e36a', 'Gan', 'Dalf', 'gandalf', '12345678', 'gandalf@revolut.com', 'CUSTOMER',
        '2000-08-29', 'gandalf.jpg'),
       ('eea4aea0886064', 'Jeff', 'Bezos', 'bezy', '12345678', 'bezy@revolut.com', 'CUSTOMER',
        '2000-08-29', 'jeffbezos.jpg');

INSERT INTO `deposit_types` (`name`, `description`, `interestRate`)
VALUES ('goldDeposit', 'the golden one', 0.2),
       ('silverDeposit', 'the silver one', 0.1),
       ('basic deposit', 'you poor!', 0.0);

INSERT INTO `card_types` (`name`, `description`, `maxWithdrawal`, `cashbackPercent`, `color`)
VALUES ("basic", "the most basic card you can get", 4000, 0, '#8B26B2'),
       ("bronze", "cool, almost gold", 10000, 0.05, '#8B452E'),
       ("silver", "silveeeerrrr", 500000, 0.075, '#C9C9C9'),
       ("gold", "not quite the fanciest, but is gold", 100000, 0.2, '#FFBF00'),
       ("platinium", "this is so fancy", 1000000, 0.3, '#737373');

INSERT INTO `card_status` (`name`, `description`)
VALUES ('FUNCTIONAL', 'Still functional, don t worry'),
       ('BLOCKED', 'It s blocked'),
       ('CLOSED', 'Closed'),
       ('NOT_ACTIVATED', 'Not activated yet, go and activate it!');

INSERT INTO `cards` (`ownerId`, `cardTypeId`, `currentAmount`, `cvv`, `number`, `expirationDate`, `statusId`, `iban`)
VALUES (1, 3, 355.98, 123, '9221405245770659', '2025-05-05', 1, 'RO18BTRBTCRCRT24592742629433747122'),
       (1, 1, 3244.18, 222, '5213863092867237', '2025-05-05', 1, 'RO18BTRBTCRCRT79004522765252323173'),
       (1, 4, 11233.2, 699, '4242163069996414', '2025-05-05', 1, 'RO18BTRLEURCRT77210474089611344738'),
       (1, 2, 2133121.78, 996, '5244349772133883', '2025-05-05', 1, 'RO18BTRLEURCRT51136998463760914292'),
       (3, 2, 677.78, 333, '4849122801389426', '2025-05-05', 1, 'RO18BTRBTCRCRT81277147498390198007'),
       (3, 2, 2336150.78, 420, '9768821344347343', '2025-05-05', 1, 'RO18BTRLEURCRT68010584607724265651'),
       (4, 3, 29752.98, 123, '2165428819947468', '2025-05-05', 1, 'RO18BTRLEURCRT72982866051810503446'),
       (5, 1, 213597.18, 222, '8327374139204640', '2025-05-05', 1, 'RO18BTRLEURCRT65734334696082290522'),
       (6, 4, 206.2, 699, '6082739312528664', '2025-05-05', 1, 'RO18BTRLEURCRT15007802308875086888'),
       (4, 2, 361507.78, 996, '4039867127396503', '2025-05-05', 1, 'RO18BTRLEURCRT04999387297774741540'),
       (5, 2, 44780.78, 333, '0194111748307068', '2025-05-05', 1, 'RO18BTRLEURCRT60607540581565151426'),
       (6, 3, 78771.98, 123, '2155378636375329', '2025-05-05', 1, 'RO18BTRLEURCRT27057082257198970396'),
       (4, 1, 3655.18, 222, '2278867516172498', '2025-05-05', 1, 'RO18BTRLEURCRT43882147700587821977'),
       (5, 4, 166532590.2, 699, '0042907739165828', '2025-05-05', 1, 'RO18BTRLEURCRT54094023653130663171'),
       (6, 2, 197551.78, 996, '4438530644780275', '2025-05-05', 1, 'RO18BTRLEURCRT20862860173561417486'),
       (5, 2, 3213.78, 333, '6051233615073792', '2025-05-05', 1, 'RO18BTRLEURCRT96019938495478070494');

INSERT INTO `deposits` (`ownerId`, `depositTypeId`, `createdOn`, `currentAmount`, `targetDate`, `targetAmount`)
VALUES (1, 2, now(), 4238.24, now() + interval 3 year, 5238.11),
       (3, 1, now(), 423894.24, now() + interval 2 year, 523894.22),
       (1, 3, now(), 23111.24, now() + interval 1 year, 13111.42),
       (1, 1, now(), 333.24, now() + interval 4 year, 1033.13),
       (3, 2, now(), 3218.24, now() + interval 2 year, 9218.31),
       (1, 2, now(), 12318.24, now() + interval 3 year, 15318.67);

INSERT INTO `revolut`.`offers` (`name`, `description`)
VALUES ('30% newcomer discount for Golden Card', 'Golden card is now cheaper'),
       ('Buy crypto currencies using the application', 'Yes, you can do that!'),
       ('Buy stocks using the application', 'Yes, you can do that!'),
       ('Pay everywhere', 'Except North Korea');

INSERT INTO contacts (`person1Id`, `person2Id`, `requestStatus`, `acceptedOn`)
VALUES (1, 4, "ACCEPTED", now()),
       (1, 5, "ACCEPTED", now()),
       (1, 6, "ACCEPTED", now()),
       (3, 5, "ACCEPTED", now());