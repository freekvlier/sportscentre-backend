package com.service.user.dto;

import com.google.gson.Gson;
import com.service.user.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    public String oid;
    public String name;

    public UserRequest(String token) throws UnsupportedEncodingException {
        String[] pieces = token.substring(6).split("\\.");
        String b64payload = pieces[1];
        String jsonString = new String(Base64.decodeBase64(b64payload), "UTF-8");
        UserRequest test = new Gson().fromJson(jsonString, UserRequest.class);
        this.oid = test.getOid();
        this.name = test.getName();
    }
}
