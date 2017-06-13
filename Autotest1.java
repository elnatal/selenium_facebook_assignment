/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package autotest1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;


/**
 *
 * @author elnatal
 */
public class Autotest1 {
    private static List<WebElement> friends;
    private static ArrayList<String> friendsList = new ArrayList<String> (Arrays.asList("Friends"));

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        
       Scanner reader = new Scanner(System.in);
       System.out.println("Email: ");
       String email = reader.next(); 
       System.out.println("Password: ");
       String password = reader.next();

        method4(email, password);
    }

    public static void method1(){
        System.setProperty("webdriver.chrome.driver", "C://chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        
        driver.get("https://www.google.com");
        
        System.out.println("page title: " + driver.getTitle());
        System.out.println("page url: " + driver.getCurrentUrl());
        System.out.println("length of the page source code: " + driver.getPageSource().toString().length());
        
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
        }
        
        driver.close();
    }
    
    public static void method2(){
        System.setProperty("webdriver.chrome.driver", "C://chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        
        driver.navigate().to("https://www.yahoo.com");
        
        try {
            Thread.sleep(2000);
        } catch (Exception e) {
        }
        
        driver.navigate().to("https://www.google.com");
        
        driver.navigate().refresh();
        driver.navigate().back();
        driver.navigate().forward();
        
        driver.close();
    }
    
    public static void method3() {
        System.setProperty("webdriver.chrome.driver", "C://chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        
        driver.navigate().to("https://www.google.com");
        
        WebElement searchBox = driver.findElement(By.id("lst-ib"));
        WebElement searchButton = driver.findElement(By.name("btnG"));
        searchBox.sendKeys("elnatal");
        
        searchButton.click();
        
        try {
            Thread.sleep(5000);
        } catch (Exception e) {
        }
        searchBox.clear();
        
        driver.close();
        
    }
    
    public static void method4(String Email, String Password) throws Exception {
        System.setProperty("webdriver.chrome.driver", "C://chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        
        driver.get("https://www.facebook.com");
        

        WebElement email = driver.findElement(By.id("email"));
        
        email.sendKeys(Email);
        WebElement password = driver.findElement(By.id("pass"));
        
        password.sendKeys(Password);
        driver.findElement(By.id("loginbutton")).click();
        
        driver.navigate().to("https://www.facebook.com/" + Email);
        
        try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                
            }
        driver.findElement(By.xpath("//div[@id='fbTimelineHeadline']/div[2]/div/div/a[3]")).click();
        
        try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                
            }
        
        WebElement Friends = driver.findElement(By.xpath("//*[@id=\"u_0_q\"]/div/a[3]/span[1]"));
        String numFriendesText = Friends.getText().toString();
        System.out.println("number of friends " + numFriendesText);
        
        int numFriends = Integer.parseInt(numFriendesText);

        int scroll = numFriends/20;
        
        for(int i = 0; i < scroll; i++){
            jse.executeScript("scroll(0, 10000000000);"); 
        try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                
            }

            Autotest1.friends = (driver.findElements(By.xpath("//div[@class='fsl fwb fcb']/a")));
            
            System.out.println("number of friends " + Autotest1.friends.size());
        }
        
        System.out.println("friends end");
        System.out.println("number of friends " + numFriends);
        System.out.println("number of friends " + Autotest1.friends.size());
        

        for (int i = 0; i < Autotest1.friends.size(); i++)  {
            System.out.println(Autotest1.friends.get(i).getText().toString());            
            friendsList.add(Autotest1.friends.get(i).getText().toString());
        }
        try {
            writeExcel(friendsList);
        } catch (Exception e) {
            
        }
    }
    
    
    public static void writeExcel(List names) throws IOException{
        HSSFWorkbook excel = new HSSFWorkbook();
        HSSFSheet list = excel.createSheet("Friends");
        
        for (int i=0; i < names.size(); i++) {
            Row r = list.createRow(i);
            r.createCell(0).setCellValue(names.get(i).toString());
         }
         
        try (FileOutputStream outputStream = new FileOutputStream("friends.xlsx")) {
            excel.write(outputStream);
        }
    }
    
}