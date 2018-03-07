package com.isa;

import com.isa.usersengine.dao.UsersRepositoryDaoRemote;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.util.Hashtable;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        Hashtable<String, String> properties = new Hashtable<String, String>();
        properties.put(Context.INITIAL_CONTEXT_FACTORY, "org.jboss.naming.remote.client.InitialContextFactory");
        properties.put("jboss.naming.client.ejb.context", "true");
        properties.put(Context.PROVIDER_URL, "http-remoting://localhost:8080");
        properties.put(Context.SECURITY_PRINCIPAL, "rami");
        properties.put(Context.SECURITY_CREDENTIALS, "rami");
        Context context = null;
        try {
            context = new InitialContext(properties);
        } catch (NamingException e) {
            e.printStackTrace();
        }

        try {
            UsersRepositoryDaoRemote usersRepositoryDaoRemote = (UsersRepositoryDaoRemote) context.lookup("users-engine/UsersRepositoryDaoBean!com.isa.usersengine.dao.UsersRepositoryDaoRemote");
            for (String name : usersRepositoryDaoRemote.getUsersNames()) {
                System.out.println(name);
            }
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }
}
