package com.adobe.aem.support.core.email;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import com.adobe.aem.support.core.email.exceptions.EmailException;
import com.day.cq.mailer.MessageGateway;
import com.day.cq.mailer.MessageGatewayService;

public class TestSimpleEmailService {

    SimpleEmailService simpleEmailService;

    @BeforeEach
    public void setup() {
        simpleEmailService = new SimpleEmailService();
        MessageGatewayService messageGatewayServiceMock = Mockito.mock(MessageGatewayService.class);
        MessageGateway messageGatewayMock = Mockito.mock(MessageGateway.class);

        Mockito.when(messageGatewayServiceMock.getGateway(Mockito.any())).thenReturn(messageGatewayMock);
        doNothing().when(messageGatewayMock).send(Mockito.any());
        
        simpleEmailService.messageGatewayService = messageGatewayServiceMock;
    }

    @Test
    public void validateNecessaryFields() throws EmailException {
        simpleEmailService.sendEmail(Arrays.asList("test@test.com"), Arrays.asList("test@test.com"), "Test name", "someemail@email.com", "YOLO!");
    }

    @Test
    public void validateNecessaryFieldsMissingFields() throws EmailException {
        assertThrows(EmailException.class, () -> {
            simpleEmailService.sendEmail(null, Arrays.asList("test@test.com"), "Test name", "someemail@email.com", "YOLO!");
        });
    }
    
}
