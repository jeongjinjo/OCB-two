package com.green.onezo.enum_column;

public enum Role {
    USER,ADMIN,STORE;
    public static Role fromString(String userRole){
        for( Role role : Role.values()){
            if(role.toString().equalsIgnoreCase(userRole)){
                return role;
            }
        }
        // 여기까지 오면 USER도 아니도 ADMIN 도 아닌 이상한 userRole
        return null;
    }
}
