-- Run this to verify the user was created
SELECT User, Host FROM mysql.user WHERE User = 'padel_user';

-- If the above returns empty, run the setup again:
CREATE USER IF NOT EXISTS 'padel_user'@'%' IDENTIFIED BY 'padel_password';
CREATE USER IF NOT EXISTS 'padel_user'@'localhost' IDENTIFIED BY 'padel_password';
GRANT ALL PRIVILEGES ON padel_reservation.* TO 'padel_user'@'%';
GRANT ALL PRIVILEGES ON padel_reservation.* TO 'padel_user'@'localhost';
FLUSH PRIVILEGES;
