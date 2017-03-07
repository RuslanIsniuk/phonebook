package com.phonebook.model.dao.impl;

import com.phonebook.entities.Client;
import com.phonebook.model.dao.ClientDAO;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.dao.EmptyResultDataAccessException;

import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Руслан on 07.03.2017.
 */
public class JDBCClientTest {
    private ClientDAO clientDAO = new JDBCClient();
    private static Client client = new Client();
    private static Client client2 = new Client();

    @BeforeClass
    public static void setClientData()throws Exception{
        client.setClientLogin("testLogin");
        client.setClientPass("testPass");
        client.setClientFullName("testFullName");

        client2.setClientLogin("testLogin2");
        client2.setClientPass("testPass2");
        client2.setClientFullName("testFullName2");
    }

    @Test
    public void testRead(){
        clientDAO.create(client);
        Client client1 = clientDAO.read(client.getClientLogin(),client.getClientPass());
        clientDAO.delete(client.getClientLogin());

        assertEquals(client,client1);

    }

    @Test
    public void testReadAll(){
        clientDAO.create(client);
        clientDAO.create(client2);

        List<Client> clientList = clientDAO.readAll();

        clientDAO.delete(client.getClientLogin());
        clientDAO.delete(client2.getClientLogin());

        assertEquals(client,clientList.get(0));
        assertEquals(client2,clientList.get(1));
    }

    @Test
    public void testCreate(){
        Client client1;

        clientDAO.create(client);
        client1 = clientDAO.read(client.getClientLogin(),client.getClientPass());
        clientDAO.delete(client.getClientLogin());

        assertEquals(client,client1);
    }

    @Test
    public void testDelete(){
        Client client1;
        Client client2 = new Client();

        clientDAO.create(client);
        clientDAO.delete(client.getClientLogin());

        try{
            client1 = clientDAO.read(client.getClientLogin(),client.getClientPass());
        }catch (EmptyResultDataAccessException erdae){
            client1 = new Client();
        }
        assertEquals(client1,client2);
    }
}