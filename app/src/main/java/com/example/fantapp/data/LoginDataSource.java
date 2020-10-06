package com.example.fantapp.data;

import com.example.fantapp.data.model.LoggedInUser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    public Result<LoggedInUser> login(String username, String password) {
        HttpURLConnection c = null;
        try{
            URL url = new URL("http://169.254.8.28:8080/WebappTwo/api/auth/login?uid=" + username + "&pwd=" + password);
            c = (HttpURLConnection) url.openConnection();
            c.setUseCaches(true);
            c.setRequestMethod("GET");

            if(c.getResponseCode() == HttpURLConnection.HTTP_OK){
                BufferedReader br = new BufferedReader(new InputStreamReader(c.getInputStream(), "UTF-8"));
                String token = br.readLine();
                System.out.println(token); //perhaps this should be written somewhere readable.. idk yet how it works. na it works pretty nice ngl
                LoggedInUser fakeUser = new LoggedInUser(username, token);
                c.getInputStream().close();
                return new Result.Success<>(fakeUser);
            }
            return new Result.Error(new IOException("Error logging in " + c.getResponseMessage()));
        } catch (Exception e){
            System.err.println("Failed to call " + e); //maybe gotta change this too
            return new Result.Error(new IOException("Error logging in", e));
        } finally{
            if(c != null) c.disconnect();
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}