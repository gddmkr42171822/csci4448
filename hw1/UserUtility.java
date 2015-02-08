/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package part2_section1_driver;

/**
 *
 * 
 */
public class UserUtility {
	public static String createUsername(String firstname, String lastname) {
		String first3 = firstname.substring(0, 3);
		String last3 = lastname.substring(0, 3);
		String username = first3.concat(last3);
		return username;
	}
}
