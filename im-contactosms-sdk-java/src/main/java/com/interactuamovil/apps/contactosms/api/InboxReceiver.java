/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interactuamovil.apps.contactosms.api;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interactuamovil.apps.contactosms.api.responses.InboxMessage;

import java.io.IOException;

/**
 *
 * @author sergeiw
 */
public class InboxReceiver {
    
    private static final ObjectMapper mapper = new ObjectMapper();
    
    public static InboxMessage parseInboxJson(String inboxJson) throws JsonMappingException, JsonParseException, IOException {
        InboxMessage messageResponse = mapper.readValue(inboxJson, InboxMessage.class);
        return messageResponse;        
    }
     
    
    
    
}
