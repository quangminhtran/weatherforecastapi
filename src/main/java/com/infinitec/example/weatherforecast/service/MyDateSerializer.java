package com.infinitec.example.weatherforecast.service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class MyDateSerializer extends StdSerializer<LocalDateTime> {
	private static final long serialVersionUID = 8179395842893871888L;

	public MyDateSerializer() {
        this(null);
    }
 
    public MyDateSerializer(Class<LocalDateTime> t) {
        super(t);
    }
     
    @Override
    public void serialize (LocalDateTime dateTime, JsonGenerator gen, SerializerProvider arg2)
      throws IOException, JsonProcessingException {
        gen.writeString("" + dateTime.toEpochSecond(ZoneOffset.UTC));
    }
}
