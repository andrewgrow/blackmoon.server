- For opening the database on server:
    - connect ssh and print 'sudo -u postgres psql blackmoon' 
    
- Clear tables
    - 'DROP TABLE IF EXISTS prj, project_file, user_role, usr CASCADE;'
    - quit '\q'
    
- Clear images
    - rm -rf /home/black/dev/blackmoon/uploads

- For deploy app
    - ./scripts/deploy.sh
    
- For start|stop|restart server application
    - print 'sudo service blackmoon start|stop|restart' (@see 'update-rc.d' )
    
- Autostart is defined in three cases:
    - 'sudo nano /etc/rc.local'
    - script '/etc/init.d/blackmoon'
    - script 'sudo nano /home/black/dev/blackmoon/scripts/blackmoon-start.sh'
    - script 'sudo nano /home/black/dev/blackmoon/scripts/blackmoon-stop.sh'
 