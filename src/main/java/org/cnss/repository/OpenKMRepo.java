package org.cnss.repository;

import org.cnss.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.Authenticator;
import java.net.HttpURLConnection;
import java.net.PasswordAuthentication;
import java.net.URL;

@Service
public class OpenKMRepo {

    @Autowired
    private Config config;

    public HttpURLConnection template(String appendUrl, String method) {
        try {
            String url = config.getOpenKMServer();
            url = url + appendUrl;
            String userName = config.getOpenKMUserName();
            String password = config.getOpenKMPassword();

            Authenticator.setDefault(new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(userName, password.toCharArray());
                }
            });
            // Set up the HTTP connection
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod(method);
            return con;
        }
        catch (Exception e){
            return null;
        }
    }
}
