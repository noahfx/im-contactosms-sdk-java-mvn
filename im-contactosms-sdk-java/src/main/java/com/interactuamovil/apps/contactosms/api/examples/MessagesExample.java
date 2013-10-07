package com.interactuamovil.apps.contactosms.api.examples;


import com.interactuamovil.apps.contactosms.api.Messages;
import com.interactuamovil.apps.contactosms.api.responses.MessageToGroupResponse;
import com.interactuamovil.apps.contactosms.api.responses.Response;
import org.apache.commons.configuration.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@SuppressWarnings("ChainedMethodCall")
public class MessagesExample extends BaseExample {

    private String testMessage = null;
    private String testContactMsisdn = null;
    private String testGroupSmsShortName = null;

    protected MessagesExample(String _apiKey, String _apiSecretKey, String _apiUri, Configuration _config) {
        super(_apiKey, _apiSecretKey, _apiUri, _config);
    }

    @Override
    public void configure() {

        testMessage = getConfig().getString("test_message");
        testContactMsisdn = getConfig().getString("test_contact_msisdn");
        testGroupSmsShortName = getConfig().getString("test_group_sms_short_name");

        if (null == testMessage) {
            throw new AssertionError("Add test_message configuration.");
        }

        if (null == testContactMsisdn) {
            throw new AssertionError("Add test_contact_msisdn configuration.");
        }

        if (null == testGroupSmsShortName) {
            throw new AssertionError("Add test_group_sms_short_name configuration.");
        }

    }

    @Override
    public void test() throws IOException, InvalidKeyException, NoSuchAlgorithmException {

        Messages messagesApi = new Messages(
            getApiKey(),
            getApiSecretKey(),
            getApiUri()
        );

        // Test send message to group
        testSendToGroup(messagesApi);

        // Test send message to single contact
        testSendToContact(messagesApi);

        // Test adding scheduled message
        testAddingScheduledMessage(messagesApi);

        // Test inbox messages
        testInboxMessages(messagesApi);

    }

    private void testInboxMessages(Messages messagesApi) {

        //messagesApi.inbox();

    }

    private void testAddingScheduledMessage(Messages messagesApi) {

        //messagesApi.addSchedule();

    }

    private void testSendToContact(Messages messagesApi) {

        Response<MessageToGroupResponse> response = messagesApi.sendToContact(
            testContactMsisdn, testMessage);

        if (response.hasError()) {
            throw new AssertionError("Error sending message to contact: "
                + response.getErrorMessage());
        }

    }

    private void testSendToGroup(Messages messagesApi) {

        Response<MessageToGroupResponse> response = messagesApi.sendToGroups(
            new String[]{testGroupSmsShortName},
            testMessage
        );

        if (response.hasError()) {
            throw new AssertionError("Error sending message to group: "
                + response.getErrorMessage());
        }

    }

}
