package com.green.onezo.review;


import com.green.onezo.enum_column.ResignYn;
import com.green.onezo.member.Member;
import com.green.onezo.store.Store;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "review", timeToLive = 1) // 1초 후 자동 삭제
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RedisReview {

    @Id
    private String id;  // Redis에서는 기본적으로 String ID 사용
    private String comment;
    private int star;
}
