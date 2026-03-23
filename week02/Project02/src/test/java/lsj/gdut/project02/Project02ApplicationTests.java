package lsj.gdut.project02;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class Project02ApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    // 【生成】
    public void createToken() {
        Map<String,Object> map = new HashMap<>();
        map.put("username","admin");
        map.put("password","admin");
        System.out.println(
                JWT.create()
                        .withClaim("user", map)//添加载荷
                        .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*12))//添过期时间
                        .sign(Algorithm.HMAC256("itheima"))//指定算法,配置秘钥
        );
    }

    @Test
    public void verifyToken() {
        String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
                "eyJ1c2VyIjp7InBhc3N3b3JkIjoiYWRtaW4iLCJ1c2VybmFtZSI6ImFkbWluIn0sImV4cCI6MTc3NDMxMzE5NH0." +
                "OORFACpze_RTkDXpDMIwNKAIeZyH_7HM8wihJfSKo-A";
        JWTVerifier jwtverifier = JWT.require(Algorithm.HMAC256("itheima")).build();

        DecodedJWT decodedJWT = jwtverifier.verify(token);
        Map<String, Claim> map = decodedJWT.getClaims();
        System.out.println(map);
        System.out.println(map.get("user"));
    }

}
