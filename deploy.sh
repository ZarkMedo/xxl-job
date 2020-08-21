
#!/bin/bash
if [ $# -ne 1 ]
then
        echo "arguments error!"
        exit 1
else
        jarname=$1
        echo $jarname
        pid=`ps -ef | grep "$jarname.jar"|grep -v grep|awk '{print $2}'`
        for id in $pid
        do
                echo $id
                kill -9 $id
                echo "killed $id"
        done
        nohup java -jar ./$jarname.jar  > /mnt/logs/hsrCronJob.log 2>&1 &
fi