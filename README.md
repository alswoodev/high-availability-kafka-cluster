# high-availability-kafka-cluster
Kafka cluster with Docker-compose for high availability

## Cluster Test
``` bash
cd {directory_what_you_want} && docker compose up -d
docker exec -it kafka2 bash
```

``` bash
[appuser@kafka2 ~]$ kafka-topics --create \
    --bootstrap-server kafka2:29092 \
    --replication-factor 3 \
    --partitions 3 \
    --topic cluster-test

[appuser@kafka2 ~]$ kafka-topics --bootstrap-server kafka2:29092 --list
```

``` bash
[appuser@kafka2 ~]$ kafka-console-producer --bootstrap-server kafka2:29092 --topic cluster-test
> #write any message

[appuser@kafka2 ~]$ kafka-console-consumer --bootstrap-server kafka2:29092 --topic cluster-test --from-beginning
# Or use below for using offset
# kafka-console-consumer --bootstrap-server kafka2:29092 --topic cluster-test --group my-app-group
```

``` bash
[appuser@kafka2 ~]$ kafka-topics --bootstrap-server kafka2:29092 --describe --topic cluster-test

# Iterate this command while after kill or recover kafka container.
# Then check leader, Isr
```

# Kafka Pub/Sub & Stream/Table Test

## Pub/Sub Test

### Prerequisites
- Kafka servers must be running.  
- Spring application must be started.

### Steps
1. Send a message using the REST API:

```bash
curl "http://localhost:8080/kafka?topic=test&message=hi"
```

2. Your message should appear in the consumer console.


## Stream/Table Test
### Prerequisites
- Kafka servers and Spring application must be running.
1. Open a bash session inside the Kafka container:
```
docker exec -it kafka2 bash
```
2. Start consumers for the streamJoin and tableJoin topics:
```
kafka-console-consumer --bootstrap-server kafka2:29092 --topic streamJoin --from-beginning
kafka-console-consumer --bootstrap-server kafka2:29092 --topic tableJoin --from-beginning
```
3. Publish messages to the `left` and `right` topics:
```
curl "http://localhost:8080/kafka/stream?topic=left&message=key:hileft"
curl "http://localhost:8080/kafka/stream?topic=right&message=key:hiright"
```
4. You should see the joined results in the console