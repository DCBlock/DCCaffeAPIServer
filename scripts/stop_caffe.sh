PID=`ps -ef | grep 'DCCaffeAPI-0.0.2' | grep -v 'grep' | awk '{ print $2 }'`
if [ -n "$PID" ]; then
	echo $PID
	kill $PID
	sleep 1
	PID=`ps -ef | grep 'DCCaffeAPI-0.0.2' | grep -v 'grep' | awk '{ print $2 }'`

	if [ -n "$PID" ]; then
	        echo "$PID is not kill yet."
	else
        	echo "kill done!"
	fi

else
	echo "Can't find running process."
fi
