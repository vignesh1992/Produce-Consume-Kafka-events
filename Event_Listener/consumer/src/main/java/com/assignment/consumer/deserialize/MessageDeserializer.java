package com.assignment.consumer.deserialize;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;

import org.apache.kafka.common.serialization.Deserializer;

import com.protobuf.model.StreamMessage;

public class MessageDeserializer extends Adapter implements Deserializer<StreamMessage> {

	@Override
	public StreamMessage deserialize(final String topic, byte[] data) {
		try {
			ByteArrayInputStream bis = new ByteArrayInputStream(data);
			ObjectInput objectInputStream = new ObjectInputStream(bis);
			return (StreamMessage) objectInputStream.readObject();

		} catch (IOException | ClassNotFoundException e) {
			throw new IllegalStateException("Can't serialize object: ", e);
		}
	}

}
