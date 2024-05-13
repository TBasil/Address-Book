/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package addressbook;

    
public class Person extends Addressbook{
        private String name;
        private String address;
        private int phone;

        public Person(String name, String address, int phone) {
            this.name = name;
            this.address = address;
            this.phone = phone;
        }

        public String getName() {
            return name;
        }

        public String getAddress() {
            return address;
        }

        public int getPhone() {
            return phone;
        }
    }
