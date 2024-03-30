package com.dnz.lojaweb.utils;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Component;

@Component
public class EmailCode {
    private Map<String, String> verifCodes = new ConcurrentHashMap<>();
    
    public void genCode(String email){
        String foundCode = verifCodes.get(email);
        
        if(foundCode != null){
            String code = "";
            Random rand = new Random();

            for(int i = 0; i < 6; i++){
                code += String.valueOf(rand.nextInt(10));
            }
            System.out.println(code);
            verifCodes.put(email, code);
        }
    }
    
    public boolean verifyCode(String email, String code){
        String foundCode = verifCodes.get(email);
        
        if(foundCode != null && foundCode.equals(code)){
            verifCodes.remove(email);
            return true;
        }else{
            return false;
        }
    }
}
