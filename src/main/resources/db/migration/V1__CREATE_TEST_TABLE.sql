CREATE TABLE dns_record (
 id int NOT NULL AUTO_INCREMENT,
 name varchar(255) UNIQUE NOT NULL,
 comment varchar(255) NOT NULL,
 status enum('PENDING', 'SYNCED','SYNC_FAILED'),
 retired enum('NO', 'MARKED', 'YES'),
 synced_at DATETIME,
 created_at DATETIME NOT NULL,
 updated_at DATETIME NOT NULL,
 PRIMARY KEY(id),
 INDEX(status),
 INDEX(retired)
);