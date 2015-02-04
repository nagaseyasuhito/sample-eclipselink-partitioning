#!/bin/sh

IFS=","
SERVERS="${jmeter.servers}"
DESTINATION="${jmeter.home}/lib/junit"

for SERVER in $SERVERS; do
	HOST="${jmeter.user}@$SERVER"

	ssh $HOST "rm $DESTINATION/*.jar"

	scp -r ${project.build.directory}/dependency/* $HOST:$DESTINATION
	scp ${project.build.directory}/${project.build.finalName}.jar $HOST:$DESTINATION
	scp ${project.build.directory}/${project.build.finalName}-tests.jar $HOST:$DESTINATION
	
	ssh $HOST "nohup ${jmeter.home}/bin/jmeter-server > ${jmeter.home}/stdout.log 2> ${jmeter.home}/stderr.log < /dev/null &"
done
