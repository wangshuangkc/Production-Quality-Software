package addressbooklib;

import java.io.*;
import java.util.*;

/**
 * AddressBook class
 * Stores Entries consisting of 
 * contact name, telephone number, postal address, email address and a note, 
 * and Allows adding, removing, searching an Entry
 * as well as importing and exporting address book files
 * @author Shuang
 */
public class AddressBook {
  private Set<AddressEntry> addressBook;
  
  public AddressBook() {
	  addressBook = new HashSet<AddressEntry>();
  }
  
  /**
   * Adds new Address Entry into the address book
   * @param entry: an instance of AddressEntry
   * @return true if the new entry is successfully added, 
   *         false if unsuccessfully or new entry is null
   */
  public boolean addEntry(AddressEntry entry) {
    if (entry == null) {
      return false;
    }
    
    return addressBook.add(entry);
  }
  
  /**
   * Removes an entry from the address book (after finding the entry in the address book)
   * @param entry: an AddressEntry instance
   * @return true if removal is successfully done, false otherwise
   */
  public boolean removeEntry(AddressEntry entry) {
    return addressBook.remove(entry);
  }
  
  /**
   * Searches a list of entries by using a given String as keyword
   * @param key: a String indicates part of an AddressEntry information
   * @return a List of AddressEntry
   */
  public List<AddressEntry> searchEntry(String key) {
    List<AddressEntry> searchResult = new ArrayList<AddressEntry>();
    String key_lowerCase = key.toLowerCase();
    for (AddressEntry e: addressBook) {
      if (e.getContactName().toLowerCase().contains(key_lowerCase)) {
        searchResult.add(e);
      }
      if (e.getPostalAddress().toLowerCase().contains(key_lowerCase)) {
        searchResult.add(e);
      }
      if (e.getPhoneNumber().contains(key)) {
        searchResult.add(e);
      }
      if (e.getEmailAddress().toLowerCase().contains(key_lowerCase)) {
        searchResult.add(e);
      }
      if (e.getNote().toLowerCase().contains(key_lowerCase)) {
        searchResult.add(e);
      }
    }
    
    return searchResult;
  }
  
  /**
   * Saves the current AddressBook to a file
   * @param filePath: a String indicating the expected output file path
   *        the file path may be invalid, the user of this method needs to figure this out
   *        by adding new path or callCreateNewFile()
   * @return true if data is successfully write to the file, false otherwise
   * The algorithm is inspired by https://processing.org/reference/PrintWriter.html
   */
  public boolean exportAddressBook(String filePath) throws IOException {
    File output = new File(filePath);
    PrintWriter pw = new PrintWriter(output);
    for (AddressEntry e: addressBook) {
      pw.println(e.toString());
    }
    pw.close();
    
    return true;
  }
  
  /**
   * Uploads an existing address book file, and forms a new addressBook
   * @param filePath: a String indicating the input file path
   * @return true if the new addressBook is uploaded, false otherwise
   */
  public boolean importAddressBook(String filePath) throws IOException {
    @SuppressWarnings("resource")
    Scanner scanner = new Scanner(new File(filePath));
    addressBook = new HashSet<AddressEntry>();
    
    String line;
    String[] tokens;
    while(scanner.hasNextLine()) {
      line = scanner.nextLine();
      tokens = line.split("\\t");
      AddressEntry entry = new AddressEntry.Builder().
          contactName(tokens[0]).
          postalAddress(tokens[1]).
          phoneNumber(tokens[2]).
          emailAddress(tokens[3]).
          note(tokens[4]).
          build();
      addressBook.add(entry);
    }
    
    return true;
  }
}
