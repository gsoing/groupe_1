package com.episen.ing3.tpmra.configuration;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.DateTimeException;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.Temporal;
import java.time.temporal.TemporalAccessor;
import java.util.function.BiFunction;

import org.threeten.bp.DateTimeUtils;

import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonTokenId;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.datatype.jsr310.DecimalUtils;
import com.fasterxml.jackson.datatype.threetenbp.deser.InstantDeserializer.FromDecimalArguments;
import com.fasterxml.jackson.datatype.threetenbp.deser.InstantDeserializer.FromIntegerArguments;
import com.fasterxml.jackson.datatype.threetenbp.deser.ThreeTenDateTimeDeserializerBase;
import com.sun.el.lang.FunctionMapperImpl.Function;

/**
 * Deserializer for ThreeTen temporal {@link Instant}s, {@link OffsetDateTime}, and {@link ZonedDateTime}s.
 * Adapted from the jackson threetenbp InstantDeserializer to add support for deserializing rfc822 format.
 *
 * @author Nick Williams
 */
public class CustomInstantDeserializer<T extends Temporal>
    extends ThreeTenDateTimeDeserializerBase<T> {
  protected CustomInstantDeserializer(Class<T> supportedType, org.threeten.bp.format.DateTimeFormatter f) {
		super(supportedType, f);
		// TODO Auto-generated constructor stub
	}

private static final long serialVersionUID = 1L;

@Override
protected ThreeTenDateTimeDeserializerBase<T> withDateFormat(org.threeten.bp.format.DateTimeFormatter dtf) {
	// TODO Auto-generated method stub
	return null;
}

@Override
protected ThreeTenDateTimeDeserializerBase<T> withLeniency(Boolean leniency) {
	// TODO Auto-generated method stub
	return null;
}

@Override
protected ThreeTenDateTimeDeserializerBase<T> withShape(Shape shape) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public T deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JsonProcessingException {
	// TODO Auto-generated method stub
	return null;
}

 
}
