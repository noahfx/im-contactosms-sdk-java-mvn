/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.interactuamovil.apps.contactosms.api;

/**
 *
 * @author sergeiw
 */
public interface InboxMessageCallback {
    
    public void handleInboxMessage(String inboxJsonString);
    
}
