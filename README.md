# high-availability-kafka-cluster
Kafka cluster with Docker-compose for high availability

## Test
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