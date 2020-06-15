package com.assignment.publisher.serialize;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import org.apache.kafka.common.serialization.Serializer;

import com.protobuf.model.StreamMessage;

public class MessageSerializer extends Adapter implements Serializer<StreamMessage> {

	@Override
	public byte[] serialize(String topic, StreamMessage message) {

		try {
			ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
			ObjectOutputStream objectStream = new ObjectOutputStream(byteStream);
			objectStream.writeObject(message);
			objectStream.flush();
			objectStream.close();
			return byteStream.toByteArray();
		} catch (IOException e) {
			throw new IllegalStateException("Can't serialize object: " + message, e);
		}
	}
}
