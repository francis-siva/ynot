# Ynot User Guide
## _**Ynot** app run with :_
**PostgreSQL 13.2 database**
### For PC configuration
First of all set the environment variable **PATH** by adding PostgreSQL 13 **`bin/`** directory's location.
Something like that _`C:\Program Files\PostgreSQL\13\bin`_.
Make sure `psql -V` or `psql --version` works on **CMD**.

To import database from **`dump/`** folder to pgAdmin tools, enter the following command into **Windows CLI**.
```sh
psql -h [host_name] -p [port_number] -d growmore < [full\path\of\resources\dump\db_gm.sql]
```

For more information get a look on [How to Backup and Restore PostgreSQL Database].

   [How to Backup and Restore PostgreSQL Database]: <https://sqlbackupandftp.com/blog/how-to-backup-and-restore-postgresql-database>

