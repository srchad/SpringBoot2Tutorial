package com.example.myproject;

import jdk.nashorn.internal.objects.annotations.Getter;

import javax.persistence.*;

@Entity
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mbrseq")
    @SequenceGenerator(sequenceName = "mbrseq", allocationSize = 1, name = "mbreq")
    Long mbrseq;

    String logonid;

    @Column(name = "REGDTTM")
    String regdttm;

    public Long getMbrseq() {
        return mbrseq;
    }

    public void setMbrseq(Long mbrseq) {
        this.mbrseq = mbrseq;
    }

    public String getLogonid() {
        return logonid;
    }

    public void setLogonid(String logonid) {
        this.logonid = logonid;
    }

    public String getRegdttm() {
        return regdttm;
    }

    public void setRegdttm(String regdttm) {
        this.regdttm = regdttm;
    }
}
