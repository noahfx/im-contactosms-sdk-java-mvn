package com.interactuamovil.apps.contactosms.api.examples;

import com.interactuamovil.apps.contactosms.api.Accounts;
import com.interactuamovil.apps.contactosms.api.responses.AccountResponse;
import com.interactuamovil.apps.contactosms.api.responses.Response;



import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import org.apache.commons.configuration.Configuration;



public class AccountsExample extends BaseExample {

    protected AccountsExample(String _apiKey, String _apiSecretKey, String _apiUri, Configuration _config) {
        super(_apiKey, _apiSecretKey, _apiUri, _config);
    }

    @Override
    public void configure() {

    }

    @Override
    public void test() throws IOException, InvalidKeyException, NoSuchAlgorithmException {

        Accounts accountsApi = new Accounts(
            getApiKey(),
            getApiSecretKey(),
            getApiUri()
        );

        // Get account package information
        Response<AccountResponse> accountResponse = accountsApi.getStatus();

        if (accountResponse.hasError()) {
            throw new AssertionError(
                "Could not get account status: "
                + accountResponse.getErrorMessage()
            );
        }

    }

}
