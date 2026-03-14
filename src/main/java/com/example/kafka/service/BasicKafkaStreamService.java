package com.example.kafka.service;

import java.time.Duration;

import org.apache.kafka.common.serialization.Serde;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.JoinWindows;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.ValueJoiner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BasicKafkaStreamService {
    private static final Serde<String> STRING_SERDE = Serdes.String();

    @Autowired
    public void buildPipeline(StreamsBuilder sb) {
        // Basic Kafka stream
        KStream<String, String> myStream = sb.stream("stream", Consumed.with(STRING_SERDE, STRING_SERDE));
        myStream.filter((key, value)-> value.contains("foo")).to("streamNext");

        // Kafka stream join
        /* Before you using join with this KStream object, you need to set topic how seperate key/value from single message
         * --property "parse.key=true" --property "key.seperator=:"
         * Then, you can seperate key/value with seperator ':' e.g. "key:value"
         */
        KStream<String, String> leftStream = sb.stream("left",
                Consumed.with(STRING_SERDE, STRING_SERDE));
        KStream<String, String> rightStream = sb.stream("right",
                Consumed.with(STRING_SERDE, STRING_SERDE));

        ValueJoiner<String, String, String> streamJoiner = (leftValue, rightValue) -> {
            return "[StreamJoiner]" + leftValue + "-" + rightValue;
        };
        KStream<String, String> joinedStream = leftStream.join(rightStream,
                streamJoiner,
                JoinWindows.ofTimeDifferenceWithNoGrace(Duration.ofSeconds(10)));
        joinedStream.to("streamJoin");

        // Kafka table with Kafka stream
        KTable<String, String> leftTableFromStream = sb.stream("left",
                Consumed.with(STRING_SERDE, STRING_SERDE)).toTable();
        KTable<String, String> rightTableFromStream = sb.stream("right",
                Consumed.with(STRING_SERDE, STRING_SERDE)).toTable();

        ValueJoiner<String, String, String> TableJoiner = (leftValue, rightValue) -> {
            return "[TableJoiner]" + leftValue + "-" + rightValue;
        };
        KTable<String, String> joinedTable = leftTableFromStream.join(rightTableFromStream, TableJoiner);

        joinedTable.toStream().to("tableJoin");
    }
}



