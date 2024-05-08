package com.green.onezo.member;

import com.fasterxml.jackson.annotation.JsonIgnore;


import com.green.onezo.enum_column.ResignYn;
import com.green.onezo.enum_column.Role;
import com.green.onezo.pay.Pay;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "member")
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, unique = true)
    private String userId;

    //@JsonIgnore
    private String password;

    private String name;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false, unique = true)
    private String phone;

    @Enumerated(EnumType.STRING)
    private ResignYn resignYn;

    @OneToMany(mappedBy = "member")
    private List<Pay> pays = new ArrayList<>();

    public void addPay(Pay pay) {
        pays.add(pay);
        pay.setMember(this);
    }

    public Member(CustomUserDetails customUserDetails) {
        this.userId = customUserDetails.getUserId();
        this.password =  customUserDetails.getPassword();
    }

}
