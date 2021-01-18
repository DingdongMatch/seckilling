package com.seeling.seckilling.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author Match Fu
 * @date 2020/3/21 9:15 下午
 */
@Data
public class User {
    private Long id;
    private String nickname;
    private String password;
    private String salt;
    private String head;
    private Date registerDate;
    private Date lastLoginDate;
    private Integer loginCount;
}
