package se.lexicon;

import java.util.Objects;

public class Contact {
    String name;
    String telephoneNumber;


    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setTelephoneNumber(String telephoneNumber){
        this.telephoneNumber = telephoneNumber;
    }

    public String getTelephoneNumber(){
        return this.telephoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Contact contact = (Contact) o;
        return Objects.equals(name, contact.name) && Objects.equals(telephoneNumber, contact.telephoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, telephoneNumber);
    }
}
