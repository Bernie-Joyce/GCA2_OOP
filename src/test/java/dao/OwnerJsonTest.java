package dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import domain.Owner;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OwnerJsonTest {

    private final ObjectMapper _mapper = new ObjectMapper();

    @Test
    void toJson_validOwner_roundTripEqualsOriginal() throws Exception {
        Owner original = new Owner(1, "John", "Doe", 30, "123 Main St", "0871234567", "john@example.com", null, null, 0, null);

        String json = _mapper.writeValueAsString(original);
        Owner deserialised = _mapper.readValue(json, Owner.class);

        assertEquals(original.getId(), deserialised.getId());
        assertEquals(original.getFirstName(), deserialised.getFirstName());
        assertEquals(original.getLastName(), deserialised.getLastName());
        assertEquals(original.getAge(), deserialised.getAge());
        assertEquals(original.getAddress(), deserialised.getAddress());
        assertEquals(original.getPhone(), deserialised.getPhone());
        assertEquals(original.getEmail(), deserialised.getEmail());
    }
}