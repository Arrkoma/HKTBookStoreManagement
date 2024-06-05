/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookstoremanagement;

public class Author {
    private String authorID;
    private String name;

    public Author(String authorID, String name) {
        this.authorID = authorID;
        this.name = name;
    }

    public String getAuthorID() {
        return authorID;
    }

    public String getName() {
        return name;
    }
    
    public void setAuthorID(String authorID){
        this.authorID = authorID;
    }
    
    public void setName(String name){
        this.name = name;
    }
}

