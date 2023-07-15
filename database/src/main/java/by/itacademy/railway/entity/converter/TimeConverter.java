package by.itacademy.railway.entity.converter;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Converter
public class TimeConverter implements AttributeConverter<LocalDateTime, Timestamp> {

    @Override
    public Timestamp convertToDatabaseColumn(LocalDateTime localDateTime) {
        if (localDateTime != null) {
            return Timestamp.valueOf(localDateTime);
        }
        return null;
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
        if (timestamp != null) {
            return timestamp.toLocalDateTime();
        }
        return null;
    }
}
