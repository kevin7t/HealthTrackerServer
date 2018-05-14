CREATE database accountdb;
CREATE USER 'dbuser'@'localhost' identified BY 'password';
GRANT ALL PRIVILEGES ON accountdb.* TO 'dbuser'@'localhost';
FLUSH PRIVILEGES;