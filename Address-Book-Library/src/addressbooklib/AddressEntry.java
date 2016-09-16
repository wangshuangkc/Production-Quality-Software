package addressbooklib;

//import java.util.*;

/**
 * Entry class
 * Stores information for an individual entry including
 * contact name, telephone number, postal address, email address and a note
 * as well as getter and setter for each field
 * Also overrides equals, hashCode, toString methods
 * @author Shuang
 */
public class AddressEntry implements Comparable<AddressEntry> {
  private String contactName = " ";
  private String postalAddress = " ";
  private String phoneNumber = " ";
  private String emailAddress = " ";
  private String note = " ";
  
  /**
   * Builder class
   * Sets multiple optional parameters of interest for the Entry
   * @author Shuang
   */
  public static class Builder {
    // Optional parameters
    private String contactName;
    private String postalAddress;
    private String phoneNumber;
    private String emailAddress;
    private String note;
    
    /**
     * Initializes contact name with a given String
     * @param nameVal: a String for initializing the name
     * @return an instance of this Builder
     */
    public Builder contactName(String nameVal) {
      contactName = nameVal;
      return this;
    }
    
    /**
     * Initializes postal address with a given String
     * @param addressVal: a String for initializing the address
     * @return an instance of this Builder
     */
    public Builder postalAddress(String addressVal) {
      postalAddress = addressVal;
      return this;
    }
    
    /**
     * Initializes phone number with a given String
     * @param numberVal: a String for initializing the number
     * @return an instance of this Builder
     */
    public Builder phoneNumber(String numberVal) {
      if (numberVal.matches("[0-9]{7,}")) {
        phoneNumber = numberVal;
      }
      else {
        throw new IllegalArgumentException("Invalid phone number: " + numberVal);
      }
      return this;
    }
    
    /**
     * Initializes email address with a given String
     * @param emailVal: a String for initializing the email
     * @return an instance of this Builder
     */
    public Builder emailAddress(String emailVal) {
      if (emailVal.matches("^(.+)@[0-9A-Za-z]+[/.][0-9A-Za-z]+$")) {
        emailAddress = emailVal;
      }
      else {
        throw new IllegalArgumentException("Invalid email address: " + emailVal);
      }
      return this;
    }
    
    /**
     * Initializing note with a given String
     * @param noteVal: a String for initializing the note
     * @return an instance of this Builder
     */
    public Builder note(String noteVal) {
      note = noteVal;
      return this;
    }
    
    /**
     * Creates an instance of Entry Object
     * @return an instance of Entry Object
     */
    public AddressEntry build() {
      return new AddressEntry(this);
    }
  }
  
  /**
   * Constructor
   * @param builder: an instance of Builder
   */
  private AddressEntry(Builder builder) {
    contactName = builder.contactName;
    postalAddress = builder.postalAddress;
    phoneNumber = builder.phoneNumber;
    emailAddress = builder.emailAddress;
    note = builder.note;
  }
  
  public String getContactName() {
    return contactName;
  }
  
  /**
   * Changes the contact name of this Entry
   * @param nameInput: this Entry's new name
   */
  public void setContactName(String nameInput) {
    contactName = nameInput;
  }
  
  public String getPostalAddress() {
    return postalAddress;
  }

  /**
   * Changes the postal address of this Entry
   * @param addressInput: this Entry's new address
   */
  public void setPostalAddress(String addressInput) {
    postalAddress = addressInput;
  }
  
  public String getPhoneNumber() {
    return phoneNumber;
  }

  /**
   * Changes the phone number of this Entry
   * @param numberInput: this Entry's new number
   */
  public void setPhoneNumber(String numberInput) {
    if (numberInput.matches("[0-9]{7,}")) {
      phoneNumber = numberInput;
    }
    else {
      throw new IllegalArgumentException("Invalid phone number: " + numberInput);
    }
  }
   
  /**
   * Gets the email address of this Entry
   * @return this Entry's email address
   */
  public String getEmailAddress() {
    return emailAddress;
  }

  /**
   * Changes the email address of this Entry
   * @param emailInput: this Entry's new email
   */
  public void setEmailAddress(String emailInput) {
    if (emailInput.matches("^(.+)@[0-9A-Za-z]+[/.][0-9A-Za-z]+$")) {
      emailAddress = emailInput;
    }
    else {
      throw new IllegalArgumentException("Invalid email address: " + emailInput);
    }
  }
  
  /**
   * Gets the note of this Entry
   * @return this Entry's note
   */
  public String getNote() {
    return note;
  }

  /**
   * Changes the note of this Entry
   * @param noteInput: this Entry's new note
   */
  public void setNote(String noteInput) {
    note = noteInput;
  }
  
  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof AddressEntry)) {
      return false;
    }
    
    AddressEntry e = (AddressEntry) o;
    return e.contactName.equals(contactName) 
        && e.postalAddress.equals(postalAddress)
        && e.phoneNumber.equals(phoneNumber)
        && e.emailAddress.equals(emailAddress)
        && e.note.equals(note);
  }
  
  @Override
  public int hashCode() {
    int result = 1;
    result = result * 31 + ((contactName == null) ? 0 : contactName.hashCode());
    result = result * 31 + ((postalAddress == null) ? 0 : postalAddress.hashCode());
    result = result * 31 + ((phoneNumber == null) ? 0 : phoneNumber.hashCode());
    result = result * 31 + ((emailAddress == null) ? 0 : emailAddress.hashCode());
    result = result * 31 + ((note == null) ? 0 : note.hashCode());
    return result;
  }
  
  @Override
  public String toString() {
    return "Name: " + getContactName() + "\n"
        + "Address: " + getPostalAddress() + "\n"
        + "Phone: " + getPhoneNumber() + "\n"
        + "Email: " + getEmailAddress() + "\n"
        + "Note: " + getNote() + "\n";
  }

  @Override
  public int compareTo(AddressEntry entry) {
    if (contactName.equalsIgnoreCase(entry.getContactName())) {
      if (postalAddress.equalsIgnoreCase(entry.getPostalAddress())) {
        if (phoneNumber.equals(entry.getPhoneNumber())) {
          if (emailAddress.equalsIgnoreCase(entry.getEmailAddress())) {
            return note.compareTo(entry.getNote());
            }
            return emailAddress.compareTo(entry.getEmailAddress());
          }
        return phoneNumber.compareTo(entry.getPhoneNumber());
      }
      return postalAddress.compareTo(entry.getPostalAddress());
    }
    return contactName.compareTo(entry.getContactName());
  }
}
