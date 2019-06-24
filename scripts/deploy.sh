#!/usr/bin/env bash

echo 'For start script print in terminal ./scripts/deploy.sh'

/Users/grow/.gradle/wrapper/dists/gradle-4.10.3-bin/31t79e2qsceia4mkbojplrgx/gradle-4.10.3/bin/gradle clean build

echo 'Copy file...'

scp -i ~/.ssh/id_rsa \
    /Users/grow/IdeaProjects/blackmoon.server/app/build/libs/blackmoon-1.0.0.jar \
    black@104.248.175.187:/home/black/dev/blackmoon/release/

echo 'Connect to server...'

ssh -i ~/.ssh/id_rsa black@104.248.175.187 <<EOF

echo 'Server connect successful. Try to stop Java...'
echo '32133213' | sudo -S service blackmoon restart
echo 'Start Java...'
echo 'Disconnect...'
EOF

echo 'Bye'



#ssh -i ~/.ssh/id_rsa black@104.248.175.187 <<EOF

#echo 'Kill Java..'
#pgrep java | xargs kill -9
#echo 'Start Java...'
#nohup java -jar /home/black/dev/blackmoon/release/blackmoon-1.0.0.jar > /home/black/dev/blackmoon/release/log.txt &
#echo 'Exit'
#EOF