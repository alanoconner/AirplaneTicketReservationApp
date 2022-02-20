package com.example.application;


import com.vaadin.flow.component.UI;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Random;
//
//
// IN THIS CLASS I ADDED VALUES TO DB;
//
//

public class SetReadUIComponents extends UI {
    public void saveIntegerValue(SetReadUIComponents ui,String name, int value){ ui.getSession().setAttribute(name ,value);}
    public void saveCharSetValue(SetReadUIComponents ui,String name,  String value){ui.getSession().setAttribute(name,value);}

    public static void main(String[] args) throws SQLException {
        Connection dbcon = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/airWaysWebApp?allowPublicKeyRetrieval=true&useSSL=false&serverTimezone=UTC",
                "airwayuser", "alnukod1993");
         Statement statement=dbcon.createStatement();
         int id= 2158;

         Random random = new Random();
         String city = "Abu Dhabi\n" +
                 "Abuja\n" +
                 "Accra\n" +
                 "Adamstown\n" +
                 "Addis Ababa\n" +
                 "Algiers\n" +
                 "Alofi\n" +
                 "Amman\n" +
                 "Amsterdam\n" +
                 "Andorra la Vella\n" +
                 "Ankara\n" +
                 "Antananarivo\n" +
                 "Apia\n" +
                 "Ashgabat\n" +
                 "Asmara\n" +
                 "Astana\n" +
                 "Asuncion\n" +
                 "Athens\n" +
                 "Avarua\n" +
                 "Baghdad\n" +
                 "Baku\n" +
                 "Bamako\n" +
                 "Bandar Seri Begawan\n" +
                 "Bangkok\n" +
                 "Bangui\n" +
                 "Banjul\n" +
                 "Basseterre\n" +
                 "Beijing\n" +
                 "Beirut\n" +
                 "Belgrade\n" +
                 "Belmopan\n" +
                 "Berlin\n" +
                 "Bern\n" +
                 "Bishkek\n" +
                 "Bissau\n" +
                 "Bogota\n" +
                 "Brasilia\n" +
                 "Bratislava\n" +
                 "Brazzaville\n" +
                 "Bridgetown\n" +
                 "Brussels\n" +
                 "Bucharest\n" +
                 "Budapest\n" +
                 "Buenos Aires\n" +
                 "Bujumbura\n" +
                 "Cairo\n" +
                 "Canberra\n" +
                 "Caracas\n" +
                 "Castries\n" +
                 "Cayenne\n" +
                 "Charlotte Amalie\n" +
                 "Chisinau\n" +
                 "Cockburn Town\n" +
                 "Conakry\n" +
                 "Copenhagen\n" +
                 "Dakar\n" +
                 "Damascus\n" +
                 "Dhaka\n" +
                 "Dili\n" +
                 "Djibouti\n" +
                 "Dodoma\n" +
                 "Dar es Salaam\n" +
                 "Doha\n" +
                 "Douglas\n" +
                 "Dublin\n" +
                 "Dushanbe\n" +
                 "Edinburgh of the Seven Seas\n" +
                 "El Aaiun\n" +
                 "Episkopi Cantonment\n" +
                 "Flying Fish Cove\n" +
                 "Freetown\n" +
                 "Funafuti\n" +
                 "Gaborone\n" +
                 "George Town\n" +
                 "Georgetown\n" +
                 "Georgetown\n" +
                 "Gibraltar\n" +
                 "Grytviken\n" +
                 "Guatemala City\n" +
                 "Gustavia\n" +
                 "Hagatna\n" +
                 "Hamilton\n" +
                 "Hanga Roa\n" +
                 "Hanoi\n" +
                 "Harare\n" +
                 "Hargeisa\n" +
                 "Havana\n" +
                 "Helsinki\n" +
                 "Honiara\n" +
                 "Islamabad\n" +
                 "Jakarta\n" +
                 "Jamestown\n" +
                 "Jerusalem\n" +
                 "Jerusalem\n" +
                 "Ramallah and Gaza\n" +
                 "Juba\n" +
                 "Kabul\n" +
                 "Kampala\n" +
                 "Kathmandu\n" +
                 "Khartoum\n" +
                 "Kiev\n" +
                 "Kigali\n" +
                 "Kingston\n" +
                 "Kingston\n" +
                 "Kingstown\n" +
                 "Kinshasa\n" +
                 "Kuala Lumpur\n" +
                 "Putrajaya \n" +
                 "Kuwait City\n" +
                 "Libreville\n" +
                 "Lilongwe\n" +
                 "Lima\n" +
                 "Lisbon\n" +
                 "Ljubljana\n" +
                 "Lome\n" +
                 "London\n" +
                 "Luanda\n" +
                 "Lusaka\n" +
                 "Luxembourg\n" +
                 "Madrid\n" +
                 "Majuro\n" +
                 "Malabo\n" +
                 "Male\n" +
                 "Managua\n" +
                 "Manama\n" +
                 "Manila\n" +
                 "Maputo\n" +
                 "Marigot\n" +
                 "Maseru\n" +
                 "Mata-Utu\n" +
                 "Mbabane \n" +
                 "Lobamba \n" +
                 "Melekeok\n" +
                 "Ngerulmud\n" +
                 "Mexico City\n" +
                 "Minsk\n" +
                 "Mogadishu\n" +
                 "Monaco\n" +
                 "Monrovia\n" +
                 "Montevideo\n" +
                 "Moroni\n" +
                 "Moscow\n" +
                 "Muscat\n" +
                 "Nairobi\n" +
                 "Nassau\n" +
                 "Naypyidaw\n" +
                 "N Djamena\n" +
                 "New Delhi\n" +
                 "Niamey\n" +
                 "Nicosia\n" +
                 "Nicosia\n" +
                 "Nouakchott\n" +
                 "Noumea\n" +
                 "Nuku alofa\n" +
                 "Nuuk\n" +
                 "Oranjestad\n" +
                 "Oslo\n" +
                 "Ottawa\n" +
                 "Ouagadougou\n" +
                 "Pago Pago\n" +
                 "Palikir\n" +
                 "Panama City\n" +
                 "Papeete\n" +
                 "Paramaribo\n" +
                 "Paris\n" +
                 "Philipsburg\n" +
                 "Phnom Penh\n" +
                 "Plymouth \n" +
                 "Brades Estate\n" +
                 "Podgorica\n" +
                 "Cetinje\n" +
                 "Port Louis\n" +
                 "Port Moresby\n" +
                 "Port Vila\n" +
                 "Port au Prince\n" +
                 "Port of Spain\n" +
                 "Porto Novo\n" +
                 "Cotonou\n" +
                 "Prague\n" +
                 "Praia\n" +
                 "Pretoria\n" +
                 "Bloemfontein\n" +
                 "Cape Town\n" +
                 "Pristina\n" +
                 "Pyongyang\n" +
                 "Quito\n" +
                 "Rabat\n" +
                 "Reykjavik\n" +
                 "Riga\n" +
                 "Riyadh\n" +
                 "Road Town\n" +
                 "Rome\n" +
                 "Roseau\n" +
                 "Saipan\n" +
                 "San Jose\n" +
                 "San Juan\n" +
                 "San Marino\n" +
                 "San Salvador\n" +
                 "Sana a\n" +
                 "Santiago\n" +
                 "Valparaiso\n" +
                 "Santo Domingo\n" +
                 "Sao Tome\n" +
                 "Sarajevo\n" +
                 "Seoul\n" +
                 "Singapore\n" +
                 "Skopje\n" +
                 "Sofia\n" +
                 "Sri Jayawardenepura Kotte\n" +
                 "Colombo\n" +
                 "St. Georges\n" +
                 "St. Helier\n" +
                 "St. Johns\n" +
                 "St. Peter Port\n" +
                 "St. Pierre\n" +
                 "Stanley\n" +
                 "Stepanakert\n" +
                 "Stockholm\n" +
                 "Sucre \n" +
                 "La Paz \n" +
                 "Sukhumi\n" +
                 "Suva\n" +
                 "Taipei\n" +
                 "Tallinn\n" +
                 "Tarawa\n" +
                 "Tashkent\n" +
                 "Tbilisi \n" +
                 "Kutaisi \n" +
                 "Tegucigalpa\n" +
                 "Tehran\n" +
                 "Thimphu\n" +
                 "Tirana\n" +
                 "Tiraspol\n" +
                 "Tokyo\n" +
                 "Torshavn\n" +
                 "Tripoli\n" +
                 "Tskhinvali\n" +
                 "Tunis\n" +
                 "Ulan Bator\n" +
                 "Vaduz\n" +
                 "Valletta\n" +
                 "The Valley\n" +
                 "Vatican City\n" +
                 "Victoria\n" +
                 "Vienna\n" +
                 "Vientiane\n" +
                 "Vilnius\n" +
                 "Warsaw\n" +
                 "Washington DC\n" +
                 "Wellington\n" +
                 "West Island\n" +
                 "Willemstad\n" +
                 "Windhoek\n" +
                 "Yamoussoukro\n" +
                 "Abidjan \n" +
                 "Yaounde\n" +
                 "Yaren \n" +
                 "Yerevan\n" +
                 "Zagreb";
        String[] cities = city.split("\n");


        for (int i = 0; i < 200; i++) {
            int days = random.nextInt(20)+10;
            int time = random.nextInt(23)+1;
            int fligtime = random.nextInt(12)+3;
            int avseats = random.nextInt(299)+1;
            int price = random.nextInt(1500)+50;
            int months = random.nextInt(2)+10;
            //int countInserted = statement.executeUpdate("INSERT INTO flightplan1 VALUES ("+id+",'"+
            // cities[random.nextInt(240)]+"',"+"'2022."+months+"."+days+"',"+time+"00,"+
            // fligtime+",'"+cities[random.nextInt(240)]+"',"+avseats+","+price+");");
            id++;
        }
    }

}

