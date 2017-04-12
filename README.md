# ExportRmqMsg
Utility to read messages from RMQ queue and save them to file.
Messages will be saved in the file with the name of the que.
Java 1.8 needed.
## Option:
* -c,--connection   Connection to RMQ: user:password@host:port
* -p,--prefetch     Prefetch count(Optional field. Default 1000)
* -q,--queues       Queues to read

## Example:
```Bash
java -jar ../ExportRmqMsg.jar -c username:password@locahost:port -q test_q
```
```Bash
java -jar ../ExportRmqMsg.jar -c username:password@locahost:port -q test_q -q test_q2
```
```Bash
java -jar ../ExportRmqMsg.jar -c username:password@locahost:port/test -q test_q -p 100
```

## Output:
timeOfRead;headers;routing_key;msg_body
