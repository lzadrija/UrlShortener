package com.lzadrija.account;

import java.util.Objects;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "account")
public class Account {

    @Id
    private String id;
    @Column(nullable = false)
    private String password;

    private Account() {
    }

    public Account(String id, String password) {
        this.id = id;
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        return (obj != null) && (getClass() == obj.getClass()) && Objects.equals(this.id, ((Account) obj).id);
    }

    /**
     * Returns a representation of this account. The exact details of the
     * representation are subject to change, but the following may be regarded
     * as typical: "Account: [Id = Bob!1986, Password = g4R68Th4]"
     *
     * @return representation of this account
     */
    @Override
    public String toString() {
        return String.format("Account: [Id = %s Password = %s]", id, password);
    }
}
