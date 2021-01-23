package ca.onepoint.yul.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "avatar")
@Data
public class Avatar {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column
    private String name;
    @Column
    private Integer type;
    @Column
    private String image;
    @Column
    private Integer waiting;
    @Column
    private Integer main;
    @Column
    private Integer x;
    @Column
    private Integer y;
    @Column
    private Integer xdest;
    @Column
    private Integer ydest;
}
