package com.seeling.seckilling.domain;

import lombok.Data;

import java.util.Date;

/**
 * @author Match Fu
 * @date 2020/6/13 11:11 上午
 */
@Data
public class MiaoshaUser {
    private Long id;
    private String nickname;
    private String password;
    private String salt;
    private String head;
    private Date registerDate;
    private Date lastLoginDate;
    private Integer loginCount;
}
