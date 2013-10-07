package com.interactuamovil.apps.contactosms.api.examples;


import com.interactuamovil.apps.contactosms.api.Contacts;
import com.interactuamovil.apps.contactosms.api.Groups;
import com.interactuamovil.apps.contactosms.api.responses.ActionMessageResponse;
import com.interactuamovil.apps.contactosms.api.responses.ContactResponse;
import com.interactuamovil.apps.contactosms.api.responses.GroupResponse;
import com.interactuamovil.apps.contactosms.api.responses.ListResponse;
import com.interactuamovil.apps.contactosms.api.responses.Response;
import org.apache.commons.configuration.Configuration;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

public class GroupsExample extends BaseExample {

    private String testGroupName = null;
    private String testGroupDescription = null;
    private String testGroupSmsShortName = null;
    private String testContactMsisdn = null;

    public GroupsExample(String _apiKey, String _apiSecretKey, String _apiUri, Configuration _config) {
        super(_apiKey, _apiSecretKey, _apiUri, _config);
    }

    @Override
    public void configure() {

        testGroupName = getConfig().getString("test_group_name");
        testGroupDescription = getConfig().getString("test_group_description");
        testGroupSmsShortName = getConfig().getString("test_group_sms_short_name");
        testContactMsisdn = getConfig().getString("test_contact_msisdn");

        if (null == testGroupName
            || null == testGroupDescription
            || null == testGroupSmsShortName) {
            throw new AssertionError(
                "Please add group configurations: test_group_name,"
                    + " test_group_description, test_group_sms_short_name."
            );
        }

    }

    @Override
    public void test() throws IOException, InvalidKeyException, NoSuchAlgorithmException {

        Groups groupsApi = new Groups(
            getApiKey(),
            getApiSecretKey(),
            getApiUri()
        );

        // Add a group and check it was really created
        testAddingGroup(groupsApi);

        // Get list of groups
        testGroupsList(groupsApi);

        // Add contact to group and test it was really added
        testAddingContactToGroup(groupsApi);

        // Update group and check it was really updated
        testUpdatingGroup(groupsApi);

        // Delete group and check it was really updated
        testDeletingGroup(groupsApi);

    }

    private void testAddingContactToGroup(Groups groupsApi) throws IOException, InvalidKeyException, NoSuchAlgorithmException {

        Contacts contactsApi = new Contacts(getApiKey(), getApiSecretKey(), getApiUri());

        Response<GroupResponse> group = groupsApi.get(testGroupSmsShortName);
        Response<ContactResponse> contact = contactsApi.getByMsisdn(testContactMsisdn);

        groupsApi.addContact(
            group.getResult().getShortName(),
            contact.getResult().getMsisdn()
        );

    }

    private void testDeletingGroup(Groups groupsApi) {

        groupsApi.delete(testGroupSmsShortName);

        Response<GroupResponse> group = groupsApi.get(testGroupSmsShortName);

        if (null != group.getResult()) {
            throw new AssertionError("Could not delete group.");
        }

    }

    private void testUpdatingGroup(Groups groupsApi) {

        groupsApi.update(
            testGroupSmsShortName,
            testGroupName,
            testGroupDescription,
            testGroupSmsShortName + "test"
        );

        Response<GroupResponse> group = groupsApi.get(testGroupSmsShortName);
        GroupResponse result = group.getResult();
        String shortName = result.getShortName();

        if (!shortName.equalsIgnoreCase(testGroupSmsShortName + "test")) {
            throw new AssertionError("Could not update group.");
        }

    }

    private void testGroupsList(Groups groupsApi) {

        ListResponse<GroupResponse> groups = groupsApi.getList();

        Boolean found = Boolean.FALSE;

        for (GroupResponse group : groups.getResult()) {
            if (group.getShortName().equalsIgnoreCase(testGroupSmsShortName)) {
                found = Boolean.TRUE;
            }
        }

        if (!found) {
            throw new AssertionError("Group not found on list.");
        }

    }

    private void testAddingGroup(Groups groupsApi) {

        Response<ActionMessageResponse> response = groupsApi.add(
            testGroupSmsShortName,
            testGroupName,
            testGroupDescription
        );

        if (response.hasError()) {
            throw new AssertionError("Could not add group.");
        }

    }

}
